package ca.ualberta.cs.cmput301w15t12;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class AddItemActivity extends Activity
{
    private EditText Date;
    private DatePickerDialog DatePickerDialog;
    private SimpleDateFormat df =new SimpleDateFormat("MM/dd/yyyy", Locale.US);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
	    
        Date = (EditText) findViewById(R.id.editItemDate);    
        Date.setInputType(InputType.TYPE_NULL);
		
        setDateTimeField();
        
		//clickable button creates Item and takes the user back to the claim list page
		Button donebutton = (Button) findViewById(R.id.buttonEditItemDone);
		donebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				addItem();
				finish();
			}
		});
		
	}
	
	public void addItem() {
		//TODO checks all user input and creates expense item based off of it,
		//and adds the appropriate flag
	}
	
	public void currencyOnClick(){
		//open currency dialog
		String[] currencies = {"CAD", "USD", "EUR", "CHF", "JPY", "CHY", "GBP"};
		AlertDialog.Builder adb = new AlertDialog.Builder(AddItemActivity.this);
		adb.setMessage("Select a currency");
		adb.setItems(currencies, new OnClickListener(){
			public void onClick(DialogInterface dialog,int which){
				TextView currency = (TextView) findViewById(R.id.editCurrency);
				//not sure if this is correct
				currency.setText(which);
			}
		});
		adb.setNegativeButton("Cancel",new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
			}
		});
	}
	
	public void categoryOnClick(){
		//TODO open category dialog
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}

    private void setDateTimeField() {
        Date.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
			    DatePickerDialog.show();
			}});
        
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {
 
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Date.setText(df.format(newDate.getTime()));
            }
 
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));}
}
