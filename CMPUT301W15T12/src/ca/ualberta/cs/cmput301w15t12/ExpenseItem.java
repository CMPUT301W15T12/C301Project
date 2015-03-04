package ca.ualberta.cs.cmput301w15t12;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExpenseItem{
	
	private String name;
	private String category;
	private String description;
	private BigDecimal Amount;
	private String Currency;
	private Date date;
	private boolean flag;
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	
	public ExpenseItem(String name,String category, String description, String currency, 
			BigDecimal amount, Date date, boolean flag){
		this.name = name;
		this.category = category;
		this.description = description;
		this.Amount = amount;
		this.Currency = currency;
		this.date = date;
		this.flag = flag;
	}
	
	//for printing the list of expense items
	public String toStringList() {
		String d = df.format(date);
		String block = "["+d+"] "+name+"\n"+category+" - "+Amount+" "+Currency+"\n"+description;
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
	
	//getters and setters for the attributes
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
		this.category = category;
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
	public boolean getFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
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
	public void setCurrency(String currency){
		this.Currency = currency;
	}
}
