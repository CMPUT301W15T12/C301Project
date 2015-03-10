package ca.ualberta.cs.cmput301w15t12;

import java.io.Serializable;
import java.util.ArrayList;



public class User implements Serializable
{
	private static final long serialVersionUID = -4565159790415717846L;
	private String UserName;
	private ArrayList<String> tagList;
	
	public User(String name) {
		this.UserName = name;
		this.tagList = new ArrayList<String>();
		
	}
	
	public boolean equals(){
		//TODO
		return false;
	}
	
	public boolean authenticate(String password) {
		//TODO
		return false;
	}
	
	//TODO equals function
	
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
