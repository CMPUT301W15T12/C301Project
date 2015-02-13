package ca.ualberta.cs.cmput301w15t12;

import java.math.BigDecimal;
import java.util.Date;

public class ExpenseItem{
	
	private String name;
	private String category;
	private String description;
	private String currency;
	private BigDecimal amount;
	private Date date;
	private boolean flag;
	
	public ExpenseItem(String name,String category, String description, String currency, 
			BigDecimal amount, Date date, boolean flag){
		this.name = name;
		this.category = category;
		this.description = description;
		this.currency = currency;
		this.amount = amount;
		this.date = date;
		this.flag = flag;
		
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
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
