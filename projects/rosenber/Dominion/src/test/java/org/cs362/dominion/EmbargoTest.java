package org.cs362.dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class EmbargoTest {

	@Test
	public void testValues() {
		
		Card test = new Embargo();
		assertEquals("Embargo name doesn't match",
				"Embargo", test.getName());
		assertEquals("Embargo cost doesn't match",
				2, test.getCost());
		assertEquals("Embargo victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Embargo value doesn't match",
				0, test.getTreasure());
		assertTrue("Embargo type doesn't match",
				test.isCardType(CardType.Action));
		
	}

	@Test
	public void testAction(){
		ArrayList<Player> players = new ArrayList<Player>();
		Player currentPlayer = new AIPlayer(1);
		Board board = new Board();
		
		board.createDeck(new Copper(), 5);
		
		Card c = new Embargo();
		
		c.Action(players, currentPlayer, board);
		
		assertEquals("Player didn't gain 2 coins",
				2, currentPlayer.getMoney());
		assertEquals("Trash didn't gain the card",
				1, board.numCardsInDeck(0));
		assertEquals("Copper deck didn't gain an embargo",
				1, board.getDeck(1).getEmbargos());
	}
	
}
