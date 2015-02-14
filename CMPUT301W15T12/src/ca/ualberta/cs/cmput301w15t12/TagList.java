package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

public class TagList {
	static ArrayList<Tags> tagList;
	
	public void TagList(){
		tagList = new ArrayList<Tags>();
	}
	
	public static void addTag(Tags tag){
		if(!isPresent(tag)){
			tagList.add(tag);
		}
	}
	
	public static boolean isPresent(Tags tag){
		return tagList.contains(tag);
	}

	public static ArrayList<Tags> getTagList() {

		return tagList;
	}

	public static void removeTag(int pos) {
		tagList.remove(pos);
		
	}

	public static void addTagAt(int i, Tags tag) {
		tagList.add(i, tag);
	}
}
