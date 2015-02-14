package ca.ualberta.cs.cmput301w15t12;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;

public class ClaimActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_claim);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.claim, menu);
		return true;
	}

	public void showSelectedClaim() {
		// TODO Auto-generated method stub
		
	}

	public Claim getFormFragment() {
		// TODO Auto-generated method stub
		return null;
	}

	public Button getSubmitButton() {
		// TODO Auto-generated method stub
		return null;
	}

}
