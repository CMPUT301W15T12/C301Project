package ca.ualberta.cs.cmput301w15t12;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;

import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Environment;
import android.util.Base64;

//to DELETE DATA curl -XDELETE <recordUrl>
public class ESClient {
	private Gson gson = new Gson();
	private HttpClient httpclient = new DefaultHttpClient();
	private static String recordUrl = "http://cmput301.softwareprocess.es:8080/cmput301w15t12/record/1";
	private static String imageUrl = "http://cmput301.softwareprocess.es:8080/cmput301w15t12/image";
	//[NOTE]: image url is the base directory. If you want to see all the images on server type 
	//"http://cmput301.softwareprocess.es:8080/cmput301w15t12/image/_search"
	
	

	class PhotoContainer{
		private String photoBase64String;
		public PhotoContainer(File file) throws IOException{
			byte[] bytes = FileUtils.readFileToByteArray(file);
			this.photoBase64String = Base64.encodeToString(bytes, Base64.DEFAULT);
		}
		public File getPhoto() throws IOException{
			byte[] bytes = Base64.decode(this.photoBase64String,  Base64.DEFAULT);
			
			String folder = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/loadedImage";
			File folderF = new File(folder);
			if (!folderF.exists()) {
				folderF.mkdir();
			}		
			String outputFilePath = folder + "/"+ String.valueOf(System.currentTimeMillis()) + ".txt";
			File outputFile = new File(outputFilePath);
			FileUtils.writeByteArrayToFile(outputFile, bytes);
			return outputFile;
		}
	}
	
	class RecordContainer{
		private ArrayList<Claim> claims;
		private ArrayList<User> users;
		
		public RecordContainer(ArrayList<Claim> claims,ArrayList<User> users){
			this.claims = claims;
			this.users = users;
		}
		
		public ArrayList<Claim> getClaims(){
			return this.claims;
		}
		public ArrayList<User> getUsers(){
			return this.users;
		}
	}
	//From CMPUT301 in class example
	class ElasticSearchResponse<T> {
	    String _index;
	    String _type;
	    String _id;
	    int _version;
	    boolean exists;
	    T _source;
	    double max_score;
	    public T getSource() {
	        return _source;
	    }
	    public String getId(){
	    	return _id;
	    }
	}
	/** Save record to elastic search server
	 * @param none
	 */ 
	public void saveRecordToServer(){
		try {
			RecordContainer loadedRecordContainer = new RecordContainer(new ClaimList().getAllClaims(),new UserList().getUsers());
			HttpPost httpPost = new HttpPost(recordUrl);
			StringEntity stringentity = null;
	
			stringentity = new StringEntity(gson.toJson(loadedRecordContainer));
	
			httpPost.setHeader("Accept","application/json");
	
			httpPost.setEntity(stringentity);
			httpclient.execute(httpPost);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	/** Load record from elastic search server
	 * @param none
	 */ 
	public void loadRecordFromServer(){
		try{
			HttpGet getRequest = new HttpGet(recordUrl);
			getRequest.addHeader("Accept","application/json");
			HttpResponse response = httpclient.execute(getRequest);
			String json = getEntityContent(response);
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<RecordContainer>>(){}.getType();
			ElasticSearchResponse<RecordContainer> esResponse = gson.fromJson(json, elasticSearchResponseType);
			RecordContainer incomingRecordContainer = esResponse.getSource();
			new ClaimList().load(incomingRecordContainer.getClaims());
			UserList.users = incomingRecordContainer.getUsers();		
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}	
	/** Load image from elastic search server
	 * @param image's relative URI on es server 
	 * @return image file
	 */ 
	public File loadImageFileFromServer(URI uri){
		try{
			String targetImageUrl = imageUrl+"/"+uri.getPath();
			HttpGet getRequest = new HttpGet(targetImageUrl);
			getRequest.addHeader("Accept","application/json");
			HttpResponse response = httpclient.execute(getRequest);
			String json = getEntityContent(response);
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<PhotoContainer>>(){}.getType();
			ElasticSearchResponse<PhotoContainer> esResponse = gson.fromJson(json, elasticSearchResponseType);
			PhotoContainer incomingPhotoContainer = esResponse.getSource();
			return incomingPhotoContainer.getPhoto();
		} catch (Exception e) {
			return null;
		} 
	}
	/** Save image to elastic search server
	 * @param image file
	 * @return image URI
	 */ 
	public URI saveImageFileToServer(File file){
		try{
			PhotoContainer photoContainer = new PhotoContainer(file);
			HttpPost httpPost = new HttpPost(imageUrl);
			StringEntity stringentity = new StringEntity(gson.toJson(photoContainer));
			httpPost.setEntity(stringentity);
			httpPost.setHeader("Accept","application/json");
			HttpResponse response = httpclient.execute(httpPost);
			
			//The status code 201 is used by the server to indicate successful saving
			if (response.getStatusLine().getStatusCode()!=201){
				return null;
			}
			
			String reponseJson = getEntityContent(response);
			ElasticSearchResponse elasticSearchResponse = gson.fromJson(reponseJson, ElasticSearchResponse.class);
			return new URI(elasticSearchResponse.getId());
		}catch(Exception e){
			return null;
		}
	}
	
	/** From CMPUT301 in class example, get the http response and return json string
	 * @param HttpResponse response
	 * @return String entity
	 */
	private String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));
		String output;
		System.err.println("Output from Server -> ");
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:"+json);
		return json;
	}
	
	
}
