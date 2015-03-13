package ca.ualberta.cs.cmput301w15t12;

import java.io.Serializable;
import java.util.ArrayList;

public class Destination implements Serializable
{
	private static final long serialVersionUID = -7586457957887443944L;
	private String Destination;
	private String Description;
	

	//for printing the list of destinations
	public String toString()
	{
		String string = Destination+": "+Description+"\n";
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
