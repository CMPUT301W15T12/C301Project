/**
 * ExpenseItemActivity lays out the interface for the expense item 
 * page, displaying all relevant information, allowing edits, and
 * displaying any photographic receipt.
 * issues: does not display a photo yet
 * 
 *   Copyright [2015] CMPUT301W15T12 https://github.com/CMPUT301W15T12
 *   licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *   
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   @author vanbelle
*/

package ca.ualberta.cs.cmput301w15t12;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
	public Uri imageFileUri = null;
	public int id;

	private class LoadingPictureTask extends AsyncTask<URI, Void, File> {
	    @Override
	    protected File doInBackground(URI... uris) {
	    	URI uri = uris[0];	//pick the first one.
	    	ESClient esClient =new ESClient();
	        return esClient.loadImageFileFromServer(uri);
	    }
	    @Override
	    protected void onPostExecute(File file) {
    		Button viewbutton = (Button) findViewById(R.id.buttonImage);

	    	if (file==null){
	    		viewbutton.setBackgroundResource(android.R.drawable.btn_default);
				viewbutton.setText("No Receipt");
	    	}else{
				Drawable picture = Drawable.createFromPath(file.getPath());
				viewbutton.setBackgroundDrawable(picture);
				viewbutton.setText("");
	    	}
	    }  
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_page);

		//gets the id for the claim and the index for the item
		id = getIntent().getIntExtra("claim_id", 0);
		Claim = CLC.getClaim(id);
		index = getIntent().getIntExtra("item_index", 0);
		Item = Claim.getExpenseItems().get(index);
		final Button viewbutton = (Button) findViewById(R.id.buttonImage);
		
		
		new LoadingPictureTask().execute(Item.getUri());

		//clickable button, confirms delete choice
		Button deletebutton = (Button) findViewById(R.id.buttonitemdelete);
		deletebutton.setOnClickListener(new View.OnClickListener() {
			//checks that deleting is what you want
			@Override
			public void onClick(View v) {
				AlertDialog.Builder adb = new AlertDialog.Builder(ExpenseItemActivity.this);
				adb.setMessage("Delete this Item?");
				adb.setCancelable(true);
				adb.setPositiveButton("Delete", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Claim.getExpenseItems().remove(index);
						finish();
					}
				});
				adb.setNegativeButton("Cancel", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				adb.show();
			}
		});

		//clickable button, if photo exists takes user to View photo page
		viewbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				if (!Item.getReceipt()){
					Toast.makeText(ExpenseItemActivity.this, "No Photo", Toast.LENGTH_SHORT).show();
				} 
				else {
					Intent intent = new Intent(ExpenseItemActivity.this, ViewPhotoActivity.class);
					intent.putExtra("claim_id", id);
					intent.putExtra("item_index", index);
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
		CheckBox flag = (CheckBox) findViewById(R.id.checkBoxToFlag);

		String date = Item.getStringDate();
		
		//setting the textviews with existing information
		Name.setText(Item.getName());
		Date.setText(date);
		Category.setText(Item.getCategory());
		Description.setText(Item.getDescription());
		AC.setText(Item.toACString());
		flag.setChecked(Item.getFlag());

		new LoadingPictureTask().execute(Item.getUri());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expense_item, menu);
		return true;
	}

//	public void deleteReceiptPhoto() {
//		// TODO Auto-generated method stub
//
//	}

//	public Bitmap getReceiptPhoto() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	/**
	 * responds to flagged checkbox being clicked by setting the expense item flag
	 * to true or false 
	 * @param view
	 */
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
	/**
	 * responds to editButton being clicked by starting the addItemActivity
	 * @param view
	 */
	public void editButton(View view){
		if (Claim.editable()){
			Intent intent = new Intent(ExpenseItemActivity.this,AddItemActivity.class);
			intent.putExtra("option","edit");
			intent.putExtra("claim_id", id);
			intent.putExtra("item_index",index);
			startActivity(intent);
		} else{
			Toast.makeText(this,"Claim not editable",Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * responds to the delete button being clicked by opening a dialog for confirmation
	 * @param view
	 */
	public void deleteButton(View view){
		if (Claim.editable()){
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
		} else {
			Toast.makeText(ExpenseItemActivity.this, "No edits allowed", Toast.LENGTH_LONG).show();
		}
	}

}
