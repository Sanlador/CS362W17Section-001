package org.cs362.dominion;

import junit.framework.TestCase;

public class CouncilRoomTest extends TestCase {
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
		p2.hand.add(Card.COUNCIL_ROOM);
		p2.play(Card.COUNCIL_ROOM);
		assertEquals(6, p1.hand.size());
		assertEquals(2, p2.buys());
		assertEquals(9, p2.hand.size());
	}
}
