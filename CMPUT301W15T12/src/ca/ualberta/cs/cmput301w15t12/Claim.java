package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Date;

public class Claim {
	private String Claimant;
	private String Comment;
	private String Name;
	private Date startDate;
	private Date endDate;
	private String Description;
	private String Status;
	private ArrayList<String> destination;
	private ArrayList<String> approvers;
	private ExpenseList expenseItems;
	//still needs tags list
	
	public Claim(String name, Date startDate, Date endDate, String description, String Status){
		this.Name = name;
		this.Status = Status;
		this.startDate = startDate; 
		this.endDate = endDate;
		this.Description = description;
		this.destination = new ArrayList<String>();
		this.approvers = new ArrayList<String>();
		expenseItems = new ExpenseList();
	}

	public ExpenseList getExpenseItems() {
		return expenseItems;
	}

	public void setExpenseItems(ExpenseList expenseItems) {
		this.expenseItems = expenseItems;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}
	
	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		this.Comment = comment;
	}
	
	public String getDescription() {
		return Description;
	}

	public void setDescription(String desc) {
		this.Description = desc;
	}
	
	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		this.Status = status;
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
	
	public void addDestination (String Destination) {
		destination.add(Destination);
	}
	
	public void removeDestination(String Destination) {
		//TODO
	}
	
	public void containsDestination(String Destination) {
		//TODO
	}

	public ArrayList<String> getDestination() {
		return destination;
	}

	public void setDestination(ArrayList<String> destination) {
		this.destination = destination;
	}
	
	public void addApprover(String name) {
		approvers.add(name);
	}
	
	public void removeApprover(String name) {
		//TODO
	}
	
	public ArrayList<String> getApprovers() {
		return approvers;
	}

	public void setApprovers(ArrayList<String> apps) {
		this.approvers = apps;
	}

	public ExpenseList getExpenses(ExpenseList expenseItems) {
		// TODO Auto-generated method stub
		return expenseItems;
	}


}
