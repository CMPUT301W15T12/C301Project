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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
	public ArrayList<String> total;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_claim_page);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
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
		
		//sets the textviews
		name.setText(claim.getName()+" - "+claim.getStatus());
		description.setText(claim.getDescription());
		String sd = df.format(claim.getStartDate());
		String ed = df.format(claim.getEndDate());
		dates.setText(sd+" - "+ed);
		destinations.setText(claim.destinationsToString());

		//total list
		ListView lv = (ListView) findViewById(R.id.listTotalSum);
		total = claim.getTotal();
		final ArrayAdapter<String> totalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, total);
		lv.setAdapter(totalAdapter);
		CLC.addListener(new Listener() {
			@Override
			public void update() {
				total = claim.getTotal();
				totalAdapter.notifyDataSetChanged();
			}
		});

		//for expense item list - adds a photo icon
		ArrayList<String> eitems = new ArrayList<String>();
		final ArrayList<ExpenseItem> Items = claim.getExpenseItems();
		Integer[] imageId = new Integer[Items.size()];
		for (int i = 0; i < Items.size(); i++) {
			eitems.add(Items.get(i).toStringList());
			if (Items.get(i).getFlag() && Items.get(i).getReceipt() && Items.get(i).getBoolLocation()) {
				imageId[i] = R.drawable.trio;
			} else if (Items.get(i).getFlag() && Items.get(i).getBoolLocation()){
				imageId[i] = R.drawable.globeflag;
			} else if (Items.get(i).getReceipt() &&Items.get(i).getBoolLocation()) {
				imageId[i] = R.drawable.globereceipt;
			} else if (Items.get(i).getBoolLocation()) {
				imageId[i] = R.drawable.globe;
			} else if (Items.get(i).getFlag() && Items.get(i).getReceipt()) {
				imageId[i] = R.drawable.both;
			} else if (Items.get(i).getFlag()) {
				imageId[i] = R.drawable.flagged;
			} else if (Items.get(i).getReceipt()) {
				imageId[i] = R.drawable.receipt;
			} else {
				imageId[i] = R.drawable.none;
			}
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
		if (!claim.editable()) {
			Toast.makeText(ClaimActivity.this, "No edits allowed", Toast.LENGTH_LONG).show();
		} else if (claim.incomplete()){
			AlertDialog.Builder adb = new AlertDialog.Builder(ClaimActivity.this);
			adb.setMessage("Submit this Claim? \n There are Incomplete Fields.");
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
		} else {	
			AlertDialog.Builder adb = new AlertDialog.Builder(ClaimActivity.this);
			adb.setMessage("Submit this Claim?");
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

	//menu item see comments
	public void seeComments(MenuItem menu) {
		Intent intent = new Intent(ClaimActivity.this, SeeCommentsActivity.class);
		intent.putExtra("claim_id", id);
		startActivity(intent);
	}

	public Claim getFormFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	public Button getSubmitButton() {
		// TODO Auto-generated method stub
		return null;
	}

	public void showSelectedClaim()
	{

		// TODO Auto-generated method stub
		
	}

}
