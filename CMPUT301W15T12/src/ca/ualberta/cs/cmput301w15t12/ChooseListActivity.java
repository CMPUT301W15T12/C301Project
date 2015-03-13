package ca.ualberta.cs.cmput301w15t12;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class ChooseListActivity extends TabActivity
{

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tab_host);
		String username = getIntent().getExtras().getString("username");

		TabHost tabHost = getTabHost();

		// Claim tab
		Intent intentClaim = new Intent().setClass(this, ClaimListActivity.class);
		intentClaim.putExtra("username", username);
		TabSpec tabSpecClaim = tabHost
				.newTabSpec("Claim")
				.setIndicator("Your Claims")
				.setContent(intentClaim);

		// Approver tab
		Intent intentApprover = new Intent().setClass(this, ApproverListActivity.class);
		intentApprover.putExtra("username", username);
		TabSpec tabSpecApprover = tabHost
				.newTabSpec("Approver")
				.setIndicator("Submitted Claims")
				.setContent(intentApprover);

		tabHost.addTab(tabSpecClaim);
		tabHost.addTab(tabSpecApprover);

		tabHost.setCurrentTab(0);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_list, menu);
		return true;
	}

}
