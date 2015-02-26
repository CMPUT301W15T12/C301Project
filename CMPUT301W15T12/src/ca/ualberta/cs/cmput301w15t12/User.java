package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;


public class User
{
	private String UserName;
	private ArrayList<String> tagList;
	
	public User(String name) {
		this.UserName = name;
		this.tagList = new ArrayList<String>();
		
	}
	
	//add/remove
	public void addTag(String tag){
		tagList.add(tag);
	}
	
	public ArrayList<String> getTags() {
		return tagList;
	}
	
	//getters and setters
	public String getUserName() {
		return UserName;
	}
	
	public void setUserName(String Name) {
		this.UserName = Name;
	}

	
	public ArrayList<String> getTagList(){
		return tagList;
	}

	public void setTagList(ArrayList<String> tagList){
		this.tagList = tagList;
	}

}
