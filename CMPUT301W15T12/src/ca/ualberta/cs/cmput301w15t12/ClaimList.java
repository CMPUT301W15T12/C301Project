package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClaimList {
	public ArrayList<Claim> Claims;
	public ArrayList<Listener> listeners;
	public String Claimant;

	public ClaimList(String Claimant) {
		this.Claims = new ArrayList<Claim>();	
		this.listeners = new ArrayList<Listener>();
		this.Claimant = Claimant;
	}

	public ArrayList<Claim> getClaims() {
		return Claims;
	}
	
	public String getClaimant() {
		return Claimant;
	}

	public void setClaimant (String name) {
		this.Claimant = name;
	}
	
	public void returnClaim(Claim claim, String approver, String claimant) throws CantApproveOwnClaimException
	{
		if (approver.equals(claimant)){
			throw new CantApproveOwnClaimException();
		}
		claim.setStatus("Returned");
		claim.addApprover(approver);
		
		//TODO add back to other list, remove from this list
	}
	
	public void approveClaim(Claim claim, String approver, String Claimant) throws CantApproveOwnClaimException{
		if (approver.equals(Claimant)){
			throw new CantApproveOwnClaimException();
		}
		claim.setStatus("Approved");
		claim.addApprover(approver);
		//TODO add back to other list, remove from this list
	}
	
	public void remove(Claim claim) {
		//TODO
	}
	
	public void add(Claim claim){
		//TODO check for name duplicates
		Claims.add(claim);
	}
	
	private ArrayList<Listener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	
	public void addClaim(Claim claim) throws AlreadyExistsException {
		for (int i = 0; i < Claims.size(); i++) {
			if (Claims.get(i).getName().equals(claim.getName())){
				throw new AlreadyExistsException();
			}
		}
		Claims.add(claim);
		notifyListeners();
	}
	
	public void removeClaim (String claimname){
		for (int i = 0; i < Claims.size() ; i++) {
			if (Claims.get(i).getName() == claimname) {
				Claims.remove(i);
			}
		}
		notifyListeners();
	}
	
	public boolean contains(String claimname) {
		for (int i = 0; i < Claims.size(); i++) {
			if (Claims.get(i).getName().equals(claimname)) {
				return true;
			}
		}
		return false;
	}
	
	public int size()
	{
		return Claims.size();
	}
	
	public void addListener(Listener l) {
		getListeners().add(l);
	}
	
	private void notifyListeners() {
		for (Listener listener : getListeners()) {
			listener.update();
		}
	}
	
	public void removeListener(Listener l) {
	 	getListeners().remove(l);
	}

	//sorts by the start date of a claim
	public void sort() {
		Collections.sort(Claims, new Comparator<Claim>() {
			@Override
			public int compare(Claim lhs, Claim rhs){
				return lhs.getStartDate().compareTo(rhs.getStartDate());
			}
		});
	}

}