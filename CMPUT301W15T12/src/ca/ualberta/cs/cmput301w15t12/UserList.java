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
		}
			return false;
		
	}
	//add user by string
	public void addUser(User user) throws AlreadyExistsException{
		if (haveUser(user)) { 
			throw new AlreadyExistsException();
	}
		users.add(user);	
		notifyListeners();
	}
	
	public static boolean haveUser(User user){
		return users.contains(user);
	}
	
	public int size() {
		return users.size();
	}
	
    public static boolean contains(String string){
    	boolean contains = false;
    	for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName() .equals(string)){
				contains = true;
			}
    	}
    	return contains;
    }
	
	public void remove(int i){
		users.remove(i);
		notifyListeners();
	}
	
	public User getUser(String username) {
		for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName() .equals(username)){
			return users.get(i);
			}
		}
		throw new RuntimeException();
		}
	
	public void editUserName(String user, String string2) {
		for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName() .equals(user)){
				users.get(i).setUserName(string2);
			}
		}
		
	}
	public void removeUser(String user_name){
		for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName() .equals(user_name)){
			users.remove(users.get(i));
			}
			}
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
