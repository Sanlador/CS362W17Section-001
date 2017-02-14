package org.cs362.dominion;

import junit.framework.TestCase;

public class AdventurerTest extends TestCase {
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
		
		int count = 0;
		for (Card c : p2.hand){
			if (c == Card.COPPER) count++;
		}
		p2.hand.add(Card.ADVENTURER);
		p2.play(Card.ADVENTURER);
		int count1 = 0;
		for (Card c : p2.hand){
			if (c == Card.COPPER) count1++;
		}
		assertEquals(count + 2, count1);
		
		p2.getDeck().getCards().remove(Card.COPPER);
		p2.getDeck().getCards().remove(Card.COPPER);
		p2.getDeck().getCards().remove(Card.COPPER);
		p2.getDeck().getCards().add(Card.COPPER);
		
		p2.hand.add(Card.ADVENTURER);
		p2.addActions(1);
		p2.play(Card.ADVENTURER);
		count = 0;
		for (Card c : p2.hand){
			if (c == Card.COPPER) count++;
		}
		assertEquals(count1 + 1, count);
	
		p2.discard.add(Card.COPPER);
		p2.discard.add(Card.COPPER);
		
		p2.hand.add(Card.ADVENTURER);
		p2.addActions(1);
		p2.play(Card.ADVENTURER);
		count1 = 0;
		for (Card c : p2.hand){
			if (c == Card.COPPER) count1++;
		}
		assertEquals(count + 2, count1);
	}
}
