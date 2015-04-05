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

import android.location.Location;
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

				s.remove(arg2);
				D.remove(arg2);
				parentActivity.setDestination(D);
				adapter.notifyDataSetChanged();
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
							//https://github.com/joshua2ua/MockLocationTester
							Destination dest = new Destination(d1, d2, location);	
							D.add(dest);
							parentActivity.setDestination(D);
							adapter.add(dest.toString());
							adapter.notifyDataSetChanged();
							
						}
					});
					adb.setNegativeButton("Remote Location", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(AddDestinationsActivity.this, MapActivity.class);
							intent.putExtra("option","add");
							startActivityForResult(intent, 0);
						}
					});
					adb.show();

				}
			}
		});


	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK){
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,s);
			final EditText destination = (EditText) findViewById(R.id.editAddDestination);
			final EditText description = (EditText) findViewById(R.id.editAddDestinationDescription);
			final String d1 = destination.getText().toString();
			final String d2 = description.getText().toString();
			final Location location2 = new Location("location");
			double latitude = data.getExtras().getDouble("latitude");
			double longitude = data.getExtras().getDouble("longitude");
			location2.setLatitude(latitude);
			location2.setLongitude(longitude);
			location = location2;
			Toast.makeText(AddDestinationsActivity.this, "Remote Location Saved",Toast.LENGTH_SHORT).show();
			destination.setText("");
			description.setText("");
			destination.requestFocus();
			Destination dest = new Destination(d1, d2, location);	
			D.add(dest);
			parentActivity.setDestination(D);
			adapter.add(dest.toString());
			adapter.notifyDataSetChanged();
		}
		else{
			Toast.makeText(AddDestinationsActivity.this, "Invalid Location",Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_destinations, menu);
		return true;
	}

}
