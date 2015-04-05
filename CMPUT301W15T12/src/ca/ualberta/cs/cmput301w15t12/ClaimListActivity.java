/**
 * This  Activity displays a list of all claims that the user has made, 
 * and also allows the user to filter these claims by tag. 
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

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.cmput301w15t12.R;


public class ClaimListActivity extends Activity {

	public String Username;
	public ClaimListController CLC = new ClaimListController();
	public User user = null;
	public boolean selected[];
	public ArrayList<String> tagsArrayList = new ArrayList<String>(); 
	public ArrayList<Claim> claims = new ArrayList<Claim>();
	public Integer[] imageId;
	public ArrayList<String> cl = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_claim_list);

		//username of user passed along from list choice activity - null pointer 
		//is returned when coming back from the add claim activity put it doesnt
		//overwrite the previous info so its ok!
		try {
			Username = getIntent().getExtras().getString("username");
		} catch (NullPointerException e) {
		}

		//gets the use corresponding to the UserName
		for (int i = 0; i < UserListController.getUserList().getUsers().size(); i++) {
			if (UserListController.getUserList().get(i).getUserName().equals(Username)) {
				user = UserListController.getUserList().get(i);
			}
		}
		
		EditText search = (EditText) findViewById(R.id.editTextSearchTags);
		search.setText("");

		//clickable button takes the user to the add claim page when clicked
		//passes along the username so that user can be added when claim is created
		Button addbutton = (Button) findViewById(R.id.buttonAddClaim);
		addbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ClaimListActivity.this, TabClaimActivity.class);
				intent.putExtra("username", Username);
				intent.putExtra("option", "Add");
				startActivity(intent);
			}
		});

		Button manager = (Button) findViewById(R.id.buttonManageTags);
		manager.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(ClaimListActivity.this, TagManagerActivity.class);
				intent.putExtra("username", Username);
				startActivity(intent);
			}
		});

	}


	public void onResume(){
		super.onResume();
		updateList();
	}

	public void updateList(){
		ListView listViewClaims = (ListView) findViewById(R.id.listViewClaims);
		if (tagsArrayList.size() == 0) {
			claims = CLC.filterByClaimant(user);
		} else {
			claims = CLC.filterByTag(user.getUserName(), tagsArrayList);
		}
		imageId = new Integer[claims.size()];
		cl = new ArrayList<String>();
		for (int i = 0; i < claims.size(); i++) {
			cl.add(claims.get(i).toStringClaimantList());
			if (claims.get(i).getDestination().size() == 0){
				imageId[i] = R.drawable.none;
			} else {
				//2015/03/27 - http://stackoverflow.com/questions/8836551/calculating-distance-between-multiple-geopositions-in-java
				Location l = claims.get(i).getDestination().get(0).getLocation();
				Location u = user.getLocation();
				Float distance = l.distanceTo(u);
				if (distance < 3000000) {
					imageId[i] = R.drawable.planeone;
				} else if (distance < 15000000) {
					imageId[i] = R.drawable.planetwo;
				} else if (distance > 15000000) {
					imageId[i] = R.drawable.planethree;			    		
				}
			}
		}
		final CustomList claimAdapter = new CustomList(this, cl, imageId) {
			@Override
			//http://stackoverflow.com/questions/16686413/text-color-arrayadapter-with-simple-list-item-single-choice
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = ClaimListActivity.this.getLayoutInflater();
				View rowView= inflater.inflate(R.layout.list_single, null, true);
				TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
				ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
				txtTitle.setText(cl.get(position));
				imageView.setImageResource(imageId[position]);
				//			    View view = super.getView(position, convertView, parent);
				//			    TextView txtTitle = (TextView) view.findViewById(android.R.id.text1);
				if (claims.get(position).getDestination().size() == 0){
					txtTitle.setTextColor(Color.BLACK);
				} else {
					//2015/03/27 - http://stackoverflow.com/questions/8836551/calculating-distance-between-multiple-geopositions-in-java
					Location l = claims.get(position).getDestination().get(0).getLocation();
					Location u = user.getLocation();
					Float distance = l.distanceTo(u);
					if (distance < 3000000) {
						txtTitle.setTextColor(Color.GREEN);
					} else if (distance < 15000000) {
						txtTitle.setTextColor(Color.BLUE);
					} else if (distance > 15000000) {
						txtTitle.setTextColor(Color.RED);			    		
					}
				}
				return rowView;
			}
		};
		listViewClaims.setAdapter(claimAdapter);

		listViewClaims.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				int id = claims.get(arg2).getId();
				Intent intent = new Intent(ClaimListActivity.this, ClaimActivity.class);
				intent.putExtra("claim_id", id);
				intent.putExtra("username", Username);
				startActivity(intent);
			}

		});

		CLC.addListener(new Listener() {

			@Override
			public void update() {
				claims.clear();
				cl.clear();
				claims = CLC.filterByClaimant(user);
				for (int i = 0; i < claims.size(); i++) {
					cl.add(claims.get(i).toStringClaimantList());
				}

				claimAdapter.notifyDataSetChanged();
			}
		});
	}

	/** 
	 * @param view
	 */
	//initialize the choose from previous tags dialogue
	public void onClickTags(View view){
		ArrayList<String> tagList = user.getTagList();
		AlertDialog.Builder builder = new AlertDialog.Builder(ClaimListActivity.this);
		final String[] userTags = (String[]) tagList.toArray(new String[tagList.size()]);
		builder.setTitle("Choose Tags");
		tagsArrayList.clear();

		//when an item is clicked
		builder.setMultiChoiceItems(userTags, selected,
				new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {	
				if (isChecked && !tagsArrayList.contains(userTags[which])) {
					// If the user checked the item, add it to the selected items
					tagsArrayList.add(userTags[which]);
				} else {
					//if the item was unchecked remove the item
					tagsArrayList.remove(userTags[which]);
				}
			}
		});

		builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				EditText tags = (EditText) findViewById(R.id.editTextSearchTags);
				String block = "";
				for (int i = 0; i < tagsArrayList.size(); i++) {
					block += tagsArrayList.get(i).toString();
					if (i != tagsArrayList.size() - 1) {
						block += ", ";
					}
				}
				tags.setText(block);
				updateList();
			}

		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		builder.show();

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claimant_claim_list, menu);
		return true;
	}

}
