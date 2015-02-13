package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;


public class UserList
{
	public ArrayList<User> users;
	
	public UserList() {
		users = new ArrayList<User>();
	}

	public void add(User user) throws AlreadyExistsException
	{
		for (int i = 0; i < users.size(); i++ ) {
			if (users.get(i).equals(user)) {
				throw new AlreadyExistsException();
			}
		}
		users.add(user);		
	}

	public ArrayList<User> getUsers()
	{
		return users;
	}
	
	public boolean contains(String user)
	{
		for (int i = 0; i < users.size(); i++ ) {
			if (users.get(i).equals(user)) {
				return true;
			}
		}
		return false;		
	}
}
