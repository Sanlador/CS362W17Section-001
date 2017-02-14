package org.cs362.dominion;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;

public class AIPlayerTest {

	Player test;
	
	@Test
	public void testConstructor() {
		
		test = new AIPlayer(1);
		
		assertEquals("Name doesn't match name given",
				"Player_1", test.getName());
		assertEquals("Hand isn't empty",
				0, test.hand.size());
		assertEquals("Deck isn't empty",
				0, test.deck.size());
		assertEquals("Discard isn't empty",
				0, test.discard.size());
	}

	@Test
	public void testDiscard(){
		test = new AIPlayer(1);
		try{
			test.discardCard();
		}
		catch(Exception e){
			fail("Discard crashed when discarding card with hand empty");
		}
		test.giveCard(new Copper());
		test.drawCard();
		test.discardCard();
		assertEquals("Hand didn't lose card when discarding",
				0, test.numCardsHand());
		assertEquals("Discard didn't gain card when discarding",
				1, test.numCardsDiscard());
	}
	
	@Test
	public void testPlayCard(){
		test = new AIPlayer(1);
		
		try{
			assertNull("Play card didn't return null when hand empty",
					test.playCard());
		}
		catch(Exception e){
			fail("Play card crashed when hand empty");
		}
		Card c = new Feast();
		test.giveCard(c);
		test.drawCard();
		Card result = null;
		while(result == null){
			result = test.playCard();
		}
		assertEquals("Player didn't play a card they had",
				c, result);
	}
	
	@Test
	public void testBuyCard(){
		Random rand = new Random();
		
		for(int j=0; j<1000; j++){
			test = new AIPlayer(1);
			Board board = new Board();
			board.createDeck(new Copper(), 1);
			board.createDeck(new Silver(), 1);
			board.createDeck(new Gold(), 1);
			Card c = null;
			int num = rand.nextInt(3);
			switch(num){
			case 0:
				try{
					test.setMoney(0);
					test.buyCard(board);
					if(test.numTotalCards() < 1)
						break;
					test.drawCard();
					c = test.playCard(0);
					assertEquals("Player bought card they didn't have the money for",
							c.getName(), new Copper().getName());
					}
				catch(Exception e){}
				break;
			case 1:
				try{
					test.setMoney(3);
					test.buyCard(board);
					if(test.numTotalCards() < 1)
						break;
					test.drawCard();
					c = test.playCard(0);
					assertTrue("Player bought card they didn't have the money for",
							c.getName().equals(new Copper().getName()) ||
							c.getName().equals(new Silver().getName()));
				}
				catch(Exception e){}
				break;
				
			case 2:
				try{
					test.setMoney(6);
					test.buyCard(board);
					if(test.numTotalCards() < 1)
						break;
					test.drawCard();
					c = test.playCard(0);
					assertTrue("Player bought card they didn't have the money for",
							c.getName().equals(new Copper().getName()) ||
							c.getName().equals(new Silver().getName()) ||
							c.getName().equals(new Gold().getName()));
				}
				catch(Exception e){};
			}
		}
	}
}
