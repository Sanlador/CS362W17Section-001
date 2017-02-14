package org.cs362.dominion;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {
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
		
		assertEquals(5, p2.getDeck().size());
		p2.discard.addAll(p2.hand);
		p2.hand.clear();
		p2.draw(6);
		assertEquals(4, p2.getDeck().size());
		assertEquals(6, p2.hand.size());
		
		p2.hand.add(Card.ADVENTURER);
		assertEquals(true, p2.hasActions());
		
		assertEquals(true, p2.play(Card.ADVENTURER));
		assertEquals(false, p2.hasActions());
		
		p2.spendGold();
		assertTrue(p2.gold() > 0);
		
		for(Card c : p2.hand){
			assertFalse(c.getType().contains(CardType.TREASURE));
		}
		int goldTest = p2.gold();
		assertEquals(true, p2.buy(Card.COUNCIL_ROOM));
		assertEquals(goldTest - 5, p2.gold());
		for (Deck d : game.kingdomCards){
			if (d.peek() == Card.COUNCIL_ROOM)
				assertEquals(9, d.size());
		}
		assertEquals(false, p2.buy(Card.PROVINCE));
		p2.addBuys(1);
		assertEquals(false, p2.buy(Card.PROVINCE));
		assertEquals(1, p2.buys());
		
		p2.addGold(9);
		assertEquals(false, p2.buy(Card.TREASURE_MAP));
		
	}
}
