package ca.ualberta.cs.cmput301w15t12.test;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301w15t12.AddClaimActivity;
import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimList;
import ca.ualberta.cs.cmput301w15t12.TagActivity;
import ca.ualberta.cs.cmput301w15t12.Tags;
import android.test.ActivityInstrumentationTestCase2;

public class TagTest extends ActivityInstrumentationTestCase2<AddClaimActivity> {
	//This code is for testing
	AddClaimActivity activity;
	
	public TagTest() {
		super(AddClaimActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		 activity = getActivity();
	}
	
	public void testFilterbyTag(){
		Tags tag = new Tags("filter_test","this is a filter test");
		
		for(Claim claim:ClaimList.getClaims()){
			if(claim.isPresent(tag)){
				ClaimList.addFiltered(claim);
			}
		}
		assertTrue("added failed", ClaimList.getFiltered().size()>0);
	}

	public void testAddTag(){
		Claim claim = new Claim("hello", null, null, null, null, null);
		//Testing adding a tag.  assume redundancy check works.//tested below...
		Tags three = new Tags("test","testTag description");
		
		int test = activity.getTagList(claim).size();
		if (!activity.checkTag(claim,three)){
			activity.addTag(claim,three);
			assertTrue("check if something adds",activity.getTagList(claim).size()>0);
			assertTrue("add works too well. too many items added",activity.getTagList(claim).size() == 1);
			activity.addListTag(claim,three);
			assertTrue("check if something adds",activity.getTagListList().size()>0);
			
		}
		assertTrue("this tag is already in list",activity.getTagList(claim).size() == test);
	}
	
	public void testDoubleTag(){
		Claim claim = new Claim("hello", null, null, null, null, null);
		// testing for double tags.  does it work. I am assuming my ADD TAG works at this point.//
		int testSize = activity.getTagList(claim).size();
		Tags tag2 = new Tags("tag2","testTag description2");
		Tags tag3 = new Tags("tag3","testTag description3");
		activity.addTag(claim,tag2);
		activity.addTag(claim,tag2);
		Boolean testPresence = activity.checkTag(claim,tag2);
		Boolean testPresence2 = activity.checkTag(claim,tag3);
		assertTrue("Added the tag twice", activity.getTagList(claim).size() == testSize+1);
		assertTrue("Added the tag again", testPresence == true);
		assertTrue("Contains tag 3", testPresence2 == false);
	}
	
	public void testEditTag(){
		Claim claim = new Claim("hello", null, null, null, null, null);
		Tags editTag = activity.getTagList(claim).get(0); 
		//Assume add and present work// now we use them to edit the tags
		activity.removeTag(claim, 0);
		assertTrue("No longer there", activity.checkTag(claim,editTag) == false);
		String old_name = editTag.getName();
		String new_name = "hahaha";
		editTag.setName(new_name);
		assertTrue("name is the same", editTag.getName() != old_name);
		activity.addTagAt(claim,0,editTag);
		assertTrue("is at 0", activity.getTagList(claim).get(0).getName() == new_name);
	}
	
	public void testDeleteTag(){
		//ASSUME ALL ELSE WORKS> this deletes as tag and checks if it is still in the list
		Claim claim = new Claim("hello", null, null, null, null, null);
		int not_there = activity.getTagList(claim).size();
		Tags to_remove = activity.getTagList(claim).get(0);
		activity.removeTag(claim,0);
		assertTrue("stil there", activity.checkTag(claim,to_remove) == false);
		assertTrue("not there any more????", activity.getTagList(claim).size() == not_there-1);
	}
}
