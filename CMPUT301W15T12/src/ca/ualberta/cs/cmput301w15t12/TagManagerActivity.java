/**
 * TabManagerActivity allows the user to globally edit/add/delete tags they are using.
 * 
 *   Copyright [2015] CMPUT301W15T12 https://github.com/CMPUT301W15T12
 *   licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *   
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   @author vanbelle
*/
package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TagManagerActivity extends Activity
{
	public String tag;
	public String newTag;
	public User user;
	public String username;
	public ArrayAdapter<String> adapter;
	public ClaimListController CLC;
	public ArrayList<String> tags;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tag_manager);
		CLC = new ClaimListController();

		//username of user passed along from list choice activity 
		username = getIntent().getExtras().getString("username");

		//gets the use corresponding to the UserName
		for (int i = 0; i < UserListController.getUserList().getUsers().size(); i++) {
			if (UserListController.getUserList().get(i).getUserName().equals(username)) {
				user = UserListController.getUserList().get(i);
			}
		}
		if (user == null) {
			Toast.makeText(this, "no User found", Toast.LENGTH_SHORT).show();
		}

		//returns the user to claimlist page
		Button done = (Button) findViewById(R.id.buttonDoneTagManager);
		done.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();		
			}
		});

		//adds a new tag to the user tag list
		Button add = (Button) findViewById(R.id.buttonAddTag);
		add.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				EditText tagedit = (EditText) findViewById(R.id.editTextNewTag);
				if (!user.getTagList().contains(tagedit.getText().toString())) {
					user.addTag(tagedit.getText().toString());
					tagedit.setText("");
					adapter.notifyDataSetChanged();
				} else if(tagedit.getText().toString().equals("")) {
					Toast.makeText(TagManagerActivity.this, "Cannot add an empty tag", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(TagManagerActivity.this,"That tag already exists", Toast.LENGTH_SHORT).show();
				}
			}
		});

		//displays existing tags
		tags = user.getTagList();
		ListView list = (ListView) findViewById(R.id.listViewTags);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, tags);
		list.setAdapter(adapter);

		//allows a user to edit a tag
		list.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				tag = tags.get(arg2);
				editTagDialog();
			}

		});
		//allows a user to delete a tag
		list.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3)
			{
				tag = tags.get(arg2);
				deleteTagDialog();
				return false;
			}
		});
	}


	/**
	 * the editing a tag dialog, displays the existing tag and confirms a global change
	 */
	protected void editTagDialog(){
		//shows the claim as it is
		LayoutInflater layoutInflater = LayoutInflater.from(TagManagerActivity.this);
		View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TagManagerActivity.this);
		alertDialogBuilder.setView(promptView);
		final EditText editText = (EditText) promptView.findViewById(R.id.editTextTagName);
		editText.setText(tag);
		// user can change tag or cancel edit
		alertDialogBuilder.setCancelable(false)
		.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
			//change the old tag to the new tag everywhere
			public void onClick(DialogInterface dialog, int id) {
				newTag = editText.getText().toString();
				user.getTagList().remove(tag);
				user.addTag(newTag);
				for (int i = 0; i < CLC.getAllClaims().size(); i++) {
					for (int j = 0; j < CLC.getAllClaims().get(i).getTagList().size(); j++) {
						if (CLC.getAllClaims().get(i).getClaimant().getUserName().equals(username) &&
								CLC.getAllClaims().get(i).getTagList().get(j).equals(tag)) {
							CLC.getAllClaims().get(i).getTagList().remove(j);
							CLC.getAllClaims().get(i).getTagList().add(newTag);
						}
					}
				}
				adapter.notifyDataSetChanged();
			}
		})
		.setNegativeButton("Cancel",
				//go back to the manager
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	/**
	 * the confirmation delete a tag dialog
	 */
	protected void deleteTagDialog(){
		//confirm delete tag everywhere or cancel
		AlertDialog.Builder adb = new AlertDialog.Builder(TagManagerActivity.this);
		adb.setMessage("Delete "+tag+" Everywhere?");
		adb.setCancelable(true);
		adb.setCancelable(true)
		.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//delete tag everywhere
				user.getTagList().remove(tag);
				for (int i = 0; i < CLC.getAllClaims().size(); i++) {
					for (int j = 0; j < CLC.getAllClaims().get(i).getTagList().size(); j++) {
						if (CLC.getAllClaims().get(i).getClaimant().getUserName().equals(username) &&
								CLC.getAllClaims().get(i).getTagList().get(j).equals(tag)) {
							CLC.getAllClaims().get(i).getTagList().remove(j);
						}
					}
				}
				adapter.notifyDataSetChanged();
			}
		})
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//don't delete anywhere
				dialog.cancel();
			}
		});
		AlertDialog alert = adb.create();
		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag_manager, menu);
		return true;
	}

}
