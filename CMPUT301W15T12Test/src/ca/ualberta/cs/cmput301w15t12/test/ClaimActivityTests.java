package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ca.ualberta.cs.cmput301w15t12.AddClaimActivity;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimActivity;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.MissingItemException;
import ca.ualberta.cs.cmput301w15t12.NotAllowedException;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;

public class ClaimActivityTests extends
ActivityInstrumentationTestCase2<ClaimActivity> {
	
	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

	public ClaimActivityTests() {
		super(ClaimActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		ExpenseItem.init(getInstrumentation().getTargetContext());
	}
	
	//US01.03.01 - As a claimant, I want to view an expense claim and its details.
	public void testViewItem() throws ParseException, AlreadyExistsException{
		ClaimActivity activity = getExpenseItemActivity();
		
		TextView nameView = (TextView) activity.findViewById(R.id.textClaimName);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),nameView);
		assertEquals(nameView.getText().toString(),"name - In Progress");
		
		TextView dateView = (TextView) activity.findViewById(R.id.textStarttoEndDate);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),dateView);
		assertEquals(dateView.getText().toString(),"01/01/1232 - 01/01/2134");
		
		TextView descriptionView = (TextView) activity.findViewById(R.id.textClaimDescription);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descriptionView);
		assertEquals(descriptionView.getText().toString(),"desc");
		
		TextView descView = (TextView) activity.findViewById(R.id.textClaimDestinations);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descView);
		assertEquals(descView.getText().toString(),"");
		
		TextView currencyView = (TextView) activity.findViewById(R.id.listTotalSum);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),currencyView);
		assertEquals(currencyView.getText().toString(),"12 USD");
	}
	
	private ClaimActivity getExpenseItemActivity() throws ParseException, AlreadyExistsException{
		Date d1 = df.parse("01/01/1232");
		Date d2 = df.parse("01/01/2134");

		User user  = new User("Leah", "123");
		UserListController.getUserList().clear();

		UserListController.getUserList().addUser(user);
		ClaimListController clc = new ClaimListController();
		clc.clear();

		int id = clc.addClaim("name", d1, d2,"desc",user);
		ExpenseItem item = new ExpenseItem("name", "", "description", "USD", new BigDecimal(12),d1,true);

		Claim claim = clc.getClaim(id);
		claim.addItem(item);

		Intent i = new Intent();
		i.putExtra("claim_id", id);
		i.putExtra("username","Leah");
		setActivityIntent(i);

		return (ClaimActivity) getActivity();
	}

}

