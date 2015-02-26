/*
Travel App: Keeps tracks of expenses and claims for various trips.

Copyright [2015] Sarah Van Belleghem vanbelle@ualberta.ca
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package ca.ualberta.cs.cmput301w15t12;

import java.io.IOException;

import ca.ualberta.cs.cmput301w15t12.Exceptions.AlreadyExistsException;

//static way to call the data stored by the claimlist manager
public class ClaimListController
{

	private static ClaimList claimlist = null;

	static public ClaimList getClaimList() {
		if (claimlist == null) {
			try {
				claimlist = ClaimListManager.getManager().loadClaimList();
				claimlist.addListener(new Listener() {
					
					@Override
					public void update() {
						saveClaimList();
					}
				});
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize ClaimList from ClaimListManager");
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Could not deserialize ClaimList from ClaimListManager");
			}
		}
		return claimlist;
	}
	
	static public void saveClaimList() {
		try {
			ClaimListManager.getManager().saveClaimList(getClaimList());
		} catch (IOException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Could not deserialize ClaimList from ClaimListManager");
		}
	}
	
	public void sort() {
		getClaimList().sort();
	}

	public void addClaim(Claim claim) throws AlreadyExistsException {
		getClaimList().addClaim(claim);
	}
}
