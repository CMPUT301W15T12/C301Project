/* Description: jUnit tests for testing the ViewPhotoActivity
 * This test ensures the photo is correctly loaded, and the image size meets the requirement.
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ESClient;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;
import ca.ualberta.cs.cmput301w15t12.ViewPhotoActivity;

public class ViewPhotoActivityTest extends ActivityInstrumentationTestCase2<ViewPhotoActivity> {

	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	
	public ViewPhotoActivityTest() {
		super(ViewPhotoActivity.class);
		
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		// Initialize currencies and categories list in ExpenseItem
		ExpenseItem.init(getInstrumentation().getTargetContext());
			
	}
	
	
	//[US06.04.01] - Limiting image file size
	public void testImageSize() throws ParseException, AlreadyExistsException{
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
			Bitmap bitmap = BitmapFactory.decodeFile(file.toString());
			assertTrue("The size of the file is greater than 65536 bytes", bitmap.getByteCount() < 65536) ;
			assertEquals("loaded file's file length doesn't match the original file length",originalFileLength, file.length());	
				
		}catch (Exception e) {
			fail("Server down");
		}	
	}
	
	//see [US06.01.01] and [US06.03.01] in ReceiptPhotoCRUDTest
	
	@SuppressWarnings("unused")
	private ViewPhotoActivity startViewPhotoActivity() throws ParseException, AlreadyExistsException{
		//make a claim with an item
		User user  = new User("Freddie", "123");
		
		UserListController.getUserList().clear();
		UserListController.getUserList().addUser(user);
		
		Date d1 = df.parse("01/01/1200");
		Date d2 = df.parse("01/02/2134");
		
		ClaimListController clc = new ClaimListController();
		clc.clear();
		
		int id = clc.addClaim("name", d1, d2,"desc",user);
		ExpenseItem item = new ExpenseItem("name", "", "description", "USD", new BigDecimal(12),d1,true);
		clc.getClaim(id).addItem(item);
		Intent intent = new Intent();
		intent.putExtra("claim_id",id);
		intent.putExtra("item_index",0);
		setActivityIntent(intent);
		return (ViewPhotoActivity) getActivity();
	}
	
}
