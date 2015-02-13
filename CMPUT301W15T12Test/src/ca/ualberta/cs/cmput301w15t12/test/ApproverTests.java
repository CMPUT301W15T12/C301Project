package ca.ualberta.cs.cmput301w15t12.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;

import ca.ualberta.cs.cmput301w15t12.CantApproveOwnClaimException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimList;
import ca.ualberta.cs.cmput301w15t12.ExpenseItemActivity;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.ViewPhotoActivity;

public class ApproverTests extends ActivityInstrumentationTestCase2<ExpenseItemActivity>
{
	DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
	
	public ApproverTests() {
		super(ExpenseItemActivity.class);
	}

//	US08.01.01
//	As an approver, I want to view a list of all the expense claims that were submitted for approval, 
//	which have their claim status as submitted, showing for each claim: the claimant name, 
//  the starting date of travel, the destination(s) of travel, the claim status, total currency amounts, and any approver name.
	public void claimUITests() {
		
	}

//	US08.02.01
//	As an approver, I want the list of submitted expense claims to be sorted by starting date of travel, 
//	in order from oldest to most recent, so that older claims are considered first.
	public void sortItemTest() {
		
	}

//	US08.03.01
//	As an approver, I want to view all the details of a submitted expense claim.
	public void itemUITest() {
		
	}

//	US08.04.01
//	As an approver, I want to list all the expense items for a submitted claim, in order of entry,
//	showing for each expense item: the date the expense was incurred, the category, the textual description, amount spent, unit of currency, and whether there is a photographic receipt.
	public void seeItemAttributes() {
		
	}

//  US08.07.1 - see ViewPhotoActivityTest

//	US08.06.01
//	As an approver, I want to add a comment to a submitted expense claim, so that I can explain why the
//	claim was returned or provide accounting details for a payment.
	public void addCommentTest() throws ParseException {
		String name = "Sarah";
		User user = new User(name);
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		Claim claim = new Claim("c1", d1, d2, "Blah", "Submitted");
		ClaimList list = new ClaimList(name);
		list.add(claim);
		user.setToApprove(list);
		user.getToApprove().getClaims().get(0).setComment("great");
		claim.setComment("Great");
		assertEquals("comment got set", claim.getStatus(), "Great");		
	}

//	US08.07.01
//	As an approver, I want to return a submitted expense claim that was not approved, denoting the claim 
//	status as returned and setting my name as the approver for the expense claim.
	public void approveClaimTest() throws ParseException, CantApproveOwnClaimException {
		String n = "leah";
		String name = "Sarah";
		User user = new User(name);
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		Claim claim = new Claim("c1", d1, d2, "Blah", "Submitted");
		ClaimList list = new ClaimList(name);
		list.add(claim);
		user.setToApprove(list);
		user.getToApprove().approveClaim(claim, name, n);
		assertTrue("Status = Returned?",claim.getStatus().equals("Returned"));
		assertTrue("Name is set?",claim.getStatus().equals("Returned"));
	}

//	US08.08.01
//	As an approver, I want to approve a submitted expense claim that was approved, denoting the claim status 
//	as approved and setting my name as the approver for the expense claim.
	public void returnClaimTest() throws ParseException, CantApproveOwnClaimException {
		String n = "leah";
		String name = "Sarah";
		User user = new User(name);
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		Claim claim = new Claim("c1", d1, d2, "Blah", "Submitted");
		ClaimList list = new ClaimList(name);
		list.add(claim);
		user.setToApprove(list);
		user.getToApprove().returnClaim(claim, name, n);
		assertTrue("Status = Approved?",claim.getStatus().equals("Approved"));
		assertTrue("Name is set?",claim.getStatus().equals("Returned"));
	}

//	US08.09.01 added 2015-02-12
//	As an approver, I want to ensure I cannot return or approve an expense claim for which I am the claimant.
	public void restrictionsTest() throws ParseException, CantApproveOwnClaimException{
		boolean thrown1 = false;
		boolean thrown2 = false;
		String approver = "Sarah";
		String claimant= "Leah";
		User user = new User(approver);
		Date d1 = format.parse("01-02-1233");
		Date d2 = format.parse("01-02-2134");
		Claim claim = new Claim("c1", d1, d2, "Blah", "Submitted");
		ClaimList list = new ClaimList(claimant);
		list.add(claim);
		user.setToApprove(list);
		try {
			user.getToApprove().returnClaim(claim, approver, claimant);
		} catch (CantApproveOwnClaimException e) {
			thrown1 = true;
		}
		try {
			user.getToApprove().approveClaim(claim, approver, claimant);
		} catch (CantApproveOwnClaimException e) {
			thrown2 = true;
		}
		assertTrue("exception thrown", thrown1 == true);
		assertTrue("exception thrown", thrown2 == true);
		
		
		
	}
}
