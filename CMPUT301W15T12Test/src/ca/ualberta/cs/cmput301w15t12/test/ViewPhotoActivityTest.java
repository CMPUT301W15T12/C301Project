/* Description: jUnit tests for testing the ViewPhotoActivity
 * This test ensures the photo is correctly loaded, and the image size meets the requirement.
 * Author: Qiushi Jiang
 * Email: qsjiang@ualberta.ca
 */
package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.ViewPhotoActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;
import ca.ualberta.cs.cmput301w15t12.R;

public class ViewPhotoActivityTest extends ActivityInstrumentationTestCase2<ViewPhotoActivity> {

	public ViewPhotoActivityTest() {
		super(ViewPhotoActivity.class);
	}
	
	//[US06.02.01] - Viewing photograph of a receipt
	//[US08.05.01] - As an approver, I want to view any attached photographic receipt for an expense item.
	public void testImageView(){
		ViewPhotoActivity activity = startViewPhotoActivity();
		View receiptImageView = (ImageView)activity.findViewById(R.id.receiptImageView);
		assertNotNull("Fail to allocate image view",receiptImageView);
		assertTrue("receiptImageView should be an instanceof ImageView",receiptImageView.getClass() == ImageView.class);
		if((BitmapDrawable)((ImageView)receiptImageView).getDrawable()==null){
			fail("Receipt photo is not loaded inside the image view");
			return;
		}
	}
	
	//[US06.04.01] - Limiting image file size
	public void testImageSize(){
		ViewPhotoActivity activity = startViewPhotoActivity();
		ImageView receiptImageView = (ImageView)activity.findViewById(R.id.receiptImageView);
		if((BitmapDrawable)receiptImageView.getDrawable()==null){
			fail("Receipt photo is not loaded inside the image view");
			return;
		}
		Bitmap imageBitMap = ((BitmapDrawable)receiptImageView.getDrawable()).getBitmap();
		assertTrue("photo should be smaller then 65536 bytes",imageBitMap.getByteCount()<65536);
	}
	
	//see [US06.01.01] and [US06.03.01] in ReceiptPhotoCRUDTest
	
	private ViewPhotoActivity startViewPhotoActivity(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		return (ViewPhotoActivity) getActivity();
	}
	
}
