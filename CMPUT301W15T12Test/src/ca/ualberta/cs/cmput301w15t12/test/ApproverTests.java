package ca.ualberta.cs.cmput301w15t12.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import android.test.ActivityInstrumentationTestCase2;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ExpenseItemActivity;
import ca.ualberta.cs.cmput301w15t12.MissingItemException;
import ca.ualberta.cs.cmput301w15t12.NotAllowedException;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;


public class ApproverTests extends ActivityInstrumentationTestCase2<ExpenseItemActivity>
{
	DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
	
	public ApproverTests() {
		super(ExpenseItemActivity.class);
	}

//	US08.02.01
//	As an approver, I want the list of submitted expense claims to be sorted by starting date of travel, 
//	in order from oldest to most recent, so that older claims are considered first.
	public void testsortItem() throws ParseException, AlreadyExistsException {
		String approver = "Sarah";
		User user = new User(approver, "123");
		ClaimListController claimListController = new ClaimListController();
		claimListController.clear();
		UserListController.getUserList().addUser(user);
		Date d1 = format.parse("01-02-1232");
		Date d2 = format.parse("01-02-2134");
		Date d3 = format.parse("01-02-1233");
		Date d4 = format.parse("01-02-2134");
		Date d5 = format.parse("01-02-1234");
		Date d6 = format.parse("01-02-2134");
		int id1 = claimListController.addClaim("c1", d5, d6, "Blah", user);
		int id2 = claimListController.addClaim("c1", d3, d4, "Blah", user);
		int id3 = claimListController.addClaim("c1", d1, d2, "Blah",user);
		claimListController.getAllClaims().get(id1).setStatus("Submitted");
		claimListController.getAllClaims().get(id2).setStatus("Submitted");
		claimListController.getAllClaims().get(id3).setStatus("Submitted");
		ArrayList<Claim> submittedClaims = claimListController.filterByStatus("Submitted");
		assertTrue("first item is claim 1",submittedClaims.get(0).getStartDate().equals(d5));
		assertTrue("first item is claim 2",submittedClaims.get(1).getStartDate().equals(d3));
		assertTrue("first item is claim 3",submittedClaims.get(2).getStartDate().equals(d1));
	}



//	US08.03.01 - see ApproverActivityTests

//	US08.06.01
//	As an approver, I want to add a comment to a submitted expense claim, so that I can explain why the
//	claim was returned or provide accounting details for a payment.
	public void testaddComment() throws ParseException, AlreadyExistsException {
		String name = "Sarah";
		ClaimListController claimListController = new ClaimListController();
		User user = new User(name, "123");
		UserListController.getUserList().addUser(user);
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		int id =claimListController.addClaim("c1", d1, d2, "Blah", user);
		claimListController.getClaim(id).setStatus("Submitted");
		claimListController.getClaim(id).addComment("Great");
		assertEquals("comment got set", claimListController.getClaim(id).getComment().get(0), "Great");		
	}

//	US08.07.01
//	As an approver, I want to return a submitted expense claim that was not approved, denoting the claim 
//	status as returned and setting my name as the approver for the expense claim.
	public void testapproveClaim() throws ParseException, AlreadyExistsException {
		String n = "leah";
		String name = "Sarah";
		ClaimListController claimListController = new ClaimListController();
		User user = new User(name, "123");
		User approver = new User(n, "123");
		UserListController.getUserList().addUser(user);
		UserListController.getUserList().addUser(approver);
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		int id = claimListController.addClaim("c1", d1, d2, "Blah", user);
		claimListController.getClaim(id).setStatus("Submitted");
		boolean thrown2 = false;
		try
		{
			claimListController.getClaim(id).approveClaim(n);
		} catch (NotAllowedException e)
		{
		} catch (MissingItemException e)
		{
			thrown2 = true;
		}
		assertTrue(thrown2);
		thrown2 = false;
		claimListController.getClaim(id).addComment("yay");
		try
		{
			claimListController.getClaim(id).approveClaim(n);
		} catch (NotAllowedException e) {
			//if the user tries to approve their own claim
		} catch (MissingItemException e) {
			thrown2 = true;
		}
		assertFalse(thrown2);
		assertTrue("Status = Approved?",claimListController.getClaim(id).getStatus().equals("Approved"));
		assertTrue("Name is set?",claimListController.getClaim(id).getApprover().getUserName().equals(n));
	}

//	US08.08.01
//	As an approver, I want to approve a submitted expense claim that was approved, denoting the claim status 
//	as approved and setting my name as the approver for the expense claim.
	public void testreturnClaim() throws ParseException, AlreadyExistsException {
		ClaimListController claimListController = new ClaimListController();
		claimListController.clear();
		String name = "Sarah";
		User approver = new User("Leah", "123");
		User user = new User(name, "123");
		UserListController.getUserList().clear();
		UserListController.getUserList().addUser(user);
		UserListController.getUserList().addUser(approver);
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		int id = claimListController.addClaim("c1", d1, d2, "Blah", user);
		claimListController.getClaim(id).setStatus("Submitted");
		boolean thrown2 = false;
		try
		{
			claimListController.getClaim(id).returnClaim(approver.getUserName());
		} catch (NotAllowedException e) {
			//if the user tries to approve their own claim
		} catch (MissingItemException e) {
			thrown2 = true;
		}
		assertTrue(thrown2);
		thrown2 = false;
		claimListController.getClaim(id).addComment("yay");
		try
		{
			claimListController.getClaim(id).returnClaim(approver.getUserName());
		} catch (NotAllowedException e) {
			//if the user tries to approve their own claim
		} catch (MissingItemException e) {
			thrown2 = true;
		}
		assertFalse(thrown2);
		assertTrue("Status = returned?",claimListController.getClaim(id).getStatus().equals("Returned"));
		assertTrue("Name is set?",claimListController.getClaim(id).getApprover().getUserName().equals(approver.getUserName()));
	}

//	US08.09.01 added 2015-02-12
//	As an approver, I want to ensure I cannot return or approve an expense claim for which I am the claimant.
	public void testrestrictions() throws ParseException,  AlreadyExistsException{
		boolean thrown1 = false;
		boolean thrown2 = false;
		String approver = "Sarah";
		User user = new User(approver, "123");
		ClaimListController claimListController = new ClaimListController();
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		claimListController.addClaim("c1", d1, d2, "Blah", user);
		claimListController.getClaim(0).setStatus("Submitted");
		try {
			claimListController.getClaim(0).returnClaim(approver);
		} catch (NotAllowedException e) {
			thrown1 = true;
		} catch (MissingItemException e)
		{
			//this just happens if comment is missing and is not relevant to this test
		}
		try {
			claimListController.getClaim(0).approveClaim(approver);;
		} catch (NotAllowedException e) {
			thrown2 = true;
		} catch (MissingItemException e)
		{
			//this just happens if comment is missing and is not relevant to this test
		}
		assertTrue("exception thrown", thrown1);
		assertTrue("exception thrown", thrown2);
	}
}
