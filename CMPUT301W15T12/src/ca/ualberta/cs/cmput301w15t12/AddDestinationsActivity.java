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

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddDestinationsActivity extends Activity
{
	public ArrayList<Destination> D = new ArrayList<Destination>();
	public TabClaimActivity parentActivity;

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
		ArrayList<String> s = new ArrayList<String>();

		for (int i = 0; i < ns.size(); i++) {
			s.add(ns.get(i).toString());
		}

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,s);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{

				// TODO Auto-generated method stub

			}

		});


		Button addbutton = (Button) findViewById(R.id.buttonAddDestination);
		addbutton.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				EditText destination = (EditText) findViewById(R.id.editAddDestination);
				EditText description = (EditText) findViewById(R.id.editAddDestinationDescription);
				String d1 = destination.getText().toString();
				String d2 = description.getText().toString();
				Boolean contains = false;
				if (d1.equals("") || d2.equals("")) {
					Toast.makeText(AddDestinationsActivity.this, "Incomplete Destination", Toast.LENGTH_SHORT).show();
				} else {
					for (int i = 0; i < D.size(); i++){
						if (D.get(i).getDestination().equals(d1) && D.get(i).getDescription().equals(d2)) {
							contains = true;
							break;
						}
					}
					if (contains) {
						Toast.makeText(AddDestinationsActivity.this, "Destination already added", Toast.LENGTH_SHORT).show();
					} else {
						Destination dest = new Destination(d1, d2);	
						D.add(dest);
						parentActivity.setDestination(D);
						adapter.add(dest.toString());
						adapter.notifyDataSetChanged();
						destination.setText("");
						description.setText("");
						destination.requestFocus();
					}
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

}
