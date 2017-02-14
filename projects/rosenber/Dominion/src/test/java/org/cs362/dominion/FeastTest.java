package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class FeastTest {

	@Test
	public void testValues() {
		
		Card test = new Feast();
		assertEquals("Feast name doesn't match",
				"Feast", test.getName());
		assertEquals("Feast cost doesn't match",
				4, test.getCost());
		assertEquals("Feast victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Feast value doesn't match",
				0, test.getTreasure());
		assertTrue("Feast type doesn't match",
				test.isCardType(CardType.Action));
		
	}

	@Test
	public void testAction(){
		ArrayList<Player> players = new ArrayList<Player>();
		Player currentPlayer = new AIPlayer(1);
		Board board = new Board();
		board.createDeck(new Copper(), 5);
		board.createDeck(new Gold(), 5);
		
		Card c = new Feast();
		
		c.Action(players, currentPlayer, board);
		currentPlayer.drawCard();
		
		assertEquals("Trash didn't gain the card",
				1, board.numCardsInDeck(0));
		try{
			currentPlayer.playCard("Copper");
		}
		catch(Exception e){
			fail("Player doesn't have a copper");
		}
	}
	
}
