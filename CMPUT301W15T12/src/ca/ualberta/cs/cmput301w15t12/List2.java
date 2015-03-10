package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class List2<T> {
	
	protected ArrayList<T> list;
	protected transient ArrayList<Listener> listeners;
	
	protected Comparator<T> comparator = new Comparator<T>() {
		@Override
		public int compare(T item1, T item2) {
			return Compare(item1, item2);
		}
	};
	
	public abstract int Compare(T item1, T item2);
	
	public void sort() {
		Collections.sort(list, comparator);
		notifyListeners();
	}
	
	public ArrayList<T> getList() {
		return list;
	}
	
	public void setList(ArrayList<T> list) {
		this.list = list;
		sort();
	}
	
	public void addItem(T item) {
		list.add(item);
		sort();
	}
	
	public void rmItem(T item) {
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
	
	public boolean contains(T item) {
		return list.contains(item);
	}
	
}
