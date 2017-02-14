package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class EstateTest {

	@Test
	public void testValues() {
		
		Card test = new Estate();
		assertEquals("Estate name doesn't match",
				"Estate", test.getName());
		assertEquals("Estate cost doesn't match",
				2, test.getCost());
		assertEquals("Estate victory points doesn't match",
				1, test.getVictoryPoints());
		assertEquals("Estate value doesn't match",
				0, test.getTreasure());
		assertTrue("Estate type doesn't match",
				test.isCardType(CardType.Victory));
		
	}

}
