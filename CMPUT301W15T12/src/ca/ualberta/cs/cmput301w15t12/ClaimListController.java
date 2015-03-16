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
 *   @author vanbelle
*/

package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Date;


public class ClaimListController{

	private ClaimList claimList;
	
	public ClaimListController(){
		this.claimList = new ClaimList();
	}
	
	public void clear() {
		claimList.clear();
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
