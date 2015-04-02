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

		Button done = (Button) findViewById(R.id.buttonDoneTagManager);
		done.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();		
			}
		});

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

		tags = user.getTagList();
		ListView list = (ListView) findViewById(R.id.listViewTags);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, tags);
		list.setAdapter(adapter);

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


	//initialize the adding a tag dialogue
	protected void editTagDialog(){
		LayoutInflater layoutInflater = LayoutInflater.from(TagManagerActivity.this);
		View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TagManagerActivity.this);
		alertDialogBuilder.setView(promptView);
		final EditText editText = (EditText) promptView.findViewById(R.id.editTextTagName);
		editText.setText(tag);

		alertDialogBuilder.setCancelable(false)
		.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
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
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	//initialize the adding a tag dialogue
	protected void deleteTagDialog(){
		AlertDialog.Builder adb = new AlertDialog.Builder(TagManagerActivity.this);
		adb.setMessage("Delete "+tag+" Everywhere?");
		adb.setCancelable(true);
		adb.setCancelable(true)
		.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
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
