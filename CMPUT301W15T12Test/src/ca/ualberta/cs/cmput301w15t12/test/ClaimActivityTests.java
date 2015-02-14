package ca.ualberta.cs.cmput301w15t12.test;

import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimActivity;
import ca.ualberta.cs.cmput301w15t12.ClaimList;
import ca.ualberta.cs.cmput301w15t12.User;

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
		ClaimList claimList = new ClaimList();
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim = new Claim("name1",  date1, date2, "description1", "Approved", "Megan");
		claimList.addClaim(claim);
		
		// Note: setStatus automatically handles valid changes to status
		// If a call is not valid, nothing happens, and the status remains the same
		
		// Set status to submitted
		claim.setStatus("Submitted");
		assertTrue("Status was not changed to submitted", claim.getStatus() == "Submitted");
		
		// Select the claim via controller
		claimList.setSelected(claim);
		
		// Start ClaimActivity and make it output data from claim
		ClaimActivity activity = getActivity();
		activity.showSelectedClaim();
		
		// Make sure status was changed to submitted
		String claimStatus = activity.getFormFragment().getStatus();
		assertEquals("Claim doesn't show as submitted, even though it is", "Submitted", claimStatus);
		
		// Try editing the claim, should fail since claim is in submitted status
		try {
			claim.setDescription("Hello world!");
			assertTrue("Not supposed to be able to edit submitted status", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//US07.02.01 - Visual warning of missing values
	public void testIncompleteClaimPrompt() {
		// Start ClaimActivity, should be blank since its a new claim
		ClaimActivity activity = getActivity();
		
		// May not end up being a button, but click the submit button
		Button submitButton = (Button) activity.getSubmitButton();
		submitButton.performClick();
		
		// FormFragment should have mechanism to clear invalid inputs and reset their hint texts with red color
		// on click of submitButton 
		
		// Check if that's the case
		// TODO: Check all of the inputs in FormFragment have red hint text
		//FormFragment formFragment = activity.getFormFragment();
		//EditText description = (EditText) formFragment.editDescription();
		
		//assertTrue("Missing value prompt was not initiated", description.getHintTextColors() == formFragment.colorStateList);
	}
	
	// US07.03.01 - Returning a claim, allowing edits thereafter
	public void testReturnedStatus() throws AlreadyExistsException {
		// Make new claim and add to claims list
		Date date1 = new Date();
		Date date2 = new Date();
		ClaimList claimList = new ClaimList();
		Claim claim = new Claim("name1",  date1, date2, "description1", "Approved", "Megan");
		claimList.addClaim(claim);
		
		// Make sure claim is in submitted status
		claim.setStatus("Submitted");
		assertTrue("Status was not changed to submitted", claim.getStatus() == "Submitted");
		
		// Set status to returned
		claim.setStatus("Returned");
		assertTrue("Status was not changed to returned", claim.getStatus() == "Returned");
		
		// Select the claim via controller
		claimList.setSelected(claim);
		
		// Start ClaimActivity and make it output data from claim
		ClaimActivity activity = getActivity();
		activity.showSelectedClaim();
		
		// Make sure status was changed to returned
		String claimStatus = activity.getFormFragment().getStatus();
		assertEquals("Claim doesn't show as submitted, even though it is", "Returned", claimStatus);
		
		// Try editing the claim, should work since claim is in returned status
		try {
			claim.setDescription("Hello world!");
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue("Supposed to be able to edit returned status", false);
		}
	}
	
	// US07.04.01 - Approving a claim, allowing no edits thereafter
	public void testApprovedStatus() throws AlreadyExistsException {
		// Make new claim and add to claims list
		Date date1 = new Date();
		Date date2 = new Date();
		ClaimList claimList = new ClaimList();
		Claim claim = new Claim("name1",  date1, date2, "description1", "Approved", "Megan");
		claimList.addClaim(claim);
		
		// Make sure claim is in submitted status
		claim.setStatus("Submitted");
		assertTrue("Status was not changed to submitted", claim.getStatus() == "Submitted");
		
		// Set status to approved
		claim.setStatus("Approved");
		assertTrue("Status was not changed to approved", claim.getStatus() == "Approved");
		
		// Start ClaimActivity and make it output data from claim
		ClaimActivity activity = getActivity();
		activity.showSelectedClaim();
		
		// Make sure status was changed to approved
		String claimStatus = activity.getFormFragment().getStatus();
		assertEquals("Claim doesn't show as submitted, even though it is", "Approved", claimStatus);
		
		// Try editing the claim, should fail since claim is in approved status
		try {
			claim.setDescription("Hello world!");
			assertTrue("Not supposed to be able to edit approved status", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//US07.05.01 - Show name and comments of approver
	public void testApproverComments() {
		// Make new claim
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim = new Claim("name1",  date1, date2, "description1", "Approved", "Megan");
		
		// Make new user who will act as approver, and his comments
		User approver = new User("Megan");
		String comment = "";
		
		// Make sure claim is in submitted status
		claim.setStatus("Submitted");
		assertTrue("Status was not changed to submitted", claim.getStatus() == "Submitted"); 
		
		// Claimant approves the claim
		// Set status to approved
		claim.setStatus("Approved");
		assertTrue("Status was not changed to approved", claim.getStatus() == "Approved");
		claim.setApprover(approver);
		comment = "Looking good!";
		claim.setComment(comment);
		
		// Make sure approver and his comments are correct
		assertTrue("Not the right approver", claim.getApprovers().equals(approver));
		assertTrue("Comment is not correct", claim.getComment().matches(comment));
		
		// Make new claim
		claim = new Claim("name1",  date1, date2, "description1", "Approved", "Megan");
		
		// Make sure claim is in submitted status
		claim.setStatus("Submitted");
		assertTrue("Status was not changed to submitted", claim.getStatus() == "Submitted"); 
		
		// Claimant returns the claim
		// Set status to returned
		claim.setStatus("Returned");
		assertTrue("Status was not changed to returned", claim.getStatus() == "Returned");
		claim.setApprover(approver);
		comment = "Needs some fixing...";
		claim.setComment(comment);
		
		// Make sure approver and his comments are correct
		assertTrue("Not the right approver", claim.getApprovers().equals(approver));
		assertTrue("Comment is not correct", claim.getComment().matches(comment));
	}
}

