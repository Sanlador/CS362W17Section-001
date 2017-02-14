package org.cs362.dominion;

import java.util.ArrayList;

import junit.framework.TestCase;

public class SalavagerTest extends TestCase {
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
		
		p2.hand.add(Card.ESTATE); //Salvager won't trash copper since cost == 0;
		ArrayList<Card> handCopy = new ArrayList<>(p2.hand);
		p2.hand.add(Card.SALVAGER);
		p2.play(Card.SALVAGER);
		for (Card c : p2.hand){
			handCopy.remove(c);
		}
		assertEquals(1, handCopy.size());
		assertEquals(handCopy.get(0).cost(),p2.gold());
		
		p2.addGold(p2.gold() * -1);
		p2.hand.clear();
		p2.hand.add(Card.SALVAGER);
		p2.hand.add(Card.SALVAGER);
		p2.play(Card.SALVAGER);
		assertEquals(Card.SALVAGER.cost(), p2.gold());
	}
}
