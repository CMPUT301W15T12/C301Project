/**
 * UserList data model models the functionality for the UserList, including
 * add/removing users, and authenticating users.
 * 
 *   Copyright [2015] CMPUT301W15T12 https://github.com/CMPUT301W15T12
 *   licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *   
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   @author vanbelle
*/

package ca.ualberta.cs.cmput301w15t12;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable
{
	private static final long serialVersionUID = -654637474874722866L;
	public static ArrayList<User> users = new ArrayList<User>();
	protected transient ArrayList<Listener> listeners = null;
	
	/** 
	 * userlist constructor
	 */
	public UserList() {
		listeners = new ArrayList<Listener>();
	}
	
	/**
	 * 
	 * @param UserName
	 * @param Password
	 * @return true username and password correspond to a single user
	 */
	public boolean authenticateUser(String UserName, String Password){
		for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName().equals(UserName) && users.get(i).getPassword().equals(Password)){
				return true;
			}
		}
			return false;
		
	}
	/**
	 * adds a user to the user list
	 * @param user
	 * @throws AlreadyExistsException
	 */
	public void addUser(User user) throws AlreadyExistsException{
		if (haveUser(user)) { 
			throw new AlreadyExistsException();
	}
		users.add(user);	
		notifyListeners();
	}
	/**
	 * checks that userlist contains a certain user
	 */
	public static boolean haveUser(User user){
		return users.contains(user);
	}
	
	/**
	 * 
	 * @return the number of users in userlist
	 */
	public int size() {
		return users.size();
	}
	/**
	 * check that userlist contains a user by searching with username
	 * @param string
	 * @return
	 */
    public static boolean contains(String string){
    	for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName() .equals(string)){
				return true;
			}
    	}
    	return false;
    }
	/**
	 * remove a user from the userlist
	 * @param i
	 */
	public void remove(int i){
		users.remove(i);
		notifyListeners();
	}
	/**
	 * @param username
	 * @return the user corresponding to the particular username
	 */
	public User getUser(String username) {
		for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName() .equals(username)){
			return users.get(i);
			}
		}
		throw new RuntimeException();
		}
	/**
	 * allows the user to edit a username
	 * @param user
	 * @param string2
	 */
	public void editUserName(String user, String string2) {
		for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName() .equals(user)){
				users.get(i).setUserName(string2);
			}
		}
		
	}
	/**
	 * rmoves a user with a particular username
	 * @param user_name
	 */
	public void removeUser(String user_name){
		for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName() .equals(user_name)){
			users.remove(users.get(i));
			}
			}
		}
	/**
	 * @return all the users 
	 */
	public ArrayList<User> getUsers() {
		return users;
	}
	/** 
	 * get a user with a particular using an index
	 * @param i
	 * @return
	 */
	public User get(int i) {
		return users.get(i);
	}
	/** 
	 * get the listeners for the userlist
	 * @return
	 */
	private ArrayList<Listener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	/**
	 * add a listener to the listener list
	 * @param listener
	 */
	public void addListener(Listener listener) {
		getListeners().add(listener);		
	}
	/**
	 * remove a listener from the listener list
	 * @param listener
	 */
	public void removeListener(Listener listener) {
		getListeners().remove(listener);
	}
	/**
	 * notifies all listening listeners
	 */
	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.update();
		}
	}
	/** clear the userlist
	 * 
	 */
	public void clear()
	{

		users.clear();
		
	}

	
}
