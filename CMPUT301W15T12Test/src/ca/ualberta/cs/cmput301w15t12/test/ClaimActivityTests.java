package ca.ualberta.cs.cmput301w15t12.test;

import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimActivity;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.MissingItemException;
import ca.ualberta.cs.cmput301w15t12.NotAllowedException;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;

public class ClaimActivityTests extends
ActivityInstrumentationTestCase2<ClaimActivity> {


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

	//US07.02.01 - Visual warning of missing values
	public void testIncompleteClaimPrompt() {
		// Start ClaimActivity, should be blank since its a new claim
		//ClaimActivity activity = getActivity();

		// May not end up being a button, but click the submit button
		//Button submitButton = (Button) activity.getSubmitButton();
		//submitButton.performClick();

		// FormFragment should have mechanism to clear invalid inputs and reset their hint texts with red color
		// on click of submitButton 

		// Check if that's the case
		// TODO: Check all of the inputs in FormFragment have red hint text
		//FormFragment formFragment = activity.getFormFragment();
		//EditText description = (EditText) formFragment.editDescription();

		//assertTrue("Missing value prompt was not initiated", description.getHintTextColors() == formFragment.colorStateList);
		assertTrue(false);
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

}

