package ca.ualberta.cs.cmput301w15t12;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Claim {
	private User Claimant;
	private User Approver;
	
	private String Comment;
	
	private String Name;
	private Date startDate;
	private Date endDate;
	private String Description;
	private String Status;
	
	private ArrayList<Destination> destinations;
	private ArrayList<String> approvers;
	private ArrayList<String> total;
	private ArrayList<ExpenseItem> expenseItems;
	private ArrayList<String> tagList;
	protected transient ArrayList<Listener> listeners = null;
	
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	
	public Claim(String name, Date startDate, Date endDate, String description, String Status, User Claimant){
		this.Name = name;
		this.Claimant = Claimant;
		this.Status = Status;
		this.startDate = startDate; 
		this.endDate = endDate;
		this.Description = description;
		this.destinations = new ArrayList<Destination>();
		this.approvers = new ArrayList<String>();
		this.total = new ArrayList<String>();
		this.expenseItems = new ArrayList<ExpenseItem>();
		this.tagList = new ArrayList<String>();
		this.listeners = new ArrayList<Listener>();
		this.Approver = null;
	}
	//TODO generate total fcn
	
	//All the toString functions
	public String toStringApproverList() {
		String ds= df.format(startDate);
		String block = "["+ds+"] "+Claimant+" - "+Status+"\n"+toStringList(total)+"\n"+destinationsToString()+"\n"+toStringList(approvers);
		return block;
	}
	
	public String toStringClaimantList() {
		String ds = df.format(startDate);
		String block = "["+ds+"] "+Name+" - "+Status+"\n"+toStringList(total)+"\n"+destinationsToString()+"\n"+toStringList(tagList);
		return block;
	}
	public String destinationsToString() {
		String dests = "";
		for (int i = 0; i < destinations.size(); i++) {
			dests += destinations.get(i).toFullString()+"\n";
		}
		return dests;
	}
	public String toStringList(ArrayList<String> list) {
		String string = "";
		for (int i = 0; i < list.size(); i++) {
			string += list.get(i)+"\n";
		}
		return string;
	}
	
	public String toEmail() {
		String ds = df.format(startDate);
		String de = df.format(endDate);
		String string = Name+"\n";
		string += Status+"\n"+Description+"\n";
		string += ds+" - "+de+"\n";
		string += "Destinations:"+destinationsToString()+"\n";
		string += "Total"+toStringList(total)+"\n";
		string += "Items:";
		for (int i = 0; i < expenseItems.size(); i++) {
			string += expenseItems.get(i).toEmail()+"\n";
		}
		return string;
	}
	// end toString functions
	
	//all the adds/removes/contains for the lists
	public void removeItem(int pos) {
		expenseItems.remove(pos);
		notifyListeners();
	}

	public void addItem(ExpenseItem Item) {
		//TODO check for duplicates
		expenseItems.add(Item);
		notifyListeners();
	}
	public boolean containsItem(ExpenseItem Item){
		return expenseItems.contains(Item);	
	}
	public void addApprover(String name) {
		//TODO check for duplicates
		approvers.add(name);
	}
	public void removeApprover(String name) {
		//TODO
	}
	public void containsApprover(User Approver) {
		//TODO
	}
	public void addDestination (Destination destination) {
		//TODO check for duplicates
		destinations.add(destination);
	}
	public void removeDestination(String Destination) {
		//TODO
	}
	public void containsDestination(String Destination) {
		//TODO
	}
	public void removeTag(int pos) {
		tagList.remove(pos);
	}

	public void addTag(String tag) {
		//TODO check for duplicates
		tagList.add(tag);
	}
	public boolean containsTag(String tag){
		return tagList.contains(tag);	
	}
	//end add/remove/contains
	
	//All the getters and setters
	public DateFormat getDf() {
		return df;
	}
	public void setDf(DateFormat df) {
		this.df = df;
	}
	public ArrayList<ExpenseItem> getExpenseItems() {
		return expenseItems;
	}
	public void setExpenseItems(ArrayList<ExpenseItem> expenseItems) {
		this.expenseItems = expenseItems;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}	
	public User getClaimant() {
		return Claimant;
	}
	public void setClaimant (User name) {
		this.Claimant = name;
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
	public ArrayList<Destination> getDestination() {
		return destinations;
	}
	public void setDestination(ArrayList<Destination> destination) {
		this.destinations = destination;
	}
	public ArrayList<String> getApprovers() {
		return approvers;
	}
	public void setApprovers(ArrayList<String> apps) {
		this.approvers = apps;
	}
	public ArrayList<ExpenseItem> getExpenses() {
		return expenseItems;
	}
	public void setApprover(User approver) {
		this.Approver = approver;
	}
	public User getApprover(){
		return Approver;
	}
	public ArrayList<String> getTagList(){
		if (tagList == null){
			tagList = new ArrayList<String>();
		}
		return tagList;
	}
	//end getters and setters
	
	//All Listener Functions
	private ArrayList<Listener> getListeners() {
		if (listeners == null ) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	private void notifyListeners() {
		for (Listener listener : getListeners()) {
			listener.update();
		}
	}
	public void addListener (Listener L) {
		getListeners().add(L);
	}
	public void removeListener(Listener l) {
		getListeners().remove(l);
	}
}
