/**
 * This  Activity displays the summary info for a given claim, and 
 * allows a user to see comments, email the claim, delete or edit the claim, 
 * add expense items, or view expense items. 
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author msumner
 *
 */

public class ClaimActivity extends Activity {

	public int id;
	public Claim claim;
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	public ClaimListController CLC = new ClaimListController();
	public String Username;
	private boolean selected[];
	private ArrayList<String> tagsArrayList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_claim_page);
		try {
			Username = getIntent().getExtras().getString("username");
			id = getIntent().getIntExtra("claim_id", 0);
		} catch (NullPointerException e) {
		}

		claim = CLC.getClaim(id);

		//clickable button takes the user to the edit add item page when clicked
		Button addbutton = (Button) findViewById(R.id.buttonAddItem);
		addbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				if (!claim.editable()) {
					Toast.makeText(ClaimActivity.this, "No Edits allowed",Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(ClaimActivity.this, AddItemActivity.class);
					intent.putExtra("claim_id", id);
					intent.putExtra("option","add");
					startActivity(intent);
				}
			}
		});

	}

	@Override
	public void onResume() {
		super.onResume();
		//gets the textviews
		TextView name = (TextView) findViewById(R.id.textClaimName);
		TextView description = (TextView) findViewById(R.id.textClaimDescription);
		TextView dates = (TextView) findViewById(R.id.textStarttoEndDate);
		TextView destinations = (TextView) findViewById(R.id.textClaimDestinations);
		TextView tags = (TextView) findViewById(R.id.textViewTagView);

		//sets the textviews
		name.setText(claim.getName()+" - "+claim.getStatus());
		description.setText(claim.getDescription());
		String sd = df.format(claim.getStartDate());
		String ed = df.format(claim.getEndDate());
		dates.setText(sd+" - "+ed);
		destinations.setText(claim.destinationsToString());
		tagsArrayList = claim.getTagList();
		tags.setText(claim.toStringTagList(tagsArrayList));

		//total list
		TextView tv = (TextView) findViewById(R.id.listTotalSum);
		String total = claim.getTotalString();
		tv.setText(total);

		//for expense item list - adds a photo icon
		ArrayList<String> eitems = new ArrayList<String>();
		final ArrayList<ExpenseItem> Items = claim.getExpenseItems();
		Integer[] imageId = claim.getIds();
		for (int i = 0; i < Items.size();i++){
			eitems.add(Items.get(i).toStringList());
		}

		final CustomList adapter = new CustomList(ClaimActivity.this, eitems, imageId);
		ListView list = (ListView) findViewById(R.id.listExpenseItems);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3)
			{
				Intent intent = new Intent(ClaimActivity.this, ExpenseItemActivity.class);
				intent.putExtra("claim_id", id);
				intent.putExtra("item_index", position);
				startActivity(intent);
			}
		});

		//adds a listener to the expense item list
		CLC.addListener(new Listener() {
			@Override
			public void update() {
				ArrayList<String> eitems = new ArrayList<String>();
				final ArrayList<ExpenseItem> Items = claim.getExpenseItems();
				Integer[] imageId = new Integer[Items.size()];
				for (int i = 0; i < Items.size(); i++) {
					eitems.add(Items.get(i).toStringList());
					if (Items.get(i).getFlag() && Items.get(i).getReceipt()) {
						imageId[i] = R.drawable.both;
					} else if (Items.get(i).getFlag()) {
						imageId[i] = R.drawable.flagged;
					} else if (Items.get(i).getReceipt()) {
						imageId[i] = R.drawable.receipt;
					} else {
						imageId[i] = R.drawable.none;
					}
				}

				adapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim, menu);
		return true;
	}

	//menu item delete claim
	public void deleteClaim(MenuItem menu) {
		if (!claim.editable()) {
			Toast.makeText(ClaimActivity.this, "No edits allowed", Toast.LENGTH_LONG).show();
		} else {

			AlertDialog.Builder adb = new AlertDialog.Builder(ClaimActivity.this);
			adb.setMessage("Delete this Claim?");
			adb.setCancelable(true);
			adb.setPositiveButton("Delete", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					CLC.removeClaim(id);
					finish();
				}
			});
			adb.setNegativeButton("Cancel", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			adb.show();
		}
	}

	//menu item edit claim
	public void editClaim(MenuItem menu) {
		if (!claim.editable()) {
			Toast.makeText(ClaimActivity.this, "No edits allowed", Toast.LENGTH_LONG).show();
		} else {
			Intent intent = new Intent(ClaimActivity.this, TabClaimActivity.class);
			intent.putExtra("claim_id", id);
			intent.putExtra("option", "Edit");
			intent.putExtra("username", Username);
			startActivity(intent);
		}
	}

	//menu item submitClaim
	public void submitClaim(MenuItem menu) {
		String text = "Submit this Claim?";
		if (claim.incomplete()) {
			text = "Submit this Claim? \n There are Incomplete Fields.";
		}
		if (!claim.editable()) {
			Toast.makeText(ClaimActivity.this, "No edits allowed", Toast.LENGTH_LONG).show();
		} else {	
			AlertDialog.Builder adb = new AlertDialog.Builder(ClaimActivity.this);
			adb.setMessage(text);
			adb.setCancelable(true);
			adb.setPositiveButton("Submit", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					claim.setStatus("Submitted");
					finish();
				}
			});
			adb.setNegativeButton("Cancel", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			adb.show();
		}
	}

	//menu item email claim	
	public void emailClaim(MenuItem menu) {
		Intent intent = new Intent(ClaimActivity.this, EmailActivity.class);
		intent.putExtra("claim_id", id);
		startActivity(intent);
	}

	//menu item see comments takes user to seeComments activity
	public void seeComments(MenuItem menu) {
		Intent intent = new Intent(ClaimActivity.this, SeeCommentsActivity.class);
		intent.putExtra("claim_id", id);
		startActivity(intent);
	}
	
	//open map activity to see all locations associated with a claim
	public void seeLocations(MenuItem menu) {
		Intent intent = new Intent(ClaimActivity.this, MapActivity.class);
		intent.putExtra("claim_id", id);
		intent.putExtra("option","see");
		intent.putExtra("username", Username);
		startActivity(intent);
	}
	
	/** A dialog box which opens to allow the user to choose from any previously 
	 * existing tags, and save them to the claim. It also allows the user to 
	 * choose to add a new custom tag.
	 * @param view
	 */
	public void editTags(MenuItem menu){
		User user = UserListController.getUserList().getUser(Username);
		ArrayList<String> tagList = user.getTagList();
		AlertDialog.Builder builder = new AlertDialog.Builder(ClaimActivity.this);
		final String[] userTags = tagList.toArray(new String[tagList.size()]);
		builder.setTitle("Choose Tags");

		//check the items already included
		selected = new boolean[tagList.size()];
		for (int i = 0;  i < tagList.size(); i++) {
			if (tagsArrayList.contains(userTags[i])) {
				selected[i] = true;
			}
		}
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
		//allow the user to add a new tag
		builder.setPositiveButton("Add New", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				TextView tags = (TextView) findViewById(R.id.textViewTagView);
				claim.setTagList(tagsArrayList);
				tags.setText(claim.toStringTagList(tagsArrayList));
				addTagDialog();
			}

		});
		//save any added tags from the user
		builder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				TextView tags = (TextView) findViewById(R.id.textViewTagView);
				claim.setTagList(tagsArrayList);
				tags.setText(claim.toStringTagList(tagsArrayList));
			}
		});
		builder.show();

	}
	
	/**Saves a tag the user just made to the claim list
	 */
	protected void addTagDialog(){
		final User user = UserListController.getUserList().getUser(Username);
		LayoutInflater layoutInflater = LayoutInflater.from(ClaimActivity.this);
		View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ClaimActivity.this);
		alertDialogBuilder.setView(promptView);

		final EditText editText = (EditText) promptView.findViewById(R.id.editTextTagName);
		
		alertDialogBuilder.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//adds tags to userList and claimList
				TextView tags = (TextView) findViewById(R.id.textViewTagView);
				if (!tagsArrayList.contains(editText.getText().toString())) {
					tagsArrayList.add(editText.getText().toString());
				}
				if (!user.getTagList().contains(editText.getText().toString())) {
					user.addTag(editText.getText().toString());
				}
				claim.setTagList(tagsArrayList);
				tags.setText(claim.toStringTagList(tagsArrayList));
			}
		})
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

}
