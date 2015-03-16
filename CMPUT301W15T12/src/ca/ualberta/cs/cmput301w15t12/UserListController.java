/**
 * UserListController acts as an interface between the user functions and the
 * and the login/create new users activity.
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

import java.io.IOException;

public class UserListController
{

	private static UserList userlist = null;

	static public UserList getUserList() {
		if (userlist == null) {
			userlist = new UserList();
		}
			/*try {
				userlist = UserListManager.getManager().loadUserList();
				userlist.addListener(new Listener() {
					@Override
					public void update() {
						saveUserList();
					}
				});

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize UserList from UserListManager");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize UserList from UserListManager");
			}
		}*/
		return userlist;
	}

	static public void saveUserList() {
		try {
			UserListManager.getManager().saveUserList(getUserList());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not deserialize UserList from UserListManager");
		}
	}
	public boolean authenticateUser(String UserName){
		return getUserList().authenticateUser(UserName);
	}
	
	public void addUser(String string) throws AlreadyExistsException {
		getUserList().addUser(new User(string));
	}
	
	public static void removeUser(String user){
		getUserList().removeUser(user);
	}

	public static void editUserName(String user, String string2) {
		getUserList().editUserName(user, string2);
		
	}
	
	public boolean containsUser(User user){
		return UserList.haveUser(user);
	}
	
	public boolean contains(String user){
		return UserList.contains(user);
	}
}
