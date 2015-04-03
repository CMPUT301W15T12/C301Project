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
	
	public UserList() {
		listeners = new ArrayList<Listener>();
	}
	
	//returns true if UserName in users
	public boolean authenticateUser(String UserName, String Password){
		for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName().equals(UserName) && users.get(i).getPassword().equals(Password)){
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
    	for (int i = 0; i < users.size(); ++i){
			if (users.get(i).getUserName() .equals(string)){
				return true;
			}
    	}
    	return false;
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

	public void clear()
	{

		users.clear();
		
	}

	
}
