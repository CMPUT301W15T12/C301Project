/**
 * This  Activity allows a reviewer to add comments to a claim
 * that they are reviewing 
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

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCommentsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comments);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
		
        //gets the claim id
        final int id = getIntent().getIntExtra("claim_id", 0);
        
        //get the correct claim
        ClaimListController CLC = new ClaimListController();
        final Claim claim = CLC.getClaim(id);
        
        //gets the edit text for the comments and fills in existing comments
        final EditText comments = (EditText) findViewById(R.id.editAddComments);
        comments.setText(claim.getComment());
        
		Button doneBtn = (Button) findViewById(R.id.buttonAddCommentsDone);
		doneBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				claim.setComment(comments.getText().toString());
				Toast.makeText(AddCommentsActivity.this, "Comments Added", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_comments, menu);
        return true;
    }
    
}
