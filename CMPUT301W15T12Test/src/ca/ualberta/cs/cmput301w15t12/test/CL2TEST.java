package ca.ualberta.cs.cmput301w15t12.test;

import ca.ualberta.cs.cmput301w15t12.ClaimList2;
import junit.framework.TestCase;

public class CL2TEST extends TestCase {

	public CL2TEST(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testini() {
		ClaimList2 instance1 = ClaimList2.getInstance();
		assertTrue("Not empty", instance1.getCounter() == 0);
		instance1.incrementCounter();
		
		
		ClaimList2 instance2 = ClaimList2.getInstance();
		assertTrue("Instance2 oesn't have the increment from instance 1", instance2.getCounter() == 1);
		instance2.setCounter(666);
		
		assertTrue("Instance1 does not see the updated counter from instance 2", instance1.getCounter() == 666);
	}
}
