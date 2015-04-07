/* Description: jUnit tests for testing CRUD related features inside item_page
 * Author: Qiushi Jiang
 * Email: qsjiang@ualberta.ca
 */
package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.AddItemActivity;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.ExpenseItemActivity;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;
import ca.ualberta.cs.cmput301w15t12.ViewPhotoActivity;
import android.content.Intent;
import android.net.Uri;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;


public class ReceiptPhotoCRUDTest extends ActivityInstrumentationTestCase2<AddItemActivity> {

	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	
	public ReceiptPhotoCRUDTest() {
		super(AddItemActivity.class);
	}
	//[US06.01.01] - Taking photograph of a receipt
	public void testImageButton(){
		AddItemActivity activity = startExpenseItemActivity();
		View buttonImage=activity.findViewById(R.id.buttonImage);
		assertTrue("receiptImageView should be an instanceof ImageButton",buttonImage.getClass() == ImageButton.class);
	}	
	
	//[US06.03.01] - Retaking photograph of a receipt
	public void testDeleteImage(){
		AddItemActivity activity = startExpenseItemActivity();
		//deleteImage(view);
		//assertTrue("After deleting the receiptphoto, deleteImage() should return null",activity.getPhoto()==null);
		assert(false);
	}
	
	//see [US06.02.01] and [US06.04.01] in ViewPhotoActivityTest.java
	
	private AddItemActivity startExpenseItemActivity(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		return (AddItemActivity) getActivity();
	}
	
	
	private AddItemActivity startViewPhotoActivity() throws ParseException, AlreadyExistsException{
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
		return (AddItemActivity) getActivity();
	}

}
