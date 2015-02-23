package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

public class TagList {

	public static ArrayList<Tags> tags;
	public ArrayList<Listener> listeners;

	public TagList() {
		this.tags = new ArrayList<Tags>();	
		this.listeners = new ArrayList<Listener>();
	}

	public ArrayList<Tags> getClaims() {
		return tags;
	}
	
	public void remove(Tags tag) {
		//TODO
	}
	
	public void add(Tags tag){
		//TODO check for name duplicates
		tags.add(tag);
	}
	
	private ArrayList<Listener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	public void addToClaim(Claim claim, Tags tag){
		//TODO method
	}
	
	public static ArrayList<Tags> getTagList(){
		return tags;
	}

	public static void addTag(Tags three) {
		tags.add(three);
	}

	public static boolean isPresent(Tags three) {
		return tags.contains(three);
	}
}
