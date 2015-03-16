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
	
	//sort/clear created by sarah - leave until after demo
	private ArrayList<Claim> sort(ArrayList<Claim> list)
	{

		Collections.sort(list, new Comparator<Claim>() {
			@Override
			public int compare(Claim lhs, Claim rhs){
				return lhs.getStartDate().compareTo(rhs.getStartDate());
			}
		});
		return list;
	}
	
	public void clear()
	{
		claims.clear();
	}

	//==================Filters==================
	public ArrayList<Claim> filterByClaimant(User claimant) {
		ArrayList<Claim> filteredClaimList = new ArrayList<Claim>();
		for (int i = 0; i < ClaimList.claims.size(); i++) {
			if (ClaimList.claims.get(i).getClaimant().equals(claimant)) {
				filteredClaimList.add(ClaimList.claims.get(i));
			}
		}
		//leave sort unil after demo
		return sort(filteredClaimList);
	}

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

	public ArrayList<Claim> filterByTag(String tag){
		ArrayList<Claim> filteredClaimList = new ArrayList<Claim>();
		for (int i = 0; i < ClaimList.claims.size(); i++) {
			if (ClaimList.claims.get(i).containsTag(tag)){
				filteredClaimList.add(ClaimList.claims.get(i));
			}
		}
		//leave sort until after demo 
		return sort(filteredClaimList);
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
