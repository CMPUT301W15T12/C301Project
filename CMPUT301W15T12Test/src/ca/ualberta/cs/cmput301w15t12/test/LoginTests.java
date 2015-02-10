package ca.ualberta.cs.cmput301w15t12.test;

import java.util.ArrayList;

import junit.framework.TestCase;
//where userlist is the list of registered users
//already exists exception thrown when a new account is being created with an email address used previously 

public class LoginTests extends TestCase
{
	
	public void testTweetList() {
		//test constructor
	}
	
	public void addAccount(){
		boolean thrown = false;
		UserList users = new UserList();
		String email = "1234@ualbert.ca";
		users.add(email);
		assertTrue("User Added", users.getUsers()[0].equals(email));
		try {
			users.add(email);
		} catch (AlreadyExistsException e){
			thrown = true;
		}
		assertTrue("Cannot create two accounts with the same email", thrown);
	}
	
	public void login() {
		String email = "1234@ualbert.ca";
		UserList users = new UserList();
		users.add(email);
		assertTrue("Can retrieve accounts", users.getUsers().getUser(email).equals(email));
	}
}
