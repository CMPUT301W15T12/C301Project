package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

public class TagListController {

	public static void addTag(Tags tag) {
		TagList.addTag(tag);
		
	}

	public static ArrayList<Tags> getTagList() {
		return TagList.getTagList();
		
	}

	public static boolean checkTags(Tags tag) {
		return TagList.isPresent(tag);
	}

	public static void rmTag(int pos) {
		TagList.removeTag(pos);
		
	}

	public static void addTagAt(int i, Tags tag) {
		TagList.addTagAt(i, tag);
		
	}

}
