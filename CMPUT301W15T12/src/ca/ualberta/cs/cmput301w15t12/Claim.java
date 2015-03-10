package ca.ualberta.cs.cmput301w15t12;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Claim {
	private User Claimant;
	private String Comment;

	private int Id;

	private String Name;
	private Date startDate;
	private Date endDate;
	private String Description;
	private String Status;

	private ArrayList<Destination> destinations;
	private ArrayList<String> approvers;
	private ArrayList<ExpenseItem> expenseItems;
	private ArrayList<String> tagList;
	protected transient ArrayList<Listener> listeners = null;

	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

	public Claim(String name, Date startDate, Date endDate, String description, User Claimant){
		this.Comment = "";
		this.Name = name;
		this.Claimant = Claimant;
		this.Status = "In Progress";
		this.startDate = startDate; 
		this.endDate = endDate;
		this.Description = description;
		this.destinations = new ArrayList<Destination>();
		this.approvers = new ArrayList<String>();
		this.expenseItems = new ArrayList<ExpenseItem>();
		this.tagList = new ArrayList<String>();
		this.listeners = new ArrayList<Listener>();
		this.Id = ClaimListController.getClaimList().getCounter();
		ClaimListController.getClaimList().incrementCounter();
	}

	public void returnClaim(String name) throws CantApproveOwnClaimException {
		if (name.equals(Claimant)) {
			throw new CantApproveOwnClaimException();
		} else {
			if (!approvers.contains(name)) {
			}
			approvers.add(name);
		}
		setStatus("Returned");
	}

	public void approveClaim(String name) throws CantApproveOwnClaimException {
		if (name.equals(Claimant)) {
			throw new CantApproveOwnClaimException();
		} else {
			if (!approvers.contains(name)) {
				approvers.add(name);
			}
			setStatus("Approved");
		}

	}

	public boolean editable() {
		if (Status.equals("In Progress")||Status.equals("Returned")){
			return true;
		} else {
			return false;
		}
	}

	//All the toString functions
	public String toStringApproverList() {
		String ds= df.format(startDate);
		String block = "["+ds+"] "+Claimant+" - "+Status+"\n"+toStringList(getTotal())+"\n"+destinationsToString()+"\n"+toStringList(approvers);
		return block;
	}

	public String toStringClaimantList() {
		String ds = df.format(startDate);
		String block = "["+ds+"] "+Name+" - "+Status+"\n"+toStringList(getTotal())+"\n"+destinationsToString()+"\n"+toStringList(tagList);
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
		string += "Total"+toStringList(getTotal())+"\n";
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

	public void addItem(ExpenseItem Item) throws AlreadyExistsException {
		if (!expenseItems.contains(Item)) {
			expenseItems.add(Item);
			notifyListeners();
		} else {
			throw new AlreadyExistsException();
		}

	}
	public boolean containsItem(ExpenseItem Item){
		return expenseItems.contains(Item);	
	}
	public void addApprover(String name) throws AlreadyExistsException {
		if(!approvers.contains(name)){
			approvers.add(name);
		} else {
			throw new AlreadyExistsException();
		}
	}
	public void removeApprover(String name) {
		approvers.remove(name);
	}
	public boolean containsApprover(String Approver) {
		return approvers.contains(Approver);
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
	}
	public boolean containsTag(String tag){
		return tagList.contains(tag);	
	}
	//end add/remove/contains

	//All the getters and setters
	public int getId(){
		return Id;
	}
	public void setId(int id){
		Id = id;
	}
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
	public ArrayList<String> getTagList(){
		if (tagList == null){
			tagList = new ArrayList<String>();
		}
		return tagList;
	}
	//gets the total
	public ArrayList<String> getTotal() {
		BigDecimal cad = new BigDecimal(0);
		BigDecimal usd = new BigDecimal(0);
		BigDecimal eur = new BigDecimal(0);
		BigDecimal gbp = new BigDecimal(0);
		BigDecimal chf = new BigDecimal(0);
		BigDecimal jpy = new BigDecimal(0);
		BigDecimal cny = new BigDecimal(0);
		ArrayList<String> total = new ArrayList<String>();
		for (int i = 0; i < expenseItems.size(); i++) {
			if(expenseItems.get(i).getCurrency().equals("CAD")) {
				cad.add(expenseItems.get(i).getAmount());
			} else if (expenseItems.get(i).getCurrency().equals("USD")) {
				usd.add(expenseItems.get(i).getAmount());
			} else if (expenseItems.get(i).getCurrency().equals("EUR")) {
				eur.add(expenseItems.get(i).getAmount());
			} else if (expenseItems.get(i).getCurrency().equals("GBP")) {
				gbp.add(expenseItems.get(i).getAmount());
			} else if (expenseItems.get(i).getCurrency().equals("CHF")) {
				chf.add(expenseItems.get(i).getAmount());
			} else if (expenseItems.get(i).getCurrency().equals("JPY")) {
				jpy.add(expenseItems.get(i).getAmount());
			} else {
				cny.add(expenseItems.get(i).getAmount());
			}
		}
		if (!cad.equals(0)){
			total.add(cad.toString()+" "+"CAD"+"\n");
		}
		if (!usd.equals(0)){
			total.add(usd.toString()+" "+"USD"+"\n");
		}
		if (!eur.equals(0)){
			total.add(eur.toString()+" "+"EUR"+"\n");
		}
		if (!gbp.equals(0)){
			total.add(gbp.toString()+" "+"GBP"+"\n");
		}
		if (!chf.equals(0)){
			total.add(chf.toString()+" "+"CHF"+"\n");
		}
		if (!jpy.equals(0)){
			total.add(jpy.toString()+" "+"JPY"+"\n");
		}
		if (!cny.equals(0)){
			total.add(cny.toString()+" "+"CNY"+"\n");
		}
		return total;
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

