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
		  .setIndicator("Submitted Claims")
		  .setContent(intentClaim);
		
		// Approver tab
		Intent intentApprover = new Intent().setClass(this, ApproverListActivity.class);
		intentApprover.putExtra("username", username);
		TabSpec tabSpecApprover = tabHost
		  .newTabSpec("Approver")
		  .setIndicator("Your Claims")
		  .setContent(intentApprover);
		
		tabHost.addTab(tabSpecClaim);
		tabHost.addTab(tabSpecApprover);
		
		tabHost.setCurrentTab(0);
	}

	//		final ActionBar actionbar = getActionBar();
	//		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	//		
	//		ActionBar.TabListener tablistener = new ActionBar.TabListener() {
	//			
	//			@Override
	//			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	//				// TODO Auto-generated method stub
	//				
	//			}
	//			
	//			@Override
	//			public void onTabSelected(Tab tab, FragmentTransaction ft){			
	//				// TODO Auto-generated method stub
	//				
	//			}
	//			
	//			@Override
	//			public void onTabReselected(Tab tab, FragmentTransaction ft)	{
	//			}
	//		};
	//		
	//		claim = actionbar.newTab().setText("Your CLaims").setTabListener(tablistener);
	//		approver = actionbar.newTab().setText("Submitted Claims").setTabListener(tablistener);
	//		
	//		actionbar.addTab(claim);
	//		actionbar.addTab(approver);

	//		UserListManager.initManager(this.getApplicationContext());
	//		ClaimListManager.initManager(this.getApplicationContext());
	//		
	//		final String username = getIntent().getExtras().getString("username");
	//		
	//		Button claimBtn = (Button) findViewById(R.id.buttonChooseClaimant);
	//		claimBtn.setOnClickListener(new View.OnClickListener() {
	//			public void onClick(View view) {
	//				Intent intent = new Intent(ChooseListActivity.this, ClaimListActivity.class);
	//				intent.putExtra("username", username);
	//				startActivity(intent);
	//			}
	//		});
	//		
	//		Button approverBtn = (Button) findViewById(R.id.buttonChooseApprover);
	//		approverBtn.setOnClickListener(new View.OnClickListener() {
	//			public void onClick(View view) {
	//				Intent intent = new Intent(ChooseListActivity.this, ApproverListActivity.class);
	//				intent.putExtra("username", username);
	//				startActivity(intent);
	//			}
	//		});
	//}

@Override
public boolean onCreateOptionsMenu(Menu menu)
{

	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.choose_list, menu);
	return true;
}

}
