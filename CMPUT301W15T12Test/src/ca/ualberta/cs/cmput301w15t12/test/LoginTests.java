package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;

import junit.framework.TestCase;
//where userlist is the list of registered users
//already exists exception thrown when a new account is being created with an email address used previously 

public class LoginTests extends TestCase
{	
	//[DC01.02]
	public void testaddAccount() throws AlreadyExistsException{
		boolean thrown = false;
		UserListController.getUserList().clear();
		User user = new User("Sarah", "123");
		UserListController.getUserList().addUser(user);
		assertTrue("User Added", UserListController.getUserList().getUsers().contains(user));
		try {
			UserListController.getUserList().addUser(new User("Sarah", "123"));
		} catch (AlreadyExistsException e){
			thrown = true;
		}
		assertTrue("Cannot create two accounts with the same email", thrown);
	}
	
	//[DC01.01]
	public void testlogin() throws AlreadyExistsException {
		UserListController.getUserList().clear();
		User user = new User("Sarah","123");
		UserListController.getUserList().addUser(user);
		assertTrue("Can retrieve accounts", UserListController.getUserList().getUsers().contains(user));
	}
}
