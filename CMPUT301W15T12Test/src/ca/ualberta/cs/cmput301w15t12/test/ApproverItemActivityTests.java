package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.ApproverItemActivity;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.TextView;

public class ApproverItemActivityTests extends ActivityInstrumentationTestCase2<ApproverItemActivity> {
	
	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

	public ApproverItemActivityTests(){
		super(ApproverItemActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		ExpenseItem.init(getInstrumentation().getTargetContext());
	}

	public void testitemUI() throws ParseException, AlreadyExistsException {
		ApproverItemActivity activity = startApproverItemActivity();

		TextView nameView = (TextView) activity.findViewById(R.id.textApproverItemName);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),nameView);
		assertTrue(nameView.getText().toString().equals("name"));
		
		TextView categoryView = (TextView) activity.findViewById(R.id.textApproverCategory);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),categoryView);
		assertTrue(categoryView.getText().toString().equals(""));
		
		TextView descriptionView = (TextView) activity.findViewById(R.id.textApproverItemDescription);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descriptionView);
		assertTrue(descriptionView.getText().toString().equals("description"));
		
		TextView currencyView = (TextView) activity.findViewById(R.id.textApproverItemCurrency);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),currencyView);
		assertTrue(currencyView.getText().toString().equals("12 USD"));
		
		TextView dateView = (TextView) activity.findViewById(R.id.textApproverDate);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),dateView);
		assertTrue(dateView.getText().toString().equals("01/02/1232"));
	}

	private ApproverItemActivity startApproverItemActivity() throws ParseException, AlreadyExistsException{
		Date d1 = df.parse("01/02/1232");
		Date d2 = df.parse("01/02/2134");
		User user  = new User("Leah", "123");
		UserListController.getUserList().clear();
		UserListController.getUserList().addUser(user);
		ClaimListController clc = new ClaimListController();
		clc.clear();
		int id = clc.addClaim("name", d1, d2,"desc",user);
		ExpenseItem item = new ExpenseItem("name", "", "description", "USD", new BigDecimal(12),d1,true);
		clc.getClaim(id).addItem(item);

		Intent i = new Intent();
		i.putExtra("claim_id", id);
		i.putExtra("item_index", 0);
		setActivityIntent(i);
		return (ApproverItemActivity) getActivity();
	}

}


