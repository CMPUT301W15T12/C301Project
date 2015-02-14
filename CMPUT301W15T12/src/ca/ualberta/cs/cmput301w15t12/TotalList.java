package ca.ualberta.cs.cmput301w15t12;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TotalList
{
	public ArrayList<Amt_Cur> sum;
	protected transient ArrayList<Listener> listeners = null;
	
	public TotalList() {
		super();		
		this.listeners = new ArrayList<Listener>();
		this.sum = new ArrayList<Amt_Cur>();
	}

	public String toStringList()
	{
		if (size() == 0) {
			return null;
		}
		String total = sum.get(0).toString();
		for (int i = 1; i < size(); i++){
			total += sum.get(i).toString();
		}
		return total;
	}
	
	public ArrayList<Amt_Cur> computeTotal(ExpenseList EItems) {
		this.sum = new ArrayList<Amt_Cur>();
		ArrayList<Amt_Cur> extra = new ArrayList<Amt_Cur>();
		for (int i = 0; i < EItems.size(); i++){
			extra.add(EItems.getItems().get(i).getAmt_Cur());
		}	
		for (int i = 0; i < extra.size(); i++){
			String cur = extra.get(i).getCurrency();
			if (contains(cur)){
				for (int j = 0; j < size(); j++ ){
					if (sum.get(j).getCurrency().equals(cur)) {
						BigDecimal a  = sum.get(j).getAmount();
						BigDecimal b = extra.get(i).getAmount();
						BigDecimal c = a.add(b);
						Amt_Cur both = new Amt_Cur(c,cur);
						sum.remove(j);
						sum.add(both);
					}
				}
			} else {
				sum.add(extra.get(i));	
			}
		}
		notifyListeners();
		return sum;
	}
	
	public ArrayList<Amt_Cur> getTS(){
		return sum;
	}
	
	public void add(Amt_Cur ac){
		sum.add(ac);
		notifyListeners();
	}
	
	public void remove(int i) {
		sum.remove(i);
	}
	
	public void remove(Amt_Cur ac){
		sum.remove(ac);
	}
	
	public void clear(){
		int s = size();
		for (int i = 0; i < s; i++) {
			sum.remove(0);
		}
	}
	
	public int size() {
		return sum.size();
	}
	
	public boolean contains(String currency) {
		for (int i = 0; i < size(); i++) {
			if (sum.get(i).getCurrency().equals(currency)){
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Listener> getListeners() {
		if (listeners == null ) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	

	private void notifyListeners() {
		for (Listener listener : getListeners()) {
			listener.update();
		}
	}
	
	public void addListener (Listener L) {
		getListeners().add(L);
	}
	

	public void removeListener(Listener l) {
		getListeners().remove(l);
	}

}
