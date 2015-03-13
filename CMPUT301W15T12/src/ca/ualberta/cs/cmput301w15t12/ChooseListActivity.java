package ca.ualberta.cs.cmput301w15t12;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

public class ChooseListActivity extends FragmentActivity
{
	private FragmentTabHost mTabHost;
	
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_host);
		
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_list, menu);
		return true;
	}

}
