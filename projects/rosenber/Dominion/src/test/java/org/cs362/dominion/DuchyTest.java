package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class DuchyTest {

	@Test
	public void testValues() {
		
		Card test = new Duchy();
		assertEquals("Duchy name doesn't match",
				"Duchy", test.getName());
		assertEquals("Duchy cost doesn't match",
				5, test.getCost());
		assertEquals("Duchy victory points doesn't match",
				3, test.getVictoryPoints());
		assertEquals("Duchy value doesn't match",
				0, test.getTreasure());
		assertTrue("Duchy type doesn't match",
				test.isCardType(CardType.Victory));
		
	}

}
