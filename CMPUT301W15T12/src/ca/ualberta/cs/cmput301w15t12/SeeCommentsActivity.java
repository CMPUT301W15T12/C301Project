/**
 * SeeComments Activity displays comments for any returned or approved claims.
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

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SeeCommentsActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.see_comments);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());

		//gets the id of the claim whose comments are being viewed
		final int id = getIntent().getIntExtra("claim_id", 0);

		//gets the edit text ids
		TextView Comments = (TextView) findViewById(R.id.textComments);
		TextView Approvers = (TextView) findViewById(R.id.textSeeCommentsApprovers);

		//gets the correct claim
		ClaimListController CLC = new ClaimListController();
		Claim claim = CLC.getClaim(id);

		//sets text fields
		if (claim.getApprover()== null) {
			Toast.makeText(this,"No Comments", Toast.LENGTH_SHORT).show();
		} else if (claim.getComment().equals("")) {
			Toast.makeText(this,"No Comments", Toast.LENGTH_SHORT).show();
			Approvers.setText(claim.getApprover().getUserName());
		} else {
			Comments.setText(claim.getComment());
			Approvers.setText(claim.getApprover().getUserName());
		}
		Button doneBtn = (Button) findViewById(R.id.buttonSeeCommentsDone);
		doneBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.see_comments, menu);
		return true;
	}

}
