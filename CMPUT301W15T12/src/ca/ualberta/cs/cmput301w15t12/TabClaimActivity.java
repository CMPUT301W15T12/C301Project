/**
 * TabClaimActivity puts the adClaimActivity and the destinations tabs together
 * on a single page, passing on all relevant information.
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

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class TabClaimActivity extends TabActivity
{	
	public ArrayList<Destination> DP = new ArrayList<Destination>();
	public TabClaimActivity parent;
	public TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_host);

		String username = getIntent().getExtras().getString("username");
		String option = getIntent().getExtras().getString("option");

		tabHost = getTabHost();
		
		parent = (TabClaimActivity) this.getParent();

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
			ClaimListController CLC = new ClaimListController();
			DP = CLC.getClaim(claim_id).getDestination();

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
	
	public void setDestination(ArrayList<Destination> d){
		DP = d;
	}
	
	public ArrayList<Destination> getDestination(){
		if (DP == null) {
			return new ArrayList<Destination>();
		}
		return DP;
	}
	
	public void chooseTab(int i){
		tabHost.setCurrentTab(i);
	}

}
