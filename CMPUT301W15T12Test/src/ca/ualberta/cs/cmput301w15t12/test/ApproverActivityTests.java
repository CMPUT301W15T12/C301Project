package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.ApproverClaimActivity;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.ListView;
import android.widget.TextView;

public class ApproverActivityTests extends ActivityInstrumentationTestCase2<ApproverClaimActivity> {
	
	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	
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
	public void testclaimUI() throws ParseException, AlreadyExistsException {
		ApproverClaimActivity activity = startApproverActivity();
		TextView nameView = (TextView) activity.findViewById(R.id.textApproverClaimName);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),nameView);
		assertTrue(nameView.getText().toString().equals("name - In Progress"));
		
		TextView dateView = (TextView) activity.findViewById(R.id.textApproverStarttoEndDate);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),dateView);
		assertEquals(dateView.getText().toString(),"01/01/1232 - 01/01/2134");
		
		TextView descriptionView = (TextView) activity.findViewById(R.id.textApproverClaimDescription);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descriptionView);
		assertTrue(descriptionView.getText().toString().equals("desc"));
		
		TextView totalSumView = (TextView) activity.findViewById(R.id.textViewApproverTotal);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),totalSumView);
		assertEquals(totalSumView.getText().toString(),"12 USD");
		
		Date d1 = df.parse("01/01/1232");
		ExpenseItem item = new ExpenseItem("name", "", "description", "USD", new BigDecimal(12),d1,true);
		
		ListView expenseView = (ListView) activity.findViewById(R.id.listApproverlistExpenseItems);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),expenseView);
		assertEquals(expenseView.getAdapter().getItem(0),item.toStringList());

	}
	
	private ApproverClaimActivity startApproverActivity() throws ParseException, AlreadyExistsException{
		Date d1 = df.parse("01/01/1232");
		Date d2 = df.parse("01/01/2134");
		User user  = new User("Leah", "123");
		UserListController.getUserList().clear();
		UserListController.getUserList().addUser(user);
		ClaimListController clc = new ClaimListController();
		clc.clear();
		int id = clc.addClaim("name", d1, d2,"desc",user);
		ExpenseItem item = new ExpenseItem("name", "", "description", "USD", new BigDecimal(12),d1,true);
		clc.getClaim(id).addItem(item);

		Intent i = new Intent();
		i.putExtra("claim_id", id);
		i.putExtra("item_index", 0);
		setActivityIntent(i);
		return (ApproverClaimActivity) getActivity();
	}
}
