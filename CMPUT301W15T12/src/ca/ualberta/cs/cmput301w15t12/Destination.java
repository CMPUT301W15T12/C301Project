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

import android.location.Location;

public class Destination implements Serializable
{
	private static final long serialVersionUID = -7586457957887443944L;
	private String Destination;
	private String Description;
	private Location location;
	
	/**
	 * creates a new destination from the provided input values
	 * @param name
	 * @param desc
	 * @param location
	 */
	public Destination(String name, String desc,Location location)
	{
		this.Destination = name;
		this.Description = desc;
		this.location = location;
	}

	//for printing the list of destinations
	/**
	 * prints the destination name and description together as a string
	 * @return destination string
	 */
	public String toString()
	{
		String string = Destination+": "+Description;
		return string;
	}	
	/**
	 * checks if 2 destinations are equals
	 * @param d
	 * @return true if they are, otherwise false
	 */
	public boolean equals(Destination d) {
		if (d == null) {
			return false;
		} else {
			return (getDescription().equals(d.getDescription()) && getDestination().equals(d.getDestination()));
		}
	}

	//getters and setters
	/**
	 * 
	 * @return destination name
	 */
	public String getDestination(){
		return Destination;
	}
	/**
	 * sets destination name
	 * @param destination
	 */
	public void setDestination(String destination){
		Destination = destination;
	}	
	/**
	 * 
	 * @return destination description
	 */
	public String getDescription(){
		return Description;
	}
	/**
	 * sets destination description
	 * @param description
	 */
	public void setDescription(String description){
		Description = description;
	}
	/**
	 * @return the gps location for a destination
	 */
	public Location getLocation()
	{
	
		return location;
	}

	/**
	 *  sets the gps location for a destination
	 * @param location
	 */
	public void setLocation(Location location)
	{
	
		this.location = location;
	}
}
