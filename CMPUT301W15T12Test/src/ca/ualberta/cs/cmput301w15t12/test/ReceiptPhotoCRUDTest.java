/* Description: jUnit tests for testing CRUD related features inside item_page
 * Author: Qiushi Jiang
 * Email: qsjiang@ualberta.ca
 */
package ca.ualberta.cs.cmput301w15t12.test;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import ca.ualberta.cs.cmput301w15t12.AddItemActivity;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ESClient;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;


public class ReceiptPhotoCRUDTest extends ActivityInstrumentationTestCase2<AddItemActivity> {

	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	
	public ReceiptPhotoCRUDTest() {
		super(AddItemActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		// Initialize currencies and categories list in ExpenseItem
		ExpenseItem.init(getInstrumentation().getTargetContext());
			
	}
	
	//[US06.01.01] - Taking photograph of a receipt
	//[US06.03.01] - Retaking photograph of a receipt
	public void testImage() throws ParseException, AlreadyExistsException{
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
			ExpenseItem expenseItem = new ExpenseItem("name","Air Fare","description","USD", new BigDecimal(66.69), new Date(), false);
			expenseItem.setUri(uri);
			assertEquals("The image was not saved to the expense item",expenseItem.getUri(), uri);	
			expenseItem.setUri(null);
			assertEquals("The image has been deleted",expenseItem.getUri(), null);	
			
		}catch (Exception e) {
			fail("Server down");
		}
	}
	

	
	
	private AddItemActivity startExpenseItemActivity() throws ParseException, AlreadyExistsException{
		//make a claim with an item
		User user  = new User("Freddie", "123");
		
		UserListController.getUserList().clear();
		UserListController.getUserList().addUser(user);
		
		Date d1 = df.parse("01/01/1200");
		Date d2 = df.parse("01/02/2134");
		
		ClaimListController clc = new ClaimListController();
		clc.clear();
		
		int id = clc.addClaim("name", d1, d2,"desc",user);
		ExpenseItem item = new ExpenseItem("name","","description","", new BigDecimal(66.69), new Date(), false);
		clc.getClaim(id).addItem(item);
		Intent intent = new Intent();
		intent.putExtra("claim_id",id);
		intent.putExtra("item_index",0);
		intent.putExtra("username","Freddie");
		intent.putExtra("option","Add");
		setActivityIntent(intent);
		return (AddItemActivity) getActivity();
	}

}

