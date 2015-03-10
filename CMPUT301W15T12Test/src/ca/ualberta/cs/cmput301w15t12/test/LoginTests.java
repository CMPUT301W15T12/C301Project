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
		User user = new User("Sarah");
		UserListController.getUserList().addUser(user);
		assertTrue("User Added", UserListController.getUserList().get(0).getUserName().equals(user));
		try {
			UserListController.getUserList().addUser(user);
		} catch (AlreadyExistsException e){
			thrown = true;
		}
		assertTrue("Cannot create two accounts with the same email", thrown);
	}
	
	//[DC01.01]
	public void testlogin() throws AlreadyExistsException {
		User user = new User("Sarah");
		assertTrue("Can retrieve accounts", UserListController.getUserList().contains(user));
	}
}
