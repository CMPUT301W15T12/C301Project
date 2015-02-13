package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Date;

public class Claim {
	private String name;
	private Date startDate;
	private Date endDate;
	private ArrayList<String> destination;
	//still needs tags list
	
	public Claim(String name, Date startDate, Date endDate){
		this.name = name;
		this.startDate = startDate; 
		this.endDate = endDate;
		//destination should be a list later on
		this.destination = destination;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ArrayList<String> getDestination() {
		return destination;
	}

	public void setDestination(ArrayList<String> destination) {
		this.destination = destination;
	}


}
