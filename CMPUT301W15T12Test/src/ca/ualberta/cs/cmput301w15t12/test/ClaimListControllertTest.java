package ca.ualberta.cs.cmput301w15t12.test;

import java.util.ArrayList;
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
}