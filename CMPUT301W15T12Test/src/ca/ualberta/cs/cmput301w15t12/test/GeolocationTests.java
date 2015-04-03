package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import junit.framework.TestCase;

import ca.ualberta.cs.cmput301w15t12.Destination;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.User;
import android.location.Location;


public class GeolocationTests extends TestCase
{
	DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

	//US10.02.01 added 2015-03-23
	//As a claimant, I want to specify a geolocation assisted by my mobile device (e.g., GPS) or manually using a map.
	//Test for getting current location
	public void testgetCurrentLocation(){
		//TODO Sarah
	}

	//US10.02.01 added 2015-03-23
	//As a claimant, I want to specify a geolocation assisted by my mobile device (e.g., GPS) or manually using a map.
	//test for getting remote locations
	public void testgetRemoteLocation(){
		//TODO Megan
	}

	//US10.03.01 added 2015-03-23
	//As a claimant, I want to view any set or attached geolocation using a map.
	//test for viewing locations
	public void testviewLocations(){
		//TODO Megan
	}

	//US10.01.01 added 2015-03-23
	//As a claimant, I want to set my home geolocation.
	//test for adding a location to a user
	public void testaddHomeLocation(){
		//get a fake location to add (earlier tests are testing the ability to get the location properly
		Location loc = new Location("dummyprovider");
		loc.setLatitude(20.3);
		loc.setLongitude(52.6);
		
		User user = new User("Sarah", "132");
		user.setLocation(loc);
		
		assertTrue(user.getLocation().equals(loc));
	}

	//US01.07.01 added 2015-03-23
	//As a claimant, I want to attach a geolocation to a destination.
	//test for adding a location to a destination
	public void testaddDestinationLocation(){
		//get a fake location to add (earlier tests are testing the ability to get the test properly
		Location loc = new Location("dummyprovider");
		loc.setLatitude(20.3);
		loc.setLongitude(52.6);
		
		//mandatory
		Destination destination = new Destination("destination", "desc", loc);
		assertTrue(destination.getLocation().equals(loc));
	}

	//US04.09.01 added 2015-03-23
	//As a claimant, I want to optionally attach a geolocation to an editable expense item, so I can record where an expense was incurred.
	public void testaddItemLocation() throws ParseException{
		//get a fake location to add (earlier tests are testing the ability to get the test properly
		Location loc = new Location("dummyprovider");
		loc.setLatitude(20.3);
		loc.setLongitude(52.6);
		Date d1 = format.parse("01-02-1232");
		ExpenseItem item = new ExpenseItem("item", "Ground Transport", "Fun","USD",new BigDecimal(123), d1,false);
		
		item.setlocation(loc);
		
		assertTrue(item.getlocation().equals(loc));
	}

}
