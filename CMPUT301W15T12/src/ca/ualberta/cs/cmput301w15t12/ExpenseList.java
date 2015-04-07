/**
 * ExpenseList data model models all the functionality for the 
 * 
 *   Copyright [2015] CMPUT301W15T12 https://github.com/CMPUT301W15T12
 *   licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *   
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   @author vanbelle
*/

package ca.ualberta.cs.cmput301w15t12;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class ExpenseList {
	
	protected ArrayList<ExpenseItem> list;
	protected transient ArrayList<Listener> listeners;
	protected Integer[] ids;
	/** 
	 * constructor creates a new list for the items
	 */
	public ExpenseList() {
		list = new ArrayList<ExpenseItem>();
		listeners = new ArrayList<Listener>();
	}
	/** gets the imageid corresponding to each expense item and returns them as a list
	 * 
	 * @return a list of image ids
	 */
	public Integer[] getIds() {
		ids = new Integer[size()];
		for (int i = 0; i < size(); i++) {
			ids[i] = list.get(i).getImageId();
		}
		return ids;
	}
	/**
	 * @return all the expense items
	 */
	public ArrayList<ExpenseItem> getList() {
		return list;
	}
	/**
	 * sets the list of expense items to the one provided
	 * @param list
	 */
	public void setList(ArrayList<ExpenseItem> list) {
		this.list = list;
		notifyListeners();
	}
	/**
	 * adds a new expense item and
	 * @param item
	 */
	public void addExpenseItem(ExpenseItem item) {
		list.add(item);
		notifyListeners();
	}
	/**
	 * adds an expense item to the list of expense items list
	 * @param name
	 * @param category
	 * @param description
	 * @param currency
	 * @param amount
	 * @param date
	 * @param flag
	 */
	public void addExpenseItem(String name,String category, String description, String currency, 
			BigDecimal amount, Date date, boolean flag) {
		ExpenseItem item = new ExpenseItem(name, category, description, currency, amount, date, flag);
		list.add(item);
		notifyListeners();
	}
	/**
	 * removes an expense item from the expense item list
	 * using the item
	 * @param item
	 */
	public void rmExpenseItem(ExpenseItem item) {
		list.remove(item);
		notifyListeners();
	}
	/**
	 * removes an expense item from the expense item list
	 * using the position of that item
	 * @param item
	 */
	public void rmExpenseItem(int i){
		list.remove(i);
		notifyListeners();
	}
	/**
	 * gets the listeners
	 * @return
	 */
	public ArrayList<Listener> getListeners() {
		return listeners;
	}
	/**
	 * adds a listener
	 * @param listener
	 */
	public void addListener(Listener listener) {
		listeners.add(listener);
	}
	/** removes a listener
	 * 
	 * @param listener
	 */
	public void rmListener(Listener listener) {
		listeners.remove(listener);
	}
	/** notifies all the listening listeners
	 */
	public void notifyListeners() {
		for (Listener listener: listeners) {
			listener.update();
		}
	}
	/** returns the size of the expense item list
	 * 
	 * @return
	 */
	public int size() {
		return list.size();
	}
	/**
	 * checks if the list contains a certain expense item
	 * @return true if it does otherwise false
	 */
	public boolean contains(ExpenseItem item) {
		return list.contains(item);
	}

}
