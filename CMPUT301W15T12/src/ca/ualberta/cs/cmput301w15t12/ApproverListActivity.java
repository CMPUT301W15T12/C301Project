package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ApproverListActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claim_list);
	}
	
	public void onResume() {
		super.onResume();
		
		ListView lv = (ListView) findViewById(R.id.listApproverClaimList);
		ArrayList<Claim> claims = ClaimListController.getClaimList().getSubmittedClaims();
		ArrayList<String> sclaims = new ArrayList<String>();
		
		for (int i = 0; i < claims.size(); i++){
			sclaims.add(claims.get(i).toStringApproverList());
		}
		
		final ArrayAdapter<String> SubClaimAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sclaims);
		lv.setAdapter(SubClaimAdapter);
		
		ClaimListController.getClaimList().addListener(new Listener() {
			@Override
			public void update() {
				ArrayList<Claim> claims = ClaimListController.getClaimList().getSubmittedClaims();
				ArrayList<String> sclaims = new ArrayList<String>();
				
				for (int i = 0; i < claims.size(); i++){
					sclaims.add(claims.get(i).toStringApproverList());
				}

				SubClaimAdapter.notifyDataSetChanged();
			}
		});
		
		lv.setOnItemClickListener(new OnItemClickListener()
		{ 
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				Intent intent = new Intent(ApproverListActivity.this, ApproverClaimActivity.class);
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
