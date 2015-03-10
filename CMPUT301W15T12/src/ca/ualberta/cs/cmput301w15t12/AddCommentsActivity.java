package ca.ualberta.cs.cmput301w15t12;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCommentsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comments);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
		
        //gets the claim id
        final int id = getIntent().getIntExtra("claim_id", 0);
        
        //get the correct claim
        final Claim claim = ClaimListController.getClaimList().getClaim(id);
        
        //gets the edit text for the comments and fills in existing comments
        final EditText comments = (EditText) findViewById(R.id.editAddComments);
        comments.setText(claim.getComment());
        
		Button doneBtn = (Button) findViewById(R.id.buttonAddCommentsDone);
		doneBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				claim.setComment(comments.getText().toString());
				Toast.makeText(AddCommentsActivity.this, "Comments Added", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_comments, menu);
        return true;
    }
    
}
