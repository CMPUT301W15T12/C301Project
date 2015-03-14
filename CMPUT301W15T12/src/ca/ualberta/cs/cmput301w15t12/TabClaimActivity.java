package ca.ualberta.cs.cmput301w15t12;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class TabClaimActivity extends TabActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_host);

		String username = getIntent().getExtras().getString("username");
		String option = getIntent().getExtras().getString("option");

		TabHost tabHost = getTabHost();

		if (option.equals("Add")) {
			// Claim tab
			Intent intentClaim = new Intent().setClass(this, AddClaimActivity.class);
			intentClaim.putExtra("username", username);
			intentClaim.putExtra("option", "Add");
			TabSpec tabSpecClaim = tabHost
					.newTabSpec("addClaim")
					.setIndicator("Basic Info")
					.setContent(intentClaim);

			// Approver tab
			Intent intentDestinations = new Intent().setClass(this, AddDestinationsActivity.class);
			intentDestinations.putExtra("username", username);
			intentDestinations.putExtra("option", "Add");
			TabSpec tabSpecDestinations = tabHost
					.newTabSpec("destinations")
					.setIndicator("Destinations")
					.setContent(intentDestinations);

			tabHost.addTab(tabSpecClaim);
			tabHost.addTab(tabSpecDestinations);

		} else {
			int claim_id = getIntent().getIntExtra("claim_id", 0);

			// Claim tab
			Intent intentClaim = new Intent().setClass(this, AddClaimActivity.class);
			intentClaim.putExtra("username", username);
			intentClaim.putExtra("claim_id", claim_id);
			intentClaim.putExtra("option", "Edit");
			TabSpec tabSpecClaim = tabHost
					.newTabSpec("addClaim")
					.setIndicator("Basic Info")
					.setContent(intentClaim);

			// Approver tab
			Intent intentDestinations = new Intent().setClass(this, AddDestinationsActivity.class);
			intentDestinations.putExtra("username", username);
			intentDestinations.putExtra("claim_id", claim_id);
			intentDestinations.putExtra("option", "Edit");
			TabSpec tabSpecDestinations = tabHost
					.newTabSpec("destinations")
					.setIndicator("Destinations")
					.setContent(intentDestinations);


			tabHost.addTab(tabSpecClaim);
			tabHost.addTab(tabSpecDestinations);
		}
		tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_claim, menu);
		return true;
	}

}
