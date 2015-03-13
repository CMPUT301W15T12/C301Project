package ca.ualberta.cs.cmput301w15t12;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

import ca.ualberta.cs.cmput301w15t12.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
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
    private Claim claim;
    private int claim_id;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item);
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
		
		Intent intent = getIntent();
		claim_id = intent.getIntExtra("claim_id", 0);
		ClaimListController clc = new ClaimListController();
		claim = clc.getClaim(claim_id);
	    
        Date = (EditText) findViewById(R.id.editItemDate);    
        Date.setInputType(InputType.TYPE_NULL);
		
        setDateTimeField();
        
		//clickable button creates Item and takes the user back to the claim list page
		Button donebutton = (Button) findViewById(R.id.buttonEditItemDone);
		donebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				EditText editName = (EditText) findViewById(R.id.editItemName );
				EditText editCategory = (EditText) findViewById(R.id.editCategory);
				EditText editDescription = (EditText) findViewById(R.id.editItemDescription);
				EditText editCurrency = (EditText) findViewById(R.id.editCurrency);
				EditText editAmount = (EditText) findViewById(R.id.editAmount);
				EditText editDate = (EditText) findViewById(R.id.editItemDate);
				
				BigDecimal amount = new BigDecimal(editAmount.getText().toString());
				
				Date date;
				try {
					date = df.parse(editDate.getText().toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					date = null;
				}
				
				ExpenseItem expenseItem = new ExpenseItem(editName.getText().toString(),editCategory.getText().toString(),
						editDescription.getText().toString(), editCurrency.getText().toString(),amount, date);
				
				try {
					claim.addItem(expenseItem);
				} catch (AlreadyExistsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
			}
		});
		
	}
	
	public void addItem(ExpenseItem expenseitem) throws AlreadyExistsException {
		//TODO checks all user input and creates expense item based off of it,
		//and adds the appropriate flag
		claim.addItem(expenseitem);
	}
	
	public void currencyOnClick(View view){
		//open currency dialog
		final String[] currencies = {"CAD", "USD", "EUR", "GBP", "CHF", "JPY", "CHY"};
		AlertDialog.Builder adb = new AlertDialog.Builder(AddItemActivity.this);
		adb.setTitle("Select a currency");
		adb.setItems(currencies, new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog,int which){
				TextView currency = (TextView) findViewById(R.id.editCurrency);
				String selection = currencies[which];
				currency.setText(selection);
			}
		});
		adb.setNegativeButton("Cancel",new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
			}
		});
		adb.show();
	}
	
	public void categoryOnClick(View view){
		//open category dialog
		final String[] categories = {"Air Fare", "Ground Transport", "Vehicle Rental", "Private Automobile",
				"Fuel", "Parking", "Registration", "Accommodation", "Meal", "Supplies"};
		AlertDialog.Builder adb = new AlertDialog.Builder(AddItemActivity.this);
		adb.setTitle("Select a category");
		adb.setItems(categories, new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog,int which){
				TextView currency = (TextView) findViewById(R.id.editCategory);
				String selection = categories[which];
				currency.setText(selection);
			}
		});
		adb.setNegativeButton("Cancel",new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which){
			}
		});
		adb.show();
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
