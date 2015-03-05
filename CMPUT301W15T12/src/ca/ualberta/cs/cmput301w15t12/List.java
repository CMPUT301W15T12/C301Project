package ca.ualberta.cs.cmput301w15t12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public interface List<T> {
	public abstract int Compare(T item1, T item2);
	public void sort();
	public ArrayList<T> getList();
	public void setList(ArrayList<T> list);
	public boolean contains(T item);
	public void addItem(T item);
	public void rmItem(T item);
	public ArrayList<Listener> getListeners();
	public void addListener(Listener listener);
	public void rmListener(Listener listener);
	public void notifyListeners();
	public int size();
}
