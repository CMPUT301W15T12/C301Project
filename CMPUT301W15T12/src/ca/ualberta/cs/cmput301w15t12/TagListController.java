package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

public class TagListController {

	public static void addTag(Tags three) {
		if(!TagList.isPresent(three)){
			TagList.addTag(three);
		}
		
	}

	public static ArrayList<Tags> getTagList() {
		return TagList.getTagList();
	}

}
