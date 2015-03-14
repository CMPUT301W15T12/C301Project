package ca.ualberta.cs.cmput301w15t12;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
		Comments.setText(claim.getComment());
		Approvers.setText(claim.getApprover().getUserName());
		
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
