package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

public class ClaimListController {
	
	public static void addTag(Claim claim, String tag) {
		claim.addTag(tag);
	}

	public static ArrayList<String> getTagList(Claim claim) {
		return claim.getTagList();
		
	}

	public static boolean checkTags(Claim claim, String tag) {
		return claim.containsTag(tag);
	}

	public static void rmTag(Claim claim, int pos) {
		claim.removeTag(pos);
	}
}
