
package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Date;


public class ClaimListController{

	private ClaimList claimList;
	
	public ClaimListController(){
		this.claimList = new ClaimList();
	}
	
	//==================Get/Add/Remove/Contains/Size==================
	public Claim getClaim(int id){
		return this.claimList.getClaim(id);
	}
	
	public ArrayList<Claim> getAllClaims(){
		return this.claimList.getAllClaims();
	}
	
	//return the id of the newly created claim
	public int addClaim(String name, Date startDate, Date endDate, String description, User Claimant){
		return this.claimList.addClaim(name, startDate, endDate, description, Claimant);
	}
	
	public void removeClaim(int id){
		this.claimList.removeClaim(id);
	}
	
	public boolean contains(Claim claim){
		return this.claimList.contains(claim);
	}
	
	public int size(){
		return this.claimList.size();
	}
	//==================Stable Claim Getter/Setter==================
	public void addTagToClaim(int claimId, String tag) throws AlreadyExistsException{
		Claim tagetClaim = this.claimList.getClaim(claimId);
		tagetClaim.addTag(tag);
	}
	public ArrayList<String> getTagListFromClaim(int claimId){
		return this.claimList.getClaim(claimId).getTagList();
	}
	
	//==================Filters==================
	public ArrayList<Claim> filterByClaimant(User claimant) {
		return this.claimList.filterByClaimant(claimant); 
	}
	
	public ArrayList<Claim> filterByStatus(String status) {
		return this.claimList.filterByStatus(status);
	}
	
	public ArrayList<Claim> filterByTag(String tag){
		return this.claimList.filterByTag(tag);
	}
	
	//==================Listener==================
	public void addListener(Listener listener) {
		this.claimList.addListener(listener);
	}
	
	public void removeListener(Listener listener) {
		this.claimList.removeListener(listener);
	}
}
