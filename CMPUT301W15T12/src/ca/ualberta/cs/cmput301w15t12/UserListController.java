package ca.ualberta.cs.cmput301w15t12;

import java.io.IOException;

public class UserListController
{

	private static UserList userlist = null;

	static public UserList getUserList() {
		if (userlist == null) {
			try {
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
		}
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
		getUserList().addUser(string);
	}
	
	public static void removeUser(User user){
		getUserList().removeUser(user);
	}

	public static void editUserName(String string1, String string2) {
		getUserList().editUserName(string1, string2);
		
	}
}
