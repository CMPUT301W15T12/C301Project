/**
 * This  data model models all the functions of a claimlist, including filtering, 
 * sorting, and transforming subsets of the list into strings.
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;



public class ClaimList{

	private static ArrayList<Claim> claims;
	private static ArrayList<Listener> listeners;
	private static int nextUnassignedId;
	/**
	 * constructor initializes the claimlist making sure its not null
	 * and initializes the claim ids
	 */
	public ClaimList() {
		if (ClaimList.claims==null){
			claims = new ArrayList<Claim>();
			listeners = new ArrayList<Listener>();
			ClaimList.nextUnassignedId = 0;
		}
	}

	//==================Get/Add/Remove/Contains/Size==================
	/**
	 * returns the claim with the corresponding id
	 * @param id
	 * @return
	 */
	public Claim getClaim(int id) {
		for (int i = 0; i < claims.size(); i++) {
			if (claims.get(i).getId() == id) {
				return claims.get(i);
			}
		}
		return null; //return null if not found
	}
	/**
	 * 
	 * @return all the claim
	 */
	public ArrayList<Claim> getAllClaims(){
		return ClaimList.claims;
	}
	/**
	 * adds a new claim with the input values
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @param description
	 * @param Claimant
	 * @return the claim id associated with the newly created claim
	 */
	public int addClaim(String name, Date startDate, Date endDate, String description, User Claimant){
		int id = getNextUnassignedId();
		incrementeNextUnassignedId(); //important
		Claim aNewClaim = new Claim(name,startDate,endDate, description, Claimant, id);
		claims.add(aNewClaim);
		notifyListeners();
		return id;		//return the id of the newly created claim
	}
	/**
	 * deletes the claim with corresponding claim id
	 * @param id
	 */
	public void removeClaim(int id) {
		for (int i = 0; i < claims.size() ; i++) {
			if (claims.get(i).getId() == id) {
				claims.remove(i);
			}
		}
		notifyListeners();
	}
	/**
	 * checks if the claim list contains a particular claim
	 * @param claim
	 * @return true if it does, otherwise false
	 */
	public boolean contains(Claim claim) {
		return claims.contains(claim);
	}
	/**
	 * 
	 * @return size of the claim list
	 */
	public int size() {
		return claims.size();
	}

	/**
	 * sorts an array;ist of claims in descending order of start date
	 * @param list
	 * @return
	 */
	private ArrayList<Claim> sort(ArrayList<Claim> list)
	{

		Collections.sort(list, new Comparator<Claim>() {
			@Override
			public int compare(Claim lhs, Claim rhs){
				return lhs.getStartDate().compareTo(rhs.getStartDate());
			}
		});
		Collections.reverse(list);
		return list;
	}

	//clears list of claims, and sets the next id to 0.
	/**
	 * clears the claimlist of all claims
	 */
	public void clear()
	{
		claims.clear();
		ClaimList.nextUnassignedId=0;
	}

	//==================Filters==================
	/**
	 * filters for a subset of claims matching the provided user
	 * @param claimant
	 * @return an array list of claims
	 */
	public ArrayList<Claim> filterByClaimant(User claimant) {
		ArrayList<Claim> filteredClaimList = new ArrayList<Claim>();
		for (int i = 0; i < ClaimList.claims.size(); i++) {
			if (ClaimList.claims.get(i).getClaimant().equals(claimant)) {
				filteredClaimList.add(ClaimList.claims.get(i));
			}
		}
		//leave sort until after demo
		return sort(filteredClaimList);
	}
    /**
     * filters claims for claims of a particular status
     * @param status
     * @return an array list of claims
     */
	public ArrayList<Claim> filterByStatus(String status) {
		ArrayList<Claim> filteredClaimList = new ArrayList<Claim>();
		for (int i = 0; i < ClaimList.claims.size(); i++){
			if (ClaimList.claims.get(i).getStatus().equals(status)) {
				filteredClaimList.add(ClaimList.claims.get(i));
			}
		}
		//leave sort until after demo
		return sort(filteredClaimList);
	}
	/**
	 * filters claims for claims containing a particular tag and user
	 * @param user
	 * @param tag
	 * @return an arraylist of claims
	 */
	public ArrayList<Claim> filterByTag(String user, ArrayList<String> tag){
		ArrayList<Claim> filteredClaimList = new ArrayList<Claim>();
		for (int i = 0; i < ClaimList.claims.size(); i++) {
			for (int j = 0; j < ClaimList.claims.get(i).getTagList().size(); j++){
				if (ClaimList.claims.get(i).getClaimant().getUserName().equals(user) && tag.contains(ClaimList.claims.get(i).getTagList().get(j)) && !filteredClaimList.contains(ClaimList.claims.get(i))){
					filteredClaimList.add(ClaimList.claims.get(i));
				}
			}
		}
		//leave sort until after demo 
		return sort(filteredClaimList);
	}

	//==================Listener==================
	/**
	 * 
	 * @return an arraylist of listeners
	 */
	public ArrayList<Listener> getListeners() {
		return ClaimList.listeners;
	}
	/**
	 * adds a listener to the listener list
	 * @param listener
	 */
	public void addListener(Listener listener) {
		ClaimList.listeners.add(listener);		
	}
	/**
	 * removes a listener from the listener list
	 * @param listener
	 */
	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}
	/**
	 * notifies the listeners of a change to the data
	 */
	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.update();
		}
	}
	//Do not call this unless your are ClaimListController or ESClient.
	//This method should ONLY be used by ClaimListController or ESClient for synchronizing data with the elastic server
	public void load( ArrayList<Claim> claims){
		//load the given claim arraylist
		ClaimList.claims = claims;		
		int maxId = 0;
		for (int i=0;i<claims.size();i++){
			if (claims.get(i).getId()>maxId){
				maxId = claims.get(i).getId();	
			}
		}
		//get the next unused ID
		ClaimList.nextUnassignedId = maxId+1;
	}
	//==================Private==================
	/**
	 * 
	 * @return the next unassigned id for the creation of a claim
	 */
	private int getNextUnassignedId(){
		return ClaimList.nextUnassignedId;
	}
	/**
	 * increments the id counter by one
	 */
	private void incrementeNextUnassignedId() {
		ClaimList.nextUnassignedId += 1;
	}


}
