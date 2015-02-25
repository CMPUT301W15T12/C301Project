package ca.ualberta.cs.cmput301w15t12.test;

import java.util.Date;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.TextView;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.Activities.ClaimActivity;


public class ClaimTests extends ActivityInstrumentationTestCase2<ClaimActivity>
{
	public ClaimTests(){
		super(ClaimActivity.class);
	}
	//Tests for equal/submit/edit/getSum
	//Test for equals, claim name must be unique
	public void testEquals(){
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim1 = new Claim("name1",  date1, date2, "description1", "Approved", "Leah");
		Claim claim2 = new Claim("name1",  date1, date2, "description1", "Approved", "Leah");
		assertTrue("names are not the same", claim1.getName().equals(claim2.getName()));
	}
	
	//US01.01.01 - expense claim that records my name, a starting date of travel, and an ending date of travel
	//US01.02.01 - record one or more destinations of travel and an associated reason for travel to each destination
	public void testClaim(){
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim = new Claim("name1",  date1, date2, "description1", "Approved", "Leah");
		assertNotNull("Name not initialized", claim.getName());
		assertNotNull("Start Date not initialized", claim.getStartDate());
		assertNotNull("End Date not initialized", claim.getEndDate());
		assertNotNull("Destination not initialized", claim.getDestination());
	}
	
	
	//US01.03.01 - view an expense claim and its details
	public void testViewClaim(){
//		Date date1 = new Date();
//		Date date2 = new Date();
//		Claim claim = new Claim("name1",date1, date2);
		ClaimActivity activity = startClaimActivity();
		TextView viewName = (TextView) activity.findViewById(R.id.textViewName);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), viewName);
		TextView viewStartDate = (TextView) activity.findViewById(R.id.textViewStartDate);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), viewStartDate);
		TextView viewEndDate = (TextView) activity.findViewById(R.id.textViewEndDate);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), viewEndDate);
	}
	
	//US01.04.01 - edit an expense claim while changes are allowed (setters)
	public void testEditClaim(){
		Date date1 = new Date();
		Date date2 = new Date();
		Date date3 = new Date();
		Date date4 = new Date();
		Claim claim = new Claim("name1",  date1, date2, "description1", "Approved", "Leah");
		claim.setName("name2");
		claim.setStartDate(date3);
		claim.setEndDate(date4);
		assertEquals("Name not initialized", "name2", claim.getName());
		assertEquals("Start Date not initialized",date3, claim.getStartDate());
		assertEquals("End Date not initialized", date4, claim.getEndDate());
	}
	
	//US01.06.01 - entered information to be remembered, so that I do not lose data
	public void testInfoSaved(){
		
	}
	
	//US09.01.01 - make and work on expense claims and items while offline, and push application and expense information online once I get connectivity.
	public void testOffline(){
		
	}
	
	private ClaimActivity startClaimActivity(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		return (ClaimActivity) getActivity();
	}
}
