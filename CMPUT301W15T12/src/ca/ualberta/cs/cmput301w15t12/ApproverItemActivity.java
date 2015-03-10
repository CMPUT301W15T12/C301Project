package ca.ualberta.cs.cmput301w15t12;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ApproverItemActivity extends Activity {

	public ExpenseItem Item;
	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_expense_item);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());

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
					Toast.makeText(ApproverItemActivity.this, "No Photo", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(ApproverItemActivity.this,ViewPhotoActivity.class);
					intent.putExtra("claim_id", id);
					intent.putExtra("intem_index", index);
					startActivity(intent);
				}
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		//all the textviews
		TextView Name = (TextView) findViewById(R.id.textApproverItemName);
		TextView Date = (TextView) findViewById(R.id.textApproverDate);
		TextView Category = (TextView) findViewById(R.id.textApproverCategory);
		TextView Description = (TextView) findViewById(R.id.textApproverItemDescription);
		TextView AC = (TextView) findViewById(R.id.textApproverItemCurrency);
		
		//date to string
		String date = df.format(Item.getDate());
		
		//setting the textviews with existing information
		Name.setText(Item.getName());
		Date.setText(date);
		Category.setText(Item.getCategory());
		Description.setText(Item.getDescription());
		AC.setText(Item.toACString());
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approver_item, menu);
		return true;
	}

}
