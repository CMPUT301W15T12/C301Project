package ca.ualberta.cs.cmput301w15t12;

import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.R.layout;
import ca.ualberta.cs.cmput301w15t12.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddClaimActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_claim);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_claim, menu);
		return true;
	}

}
