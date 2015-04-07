package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.widget.TextView;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimActivity;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.MissingItemException;
import ca.ualberta.cs.cmput301w15t12.NotAllowedException;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;

public class ClaimActivityTests extends
ActivityInstrumentationTestCase2<ClaimActivity> {
	
	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

	public ClaimActivityTests() {
		super(ClaimActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	// US07.01.01 - Submission of claim, allowing no edits thereafter
	public void testSubmitStatus() throws AlreadyExistsException {
		// Make new claim and add to claims list
		ClaimListController claimList = new ClaimListController();
		Date date1 = new Date();
		Date date2 = new Date();

		int id = claimList.addClaim("name1",  date1, date2, "description1", new User("Megan", "123"));

		// Set status to submitted
		claimList.getClaim(id).setStatus("Submitted");
		assertTrue("Status was not changed to submitted", claimList.getClaim(id).getStatus() == "Submitted");

		// Select the claim via controller
		claimList.getClaim(id);
		assertFalse("Not supposed to be able to edit submitted status", claimList.getClaim(id).editable());

	}


	// US07.03.01 - Returning a claim, allowing edits thereafter
	public void testReturnedStatus() throws AlreadyExistsException, NotAllowedException, MissingItemException {
		// Make new claim and add to claims list
		Date date1 = new Date();
		Date date2 = new Date();
		ClaimListController claimList = new ClaimListController();
		claimList.clear();
		UserListController.getUserList().clear();
		User c = new User("Megan", "123");
		User a = new User("Sarah", "123");
		UserListController.getUserList().addUser(a);
		UserListController.getUserList().addUser(c);
		int id = claimList.addClaim("name1",  date1, date2, "description1", c);
		Claim claim = claimList.getClaim(id);

		// Make sure claim is in submitted status
		claim.setStatus("Submitted");
		claim.addComment("comment");
		assertTrue("Status was not changed to submitted", claim.getStatus() == "Submitted");

		// Set status to returned
		claimList.getClaim(id).returnClaim("Sarah");
		assertTrue("Status was not changed to returned", claimList.getClaim(id).getStatus().equals("Returned"));

		// editing allowed
		assertTrue(claimList.getClaim(id).editable());
	}

	// US07.04.01 - Approving a claim, allowing no edits thereafter
	public void testApprovedStatus() throws AlreadyExistsException, MissingItemException, NotAllowedException {
		// Make new claim and add to claims list
		Date date1 = new Date();
		Date date2 = new Date();
		ClaimListController claimList = new ClaimListController();
		claimList.clear();
		UserListController.getUserList().clear();
		User c = new User("Megan", "123");
		User a = new User("Sarah", "123");
		UserListController.getUserList().addUser(a);
		UserListController.getUserList().addUser(c);
		int id = claimList.addClaim("name1",  date1, date2, "description1", c);
		Claim claim = claimList.getClaim(id);

		// Make sure claim is in submitted status
		claim.setStatus("Submitted");
		claim.addComment("comment");
		assertTrue("Status was not changed to submitted", claim.getStatus() == "Submitted");

		// Set status to approved
		claim.approveClaim("Sarah");
		assertTrue("Status was not changed to approved", claim.getStatus() == "Approved");

		assertFalse(claim.editable());
	}
	
	//US01.03.01 - As a claimant, I want to view an expense claim and its details.
	@UiThreadTest
	public void testViewItem() throws ParseException, AlreadyExistsException{
		ClaimActivity activity = getExpenseItemActivity();
		
		TextView nameView = (TextView) activity.findViewById(R.id.textClaimName);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),nameView);
		assertTrue(nameView.getText().toString().equals("name - In Progress"));
		
		TextView dateView = (TextView) activity.findViewById(R.id.textStarttoEndDate);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),dateView);
		assertEquals(dateView.getText().toString(),"01/01/1232 - 01/01/2134");
		
		TextView descriptionView = (TextView) activity.findViewById(R.id.textClaimDescription);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descriptionView);
		assertEquals(descriptionView.getText().toString(),"desc");
		
		TextView descView = (TextView) activity.findViewById(R.id.textClaimDestinations);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descView);
		assertEquals(descView.getText().toString(),"");
		
		TextView currencyView = (TextView) activity.findViewById(R.id.listTotalSum);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),currencyView);
		assertEquals(currencyView.getText().toString(),"12 USD");
	}
	
	private ClaimActivity getExpenseItemActivity() throws ParseException, AlreadyExistsException{
		Date d1 = df.parse("01/01/1232");
		Date d2 = df.parse("01/01/2134");

		User user  = new User("Leah", "123");
		UserListController.getUserList().clear();

		UserListController.getUserList().addUser(user);
		ClaimListController clc = new ClaimListController();
		clc.clear();

		int id = clc.addClaim("name", d1, d2,"desc",user);
		ExpenseItem item = new ExpenseItem("name", "", "description", "USD", new BigDecimal(12),d1,true);

		Claim claim = clc.getClaim(id);
		claim.addItem(item);

		Intent i = new Intent();
		i.putExtra("claim_id", id);
		i.putExtra("username","Leah");
		setActivityIntent(i);

		return (ClaimActivity) getActivity();
	}

}

