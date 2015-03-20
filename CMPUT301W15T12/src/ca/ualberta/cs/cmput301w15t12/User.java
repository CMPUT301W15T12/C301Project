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



public class User implements Serializable
{
	private static final long serialVersionUID = -4565159790415717846L;
	private String UserName;
	private ArrayList<String> tagList;
	private String Password;

	public User(String name, String Password) {
		this.UserName = name;
		this.Password = Password;
		this.tagList = new ArrayList<String>();

	}

	public boolean equals(Object compareUser) {
		if (compareUser != null &&
				compareUser.getClass()==this.getClass()) {
			return this.equals((User)compareUser);
		} else {
			return false;
		}
	}
	
	public boolean equals(User compareUser) {
		if(compareUser==null) {
			return false;
		}
		return getUserName().equals(compareUser.getUserName());
	} 
	
	public boolean authenticate(String password) {
		//TODO
		return false;
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
	public String getPassword()
	{
	
		return Password;
	}

	
	public void setPassword(String password)
	{
	
		Password = password;
	}

}
