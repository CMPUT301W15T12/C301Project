package ca.ualberta.cs.cmput301w15t12;


public class User
{
	private ClaimList toClaim;
	private ClaimList toApprove;
	private String UserName;
	
	public User(String name) {
		toClaim = new ClaimList();
		toApprove = new ClaimList();
		this.UserName = name;
	}
	
	public String getUserName() {
		return UserName;
	}
	
	public void setUserName(String Name) {
		this.UserName = Name;
	}

	
	public ClaimList getToClaim(){
		return toClaim;
	}

	
	public void setToClaim(ClaimList toClaim){
		this.toClaim = toClaim;
	}

	
	public ClaimList getToApprove(){
		return toApprove;
	}

	
	public void setToApprove(ClaimList theirs){
		this.toApprove = theirs;
	}
	
 

}
