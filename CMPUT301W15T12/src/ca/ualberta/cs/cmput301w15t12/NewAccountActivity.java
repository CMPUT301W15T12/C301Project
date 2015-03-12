package ca.ualberta.cs.cmput301w15t12;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewAccountActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_account);
		UserListManager.initManager(this.getApplicationContext());

		//clickable create account button creates new user and takes user to choose claim list page
		Button createbutton = (Button) findViewById(R.id.buttonNewAccountDone);
		createbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				EditText username = (EditText) findViewById(R.id.editNewUserName);
				try {
					String un = username.getText().toString();
					UserListController.getUserList().addUser(un);
					UserListController.saveUserList();
					Intent intent = new Intent(NewAccountActivity.this, ChooseListActivity.class);
					intent.putExtra("username", un);
					startActivity(intent);
				} catch (AlreadyExistsException e) {
					Toast.makeText(NewAccountActivity.this, "UserName already in use", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_account, menu);
		return true;
	}

}
