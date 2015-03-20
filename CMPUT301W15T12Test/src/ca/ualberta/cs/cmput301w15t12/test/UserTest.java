package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;
import junit.framework.TestCase;

public class UserTest extends TestCase {
	public UserTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testNewUser() throws AlreadyExistsException{
		UserListController usrc = new UserListController();
		usrc.addUser("user1");
		//assertTrue("Userlist not containing corrent number of users", usrc.contains("user1"));
		assertTrue("wrong number", UserListController.getUserList().size() > 0);
		assertTrue("not correct user", UserListController.getUserList().contains("user1"));
	}
	
	public void testDeleteUser() throws AlreadyExistsException{
		UserListController usrc = new UserListController();
		usrc.addUser("usertodelete");
		assertTrue("fail add", usrc.contains("usertodelete"));
		UserListController.removeUser("usertodelete");
		assertTrue("removed the only user",UserListController.getUserList().size() == 0);
		assertFalse("deleted the user.", usrc.contains("usertodelete"));
		assertTrue("incorrect userList size", UserListController.getUserList().size() <1);
	}
	
	public void testEditUserName() throws AlreadyExistsException{
		UserListController uscr = new UserListController();
		uscr.addUser("Test_user");
		assertTrue("username incorrect", UserListController.getUserList().getUser("Test_user").getUserName() == "Test_user");
		UserListController.editUserName("Test_user", "change=True");
		assertTrue("Username did not change", UserListController.getUserList().getUser("change=True").getUserName() == "change=True");
	}
	
	public void testPassword() throws AlreadyExistsException{
		UserListController usrc = new UserListController();
		usrc.addUserWithPass("test_pass", "123");
		assertTrue("password is good", UserListController.getUserList().getUser("test_pass").getPassword() == "123");
		UserListController.getUserList().getUser("test_pass").changePassword("123", "321");
		assertTrue("change password fail", UserListController.getUserList().getUser("test_pass").getPassword() == "321");

	}
}
