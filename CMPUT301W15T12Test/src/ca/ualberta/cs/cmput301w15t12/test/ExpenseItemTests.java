package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.util.Date;

import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import junit.framework.TestCase;


public class ExpenseItemTests extends TestCase {
	public ExpenseItemTests(){
		super();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	//tests for equals - name must be unique to an expense item
	public void testEquals(){
		Date date1 = new Date();
		Date date2 = new Date();
		BigDecimal amount1 = new BigDecimal(45.50);
		BigDecimal amount2 = new BigDecimal(50.00);
		ExpenseItem expenseItem1 = new ExpenseItem("name1","air fare","description1","USD",amount1,date1,false);
		ExpenseItem expenseItem2 = new ExpenseItem("name2","registration","description2","JPY",amount2,date2,false);
		boolean i = false;
		if (expenseItem1.getName().equals(expenseItem2.getName())){
			i=true;
		}
		assertFalse("Names are the same",i);
	}
	
	//US04.01.01 - expense item has date, category, description, amount, currency
	public void testExpenseItem(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","air fare","description","USD",amount,date,false);
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
		ExpenseItem expenseItem = new ExpenseItem("name","air fare","description","USD",amount,date,false);
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
		ExpenseItem expenseItem = new ExpenseItem("name","air fare","description","USD",amount,date,false);
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
		ExpenseItem expenseItem = new ExpenseItem("name","air fare","description","USD",amount,date,false);
		//passes if item has a flag
		assertTrue("Item does not have a flag",expenseItem.isFlag());
		
		//passes if item does not have a flag
		//assertFalse("Item does have a flag",expenseItem.getFlag());
	}
	
	//US04.05.01 - able to view an expense item its details - xml/activity
	
	
	//US04.06.01 - able to edit an expense item when allowed - setters
	public void testEditItem(){
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		ExpenseItem expenseItem = new ExpenseItem("name","air fare","description","USD",amount,date,false);
		assertNotNull("date not initialized",expenseItem.getDate());
		assertNotNull("category not initialized",expenseItem.getCategory());
		assertNotNull("description not initialized",expenseItem.getDescription());
		assertNotNull("amount not initialized",expenseItem.getAmount());
		assertNotNull("currency not initialized",expenseItem.getCurrency());
		assertNotNull("name not initialized",expenseItem.getName());
	}
	
	//US04.08.01 - minimal required navigation in user interface when entering an item - test manually
	
	//US06.01.01 - take and attach a photograph to item
	//US06.02.01 - view photograph
	//US06.03.01 - able to delete photograph (when edits are allowed)
	//US06.04.01 - image file < 65536 bytes
	
}
