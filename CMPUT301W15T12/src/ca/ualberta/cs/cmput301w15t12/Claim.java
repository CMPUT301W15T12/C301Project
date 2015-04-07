/**
 * This data model models all the behaviour relating to a particular claim, 
 * and stores information regarding expense items, tags, and destinations.
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
 *   @megsum
 *   @olexson
 */

package ca.ualberta.cs.cmput301w15t12;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.location.Location;

public class Claim {
	//static key work on dataFormat ensures all instances of the Claim class are going to have a consistent dateformat. 
	//This is a design decision, talk to Jim if this doesn't suit your needs
	static private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

	private User Claimant;
	private User approver;
	private ArrayList<String> comment = new ArrayList<String>();
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;
	private String Description;
	private String Status;
	private ArrayList<Destination> destinations;
	private ExpenseList expenseList;
	private ArrayList<String> tagList;
	private ArrayList<Location> locations = new ArrayList<Location>();
	private ArrayList<Listener> listeners;
	/** Claim constructor sets the values for name, satrt date, end date, description, claimannt, id, approver, 
	 * expenselist,taglist,destinations.
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param description
	 * @param claimant
	 * @param id
	 */
	public Claim(String name, Date startDate, Date endDate, String description, User Claimant, int id){
		this.name = name;
		this.Claimant = Claimant;
		this.approver = null;
		this.Status = "In Progress";
		this.startDate = startDate; 
		this.endDate = endDate;
		this.Description = description;
		this.destinations = new ArrayList<Destination>();
		this.expenseList = new ExpenseList();
		this.tagList = new ArrayList<String>();
		this.listeners = new ArrayList<Listener>();
		this.id = id;
	}
	/** returns the claim to the user if the approver is not the claimant and as long as there is a 
	 * comment attached to the claim
	 * @throws NotAllowedException
	 * @throws MissingItemException
	 * @param name
	 */ 
	public void returnClaim(String name) throws NotAllowedException, MissingItemException{
		if (Claimant.getUserName().equals(name)) {
			throw new NotAllowedException();
		} else if (comment.size() == 0){
			throw new MissingItemException();
		} else {
			User user =  UserListController.getUserList().getUser(name);
			setApprover(user);
			setStatus("Returned");
		}
	}
	/** approves the claim if the approver is not the claimant and if there is a 
	 * comment attached to the claim
	 * @throws NotAllowedException
	 * @throws MissingItemException
	 * @param name
	 */ 
	public void approveClaim(String name) throws MissingItemException, NotAllowedException {
		if (Claimant.getUserName().equals(name)) {
			throw new NotAllowedException();
		} else if(comment.size() == 0) {
			throw new MissingItemException();
		} else {
			
			User user =  UserListController.getUserList().getUser(name);
			setApprover(user);
			setStatus("Approved");
		}
	}
	/** tells the user that they are allowed to edit the claim
	 * @return true if the status is not set to submitted or approved
	 * and false if the status is In Progress or Returned
	 */ 
	public boolean editable() {
		if (Status.equals("In Progress")||Status.equals("Returned")){
			return true;
		} else {
			return false;
		}
	}
	/** returns whether or not the claim is incomplete
	 * @return true if there are no expense items, or one of the items has been flagged or the
	 * claim is missing a necessary parameter.
	 * Otherwise it returns false.
	 */
	public boolean incomplete() {
		ArrayList<ExpenseItem> expenseItems = this.expenseList.getList();
		if (this.expenseList.size() == 0){
			return true;
		}
		for (int i = 0; i < expenseItems.size(); i++) {
			if (expenseItems.get(i).getFlag()){
				return true;
			}
		}
		if (name == null || startDate == null || endDate == null ||Description == null ||destinations.size() == 0) { 
			return true;
		}
		return false;
	}

