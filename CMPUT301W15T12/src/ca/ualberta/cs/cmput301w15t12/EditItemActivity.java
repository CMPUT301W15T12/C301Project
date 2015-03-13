/* EditItemActivity is responsible for allowing a selected expense item to be edited.
 * It displays the current information about the expense item, and allows changes to be made.
 * A user may then accept their changes and the expense item is updated.
 */

package ca.ualberta.cs.cmput301w15t12;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends Activity
{
    private EditText Date;
    private DatePickerDialog DatePickerDialog;
    private SimpleDateFormat df =new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    private int expenseItemId;
    private ExpenseItem expenseItem;
    private int claimIndex;
    
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
        
		Intent intent = getIntent();
		expenseItemId = intent.getIntExtra("item_index", 0);
		claimIndex = intent.getIntExtra("claim_id", 0);
		ClaimListController clc = new ClaimListController();
		expenseItem = clc.getClaim(claimIndex).getExpenseItems().get(expenseItemId);
        
        
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
				
				String name = editName.getText().toString();
				String category = editCategory.getText().toString();
				String description = editDescription.getText().toString();
				String currency = editCurrency.getText().toString();
				String amount = editAmount.getText().toString();
				String date = editDate.getText().toString();
				
				try {
					editItem(name, category, description, currency, amount, date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
			}
		});
		
	}
	
	@Override
	//will this affect the dialogs? - instead be in onCreate?
	public void onResume(){
		super.onResume();
		
        //set fields to claim details
        EditText editName = (EditText) findViewById(R.id.editItemName );
		EditText editCategory = (EditText) findViewById(R.id.editCategory);
		EditText editDescription = (EditText) findViewById(R.id.editItemDescription);
		EditText editCurrency = (EditText) findViewById(R.id.editCurrency);
		EditText editAmount = (EditText) findViewById(R.id.editAmount);
		EditText editDate = (EditText) findViewById(R.id.editItemDate);
		
		
		editName.setText(expenseItem.getName());
		editCategory.setText(expenseItem.getCategory());
		editDescription.setText(expenseItem.getDescription());
		editCurrency.setText(expenseItem.getCurrency());	
		editAmount.setText(expenseItem.getAmount().toString());
		editDate.setText(expenseItem.getDate().toString());
	}
	
	public void editItem(String name, String category, String description,
			String currency, String amount, String date) throws ParseException {
		//TODO keep index, delete and create new item and then insert at that index.
		//pass on index and attributes
		
		
		Date dfDate = df.parse(date);
		DecimalFormat deF = new DecimalFormat("0.00");
		deF.setParseBigDecimal(true);
		BigDecimal bdAmount = (BigDecimal) deF.parse(amount);
		
		expenseItem.setName(name);
		expenseItem.setCategory(category);
		expenseItem.setDescription(description);
		expenseItem.setCurrency(currency);
		expenseItem.setAmount(bdAmount);
		expenseItem.setDate(dfDate);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
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

	public void currencyOnClick(View view){
		//open currency dialog
		final String[] currencies = {"CAD", "USD", "EUR", "GBP", "CHF", "JPY", "CHY"};
		AlertDialog.Builder adb = new AlertDialog.Builder(EditItemActivity.this);
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
		AlertDialog.Builder adb = new AlertDialog.Builder(EditItemActivity.this);
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
}
