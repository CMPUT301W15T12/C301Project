package ca.ualberta.cs.cmput301w15t12.test;

import java.util.Date;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import ca.ualberta.cs.cmput301w15t12.AddClaimActivity;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;

public class AddClaimActivityTest extends ActivityInstrumentationTestCase2<AddClaimActivity>  {

	public AddClaimActivityTest() {
		super(AddClaimActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	//US07.02.01 - Visual warning of missing values
		public void testIncompleteClaim() throws AlreadyExistsException {
			// Start ClaimActivity, should be blank since its a new claim
			AddClaimActivity activity = startAddClaimActivity();
			ClaimListController claimList = new ClaimListController();
			Date date1 = new Date();
			Date date2 = new Date();
			String Username = activity.getIntent().getExtras().getString("username");
			User user = UserListController.getUserList().getUser(Username);
			int id1 = claimList.addClaim("name1",  date1, date2, null,user);

			//claim should not have been created because field missing
			assertTrue("Missing description still creates claim", claimList.getClaim(id1).getDescription() == null);
			
			int id2 = claimList.addClaim("name1",  null, date2, "description",user);

			//claim should not have been created because field missing
			assertTrue("Missing start date still creates claim", claimList.getClaim(id2).getStartDate() == null);
			
			int id3 = claimList.addClaim(null,  date1, date2, "description",user);

			//claim should not have been created because field missing
			assertTrue("Missing name still creates claim", claimList.getClaim(id3).getName() == null);
			
			int id4 = claimList.addClaim("name1",  date1, null, "description",user);

			//claim should not have been created because field missing
			assertTrue("Missing end date still creates claim", claimList.getClaim(id4).getEndDate() == null);
		}
		
		private AddClaimActivity startAddClaimActivity() throws AlreadyExistsException{

			User user  = new User("Megan", "123");
			UserListController.getUserList().clear();

			UserListController.getUserList().addUser(user);

			Intent i = new Intent();
			i.putExtra("username","Megan");
			i.putExtra("option","add");
			setActivityIntent(i);

			return (AddClaimActivity) getActivity();
		}
}
