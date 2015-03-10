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

	public void addUser(User user) throws AlreadyExistsException {
		getUserList().addUser(user);
	}
}
