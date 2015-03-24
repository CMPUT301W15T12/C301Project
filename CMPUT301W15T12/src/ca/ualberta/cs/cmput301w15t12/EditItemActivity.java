/**
 * EditItemActivity is responsible for allowing a selected expense item to be edited.
 * It displays the current information about the expense item, and allows changes to be made.
 * A user may then accept their changes and the expense item is updated.
 * issues: a photo cannot be added or changed, does not correctly show edited claim
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
 *   @author leah
*/

package ca.ualberta.cs.cmput301w15t12;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditItemActivity extends Activity
{
    private EditText Date;
    private DatePickerDialog DatePickerDialog;
    private SimpleDateFormat df =new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    private int expenseItemId;
    private ExpenseItem expenseItem;
    private int claimIndex;
    private Claim claim;
    Uri imageFileUri;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item);
		
		//initialize the manager
		UserListManager.initManager(this.getApplicationContext());
		ClaimListManager.initManager(this.getApplicationContext());
	    
		//intialize the date dialogue
        Date = (EditText) findViewById(R.id.editItemDate);    
        Date.setInputType(InputType.TYPE_NULL);
        setDateTimeField();
        
        //get all the values from the previous intent
		Intent intent = getIntent();
		expenseItemId = intent.getIntExtra("item_index", 0);
		claimIndex = intent.getIntExtra("claim_id", 0);
		ClaimListController clc = new ClaimListController();
		expenseItem = clc.getClaim(claimIndex).getExpenseItems().get(expenseItemId);
		claim = clc.getClaim(claimIndex);
		Button ib = (Button) findViewById(R.id.buttonAddImage);
		
		imageFileUri = expenseItem.getUri();
		Drawable picture = Drawable.createFromPath(imageFileUri.getPath());
		ib.setBackgroundDrawable(picture);
        ib.setText("");
		//start the user on the username edit text
		EditText editName = (EditText) findViewById(R.id.editItemName );
		editName.requestFocus();		
        
		//clickable button creates Item and takes the user back to the claim list page
		Button donebutton = (Button) findViewById(R.id.buttonEditItemDone);
		donebutton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				//get the edit texts
		        EditText editName = (EditText) findViewById(R.id.editItemName );
				EditText editCategory = (EditText) findViewById(R.id.editCategory);
				EditText editDescription = (EditText) findViewById(R.id.editItemDescription);
				EditText editCurrency = (EditText) findViewById(R.id.editCurrency);
				EditText editAmount = (EditText) findViewById(R.id.editAmount);
				
				//get the inputted values
				String name = editName.getText().toString();
				String category = editCategory.getText().toString();
				String description = editDescription.getText().toString();
				String currency = editCurrency.getText().toString();
				String amount = editAmount.getText().toString();
				String date = Date.getText().toString();

				//check date and parse it
				Date dfDate = null;
				try {
					dfDate = df.parse(date);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				//check amount and parse it
				DecimalFormat deF = new DecimalFormat("0.00");
				deF.setParseBigDecimal(true);
				BigDecimal bdAmount = null;
				try {
					bdAmount = (BigDecimal) deF.parse(amount);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				//save values for the expense item
				claim.getExpenseItems().get(expenseItemId).setName(name);
				claim.getExpenseItems().get(expenseItemId).setCategory(category);
				claim.getExpenseItems().get(expenseItemId).setDescription(description);
				claim.getExpenseItems().get(expenseItemId).setCurrency(currency);
				claim.getExpenseItems().get(expenseItemId).setAmount(bdAmount);
				claim.getExpenseItems().get(expenseItemId).setDate(dfDate);
				claim.getExpenseItems().get(expenseItemId).setUri(imageFileUri);


				
				//TODO edit picture
				finish();
			}
		});
		
	}
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	
	public void addImage(View view){
		String folder = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/tmp";
		File folderF = new File(folder);
		if (!folderF.exists()) {
			folderF.mkdir();
		}

		// Create an URI for the picture file
		String imageFilePath = folder + "/"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg";
		File imageFile = new File(imageFilePath);
		imageFileUri = Uri.fromFile(imageFile);
		
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
		startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			if (resultCode == RESULT_OK){
				Button ib = (Button) findViewById(R.id.buttonAddImage);
				Drawable picture = Drawable.createFromPath(imageFileUri.getPath());
				ib.setBackgroundDrawable(picture);
		        ib.setText("");
				Toast.makeText(EditItemActivity.this, "Photo Saved", Toast.LENGTH_SHORT).show();
			}
			else if (resultCode == RESULT_CANCELED){
				Toast.makeText(EditItemActivity.this, "Photo Cancelled", Toast.LENGTH_SHORT).show();
			}
		}
		
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

		
		editName.requestFocus();
		
		editName.setText(expenseItem.getName());
		editCategory.setText(expenseItem.getCategory());
		editDescription.setText(expenseItem.getDescription());
		editCurrency.setText(expenseItem.getCurrency());	
		editAmount.setText(expenseItem.getAmount().toString());
		editDate.setText(df.format(expenseItem.getDate()));
	}
	
//	public void editItem(ExpenseItem ei) throws AlreadyExistsException {
//		//TODO keep index, delete and create new item and then insert at that index.
//		//pass on index and attributes
//		//need a function to add at an index
//		Toast.makeText(EditItemActivity.this, "here", Toast.LENGTH_SHORT).show();
//		claim.addItem(ei);
//		claim.removeItem(expenseItemId);
//	}
	
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
