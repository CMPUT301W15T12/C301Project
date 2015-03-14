/**
 * This  Activity displays a list of all claims that have been submitted
 * EXCEPT for those which have been returned by other reviewers, and have 
 * now been resubmitted.
 * 
 *   Copyright [2015] CMPUT301W15T12 https://github.com/CMPUT301W15T12
 *   licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *   
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   @author vanbelle
*/

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
