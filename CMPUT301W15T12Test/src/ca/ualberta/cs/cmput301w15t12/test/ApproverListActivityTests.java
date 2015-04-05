package ca.ualberta.cs.cmput301w15t12.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.ApproverListActivity;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.ListView;


public class ApproverListActivityTests  extends ActivityInstrumentationTestCase2<ApproverListActivity>
{

	public SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	
	public ApproverListActivityTests()	{
		super(ApproverListActivity.class);
	}
	
	//US08.01.01
	//As an approver, I want to view a list of all the expense claims that were submitted for approval, which have their claim status as submitted, 
	//showing for each claim: the claimant name, the starting date of travel, the destination(s) of travel, the claim status, total currency amounts, 
	//and any approver name.
	
	//US08.02.01
	//As an approver, I want the list of submitted expense claims to be sorted by starting date of travel, in order from oldest to most recent, 
	//so that older claims are considered first.
	public void testApproverClaimList() throws ParseException, AlreadyExistsException{
		ApproverListActivity activity = startApproverItemActivity();
		
		ListView claimView = (ListView) activity.findViewById(R.id.listApproverClaimList);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),claimView);
		
		//check that the first item is the first entered and check that all the needed elements are there
		String s1 = (String) claimView.getAdapter().getItem(0);
		assertEquals(s1, "[01/01/1200] Freddie - Submitted");
		
		String s2 = (String) claimView.getAdapter().getItem(1);
		assertEquals(s2, "[01/01/1300] Freddie - Submitted");
		
		String s3 = (String) claimView.getAdapter().getItem(2);
		assertEquals(s3, "[01/01/1400] Freddie - Submitted");
	}

	private ApproverListActivity startApproverItemActivity() throws ParseException, AlreadyExistsException{
		User user  = new User("Freddie", "123");
		
		UserListController.getUserList().clear();
		UserListController.getUserList().addUser(user);
		
		Date d1 = df.parse("01/01/1200");
		Date d2 = df.parse("01/02/2134");
		
		ClaimListController clc = new ClaimListController();
		clc.clear();
		
		int id1 = clc.addClaim("name1", d1, d2,"desc",user);
		
		d1 = df.parse("01/01/1400");
		int id3 = clc.addClaim("name3", d1, d2,"desc",user);
		
		d1 = df.parse("01/01/1300");
		int id2 = clc.addClaim("name2", d1, d2,"desc",user);
		
		clc.getClaim(id1).setStatus("Submitted");
		clc.getClaim(id2).setStatus("Submitted");
		clc.getClaim(id3).setStatus("Submitted");
		
		//start intent
		Intent i = new Intent();
		i.putExtra("username", "Freddie");
		setActivityIntent(i);
		return (ApproverListActivity) getActivity();
	}


}
