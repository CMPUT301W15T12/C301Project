/* Description: jUnit tests for testing the ViewPhotoActivity
 * This test ensures the photo is correctly loaded, and the image size meets the requirement.
 */
package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageView;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.R;
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
	
	//[US06.02.01] - Viewing photograph of a receipt
	//[US08.05.01] - As an approver, I want to view any attached photographic receipt for an expense item.
	public void testImageView() throws ParseException, AlreadyExistsException{
		ViewPhotoActivity activity = startViewPhotoActivity();
		View receiptImageView = (ImageView)activity.findViewById(R.id.receiptImageView);
		assertNotNull("Fail to allocate image view",receiptImageView);
		assertTrue("receiptImageView should be an instanceof ImageView",receiptImageView.getClass() == ImageView.class);
	}
	
	//[US06.04.01] - Limiting image file size
	public void testImageSize() throws ParseException, AlreadyExistsException{
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
