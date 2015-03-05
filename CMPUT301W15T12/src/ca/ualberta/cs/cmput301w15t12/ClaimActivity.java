package ca.ualberta.cs.cmput301w15t12;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ClaimActivity extends Activity {

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
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim, menu);
		return true;
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
