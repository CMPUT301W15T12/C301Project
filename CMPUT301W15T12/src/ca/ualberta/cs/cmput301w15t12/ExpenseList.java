package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

public class ExpenseList extends List2<ExpenseItem> {
	
	public ExpenseList() {
		list = new ArrayList<ExpenseItem>();
		listeners = new ArrayList<Listener>();
	}
	
	@Override
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

}
