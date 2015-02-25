package ca.ualberta.cs.cmput301w15t12.Activities;

import java.util.ArrayList;

import ca.ualberta.cs.cmput301w15t12.Claim;
import ca.ualberta.cs.cmput301w15t12.ClaimListController;
import ca.ualberta.cs.cmput301w15t12.TagListController;
import ca.ualberta.cs.cmput301w15t12.Tags;

import android.app.Activity;
import android.os.Bundle;

public class TagActivity extends Activity{

	public ArrayList<Tags> getTags(){
		return null;
	}
	
	public boolean isPresentTag(){
		return true;
	}
	
	public void addTag(Claim claim, Tags tag) {
		ClaimListController.addTag(claim,tag);
		
	}
	public ArrayList<Tags> getTagList(Claim claim) {
		ArrayList<Tags> tagList = ClaimListController.getTagList(claim);
		return tagList;
	}
	public boolean checkTag(Claim claim, Tags three) {
		return ClaimListController.checkTags(claim, three);
		
	}
	public void removeTag(Claim claim, int pos) {
		ClaimListController.rmTag(claim, pos);
		
	}
	public void addTagAt(Claim claim, int i, Tags tag) {
		ClaimListController.addTagAt(claim, i, tag);
	}
	public void addListTag(Claim claim,Tags three) {
		TagListController.addTag(three);
		
	}
	public ArrayList<Tags> getTagListList() {
		ArrayList<Tags> tagList = TagListController.getTagList();
		return tagList;
	}
}
