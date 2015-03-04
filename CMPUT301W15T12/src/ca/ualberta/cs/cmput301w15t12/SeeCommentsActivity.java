package ca.ualberta.cs.cmput301w15t12;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SeeCommentsActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.see_comments);
		//gets the id of the claim whose comments are being viewed
		final int id = getIntent().getIntExtra("claim_id", 0);
		
		//gets the edit text ids
		EditText Comments = (EditText) findViewById(R.id.textComments);
		EditText Approvers = (EditText) findViewById(R.id.textSeeCommentsApprovers);
		
		//gets the correct claim
		Claim claim = ClaimListController.getClaimList().getClaim(id);
		
		//sets text fields
		Comments.setText(claim.getComment());
		Approvers.setText(claim.toStringList(claim.getApprovers()));
		
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