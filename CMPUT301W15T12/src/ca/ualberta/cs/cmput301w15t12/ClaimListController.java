
package ca.ualberta.cs.cmput301w15t12;

import java.io.IOException;


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
			e.printStackTrace();
			throw new RuntimeException("Could not deserialize ClaimList from ClaimListManager");
		}
	}

	public void addClaim(Claim claim) throws AlreadyExistsException {
		getClaimList().addClaim(claim);
	}
}
