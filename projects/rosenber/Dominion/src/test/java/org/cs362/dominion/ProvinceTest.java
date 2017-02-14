package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProvinceTest {

	@Test
	public void testValues() {
		
		Card test = new Province();
		assertEquals("Province name doesn't match",
				"Province", test.getName());
		assertEquals("Province cost doesn't match",
				8, test.getCost());
		assertEquals("Province victory points doesn't match",
				6, test.getVictoryPoints());
		assertEquals("Province value doesn't match",
				0, test.getTreasure());
		assertTrue("Curse type doesn't match",
				test.isCardType(CardType.Victory));
		
	}

}
