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
	private String comment;
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;
	private String Description;
	private String Status;
	private Location location;
	private ArrayList<Destination> destinations;
	private ExpenseList expenseList;
	private ArrayList<String> tagList;
	private ArrayList<Listener> listeners;

	public Claim(String name, Date startDate, Date endDate, String description, User Claimant, int id){
		this.comment = "";
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

	public void returnClaim(String name) throws Exception {
		if (Claimant.getUserName().equals(name)) {
			throw new Exception();
		}
		User user =  UserListController.getUserList().getUser(name);
		setApprover(user);
		setStatus("Returned");
	}

	public void approveClaim(String name) throws Exception {
		if (Claimant.getUserName().equals(name)) {
			throw new Exception();
		}
		User user =  UserListController.getUserList().getUser(name);
		setApprover(user);
		setStatus("Approved");
	}

	public boolean editable() {
		if (Status.equals("In Progress")||Status.equals("Returned")){
			return true;
		} else {
			return false;
		}
	}

	public boolean incomplete() {
		ArrayList<ExpenseItem> expenseItems = this.expenseList.getList();
		for (int i = 0; i < expenseItems.size(); i++) {
			expenseItems.get(i).incomplete();
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
	public String toStringApproverList() {
		String ds= dateFormat.format(startDate);
		String block = "["+ds+"] "+Claimant.getUserName()+" - "+Status;
		if(!(getTotal().size() == 0)) {
			block += "\n"+toStringList(getTotal());
		}
		if(!(destinations.size() == 0)) {
			block += "\n"+destinationsListToString();
		}
		if (!(approver == null)) {
			block += "\n"+approver.getUserName();
		}
		return block;
	}

	public String toStringClaimantList() {
		String ds = dateFormat.format(startDate);
		String block = "["+ds+"] "+name+" - "+Status;
		if(!(getTotal().size() == 0)) {
			block += "\n"+toStringList(getTotal());
		} if(!(destinations.size() == 0)) {
			block += "\n"+destinationsListToString();
		} if (!(tagList.size() == 0)) {
			block += "\n"+toStringList(tagList);
		}
		return block;
	}
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
	public String toStringList(ArrayList<String> list) {
		String string = "";
		for (int i = 0; i < list.size(); i++) {
			if (i + 1 == list.size()) {
				string += list.get(i);
			} else {
				string += list.get(i)+", ";
			}
		}
		return string;
	}

	public String toEmail() {
		ArrayList<ExpenseItem> expenseItems = this.expenseList.getList();
		String ds = dateFormat.format(startDate);
		String de = dateFormat.format(endDate);
		String string = name+"\n";
		string += Status+"\n"+Description+"\n";
		string += ds+" - "+de+"\n";
		string += "Destinations:"+destinationsToString()+"\n";
		string += "Total"+toStringList(getTotal())+"\n";
		string += "Items:";
		for (int i = 0; i < expenseItems.size(); i++) {
			string += expenseItems.get(i).toEmail()+"\n";
		}
		return string;
	}
	// end toString functions

	public boolean equals(Claim claim) {
		if (claim == null) {
			return false;
		}
		return (id == claim.getId());
	}

	//all the adds/removes/contains for the lists
	public void removeItem(int i) {
		ExpenseItem item = this.expenseList.getList().get(i);
		this.expenseList.rmExpenseItem(item);
		notifyListeners();
	}

	public void addItem(ExpenseItem item) throws AlreadyExistsException {
		if (!this.expenseList.contains(item)) {
			this.expenseList.addExpenseItem(item);
			notifyListeners();
		} else {
			throw new AlreadyExistsException();
		}

	}
	public boolean containsItem(ExpenseItem Item){
		return this.expenseList.contains(Item);	
	}
	public void addDestination (Destination destination) throws AlreadyExistsException {
		if (!destinations.contains(destination)){
			destinations.add(destination);
		} else {
			throw new AlreadyExistsException();
		}
	}
	public void removeDestination(int i) {
		destinations.remove(i);
	}
	public boolean containsDestination(Destination Destination) {
		return destinations.contains(Destination);
	}
	public void removeTag(int pos) {
		tagList.remove(pos);
	}
	public void addTag(String tag) throws AlreadyExistsException {
		if (!tagList.contains(tag)){
			tagList.add(tag);
		} else {
			throw new AlreadyExistsException();
		}
		notifyListeners();
	}
	public boolean containsTag(String tag){
		return tagList.contains(tag);	
	}
	//end add/remove/contains

	//All the getters and setters
	public int getId(){
		return id;
	}
	public ArrayList<ExpenseItem> getExpenseItems() {
		return this.expenseList.getList();
	}
	public void setExpenseItems(ArrayList<ExpenseItem> expenseItems) {
		this.expenseList.setList(expenseItems);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		notifyListeners();
	}	
	public User getClaimant() {
		return Claimant;
	}
	public void setClaimant (User name) {
		this.Claimant = name;
	}	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}	
	public String getDescription() {
		return Description;
	}

	public void setDescription(String desc) {
		this.Description = desc;
		notifyListeners();
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
		notifyListeners();
	}
	public ArrayList<Destination> getDestination() {
		return destinations;
	}
	public void setDestination(ArrayList<Destination> destination) {
		this.destinations = destination;
		notifyListeners();
	}
	public User getApprover() {
		return approver;
	}
	public void setApprover(User app) {
		this.approver = app;
	}
	public ArrayList<String> getTagList(){
		if (tagList == null){
			tagList = new ArrayList<String>();
		}
		return tagList;
	}
	public void setTagList(ArrayList<String> list) {
		this.tagList = list;
	}
	//gets the total
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
			formatedStringList.add(amount.toString()+" "+currency+"\n");
		}
		return formatedStringList;
	}
	public Location getLocation()
	{
		return location;
	}
	
	public void setLocation(Location location)
	{
		this.location = location;
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

