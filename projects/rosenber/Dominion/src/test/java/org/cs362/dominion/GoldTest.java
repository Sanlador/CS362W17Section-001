package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class GoldTest {

	@Test
	public void testValues() {
		
		Card test = new Gold();
		assertEquals("Gold name doesn't match",
				"Gold", test.getName());
		assertEquals("Gold cost doesn't match",
				6, test.getCost());
		assertEquals("Gold victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Gold value doesn't match",
				3, test.getTreasure());
		assertTrue("Curse type doesn't match",
				test.isCardType(CardType.Treasure));
		
	}

}
