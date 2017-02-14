package org.cs362.dominion;

import junit.framework.TestCase;

public class TreasureMapTest extends TestCase {
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
		
		game.start();
		
		p2.hand.add(Card.TREASURE_MAP);
		p2.play(Card.TREASURE_MAP);
		
		assertFalse(Card.GOLD == p2.getDeck().peek());
		assertEquals(true, p2.playArea.contains(Card.TREASURE_MAP));
		
		p2.hand.add(Card.TREASURE_MAP);
		p2.hand.add(Card.TREASURE_MAP);
		p2.play(Card.TREASURE_MAP);
		
		assertEquals(false, p2.hand.contains(Card.TREASURE_MAP));
		p2.playArea.remove(Card.TREASURE_MAP);
		int count = 0;
		while (p2.getDeck().peek() == Card.GOLD) {
			count++;
			p2.getDeck().take();
		}
		assertEquals(4, count);
		assertEquals(false, p2.playArea.contains(Card.TREASURE_MAP));
	}
}
