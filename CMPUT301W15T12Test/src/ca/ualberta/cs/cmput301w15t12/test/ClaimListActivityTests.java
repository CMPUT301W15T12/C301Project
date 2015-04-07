package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimListActivity;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.Destination;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


public class ClaimListActivityTests extends ActivityInstrumentationTestCase2<ClaimListActivity>
{
	
	public SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

	public ClaimListActivityTests()	{
		super(ClaimListActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		ExpenseItem.init(getInstrumentation().getTargetContext());
	}
	
	//US02.03.01 added 2015-03-23
	//As a claimant, I want the list of expense claims to have each claim color code by the distance of its first destination 
	//geolocation to my home geolocation, so that claims for distant travel can be distinguished from claims for nearby travel.
	
	//US02.01.01
	//As a claimant, I want to list all my expense claims, showing for each claim: the starting date of travel, 
	//the destination(s) of travel, the claim status, tags, and total currency amounts.
	
	//US02.02.01
	//As a claimant, I want the list of expense claims to be sorted by starting date of travel, in order 
	//from most recent to oldest, so that ongoing or recent travel expenses are quickly accessible.
	public void testColorAndText() throws ParseException, AlreadyExistsException{
		ClaimListActivity activity = startApproverItemActivity();	

		ListView claimView = (ListView) activity.findViewById(R.id.listViewClaims);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),claimView);
		
		//check that the furthest claim is red and that the newest claim is first
		View view1 = claimView.getAdapter().getView(0, null, null);
		TextView tv1 = (TextView) view1.findViewById(R.id.txt);	
		assertEquals(tv1.getCurrentTextColor(), Color.RED);
		assertEquals(tv1.getText().toString(), "[01/01/1300] name3 - In Progress\n12 USD\nname\ntag1");
		
		//check that the medium claim is blue
		View view2 = claimView.getAdapter().getView(1, null, null);
		TextView tv2 = (TextView) view2.findViewById(R.id.txt);	
		assertEquals(tv2.getCurrentTextColor(), Color.BLUE);
		assertEquals(tv2.getText().toString(), "[01/02/1250] name2 - In Progress\nname");
		
		//check that the closest claim is green and that the oldest claim is last
		View view3 = claimView.getAdapter().getView(2, null, null);
		TextView tv3 = (TextView) view3.findViewById(R.id.txt);	
		assertEquals(tv3.getCurrentTextColor(), Color.GREEN);
		assertEquals(tv3.getText().toString(), "[01/01/1200] name1 - In Progress\nname");
	}
	
	private ClaimListActivity startApproverItemActivity() throws ParseException, AlreadyExistsException{
		User user  = new User("Freddie", "123");
		Location loc = new Location(LocationManager.NETWORK_PROVIDER);
		loc.setLatitude(80.3);
		loc.setLongitude(10.6);
		user.setLocation(loc);
		
		UserListController.getUserList().clear();
		UserListController.getUserList().addUser(user);
		
		Date d1 = df.parse("01/01/1200");
		Date d2 = df.parse("01/02/2134");
		
		ClaimListController clc = new ClaimListController();
		clc.clear();
		
		//close claim - worked
		int id = clc.addClaim("name1", d1, d2,"desc",user);
		Claim claim = clc.getClaim(id);
		claim.addDestination(new Destination("name","desc", loc));
		
		//medium claim
		d1 = df.parse("01/02/1250");
		Location loc2 = new Location("fake");
		loc2.setLatitude(30.3);
		loc2.setLongitude(-10.6);
		
		int id2 = clc.addClaim("name2", d1, d2,"desc",user);
		Claim claim2 = clc.getClaim(id2);
		claim2.addDestination(new Destination("name","desc", loc2));

		// far claim
		d1 = df.parse("01/01/1300");
		Location loc3 = new Location(LocationManager.NETWORK_PROVIDER);
		loc3.setLatitude(-80.3);
		loc3.setLongitude(-182.6);
		
		int id3 = clc.addClaim("name3", d1, d2,"desc",user);
		Claim claim3 = clc.getClaim(id3);
		claim3.addDestination(new Destination("name","desc", loc3));
		claim3.addTag("tag1");
		ExpenseItem item = new ExpenseItem("name", "", "description", "USD", new BigDecimal(12),d1,true);
		claim3.addItem(item);
		
		//start intent
		Intent i = new Intent();
		i.putExtra("username", "Freddie");
		setActivityIntent(i);
		return (ClaimListActivity) getActivity();
	}

}
