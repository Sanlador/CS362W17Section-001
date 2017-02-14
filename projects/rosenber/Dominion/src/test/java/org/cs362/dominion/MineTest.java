package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class MineTest {

	@Test
	public void testValues() {
		
		Card test = new Mine();
		assertEquals("Mine name doesn't match",
				"Mine", test.getName());
		assertEquals("Mine cost doesn't match",
				5, test.getCost());
		assertEquals("Mine victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Mine value doesn't match",
				0, test.getTreasure());
		assertTrue("Mine type doesn't match",
				test.isCardType(CardType.Action));
	}

	@Test
	public void testAction(){
		ArrayList<Player> players = new ArrayList<Player>();
		Player currentPlayer = new AIPlayer(1);
		Board board = new Board();
		
		currentPlayer.giveCard(new Copper());
		currentPlayer.drawCard();
		board.createDeck(new Silver(), 1);
		
		int trash = board.numCardsInDeck(0);
		
		Card c = new Mine();
		c.Action(players, currentPlayer, board);
		currentPlayer.drawCard();
		
		assertEquals("Trash didn't gain card",
				trash+1, board.numCardsInDeck(0));
		
		assertEquals("Player doesn't have 1 card",
				1, currentPlayer.numTotalCards());
		try{
			currentPlayer.playCard("Silver");
		}
		catch(Exception e){
			fail("Player's hand didn't contain a silver");
		}
		
	}
}


















