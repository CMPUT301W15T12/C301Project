package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class TagActivity extends Activity{

	public ArrayList<Tags> getTags(){
		return null;
	}
	public boolean isPresentTag(){
		return true;
	}
	public void addTag(Tags tag) {
		ClaimListController.addTag(tag);
		
	}
	public ArrayList<Tags> getTagList() {
		ArrayList<Tags> tagList = ClaimListController.getTagList();
		return tagList;
	}
	public boolean checkTag(Tags three) {
		return ClaimListController.checkTags(three);
		
	}
	public void removeTag(int pos) {
		ClaimListController.rmTag(pos);
		
	}
	public void addTagAt(int i, Tags tag) {
		ClaimListController.addTagAt(i, tag);
	}
}
