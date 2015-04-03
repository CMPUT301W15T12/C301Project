package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.util.Date;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.ExpenseItemActivity;

public class ExpenseItemTests extends ActivityInstrumentationTestCase2<ExpenseItemActivity> {
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
	public void testViewItem(){
	}
	
	//US04.06.01 - able to edit an expense item when allowed - setters
	public void testEditItem(){
		// Make new expense item
		Date date = new Date();
		Date date2 = new Date();
		BigDecimal amount = new BigDecimal(66.69);
		BigDecimal amount2 = new BigDecimal(666.9);
		ExpenseItem expenseItem = new ExpenseItem("name","Air Fare","description","USD", amount, date, false);

		// Edit expense item
		expenseItem.setName("name2");
		expenseItem.setCategory("Parking");
		expenseItem.setDescription("description2");
		expenseItem.setCurrency("CAD");
		expenseItem.setAmount(amount2);
		expenseItem.setDate(date2);
		expenseItem.setFlag(true);
		
		// Make sure expense item is updated correctly
		assertTrue("name not updated", expenseItem.getName() == "name2");
		assertTrue("date not updated", expenseItem.getDate() != date && expenseItem.getDate() == date2);
		assertTrue("category not updated", expenseItem.getCategory() == "Parking");
		assertTrue("description not updated", expenseItem.getDescription() == "description2");
		assertTrue("amount not updated", expenseItem.getAmount() != amount && expenseItem.getAmount() == amount2);
		assertTrue("currency not updated", expenseItem.getCurrency() == "CAD");
		assertTrue("flag not updated", expenseItem.getFlag());
	}
	
	//US04.08.01 - minimal required navigation in user interface when entering an item - test manually
}
