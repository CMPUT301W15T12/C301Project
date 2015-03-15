package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.ExpenseList;
import ca.ualberta.cs.cmput301w15t12.Listener;
import ca.ualberta.cs.cmput301w15t12.User;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.TestCase;


public class ExpenseListTests extends TestCase
{
	
	private boolean updated = false;
	
	//US04.07.01 - able to delete an expense item when allowed
	public void testdeleteExpense() throws AlreadyExistsException {
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		Date date1 = new Date();
		Date date2 = new Date();
		User user = new User("name");
		ExpenseItem expense = new ExpenseItem("name","air fare","description","USD",amount,date);
		Claim claim = new Claim("Claim",date1,date2,"description", user,2);
		
		claim.addItem(expense);
		assertTrue("Expense was not added to expense list", 
				claim.getExpenseItems().size() > 0 && claim.getExpenseItems().contains(expense));
		
		claim.removeItem(0);
		assertTrue("Expense was not removed from expense list", 
				claim.getExpenseItems().size() == 0 && !claim.getExpenseItems().contains(expense));
	}
	
	//US05.01.01 - list all the expense items for a claim, in order of entry
	public void testExpenseOrder() throws AlreadyExistsException{
		Date date5 = new Date();
		Date date4 = new Date();
		User user = new User("name");
		Claim claim = new Claim("Claim",date5,date4,"description", user,2);;
		Date date1 = new Date();
		BigDecimal amount1 = new BigDecimal(45.50);
		Date date2 = new Date();
		BigDecimal amount2 = new BigDecimal(600.34);
		Date date3 = new Date();
		BigDecimal amount3 = new BigDecimal(12.45);
		ExpenseItem expenseItem1 = new ExpenseItem("name1","air fare","description1","USD",amount1,date1);
		ExpenseItem expenseItem2 = new ExpenseItem("name2","ground transport","description2","USD",amount2,date2);
		ExpenseItem expenseItem3 = new ExpenseItem("name3","accomodation","description3","USD",amount3,date3);
		claim.addItem(expenseItem1);
		claim.addItem(expenseItem2);
		claim.addItem(expenseItem3);
		
		assertEquals("not in order of entry",expenseItem1,claim.getExpenseItems().get(0));
		assertEquals("not in order of entry",expenseItem2,claim.getExpenseItems().get(1));
		assertEquals("not in order of entry",expenseItem3,claim.getExpenseItems().get(2));
	}
	
	public void testExpenseList(){
		ExpenseList expenseList = new ExpenseList();
		assertNotNull("expenseItem list not initialized",expenseList.getList());
		assertNotNull("listener list not initialized",expenseList.getListeners());
	}
	
	public void testAddAndRemove() {
		ExpenseList expl = new ExpenseList();
		ExpenseItem exp = new ExpenseItem(null, null, null, null, null, null);
		
		assertTrue("Should be empty but wasn't", expl.size() == 0);
		
		expl.addExpenseItem(exp);
		
		assertTrue("Should contain exp but doesn't (size)", expl.size() == 1);
		assertTrue("Should contain exp but doesn't (contains)", expl.getList().contains(exp));
		
		expl.rmExpenseItem(exp);
		assertTrue("Shouldn't contain exp but it does (size)", expl.size() == 0);
		assertTrue("Shouldn't contain exp but it does (contains)", !expl.getList().contains(exp));
	}
	
	
	public void testListeners() {
		ExpenseList expl = new ExpenseList();
		
		assertTrue("Should not have listeners but it does", expl.getListeners().size() == 0);
		
		Listener lsr  = new Listener() {
			
			@Override
			public void update() {
				updated = true;
			}
		};
		
		expl.addListener(lsr);
		
		assertTrue("Should contain lsr but doesn't (size)", expl.getListeners().size() == 1);
		assertTrue("Should contain lsr but doesn't (contains)", expl.getListeners().contains(lsr));
		
		ExpenseItem exp = new ExpenseItem(null, null, null, null, null, null);
		expl.addExpenseItem(exp);
		
		assertTrue("Listener didn't fire", updated);
		
		expl.rmListener(lsr);
		assertTrue("Shouldn't contain lsr but it does (size)", expl.getListeners().size() == 0);
		assertTrue("Shouldn't contain exp but it does (contains)", !expl.getListeners().contains(lsr));
	}
	
}
