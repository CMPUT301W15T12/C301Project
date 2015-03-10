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
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ApproverClaimActivity extends Activity {

	public Claim Claim;
	public String approver;
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claim_page);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());

		//initialize claim variable
		final int id = getIntent().getIntExtra("claim_id", 0);
		Claim = ClaimListController.getClaimList().getClaim(id);

		//initialize approver variable
		approver = getIntent().getExtras().getString("username");

		//return claim button, asks for confirmation then returns
		Button commentBtn = (Button) findViewById(R.id.buttonAddComment);
		commentBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (Claim.getClaimant().equals(approver)) {
					Toast.makeText(ApproverClaimActivity.this, "Not Allowed to Comment Own Claim", Toast.LENGTH_LONG).show();
				} else {
					Intent intent = new Intent(ApproverClaimActivity.this, AddCommentsActivity.class);
					intent.putExtra("claim_id", id);
					intent.putExtra("username", approver);
					startActivity(intent);
				}
			}

		});

		//approve claim button - asks for confirmation then approves
		Button approveBtn = (Button) findViewById(R.id.buttonApproverApprove);
		approveBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				AlertDialog.Builder adb = new AlertDialog.Builder(ApproverClaimActivity.this);
				adb.setMessage("Approve this Claim?");
				adb.setCancelable(true);
				adb.setPositiveButton("Approve", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try
						{
							Claim.approveClaim(approver);
							finish();
						} catch (CantApproveOwnClaimException e)
						{
							Toast.makeText(ApproverClaimActivity.this,"Not Allowed to Approve Own Claim", Toast.LENGTH_LONG).show();
						}
					}
				});
				adb.setNegativeButton("Cancel", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				adb.show();
			}
		});

		//return claim button, asks for confirmation then returns
		Button returnBtn = (Button) findViewById(R.id.buttonApproverReturn);
		returnBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				AlertDialog.Builder adb = new AlertDialog.Builder(ApproverClaimActivity.this);
				adb.setMessage("Return this Claim?");
				adb.setCancelable(true);
				adb.setPositiveButton("Return", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						try
						{
							Claim.returnClaim(approver);
							finish();
						} catch (CantApproveOwnClaimException e)
						{
							Toast.makeText(ApproverClaimActivity.this,"Not Allowed to Return Own Claim", Toast.LENGTH_LONG).show();
						}
					}
				});
				adb.setNegativeButton("Cancel", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				adb.show();
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
		ListView lv = (ListView) findViewById(R.id.listTotalSum);
		final ArrayList<String> total = Claim.getTotal();
		final ArrayAdapter<String> totalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, total);
		lv.setAdapter(totalAdapter);
		ClaimListController.getClaimList().addListener(new Listener() {
			@Override
			public void update() {
				total.clear();
				ArrayList<String> total = Claim.getTotal();
				totalAdapter.notifyDataSetChanged();
			}
		});
		
		ListView list = (ListView) findViewById(R.id.listApproverlistExpenseItems);
		final ArrayList<ExpenseItem> EItems = Claim.getExpenseItems();
		ArrayList<String> items = new ArrayList<String>();
		for (int i = 0; i < EItems.size(); i++) {
			items.add(EItems.get(i).toStringList());
		}
		final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
		list.setAdapter(listAdapter);
		ClaimListController.getClaimList().addListener(new Listener()
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
