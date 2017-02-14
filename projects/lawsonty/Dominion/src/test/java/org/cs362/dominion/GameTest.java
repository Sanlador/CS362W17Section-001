package org.cs362.dominion;

import junit.framework.TestCase;

public class GameTest extends TestCase {
	
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
		Player p3 = new Player(game);
		assertEquals(false, game.addPlayer(p3));
		assertEquals(5, p1.hand.size());
		assertEquals(5, p1.getDeck().size());
		assertEquals(3, game.countVictory(p1));
		p1.getDeck().add(Card.PROVINCE);
		assertEquals(9, game.countVictory(p1));
		p1.getDeck().add(Card.DUCHY);
		assertEquals(12, game.countVictory(p1));
		
		assertEquals(false, game.ended());
		game.victorySupply.get(2).getCards().clear();
		assertEquals(true, game.ended());
	}
	
}
