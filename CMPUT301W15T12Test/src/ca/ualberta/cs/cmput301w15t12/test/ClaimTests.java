package ca.ualberta.cs.cmput301w15t12.test;

import java.util.Date;

import junit.framework.TestCase;
import ca.ualberta.cs.cmput301w15t12.Claim;


public class ClaimTests extends TestCase
{
	public ClaimTests(){
		super();
	}
	//Tests for equal/submit/edit/getSum
	//Test for equals, claim name must be unique
	public void testEquals(){
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim1 = new Claim("name1",date1, date2);
		Claim claim2 = new Claim("name2", date1,date2);
		assertFalse("names are the same", claim1.getName().equals(claim2.getName()));
	}
	
	//US01.01.01 - expense claim that records my name, a starting date of travel, and an ending date of travel
	//US01.02.01 - record one or more destinations of travel and an associated reason for travel to each destination
	public void testClaim(){
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim = new Claim("name",date1, date2);
		assertNotNull("Name not initialized", claim.getName());
		assertNotNull("Start Date not initialized", claim.getStartDate());
		assertNotNull("End Date not initialized", claim.getEndDate());

	}
	
	public void testDestination(){
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim = new Claim("name",date1, date2);
		assertNotNull("Destination not initialized", claim.getDestination());
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
