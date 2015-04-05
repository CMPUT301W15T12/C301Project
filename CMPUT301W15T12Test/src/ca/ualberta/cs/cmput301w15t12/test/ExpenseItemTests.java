package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.CheckBox;
import android.widget.TextView;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.ExpenseItemActivity;
import ca.ualberta.cs.cmput301w15t12.R;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;

public class ExpenseItemTests extends ActivityInstrumentationTestCase2<ExpenseItemActivity> {
	
	public SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	
	public ExpenseItemTests(){
		super(ExpenseItemActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	//US04.01.01 - expense item has date, category, description, amount, currency
	public void testExpenseItem(){
		// Make new expense item
		ExpenseItem expenseItem = new ExpenseItem("name","Air Fare","description","USD", new BigDecimal(66.69), new Date(), false);

		// Make sure everything is initialized properly
		assertNotNull("date not initialized", expenseItem.getDate());
		assertNotNull("category not initialized", expenseItem.getCategory());
		assertNotNull("description not initialized", expenseItem.getDescription());
		assertNotNull("amount not initialized", expenseItem.getAmount());
		assertNotNull("currency not initialized", expenseItem.getCurrency());
		assertNotNull("name not initialized", expenseItem.getName());
	}

	//US04.02.01 - category is one of air fare, round transport, vehicle rental, 
	// private automobile, fuel, parking, registration, accommodation, meal, or supplies.
	// Valid categories are stored in the expenseItem class in a static final array list
	public void testCategory(){
		// Make new expense item
		ExpenseItem expenseItem = new ExpenseItem("name","Air Fare","description","USD", new BigDecimal(66.69), new Date(), false);

		try {
			// Try setting no category (should still be valid)
			expenseItem.setCategory(null);
			expenseItem.setCategory("");
		} catch (Exception e) {
			// Exception thrown when setting no category
			fail("Shouldn't have thrown an exception with no category");
		}

		try {
			// Try setting all valid categories 
			for (String category : ExpenseItem.getCategories()) {
				expenseItem.setCategory(category);
			}
		} catch (Exception e){
			// Exception thrown when setting valid category
			fail("Shouldn't have thrown an exception with valid categories");
		}

		try {
			// Try setting invalid category
			expenseItem.setCategory("Invalid");

			// Exception not thrown when setting invalid category
			fail("Should have thrown an exception with an invalid category");
		} catch (Exception e) {
			// Exception thrown when setting invalid category
		}
	}

	//US04.03.01 - currency is one of CAD, USD, EUR, GBP, CHF, JPY, CNY
	//Valid currencies are stored in the expenseItem class in a static final array list
	public void testCurrency(){
		// Make new expense item
		ExpenseItem expenseItem = new ExpenseItem("name","Air Fare","description","USD", new BigDecimal(66.69), new Date(), false);

		try {
			// Try setting no currency (should still be valid)
			expenseItem.setCurrency(null);
			expenseItem.setCurrency("");
		} catch (Exception e) {
			// Exception thrown when setting no currency
			fail("Shouldn't have thrown an exception with no currency");
		}

		try {
			// Try setting all valid currencies
			for (String currency : ExpenseItem.getCurrencies()) {
				expenseItem.setCurrency(currency);
			}
		} catch (Exception e){
			// Exception thrown when setting valid currency
			fail("Shouldn't have thrown an exception with valid currencies");
		}

		try {
			// Try an invalid currency
			expenseItem.setCurrency("Invalid");

			// Exception not thrown when setting invalid currency
			fail("Should have thrown an exception with an invalid currency");
		} catch (Exception e) {
			// Exception thrown when setting invalid category
		}
	}

	//US04.04.01 - expense item can be flagged
	public void testFlag(){
		// Make new expense item
		ExpenseItem expenseItem = new ExpenseItem("name","Air Fare","description","USD", new BigDecimal(66.69), new Date(), false);

		// Toggle flag
		expenseItem.setFlag(true);
		assertTrue("Item does not have a flag when it should", expenseItem.getFlag());
		expenseItem.setFlag(false);
		assertFalse("Item does have a flag when it shouldn't", expenseItem.getFlag());
	}

	//US04.05.01 - able to view an expense item and its details
	public void testViewItem() throws ParseException, AlreadyExistsException{
		ExpenseItemActivity activity = getExpenseItemActivity();
		
		TextView nameView = (TextView) activity.findViewById(R.id.textItemName);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),nameView);
		assertTrue(nameView.getText().toString().equals("name"));
		
		TextView dateView = (TextView) activity.findViewById(R.id.textDate);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),dateView);
		assertEquals(dateView.getText().toString(),"01/01/1232");
		
		TextView descriptionView = (TextView) activity.findViewById(R.id.textCategory);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descriptionView);
		assertEquals(descriptionView.getText().toString(),"");
		
		TextView descView = (TextView) activity.findViewById(R.id.textItemDescription);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descView);
		assertEquals(descView.getText().toString(),"description");
		
		TextView currencyView = (TextView) activity.findViewById(R.id.textItemCurrency);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),currencyView);
		assertEquals(currencyView.getText().toString(),"12 USD");
		
		CheckBox boxView = (CheckBox) activity.findViewById(R.id.checkBoxToFlag);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),boxView);
		assertEquals(boxView.isChecked(),true);
	}

	//US04.06.01 - able to edit an expense item when allowed
	public void testEditItem() throws AlreadyExistsException{
		// Make new expense item
		Date date = new Date();
		Date date2 = new Date();
		BigDecimal amount = new BigDecimal(66.69);
		BigDecimal amount2 = new BigDecimal(666.9);
		ClaimListController clc = new ClaimListController();
		User user = new User("Freddie", "123");
		UserListController.getUserList().clear();
		UserListController.getUserList().addUser(user);
		int id = clc.addClaim("name", date, date2, "description",user);
		clc.getClaim(id).addItem(new ExpenseItem("name","Air Fare","description","USD", amount, date, false));

		// Edit expense item when allowed
		ExpenseItem item = clc.getClaim(id).getExpenseItems().get(0);
		item.setName("name2");
		item.setCategory("Parking");
		item.setDescription("description2");
		item.setCurrency("CAD");
		item.setAmount(amount2);
		item.setDate(date2);
		item.setFlag(true);

		// Make sure expense item is updated correctly when allowed
		assertTrue("name not updated", item.getName() == "name2");
		assertTrue("date not updated", item.getDate() != date && item.getDate() == date2);
		assertTrue("category not updated", item.getCategory() == "Parking");
		assertTrue("description not updated", item.getDescription() == "description2");
		assertTrue("amount not updated", item.getAmount() != amount && item.getAmount() == amount2);
		assertTrue("currency not updated", item.getCurrency() == "CAD");
		assertTrue("flag not updated", item.getFlag());

		//before editing, call this function to check if allowed
		clc.getClaim(id).setStatus("Submitted");
		assertFalse(clc.getClaim(id).editable());		
	}

	//US04.08.01 - minimal required navigation in user interface when entering an item - test manually

	private ExpenseItemActivity getExpenseItemActivity() throws ParseException, AlreadyExistsException{
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
		i.putExtra("item_index", 0);
		setActivityIntent(i);

		return (ExpenseItemActivity) getActivity();
	}
}
