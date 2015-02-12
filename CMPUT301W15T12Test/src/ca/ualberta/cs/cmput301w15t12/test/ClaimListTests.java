package ca.ualberta.cs.cmput301w15t12.test;

import junit.framework.TestCase;


public class ClaimListTests extends TestCase
{
	//TODO constructor, setup?
	//TODO tests for get/add/remove claims
	//TODO tests for get/count list
	
	public void deleteClaim() {
		ClaimItem claim = new ClaimItem();
		ClaimsList claimsList = new ClaimsList();
		
		claimsList.add(claim);
		assertTrue("Claim was not added to claims list", claimsList.size() > 0 && claimsList.contains(claim));
		
		claimsList.remove(claim);
		assertTrue("Claim was not removed from claims list", claimsList.size() == 0 && !claimsList.contains(claim));
	}
}
