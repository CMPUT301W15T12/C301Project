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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    public ClaimListController CLC = new ClaimListController();
    public User user;
    public String Username;
    public ArrayList tagsArrayList = new ArrayList();
    public Integer id;
    public Claim claim;
	final Destination destination = new Destination();
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_claim);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
		
		//username of user passed along from list choice activity
		Username = getIntent().getExtras().getString("username");
		final String option = getIntent().getExtras().getString("option");
		
		//initializes the date fields
		startDate = (EditText) findViewById(R.id.EnterStartDate);
		endDate = (EditText) findViewById(R.id.EnterEndDate);
		endDate.setInputType(InputType.TYPE_NULL);
		startDate.setInputType(InputType.TYPE_NULL);
		setDateTimeField();
		
		if (option.equals("Edit")){
			int id = getIntent().getIntExtra("claim_id",1000000);
			claim = CLC.getClaim(id);
			//TODO fill in existing fields
			
		}
		
		//gets the user corresponding to the UserName
		for (int i = 0; i < UserListController.getUserList().size(); i++) {
			if (UserListController.getUserList().get(i).getUserName().equals(Username)) {
				user = UserListController.getUserList().get(i);
			}
		}

		
		//clickable button creates claim and takes the user back to the claim list page
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
				finish();			}
		});
		
	}
	
	public void editClaim() {
		String status = claim.getStatus();
    	Context context = this.getApplicationContext();

		//edit Claim
		if (claim.editable()){
			Claim prevClaim = claim;
			
	    	String name = new String();
	    	Date startDate = new Date();
	    	Date endDate = new Date();
	    	String description = new String();
	    	
	    	EditText editTextName = (EditText) findViewById(R.id.EnterClaimName);
	    	EditText editTextStartDate = (EditText) findViewById(R.id.EnterStartDate);
	    	EditText editTextEndDate = (EditText) findViewById(R.id.EnterEndDate);
	    	EditText editTextDescription = (EditText) findViewById(R.id.EnterDescription);	    	
	    	
	    	////Shouldn't Use this method - We would lose any existing expense items, comments, or approvers!!
//	    	//Convert XML to String
//	    	name = editTextName.getText().toString();
//	    	//startDate = editTextStartDate.getDate().toString();
//	    	//endDate = editTextEndDate.getText().toString();
//	    	description = editTextDescription.getText().toString();
//
//	    	if (name.equals("")){
//	    		name = prevClaim.getName();
//	    	}
//	    	if (startDate.equals("")){
//	    		startDate = prevClaim.getStartDate();
//	    	}
//	    	if (endDate.equals("")){
//	    		endDate = prevClaim.getEndDate();
//	    	}
//	    	claimController.addClaim(name, startDate, endDate, description, this.user);
//			claimController.removeClaim(positionClaim);
			
	    	String toastText = "Claim Updated";
	    	Toast toast = Toast.makeText(context,toastText, Toast.LENGTH_LONG);
	    	toast.show();	
		}
		
		//TOAST http://developer.android.com/guide/topics/ui/notifiers/toasts.html
		//Can't edit Claim
		else {
			CharSequence text = "No edits allowed";
			int duration = Toast.LENGTH_LONG;
			
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
		}	
	}
	
	public void addClaim() throws ParseException  {
		//Initializing variables
		Date sdate = df.parse(startDate.getText().toString());
		Date edate = df.parse(endDate.getText().toString());
		String name = new String();
		String description = new String();
		//need to add destination to claim
		Destination destination = new Destination();
		String tags = new String();
		Context context = this.getApplicationContext();
		
		//XML Inputs
		EditText editTextName = (EditText) findViewById(R.id.EnterClaimName);
		name = editTextName.getText().toString();
		EditText editTextTags = (EditText) findViewById(R.id.EnterTags);
		tags = editTextTags.getText().toString();
		CharSequence toastText;
		Toast toast = null;
		EditText editTextDescription = (EditText) findViewById(R.id.EnterDescription);
		description = editTextDescription.getText().toString();
		
		//create claim
		id = CLC.addClaim(name, sdate, edate, description, this.user);
		
		//add Tag to Claim
		for (int i = 0; i<  tagsArrayList.size(); i++){
			try {
				CLC.addTagToClaim(id, tagsArrayList.get(i).toString());
			} catch (AlreadyExistsException e) {
				e.printStackTrace();
			}
		}

		//toast finished
		toastText = "Claim Saved.";
		toast = Toast.makeText(context,toastText, Toast.LENGTH_SHORT);
		toast.show();	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_claim, menu);
		return true;
	}
	
//	public void onClickDestination(View view){
//		AlertDialog.Builder builder = new AlertDialog.Builder(AddClaimActivity.this);
//		builder.setTitle("Add Destination");
//		LayoutInflater inflater = LayoutInflater.from(AddClaimActivity.this);
//		View promptView = inflater.inflate(R.layout.destination_dialog, null);
//		builder.setView(promptView);
//		final EditText editTextDestination = (EditText) promptView.findViewById(R.id.editTextDestination);
//		final EditText editTextDescription = (EditText) promptView.findViewById(R.id.editTextDescription);
//		builder.setCancelable(false)
//			.setPositiveButton("Save", new DialogInterface.OnClickListener(){
//				@Override
//				public void onClick(DialogInterface dialog, int id){
//					destination.setDestination(editTextDestination.getText().toString());
//					destination.setDescription(editTextDescription.getText().toString());
//				}
//			})
//			.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
//				public void onClick(DialogInterface dialog, int id){
//				}
//			});
//	}
	
	
	/** 
	 * @param view
	 */
	public void onClickTags(View view){
		final ArrayList<String> tagList = new ArrayList<String>();
		AlertDialog.Builder builder = new AlertDialog.Builder(AddClaimActivity.this);
		String[] userTags = new String[tagList.size()];
		userTags = (String[]) tagList.toArray(userTags);
		final String[] finalUserTags = userTags;
		builder.setTitle("Choose Tags");
		builder.setMultiChoiceItems(userTags, null,
				new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				if (isChecked) {
					// If the user checked the item, add it to the selected items
					tagList.add(finalUserTags[which]);
				} else if (tagList.contains(finalUserTags[which])) {
					// Else, if the item is already in the array, remove it 
					tagList.remove(finalUserTags[which]);
				}
			}
		});
		builder.setPositiveButton("Add New", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				addTagDialog();
			}
			
		});
		builder.setNegativeButton("Save", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				tagsArrayList.addAll(tagList);
			}
		});
		builder.show();

	}

	protected void addTagDialog(){
		LayoutInflater layoutInflater = LayoutInflater.from(AddClaimActivity.this);
		View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddClaimActivity.this);
		alertDialogBuilder.setView(promptView);

		final EditText editText = (EditText) promptView.findViewById(R.id.editTextTag);

		alertDialogBuilder.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				tagsArrayList.add(editText.getText().toString());
				user.addTag(editText.getText().toString());
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
	
	
	
	//initialize calendar view
    private void setDateTimeField() {
        startDate.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
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

}
