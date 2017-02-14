package org.cs362.dominion;

import static org.junit.Assert.*;
import org.junit.Test;

public class PlayerTest {

	Player test;
	
	private Card createCard(String name, int cost, int vic, int mon){
		return new Card(name, cost, vic, mon, new CardType[] {CardType.Action});
	}
	
	@Test
	public void testConstructorString() {
		
		test = new Player("test player", false);
		
		assertEquals("Name doesn't match name given",
				"test player", test.getName());
		assertEquals("Hand isn't empty",
				0, test.hand.size());
		assertEquals("Deck isn't empty",
				0, test.deck.size());
		assertEquals("Discard isn't empty",
				0, test.discard.size());
	}

	@Test
	public void testConstructorInt(){
		//TODO: implement this
	}
	
	@Test
	public void testSetters(){
		
		test = new Player("test player", false);
		
		test.setBuys(5);
		assertEquals("Buys doesn't equal what it was set to",
				5, test.getBuys());
		
		test.setActions(4);
		assertEquals("Actions doesn't equal what is was set to",
				4, test.getActions());
		
		test.setMoney(3);
		assertEquals("Money doesn't equal what it was set to",
				3, test.getMoney());
	}
	
	@Test
	public void testAdds(){
		
		test = new Player("test player", false);
		test.setBuys(1);
		test.addBuys(5);
		assertEquals("Buys doesn't equal what it was set to",
				6, test.getBuys());
		
		test.setActions(1);
		test.addActions(4);
		assertEquals("Actions doesn't equal what is was set to",
				5, test.getActions());
		
		test.setMoney(1);
		test.addMoney(3);
		assertEquals("Money doesn't equal what it was set to",
				4, test.getMoney());
		
	}
	
	@Test
	public void testHandDeckDiscardInteractions(){
		
		test = new Player("test player", false);
		Card c = createCard("foo", 1, 2, 3);
		test.hand.addCard(c);
		test.discardCard(c);
		assertEquals("Discard did not increase when adding card to it",
				test.discard.size(), 1);
		assertEquals("Deck increased in size when discarding card",
				test.deck.size(), 0);
		assertEquals("Hand increased in size when discarding card",
				test.hand.size(), 0);
		
		test.reshuffleDeck();
		assertEquals("Discard isn't empty after reshuffle",
				test.discard.size(), 0);
		assertEquals("Hand size changed when reshuffling deck",
				test.hand.size(), 0);
		assertEquals("Deck didn't gain all cards when reshuffled",
				test.deck.size(), 1);
		
		test.drawCard();
		assertEquals("Discard size changed when card drawn",
				test.discard.size(), 0);
		assertEquals("Deck didn't decrement when card drawn",
				test.deck.size(), 0);
		assertEquals("Hand didn't gain card when card drawn",
				test.hand.size(), 1);
		
		
		test.discardCard(c);
		test.drawCard();
		assertEquals("Hand didn't receive a card on the drawn with deck empty",
				test.hand.size(), 1);
		assertEquals("Discard didn't get reshuffled when drawing on empty deck",
				test.discard.size(), 0);
		assertEquals("Deck isn't empty after last card drawn",
				test.deck.size(), 0);
		
	}
	
	@Test
	public void testSeeHand(){
		
		test = new Player("test player", false);
		try{
			test.seeHand();
		}
		catch(Exception e){
			fail("issue when printing hand");
		}
		
	}
	
	@Test
	public void testMoneyVictoryComputations(){
		
		test = new Player("test player", false);
		Card c = createCard("foo", 0, 2, 4);
		test.giveCard(c);
		
		
		test.computeMoney();
		assertEquals("Player has money even though hand is empty after computing it",
				test.getMoney(), 0);
		
		assertEquals("Player has 0 victory points even though 2 are in discard",
				2, test.getVictoryPoints());
		
		test.reshuffleDeck();
		assertEquals("Player has 0 victory points even though 2 are in deck",
				2, test.getVictoryPoints());
		
		test.drawCard();
		assertEquals("Player has 0 victory points even though 2 are in hand",
				2, test.getVictoryPoints());
		
		test.computeMoney();
		assertEquals("Player has doesn't have 4 coins even though 4 are in hand",
				4, test.getMoney());
	}
	
	@Test
	public void testVictoryComputationWithGardens(){
		
		test = new AIPlayer(1);
		
		for(int j=0; j<30; j++)
			test.giveCard(new Copper());
		
		test.discard.addBottom(new Garden());
		test.deck.addBottom(new Garden());
		test.addToHand(new Garden());
		
		assertEquals("Victory points from gardens doesn't equal 3",
				9, test.getVictoryPoints());
	}
	
	
	
}











