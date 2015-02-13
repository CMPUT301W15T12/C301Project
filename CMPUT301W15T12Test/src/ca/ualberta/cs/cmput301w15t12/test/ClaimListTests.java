package ca.ualberta.cs.cmput301w15t12.test;

import junit.framework.TestCase;


public class ClaimListTests extends TestCase
{
	//TODO constructor, setup?
	//TODO tests for get/add/remove claims
	//TODO tests for get/count list
	
	//constructor
	public ClaimListTests(){
		super(ClaimList.class);
	}
	
	public void deleteClaim() {
		ClaimItem claim = new ClaimItem();
		ClaimList claimList = ClaimListController.getClaims();
		
		claimList.add(claim);
		assertTrue("Claim was not added to claims list", claimList.size() > 0 && claimList.contains(claim));
		
		claimList.remove(claim);
		assertTrue("Claim was not removed from claims list", claimList.size() == 0 && !claimList.contains(claim));
	}
	
	//US02.01.01 - list all my expense claims, showing for each claim: the starting date of travel, the destination(s) of travel, the claim status, tags, and total currency amounts
	public void testListClaims(){
		ClaimItem claim = new ClaimItem();
		ClaimList claimList = ClaimListController.getClaims();
		
	}
	
	//US02.02.01 - list of expense claims to be sorted by starting date of travel, in order from most recent to oldest
	public void testSorted(){
		
	}
}
