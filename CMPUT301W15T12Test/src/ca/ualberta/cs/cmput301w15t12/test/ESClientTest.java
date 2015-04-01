package ca.ualberta.cs.cmput301w15t12.test;
import java.util.GregorianCalendar;

import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ESClient;
import ca.ualberta.cs.cmput301w15t12.User;
import junit.framework.TestCase;


public class ESClientTest extends TestCase {
	//constructor
	public ESClientTest(){
		super();
	}
	
	//setup
	protected void setUp() throws Exception {
		super.setUp();
	}
	public void testSave(){
		ClaimListController claimListController = new ClaimListController();		
		User claimantA =  new User("Jim", "123");
		int id = claimListController.addClaim(	"Claim 1", 
								new GregorianCalendar().getTime(),
								new GregorianCalendar().getTime(),
								"description",
								claimantA);	
		Claim claim = claimListController.getClaim(id);
		ESClient esClient = new ESClient();
		esClient.saveClaimToServer(claim);
	}
}
