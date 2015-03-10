package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;

public class ClaimList2 extends List2<Claim>{
	
	private int counter;
	private static final ClaimList2 instance = new ClaimList2();

	private ClaimList2() {
		list = new ArrayList<Claim>();
		listeners = new ArrayList<Listener>();
		counter = 0;
	}
			
	public static ClaimList2 getInstance() {
		return instance;
	}
	
	
	@Override
	public int Compare(Claim item1, Claim item2) {
		if (item1.getStartDate().compareTo(item2.getStartDate()) != 0) {
			// Compare by startDates
			return item1.getStartDate().compareTo(item2.getStartDate());
		} else if (item1.getEndDate().compareTo(item2.getEndDate()) != 0) {
			// startDates are the same, compare by endDates
			return item1.getEndDate().compareTo(item2.getEndDate());
		} else if (item1.getName().compareTo(item2.getName()) != 0){
			// endDates are the same as well, compare by names
			return item1.getName().compareTo(item2.getName());
		} else {
			// names are the same as well, compare by Description
			return item1.getDescription().compareTo(item2.getDescription());
		}
	}
	
	public void incrementCounter() {
		counter += 1;
	}
	
	public int getCounter(){
		return counter;
	}
	
	public void setCounter(int id_counter){
		counter = id_counter;
	}
	
}
