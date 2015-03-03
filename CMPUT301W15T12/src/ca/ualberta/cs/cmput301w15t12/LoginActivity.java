package ca.ualberta.cs.cmput301w15t12;

import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.R.id;
import ca.ualberta.cs.cmput301w15t12.R.layout;
import ca.ualberta.cs.cmput301w15t12.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);
		EditText username = (EditText) findViewById(R.id.editLoginUserName);
		final String name = username.getText().toString();
		
		//clickable create account button takes user to create account page
		Button newaccountbutton = (Button) findViewById(R.id.buttonNewAccount);
		newaccountbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, NewAccountActivity.class);
				startActivity(intent);
			}
		});
		
		//clickable login button takes user to Claim list page
		Button loginbutton = (Button) findViewById(R.id.buttonLogin);
		loginbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				//TODO check that userlist contains name
				
				Toast.makeText(LoginActivity.this, "No such UserName", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(LoginActivity.this, ClaimListActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
