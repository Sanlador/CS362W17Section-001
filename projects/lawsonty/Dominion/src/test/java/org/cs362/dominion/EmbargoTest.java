package org.cs362.dominion;

import junit.framework.TestCase;

public class EmbargoTest extends TestCase {
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
		
		p2.hand.add(Card.EMBARGO);
		p2.play(Card.EMBARGO);
		assertEquals(false, p2.playArea.contains(Card.EMBARGO));
		p2.addGold(10);
		p2.buy(game.kingdomCards.get(0).peek());
		assertEquals(2, p2.discard.size());
		assertEquals(true, p2.discard.contains(Card.CURSE));
		
	}
}
