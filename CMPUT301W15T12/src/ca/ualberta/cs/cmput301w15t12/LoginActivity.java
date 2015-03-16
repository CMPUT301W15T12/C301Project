/**
 * LoginActivity displays a textbar for a username to be submitted and authenticated, 
 * or allows the user to create a new account. 
 * 
 *  Issues: (Could do) add a password?
 * 
 *   Copyright [2015] CMPUT301W15T12 https://github.com/CMPUT301W15T12
 *   licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *   
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   @author vanbelle
*/

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
		ULC = new UserListController();
		
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
