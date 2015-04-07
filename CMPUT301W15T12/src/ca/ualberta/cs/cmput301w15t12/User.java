/**
 * User data model models the functionality for users including constructors, 
 * checking equality, and storing tags particular to that user.
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

import android.location.Location;



public class User implements Serializable
{
	private static final long serialVersionUID = -4565159790415717846L;
	private String UserName;
	private ArrayList<String> tagList;
	private String Password;
	private Location location;
	/**
	 * user constructor
	 * @param name
	 * @param Password
	 */
	public User(String name, String Password) {
		this.UserName = name;
		this.Password = Password;
		this.tagList = new ArrayList<String>();

	}
	/**
	 * checks that 2 users objects are equal
	 */
	public boolean equals(Object compareUser) {
		if (compareUser != null &&
				compareUser.getClass()==this.getClass()) {
			return this.equals((User)compareUser);
		} else {
			return false;
		}
	}
	/**
	 * checks that 2 users are equal
	 */
	public boolean equals(User compareUser) {
		if(compareUser==null) {
			return false;
		}
		return getUserName().equals(compareUser.getUserName());
	} 
	/**
	 * sets user password
	 * @param password
	 */
	public void setPassword(String password){
		this.Password = password;
	}
	/**
	 * get user password
	 * @return
	 */
	public String getPassword(){
		return Password;
	}
	
	/**
	 * check if the old password match, otherwise do not change it.
	 * @param old_pass
	 * @param password
	 */
	public void changePassword(String old_pass, String password){
		if (Password.equals(old_pass)){
			this.setPassword(password);
		}
	}
	
	/**
	 * add tag to user tag list
	 * @param tag
	 */
	public void addTag(String tag){
		tagList.add(tag);
	}
	/** 
	 * remove tag from user tag list
	 * @param tag
	 */
	public void removeTag(String tag) {
		if (tagList.indexOf(tag) > -1) {
			tagList.remove(tag);
		} else {
			throw new RuntimeException("Tag doesn't exist");
		}	
	}
	
	//getters and setters
	/**
	 * gets the user name
	 * @return
	 */
	public String getUserName() {
		return UserName;
	}
	/** sets the user name
	 * 
	 * @param Name
	 */

	public void setUserName(String Name) {
		this.UserName = Name;
	}
	/** gets the taglist 
	 * 
	 * @return
	 */
	public ArrayList<String> getTagList(){
		return tagList;
	}
	/** sets the taglist
	 * 
	 * @param tagList
	 */
	public void setTagList(ArrayList<String> tagList){
		this.tagList = tagList;
	}
	/**
	 * gets the home location
	 * @return
	 */
	public Location getLocation()
	{
		return location;
	}
	/** 
	 * sets the home location
	 * @param location
	 */
	public void setLocation(Location location)
	{
		this.location = location;
	}

}
