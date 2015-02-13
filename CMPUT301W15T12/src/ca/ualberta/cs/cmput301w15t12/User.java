package ca.ualberta.cs.cmput301w15t12;


public class User
{
	private ClaimList Yours;
	private ClaimList Theirs;
	private String UserName;
	
	public User(String name) {
		Yours = new ClaimList();
		Theirs = new ClaimList();
		this.UserName = name;
	}
	
	public String getUserName() {
		return "";
	}

}
