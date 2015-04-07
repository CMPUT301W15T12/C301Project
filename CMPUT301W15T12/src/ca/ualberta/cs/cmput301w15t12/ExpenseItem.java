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
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.location.Location;

public class ExpenseItem implements Serializable{

	private static final long serialVersionUID = -2552967637419002646L;
	private static ArrayList<String> currencies;
	private static ArrayList<String> categories;
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
	private Integer imageId; 
	private URI uri;

	/** Initialize currencies and categories if they weren't already initialized
	 * 
	 * @param c
	 */
	public static void init(Context c) {
		if (currencies == null) currencies = new ArrayList<String>(Arrays.asList(c.getResources().getStringArray(R.array.currency)));
		if (categories == null) categories = new ArrayList<String>(Arrays.asList(c.getResources().getStringArray(R.array.category)));
	}
	/**
	 * constructs a new expense item with the given parameters
	 * @param name
	 * @param category
	 * @param description
	 * @param currency
	 * @param amount
	 * @param date
	 * @param flag
	 */
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

	/**
	 * @return a string of the expense item details
	 */
	public String toStringList() {	
		String block = "["+getStringDate()+"] "+name+"\n"+category+" - "+Amount+" "+Currency+"\n"+description;
		return block;
	}

	/**
	 * @return a string of the expense item details to be shown in the email
	 */
	public String toEmail() {
		String ds = df.format(date);
		String string = name+"\n";
		string += category+"\n"+description+"\n";
		string += ds+"\n"+toACString()+"\n";
		return string;
	}


	/**
	 * @return string format of Amount and Currency --> for printing the total List
	 */
	public String toACString() {
		return Amount.toString()+" "+this.Currency;
	}

	//getters and setters for the attributes
	/**
	 * 
	 * @return expense item date
	 */
	public String getStringDate(){
		String d = "";
		if(date != null) {
			d = df.format(date);
		} 
		return d;
	}
	/**
	 * 
	 * @return expense item currency choices
	 */
	public static ArrayList<String> getCurrencies() {
		return currencies;
	}
	/**
	 * 
	 * @return expense item category choices
	 */
	public static ArrayList<String> getCategories() {
		return categories;
	}
	/**
	 * 
	 * @return expense item flagged state
	 */
	public boolean getFlag() {
		return flag;
	}
	/**
	 * set whether or not the expense item is flagged
	 * @param flag
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	/**
	 * 
	 * @return true if the expense item has a receipt otherwise false
	 */
	public boolean getReceipt(){
		return receipt;
	}
	/**
	 * set whether or not the expense item has a receipt
	 * @param receipt
	 */
	public void setReceipt(boolean receipt){
		this.receipt = receipt;
	}
	/**
	 * 
	 * @return expense item name
	 */
	public String getName() {
		return name;
	}
	/**
	 * sets expense item name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return expense item category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * sets the expense item category
	 * @param category
	 */
	public void setCategory(String category) {
		if (category == null || category.equals("") || categories.contains(category)) {
			this.category = category;
		} else {
			throw new RuntimeException("Invalid category"+category+"!");
		}
	}
	/**
	 * 
	 * @return the expense item description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * sets the expense item description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 
	 * @return the expense item date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * sets the expense item date
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * 
	 * @return the expense item amount
	 */
	public BigDecimal getAmount() {
		return Amount;
	}
	/**
	 * sets the expense item amount
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.Amount = amount;
	}
	/**
	 * 
	 * @return the expense item currency
	 */
	public String getCurrency() {
		return Currency;
	}
	/**
	 * sets the expense item currency checking that its valid 
	 * @param currency
	 */
	public void setCurrency(String currency) {
		if (currency == null || currency.equals("") || currencies.contains(currency)) {
			this.Currency = currency;
		} else {
			throw new RuntimeException("Invalid currency");
		}
	}
	/**
	 * sets the image file
	 * @param imageFileUri
	 */
	public void setUri(URI imageFileUri) {
		if (imageFileUri == null) {
			this.receipt = false;
			this.uri = null;
		} else {
			this.uri = imageFileUri;
			this.receipt = true;
		}
	}
	/** 
	 * @return the image id corresponding to the state of the expense item
	 */
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
	/**
	 * @return the expense item image file
	 */
	public URI getUri() {
		return uri;
	}
	/**
	 * 
	 * @return true if location is attached, else false
	 */
	public boolean getBoolLocation(){
		return boolLocation;
	}
	/**
	 * sets the value of whether there is a location attached
	 * @param location
	 */
	public void setBoolLocation(boolean location){
		this.boolLocation = location;
	}
	/**
	 * 
	 * @return the expense item location
	 */
	public Location getlocation(){
		return location;
	}
	/**
	 * sets the expense item location
	 * @param location
	 */
	public void setlocation(Location location){
		this.location = location;
		if (location == null) {
			setBoolLocation(false);
		} else {
			setBoolLocation(true);
		}
	}

}
