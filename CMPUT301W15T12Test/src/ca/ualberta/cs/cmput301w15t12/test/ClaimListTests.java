package ca.ualberta.cs.cmput301w15t12.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;
import android.widget.ListView;
import android.widget.TextView;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimList;
import ca.ualberta.cs.cmput301w15t12.ClaimListActivity;
import ca.ualberta.cs.cmput301w15t12.User;


public class ClaimListTests extends TestCase
{
	DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
	//constructor
	public ClaimListTests(){
		super();
	}
	
	//setup
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void deleteClaim() {
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim = new Claim("name1",  date1, date2, "description1", "Approved", "Megan");
		//ClaimList claimList = ClaimListController.getClaims();
		ClaimList claimList = new ClaimList();
		claimList.add(claim);
		assertTrue("Claim was not added to claims list", claimList.size() > 0 && claimList.contains(claim));
		
		claimList.remove(claim);
		assertTrue("Claim was not removed from claims list", claimList.size() == 0 && !claimList.contains(claim));
	}
	

	
	//US02.01.01 - list all my expense claims, showing for each claim: the starting date of travel, the destination(s) of travel, the claim status, tags, and total currency amounts
	public void testListClaims(){
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim = new Claim("name1",  date1, date2, "description1", "Approved", "Megan");
		ClaimList activity = startWithClaim(claim, ClaimListActivity.NORMAL);
		ListView view = (ListView) activity.findViewById(R.id.listViewClaims);
		
	}
	
	//US02.02.01 - list of expense claims to be sorted by starting date of travel, in order from most recent to oldest
	public void testSorted() throws ParseException{
		String claimant = "Sarah";
		User user = new User(claimant);
		Date d1 = format.parse("01-02-1232");
		Date d2 = format.parse("01-02-2134");
		Claim claim1 = new Claim("c1", d1, d2, "Blah", "Submitted","Leah");
		Date d3 = format.parse("01-02-1233");
		Date d4 = format.parse("01-02-2134");
		Claim claim2 = new Claim("c1", d3, d4, "Blah", "Submitted","Leah");
		Date d5 = format.parse("01-02-1234");
		Date d6 = format.parse("01-02-2134");
		Claim claim3 = new Claim("c1", d5, d6, "Blah", "Submitted","Leah");
		ClaimList list = new ClaimList();
		list.add(claim3);
		list.add(claim1);
		list.add(claim2);
		user.setToClaim(list);
		user.getToClaim().sort();
		assertTrue("first item is claim 1",user.getToApprove().getClaims().get(0).equals(claim1));
		assertTrue("first item is claim 2",user.getToApprove().getClaims().get(1).equals(claim2));
		assertTrue("first item is claim 3",user.getToApprove().getClaims().get(2).equals(claim3));	
	}
}
