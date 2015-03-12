package ca.ualberta.cs.cmput301w15t12;

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
	public UserListController ULC;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);
		UserListManager.initManager(this.getApplicationContext());
		UserListController ULC = new UserListController();
		
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
				EditText username = (EditText) findViewById(R.id.editLoginUserName);
				String name = username.getText().toString();
				if (!ULC.authenticateUser(name)) {
					Toast.makeText(LoginActivity.this, "No such UserName", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(LoginActivity.this, name, Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(LoginActivity.this, ChooseListActivity.class);
					intent.putExtra("username", name);
					startActivity(intent);
				}
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
