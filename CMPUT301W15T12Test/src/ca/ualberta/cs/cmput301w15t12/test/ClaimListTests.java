
package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimList;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.ExpenseList;
import ca.ualberta.cs.cmput301w15t12.User;


public class ClaimListTests extends TestCase
{
	DateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
	//constructor
	public ClaimListTests(){
		super();
	}
	
	//setup
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testdeleteClaim() {
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim = new Claim("name1",  date1, date2, "description1", "Approved", "Megan");
		//ClaimList claimList = ClaimListController.getClaims();
		ClaimList claimList = new ClaimList();
		claimList.add(claim);
		assertTrue("Claim was not added to claims list", claimList.size() > 0 && claimList.contains(claim));
		
		claimList.remove(claim);
		assertTrue("Claim was not removed from claims list", claimList.size() == 0 && !claimList.contains(claim));
	}
	

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
	
	//US02.01.01 - list all my expense claims, showing for each claim: the starting date of travel, the destination(s) of travel, the claim status, tags, and total currency amounts
	public void testListClaims(){
		ClaimList claimList = new ClaimList();
		Date date1 = new Date();
		Date date2 = new Date();
		Claim claim = new Claim("name1",  date1, date2, "description1", "Approved", "Megan");
		claimList.add(claim);
	}
		
	//US02.02.01 - list of expense claims to be sorted by starting date of travel, in order from most recent to oldest
	public void testSorted() throws ParseException{
		String claimant = "Sarah";
		User user = new User(claimant);
		Date d1 = format.parse("01-02-1232");
		Date d2 = format.parse("01-02-2134");
		Claim claim1 = new Claim("c1", d1, d2, "Blah", "Submitted","Leah");
		Date d3 = format.parse("01-02-1233");
		Date d4 = format.parse("01-02-2134");
		Claim claim2 = new Claim("c1", d3, d4, "Blah", "Submitted","Leah");
		Date d5 = format.parse("01-02-1234");
		Date d6 = format.parse("01-02-2134");
		Claim claim3 = new Claim("c1", d5, d6, "Blah", "Submitted","Leah");
		ClaimList list = new ClaimList();
		list.add(claim3);
		list.add(claim1);
		list.add(claim2);
		user.setToClaim(list);
		user.getToClaim().sort();
		assertTrue("first item is claim 1",user.getToApprove().getClaims().get(0).equals(claim1));
		assertTrue("first item is claim 2",user.getToApprove().getClaims().get(1).equals(claim2));
		assertTrue("first item is claim 3",user.getToApprove().getClaims().get(2).equals(claim3));	
	}
}

