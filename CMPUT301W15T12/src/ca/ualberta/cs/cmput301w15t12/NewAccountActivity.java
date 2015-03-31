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

import java.util.Date;

import ca.ualberta.cs.cmput301w15t12.R;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewAccountActivity extends Activity
{
	public Location location;
	public static final String MOCK_PROVIDER = "mockLocationProvider";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_account);
		UserListManager.initManager(this.getApplicationContext());
		final EditText username = (EditText) findViewById(R.id.editNewUserName);
		username.requestFocus();
		final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		//clickable create account button creates new user and takes user to choose claim list page
		Button createbutton = (Button) findViewById(R.id.buttonNewAccountDone);
		createbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				UserListController ULC = new UserListController();
				EditText p1 = (EditText) findViewById(R.id.editCreatePassword);
				EditText p2 = (EditText) findViewById(R.id.editConfirmPassword);
				if (!p1.getText().toString().equals(p2.getText().toString())) {
					Toast.makeText(NewAccountActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
				} else {
					try {
						String un = username.getText().toString();
						if (location == null) {
							Toast.makeText(NewAccountActivity.this,"No Home Geolocation chosen", Toast.LENGTH_SHORT).show();
						} else {
							ULC.addUserWithPass(un, p1.getText().toString());
							User user = UserListController.getUserList().getUser(un);
							user.setLocation(location);
							Intent intent = new Intent(NewAccountActivity.this, ChooseListActivity.class);
							intent.putExtra("username", un);
							startActivity(intent);
						}
					} catch (Exception e) {
						Toast.makeText(NewAccountActivity.this, "UserName already in use", Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				}

			}
		});

		Button geolocation = (Button) findViewById(R.id.buttonUserLocation);
		geolocation.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				AlertDialog.Builder adb = new AlertDialog.Builder(NewAccountActivity.this);
				adb.setMessage("Do you want to set your current location as your home, or choose remotely? ");
				adb.setCancelable(true);
				adb.setPositiveButton("Current Location", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
						if (location == null){
							Toast.makeText(NewAccountActivity.this,"nope",Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(NewAccountActivity.this,"Current Location added as Home Location",Toast.LENGTH_SHORT).show();
						}
					}
				});
				adb.setNegativeButton("Remote Location", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(NewAccountActivity.this, MapActivity.class);
						startActivity(intent);
						//TODO get result
					}
				});
				adb.show();				
			}
		});

		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
	}
	//    //https://github.com/joshua2ua/MockLocationTester
	private final LocationListener listener = new LocationListener() {
		public void onLocationChanged (Location location) {
			if (location != null) {
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				Date date = new Date(location.getTime());

				Toast.makeText(NewAccountActivity.this, "The location is: \nLatitude: " + lat
						+ "\nLongitude: " + lng
						+ "\n at time: " + date.toString(), Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(NewAccountActivity.this,"nope",Toast.LENGTH_SHORT).show();
			}
		}

		public void onProviderDisabled (String provider) {

		}

		public  void onProviderEnabled (String provider) {

		}

		public void onStatusChanged (String provider, int status, Bundle extras) {

		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_account, menu);
		return true;
	}

}
