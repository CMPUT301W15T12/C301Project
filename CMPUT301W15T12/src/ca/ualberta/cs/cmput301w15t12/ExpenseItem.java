/**
 * EditItem data model models all the functionality for the expense items 
 * including the getting/setting attributes, toString functions and constructors.
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

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import android.location.Location;
import android.net.Uri;

public class ExpenseItem implements Serializable{

	private static final long serialVersionUID = -2552967637419002646L;
	private static final ArrayList<String> currencies = new ArrayList<String>(
			Arrays.asList("CAD", "USD", "EUR", "GBP", "CHF", "JPY", "CNY"));
	private static final ArrayList<String> categories = new ArrayList<String>(
			Arrays.asList("Air Fare", "Ground Transport", "Vehicle Rental", "Private Automobile",
					"Fuel", "Parking", "Registration", "Accommodation", "Meal", "Supplies"));
	private String name;
	private String category;
	private String description;
	private BigDecimal Amount;
	private String Currency;
	private Date date;
	private boolean flag;
	private boolean receipt;
	private boolean boolLocation;
	private Location location;
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	private Uri uri;
	private Integer imageId; 


	public ExpenseItem(String name,String category, String description, String currency, 
			BigDecimal amount, Date date, boolean flag){
		this.name = name;
		setCategory(category);
		this.description = description;
		this.Amount = amount;
		setCurrency(currency);
		this.date = date;
		this.flag = flag;
		this.receipt = false;
		this.boolLocation = false;
		this.imageId = null;
	}

	//for printing the list of expense items
	public String toStringList() {	
		String block = "["+getStringDate()+"] "+name+"\n"+category+" - "+Amount+" "+Currency+"\n"+description;
		return block;
	}


	public String toEmail() {
		String ds = df.format(date);
		String string = name+"\n";
		string += category+"\n"+description+"\n";
		string += ds+"\n"+toACString()+"\n";
		return string;
	}


	//returns string format of Amount and Currency --> for printing the total List
	public String toACString() {
		return Amount.toString()+" "+this.Currency;
	}

	public void incomplete() {
		if (name == null || category == null || description == null || Amount == null || Currency == null || date == null) {
			flag = true;
		}
	}

	//getters and setters for the attributes
	public String getStringDate(){
		String d = "";
		if(date != null) {
			d = df.format(date);
		} 
		return d;
	}
	public static ArrayList<String> getCurrencies() {
		return currencies;
	}

	public static ArrayList<String> getCategories() {
		return categories;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean getReceipt(){
		return receipt;
	}

	public void setReceipt(boolean receipt){
		this.receipt = receipt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		if (category.equals("") || categories.contains(category)) {
			this.category = category;
		} else {
			throw new RuntimeException("Invalid category"+category+"!");
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return Amount;
	}

	public void setAmount(BigDecimal amount) {
		this.Amount = amount;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		if (currency.equals("") || currencies.contains(currency)) {
			this.Currency = currency;
		} else {
			throw new RuntimeException("Invalid currency");
		}
	}

	public void setUri(Uri imageFileUri) {
		if (imageFileUri == null) {
			this.receipt = false;
			this.uri = null;
		} else {
			this.uri = imageFileUri;
			this.receipt = true;
		}
	}
	public Integer getImageId(){
		if (getFlag() && getReceipt() && getBoolLocation()) {
			imageId = R.drawable.trio;
		} else if (getFlag() && getBoolLocation()){
			imageId = R.drawable.globeflag;
		} else if (getReceipt() && getBoolLocation()) {
			imageId = R.drawable.globereceipt;
		} else if (getBoolLocation()) {
			imageId = R.drawable.globe;
		} else if (getFlag() && getReceipt()) {
			imageId = R.drawable.both;
		} else if (getFlag()) {
			imageId = R.drawable.flagged;
		} else if (getReceipt()) {
			imageId = R.drawable.receipt;
		} else {
			imageId = R.drawable.none;
		}
		return imageId;
	}
	public Uri getUri() {
		return uri;
	}
	public boolean getBoolLocation(){
		return boolLocation;
	}
	public void setBoolLocation(boolean location){
		this.boolLocation = location;
	}
	public Location getlocation(){
		return location;
	}
	public void setlocation(Location location){
		this.location = location;
		if (location == null) {
			setBoolLocation(false);
		} else {
			setBoolLocation(true);
		}
	}

}
