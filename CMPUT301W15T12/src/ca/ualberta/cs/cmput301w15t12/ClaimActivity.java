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

public class ClaimActivity extends Activity {

	public int id;
	public Claim claim;
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_claim_page);

		//Claim passed on is stored in claim variable
		final int id = getIntent().getIntExtra("claim_id", 0);
		claim = ClaimListController.getClaimList().getClaim(id);

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
		final ArrayList<String> total = claim.getTotal();
		final ArrayAdapter<String> totalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, total);
		lv.setAdapter(totalAdapter);
		ClaimListController.getClaimList().addListener(new Listener() {
			@Override
			public void update() {
				total.clear();
				ArrayList<String> total = claim.getTotal();
				totalAdapter.notifyDataSetChanged();
			}
		});

		//for expense item list - adds a photo icon
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
		CustomList adapter = new CustomList(ClaimActivity.this, eitems, imageId);
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim, menu);
		return true;
	}

	//menu item delete claim
	public void deleteClaim(MenuItem menu) {
		AlertDialog.Builder adb = new AlertDialog.Builder(ClaimActivity.this);
		adb.setMessage("Return this Claim?");
		adb.setCancelable(true);
		adb.setPositiveButton("Return", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//TODO delete claim
				finish();
			}
		});
		adb.setNegativeButton("Cancel", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		adb.show();
	}

	//menu item edit claim
	public void editClaim(MenuItem menu) {
		Intent intent = new Intent(ClaimActivity.this, EditClaimActivity.class);
		intent.putExtra("claim_id", id);
		startActivity(intent);
	}

	//menu item submitClaim
	public void submitClaim(MenuItem menu) {
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

	public void showSelectedClaim() {
		// TODO Auto-generated method stub

	}

	public Claim getFormFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	public Button getSubmitButton() {
		// TODO Auto-generated method stub
		return null;
	}

}
