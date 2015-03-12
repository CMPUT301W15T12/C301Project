
package ca.ualberta.cs.cmput301w15t12.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import junit.framework.TestCase;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimList;
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
	
	public void testSingleton() {
		ClaimList claimList1 = new ClaimList();		

		claimList1.addClaim(	"Claim Name", 
								new GregorianCalendar().getTime(),
								new GregorianCalendar().getTime(),
								"description",
								new User("Jim"));
		
		
		ClaimList claimList2 = new ClaimList();
		
		assertEquals("Both instances of claimList2 should return the same ArrayList<Claim> claims", claimList1.getAllClaims(),claimList2.getAllClaims());
	}
	
	public void testFilterByClaimant(){
		ClaimList claimList = new ClaimList();		
		User claimantA =  new User("Jim");
		User claimantB = new User("Bob");
		claimList.addClaim(	"Claim 1", 
								new GregorianCalendar().getTime(),
								new GregorianCalendar().getTime(),
								"description",
								claimantA);	
		claimList.addClaim(	"Claim 2", 
				new GregorianCalendar().getTime(),
				new GregorianCalendar().getTime(),
				"description",
				claimantB);			
		claimList.addClaim(	"Claim 3", 
				new GregorianCalendar().getTime(),
				new GregorianCalendar().getTime(),
				"description",
				claimantB);	
		
		ArrayList<Claim> claimsBelongToClaimantA = claimList.filterByClaimant(claimantA);
		for (int i=0; i<claimsBelongToClaimantA.size();i++){
			assertEquals("filterByClaimant(claimantA) returned a claim that doesn't belong to claimantA",claimsBelongToClaimantA.get(i).getClaimant(),claimantA);
		}
		ArrayList<Claim> claimsBelongToClaimantB = claimList.filterByClaimant(claimantB);
		for (int i=0; i<claimsBelongToClaimantB.size();i++){
			assertEquals("filterByClaimant(claimantB) returned a claim that doesn't belong to claimantB",claimsBelongToClaimantB.get(i).getClaimant(),claimantB);
		}		
	}
	
	public void testFilterByStatus(){
		ClaimList claimList = new ClaimList();		

		claimList.addClaim(	"Claim 1", 
								new GregorianCalendar().getTime(),
								new GregorianCalendar().getTime(),
								"description",
								new User("Jim"));	
		int claim2Id = claimList.addClaim(	"Claim 2", 
				new GregorianCalendar().getTime(),
				new GregorianCalendar().getTime(),
				"description",
				new User("Jim"));		
		
		
		
		int claim3Id = claimList.addClaim(	"Claim 3", 
				new GregorianCalendar().getTime(),
				new GregorianCalendar().getTime(),
				"description",
				new User("Jim"));	
		
		Claim claim2 = claimList.getClaim(claim2Id);
		Claim claim3 = claimList.getClaim(claim3Id);
		claim2.setStatus("Approved");
		claim3.setStatus("Approved");
		
		ArrayList<Claim> approvedClaims = claimList.filterByStatus("Approved");

		assertEquals("The number of 'Approved' claims doesn't match",2,approvedClaims.size());		
	}	
	
	public void testFilteredByTag(){
		ClaimList claimList = new ClaimList();		

		int claim1Id = claimList.addClaim(	"Claim 1", 
								new GregorianCalendar().getTime(),
								new GregorianCalendar().getTime(),
								"description",
								new User("Jim"));	
		int claim2Id = claimList.addClaim(	"Claim 2", 
				new GregorianCalendar().getTime(),
				new GregorianCalendar().getTime(),
				"description",
				new User("Jim"));		
		
		Claim claim1 = claimList.getClaim(claim1Id);
		Claim claim2 = claimList.getClaim(claim2Id);
		try {
			claim1.addTag("cat");
			claim2.addTag("cat");
			assertEquals("there should be two claims with tag 'cat'",2,claimList.filterByTag("cat").size());
		} catch (AlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
	
	//TODO Rewrite this test case once finish integrating with ExpenseList class
	//US02.01.01 - list all my expense claims, showing for each claim: the starting date of travel, the destination(s) of travel, the claim status, tags, and total currency amounts
	public void testListClaims(){
		/*ClaimList claimList = new ClaimList();
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim = new Claim("name1",  date1, date2, "description1", "Tag", "Megan");
		claimList.addClaim(claim);*/
	}
	
	//TODO Rewrite this test case once finish integrating with ExpenseList class		
	//US02.02.01 - list of expense claims to be sorted by starting date of travel, in order from most recent to oldest
	public void testSorted() throws ParseException{
	/*	String claimant = "Sarah";
		User user = new User(claimant);
		Date d1 = format.parse("01-02-1232");
		Date d2 = format.parse("01-02-2134");
		Claim claim1 = new Claim("c1", d1, d2, "Blah", "Tag","Leah");
		Date d3 = format.parse("01-02-1233");
		Date d4 = format.parse("01-02-2134");
		Claim claim2 = new Claim("c1", d3, d4, "Blah", "Tag","Leah");
		Date d5 = format.parse("01-02-1234");
		Date d6 = format.parse("01-02-2134");
		Claim claim3 = new Claim("c1", d5, d6, "Blah", "Tag","Leah");
		ClaimList list = new ClaimList();
		list.addClaim(claim3);
		list.addClaim(claim1);
		list.addClaim(claim2);
		user.setToClaim(list);
		user.getToClaim().sort();
		assertTrue("first item is claim 1",user.getToApprove().getClaims().get(0).equals(claim1));
		assertTrue("first item is claim 2",user.getToApprove().getClaims().get(1).equals(claim2));
		assertTrue("first item is claim 3",user.getToApprove().getClaims().get(2).equals(claim3));	*/
	}
}

