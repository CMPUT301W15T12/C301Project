/**
 * This  controller is used to confer between the claim list data model and 
 * all the activities that hat use that model.
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
 *   @author qsjiang
*/

package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Date;

import android.location.Location;


public class ClaimListController{

	private ClaimList claimList;
	/** 
	 * constructs a new claim list
	 */
	public ClaimListController(){
		this.claimList = new ClaimList();
	}
	/**
	 * clears the claim list
	 */
	public void clear() {
		claimList.clear();
	}
	
	//==================Get/Add/Remove/Contains/Size==================
	/**gets the claim corresponding to an id
	 * 
	 * @param id
	 * @return the claim of that id
	 */
	public Claim getClaim(int id){
		return this.claimList.getClaim(id);
	}
	/**
	 * returns an arraylist of all the claims
	 * @return
	 */
	public ArrayList<Claim> getAllClaims(){
		return this.claimList.getAllClaims();
	}
	
	/**
	 * adds a new claim to the claimlist
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param description
	 * @param Claimant
	 * @return the id of the newly created claim
	 */
	public int addClaim(String name, Date startDate, Date endDate, String description, User Claimant){
		return this.claimList.addClaim(name, startDate, endDate, description, Claimant);
	}
	/** 
	 * removes a claim from the claim list
	 * @param id
	 */
	public void removeClaim(int id){
		this.claimList.removeClaim(id);
	}
	/**
	 * checks if the claim list contains a certain claim
	 * @param claim
	 * @return
	 */
	public boolean contains(Claim claim){
		return this.claimList.contains(claim);
	}
	/**
	 * 
	 * @return the size of the claim list
	 */
	public int size(){
		return this.claimList.size();
	}
	//==================Stable Claim Getter/Setter==================
	/** adds a tag to an existing claim provided by the id
	 * 
	 * @param claimId
	 * @param tag
	 * @throws AlreadyExistsException if that tag is already attached to the claim
	 */
	public void addTagToClaim(int claimId, String tag) throws AlreadyExistsException{
		Claim targetClaim = this.claimList.getClaim(claimId);
		targetClaim.addTag(tag);
	}
	/**
	 * returns the list of tags for a particular claim
	 * @param claimId
	 * @return
	 */
	public ArrayList<String> getTagListFromClaim(int claimId){
		return this.claimList.getClaim(claimId).getTagList();
	}
	/**
	 * adds a destination to a claim with id = claimId
	 * @param claimId
	 * @param name
	 * @param desc
	 * @param location
	 * @throws AlreadyExistsException that destination alreasy exists for that claim
	 */
	public void addDestination(int claimId, String name, String desc, Location location) throws AlreadyExistsException {
		Claim targetClaim = this.claimList.getClaim(claimId);
		targetClaim.addDestination(new Destination(name, desc, location));
	}
	
	//==================Filters==================
	/**
	 * filters for a subset of claims matching the provided user
	 * @param claimant
	 * @return an array list of claims
	 */
	public ArrayList<Claim> filterByClaimant(User claimant) {
		return this.claimList.filterByClaimant(claimant); 
	}
    /**
     * filters claims for claims of a particular status
     * @param status
     * @return an array list of claims
     */
	public ArrayList<Claim> filterByStatus(String status) {
		return this.claimList.filterByStatus(status);
	}
	/**
	 * filters claims for claims containing a particular tag and user
	 * @param user
	 * @param tag
	 * @return an arraylist of claims
	 */
	public ArrayList<Claim> filterByTag(String claimant, ArrayList<String> tag){
		return this.claimList.filterByTag(claimant, tag);
	}
	
	//==================Listener==================
	/**add listener to claim list
	 * 
	 * @param listener
	 */
	public void addListener(Listener listener) {
		this.claimList.addListener(listener);
	}
	/**
	 * remove listener from the claim list listeners
	 * @param listener
	 */
	public void removeListener(Listener listener) {
		this.claimList.removeListener(listener);
	}
}
