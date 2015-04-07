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

public class UserListController
{

	private static UserList userlist = null;
	/**
	 * userlist controller constructor
	 * @return
	 */
	static public UserList getUserList() {
		if (userlist == null) {
			userlist = new UserList();
		}
		return userlist;
	}
	/**check that a username and password correspond to a particular user
	 * 
	 * @param UserName
	 * @param Password
	 * @return
	 */
	public boolean authenticateUser(String UserName, String Password){
		return getUserList().authenticateUser(UserName, Password);
	}

	/**
	 * add a user to the userlist
	 * @param name
	 * @param pass
	 * @throws AlreadyExistsException
	 */
	public void addUserWithPass(String name, String pass) throws AlreadyExistsException{
		getUserList().addUser(new User(name, pass));
	}
	/**
	 * remove a user from the userlist
	 * @param user
	 */
	public static void removeUser(String user){
		getUserList().removeUser(user);
	}
	/**
	 * edit the username of a particular user
	 * @param user
	 * @param string2
	 */
	public static void editUserName(String user, String string2) {
		getUserList().editUserName(user, string2);
		
	}
	/**
	 * check that the userlist contains a certain user
	 * @param user
	 * @return
	 */
	public boolean containsUser(User user){
		return UserList.haveUser(user);
	}
	
	/**
	 * check that the userlist contains a user with a particular username
	 * @param user
	 * @return
	 */
	public boolean contains(String user){
		return UserList.contains(user);
	}
}
