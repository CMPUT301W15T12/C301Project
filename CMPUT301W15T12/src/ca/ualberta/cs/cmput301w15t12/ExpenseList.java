package ca.ualberta.cs.cmput301w15t12;

import java.io.Serializable;
import java.util.ArrayList;

public class ExpenseList implements Serializable
{

	private static final long serialVersionUID = 1638804361140989950L;
	ArrayList<ExpenseItem> Items;
	protected transient ArrayList<Listener> listeners = null;
	
	public ExpenseList() {
		this.Items = new ArrayList<ExpenseItem>();
		this.listeners = new ArrayList<Listener>();
	}
	
	public ArrayList<ExpenseItem> getItems() {
		return Items;
	}

	public void setItems(ArrayList<ExpenseItem> items) {
		Items = items;
	}

	public void add(ExpenseItem item) throws AlreadyExistsException {
		for (int i = 0; i < Items.size(); i++) {
			if (Items.get(i).equals(item)){
				throw new AlreadyExistsException();
			}
		}
		Items.add(item);
	}
	
	public boolean contains(ExpenseItem item) {
		for (int i = 0; i < Items.size(); i++) {
			if (Items.get(i).equals(item)){
				return true;
			}
		}
		return false;
	}
	
	public void removeItem(String ItemName) {
		for (int i = 0; i < Items.size(); i++) {
			if (Items.get(i).getName().equals(ItemName)) {
				Items.remove(i);
			}
		}
		notifyListeners();
	}
	
	
	public int size() {
		return Items.size();
	}
	
	private ArrayList<Listener> getListeners() {
		if (listeners == null ) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}
	
	public void addListener (Listener L) {
		getListeners().add(L);
	}
	

	public void removeListener(Listener l) {
		getListeners().remove(l);
	}
	
	private void notifyListeners() {
		for (Listener listener : getListeners()) {
			listener.update();
		}
	}
}
