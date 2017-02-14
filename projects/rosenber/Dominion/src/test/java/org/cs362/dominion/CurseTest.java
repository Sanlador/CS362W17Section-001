package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class CurseTest {

	@Test
	public void testValues() {
		
		Card test = new Curse();
		assertEquals("Curse name doesn't match",
				"Curse", test.getName());
		assertEquals("Curse cost doesn't match",
				0, test.getCost());
		assertEquals("Curse victory points doesn't match",
				-1, test.getVictoryPoints());
		assertEquals("Curse value doesn't match",
				0, test.getTreasure());
		assertTrue("Curse type doesn't match",
				test.isCardType(CardType.Curse));
		
	}

}
