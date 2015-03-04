package ca.ualberta.cs.cmput301w15t12.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.graphics.SumPathEffect;
import android.test.ActivityInstrumentationTestCase2;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.CantApproveOwnClaimException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimList;
import ca.ualberta.cs.cmput301w15t12.ExpenseItemActivity;
import ca.ualberta.cs.cmput301w15t12.User;

public class ApproverTests extends ActivityInstrumentationTestCase2<ExpenseItemActivity>
{
	DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
	
	public ApproverTests() {
		super(ExpenseItemActivity.class);
	}

//	US08.01.01 - see ApproverActivityTests

//	US08.02.01
//	As an approver, I want the list of submitted expense claims to be sorted by starting date of travel, 
//	in order from oldest to most recent, so that older claims are considered first.
	public void testsortItem() throws ParseException, AlreadyExistsException {
		String approver = "Sarah";
		User user = new User(approver);
		Date d1 = format.parse("01-02-1232");
		Date d2 = format.parse("01-02-2134");
		Claim claim1 = new Claim("c1", d1, d2, "Blah",user);
		Date d3 = format.parse("01-02-1233");
		Date d4 = format.parse("01-02-2134");
		Claim claim2 = new Claim("c1", d3, d4, "Blah", user);
		Date d5 = format.parse("01-02-1234");
		Date d6 = format.parse("01-02-2134");
		Claim claim3 = new Claim("c1", d5, d6, "Blah", user);
		claim1.setStatus("Submitted");
		claim3.setStatus("Submitted");
		claim2.setStatus("Submitted");
		ClaimList list = new ClaimList();
		list.addClaim(claim3);
		list.addClaim(claim1);
		list.addClaim(claim2);
		ArrayList<Claim> s = list.getSubmittedClaims();
		ArrayList<Claim> submittedClaims = list.sort(s);
		assertTrue("first item is claim 1",submittedClaims.get(0).equals(claim1));
		assertTrue("first item is claim 2",submittedClaims.get(1).equals(claim2));
		assertTrue("first item is claim 3",submittedClaims.get(2).equals(claim3));
	}

//	US08.03.01 - see ApproverActivityTests

//	US08.04.01 - see US05.01.01 in ExpenvseListTests
//	As an approver, I want to list all the expense items for a submitted claim, in order of entry,
//	showing for each expense item: the date the expense was incurred, the category, the textual description, amount spent, unit of currency, and whether there is a photographic receipt.


//  US08.07.1 - see ViewPhotoActivityTest

//	US08.06.01
//	As an approver, I want to add a comment to a submitted expense claim, so that I can explain why the
//	claim was returned or provide accounting details for a payment.
	public void testaddComment() throws ParseException, AlreadyExistsException {
		String name = "Sarah";
		User user = new User(name);
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		Claim claim = new Claim("c1", d1, d2, "Blah", user);
		claim.setStatus("Submitted");
		ClaimList list = new ClaimList();
		list.addClaim(claim);
		claim.setComment("Great");
		assertEquals("comment got set", claim.getComment(), "Great");		
	}

//	US08.07.01
//	As an approver, I want to return a submitted expense claim that was not approved, denoting the claim 
//	status as returned and setting my name as the approver for the expense claim.
	public void testapproveClaim() throws ParseException, CantApproveOwnClaimException {
		String n = "leah";
		String name = "Sarah";
		User user = new User(name);
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		Claim claim = new Claim("c1", d1, d2, "Blah", user);
		ClaimList list = new ClaimList();
		list.addClaim(claim);
		user.setToApprove(list);
		user.getToApprove().approveClaim(claim, name);
		assertTrue("Status = Approved?",claim.getStatus().equals("Approved"));
		assertTrue("Name is set?",claim.getApprovers().contains(name));
	}

//	US08.08.01
//	As an approver, I want to approve a submitted expense claim that was approved, denoting the claim status 
//	as approved and setting my name as the approver for the expense claim.
	public void testreturnClaim() throws ParseException, CantApproveOwnClaimException {
		String n = "leah";
		String name = "Sarah";
		User user = new User(name);
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		Claim claim = new Claim("c1", d1, d2, "Blah", user);
		ClaimList list = new ClaimList();
		list.add(claim);
		user.setToApprove(list);
		user.getToApprove().returnClaim(claim, name);
		assertTrue("Status = returned?",claim.getStatus().equals("Returned"));
		assertTrue("Name is set?",claim.getApprovers().contains(name));
	}

//	US08.09.01 added 2015-02-12
//	As an approver, I want to ensure I cannot return or approve an expense claim for which I am the claimant.
	public void testrestrictions() throws ParseException, CantApproveOwnClaimException{
		boolean thrown1 = false;
		boolean thrown2 = false;
		String approver = "Sarah";
		User user = new User(approver);
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		Claim claim = new Claim("c1", d1, d2, "Blah", user);
		ClaimList list = new ClaimList();
		list.add(claim);
		user.setToApprove(list);
		try {
			user.getToApprove().returnClaim(claim, approver);
		} catch (CantApproveOwnClaimException e) {
			thrown1 = true;
		}
		try {
			user.getToApprove().approveClaim(claim, approver);
		} catch (CantApproveOwnClaimException e) {
			thrown2 = true;
		}
		assertTrue("exception thrown", thrown1);
		assertTrue("exception thrown", thrown2);
	}
}
