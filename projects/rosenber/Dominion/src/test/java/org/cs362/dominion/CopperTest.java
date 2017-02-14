package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class CopperTest {

	@Test
	public void testValues() {
		
		Card test = new Copper();
		assertEquals("Copper name doesn't match",
				"Copper", test.getName());
		assertEquals("Copper cost doesn't match",
				0, test.getCost());
		assertEquals("Copper victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Copper value doesn't match",
				1, test.getTreasure());
		assertTrue("Curse type doesn't match",
				test.isCardType(CardType.Treasure));
		
	}

}
