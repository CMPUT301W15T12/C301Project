package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;
import junit.framework.TestCase;

public class UserTest extends TestCase {
	User user1 = new User("Omar");
	public UserTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testNewUser() throws AlreadyExistsException{
		
		UserListController usrc = new UserListController();
		usrc.addUser(user1);
		assertTrue("Userlist not containing corrent number of users", UserListController.getUserList().size() == 1);
		assertTrue("wrong number", UserListController.getUserList().size() > 0);
	}
	
	public void testDeleteUser(){
		UserListController.removeUser(user1);
		assertTrue("removed the only user",UserListController.getUserList().size() == 0);
		assertTrue("incorrect userList size", UserListController.getUserList().size() <1);
	}
	
	public void testEditUserName() throws AlreadyExistsException{
		User user = new User("Test_user");
		UserListController usrc = new UserListController();
		usrc.addUser(user);
		assertTrue("username incorrect", UserListController.getUserList().getUser(user).getUserName() == "Test_user");
		UserListController.editUserName(user, "change=True");
		assertTrue("Username did not change", UserListController.getUserList().getUser(user).getUserName() == "change=True");
	}

}
