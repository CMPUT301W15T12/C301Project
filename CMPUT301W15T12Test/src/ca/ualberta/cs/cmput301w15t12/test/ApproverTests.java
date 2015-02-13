package ca.ualberta.cs.cmput301w15t12.test;

import junit.framework.TestCase;


public class ApproverTests extends TestCase
{

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
//	US08.05.01
//	As an approver, I want to view any attached photographic receipt for an expense item.
	public void seePhotoTest() {
		
	}
//	US08.06.01
//	As an approver, I want to add a comment to a submitted expense claim, so that I can explain why the
//	claim was returned or provide accounting details for a payment.
	public void addCommentTest() {
		
	}

//	US08.07.01
//	As an approver, I want to return a submitted expense claim that was not approved, denoting the claim 
//	status as returned and setting my name as the approver for the expense claim.
	public void approveClaimTest() {
		
	}

//	US08.08.01
//	As an approver, I want to approve a submitted expense claim that was approved, denoting the claim status 
//	as approved and setting my name as the approver for the expense claim.
	public void returnClaimTest() {
		
	}

//	US08.09.01 added 2015-02-12
//	As an approver, I want to ensure I cannot return or approve an expense claim for which I am the claimant.
	public void restrictionsTest() {
		
	}
}
