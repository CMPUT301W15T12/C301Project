package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Date;

public class Claim {
	private String name;
	private Date startDate;
	private Date endDate;
	private String destination;
	private String destinationDescription;
	//still needs tags list
	
	public Claim(String name, Date startDate, Date endDate){
		this.name = name;
		this.startDate = startDate; 
		this.endDate = endDate;
		//destination should be a list later on
		this.destination = destination;
		this.destinationDescription = destinationDescription;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStartDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEndDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList getDestination() {
		// TODO Auto-generated method stub
		return null;
	}
}
