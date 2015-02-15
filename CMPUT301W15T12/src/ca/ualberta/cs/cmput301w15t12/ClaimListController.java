package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

public class ClaimListController {

	public static void addTag(Tags tag) {
		Claim.addTag(tag);
		
	}

	public static ArrayList<Tags> getTagList() {
		return Claim.getTags();
		
	}

	public static boolean checkTags(Tags tag) {
		return Claim.isPresent(tag);
	}

	public static void rmTag(int pos) {
		Claim.removeTag(pos);
		
	}

	public static void addTagAt(int i, Tags tag) {
		Claim.addTagAt(i, tag);
		
	}
}
