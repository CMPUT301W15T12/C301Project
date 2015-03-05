package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ClaimList implements List<Claim>{
	private static ArrayList<Claim> claims;
	private static ArrayList<Listener> listeners;
	private static int counter;
	
	public ClaimList() {
		// not already initialized
		if (claims == null) {
			claims = new ArrayList<Claim>();
			listeners = new ArrayList<Listener>();
			counter = 0;
		}
	}
	
	//filtered returns a sublist of from the list claims that have the specified tag
	public ArrayList<Claim> getFiltered(ArrayList<Claim> claims, String tag){
		ArrayList<Claim> filtered = new ArrayList<Claim>();
		for (int i = 0; i < claims.size(); i++) {
			if (claims.get(i).containsTag(tag)){
				filtered.add(claims.get(i));
			}
		}
		return filtered;
	}
	
	//given a username, returns a list of claims with that username
	public ArrayList<Claim> getUserClaims(String Username) {
		ArrayList<Claim> claims = new ArrayList<Claim>();
		for (int i = 0; i < claims.size(); i++) {
			if (claims.get(i).getClaimant().getUserName().equals(Username)) {
				claims.add(claims.get(i));
			}
		}
		return claims;
	}
		
	public void setSelected(Claim claim) {
		// TODO Auto-generated method stub
		
	}
	
	public Claim getClaim(int id) {
		for (int i = 0; i < claims.size(); i++) {
			if (claims.get(i).getId() == id) {
				return claims.get(i);
			}
		}
		return claims.get(0); // TODO what to return if claim not found?
	}

	//gets the list of claims that have been submitted
	public ArrayList<Claim> getSubmittedclaims() {
		ArrayList<Claim> list = new ArrayList<Claim>();
		for (int i = 0; i < claims.size(); i++){
			if (claims.get(i).getStatus().equals("Submitted")) {
				list.add(claims.get(i));
			}
		}
		return list;
	}
	
	public void returnClaim(Claim claim, String approver) throws CantApproveOwnClaimException, AlreadyExistsException
	{
		if (approver.equals(claim.getClaimant())){
			throw new CantApproveOwnClaimException();
		}
		claim.setStatus("Returned");
		claim.addApprover(approver);
	}
	
	public void approveClaim(Claim claim, String approver) throws CantApproveOwnClaimException, AlreadyExistsException{
		if (approver.equals(claim.getClaimant())){
			throw new CantApproveOwnClaimException();
		}
		claim.setStatus("Approved");
		claim.addApprover(approver);
	}
	
	//add/remove/contains/size functions
	public void addClaim(Claim claim) throws AlreadyExistsException {
		for (int i = 0; i < claims.size(); i++) {
			if (claims.get(i).getName().equals(claim.getName())){
				throw new AlreadyExistsException();
			}
		}
		claims.add(claim);
		notifyListeners();
	}
	
	public void removeClaim(String claimname){
		for (int i = 0; i < claims.size() ; i++) {
			if (claims.get(i).getName() == claimname) {
				claims.remove(i);
			}
		}
		notifyListeners();
	}	
	
	// TODO: Change Compare for different types of filtering
	@Override
	public int Compare(Claim item1, Claim item2) {
		if (item1.getStartDate().compareTo(item2.getStartDate()) != 0) {
			// Compare by startDates
			return item1.getStartDate().compareTo(item2.getStartDate());
		} else if (item1.getEndDate().compareTo(item2.getEndDate()) != 0) {
			// startDates are the same, compare by endDates
			return item1.getEndDate().compareTo(item2.getEndDate());
		} else if (item1.getName().compareTo(item2.getName()) != 0){
			// endDates are the same as well, compare by names
			return item1.getName().compareTo(item2.getName());
		} else {
			// names are the same as well, compare by Description
			return item1.getDescription().compareTo(item2.getDescription());
		}
	}
	
	@Override
	public void sort() {
		Collections.sort(claims, new Comparator<Claim>() {
			
			@Override
			public int compare(Claim lhs, Claim rhs){
				return Compare(lhs,rhs);
			}
			
		});
		
		notifyListeners();
	}

	@Override
	public ArrayList<Claim> getList() {
		return claims;
	}

	@Override
	public void setList(ArrayList<Claim> list) {
		claims = list;
		sort();
	}

	@Override
	public boolean contains(Claim claim) {
		return claims.contains(claim);
	}
	
	@Override
	public void addItem(Claim item) {
		claims.add(item);
		sort();
	}

	@Override
	public void rmItem(Claim item) {
		claims.remove(item);
		sort();
	}

	@Override
	public ArrayList<Listener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}

	@Override
	public void addListener(Listener listener) {
		listeners.add(listener);		
	}
	
	@Override
	public void rmListener(Listener listener) {
		listeners.remove(listener);
	}

	@Override
	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.update();
		}
	}
	
	public void incrementCounter() {
		counter += 1;
	}
	
	public int getCounter(){
		return counter;
	}
	
	public void setCounter(int id_counter){
		counter = id_counter;
	}
	
	@Override
	public int size() {
		return claims.size();
	}

}
