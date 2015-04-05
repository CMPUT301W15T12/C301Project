/* Description: jUnit tests for testing CRUD related features inside item_page
 * Author: Qiushi Jiang
 * Email: qsjiang@ualberta.ca
 */
package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.AddItemActivity;
import ca.ualberta.cs.cmput301w15t12.ExpenseItemActivity;
import ca.ualberta.cs.cmput301w15t12.R;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;


public class ReceiptPhotoCRUDTest extends ActivityInstrumentationTestCase2<AddItemActivity> {

	public ReceiptPhotoCRUDTest() {
		super(AddItemActivity.class);
		// TODO Auto-generated constructor stub
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
	
}
