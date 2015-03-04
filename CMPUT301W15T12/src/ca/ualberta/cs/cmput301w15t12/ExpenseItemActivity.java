package ca.ualberta.cs.cmput301w15t12;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ExpenseItemActivity extends Activity {
	
	public ExpenseItem Item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_page);
		
		final int id = getIntent().getIntExtra("claim_id", 0);
		Claim Claim = ClaimListController.getClaimList().getClaim(id);
		final int index = getIntent().getIntExtra("item_index", 0);
		Item = Claim.getExpenseItems().get(index);

		Button viewbutton = (Button) findViewById(R.id.buttonApproverImage);
		viewbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				if (!Item.getReceipt()){
					Toast.makeText(ExpenseItemActivity.this, "No Photo", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(ExpenseItemActivity.this,ViewPhotoActivity.class);
					intent.putExtra("claim_id", id);
					intent.putExtra("intem_index", index);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expense_item, menu);
		return true;
	}

	public void deleteReceiptPhoto() {
		// TODO Auto-generated method stub
		
	}

	public Bitmap getReceiptPhoto() {
		// TODO Auto-generated method stub
		return null;
	}

}
