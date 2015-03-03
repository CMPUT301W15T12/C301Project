package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.ExpenseList;

import java.math.BigDecimal;
import java.util.Date;
import junit.framework.TestCase;


public class ExpenseListTests extends TestCase
{
	//TODO constructor, setup?
	//TODO tests for get/add/remove items
	//TODO tests for get/count list
	
	//US04.07.01 - able to delete an expense item when allowed
	public void testdeleteExpense() throws AlreadyExistsException {
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		Date date1 = new Date();
		Date date2 = new Date();
		ExpenseItem expense = new ExpenseItem("name","air fare","description","USD",amount,date,false);
		Claim claim = new Claim("Claim",date1,date2,"description","Submitted", "Leah");
		ExpenseList expenseList = claim.getExpenses(claim.getExpenseItems());
		
		expenseList.add(expense);
		assertTrue("Expense was not added to expense list", expenseList.size() > 0 && expenseList.contains(expense));
		
		expenseList.removeItem(expense.getName());
		assertTrue("Expense was not removed from expense list", expenseList.size() == 0 && !expenseList.contains(expense));
	}
	
	//US05.01.01 - list all the expense items for a claim, in order of entry
	
	public void testExpenseOrder() throws AlreadyExistsException{
		ExpenseList expenseList = new ExpenseList();
		Date date1 = new Date();
		BigDecimal amount1 = new BigDecimal(45.50);
		Date date2 = new Date();
		BigDecimal amount2 = new BigDecimal(600.34);
		Date date3 = new Date();
		BigDecimal amount3 = new BigDecimal(12.45);
		ExpenseItem expenseItem1 = new ExpenseItem("name1","air fare","description1","USD",amount1,date1,false);
		ExpenseItem expenseItem2 = new ExpenseItem("name2","ground transport","description2","USD",amount2,date2,false);
		ExpenseItem expenseItem3 = new ExpenseItem("name3","accomodation","description3","USD",amount3,date3,false);
		expenseList.add(expenseItem1);
		expenseList.add(expenseItem2);
		expenseList.add(expenseItem3);
		
		
		assertEquals("not in order of entry",expenseItem1,expenseList.getItems().get(0));
		assertEquals("not in order of entry",expenseItem2,expenseList.getItems().get(1));
		assertEquals("not in order of entry",expenseItem3,expenseList.getItems().get(2));
	}
}
