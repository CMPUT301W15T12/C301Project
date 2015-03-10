package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.ExpenseItem;
import ca.ualberta.cs.cmput301w15t12.ExpenseList;
import ca.ualberta.cs.cmput301w15t12.Listener;
import junit.framework.TestCase;

public class ELTEST extends TestCase {

	private boolean updated = false;
	
	public ELTEST(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testExpenseList() {
		ExpenseList expl = new ExpenseList();
		ExpenseItem exp = new ExpenseItem(null, null, null, null, null, null);
		
		assertTrue("Should be empty but wasn't", expl.size() == 0);
		
		expl.addItem(exp);
		
		assertTrue("Should contain exp but doesn't (size)", expl.size() == 1);
		assertTrue("Should contain exp but doesn't (contains)", expl.getList().contains(exp));
		
		expl.rmItem(exp);
		assertTrue("Shouldn't contain exp but it does (size)", expl.size() == 0);
		assertTrue("Shouldn't contain exp but it does (contains)", !expl.getList().contains(exp));
	}
	
	
	public void testListeners() {
		ExpenseList expl = new ExpenseList();
		
		assertTrue("Should not have listeners but it does", expl.getListeners().size() == 0);
		
		Listener lsr  = new Listener() {
			
			@Override
			public void update() {
				updated = true;
			}
		};
		
		expl.addListener(lsr);
		
		assertTrue("Should contain lsr but doesn't (size)", expl.getListeners().size() == 1);
		assertTrue("Should contain lsr but doesn't (contains)", expl.getListeners().contains(lsr));
		
		ExpenseItem exp = new ExpenseItem(null, null, null, null, null, null);
		expl.addItem(exp);
		
		assertTrue("Listener didn't fire", updated);
		
		expl.rmListener(lsr);
		assertTrue("Shouldn't contain lsr but it does (size)", expl.getListeners().size() == 0);
		assertTrue("Shouldn't contain exp but it does (contains)", !expl.getListeners().contains(lsr));
	}
	
	public void testSort() {
		// TODO
	}
	
}
