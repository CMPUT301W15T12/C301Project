package ca.ualberta.cs.cmput301w15t12.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimList;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.User;
import junit.framework.TestCase;

public class ClaimListControllertTest extends TestCase {
	//constructor
	public ClaimListControllertTest(){
		super();
	}
	
	//setup
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testFilterByClaimant(){
		ClaimListController claimListController = new ClaimListController();		
		User claimantA =  new User("Jim");
		User claimantB = new User("Bob");
		claimListController.addClaim(	"Claim 1", 
								new GregorianCalendar().getTime(),
								new GregorianCalendar().getTime(),
								"description",
								claimantA);	
		claimListController.addClaim(	"Claim 2", 
				new GregorianCalendar().getTime(),
				new GregorianCalendar().getTime(),
				"description",
				claimantB);			
		claimListController.addClaim(	"Claim 3", 
				new GregorianCalendar().getTime(),
				new GregorianCalendar().getTime(),
				"description",
				claimantB);	
		
		ArrayList<Claim> claimsBelongToClaimantA = claimListController.filterByClaimant(claimantA);
		for (int i=0; i<claimsBelongToClaimantA.size();i++){
			assertEquals("filterByClaimant(claimantA) returned a claim that doesn't belong to claimantA",claimsBelongToClaimantA.get(i).getClaimant(),claimantA);
		}
		ArrayList<Claim> claimsBelongToClaimantB = claimListController.filterByClaimant(claimantB);
		for (int i=0; i<claimsBelongToClaimantB.size();i++){
			assertEquals("filterByClaimant(claimantB) returned a claim that doesn't belong to claimantB",claimsBelongToClaimantB.get(i).getClaimant(),claimantB);
		}		
	}
	
	public void testFilterByStatus(){
		ClaimListController claimListController = new ClaimListController();		

		claimListController.addClaim(	"Claim 1", 
								new GregorianCalendar().getTime(),
								new GregorianCalendar().getTime(),
								"description",
								new User("Jim"));	
		int claim2Id = claimListController.addClaim(	"Claim 2", 
				new GregorianCalendar().getTime(),
				new GregorianCalendar().getTime(),
				"description",
				new User("Jim"));		
		
		
		
		int claim3Id = claimListController.addClaim(	"Claim 3", 
				new GregorianCalendar().getTime(),
				new GregorianCalendar().getTime(),
				"description",
				new User("Jim"));	
		
		Claim claim2 = claimListController.getClaim(claim2Id);
		Claim claim3 = claimListController.getClaim(claim3Id);
		claim2.setStatus("Approved");
		claim3.setStatus("Approved");
		
		ArrayList<Claim> approvedClaims = claimListController.filterByStatus("Approved");

		assertEquals("The number of 'Approved' claims doesn't match",2,approvedClaims.size());		
	}	
	
	public void testFilteredByTag(){
		ClaimListController claimListController = new ClaimListController();		

		int claim1Id = claimListController.addClaim(	"Claim 1", 
								new GregorianCalendar().getTime(),
								new GregorianCalendar().getTime(),
								"description",
								new User("Jim"));	
		int claim2Id = claimListController.addClaim(	"Claim 2", 
				new GregorianCalendar().getTime(),
				new GregorianCalendar().getTime(),
				"description",
				new User("Jim"));		
		
		Claim claim1 = claimListController.getClaim(claim1Id);
		Claim claim2 = claimListController.getClaim(claim2Id);
		try {
			claim1.addTag("cat");
			claim2.addTag("cat");
			assertEquals("there should be two claims with tag 'cat'",2,claimListController.filterByTag("cat").size());
		} catch (AlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}	
	
	public void testTagGetterSetter(){
		ClaimListController claimListController = new ClaimListController();	
		String tag1 = "tag1";
		String tag2 = "tag2";
		String unusedTag = "unused tag";
		int claimId = claimListController.addClaim(	"Claim 1", 
				new GregorianCalendar().getTime(),
				new GregorianCalendar().getTime(),
				"description",
				new User("Jim"));	
		try {
			claimListController.addTagToClaim(claimId, tag1);
			claimListController.addTagToClaim(claimId, tag2);

			ArrayList<String> tagList = claimListController.getTagListFromClaim(claimId);
			assertTrue("Wrong number of tags inside taglist",tagList.size()==2);
			assertTrue("tag1 is not added",tagList.contains(tag1));
			assertTrue("tag2 is not added",tagList.contains(tag2));
			assertFalse("unusedTag should not be inside the taglist",tagList.contains(unusedTag));

		} catch (AlreadyExistsException e) {
			//tag already existed. Do nothing
		}
		
	}
	
	//US01.05.01
	public void testRemoveClaim(){
		ClaimListController clc = new ClaimListController();
		clc.clear();
		Date date1 = new Date();
		Date date2 = new Date();
		User user = new User("name");
		clc.addClaim("Claim",date1, date2, "description", user);
		assertEquals("claim was not added", 1,clc.size());
		if (clc.getClaim(0).editable()){
			clc.removeClaim(0);
			assertEquals("claim was not removed", 0, clc.size());
		} else {
			//cannot remove claim, do nothing
		}
		
	}
	
}
