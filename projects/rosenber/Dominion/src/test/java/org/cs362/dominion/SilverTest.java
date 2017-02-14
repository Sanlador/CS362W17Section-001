package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class SilverTest {

	@Test
	public void testValues() {
		
		Card test = new Silver();
		assertEquals("Silver name doesn't match",
				"Silver", test.getName());
		assertEquals("Silver cost doesn't match",
				3, test.getCost());
		assertEquals("Silver victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Silver value doesn't match",
				2, test.getTreasure());
		assertTrue("Silver type doesn't match",
				test.isCardType(CardType.Treasure));
		
	}

}
