/**
 * This  Activity allows a user to add or edit a given claim
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddClaimActivity extends Activity
{
	//Date field variables
	private EditText startDate;
	private EditText endDate;
	private EditText editTextName;
	private EditText tags;
	private EditText editTextDescription;
	private DatePickerDialog fromDatePickerDialog;
	private DatePickerDialog toDatePickerDialog;
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

	private ClaimListController CLC = new ClaimListController();
	private User user;
	private String Username;
	private ArrayList<String> tagsArrayList = new ArrayList<String>();
	private int id;
	private Claim claim;
	private TabClaimActivity parentActivity;
	private boolean selected[];

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_claim);

		//username of user passed along from list choice activity
		Username = getIntent().getExtras().getString("username");
		final String option = getIntent().getExtras().getString("option");

		//initializes edittext variables globally
		editTextName = (EditText) findViewById(R.id.EnterClaimName);
		editTextDescription = (EditText) findViewById(R.id.EnterDescription);
		tags = (EditText) findViewById(R.id.EnterTags);
		startDate = (EditText) findViewById(R.id.EnterStartDate);
		endDate = (EditText) findViewById(R.id.EnterEndDate);

		//initializes the date fields
		endDate.setInputType(InputType.TYPE_NULL);
		startDate.setInputType(InputType.TYPE_NULL);
		setDateTimeField();

		//if the user is editing an existing claim, set all existing attributes
		if (option.equals("Edit")){
			id = getIntent().getIntExtra("claim_id",1000000);
			claim = CLC.getClaim(id);
			tagsArrayList = claim.getTagList();
			startDate.setText(df.format(claim.getStartDate()));
			endDate.setText(df.format(claim.getEndDate()));
			editTextDescription.setText(claim.getDescription());
			editTextName.setText(claim.getName());
			String t = claim.toStringTagList(claim.getTagList());
			tags.setText(t);
		}

		//gets the user corresponding to the UserName
		for (int i = 0; i < UserListController.getUserList().size(); i++) {
			if (UserListController.getUserList().get(i).getUserName().equals(Username)) {
				user = UserListController.getUserList().get(i);
			}
		}

		//get tab parent activity
		parentActivity = (TabClaimActivity) this.getParent();

		//clickable button creates  or edit a claim and takes the user back to page they came from
		Button donebutton = (Button) findViewById(R.id.buttonsaveClaim);
		donebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				try {
					if (option.equals("Add")){
						addClaim();
					} else {
						editClaim(); 
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});

	}

	//saves any changes the user make to the claim and calls finish
	public void editClaim() throws ParseException {
		//edit Claim	
		//Convert EditTexts to Strings and Dates
		String name = editTextName.getText().toString();
		String description = editTextDescription.getText().toString();

		//check for incomplete fields
		if (startDate.getText().toString().equals("") ||endDate.getText().toString().equals("") || name.equals("") || description.equals("")) {
			Toast.makeText(AddClaimActivity.this,"Incomplete Fields", Toast.LENGTH_SHORT).show();	
		} else {

			Date sdate = df.parse(startDate.getText().toString());
			Date edate = df.parse(endDate.getText().toString());

			//ensures end date is after start date
			if (!sdate.after(edate)){
				//gets destinations from other tab
				ArrayList<Destination> destination = parentActivity.getDestination();

				//set new values
				claim.setAll(name, sdate, edate, description, new ArrayList<String>(), destination);
				
				for (int i = 0; i < tagsArrayList.size(); i++){
					try{
						CLC.addTagToClaim(id, tagsArrayList.get(i));
					} catch (AlreadyExistsException e){
						e.printStackTrace();
					}
				}

				//toasts the user and finishes
				Toast.makeText(AddClaimActivity.this,"Claim Updated", Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(this,"End Date needs to be after Start Date", Toast.LENGTH_SHORT).show();
			}
		}

	}	

	//adds a new claim and goes back to claim list
	public void addClaim() throws ParseException  {
		String name = editTextName.getText().toString();
		String description = editTextDescription.getText().toString();
		//ensures no empty fields for date(s), name and description
		if (startDate.getText().toString().equals("") ||endDate.getText().toString().equals("")|| name.equals("") || description.equals("")) {
			Toast.makeText(AddClaimActivity.this,"Incomplete Fields", Toast.LENGTH_SHORT).show();	
		} else {
			//Initializing variables
			Date sdate = df.parse(startDate.getText().toString());
			Date edate = df.parse(endDate.getText().toString());

			//ensures end date is after start date
			if (sdate.after(edate)) {
				Toast.makeText(this, "End Date needs to be after Start Date", Toast.LENGTH_SHORT).show();
			} else {
				//create claim
				id = CLC.addClaim(name, sdate, edate, description, this.user);
				ArrayList<Destination> destination = parentActivity.getDestination();
				CLC.getClaim(id).setDestination(destination);

				//add Tag to Claim
				for (int i = 0; i<  tagsArrayList.size(); i++){
					try {
						CLC.addTagToClaim(id, tagsArrayList.get(i));
					} catch (AlreadyExistsException e) {
					}
				}

				//toast finished
				Toast.makeText(AddClaimActivity.this,"Claim Saved.", Toast.LENGTH_SHORT).show();	
				finish();
			}
		}
	}

	/** 
	 * @param view
	 */
	//initialize the choose from previous tags dialogue
	public void onClickTags(View view){
		ArrayList<String> tagList = user.getTagList();
		AlertDialog.Builder builder = new AlertDialog.Builder(AddClaimActivity.this);
		final String[] userTags = (String[]) tagList.toArray(new String[tagList.size()]);
		builder.setTitle("Choose Tags");

		//check the items already included
		selected = new boolean[tagList.size()];
		for (int i = 0;  i < tagList.size(); i++) {
			if (tagsArrayList.contains(userTags[i])) {
				selected[i] = true;
			}
		}

		//when an item is clicked
		builder.setMultiChoiceItems(userTags, selected,
				new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {	
				if (isChecked && !tagsArrayList.contains(userTags[which])) {
					// If the user checked the item, add it to the selected items
					tagsArrayList.add(userTags[which]);
				} else {
					//if the item was unchecked remove the item
					tagsArrayList.remove(userTags[which]);
				}
			}
		});
		//allow the user to add a new tag
		builder.setPositiveButton("Add New", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				addTagDialog();
			}

		});
		//save any added tags from the user
		builder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				//print the tags associated to the claim
				String block = "";
				for (int i = 0; i < tagsArrayList.size(); i++) {
					block += tagsArrayList.get(i).toString();
					if (i != tagsArrayList.size() - 1) {
						block += ", ";
					}
				}
				tags.setText(block);
			}
		});
		builder.show();

	}

	//initialize the adding a tag dialogue
	protected void addTagDialog(){
		LayoutInflater layoutInflater = LayoutInflater.from(AddClaimActivity.this);
		View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddClaimActivity.this);
		alertDialogBuilder.setView(promptView);

		final EditText editText = (EditText) promptView.findViewById(R.id.editTextTagName);
		
		alertDialogBuilder.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				//adds tags to userList and claimList
				if (!tagsArrayList.contains(editText.getText().toString())) {
					tagsArrayList.add(editText.getText().toString());
				}
				if (!user.getTagList().contains(editText.getText().toString())) {
					user.addTag(editText.getText().toString());
				}
				tags = (EditText) findViewById(R.id.EnterTags);
				String block = "";
				for (int i = 0; i < tagsArrayList.size(); i++) {
					block += tagsArrayList.get(i).toString();
					if (i != tagsArrayList.size() - 1) {
						block += ", ";
					}
				}
				tags.setText(block);
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


	//http://androidopentutorials.com/android-datepickerdialog-on-edittext-click-event/ 2015/01/26
	//initialize calendar view dialogues
	private void setDateTimeField() {
		startDate.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				fromDatePickerDialog.show();
			}
		});
		endDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				toDatePickerDialog.show();   
			}
		});

		Calendar newCalendar = Calendar.getInstance();
		fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				startDate.setText(df.format(newDate.getTime()));
			}

		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				endDate.setText(df.format(newDate.getTime()));
			}

		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_claim, menu);
		return true;
	}

}
