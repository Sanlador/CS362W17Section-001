package org.cs362.dominion;

import junit.framework.TestCase;

public class DeckTest extends TestCase {
	
	public void test(){
		
		Deck deck = new Deck();
		
		assertEquals(0, deck.size());
		
		deck.add(Card.COPPER);
		assertEquals(1, deck.size());
		assertEquals(true, deck.getCards().contains(Card.COPPER));
		deck.add(Card.BARON);
		assertEquals(2, deck.size());
		assertEquals(true, deck.getCards().contains(Card.BARON));
		assertEquals(true, deck.getCards().contains(Card.COPPER));
		
		deck = new Deck(Card.ADVENTURER, 10);
		
		assertEquals(10, deck.size());
		assertEquals(true, deck.getCards().contains(Card.ADVENTURER));
		assertEquals(Card.ADVENTURER, deck.peek());
		deck.take();
		assertEquals(9, deck.size());
		
		assertEquals(0, deck.embargo());
		deck.addEmbargo();
		assertEquals(1, deck.embargo());
		
	}
}
