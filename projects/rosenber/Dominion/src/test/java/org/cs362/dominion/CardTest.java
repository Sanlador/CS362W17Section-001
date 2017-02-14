package org.cs362.dominion;

import static org.junit.Assert.*;
import org.junit.Test;
public class CardTest {

	Card testCard;
	
	@Test
	public void test() {
		try{
			testCard = new Card();
		}
		catch(Exception e){
			fail("Default card constructor crashes");
		}
		
		testCard = new Card("James", 1, 2, 3, new CardType[] {CardType.Attack, CardType.Action});
		Card other = new Card(testCard);
		
		assertFalse("The card constructor doesn't create a new object",
				testCard == other);
		
		assertEquals("The names aren't the same", 
				testCard.getName(), other.getName());
		
		assertEquals("The costs aren't the same",
				testCard.getCost(), other.getCost());
		
		assertEquals("The victory points aren't the same",
				testCard.getVictoryPoints(), other.getVictoryPoints());
		
		assertEquals("The treasure amount isn't the same",
				testCard.getTreasure(), other.getTreasure());
		
		assertTrue("The test card isn't action type", 
				testCard.isCardType(CardType.Action));
		
		assertTrue("The test card isn't attack type", 
				testCard.isCardType(CardType.Attack));
		
		assertTrue("The other card is the wrong type",
				other.isCardType(CardType.Action));
		
		assertFalse("The test card is reaction type",
				testCard.isCardType(CardType.Reaction));
		
		assertEquals("Card name isn't James",
				testCard.getName(), "James");
		
		assertEquals("Cost isn't 1",
				testCard.getCost(), 1);
		
		assertEquals("VictoryPoints isn't 2",
				testCard.getVictoryPoints(), 2);
		
		assertEquals("Treasure ammount isn't 3",
				testCard.getTreasure(), 3);
	}
}
















































