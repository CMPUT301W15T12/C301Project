package ca.ualberta.cs.cmput301w15t12;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;;

public class ViewPhotoActivity extends Activity {

	public ExpenseItem Item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_photo);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
		
		final int id = getIntent().getIntExtra("claim_id", 0);
		Claim Claim = ClaimListController.getClaimList().getClaim(id);
		final int index = getIntent().getIntExtra("item_index", 0);
		Item = Claim.getExpenseItems().get(index);
		
		//TODO get photo and place it in the image box
		
		Button doneBtn = (Button) findViewById(R.id.buttonViewPictureDone);
		doneBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_photo, menu);
		return true;
	}

}
