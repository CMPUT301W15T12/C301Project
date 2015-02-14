package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.TagActivity;
import ca.ualberta.cs.cmput301w15t12.Tags;

import android.test.ActivityInstrumentationTestCase2;

public class TagTest extends ActivityInstrumentationTestCase2<TagActivity> {
	//This code is for testing
	TagActivity activity;
	
	public TagTest() {
		super(TagActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		 activity = getActivity();
	}

	public void testAddTag(){
		//Testing adding a tag.  assume redundncy check works.//tested below...
		Tags three = new Tags("test","testTag description");
		
		int test = activity.getTagList().size();
		if (activity.checkTag(three)){
			activity.addTag(three);
			assertTrue("check if something adds",activity.getTagList().size()>0);
			assertTrue("add works too well. too many items added",activity.getTagList().size() == 1);
		}
		assertTrue("this tag is already in list",activity.getTagList().size() == test);
			
	}
	
	public void testDoubleTag(){
		// testing for double tags.  does it work. I am assuming my ADD TAG works at this point.//
		int testSize = activity.getTagList().size();
		Tags tag2 = new Tags("tag2","testTag description2");
		Tags tag3 = new Tags("tag3","testTag description3");
		activity.addTag(tag2);
		activity.addTag(tag2);
		Boolean testPresence = activity.checkTag(tag2);
		Boolean testPresence2 = activity.checkTag(tag3);
		assertTrue("Added the tag twice", activity.getTagList().size() == testSize+1);
		assertTrue("Added the tag again", testPresence == true);
		assertTrue("Contains tag 3", testPresence2 == false);
	}
	
	public void testEditTag(){
		Tags editTag = activity.getTagList().get(0); 
		//Assume add and present work// now we use them to edit the tags
		activity.removeTag(0);
		assertTrue("No longer there", activity.checkTag(editTag) == false);
		String old_name = editTag.getName();
		String new_name = "hahaha";
		editTag.setName(new_name);
		assertTrue("name is the same", editTag.getName() != old_name);
		activity.addTagAt(0,editTag);
		assertTrue("is at 0", activity.getTagList().get(0).getName() == new_name);
	}
	
	public void testDeleteTag(){
		//ASSUME ALL ELSE WORKS> this deletes as tag and checks if it is still in the list
		int not_there = activity.getTagList().size();
		Tags to_remove = activity.getTagList().get(0);
		activity.removeTag(0);
		assertTrue("stil there", activity.checkTag(to_remove) == false);
		assertTrue("not there any more????", activity.getTagList().size() == not_there-1);
	}
}
