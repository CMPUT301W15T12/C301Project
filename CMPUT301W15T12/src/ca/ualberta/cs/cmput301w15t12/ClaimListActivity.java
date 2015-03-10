package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ClaimListActivity extends Activity {

	public String Username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_claim_list);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
		
		//username of user passed along from list choice activity
		try {
			Username = getIntent().getExtras().getString("username");
		} catch (NullPointerException e) {
			Toast.makeText(this, "nope", Toast.LENGTH_LONG).show();
		}

		//clickable button takes the user to the add claim page when clicked
		//passes along the username so that user can be added when claim is created
		Button addbutton = (Button) findViewById(R.id.buttonAddClaim);
		addbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ClaimListActivity.this, AddClaimActivity.class);
				intent.putExtra("username", Username);
				startActivity(intent);
			}
		});

	}


	public void onResume(){
		super.onResume();

		ListView listViewClaims = (ListView) findViewById(R.id.listViewClaims);
		final ArrayList<Claim> claims2 = ClaimListController.getClaimList().getUserClaims(Username);
		ArrayList<String> claims = new ArrayList<String>();
		for (int i = 0; i < claims2.size(); i++) {
			claims.add(claims2.get(i).toStringClaimantList());
		}
		final ArrayAdapter<String> claimAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, claims);
		listViewClaims.setAdapter(claimAdapter);

		listViewClaims.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				int id = claims2.get(arg2).getId();
				Intent intent = new Intent(ClaimListActivity.this, ClaimActivity.class);
				intent.putExtra("claim_id", id);
				startActivity(intent);
			}

		});
		
		ClaimListController.getClaimList().addListener(new Listener() {
			
			@Override
			public void update() {
				final ArrayList<Claim> claims2 = ClaimListController.getClaimList().getUserClaims(Username);
				ArrayList<String> claims = new ArrayList<String>();
				for (int i = 0; i < claims2.size(); i++) {
					claims.add(claims2.get(i).toStringClaimantList());
				}
				claimAdapter.notifyDataSetChanged();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim_list, menu);
		return true;
	}

}
