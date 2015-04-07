package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.ApproverClaimActivity;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ApproverActivityTests extends ActivityInstrumentationTestCase2<ApproverClaimActivity> {
	
	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	
	public ApproverActivityTests(){
		super(ApproverClaimActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		ExpenseItem.init(getInstrumentation().getTargetContext());
	}
	
	//US08.03.01
	//As an approver, I want to view all the details of a submitted expense claim.
	
	//US08.04.01
	//As an approver, I want to list all the expense items for a submitted claim, in order of 
	//entry, showing for each expense item: the date the expense was incurred, the category, the textual description, 
	//amount spent, unit of currency, and whether there is a photographic receipt.
	public void testclaimUI() throws ParseException, AlreadyExistsException {
		ApproverClaimActivity activity = startApproverActivity();
		TextView nameView = (TextView) activity.findViewById(R.id.textApproverClaimName);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),nameView);
		assertTrue(nameView.getText().toString().equals("name - In Progress"));
		
		TextView dateView = (TextView) activity.findViewById(R.id.textApproverStarttoEndDate);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),dateView);
		assertEquals(dateView.getText().toString(),"01/01/1232 - 01/01/2134");
		
		TextView descriptionView = (TextView) activity.findViewById(R.id.textApproverClaimDescription);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descriptionView);
		assertTrue(descriptionView.getText().toString().equals("desc"));
		
		TextView totalSumView = (TextView) activity.findViewById(R.id.textViewApproverTotal);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),totalSumView);
		assertEquals(totalSumView.getText().toString(),"36 USD");
		
		ListView itemView = (ListView) activity.findViewById(R.id.listApproverlistExpenseItems);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),itemView);
		
		//check that the first item is the first entered
		View view1 = itemView.getAdapter().getView(0, null, null);
		TextView tv1 = (TextView) view1.findViewById(R.id.txt);	
		ImageView i1 = (ImageView) view1.findViewById(R.id.img);
		assertEquals(i1.getTag(), R.drawable.none);
		assertEquals(tv1.getText().toString(), "[01/01/1232] name\n - 12 USD\ndescription");
		//"["+getStringDate()+"] "+name+"\n"+category+" - "+Amount+" "+Currency+"\n"+description;
		
		//check that the second item is the second entered
		View view2 = itemView.getAdapter().getView(1, null, null);
		TextView tv2 = (TextView) view2.findViewById(R.id.txt);	
		ImageView i2 = (ImageView) view2.findViewById(R.id.img);
		assertEquals(i2.getTag(), R.drawable.receipt);
		assertEquals(tv2.getText().toString(), "[01/01/1232] name2\n - 12 USD\ndescription");
		
		//check that the third item is the third entered
		View view3 = itemView.getAdapter().getView(2, null, null);
		TextView tv3 = (TextView) view3.findViewById(R.id.txt);	
		ImageView i3 = (ImageView) view3.findViewById(R.id.img);
		assertEquals(i3.getTag(), R.drawable.none);
		assertEquals(tv3.getText().toString(), "[01/01/1232] name3\n - 12 USD\ndescription");
	}
	
	private ApproverClaimActivity startApproverActivity() throws ParseException, AlreadyExistsException{
		Date d1 = df.parse("01/01/1232");
		Date d2 = df.parse("01/01/2134");
		
		User user  = new User("Leah", "123");
		UserListController.getUserList().clear();
		
		UserListController.getUserList().addUser(user);
		ClaimListController clc = new ClaimListController();
		clc.clear();
		
		int id = clc.addClaim("name", d1, d2,"desc",user);
		ExpenseItem item = new ExpenseItem("name", "", "description", "USD", new BigDecimal(12),d1,true);
		ExpenseItem item2 = new ExpenseItem("name2", "", "description", "USD", new BigDecimal(12),d1,true);
		ExpenseItem item3 = new ExpenseItem("name3", "", "description", "USD", new BigDecimal(12),d1,true);
		
		Claim claim = clc.getClaim(id);
		claim.addItem(item);
		
		//pretend item 2 has a photo
		item2.setReceipt(true);
		
		claim.addItem(item2);
		claim.addItem(item3);

		Intent i = new Intent();
		i.putExtra("claim_id", id);
		i.putExtra("item_index", 0);
		setActivityIntent(i);
		return (ApproverClaimActivity) getActivity();
	}
}
