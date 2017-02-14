package org.cs362.dominion;

import junit.framework.TestCase;

public class SeaHagTest extends TestCase {
	public void test(){
		Card[] kCard = new Card[10];
		kCard[0] = Card.ADVENTURER;
		kCard[1] = Card.SMITHY;
		kCard[2] = Card.GARDENS;
		kCard[3] = Card.MINE;
		kCard[4] = Card.REMODEL;
		kCard[5] = Card.COUNCIL_ROOM;
		kCard[6] = Card.GREAT_HALL;
		kCard[7] = Card.DUCHY;
		kCard[8] = Card.FEAST;
		kCard[9] = Card.VILLAGE;
		Game game = new Game(kCard);
		Player p1 = new Player(game);
		Player p2 = new Player(game);
		Player p3 = new Player(game);
		
		game.start();
		
		p2.hand.add(Card.SEA_HAG);
		p2.play(Card.SEA_HAG);
		
		assertEquals(5, p1.getDeck().size());
		assertEquals(1, p1.discard.size());
		assertEquals(Card.CURSE, p1.getDeck().peek());
		
		assertEquals(5, p3.getDeck().size());
		assertEquals(1, p3.discard.size());
		assertEquals(Card.CURSE, p3.getDeck().peek());
		
		assertEquals(5, p2.getDeck().size());
		assertEquals(0, p2.discard.size());
		assertFalse(Card.CURSE == p2.getDeck().peek());
	}
}
