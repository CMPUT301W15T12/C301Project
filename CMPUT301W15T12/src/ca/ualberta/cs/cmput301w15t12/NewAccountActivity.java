/**
 * NewAccountActivity creates a new user.
 * 
 * Issues: No persistance
 * (Could do) add a password?
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
	UserListController ULC;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_account);
		UserListManager.initManager(this.getApplicationContext());
		ULC = new UserListController();

		//clickable create account button creates new user and takes user to choose claim list page
		Button createbutton = (Button) findViewById(R.id.buttonNewAccountDone);
		createbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				EditText username = (EditText) findViewById(R.id.editNewUserName);
				try {
					String un = username.getText().toString();
					ULC.addUser(un);
					Intent intent = new Intent(NewAccountActivity.this, ChooseListActivity.class);
					intent.putExtra("username", un);
					startActivity(intent);
				} catch (Exception e) {
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
