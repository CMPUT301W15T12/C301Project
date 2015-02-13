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
	public void deleteExpense() throws AlreadyExistsException {
		Date date = new Date();
		BigDecimal amount = new BigDecimal(45.50);
		Date date1 = new Date();
		Date date2 = new Date();
		ExpenseItem expense = new ExpenseItem("name","air fare","description","USD",amount,date,false);
		Claim claim = new Claim("Claim",date1,date2,"description","Submitted");
		ExpenseList expenseList = claim.getExpenses(claim.getExpenseItems());
		
		expenseList.add(expense);
		assertTrue("Expense was not added to expense list", expenseList.size() > 0 && expenseList.contains(expense));
		
		expenseList.removeItem(expense.getName());
		assertTrue("Expense was not removed from expense list", expenseList.size() == 0 && !expenseList.contains(expense));
	}
}
