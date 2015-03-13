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

		TabHost tabHost = getTabHost();

		// Claim tab
		Intent intentClaim = new Intent().setClass(this, AddClaimActivity.class);
		intentClaim.putExtra("username", username);
		TabSpec tabSpecClaim = tabHost
				.newTabSpec("addClaim")
				.setIndicator("Basic Info")
				.setContent(intentClaim);

		// Approver tab
		Intent intentApprover = new Intent().setClass(this, AddDestinationsActivity.class);
		intentApprover.putExtra("username", username);
		TabSpec tabSpecApprover = tabHost
				.newTabSpec("destinations")
				.setIndicator("Destinations")
				.setContent(intentApprover);

		tabHost.addTab(tabSpecClaim);
		tabHost.addTab(tabSpecApprover);

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
