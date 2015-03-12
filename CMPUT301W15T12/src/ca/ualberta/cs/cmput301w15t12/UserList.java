package ca.ualberta.cs.cmput301w15t12;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable
{
	private static final long serialVersionUID = -654637474874722866L;
	public static ArrayList<User> users = new ArrayList<User>();
	protected transient ArrayList<Listener> listeners = null;
	
	public UserList() {
		listeners = new ArrayList<Listener>();
	}
	
	//returns true if UserName in users
	public boolean authenticateUser(String UserName){
		for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName() .equals(UserName)){
				return true;
			}
			return false;
	}

	public void addUser(String string) throws AlreadyExistsException{
		for (int i = 0; i < users.size(); i++ ) {
			if (users.get(i).getUserName().equals(string)) { 
				throw new AlreadyExistsException();
			}
		}
		users.add(new User(string));	
		notifyListeners();
	}
	
	public int size() {
		return users.size();
	}
	
    public boolean contains(String string){
    	return users.contains(string);
    }
	
	public void remove(int i){
		users.remove(i);
		notifyListeners();
	}
	
	public User getUser(String username) {
		for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName() .equals(username));
			return users.get(i);
		}
		throw new RuntimeException();
		}
	
	public void editUserName(String string1, String string2) {
		users.get(users.indexOf(string1)).setUserName(string2);
		
	}
	public void removeUser(User user){
		users.remove(user);
	}
	public ArrayList<User> getUsers() {
		return users;
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
