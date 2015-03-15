package ca.ualberta.cs.cmput301w15t12;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ExpenseList {
	
	protected ArrayList<ExpenseItem> list;
	protected transient ArrayList<Listener> listeners;
	
	public ExpenseList() {
		list = new ArrayList<ExpenseItem>();
		listeners = new ArrayList<Listener>();
	}
	
	protected Comparator<ExpenseItem> comparator = new Comparator<ExpenseItem>() {
		@Override
		public int compare(ExpenseItem item1, ExpenseItem item2) {
			return Compare(item1, item2);
		}
	};
	
	public int Compare(ExpenseItem item1, ExpenseItem item2) {
		if (item1.getDate().compareTo(item2.getDate()) != 0) {
			// Compare by startDates
			return item1.getDate().compareTo(item2.getDate());
		} else if (item1.getName().compareTo(item2.getName()) != 0) {
			// startDates are the same, compare by names
			return item1.getName().compareTo(item2.getName());
		} else if (item1.getCurrency().compareTo(item2.getCurrency()) != 0) {
			// names are the same, compare by currencies
			return item1.getCurrency().compareTo(item2.getCurrency());
		} else if (item1.getAmount().compareTo(item2.getAmount()) != 0) {
			// currencies are the same, compare by amounts
			return item1.getAmount().compareTo(item2.getAmount());
		} else if (item1.getCategory().compareTo(item2.getCategory()) != 0) {
			// amounts are the same, compare by categories
			return item1.getCategory().compareTo(item2.getCategory());
		} else {
			// categories are the same, compare by description
			return item1.getDescription().compareTo(item2.getDescription());
		}
	}
	
	public void sort() {
		Collections.sort(list, comparator);
		notifyListeners();
	}
	
	public ArrayList<ExpenseItem> getList() {
		return list;
	}
	
	public void setList(ArrayList<ExpenseItem> list) {
		this.list = list;
		sort();
	}
	
	public void addExpenseItem(ExpenseItem item) {
		list.add(item);
		sort();
	}
	
	public void addExpenseItem(String name,String category, String description, String currency, 
			BigDecimal amount, Date date) {
		ExpenseItem item = new ExpenseItem(name, category, description, currency, amount, date);
		list.add(item);
		sort();
	}
	
	public void rmExpenseItem(ExpenseItem item) {
		list.remove(item);
		sort();
	}
	
	public ArrayList<Listener> getListeners() {
		return listeners;
	}
	
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	public void rmListener(Listener listener) {
		listeners.remove(listener);
	}
	
	public void notifyListeners() {
		for (Listener listener: listeners) {
			listener.update();
		}
	}
	
	public int size() {
		return list.size();
	}
	
	public boolean contains(ExpenseItem item) {
		return list.contains(item);
	}

}
