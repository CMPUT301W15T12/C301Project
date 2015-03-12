package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Date;


public class ClaimList{

	private static ArrayList<Claim> claims;
	private static ArrayList<Listener> listeners;
	private static int nextUnassignedId;
	
	public ClaimList() {
		if (ClaimList.claims==null){
			claims = new ArrayList<Claim>();
			listeners = new ArrayList<Listener>();
			ClaimList.nextUnassignedId = 0;
		}
	}
	
	//==================Get/Add/Remove/Contains/Size==================
	public Claim getClaim(int id) {
		for (int i = 0; i < claims.size(); i++) {
			if (claims.get(i).getId() == id) {
				return claims.get(i);
			}
		}
		return null; //return null if not found
	}
	
	public ArrayList<Claim> getAllClaims(){
		return ClaimList.claims;
	}
	
	public int addClaim(String name, Date startDate, Date endDate, String description, User Claimant){
		int id = getNextUnassignedId();
		incrementeNextUnassignedId(); //important
		Claim aNewClaim = new Claim(name,startDate,endDate, description, Claimant, id);
		claims.add(aNewClaim);
		notifyListeners();
		return id;		//return the id of the newly created claim
	}
	
	public void removeClaim(int id) {
		for (int i = 0; i < claims.size() ; i++) {
			if (claims.get(i).getId() == id) {
				claims.remove(i);
			}
		}
		notifyListeners();
	}
	
	public boolean contains(Claim claim) {
		return claims.contains(claim);
	}
	
	public int size() {
		return claims.size();
	}

	//==================Filters==================
	public ArrayList<Claim> filterByClaimant(User claimant) {
		ArrayList<Claim> filteredClaimList = new ArrayList<Claim>();
		for (int i = 0; i < ClaimList.claims.size(); i++) {
			if (ClaimList.claims.get(i).getClaimant().equals(claimant)) {
				filteredClaimList.add(ClaimList.claims.get(i));
			}
		}
		return filteredClaimList;
	}
	
	public ArrayList<Claim> filterByStatus(String status) {
		ArrayList<Claim> filteredClaimList = new ArrayList<Claim>();
		for (int i = 0; i < ClaimList.claims.size(); i++){
			if (ClaimList.claims.get(i).getStatus().equals(status)) {
				filteredClaimList.add(ClaimList.claims.get(i));
			}
		}
		return filteredClaimList;
	}

	public ArrayList<Claim> filterByTag(String tag){
		ArrayList<Claim> filteredClaimList = new ArrayList<Claim>();
		for (int i = 0; i < ClaimList.claims.size(); i++) {
			if (ClaimList.claims.get(i).containsTag(tag)){
				filteredClaimList.add(ClaimList.claims.get(i));
			}
		}
		return filteredClaimList;
	}
	
	//==================Listener==================
	public ArrayList<Listener> getListeners() {
		return ClaimList.listeners;
	}

	public void addListener(Listener listener) {
		ClaimList.listeners.add(listener);		
	}
	
	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	
	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.update();
		}
	}
	
	//==================Private==================
	private int getNextUnassignedId(){
		return ClaimList.nextUnassignedId;
	}
	private void incrementeNextUnassignedId() {
		ClaimList.nextUnassignedId += 1;
	}


}
