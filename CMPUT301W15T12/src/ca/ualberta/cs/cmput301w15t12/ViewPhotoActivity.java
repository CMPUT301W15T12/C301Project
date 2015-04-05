/**
 * ViewPhotoActivity enlarges a photographed receipt for the user to view.
 * 
 * Issues: No photo implementation, doesnt load/show picture
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

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ViewPhotoActivity extends Activity {

	public ExpenseItem Item;
	public ClaimListController CLC = new ClaimListController();
    Uri imageFileUri;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_photo);
		
		final int id = getIntent().getIntExtra("claim_id", 0);
		Claim claim = CLC.getClaim(id);
		final int index = getIntent().getIntExtra("item_index", 0);
		Item = claim.getExpenseItems().get(index);
		
		ImageView iv = (ImageView) findViewById(R.id.receiptImageView);
		
		imageFileUri = Item.getUri();
		Drawable picture = Drawable.createFromPath(imageFileUri.getPath());
		iv.setImageDrawable(picture);
        
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
