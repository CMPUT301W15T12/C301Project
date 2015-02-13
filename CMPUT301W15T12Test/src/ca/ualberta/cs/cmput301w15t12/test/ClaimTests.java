package ca.ualberta.cs.cmput301w15t12.test;

import junit.framework.TestCase;


public class ClaimTests extends TestCase
{
	public ClaimTests(){
		super(ClaimItem.class);
	}
	//Tests for equal/submit/edit/getSum
	//Test for equals, claim name must be unique
	public void testEquals(){
		ClaimItem claim1 = new ClaimItem();
		ClaimItem claim2 = new ClaimItem();
		assertFalse("names are the same", claim1.getName().equals(claim2.getName()));
	}
	
	//US01.01.01 - expense claim that records my name, a starting date of travel, and an ending date of travel
	//US01.02.01 - record one or more destinations of travel and an associated reason for travel to each destination
	public void testClaim(){
		ClaimItem claim = new ClaimItem();
		assertNotNull("Name not initialized", claim.getName());
		assertNotNull("Start Date not initialized", claim.getStartDate());
		assertNotNull("End Date not initialized", claim.getEndDate());
		assertNotNull("Destination not initialized", claim.getDestination());
		assertNotNull("Destination description not initialized", claim.getDestinationDescription);
	}
	
	//US01.03.01 - view an expense claim and its details
	public void testViewClaim(){
	
	}
	
	//US01.04.01 - edit an expense claim while changes are allowed
	public void testCanEdit(){
		
	}
	
	//US01.06.01 - entered information to be remembered, so that I do not lose data
	public void testInfoSaved(){
		
	}
	
	//US09.01.01 - make and work on expense claims and items while offline, and push application and expense information online once I get connectivity.
	public void testOffline(){
		
	}
}
