/* Description: jUnit tests for testing CRUD related features inside item_page
 * Author: Qiushi Jiang
 * Email: qsjiang@ualberta.ca
 */
package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.Activities.ExpenseItemActivity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;


public class ReceiptPhotoCRUDTest extends ActivityInstrumentationTestCase2<ExpenseItemActivity> {

	public ReceiptPhotoCRUDTest() {
		super(ExpenseItemActivity.class);
		// TODO Auto-generated constructor stub
	}
	//[US06.01.01] - Taking photograph of a receipt
	public void testImageButton(){
		ExpenseItemActivity activity = startExpenseItemActivity();
		View buttonImage=activity.findViewById(R.id.buttonImage);
		assertTrue("receiptImageView should be an instanceof ImageButton",buttonImage.getClass() == ImageButton.class);
	}	
	
	//[US06.03.01] - Retaking photograph of a receipt
	public void testDeleteImage(){
		ExpenseItemActivity activity = startExpenseItemActivity();
		activity.deleteReceiptPhoto();
		assertTrue("After deleting the receiptphoto, getReceiptPhoto() should return null",activity.getReceiptPhoto()==null);
	}
	
	//see [US06.02.01] and [US06.04.01] in ViewPhotoActivityTest.java
	
	private ExpenseItemActivity startExpenseItemActivity(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		return (ExpenseItemActivity) getActivity();
	}
	
}
