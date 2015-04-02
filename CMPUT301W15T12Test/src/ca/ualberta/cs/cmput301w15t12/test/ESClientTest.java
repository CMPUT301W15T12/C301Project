package ca.ualberta.cs.cmput301w15t12.test;
import java.util.GregorianCalendar;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ESClient;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;
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
		esClient.saveRecordToServer();
		//delete created record, so other tests can start with a fresh claimlist
		claimListController.removeClaim(id);
	}
	public void testLoad(){
		try {
			ClaimListController claimListController = new ClaimListController();	
			UserListController userListController = new UserListController();		
			ESClient esClient = new ESClient();

			User claimantA =  new User("Jim", "123");
			int id1 = claimListController.addClaim(	"Claim 1", 
									new GregorianCalendar().getTime(),
									new GregorianCalendar().getTime(),
									"description",
									claimantA);	
			int id2 = claimListController.addClaim(	"Claim 2", 
					new GregorianCalendar().getTime(),
					new GregorianCalendar().getTime(),
					"description",
					claimantA);	
			int id3 = claimListController.addClaim(	"Claim 3", 
					new GregorianCalendar().getTime(),
					new GregorianCalendar().getTime(),
					"description",
					claimantA);			
	
			userListController.addUserWithPass("Jim", "123");
			userListController.addUserWithPass("Bob", "123");
			esClient.saveRecordToServer();
			
			
			claimListController.removeClaim(id1);
			claimListController.removeClaim(id2);
			claimListController.removeClaim(id3);
			userListController.removeUser("Jim");
			userListController.removeUser("Bob");
			assertTrue("After removing all claim, claimlist should have 0 claims at this moment",claimListController.size()==0);
			assertTrue("After removing all users, claimlist should have 0 users at this moment",userListController.getUserList().size()==0);


			esClient.loadRecordFromServer();
			assertTrue("After loading, claimlist should have 3 claims",claimListController.size()==3);
			assertTrue("After loading, userlist should have 2 users",userListController.getUserList().size()==2);

		} catch (AlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