	//All the toString functions
	/** Creates a string representation of the claim containing all the requirements from the approver list
	 *  --> the date, claimant name, total,destinations, and any approver name
	 * @return String representation of the claim
	 */
	public String toStringApproverList() {
		String ds= dateFormat.format(startDate);
		String block = "["+ds+"] "+Claimant.getUserName()+" - "+Status;
		if(!(getTotal().size() == 0)) {
			block += "\n"+getTotalString();
		}
		if(!(destinations.size() == 0)) {
			block += "\n"+destinationsListToString();
		}
		if (!(approver == null)) {
			block += "\n"+approver.getUserName();
		}
		return block;
	}
	/** Creates a string representation of the claim containing all the requirements from the claimant list
	 *  --> the date, claim name, total,destinations,tags
	 * @return String representation of the claim
	 */
	public String toStringClaimantList() {
		String ds = dateFormat.format(startDate);
		String block = "["+ds+"] "+name+" - "+Status;
		if(!(getTotal().size() == 0)) {
			block += "\n"+getTotalString();
		} if(!(destinations.size() == 0)) {
			block += "\n"+destinationsListToString();
		} if (!(tagList.size() == 0)) {
			block += "\n"+toStringTagList(tagList);
		}
		return block;
	}
	/** Creates a string representation of the destination list including the descriptions
	 * @return String representation of the destination list
	 */
	public String destinationsToString() {
		String dests = "";
		for (int i = 0; i < destinations.size(); i++) {
			if (i + 1 == destinations.size()) {
				dests += destinations.get(i).toString();
			} else {
				dests += destinations.get(i).toString()+", ";
			}
		}
		return dests;
	}
	/** Creates a string representation of the destination list excluding the descriptions
	 * @return String representation of the destination list
	 */
	public String destinationsListToString() {
		String dests = "";
		for (int i = 0; i < destinations.size(); i++) {
			if (i + 1 == destinations.size()) {
				dests += destinations.get(i).getDestination();
			} else {
				dests += destinations.get(i).getDestination()+", ";
			}
		}
		return dests;
	}
	/** Creates a string representation of the tag list
	 * with commas separating the items
	 * @return String representation of the tag list
	 */
	public String toStringTagList(ArrayList<String> list) {
		String string = "";
		for (int i = 0; i < list.size(); i++) {
			if(i + 1 != list.size()){
				string += list.get(i)+", ";
			} else {		
				string += list.get(i);
			}
		}
		return string;
	}
	/** Creates a string representation of the claim with everything included, 
	 * ready to be emailed
	 * @return String representation of the claim
	 */
	public String toEmail() {
		ArrayList<ExpenseItem> expenseItems = this.expenseList.getList();
		String ds = dateFormat.format(startDate);
		String de = dateFormat.format(endDate);
		String string = name+"\n";
		string += Status+"\n"+Description+"\n";
		string += ds+" - "+de+"\n";
		string += "Destinations:"+destinationsToString()+"\n";
		string += "Total: "+getTotalString()+"\n";
		string += "Items:";
		for (int i = 0; i < expenseItems.size(); i++) {
			string += expenseItems.get(i).toEmail()+"\n";
		}
		return string;
	}
	// end toString functions
	/**checks if two claims are equal by comparing the claim ids
	 * 
	 * @param claim
	 * @return true if the ids are equal and false otherwise
	 */
	public boolean equals(Claim claim) {
		if (claim == null) {
			return false;
		}
		return (id == claim.getId());
	}

