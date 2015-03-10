package ca.ualberta.cs.cmput301w15t12;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable
{
	private static final long serialVersionUID = -654637474874722866L;
	public static ArrayList<User> users;
	protected transient ArrayList<Listener> listeners = null;
	
	public UserList() {
		users = new ArrayList<User>();
		listeners = new ArrayList<Listener>();
	}
	
	//returns true if UserName in users
	public boolean authenticateUser(String UserName){
		//TODO
		return false;
	}

	public void addUser(User user) throws AlreadyExistsException{
		for (int i = 0; i < users.size(); i++ ) {
			if (users.get(i).getUserName().equals(user.getUserName())) { 
				throw new AlreadyExistsException();
			}
		}
		users.add(user);	
		notifyListeners();
	}
	
	public int size() {
		return users.size();
	}
	
    public boolean contains(User user){
    	return users.contains(user);
    }
	
	public void remove(int i){
		users.remove(i);
		notifyListeners();
	}
	
	public void removeUser(User user){
		users.remove(user);
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
	
	public User get(int i) {
		return users.get(i);
	}
	
	private ArrayList<Listener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}

	public void addListener(Listener listener) {
		getListeners().add(listener);		
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
