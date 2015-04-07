package ca.ualberta.cs.cmput301w15t12.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimActivity;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.Destination;
import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.MissingItemException;
import ca.ualberta.cs.cmput301w15t12.NotAllowedException;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;


public class ClaimTests extends ActivityInstrumentationTestCase2<ClaimActivity>
{
	public ClaimTests(){
		
		super(ClaimActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		ExpenseItem.init(getInstrumentation().getTargetContext());
	}
	
	// US07.01.01 - Submission of claim, allowing no edits thereafter
	public void testSubmitStatus() throws AlreadyExistsException {
		// Make new claim and add to claims list
		ClaimListController claimList = new ClaimListController();
		Date date1 = new Date();
		Date date2 = new Date();

		int id = claimList.addClaim("name1",  date1, date2, "description1", new User("Megan", "123"));

		// Set status to submitted
		claimList.getClaim(id).setStatus("Submitted");
		assertTrue("Status was not changed to submitted", claimList.getClaim(id).getStatus() == "Submitted");

		// Select the claim via controller
		claimList.getClaim(id);
		assertFalse("Not supposed to be able to edit submitted status", claimList.getClaim(id).editable());

	}


	// US07.03.01 - Returning a claim, allowing edits thereafter
	public void testReturnedStatus() throws AlreadyExistsException, NotAllowedException, MissingItemException {
		// Make new claim and add to claims list
		Date date1 = new Date();
		Date date2 = new Date();
		ClaimListController claimList = new ClaimListController();
		claimList.clear();
		UserListController.getUserList().clear();
		User c = new User("Megan", "123");
		User a = new User("Sarah", "123");
		UserListController.getUserList().addUser(a);
		UserListController.getUserList().addUser(c);
		int id = claimList.addClaim("name1",  date1, date2, "description1", c);
		Claim claim = claimList.getClaim(id);

		// Make sure claim is in submitted status
		claim.setStatus("Submitted");
		claim.addComment("comment");
		assertTrue("Status was not changed to submitted", claim.getStatus() == "Submitted");

		// Set status to returned
		claimList.getClaim(id).returnClaim("Sarah");
		assertTrue("Status was not changed to returned", claimList.getClaim(id).getStatus().equals("Returned"));

		// editing allowed
		assertTrue(claimList.getClaim(id).editable());
	}

	// US07.04.01 - Approving a claim, allowing no edits thereafter
	public void testApprovedStatus() throws AlreadyExistsException, MissingItemException, NotAllowedException {
		// Make new claim and add to claims list
		Date date1 = new Date();
		Date date2 = new Date();
		ClaimListController claimList = new ClaimListController();
		claimList.clear();
		UserListController.getUserList().clear();
		User c = new User("Megan", "123");
		User a = new User("Sarah", "123");
		UserListController.getUserList().addUser(a);
		UserListController.getUserList().addUser(c);
		int id = claimList.addClaim("name1",  date1, date2, "description1", c);
		Claim claim = claimList.getClaim(id);

		// Make sure claim is in submitted status
		claim.setStatus("Submitted");
		claim.addComment("comment");
		assertTrue("Status was not changed to submitted", claim.getStatus() == "Submitted");

		// Set status to approved
		claim.approveClaim("Sarah");
		assertTrue("Status was not changed to approved", claim.getStatus() == "Approved");

		assertFalse(claim.editable());
	}


	//US01.01.01 - expense claim that records my name, a starting date of travel, and an ending date of travel
	public void testCreatingClaim(){
		String name = "travel to Toronto";
		Date startDate = new GregorianCalendar().getTime(); //Default to now when no input is supplied
		Date endDate = new GregorianCalendar(2015,GregorianCalendar.MARCH,21).getTime();
		String description = "This is a fake claim";
		User user = new User("Jim", "123");
		int id = 0;

		Claim claim = new Claim(name,  startDate, endDate, description,user,id);
		assertNotNull("Name not initialized", claim.getName());
		assertEquals("Name does not match",name,claim.getName());
		assertNotNull("Start Date not initialized", claim.getStartDate());
		assertEquals("Start Date not match",startDate,claim.getStartDate());
		assertNotNull("End Date not initialized", claim.getEndDate());
		assertEquals("End Date not match",endDate,claim.getEndDate());
		assertNotNull("Destination not initialized", claim.getDestination());
	}

	//US01.02.01 - record one or more destinations of travel and an associated reason for travel to each destination
	public void testDestination() throws AlreadyExistsException{
		Location loc = new Location("dummyprovider");
		loc.setLatitude(20.3);
		loc.setLongitude(52.6);
		
		Date startDate = new GregorianCalendar().getTime(); //Default to now when no input is supplied
		Date endDate = new GregorianCalendar(2015,GregorianCalendar.MARCH,21).getTime();
		
		Claim claim = new Claim("name",  startDate, endDate, "description",new User("Sarah", "123"), 0);
		claim.addDestination(new Destination("name", "desc", loc));
		
		assertEquals(claim.getDestination().get(0).getDestination(), "name");
		assertEquals(claim.getDestination().get(0).getDescription(), "desc");
	}


	//US01.04.01 - edit an expense claim while changes are allowed (setters)
	public void testEditClaim(){
		String oldName = "travel to Toronto";
		String newName = "travel to Calgary";
		Date startDate = new GregorianCalendar().getTime(); //Default to now when no input is supplied
		Date endDate = new GregorianCalendar(2015,GregorianCalendar.MARCH,21).getTime();
		String description = "This is a fake claim";
		User user = new User("Jim", "123");
		int id = 0;

		Claim claim = new Claim(oldName,  startDate, endDate, description,user,id);
		claim.setName(newName);
		assertEquals("Name is not updated", newName, claim.getName());
		
		claim.setStatus("Submitted");
		assertFalse(claim.editable());
	}
	
	//US01.05.01
	//As a claimant, I want to delete an expense claim while changes are allowed.
	public void testdeleteClaim(){
		ClaimListController clc  = new ClaimListController();
		clc.clear();
		User user = new User("Jim", "123");
		int id = clc.addClaim("my claim",  new GregorianCalendar().getTime(), new GregorianCalendar().getTime(), "decription",user);
		assertEquals(clc.getClaim(id).getName(), "my claim");
		clc.removeClaim(id);
		assertTrue(clc.size() == 0);
	}

	public void testAddExpenseItemToClaim(){
		String name = "my expense";
		String category = "";
		String description = "description";
		String currency = "CAD";
		BigDecimal amount = new BigDecimal(10.0);
		Date date = new GregorianCalendar().getTime(); //Default to know when no input is supplied

		ExpenseItem expenseItem = new ExpenseItem(name,category, description, currency, amount, date, false);
		Claim claim = new Claim("my claim",  new GregorianCalendar().getTime(), new GregorianCalendar().getTime(), "decription",new User("Jim", "123"),0);

		claim.addItem(expenseItem);
		assertTrue("Wrong number of expenseItem returned",claim.getExpenseItems().size()==1);
	}

	public void testGetTotal(){
		String name = "my expense";
		String category = "";
		String description = "description";
		String currency = "CAD";
		BigDecimal amount = new BigDecimal(10);
		Date date = new GregorianCalendar().getTime(); //Default to now when no input is supplied

		ExpenseItem expenseItem = new ExpenseItem(name,category, description, currency, amount, date, false);
		ExpenseItem expenseItem2 = new ExpenseItem("TEST",category, description, currency, amount, date, false);

		Claim claim = new Claim("my claim",  new GregorianCalendar().getTime(), new GregorianCalendar().getTime(), "decription",new User("Jim", "123"),0);


		claim.addItem(expenseItem);
		claim.addItem(expenseItem2);

		assertTrue("Wrong total amount returned",claim.getTotal().size()==1);
		assertEquals("Wrong sum value",claim.getTotal().get(0),"20 CAD");

	}
}
