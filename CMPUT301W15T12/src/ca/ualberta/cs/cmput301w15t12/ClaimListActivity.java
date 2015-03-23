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
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ClaimListActivity extends Activity {

	public String Username;
	public ClaimListController CLC = new ClaimListController();
	public User user = null;
	public boolean selected[];
	public ArrayList<String> tagsArrayList = new ArrayList<String>(); 
	public ArrayList<Claim> claims = new ArrayList<Claim>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_claim_list);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());

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
		if (user == null) {
			Toast.makeText(this, "no User found", Toast.LENGTH_SHORT).show();
		}


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
		final ArrayList<String> cl = new ArrayList<String>();
		for (int i = 0; i < claims.size(); i++) {
			cl.add(claims.get(i).toStringClaimantList());
		}
		final ArrayAdapter<String> claimAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cl);
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
		EditText tags = (EditText) findViewById(R.id.editTextSearchTags);
		tagsArrayList = new ArrayList<String>();
		final ArrayList<String> tagList = user.getTagList();
		AlertDialog.Builder builder = new AlertDialog.Builder(ClaimListActivity.this);
		final String[] userTags = (String[]) tagList.toArray(new String[tagList.size()]);
		builder.setTitle("Choose Tags");

		//check the items already included
//		selected = new boolean[tagList.size()];
//		for (int i = 0;  i < tagList.size(); i++) {
//			selected[i] = false;
//		}

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim_list, menu);
		return true;
	}

}
