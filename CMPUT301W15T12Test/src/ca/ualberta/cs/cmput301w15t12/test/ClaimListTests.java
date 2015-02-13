package ca.ualberta.cs.cmput301w15t12.test;

import junit.framework.TestCase;
import android.widget.ListView;
import android.widget.TextView;


public class ClaimListTests extends TestCase
{
	//TODO tests for get/add/remove claims
	//TODO tests for get/count list
	
	//constructor
	public ClaimListTests(){
		super(ClaimList.class);
	}
	
	//setup
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void deleteClaim() {
		Claim claim = new Claim();
		ClaimList claimList = ClaimListController.getClaims();
		
		claimList.add(claim);
		assertTrue("Claim was not added to claims list", claimList.size() > 0 && claimList.contains(claim));
		
		claimList.remove(claim);
		assertTrue("Claim was not removed from claims list", claimList.size() == 0 && !claimList.contains(claim));
	}
	
	public void testDisplayText(){
		String text = "Hello";
		IntentReaderActivity activity = startWithText(text, IntentReaderActivity.NORMAL);
		TextView view = (TextView) activity.findViewById(R.id.intentText);
		assertEquals("correct text?", text, view.getText());	
	}
	
	//US02.01.01 - list all my expense claims, showing for each claim: the starting date of travel, the destination(s) of travel, the claim status, tags, and total currency amounts
	public void testListClaims(){
		Claim claim = new Claim();
		ClaimList claimList = ClaimListController.getClaims();
		ClaimListActivity activity = startWithClaim(claim, ClaimListActivity.NORMAL);
		ListView view = (ListView) activity.findViewById(R.id.listViewClaims);
		
	}
	
	//US02.02.01 - list of expense claims to be sorted by starting date of travel, in order from most recent to oldest
	public void testSorted(){
		
	}
}
