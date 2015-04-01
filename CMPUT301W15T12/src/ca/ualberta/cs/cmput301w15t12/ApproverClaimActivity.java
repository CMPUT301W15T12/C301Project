/**
 * This  Activity displays a submitted claim which a reviewer can inspect, 
 * add comments to, return, or approve. 
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ApproverClaimActivity extends Activity {

	public ClaimListController CLC = new ClaimListController();
	public Claim Claim;
	public String approver;
	public int id;
	public ArrayList<String> total;
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claim_page);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());

		//initialize claim variable
		id = getIntent().getIntExtra("claim_id", 0);
		Claim = CLC.getClaim(id);

		//initialize approver variable
		approver = getIntent().getExtras().getString("username");

		//return claim button, asks for confirmation then returns
		Button commentBtn = (Button) findViewById(R.id.buttonAddComment);
		commentBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (Claim.getClaimant().getUserName().equals(approver)) {
					Toast.makeText(ApproverClaimActivity.this, "Not Allowed to Comment Own Claim", Toast.LENGTH_LONG).show();
				} else {
					LayoutInflater LI = LayoutInflater.from(ApproverClaimActivity.this);
					final View promptView = LI.inflate(R.layout.input_dialog, null);
					EditText enter = (EditText) promptView.findViewById(R.id.editTextTagName);
					TextView title = (TextView) promptView.findViewById(R.id.textEditTag);
					title.setText("Enter Comment");
					enter.setHint("Comment");
					AlertDialog.Builder adb = new AlertDialog.Builder(ApproverClaimActivity.this);
					adb.setView(promptView);

					adb.setCancelable(false)
					.setPositiveButton("Add", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//add comment to the comment list
							EditText enter = (EditText) promptView.findViewById(R.id.editTextTagName);
							String comment = enter.getText().toString();
							if (comment.equals("")) {
								Toast.makeText(ApproverClaimActivity.this,"Cannot add an empty comment", Toast.LENGTH_SHORT).show();
							} else {
								Claim.addComment(comment);
							}
						}
					})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
					AlertDialog alert =adb.create();
					alert.show();
				}
			}

		});

		//approve claim button - asks for confirmation then approves
		Button approveBtn = (Button) findViewById(R.id.buttonApproverApprove);
		approveBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (approver.equals(Claim.getClaimant().getUserName())){
					Toast.makeText(ApproverClaimActivity.this,"Not Allowed to Approve Own Claim", Toast.LENGTH_LONG).show();
				} else if (Claim.getComment().size() == 0){
					Toast.makeText(ApproverClaimActivity.this,"Stil Needs a Comment", Toast.LENGTH_LONG).show();
				} else {
					AlertDialog.Builder adb = new AlertDialog.Builder(ApproverClaimActivity.this);
					adb.setMessage("Approve this Claim?");
					adb.setCancelable(true);
					adb.setPositiveButton("Approve", new OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try{
								Claim.approveClaim(approver);
							} catch (NotAllowedException e1){
								Toast.makeText(ApproverClaimActivity.this,"Not Allowed to Approve Own Claim", Toast.LENGTH_LONG).show();
							} catch(MissingItemException e2){
								Toast.makeText(ApproverClaimActivity.this,"Stil Needs a Comment", Toast.LENGTH_LONG).show();
							}
						}
					});
					adb.setNegativeButton("Cancel", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					adb.show();
				}
			}
		});

		//return claim button, asks for confirmation then returns
		Button returnBtn = (Button) findViewById(R.id.buttonApproverReturn);
		returnBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (approver.equals(Claim.getClaimant().getUserName())){
					Toast.makeText(ApproverClaimActivity.this,"Not Allowed to Return Own Claim", Toast.LENGTH_LONG).show();
				} else if (Claim.getComment().size() == 0){
					Toast.makeText(ApproverClaimActivity.this,"Stil Needs a Comment", Toast.LENGTH_LONG).show();
				} else{
					AlertDialog.Builder adb = new AlertDialog.Builder(ApproverClaimActivity.this);
					adb.setMessage("Return this Claim?");
					adb.setCancelable(true);
					adb.setPositiveButton("Return", new OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								Claim.returnClaim(approver);
								finish();
							} catch (NotAllowedException e1){
								Toast.makeText(ApproverClaimActivity.this,"Not Allowed to Approve Own Claim", Toast.LENGTH_LONG).show();
							} catch(MissingItemException e2){
								Toast.makeText(ApproverClaimActivity.this,"Stil Needs a Comment", Toast.LENGTH_LONG).show();
							}
						}
					});
					adb.setNegativeButton("Cancel", new OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					adb.show();
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();

		//gets the textviews
		TextView name = (TextView) findViewById(R.id.textApproverClaimName);
		TextView description = (TextView) findViewById(R.id.textApproverClaimDescription);
		TextView dates = (TextView) findViewById(R.id.textApproverStarttoEndDate);
		TextView destinations = (TextView) findViewById(R.id.textApproverClaimDestinations);


		//sets the textviews
		name.setText(Claim.getName()+" - "+Claim.getStatus());
		description.setText(Claim.getDescription());
		String sd = df.format(Claim.getStartDate());
		String ed = df.format(Claim.getEndDate());
		dates.setText(sd+" - "+ed);
		destinations.setText(Claim.destinationsToString());

		//total list
		TextView tv = (TextView) findViewById(R.id.textViewApproverTotal);
		String total = Claim.getTotalString();
		tv.setText(total);

		ListView list = (ListView) findViewById(R.id.listApproverlistExpenseItems);
		final ArrayList<ExpenseItem> EItems = Claim.getExpenseItems();
		ArrayList<String> items = new ArrayList<String>();
		for (int i = 0; i < EItems.size(); i++) {
			items.add(EItems.get(i).toStringList());
		}
		final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
		list.setAdapter(listAdapter);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3)
			{
				Intent intent = new Intent(ApproverClaimActivity.this, ApproverItemActivity.class);
				intent.putExtra("claim_id", id);
				intent.putExtra("item_index", position);
				startActivity(intent);
			}
		});

		CLC.addListener(new Listener()
		{

			@Override
			public void update()
			{
				final ArrayList<ExpenseItem> EItems = Claim.getExpenseItems();
				ArrayList<String> items = new ArrayList<String>();
				for (int i = 0; i < EItems.size(); i++) {
					items.add(EItems.get(i).toStringList());
				}				
				listAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approver, menu);
		return true;
	}

}
