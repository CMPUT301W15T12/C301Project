/**
 * This  Activity allows a user to add a new Item in association with 
 * a specific claim or edit an existing item
 *
 * issues: does not allow a photo to be taken or included
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
 *   @author olexson
 *   @author vanbelle
 *   @author megsum
 */

package ca.ualberta.cs.cmput301w15t12;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddItemActivity extends Activity
{
	private EditText Date;
	private DatePickerDialog DatePickerDialog;
	private SimpleDateFormat df =new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	public static final String MOCK_PROVIDER = "mockLocationProvider";
	private Claim claim;
	private int claim_id;
	private Uri tempImageFileUri = null;	//[ATTENTION]: tempImageFileUri links to the temp image file returned from the camera app
	private URI imageURI = null; 			//[ATTENTION]: imageURI links to the file on the server.
	private boolean flag;
	private ExpenseItem expenseItem;
	private int expenseItemId;
	private String option;
	private EditText editName;
	private EditText editCategory;
	private EditText editDescription;
	private EditText editCurrency;
	private EditText editAmount;
	public Location location;
	
	/**
	 * 
	 * 
	 */
	
	private class LoadingPictureTask extends AsyncTask<URI, Void, File> {
	    @Override
	    protected File doInBackground(URI... uris) {
	    	URI uri = uris[0];	//pick the first one.
	    	ESClient esClient =new ESClient();
	        return esClient.loadImageFileFromServer(uri);
	    }
	    @Override
	    protected void onPostExecute(File file) {
    		Button ib = (Button) findViewById(R.id.buttonAddImage);

	    	if (file==null){
	    		ib.setText("No Receipt");
	    	}else{
				Drawable picture = Drawable.createFromPath(file.getPath());
				ib.setBackgroundDrawable(picture);
				ib.setText("");
	    	}
	    }  
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item);
		
		// Initialize currencies and categories list in ExpenseItem
		ExpenseItem.init(getApplicationContext());
		
		Intent intent = getIntent();
		claim_id = intent.getIntExtra("claim_id", 0);
		option = intent.getExtras().getString("option");
		ClaimListController clc = new ClaimListController();
		claim = clc.getClaim(claim_id);

		//initialize date dialogues
		Date = (EditText) findViewById(R.id.editItemDate);    
		Date.setInputType(InputType.TYPE_NULL);
		setDateTimeField();
		
		//get the updated values
		Button ib = (Button) findViewById(R.id.buttonAddImage);
		editName = (EditText) findViewById(R.id.editItemName );
		editCategory = (EditText) findViewById(R.id.editCategory);
		editDescription = (EditText) findViewById(R.id.editItemDescription);
		editCurrency = (EditText) findViewById(R.id.editCurrency);
		editAmount = (EditText) findViewById(R.id.editAmount);
		Date = (EditText) findViewById(R.id.editItemDate);
		editName.requestFocus();

		if (option == null) {
			Toast.makeText(this,"add or Edit?",Toast.LENGTH_SHORT).show();
			finish();
		}

		if (option.equals("edit")){
			expenseItemId = intent.getIntExtra("item_index", 0);
			expenseItem = clc.getClaim(claim_id).getExpenseItems().get(expenseItemId);
			location = expenseItem.getlocation();
			//set fields to claim details    

			String d = "";
			if (expenseItem.getDate() != null) {
				d = df.format(expenseItem.getDate());
			}
			//update the current expense item
			editName.setText(expenseItem.getName());
			editCategory.setText(expenseItem.getCategory());
			editDescription.setText(expenseItem.getDescription());
			editCurrency.setText(expenseItem.getCurrency());	
			editAmount.setText(expenseItem.getAmount().toString());
			Date.setText(d);

			CheckBox checkImage = (CheckBox) findViewById(R.id.checkBoxIncludePicture);
			CheckBox checkLocation = (CheckBox) findViewById(R.id.checkBoxIncludeLocation);
			CheckBox checkFlag = (CheckBox) findViewById(R.id.checkBoxFlag);

			//set check boxes if previously checked
			if(expenseItem.getFlag()){
				checkFlag.setChecked(true);
			}

			if(expenseItem.getBoolLocation()){
				checkLocation.setChecked(true);
			}
			
			if(expenseItem.getReceipt()){
				checkImage.setChecked(true);
			}

			new LoadingPictureTask().execute(expenseItem.getUri());

		}

		//start the user on the username edit text
		EditText editName = (EditText) findViewById(R.id.editItemName );
		editName.requestFocus();		
	}


	
	/**this method delegates responsibility to either the addItem method or to 
	 * the editItem method depending on the option provided in the getExtra
	 * @param view
	 */ 
	public void onDoneButtonClick(View view){
		if(option.equals("edit")){
			editItem();
		} else {
			addItem();
			finish();
		}
	}
	
	/**the method edits an existing expense item with any updated values
	 */ 
	public void editItem() {	
		//get the inputted values
		String name = editName.getText().toString();
		String category = editCategory.getText().toString();
		String description = editDescription.getText().toString();
		String currency = editCurrency.getText().toString();
		String amount = editAmount.getText().toString();
		//check date and parse it
		Date dfDate = null;
		try {
			dfDate = df.parse(Date.getText().toString());
		} catch (ParseException e1) {
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
		expenseItem.setName(name);
		try {
			expenseItem.setCategory(category);
		} catch (RuntimeException e) {
			Toast.makeText(this, "Incorrect category", Toast.LENGTH_SHORT).show();
		}
		expenseItem.setDescription(description);
		try {
			expenseItem.setCurrency(currency);
		} catch (RuntimeException e) {
			Toast.makeText(this, "Incorrect currency", Toast.LENGTH_SHORT).show();
		}
		expenseItem.setAmount(bdAmount);
		expenseItem.setFlag(flag);
		expenseItem.setDate(dfDate);
		expenseItem.setUri(imageURI);
		expenseItem.setlocation(location);
		finish();

	}
	
	/**this method adds a new expense item to the specified claim
	 */ 
	private void addItem() {
		BigDecimal amount = new BigDecimal(0);
		Date date = null;
		//default values for incomplete expense items
		if(!editAmount.getText().toString().equals("")){
			amount = new BigDecimal(editAmount.getText().toString());
		}
		try {
			date = df.parse(Date.getText().toString());
		} catch (ParseException e) {
		} 
		try {
			expenseItem = new ExpenseItem(editName.getText().toString(),editCategory.getText().toString(),
					editDescription.getText().toString(), editCurrency.getText().toString(),amount, date, flag);
			expenseItem.setUri(imageURI);
			expenseItem.setlocation(location);

			//check to see fields are filled in
			claim.addItem(expenseItem);
		} catch (RuntimeException e){
			Toast.makeText(this, "Incorrect category or currency", Toast.LENGTH_SHORT).show();
		}
	}
	/**this method opens a dialog list from the which the user can choose a currency
	 * @param view
	 */ 
	public void currencyOnClick(View view){
		//open currency dialog
		final String[] currencies = getResources().getStringArray(R.array.currency);
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
	/**this method deletes a previously added image
	 * @param view
	 */ 
	public void deleteImage(View view){
		imageURI = null;
		Button ib = (Button) findViewById(R.id.buttonAddImage);
		//2015/03/26 - http://stackoverflow.com/questions/14802354/how-to-reset-a-buttons-background-color-to-default
		ib.setBackgroundResource(android.R.drawable.btn_default);
		ib.setText("Add Image");
		Toast.makeText(AddItemActivity.this, "Photo Deleted", Toast.LENGTH_SHORT).show();
	}

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	/**gets an image from the camera app to be added to the expense item
	*/ 
	//Code implemented from camera test used in lab: https://github.com/joshua2ua/BogoPicLab March 24, 2015
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
		tempImageFileUri = Uri.fromFile(imageFile);

		//takes user to camera app
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempImageFileUri);
		startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
	/**
	 * 
	 * 
	 *
	 */
	
	private class SavingPictureTask extends AsyncTask<File, Void, URI> {
	    @Override
	    protected URI doInBackground(File... files) {
	    	File file = files[0];	//pick the first one.
	    	ESClient esClient =new ESClient();
	        return esClient.saveImageFileToServer(file);
	    }
	    @SuppressWarnings("deprecation")
		@Override
	    protected void onPostExecute(URI uri) {
	    	if (uri ==null){
				Toast.makeText(AddItemActivity.this, "Receipt too big, try again", Toast.LENGTH_SHORT).show();
	    	}else{
	    		imageURI = uri;
				Button ib = (Button) findViewById(R.id.buttonAddImage);
				Drawable picture = Drawable.createFromPath(tempImageFileUri.getPath());
				ib.setText("");
				ib.setBackgroundDrawable(picture);
				Toast.makeText(AddItemActivity.this, "Photo Saved", Toast.LENGTH_SHORT).show();
				CheckBox flagged = (CheckBox) findViewById(R.id.checkBoxIncludePicture);
				flagged.setChecked(true);
				
	    	}
	    }  
	}
	
	
	
	@SuppressLint("NewApi")
	@Override
	//gets the image or the remote location from the map activity or camera app
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			//saves photo if save clicked
			if (resultCode == RESULT_OK){
				new SavingPictureTask().execute(new File(tempImageFileUri.getPath()));
			}
			
			//doesn't save photo if cancel clicked 
			else if (resultCode == RESULT_CANCELED){
				Toast.makeText(AddItemActivity.this, "Photo Cancelled", Toast.LENGTH_SHORT).show();
			}
		} else {
			if (resultCode == RESULT_OK){
				if (resultCode == RESULT_OK){
					final Location location2 = new Location("location");
					double latitude = data.getExtras().getDouble("latitude");
					double longitude = data.getExtras().getDouble("longitude");
					location2.setLatitude(latitude);
					location2.setLongitude(longitude);
					location = location2;
					Toast.makeText(AddItemActivity.this, "Remote Location Saved",Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(AddItemActivity.this, "Invalid Location",Toast.LENGTH_SHORT).show();
				}
			}
		}

	}
	/** this method opens a category dialog list from which the user can choose from the category options
	 * @param view
	 */ 
	public void categoryOnClick(View view){
		//open category dialog
		final String[] categories = getResources().getStringArray(R.array.category);
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

	/** reacts when the flag checkbox is clicked, sets the flag boolean to true if it was unclicked and to false
	 * if it was previously unchecked.
	 * @param view
	 */ 
	public void onCheckBoxClicked(View view){
		boolean checked = ((CheckBox) view).isChecked();
		if (checked){
			//expense item has a flag
			flag = true;
		} else{
			//expense item doesn't have a flag
			flag = false;
		}

	}
	/** reacts when image check box is clicked by deleting the image if the box is set to unchecked, and opening
	 * the camera app if it was checked.
	 * @param view
	 */ 
	public void onImageCheckBoxClick(View view){
		boolean checked = ((CheckBox) view).isChecked();
		if (!checked) {
			deleteImage(view);
		} else if (tempImageFileUri == null) {
			addImage(view);
		}
	}
	
	/** reacts to the location check box, when going from unchecked to checked 
	 * opens up the geolocation dialog box asking the user if they want a remote location of their current gps location.
	 * if the box goes from checked to unchecked it deletes the current added location
	 */ 
	//https://github.com/joshua2ua/MockLocationTester
	public void onLocationClick(View view){
		boolean checked = ((CheckBox) view).isChecked();
		if (checked) {
			//get the current gps location
			final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			AlertDialog.Builder adb = new AlertDialog.Builder(AddItemActivity.this);
			adb.setMessage("Do you want to set your current location as this item location, or choose remotely? ");
			adb.setCancelable(true);
			adb.setPositiveButton("Current Location", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					if (location == null){
						Toast.makeText(AddItemActivity.this,"Error No Location added",Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(AddItemActivity.this,"Current Location added as Destination Location",Toast.LENGTH_SHORT).show();
					}
				}
			});
			//call remote location if they choose that option
			adb.setNegativeButton("Remote Location", new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(AddItemActivity.this, MapActivity.class);
					intent.putExtra("option","add");
					startActivityForResult(intent, 0);
				}
			});
			adb.show();	
		//delete the location	
		} else {
			location = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}

	/**initialize the date fields which open the date edit text gets opened
	*/
	//http://androidopentutorials.com/android-datepickerdialog-on-edittext-click-event/
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
