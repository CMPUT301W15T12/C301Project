package ca.ualberta.cs.cmput301w15t12;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_claim);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
		
		//username of user passed along from list choice activity
		Username = getIntent().getExtras().getString("username");
		
		//gets the use corresponding to the UserName
		for (int i = 0; i < UserListController.getUserList().size(); i++) {
			if (UserListController.getUserList().get(i).getUserName().equals(Username)) {
				user = UserListController.getUserList().get(i);
			}
		}
		
		//initializes the date fields
		startDate = (EditText) findViewById(R.id.EnterStartDate);
		endDate = (EditText) findViewById(R.id.EnterEndDate);
		endDate.setInputType(InputType.TYPE_NULL);
		startDate.setInputType(InputType.TYPE_NULL);
		setDateTimeField();
		
		//clickable button creates claim and takes the user back to the claim list page
		Button donebutton = (Button) findViewById(R.id.buttonsaveClaim);
		donebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				try {
					addClaim();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();			}
		});
		
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
		CLC.addClaim(name, sdate, edate, description, this.user);
		
		//TODO tags, destinations are added separately!
		
		//put this back         android:focusableInTouchMode="false"
		//and this         android:focusableInTouchMode="false"
		
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
