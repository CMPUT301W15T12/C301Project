package ca.ualberta.cs.cmput301w15t12;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ChooseListActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose_list);
		final String username = getIntent().getExtras().getString("username");
		
		Button claimBtn = (Button) findViewById(R.id.buttonChooseClaimant);
		claimBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(ChooseListActivity.this, ClaimListActivity.class);
				intent.putExtra("username", username);
				startActivity(intent);
			}
		});
		
		Button approverBtn = (Button) findViewById(R.id.buttonChooseApprover);
		approverBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(ChooseListActivity.this, ApproverListActivity.class);
				intent.putExtra("username", username);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_list, menu);
		return true;
	}

}
