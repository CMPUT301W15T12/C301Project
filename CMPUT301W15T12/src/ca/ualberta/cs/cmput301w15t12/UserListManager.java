package ca.ualberta.cs.cmput301w15t12;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class UserListManager {
	static final String prefFile = "UserList";
	static final String clKey = "userList";
	Context context;

	static private UserListManager userListManager = null;

	public static void initManager(Context context) {
		if (userListManager == null) {
			if (context==null) {
				throw new RuntimeException("missing context for UserListManager");
			}
			userListManager = new UserListManager(context);
		}
	}

	public static UserListManager getManager() {
		if (userListManager==null) {
			throw new RuntimeException("Did not initialize Manager");
		}
		return userListManager;
	}

	public UserListManager(Context context) {
		this.context = context;
	}

	public UserList loadUserList() throws ClassNotFoundException, IOException {
		SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
		String userListData = settings.getString(clKey, "");	
		if (userListData.equals("")) {
			return new UserList();
		} else {	
			return userListFromString(userListData);
		}
	}

	static public UserList userListFromString(String userListData) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(userListData, Base64.DEFAULT));
		ObjectInputStream oi = new ObjectInputStream(bi);
		return (UserList)oi.readObject();
	}

	static public String userListToString(UserList cl) throws IOException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(cl);
		oo.close();
		byte bytes[] = bo.toByteArray();
		return Base64.encodeToString(bytes,Base64.DEFAULT);
	}

	public void saveUserList(UserList cl) throws IOException {
		SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(clKey, userListToString(cl));
		editor.commit();	
	}

}

