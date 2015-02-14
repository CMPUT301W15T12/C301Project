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
		TagListController.addTag(tag);
		
	}
	public ArrayList<Tags> getTagList() {
		ArrayList<Tags> tagList = TagListController.getTagList();
		return tagList;
	}
	public boolean checkTag(Tags three) {
		return TagListController.checkTags(three);
		
	}
	public void removeTag(int pos) {
		TagListController.rmTag(pos);
		
	}
	public void addTagAt(int i, Tags tag) {
		TagListController.addTagAt(i, tag);
	}
}
