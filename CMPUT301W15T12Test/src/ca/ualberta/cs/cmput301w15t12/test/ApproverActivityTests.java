package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.ApproverClaimActivity;
import ca.ualberta.cs.cmput301w15t12.R;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.ListView;
import android.widget.TextView;

public class ApproverActivityTests extends ActivityInstrumentationTestCase2<ApproverClaimActivity> {
	
	public ApproverActivityTests(){
		super(ApproverClaimActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
//	US08.01.01
//	As an approver, I want to view a list of all the expense claims that were submitted for approval, 
//	which have their claim status as submitted, showing for each claim: the claimant name, 
	//  the starting date of travel, the destination(s) of travel, the claim status, total currency amounts, and any approver name.
	public void testclaimUI() {
		ApproverClaimActivity activity = startApproverActivity();
		TextView nameView = (TextView) activity.findViewById(R.id.textApproverClaimName);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),nameView);
		TextView dateView = (TextView) activity.findViewById(R.id.textApproverStarttoEndDate);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),dateView);
		TextView descriptionView = (TextView) activity.findViewById(R.id.textApproverClaimDescription);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descriptionView);
		ListView totalSumView = (ListView) activity.findViewById(R.id.ApproverlistTotalSum);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),totalSumView);
		ListView expenseView = (ListView) activity.findViewById(R.id.listApproverlistExpenseItems);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),expenseView);

	}


	//	US08.03.01
	//As an approver, I want to view all the details of a submitted expense claim.
	public void testitemUI() {

	}
	
	private ApproverClaimActivity startApproverActivity(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		return (ApproverClaimActivity) getActivity();
	}
}
