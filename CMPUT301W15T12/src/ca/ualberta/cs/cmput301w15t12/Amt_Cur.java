package ca.ualberta.cs.cmput301w15t12;

import java.io.Serializable;
import java.math.BigDecimal;

public class Amt_Cur implements Serializable
{
	private static final long serialVersionUID = -5039795692912686324L;
	public BigDecimal Amount;
	public String Currency;
	
	public Amt_Cur(BigDecimal Amount, String Currency) {
		this.Amount = Amount;
		this.Currency = Currency;	
	}
	
	public boolean equals(Object compareac) {
		if (compareac != null &&
				compareac.getClass()==this.getClass()) {
			return this.equals((Amt_Cur)compareac);
		} else {
			return false;
		}
	}
	
	public boolean equals(Amt_Cur ac) {
		if(ac==null) {
			return false;
		}
		return toString().equals(ac.toString());
	}
	
	public int hashCode() {
		return ("Amount Currency:"+toString()).hashCode();
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
	
	public String toString() {
		return Amount.toString()+"  "+this.Currency;
	}
}
