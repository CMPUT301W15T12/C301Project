
package ca.ualberta.cs.cmput301w15t12.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301w15t12.AddClaimActivity;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimList;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.User;
import ca.ualberta.cs.cmput301w15t12.UserListController;

public class TagTest extends ActivityInstrumentationTestCase2<AddClaimActivity> {
	public TagTest() {
		super(AddClaimActivity.class);
	}

	//US03.01.01 As a claimant, I want to give an expense claim one zero or more alphanumeric tags, so that claims can be organized by me into groups.
	public void testAddTag() {
		// Create new claim and obtain tags list
		Claim claim = new Claim("name", new Date(), new Date(), "description", new User("Test", "User"), 0); 
		
		// New claim should have no tags
		assertEquals("New claim has tags", 0, claim.getTagList().size());
		
		// Test populating tags list
		for (int i = 1; i <= 10; i++) {
			try {
				String s = String.valueOf(i);
				
				// Add new tag
				claim.addTag(s);
				
				// Make sure tag was properly added
				assertTrue("Tag was not added", claim.getTagList().contains(s));
				
				// Test adding duplicate tag
				try {
					// Read same tag
					claim.addTag(s);
					fail("Should have thrown an exception when adding duplicate tags");
				} catch (Exception e) {
					// Already exists exception thrown when adding duplicate tag
				}
				
				// Check size of tags list
				assertEquals("Tags list was incorrectly populated", i, claim.getTagList().size());
			} catch (Exception e){
				// Already exists exception thrown when adding new tag
				fail("Shouldn't have thrown an exception adding new tags");
			}
		}
	}
	
	public void testDeleteTag() throws AlreadyExistsException{
		// Create new claim
		Claim claim = new Claim("name", new Date(), new Date(), "description", new User("Test", "User"), 0); 
		
		// Populate tags list with new unique tags
		for (int i = 1; i <= 10; i++) {
			String s = String.valueOf(i);
			claim.addTag(s);
		}
		
		// Make sure tags list is populated
		assertTrue("Tags list empty after populating", claim.getTagList().size() > 0);
		
		while (claim.getTagList().size() > 0) {
			// Get tag at index 0
			String tag = claim.getTagList().get(0);
			
			// Remove tag
			claim.removeTag(0);
			
			// Make sure tag was removed
			assertFalse("Claim still has removed tag", claim.getTagList().contains(tag));
		}
		
		// Make sure tags list properly emptied
		assertEquals("Tags list not fully emptied", 0, claim.getTagList().size());
	}
	
	//US03.02.01 As a claimant, I want to manage my personal use of tags by listing the available tags, adding a tag, renaming a tag, and deleting a tag.
	public void testPersonalTags () {
		// Create new User
		User u = new User("test", "account");
		UserListController ul = new UserListController();

		// Add tags to u
		for (int i = 1; i <= 10; i++) {
			u.addTag(String.valueOf(i));
			
			// Make sure the personal tag was added correctly 
			assertTrue("Tag was not properly added to user", u.getTagList().contains(String.valueOf(i)));
			
			// Edit tag (by first removing old tag and adding new
			try {
				u.removeTag(String.valueOf(i));
				
				// Make sure tag was removed
				assertFalse("Tag was not properly removed from user", u.getTagList().contains(String.valueOf(i)));
			} catch (Exception e) {
				// Exception thrown when removing an existing tag
				fail("Shouldn't have failed removing an existing tag");
			}
			
			// Add edited tag
			u.addTag(String.valueOf(i+10));
			
			// Make sure edited personal tag was added correctly 
			assertTrue("Tag was not properly edited", u.getTagList().contains(String.valueOf(i+10)));
		}
		
		// Remove all personal tags 
		while (u.getTagList().size() > 0) {
			u.removeTag(u.getTagList().get(0));
		}
		
		// Make sure tag is empty
		assertEquals("Tags list was not properly emptied", 0, u.getTagList().size());
		
		// Test removing non-existent tag
		try {
			u.removeTag("test");
			
			// Exception not thrown when removing non-existent tag
			fail("Should have thrown exception removing non-existent tag");
		} catch (Exception e) {
			// Exception thrown when removing non-existent tag
		}
	}
	
	// US03.03.01 As a claimant, I want to filter the list of expense claims by tags, to show only those claims that have at least one tag matching any of a given set of one or more filter tags.
	public void testFilterByTags () throws AlreadyExistsException {
		String username = "Username";
		int max = 10;
		
		// Create user and claim list controller
		User u = new User(username, "Password");
		ClaimListController clt = new ClaimListController();
		
		// Populate claims list
		for (int i = 1; i <= max; i++) {
			// Add new claim to claims list
			int claimID = clt.addClaim(String.valueOf(i), new Date(), new Date(), "description", u);
			
			// Add tags to claim ({1, ..., i})
			for (int j = 1; j <= i; j++) {
				clt.addTagToClaim(claimID, String.valueOf(j));
			}
		}
		
		// Test filtering by tags
		for (int i = 1; i <= max; i++ ) {
			ArrayList<String> tags = new ArrayList<String>();
			
			for (int j = i; j <= max; j++) {
				// Add tag to filter tags list
				tags.add(String.valueOf(j));

				// Filtered list
				ArrayList<Claim> fcl = clt.filterByTag(username, tags);
				
				// Make sure the filtered tags list is correct
				assertEquals("Filtered by tags list has the wrong number of claims", max - (i-1), fcl.size());
			}
		}
	}
}