package ca.ualberta.cs.cmput301w15t12;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ApproverListActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claim_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approver_list, menu);
		return true;
	}

}
