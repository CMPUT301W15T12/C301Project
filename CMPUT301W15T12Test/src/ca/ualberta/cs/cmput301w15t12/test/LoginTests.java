package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserList;

import junit.framework.TestCase;
//where userlist is the list of registered users
//already exists exception thrown when a new account is being created with an email address used previously 

public class LoginTests extends TestCase
{
	
	public void testTweetList() {
		UserList users = new UserList();
		assertNotNull("users not initialized", users);
	}
	
	//[DC01.02]
	public void addAccount() throws AlreadyExistsException{
		boolean thrown = false;
		UserList users = new UserList();
		User user = new User("Sarah");
		users.add(user);
		assertTrue("User Added", users.getUsers().get(0).equals(user));
		try {
			users.add(user);
		} catch (AlreadyExistsException e){
			thrown = true;
		}
		assertTrue("Cannot create two accounts with the same email", thrown);
	}
	
	//[DC01.01]
	public void login() throws AlreadyExistsException {
		User user = new User("Sarah");
		UserList users = new UserList();
		users.add(user);
		assertTrue("Can retrieve accounts", users.getUsers().contains(user));
	}
}
