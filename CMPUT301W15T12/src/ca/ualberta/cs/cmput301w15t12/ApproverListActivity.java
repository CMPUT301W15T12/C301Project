package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ApproverListActivity extends Activity
{
	public ClaimListController CLC = new ClaimListController();
	public String approver;
	public Context c;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claim_list);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
		
		approver = getIntent().getExtras().getString("username");
	}
	
	public void onResume() {
		super.onResume();
		
		//lists all the submitted claims for the approver to approve
		ListView lv = (ListView) findViewById(R.id.listApproverClaimList);
		final ArrayList<Claim> claims = CLC.filterByStatus("Submitted");
		ArrayList<String> sclaims = new ArrayList<String>();
		
		for (int i = 0; i < claims.size(); i++){
			sclaims.add(claims.get(i).toStringApproverList());
		}
		
		if (claims.size() == 0) {
			Toast.makeText(this, "No Submitted Claims", Toast.LENGTH_SHORT).show();
		}
		
		final ArrayAdapter<String> SubClaimAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sclaims);
		lv.setAdapter(SubClaimAdapter);
		
		//adds a listener to the list
		CLC.addListener(new Listener() {
			@Override
			public void update() {
				ArrayList<Claim> claims = CLC.filterByStatus("Submitted");
				ArrayList<String> sclaims = new ArrayList<String>();
				
				for (int i = 0; i < claims.size(); i++){
					sclaims.add(claims.get(i).toStringApproverList());
				}

				SubClaimAdapter.notifyDataSetChanged();
			}
		});
		
		//takes user to claim page when clicked
		lv.setOnItemClickListener(new OnItemClickListener()
		{ 
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				int id = claims.get(arg2).getId();
				Intent intent = new Intent(ApproverListActivity.this, ApproverClaimActivity.class);
				intent.putExtra("claim_id",id);
				intent.putExtra("username", approver);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approver_list, menu);
		return true;
	}

}
