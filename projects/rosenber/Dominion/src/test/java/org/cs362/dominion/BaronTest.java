package org.cs362.dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class BaronTest {

	@Test
	public void testValues() {
		
		Card test = new Baron();
		assertEquals("Baron name doesn't match",
				"Baron", test.getName());
		assertEquals("Baron cost doesn't match",
				4, test.getCost());
		assertEquals("Baron victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Baron value doesn't match",
				0, test.getTreasure());
		assertTrue("Baron type doesn't match",
				test.isCardType(CardType.Action));
		
	}

	@Test
	public void testAction(){
		ArrayList<Player> players = new ArrayList<Player>();
		Player currentPlayer = new AIPlayer(1);
		Board board = new Board();
		board.createDeck(new Estate(), 20);
		
		Card c = new Baron();
		c.Action(players, currentPlayer, board);
		currentPlayer.drawCard();
		
		assertEquals("Estate card wasn't drawn",
				19, board.numCardsInDeck("Estate"));
		assertEquals("Player didn't receive new estate card",
				1, currentPlayer.numTotalCards());
		assertEquals("Player didn't receive another buy",
				1, currentPlayer.getBuys());
		
		c.Action(players, currentPlayer, board);
		
		while(currentPlayer.getMoney() < 4){
			try{
				c.Action(players, currentPlayer, board);
			}
			catch(Exception e){
				board.getDeck(1).addTop(new Estate());
			}
		}
		
	}
	
}












