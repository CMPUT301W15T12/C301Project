package ca.ualberta.cs.cmput301w15t12;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import com.google.gson.Gson;
import android.location.Location;


public class ESClient {
	private Gson gson = new Gson();
	private HttpClient httpclient = new DefaultHttpClient();
	
	public void saveClaimToServer(Claim claim){
		HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/qsjiangtest/claim/"+claim.getId());
		StringEntity stringentity = null;
		try {
			stringentity = new StringEntity(gson.toJson(claim));
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
		//httpPost.releaseConnection();

	}
	
	
	
}
