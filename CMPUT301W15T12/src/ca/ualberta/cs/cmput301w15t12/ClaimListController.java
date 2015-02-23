package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

public class ClaimListController {
	
	public static void addTag(Claim claim, Tags tag) {
		claim.addTag(tag);
	}

	public static ArrayList<Tags> getTagList(Claim claim) {
		return claim.getTags();
		
	}

	public static boolean checkTags(Claim claim, Tags tag) {
		return claim.isPresent(tag);
	}

	public static void rmTag(Claim claim, int pos) {
		claim.removeTag(pos);
	}

	public static void addTagAt(Claim claim, int i, Tags tag) {
		claim.addTagAt(i, tag);
		
	}
}
