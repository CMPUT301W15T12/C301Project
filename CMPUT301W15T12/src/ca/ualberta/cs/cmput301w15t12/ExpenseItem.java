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
	private Amt_Cur AC;
	private Date date;
	private boolean flag;
	private DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
	
	public ExpenseItem(String name,String category, String description, String currency, 
			BigDecimal amount, Date date, boolean flag){
		this.name = name;
		this.category = category;
		this.description = description;
		this.AC = new Amt_Cur(amount, currency);
		this.date = date;
		this.flag = flag;
		
	}
	
	public String toStringList() {
		String d = df.format(date);
		String block = "["+d+"] "+name+"\n"+category+" - "+AC.getAmount()+" "+AC.getCurrency()+"\n"+description;
		return block;
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
	public Amt_Cur getAmt_Cur() {
		return AC;
	}
	public String getCurrency() {
		return AC.getCurrency();
	}
	public void setCurrency(String currency) {
		this.AC.setCurrency(currency);
	}
	public BigDecimal getAmount() {
		return AC.getAmount();
	}
	public void setAmount(BigDecimal amount) {
		this.AC.setAmount(amount);
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
