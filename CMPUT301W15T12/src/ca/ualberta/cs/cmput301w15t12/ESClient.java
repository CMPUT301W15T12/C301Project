package ca.ualberta.cs.cmput301w15t12;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.location.Location;

//to DELETE DATA curl -XDELETE <recordUrl>
public class ESClient {
	private Gson gson = new Gson();
	private HttpClient httpclient = new DefaultHttpClient();
	private static String recordUrl = "http://cmput301.softwareprocess.es:8080/cmput301w15t12/record/1";

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
	public void saveRecordToServer(){
		RecordContainer loadedRecordContainer = new RecordContainer(new ClaimList().getAllClaims(),new UserList().getUsers());
		HttpPost httpPost = new HttpPost(recordUrl);
		StringEntity stringentity = null;
		try {
			stringentity = new StringEntity(gson.toJson(loadedRecordContainer));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpPost.setHeader("Accept","application/json");

		httpPost.setEntity(stringentity);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
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
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * get the http response and return json string
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
