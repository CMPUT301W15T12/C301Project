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
import android.widget.ListView;

public class ClaimListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimant_claim_list);
	}
	
	public void onResume(){
		super.onResume();
		
		ListView listViewClaims = (ListView) findViewById(R.id.listViewClaims);
		ArrayList<Claim> claims = ClaimListController.getClaimList().getClaims();
		final ArrayAdapter<Claim> claimAdapter = new ArrayAdapter<Claim>
			(this, android.R.layout.simple_list_item_1, claims);
		listViewClaims.setAdapter(claimAdapter);
		
		listViewClaims.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				Intent intent = new Intent(ClaimListActivity.this, ClaimActivity.class);
				startActivity(intent);
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
