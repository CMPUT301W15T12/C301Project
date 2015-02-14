package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.ApproverItemActivity;
import ca.ualberta.cs.cmput301w15t12.R;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.TextView;

public class ApproverItemActivityTests extends ActivityInstrumentationTestCase2<ApproverItemActivity> {
	public ApproverItemActivityTests(){
		super(ApproverItemActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	//	US08.03.01
	//As an approver, I want to view all the details of a submitted expense claim.
	public void itemUITest() {
		ApproverItemActivity activity = startApproverItemActivity();
		
		TextView nameView = (TextView) activity.findViewById(R.id.textApproverItemName);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),nameView);
		TextView categoryView = (TextView) activity.findViewById(R.id.textApproverCategory);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),categoryView);
		TextView descriptionView = (TextView) activity.findViewById(R.id.textApproverItemDescription);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descriptionView);
		TextView currencyView = (TextView) activity.findViewById(R.id.textApproverItemCurrency);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),currencyView);
		TextView dateView = (TextView) activity.findViewById(R.id.textApproverDate);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),dateView);
	}
	
	private ApproverItemActivity startApproverItemActivity(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		return (ApproverItemActivity) getActivity();
	}
	
}


