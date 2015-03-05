package ca.ualberta.cs.cmput301w15t12;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.w3c.dom.Text;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ApproverClaimActivity extends Activity {

	public Claim Claim;
	public String approver;
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approver_claim_page);
		//initialize claim variable
		final int id = getIntent().getIntExtra("claim_id", 0);
		Claim = ClaimListController.getClaimList().getClaim(id);

		//initialize approver variable
		approver = getIntent().getExtras().getString("username");
		
		//return claim button, asks for confirmation then returns
		Button commentBtn = (Button) findViewById(R.id.buttonAddComment);
		commentBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(ApproverClaimActivity.this, AddCommentsActivity.class);
				intent.putExtra("claim_id", id);
				intent.putExtra("username", approver);
				startActivity(intent);
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
						Claim.approveClaim(approver);
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
						Claim.returnClaim(approver);
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
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.approver, menu);
		return true;
	}

}
