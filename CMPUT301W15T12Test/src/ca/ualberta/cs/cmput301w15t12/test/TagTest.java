
package ca.ualberta.cs.cmput301w15t12.test;

import java.util.Date;

import android.test.ActivityInstrumentationTestCase2;
import ca.ualberta.cs.cmput301w15t12.AddClaimActivity;
import ca.ualberta.cs.cmput301w15t12.AlreadyExistsException;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.User;

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
		for (int i = 0; i < 10; i++) {
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
}