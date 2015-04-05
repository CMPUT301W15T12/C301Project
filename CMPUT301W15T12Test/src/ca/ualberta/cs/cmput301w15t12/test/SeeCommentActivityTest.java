package ca.ualberta.cs.cmput301w15t12.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.ListView;
import android.widget.TextView;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.MissingItemException;
import ca.ualberta.cs.cmput301w15t12.NotAllowedException;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.SeeCommentsActivity;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;


public class SeeCommentActivityTest  extends ActivityInstrumentationTestCase2<SeeCommentsActivity>
{
	public SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	
	public SeeCommentActivityTest()
	{
		super(SeeCommentsActivity.class);	
	}

		//US07.05.01 - Show name and comments of approver
		public void testApproverComments() throws ParseException, AlreadyExistsException, NotAllowedException, MissingItemException {
			SeeCommentsActivity activity = startApproverItemActivity();
			
			ListView commentView = (ListView) activity.findViewById(R.id.listViewComments);
			ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),commentView);
			
			String s1 = (String) commentView.getAdapter().getItem(0);
			String s2 = (String) commentView.getAdapter().getItem(1);
			
			//check the order
			assertEquals(s1, "comment2");
			assertEquals(s2, "comment1");
			
			TextView approver = (TextView) activity.findViewById(R.id.textSeeCommentsApprovers);
			ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), approver);
			assertEquals(approver.getText().toString(), "Approver: Sarah");
			
		}

		private SeeCommentsActivity startApproverItemActivity() throws ParseException, AlreadyExistsException, NotAllowedException, MissingItemException{
			User user  = new User("Freddie", "123");
			User approver = new User("Sarah", "123");
			
			UserListController.getUserList().clear();
			UserListController.getUserList().addUser(user);
			UserListController.getUserList().addUser(approver);
			
			Date d1 = df.parse("01/01/1200");
			Date d2 = df.parse("01/02/2134");
			
			ClaimListController clc = new ClaimListController();
			clc.clear();
			
			int id = clc.addClaim("name1", d1, d2,"desc",user);
			clc.getClaim(id).setStatus("Submitted");
			clc.getClaim(id).addComment("comment1");
			clc.getClaim(id).addComment("comment2");
			clc.getClaim(id).returnClaim("Sarah");
			
			
			//start intent
			Intent i = new Intent();
			i.putExtra("claim_id", id);
			setActivityIntent(i);
			return (SeeCommentsActivity) getActivity();
		}
	
}
