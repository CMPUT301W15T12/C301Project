package ca.ualberta.cs.cmput301w15t12;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class ExpenseItemActivity extends Activity {

	public ExpenseItem Item;
	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	public Claim Claim;
	public int index;
	public ClaimListController CLC = new ClaimListController();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_page);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());

		//gets the id for the claim and the index for the item
		final int id = getIntent().getIntExtra("claim_id", 0);
		Claim = CLC.getClaim(id);
		index = getIntent().getIntExtra("item_index", 0);
		Item = Claim.getExpenseItems().get(index);


		//clickable button, confirms delete choice
//		Button deletebutton = (Button) findViewById(R.id.buttonitemdelete);
//		deletebutton.setOnClickListener(new View.OnClickListener() {
//			//checks that deleting is what you want
//			@Override
//			public void onClick(View v) {
//				AlertDialog.Builder adb = new AlertDialog.Builder(ExpenseItemActivity.this);
//				adb.setMessage("Delete this Item?");
//				adb.setCancelable(true);
//				adb.setPositiveButton("Delete", new OnClickListener(){
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						//TODO delete item
//					}
//				});
//				adb.setNegativeButton("Cancel", new OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//					}
//				});
//				adb.show();
//			}
//		});

		//clickable button, if photo exists takes user to View photo page
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
	public void onResume() {
		super.onResume();

		//all the textviews
		TextView Name = (TextView) findViewById(R.id.textItemName);
		TextView Date = (TextView) findViewById(R.id.textDate);
		TextView Category = (TextView) findViewById(R.id.textCategory);
		TextView Description = (TextView) findViewById(R.id.textItemDescription);
		TextView AC = (TextView) findViewById(R.id.textItemCurrency);
		CheckBox flag = (CheckBox) findViewById(R.id.checkBox1);

		//date to string
		String date = df.format(Item.getDate());

		//setting the textviews with existing information
		Name.setText(Item.getName());
		Date.setText(date);
		Category.setText(Item.getCategory());
		Description.setText(Item.getDescription());
		AC.setText(Item.toACString());
		flag.setChecked(Item.getFlag());



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
	//checkbox function for checkBox1 (in item_page) when pressed by user
	public void onCheckBoxClicked(View view){
		boolean checked = ((CheckBox) view).isChecked();
		if (checked){
			//expense item has a flag
			Item.setFlag(true);
		} else{
			//expense item doesn't have a flag
			Item.setFlag(false);
		}
		
	}
	
	public void editButton(View view){
		if (Claim.editable()){
			Intent intent = new Intent(ExpenseItemActivity.this,EditItemActivity.class);
			startActivity(intent);
		} else{
			Toast.makeText(this,"Claim not editable",Toast.LENGTH_SHORT).show();
		}
	}
	
	public void deleteButton(View view){
		AlertDialog.Builder adb = new AlertDialog.Builder(ExpenseItemActivity.this);
		adb.setMessage("Delete "+Item.getName().toString()+"?");
		adb.setCancelable(true);
		adb.setPositiveButton("Delete", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
				Claim.removeItem(index);
				finish();
			}
		});
		adb.setNegativeButton("Cancel",new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
			}
		});
		adb.show();
	}

}
