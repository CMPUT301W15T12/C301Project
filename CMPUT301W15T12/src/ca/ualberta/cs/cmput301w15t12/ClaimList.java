package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ClaimList {
	public static ArrayList<Claim> Claims;
	public ArrayList<Listener> listeners;

	public ClaimList() {
		ClaimList.Claims = new ArrayList<Claim>();	
		this.listeners = new ArrayList<Listener>();
	}

	public ArrayList<Claim> getClaims() {
		return Claims;
	}	
	public ArrayList<Claim> getFiltered(){
		//TODO get some tag from search
		return ;
	}
	
	public void setSelected(Claim claim) {
		// TODO Auto-generated method stub
		
	}
	
	//gets the list of claims that have been submitted
	public ArrayList<Claim> getSubmittedClaims() {
		ArrayList<Claim> list = new ArrayList<Claim>();
		for (int i = 0; i < Claims.size(); i++){
			if (Claims.get(i).getStatus().equals("Submitted")) {
				list.add(Claims.get(i));
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

	//sorts by the start date of a claim
	public void sort() {
		Collections.sort(Claims, new Comparator<Claim>() {
			@Override
			public int compare(Claim lhs, Claim rhs){
				return lhs.getStartDate().compareTo(rhs.getStartDate());
			}
		});
	}
	//add/remove/contains/size functions
	public void add(Claim claim) throws AlreadyExistsException {
		for (int i = 0; i < Claims.size(); i++) {
			if (Claims.get(i).getName().equals(claim.getName())){
				throw new AlreadyExistsException();
			}
		}
		Claims.add(claim);
		notifyListeners();
	}
	
	public void remove(String claimname){
		for (int i = 0; i < Claims.size() ; i++) {
			if (Claims.get(i).getName() == claimname) {
				Claims.remove(i);
			}
		}
		notifyListeners();
	}
	
	public boolean contains(Claim claim) {
		return Claims.contains(claim);
	}
	
	public int size() {
		return Claims.size();
	}
	
	//ALL Listener functions
	private ArrayList<Listener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
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

}
