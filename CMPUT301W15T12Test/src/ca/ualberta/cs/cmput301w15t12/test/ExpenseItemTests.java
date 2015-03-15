package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.util.Date;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.CheckBox;
import android.widget.TextView;

import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.ExpenseItemActivity;
import ca.ualberta.cs.cmput301w15t12.R;

public class ExpenseItemTests extends ActivityInstrumentationTestCase2<ExpenseItemActivity> {
	public ExpenseItemTests(){
		super(ExpenseItemActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	//US04.01.01 - expense item has date, category, description, amount, currency
	public void testExpenseItem(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","air fare","description","USD",amount,date);
		assertNotNull("date not initialized",expenseItem.getDate());
		assertNotNull("category not initialized",expenseItem.getCategory());
		assertNotNull("description not initialized",expenseItem.getDescription());
		assertNotNull("amount not initialized",expenseItem.getAmount());
		assertNotNull("currency not initialized",expenseItem.getCurrency());
		assertNotNull("name not initialized",expenseItem.getName());
	}
	
	//US04.02.01 - category is one of air fare, round transport, vehicle rental, 
	//private automobile, fuel, parking, registration, accommodation, meal, or supplies.
	public void testCategory(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","air fare","description","USD",amount,date);
		boolean i = false;
		if (expenseItem.getCategory().equals("air fare") || 
				expenseItem.getCategory().equals("round transport") || 
				expenseItem.getCategory().equals("vehicle rental") || 
				expenseItem.getCategory().equals("private automobile") || 
				expenseItem.getCategory().equals("fuel") || 
				expenseItem.getCategory().equals("parking") || 
				expenseItem.getCategory().equals("registration") || 
				expenseItem.getCategory().equals("accomodation") || 
				expenseItem.getCategory().equals("meal") || 
				expenseItem.getCategory().equals("supplies")) { 
			i=true;
		}
		assertTrue("Category is not one of the required categories",i);
	}
	
	//US04.03.01 - currency is one of CAD, USD, EUR, GBP, CHF, JPY, CNY
	public void testCurrency(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","air fare","description","USD",amount,date);
		boolean i = false;
		if (expenseItem.getCurrency().equals("CAD") || 
				expenseItem.getCurrency().equals("USD") || 
				expenseItem.getCurrency().equals("EUR") || 
				expenseItem.getCurrency().equals("GBP") || 
				expenseItem.getCurrency().equals("CHF") || 
				expenseItem.getCurrency().equals("JPY") || 
				expenseItem.getCurrency().equals("CNY")) { 
			i=true;
		}
		assertTrue("currency is not one of the required currencies",i);
	}
	
	//US04.04.01 - expense item can be flagged
	public void testFlag(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","air fare","description","USD",amount,date);
		
		//passes if item has a flag
		assertEquals("Item does have a flag", true, expenseItem.getFlag());
		
		expenseItem.setFlag(false);
		//passes if item does not have a flag
		assertEquals("Item does not have a flag", false, expenseItem.getFlag());
	}
	
	//US04.05.01 - able to view an expense item and its details
	public void testViewItem(){
		ExpenseItemActivity activity = startExpenseItemActivity();
		
		TextView nameView = (TextView) activity.findViewById(R.id.textItemName);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),nameView);
		TextView categoryView = (TextView) activity.findViewById(R.id.textCategory);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),categoryView);
		TextView descriptionView = (TextView) activity.findViewById(R.id.textItemDescription);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),descriptionView);
		TextView currencyView = (TextView) activity.findViewById(R.id.textItemCurrency);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),currencyView);
		TextView dateView = (TextView) activity.findViewById(R.id.textDate);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),dateView);
		CheckBox flagView = (CheckBox) activity.findViewById(R.id.checkBox1);
		ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(),flagView);	
	}
	
	//US04.06.01 - able to edit an expense item when allowed - setters
	public void testEditItem(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","air fare","description","USD",amount,date);
		Date date2 = new Date();
		BigDecimal amount2 = new BigDecimal(800.23);
		expenseItem.setName("name2");
		expenseItem.setCategory("parking");
		expenseItem.setDescription("description2");
		expenseItem.setCurrency("CAD");
		expenseItem.setAmount(amount2);
		expenseItem.setDate(date2);
		expenseItem.setFlag(true);
		
		assertEquals("date not initialized",date2,expenseItem.getDate());
		assertEquals("category not initialized","parking",expenseItem.getCategory());
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