	//all the adds/removes/contains for the lists
	/** removes an expense item from the expense item list with the index i
	 * @param i
	 */
	public void removeItem(int i) {
		this.expenseList.rmExpenseItem(i);
	}
	/**adds an expense item to the expense item list
	 * 
	 * @param item
	 */
	public void addItem(ExpenseItem item) {
		this.expenseList.addExpenseItem(item);
	}
	/**adds a comment to the comment list
	 * 
	 * @param Comment
	 */
	public void addComment(String Comment) {
		this.comment.add(Comment);
	}
	/**checks if the expense item list contians 
	 * a particular expense item
	 * @param Item
	 * @return true if it does, otherwise false
	 */
	public boolean containsItem(ExpenseItem Item){
		return this.expenseList.contains(Item);	
	}
	/** returns the list of photo ids corresponding to the expense items
	 * @return integer[] where integer[i] corresponds to expense item[i]
	 * and contains the photo to be placed next to that item in the list view
	 */
	public Integer[] getIds() {
		return expenseList.getIds();
	}
	/**
	 * adds a destination to the destination list
	 * @param destination
	 * @throws AlreadyExistsException
	 */
	public void addDestination (Destination destination) throws AlreadyExistsException {
		if (!destinations.contains(destination)){
			destinations.add(destination);
		} else {
			throw new AlreadyExistsException();
		}
	}
	/**
	 * removes a destination from the destinations list
	 * @param i
	 */
	public void removeDestination(int i) {
		destinations.remove(i);
	}
	/**
	 * checks if the destinations list contains a particular destination
	 * @param Destination
	 * @return true if it does otherwise false
	 */
	public boolean containsDestination(Destination Destination) {
		return destinations.contains(Destination);
	}
	/**
	 * removes a tag from the tag list
	 * @param pos
	 */
	public void removeTag(int pos) {
		tagList.remove(pos);
	}
	/**
	 * adds a tag to the tag list
	 * @param tag
	 * @throws AlreadyExistsException if that tag is already in the list
	 */
	public void addTag(String tag) throws AlreadyExistsException {
		if (!tagList.contains(tag)){
			tagList.add(tag);
		} else {
			throw new AlreadyExistsException();
		}
		notifyListeners();
	}
	/**
	 * checks if the tag list contains a particular tag
	 * @param tag
	 * @return returns true if it does otherwise false
	 */
	public boolean containsTag(String tag){
		return tagList.contains(tag);	
	}
	//end add/remove/contains

	//All the getters and setters
	/**sets all the claim values to their editted values
	 * 
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param description
	 * @param tags
	 * @param destinations
	 */
	public void setAll(String name, Date startDate, Date endDate, String description, ArrayList<String> tags,ArrayList<Destination> destinations){
		setName(name);
		setStartDate(startDate);
		setEndDate(endDate);
		setDescription(description);
		setTagList(tags);
		setDestination(destinations);
	}
	/**
	 * @return the claim id
	 */
	public int getId(){
		return id;
	}
	/**
	 * 
	 * @return the expense item ArrayList
	 */
	public ArrayList<ExpenseItem> getExpenseItems() {
		return this.expenseList.getList();
	}
	/**
	 * sets the expense item list to a given expense item list
	 * @param expenseItems
	 */
	public void setExpenseItems(ArrayList<ExpenseItem> expenseItems) {
		this.expenseList.setList(expenseItems);
	}
	/**
	 * 
	 * @return the claim name
	 */
	public String getName() {
		return name;
	}
	/**
	 * sets the claim name to the name provided
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
		notifyListeners();
	}	
	/**
	 * 
	 * @return the claim creator user 
	 */
	public User getClaimant() {
		return Claimant;
	}
	/**
	 * sets the claimant to the provided user
	 * @param name
	 */
	public void setClaimant (User name) {
		this.Claimant = name;
	}	
	/**
	 * 
	 * @return the ArrayList of comments
	 */
	public ArrayList<String> getComment() {
		return comment;
	}
	/**
	 * sets the Arraylist of comments to the one provided
	 * @param comment
	 */
	public void setComment(ArrayList<String> comment) {
		this.comment = comment;
	}	
	/**
	 * 
	 * @return the claim description
	 */
	public String getDescription() {
		return Description;
	}
	/**
	 * sets the claim description to the one provided
	 * @param desc
	 */
	public void setDescription(String desc) {
		this.Description = desc;
		notifyListeners();
	}	
	/**
	 * @return the claim status
	 */
	public String getStatus() {
		return Status;
	}
	/**
	 * sets the claim status
	 * @param status
	 */
	public void setStatus(String status) {
		this.Status = status;
	}	
	/**
	 * 
	 * @return the claim start date
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * sets the claim start date
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/** 
	 * 
	 * @return the claim end date
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * sets the claim end date
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		notifyListeners();
	}
	/**
	 * 
	 * @return the arraylist of destinations
	 */
	public ArrayList<Destination> getDestination() {
		return destinations;
	}
	/** 
	 * sets the array list of destinations to the one provided
	 * @param destination
	 */
	public void setDestination(ArrayList<Destination> destination) {
		this.destinations = destination;
		notifyListeners();
	}
	/**
	 * 
	 * @return the claim approver User
	 */
	public User getApprover() {
		return approver;
	}
	/** 
	 * sets the claim approver to the one provided
	 * @param app
	 */
	public void setApprover(User app) {
		this.approver = app;
	}
	/**
	 * @return the arraylist of tags
	 */
	public ArrayList<String> getTagList(){
		if (tagList == null){
			tagList = new ArrayList<String>();
		}
		return tagList;
	}
	/**
	 * sets the arraylist of tags to the one provided
	 * @param list
	 */
	public void setTagList(ArrayList<String> list) {
		this.tagList = list;
	}
	
