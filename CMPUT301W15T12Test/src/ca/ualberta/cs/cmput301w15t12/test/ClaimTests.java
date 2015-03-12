package ca.ualberta.cs.cmput301w15t12.test;

import java.util.Date;
import java.util.GregorianCalendar;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimActivity;
import ca.ualberta.cs.cmput301w15t12.User;


public class ClaimTests extends ActivityInstrumentationTestCase2<ClaimActivity>
{
	public ClaimTests(){
		super(ClaimActivity.class);
	}

	
	//US01.01.01 - expense claim that records my name, a starting date of travel, and an ending date of travel
	//US01.02.01 - record one or more destinations of travel and an associated reason for travel to each destination
	public void testCreatingClaim(){
		String name = "travel to Toronto";
		Date startDate = new GregorianCalendar().getTime(); //Default to now when no input is supplied
		Date endDate = new GregorianCalendar(2015,GregorianCalendar.MARCH,21).getTime();
		String description = "This is a fake claim";
		User user = new User("Jim");
		int id = 0;
		
		Claim claim = new Claim(name,  startDate, endDate, description,user,id);
		assertNotNull("Name not initialized", claim.getName());
		assertEquals("Name does not match",name,claim.getName());
		assertNotNull("Start Date not initialized", claim.getStartDate());
		assertEquals("Start Date not match",startDate,claim.getStartDate());
		assertNotNull("End Date not initialized", claim.getEndDate());
		assertEquals("End Date not match",endDate,claim.getEndDate());
		assertNotNull("Destination not initialized", claim.getDestination());
	}
	

	//US01.04.01 - edit an expense claim while changes are allowed (setters)
	public void testEditClaim(){
		String oldName = "travel to Toronto";
		String newName = "travel to Calgary";
		Date startDate = new GregorianCalendar().getTime(); //Default to now when no input is supplied
		Date endDate = new GregorianCalendar(2015,GregorianCalendar.MARCH,21).getTime();
		String description = "This is a fake claim";
		User user = new User("Jim");
		int id = 0;
		
		Claim claim = new Claim(oldName,  startDate, endDate, description,user,id);
		claim.setName(newName);
		assertEquals("Name is not updated", newName, claim.getName());
	}
	
	//US01.06.01 - entered information to be remembered, so that I do not lose data
	public void testInfoSaved(){
		//TODO move this to claimList test.
	}
	
	//US09.01.01 - make and work on expense claims and items while offline, and push application and expense information online once I get connectivity.
	public void testOffline(){
		//TODO add this after manager class is finished
	}
}
