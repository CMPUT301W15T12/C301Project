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
	
	public ExpenseList() {
		list = new ArrayList<ExpenseItem>();
		listeners = new ArrayList<Listener>();
	}
	
	public ArrayList<ExpenseItem> getList() {
		return list;
	}
	
	public void setList(ArrayList<ExpenseItem> list) {
		this.list = list;
		notifyListeners();
	}
	
	public void addExpenseItem(ExpenseItem item) {
		list.add(item);
		notifyListeners();
	}
	
	public void addExpenseItem(String name,String category, String description, String currency, 
			BigDecimal amount, Date date) {
		ExpenseItem item = new ExpenseItem(name, category, description, currency, amount, date);
		list.add(item);
		notifyListeners();
	}
	
	public void rmExpenseItem(ExpenseItem item) {
		list.remove(item);
		notifyListeners();
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