	//gets the total
	/**
	 * computes the total from the expense items in the expense item array
	 * @return an array list of strings
	 */
	public ArrayList<String> getTotal() {
		ArrayList<ExpenseItem> expenseItems = this.expenseList.getList();
		HashMap <String,BigDecimal> costDictionary = new HashMap<String, BigDecimal>();
		ArrayList<String> formatedStringList = new ArrayList<String>();
		for (int i=0; i<expenseItems.size();i++){
			ExpenseItem expense = expenseItems.get(i);
			String currency = expense.getCurrency();

			if (costDictionary.containsKey(currency)){	//there is already an item with the same currency
				costDictionary.put(currency,costDictionary.get(currency).add(expense.getAmount()));
			}else{
				costDictionary.put(currency,expense.getAmount());
			}	
		}

		for (String currency : costDictionary.keySet()) {
			BigDecimal amount = costDictionary.get(currency);
			formatedStringList.add(amount.toString()+" "+currency+", ");
		}
		int length = formatedStringList.size() - 1;
		if (length != -1) {
			String line =  formatedStringList.get(length);
			formatedStringList.set(length, line.substring(0,line.length() - 2));
		}
		return formatedStringList;
	}
	/**format the total arraylist into a string
	 * @return a string format of the total
	 */
	public String getTotalString(){
		ArrayList<String> TS = getTotal();
		String s = "";
		for (int i = 0; i < TS.size(); i++) {
			s += TS.get(i);
		}
		return s;
	}
	/**
	 * @return the arraylist of locations from the destinations and expense items
	 */
	public ArrayList<Location> getLocations(){
		locations.clear();
		for (int i = 0; i < destinations.size(); i++) {
			locations.add(destinations.get(i).getLocation());
		}
		for (int j = 0; j < expenseList.size(); j++) {
			if (expenseList.getList().get(j).getBoolLocation()) {
				locations.add(expenseList.getList().get(j).getlocation());
			}
		}
		
		return locations;	
	}
	
	//end getters and setters

	//All Listener Functions
	/**
	 * @return an array list of listeners
	 */
	private ArrayList<Listener> getListeners() {
		if (listeners == null ) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	/**
	 * notifies all listeners of a change
	 */
	private void notifyListeners() {
		for (Listener listener : getListeners()) {
			listener.update();
		}
	}
	/**
	 * add a listener to the list of listeners
	 * @param L
	 */
	public void addListener (Listener L) {
		getListeners().add(L);
	}
	/**
	 * removes a listener from the arraylist of listeners
	 * @param l
	 */
	public void removeListener(Listener l) {
		getListeners().remove(l);
	}

}

