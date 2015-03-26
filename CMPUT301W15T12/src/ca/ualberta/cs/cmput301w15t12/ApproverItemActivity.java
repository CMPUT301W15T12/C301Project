/**
 * This  Activity displays the summary info for a given item. 
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

import java.text.SimpleDateFormat;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.R;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ApproverItemActivity extends Activity {

	public ClaimListController CLC = new ClaimListController();
	public ExpenseItem Item;
	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	Uri imageFileUri;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_expense_item);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());

		final int id = getIntent().getIntExtra("claim_id", 0);
		Claim Claim = CLC.getClaim(id);
		final int index = getIntent().getIntExtra("item_index", 0);
		Item = Claim.getExpenseItems().get(index);

		//get the image and place it in the button
		Button viewbutton = (Button) findViewById(R.id.buttonApproverImage);
		if (!Item.getReceipt()){
			viewbutton.setText("No Receipt");
		}
		else{
			imageFileUri = Item.getUri();
			Drawable picture = Drawable.createFromPath(imageFileUri.getPath());
			viewbutton.setBackgroundDrawable(picture);
			viewbutton.setText("");
		}
		
		//If there is an image, clicking viewbutton enlarges it
		viewbutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				if (!Item.getReceipt()){
					Toast.makeText(ApproverItemActivity.this, "No Photo", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(ApproverItemActivity.this,ViewPhotoActivity.class);
					intent.putExtra("claim_id", id);
					intent.putExtra("item_index", index);
					startActivity(intent);
				}
			}
		});
	}
	
	@SuppressWarnings("deprecation")
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
		
		//get the image and place it in the button
		Button viewbutton = (Button) findViewById(R.id.buttonApproverImage);
		if (!Item.getReceipt()){
			viewbutton.setText("No Receipt");
		}
		else{
			imageFileUri = Item.getUri();
			Drawable picture = Drawable.createFromPath(imageFileUri.getPath());
			viewbutton.setBackgroundDrawable(picture);
			viewbutton.setText("");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approver_item, menu);
		return true;
	}

}
