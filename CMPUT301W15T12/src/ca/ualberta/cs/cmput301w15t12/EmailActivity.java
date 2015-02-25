package ca.ualberta.cs.cmput301w15t12;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailActivity extends Activity
{
	private EditText recipient;
	private EditText subject;
	private EditText body;
	public String claim;
	public String item;
	public String total;
	public String text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
		
		recipient = (EditText) findViewById(R.id.recipient);
		subject = (EditText) findViewById(R.id.subject);
		body = (EditText) findViewById(R.id.body);
		text = "";
		
		//gets the correct Claim info
		
		//TODO get Claim String
		text += claim; 
		text += "\n";
		
		//TODO get Total String
		text += total;
		text += "\n";
		
		//TODO get Items String
		text += item;
		
		body.setText(text);
		
		Button sendBtn = (Button) findViewById(R.id.sendEmail);
		sendBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				sendEmail();
				Toast.makeText(EmailActivity.this,"Email sent", Toast.LENGTH_SHORT).show();
				//TODO go back to Claim page
			}
		});
	}
	
	//sends the email
	protected void sendEmail() {
		Intent email = new Intent(Intent.ACTION_SENDTO);
		String uriText = "mailto:"+Uri.encode(recipient.getText().toString())+"?subject="+Uri.encode(subject.getText().toString())+"&body="+Uri.encode(body.getText().toString());
		Uri uri = Uri.parse(uriText);
		email.setData(uri);
		startActivity(Intent.createChooser(email, "Send mail ..."));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email, menu);
		return true;
	}

}
