package ca.ualberta.cs.cmput301w15t12;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable
{
	private static final long serialVersionUID = -654637474874722866L;
	public static ArrayList<User> users;
	private static ArrayList<Listener> listeners;
	
	public UserList() {
		users = new ArrayList<User>();
	}
	
	//returns true if UserName in users
	public boolean authenticateUser(String UserName){
		//TODO
		return false;
	}

	public void add(User user) throws AlreadyExistsException{
		for (int i = 0; i < users.size(); i++ ) {
			if (users.get(i).equals(user)) { 
				throw new AlreadyExistsException();
			}
		}
		users.add(user);		
	}
	
	public void addUser(User user)
	{

		// TODO Auto-generated method stub
		
	}
	
	public void remove(int i){
		users.remove(i);
	}

	public ArrayList<User> getUsers() {
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
	
	public ArrayList<Listener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}

	public void addListener(Listener listener) {
		listeners.add(listener);		
	}

	public void rmListener(Listener listener) {
		listeners.remove(listener);
	}

	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.update();
		}
	}
}
