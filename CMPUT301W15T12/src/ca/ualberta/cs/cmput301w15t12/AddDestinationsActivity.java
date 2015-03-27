/**
 * This  Activity allows a user to add, edit or delete the destinations
 * associated with a claim
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
 *   @author megsum
 */

package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Date;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddDestinationsActivity extends Activity
{
	public ArrayList<Destination> D = new ArrayList<Destination>();
	public TabClaimActivity parentActivity;
	public ArrayList<String> s = new ArrayList<String>();
	public Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_destinations);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
		ClaimListController CLC = new ClaimListController();

		//username of user passed along from list choice activity
		final String option = getIntent().getExtras().getString("option");
		parentActivity = (TabClaimActivity) this.getParent();
		parentActivity.setDestination(D);

		if (option.equals("Edit")){
			final int id = getIntent().getIntExtra("claim_id",1000000);
			Claim claim = CLC.getClaim(id);
			D = claim.getDestination();
			parentActivity.setDestination(D);
		}

		Button saveButton = (Button) findViewById(R.id.buttonSaveDestinations);
		saveButton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				parentActivity.chooseTab(0);		
			}
		});


		ListView list = (ListView) findViewById(R.id.listDestinations);
		ArrayList<Destination> ns = parentActivity.getDestination();
		s.clear();

		for (int i = 0; i < ns.size(); i++) {
			s.add(ns.get(i).toString());
		}

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,s);
		list.setAdapter(adapter);
		list.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3)
			{

				s.remove(arg2);adapter.notifyDataSetChanged();
				return false;
			}
		});


		Button addbutton = (Button) findViewById(R.id.buttonAddDestination);
		addbutton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				final EditText destination = (EditText) findViewById(R.id.editAddDestination);
				final EditText description = (EditText) findViewById(R.id.editAddDestinationDescription);
				final String d1 = destination.getText().toString();
				final String d2 = description.getText().toString();
				if (d1.equals("") || d2.equals("")) {
					Toast.makeText(AddDestinationsActivity.this, "Incomplete Destination", Toast.LENGTH_SHORT).show();
				} else {
					AlertDialog.Builder adb = new AlertDialog.Builder(AddDestinationsActivity.this);
					adb.setMessage("Do you want to use your current location for your destination, or choose remotely? ");
					adb.setCancelable(true);
					adb.setPositiveButton("Current Location", new OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
							location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, listener);
							if (location == null){
								Toast.makeText(AddDestinationsActivity.this,"Error No Location added",Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(AddDestinationsActivity.this,"Current Location added as Destination Location",Toast.LENGTH_SHORT).show();
								Destination dest = new Destination(d1, d2, location);	
								D.add(dest);
								parentActivity.setDestination(D);
								adapter.add(dest.toString());
								adapter.notifyDataSetChanged();
								destination.setText("");
								description.setText("");
								destination.requestFocus();
							}
						}
					});
					adb.setNegativeButton("Remote Location", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(AddDestinationsActivity.this, MapActivity.class);
							startActivity(intent);
							//TODO get result
							if (location == null){
								Toast.makeText(AddDestinationsActivity.this,"Error No Location added",Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(AddDestinationsActivity.this,"Chosen Location added as Destination Location",Toast.LENGTH_SHORT).show();
								Destination dest = new Destination(d1, d2, location);	
								D.add(dest);
								parentActivity.setDestination(D);
								adapter.add(dest.toString());
								adapter.notifyDataSetChanged();
								destination.setText("");
								description.setText("");
								destination.requestFocus();
							}
						}
					});
					adb.show();
					
				}
			}
	});


}

@Override
public boolean onCreateOptionsMenu(Menu menu)
{

	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.add_destinations, menu);
	return true;
}

//https://github.com/joshua2ua/MockLocationTester
private final LocationListener listener = new LocationListener() {
	public void onLocationChanged (Location location) {
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			Date date = new Date(location.getTime());

			Toast.makeText(AddDestinationsActivity.this, "The location is: \nLatitude: " + lat
					+ "\nLongitude: " + lng
					+ "\n at time: " + date.toString(), Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(AddDestinationsActivity.this,"nope",Toast.LENGTH_SHORT).show();
		}
	}

	public void onProviderDisabled (String provider) {

	}

	public  void onProviderEnabled (String provider) {

	}

	public void onStatusChanged (String provider, int status, Bundle extras) {

	}
};

}
