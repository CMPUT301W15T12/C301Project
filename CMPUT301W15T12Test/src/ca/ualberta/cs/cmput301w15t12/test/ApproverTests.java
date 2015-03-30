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
		Date d1 = format.parse("01-02-1232");
		Date d2 = format.parse("01-02-2134");
		Date d3 = format.parse("01-02-1233");
		Date d4 = format.parse("01-02-2134");
		Date d5 = format.parse("01-02-1234");
		Date d6 = format.parse("01-02-2134");
		claimListController.addClaim("c1", d5, d6, "Blah", user);
		claimListController.addClaim("c1", d3, d4, "Blah", user);
		claimListController.addClaim("c1", d1, d2, "Blah",user);
		for (int i = 0; i < claimListController.size(); i++) { 
			claimListController.getAllClaims().get(i).setStatus("Submitted");
		}
		ArrayList<Claim> submittedClaims = claimListController.filterByStatus("Submitted");

		assertTrue("first item is claim 1",submittedClaims.get(0).getStartDate().equals(d1));
		assertTrue("first item is claim 2",submittedClaims.get(1).getStartDate().equals(d3));
		assertTrue("first item is claim 3",submittedClaims.get(2).getStartDate().equals(d5));
	}



//	US08.03.01 - see ApproverActivityTests

//	US08.06.01
//	As an approver, I want to add a comment to a submitted expense claim, so that I can explain why the
//	claim was returned or provide accounting details for a payment.
	public void testaddComment() throws ParseException, AlreadyExistsException {
		String name = "Sarah";
		ClaimListController claimListController = new ClaimListController();
		User user = new User(name, "123");
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		claimListController.addClaim("c1", d1, d2, "Blah", user);
		claimListController.getClaim(0).setStatus("Submitted");
		claimListController.getClaim(0).addComment("Great");
		assertEquals("comment got set", claimListController.getClaim(0).getComment().get(0), "Great");		
	}

//	US08.07.01
//	As an approver, I want to return a submitted expense claim that was not approved, denoting the claim 
//	status as returned and setting my name as the approver for the expense claim.
	public void testapproveClaim() throws ParseException {
		String n = "leah";
		String name = "Sarah";
		ClaimListController claimListController = new ClaimListController();
		User user = new User(name, "123");
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		claimListController.addClaim("c1", d1, d2, "Blah", user);
		claimListController.getClaim(0).setStatus("Submitted");
		boolean thrown1 = false;
		boolean thrown2 = false;
		try
		{
			claimListController.getClaim(0).approveClaim(n);
		} catch (NotAllowedException e)
		{
		} catch (MissingItemException e)
		{
			thrown2 = true;
		}
		assertTrue(thrown2);
		assertFalse(thrown1);
		claimListController.getClaim(0).addComment("yay");
		try
		{
			claimListController.getClaim(0).approveClaim(n);
		} catch (NotAllowedException e)
		{
		} catch (MissingItemException e)
		{
			thrown2 = true;
		}
		assertFalse(thrown1);
		assertFalse(thrown2);
		assertTrue("Status = Approved?",claimListController.getClaim(0).getStatus().equals("Approved"));
		assertTrue("Name is set?",claimListController.getClaim(0).getApprover().getUserName().equals(name));
	}

//	US08.08.01
//	As an approver, I want to approve a submitted expense claim that was approved, denoting the claim status 
//	as approved and setting my name as the approver for the expense claim.
	public void testreturnClaim() throws ParseException {
		ClaimListController claimListController = new ClaimListController();
		String name = "Sarah";
		User user = new User(name, "123");
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		claimListController.addClaim("c1", d1, d2, "Blah", user);
		claimListController.getClaim(0).setStatus("Submitted");
		boolean thrown1 = false;
		boolean thrown2 = false;
		try
		{
			claimListController.getClaim(0).returnClaim(name);
		} catch (NotAllowedException e)
		{
		} catch (MissingItemException e)
		{
			thrown2 = true;
		}
		assertTrue(thrown2);
		assertFalse(thrown1);
		claimListController.getClaim(0).addComment("yay");
		try
		{
			claimListController.getClaim(0).returnClaim(name);
		} catch (NotAllowedException e)
		{
		} catch (MissingItemException e)
		{
			thrown2 = true;
		}
		assertFalse(thrown1);
		assertFalse(thrown2);
		assertTrue("Status = returned?",claimListController.getClaim(0).getStatus().equals("Returned"));
		assertTrue("Name is set?",claimListController.getClaim(0).getApprover().getUserName().equals(name));
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
		} catch (Exception e) {
			thrown1 = true;
		}
		try {
			claimListController.getClaim(0).approveClaim(approver);;
		} catch (Exception e) {
			thrown2 = true;
		}
		assertTrue("exception thrown", thrown1);
		assertTrue("exception thrown", thrown2);
	}
}
