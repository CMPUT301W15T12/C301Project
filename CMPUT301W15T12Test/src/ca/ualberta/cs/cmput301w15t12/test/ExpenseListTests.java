package ca.ualberta.cs.cmput301w15t12.test;

import junit.framework.TestCase;


public class ExpenseListTests extends TestCase
{
	//TODO constructor, setup?
	//TODO tests for get/add/remove items
	//TODO tests for get/count list
	
	public void deleteExpense() {
		ExpenseItem expense = new ExpenseItem();
		ExpenseList expenseList = new ExpenseList();
		
		expenseList.add(expense);
		assertTrue("Expense was not added to expense list", expenseList.size() > 0 && expenseList.contains(expense));
		
		expenseList.remove(expense);
		assertTrue("Expense was not removed from expense list", expenseList.size() == 0 && !expenseList.contains(expense));
	}
}
