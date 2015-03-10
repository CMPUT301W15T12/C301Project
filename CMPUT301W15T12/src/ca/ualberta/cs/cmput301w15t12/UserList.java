package ca.ualberta.cs.cmput301w15t12;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable
{
	private static final long serialVersionUID = -654637474874722866L;
	public static ArrayList<User> users;
	private ArrayList<Listener> listeners;
	
	public UserList() {
		users = new ArrayList<User>();
		this.listeners = new ArrayList<Listener>();
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
	
	public void size() {
		users.size();
	}
	
    public boolean contains(User user){
    	return users.contains(user);
    }
	
	public void addUser(User user) {
		users.add(user);
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

	public void removeListener(Listener listener) {
		getListeners().remove(listener);
	}

	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.update();
		}
	}
}
