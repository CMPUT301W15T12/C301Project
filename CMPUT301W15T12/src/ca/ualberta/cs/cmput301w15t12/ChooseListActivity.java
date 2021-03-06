/**
 * This  Activity initializes the tab view by linking the ApproverListActivity 
 * and the ClaimListActivity as tabs.
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
 *   @author olexson
*/

package ca.ualberta.cs.cmput301w15t12;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class ChooseListActivity extends TabActivity
{
	public ProgressDialog progress;

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
				.setIndicator("All Submitted Claims")
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
	
	/** menu item calls the class which saves all changes to the server
	 * @param menu
	 */
	//menu item save claim	
	public void saveClaim(MenuItem menu) {
		progress = new ProgressDialog(this);
		progress.setTitle("Connecting");
		progress.setCanceledOnTouchOutside(false);
		progress.setMessage("Wait while the server connects and saves your information");
		new LoadingOnlineRecordTask().execute();
	}
	
	private class LoadingOnlineRecordTask extends AsyncTask<Void, Void, Void> {
	    @Override
	    protected Void doInBackground(Void... optionalInputs) {

	        new ESClient().saveRecordToServer();

	        return null;
	    }
	    @Override
	    protected void onPostExecute(Void result) {
	        progress.dismiss();
	        //ClaimListController claimListController = new ClaimListController();
	        Toast.makeText(ChooseListActivity.this,"Changes Saved", Toast.LENGTH_SHORT).show();       

	    }  
	}

}
