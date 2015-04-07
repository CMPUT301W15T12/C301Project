package ca.ualberta.cs.cmput301w15t12.test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.GregorianCalendar;

import org.apache.http.HttpResponse;

import android.os.Environment;

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
	//US01.06.01
	//As a claimant, I want entered information to be remembered, so that I do not lose data.
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
			e.printStackTrace();
		}
	}

	public void testSaveImageAndLoadingImage(){
		try {
			ESClient esClient = new ESClient();
			String fileContent ="Test file content";
			//create a test file
			String folder = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/tmp";
			File folderF = new File(folder);
			if (!folderF.exists()) {
				folderF.mkdir();
			}		
			String testFilePath = folder + "/"+ String.valueOf(System.currentTimeMillis()) + ".txt";
			PrintWriter writer = new PrintWriter(testFilePath, "UTF-8");
			writer.println(fileContent);
			writer.close();
			
			//open the newly created test file
			File testFile = new File(testFilePath);
			long originalFileLength = testFile.length();

			
			//save the newly created test file on server
			URI uri = esClient.saveImageFileToServer(testFile);
			assertTrue("Server down",uri!=null);
			
			File file = esClient.loadImageFileFromServer(uri);
			assertEquals("loaded file's file length doesn't match the original file length",originalFileLength, file.length());	
			
		}catch (Exception e) {
			fail("Server down");
		}
	}
}
