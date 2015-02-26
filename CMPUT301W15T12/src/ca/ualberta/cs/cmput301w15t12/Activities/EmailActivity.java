package ca.ualberta.cs.cmput301w15t12.Activities;

import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.R.id;
import ca.ualberta.cs.cmput301w15t12.R.layout;
import ca.ualberta.cs.cmput301w15t12.R.menu;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
		
		recipient = (EditText) findViewById(R.id.recipient);
		subject = (EditText) findViewById(R.id.subject);
		body = (EditText) findViewById(R.id.body);
		
		//gets the correct Claim info
		//Claim Claim = ...
		
		//TODO get String format of Claim info
		//String text = Claim.toEmail();
		
		//body.setText(text);
		
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
