/**
 * This  data model models all the functionality for the destinations, 
 * linking destinations and descriptions together.
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

public class Destination implements Serializable
{
	private static final long serialVersionUID = -7586457957887443944L;
	private String Destination;
	private String Description;
	
	

	public Destination(String name, String desc)
	{
		this.Destination = name;
		this.Description = desc;
	}

	//for printing the list of destinations
	public String toString()
	{
		String string = Destination+": "+Description;
		return string;
	}	

	public boolean equals(Destination d) {
		if (d == null) {
			return false;
		} else {
			return (getDescription().equals(d.getDescription()) && getDestination().equals(d.getDestination()));
		}
	}

	//getters and setters
	public String getDestination(){
		return Destination;
	}
	public void setDestination(String destination){
		Destination = destination;
	}	
	public String getDescription(){
		return Description;
	}
	public void setDescription(String description){
		Description = description;
	}
}
