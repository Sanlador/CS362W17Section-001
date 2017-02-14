package org.cs362.dominion;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class SmithyTest {

	@Test
	public void testValues() {
		
		Card test = new Smithy();
		assertEquals("Smithy name doesn't match",
				"Smithy", test.getName());
		assertEquals("Smithy cost doesn't match",
				3, test.getCost());
		assertEquals("Smithy victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Smithy value doesn't match",
				0, test.getTreasure());
		assertTrue("smithy type doesn't match",
				test.isCardType(CardType.Action));
		
	}
	
	@Test
	public void testAction(){
		ArrayList<Player> players = new ArrayList<Player>();
		Player currentPlayer = new AIPlayer(1);
		Board board = new Board();
		for(int j=0; j<3; j++)
			currentPlayer.giveCard(new Copper());
		
		Card c = new Smithy();
		
		int size = currentPlayer.numCardsHand();
		c.Action(players, currentPlayer, board);
		assertEquals("Player didn't draw three cards",
				size+3, currentPlayer.numCardsHand());
	}
}
