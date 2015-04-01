package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.util.Date;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.ExpenseItemActivity;

public class ExpenseItemTests extends ActivityInstrumentationTestCase2<ExpenseItemActivity> {
	
//	ExpenseItemActivity activity;
//	Instrumentation instrumentation;
	
	public ExpenseItemTests(){
		super(ExpenseItemActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
//		instrumentation = getInstrumentation();
//		activity = getActivity();
	}
	
	//US04.01.01 - expense item has date, category, description, amount, currency
	public void testExpenseItem(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","Air Fare","description","USD",amount,date, false);
		assertNotNull("date not initialized",expenseItem.getDate());
		assertNotNull("category not initialized",expenseItem.getCategory());
		assertNotNull("description not initialized",expenseItem.getDescription());
		assertNotNull("amount not initialized",expenseItem.getAmount());
		assertNotNull("currency not initialized",expenseItem.getCurrency());
		assertNotNull("name not initialized",expenseItem.getName());
	}
	
	//US04.02.01 - category is one of air fare, round transport, vehicle rental, 
	//private automobile, fuel, parking, registration, accommodation, meal, or supplies.
	//Valid categories are stored in the expenseItem class in a static final array list
	public void testCategory(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","Air Fare","description","USD",amount,date, false);
		
		try {
			// Try no category (should still be valid)
			expenseItem.setCategory(null);
		} catch (Exception e) {
			// Should not throw exception with no category
			fail("Shouldn't have thrown an exception");
		}
		
		try {
			// Try all valid categories 
			for (String category : ExpenseItem.getCategories()) {
				expenseItem.setCategory(category);
			}
		} catch (Exception e){
			// Should not throw exception with valid categories
			fail("Shouldn't have thrown an exception");
		}
		
		try {
			// Try invalid category
			expenseItem.setCategory("Invalid");
			
			// Should throw an exception
			fail("Should have thrown an exception");
		} catch (Exception e) {
			// Working 
		}
	}
	
	//US04.03.01 - currency is one of CAD, USD, EUR, GBP, CHF, JPY, CNY
	//Valid currencies are stored in the expenseItem class in a static final array list
	public void testCurrency(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","Air Fare","description","CAD",amount,date, false);
		
		try {
			// Try no currency (should still be valid)
			expenseItem.setCurrency(null);
		} catch (Exception e) {
			// Should not throw exception with no currency
			fail("Shouldn't have thrown an exception");
		}
		
		try {
			// Try all valid currencies
			for (String currency : ExpenseItem.getCurrencies()) {
				expenseItem.setCurrency(currency);
			}
		} catch (Exception e){
			// Should not throw exception with valid currencies
			fail("Shouldn't have thrown an exception");
		}
		
		try {
			// Try an invalid currency
			expenseItem.setCurrency("Invalid");
			
			// Should throw an exception with invalid categories
			fail("Should have thrown an exception");
		} catch (Exception e) {
			// Working 
		}
	}
	
	//US04.04.01 - expense item can be flagged
	public void testFlag(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","Air Fare","description","USD",amount,date, false);
		
		expenseItem.setFlag(true);
		//passes if item has a flag
		assertEquals("Item does not have a flag", true, expenseItem.getFlag());
		
		expenseItem.setFlag(false);
		//passes if item does not have a flag
		assertEquals("Item does have a flag", false, expenseItem.getFlag());
	}
	
	//US04.05.01 - able to view an expense item and its details
	public void testViewItem(){
		//ExpenseItemActivity activity = startExpenseItemActivity();
		//final TextView decorView = (TextView) activity.getWindow().getDecorView();
		
//		TextView nameView = (TextView) activity.findViewById(R.id.textItemName);
//		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),nameView);
		
//		TextView categoryView = (TextView) activity.findViewById(R.id.textCategory);
//		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),categoryView);
//		TextView descriptionView = (TextView) activity.findViewById(R.id.textItemDescription);
//		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descriptionView);
//		TextView currencyView = (TextView) activity.findViewById(R.id.textItemCurrency);
//		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),currencyView);
//		TextView dateView = (TextView) activity.findViewById(R.id.textDate);
//		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),dateView);
//		CheckBox flagView = (CheckBox) activity.findViewById(R.id.checkBox1);
//		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),flagView);	
	}
	
	//US04.06.01 - able to edit an expense item when allowed - setters
	public void testEditItem(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","Air Fare","description","USD",amount,date, false);
		Date date2 = new Date();
		BigDecimal amount2 = new BigDecimal(800.23);
		expenseItem.setName("name2");
		expenseItem.setCategory("Parking");
		expenseItem.setDescription("description2");
		expenseItem.setCurrency("CAD");
		expenseItem.setAmount(amount2);
		expenseItem.setDate(date2);
		expenseItem.setFlag(true);
		
		assertEquals("date not initialized",date2,expenseItem.getDate());
		assertEquals("category not initialized","Parking",expenseItem.getCategory());
		assertEquals("description not initialized","description2",expenseItem.getDescription());
		assertEquals("amount not initialized",amount2,expenseItem.getAmount());
		assertEquals("currency not initialized","CAD",expenseItem.getCurrency());
		assertEquals("name not initialized","name2",expenseItem.getName());
	}

	private ExpenseItemActivity startExpenseItemActivity(){
		Intent intent = new Intent();
		setActivityIntent(intent);
		return (ExpenseItemActivity) getActivity();
	}
	
	
	//US04.08.01 - minimal required navigation in user interface when entering an item - test manually
}
