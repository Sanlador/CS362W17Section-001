package org.cs362.dominion;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class SalvagerTest {

	@Test
	public void testValues() {
		
		Card test = new Salvager();
		assertEquals("Salvager name doesn't match",
				"Salvager", test.getName());
		assertEquals("Salvager cost doesn't match",
				4, test.getCost());
		assertEquals("Salvager victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Salvager value doesn't match",
				0, test.getTreasure());
		assertTrue("Salvager type doesn't match",
				test.isCardType(CardType.Action));
		
	}

	@Test
	public void testAction(){
		ArrayList<Player> players = new ArrayList<Player>();
		Player currentPlayer = new AIPlayer(1);
		Board board = new Board();
		
		currentPlayer.giveCard(new Salvager());
		currentPlayer.drawCard();
		
		int trashSize = board.numCardsInDeck(0);
		int handSize = currentPlayer.numCardsHand();
		
		Card c = new Salvager();
		
		c.Action(players, currentPlayer, board);
		assertEquals("Card wasn't added to trash",
				trashSize+1, board.numCardsInDeck(0));
		assertEquals("Card wasn't removed from hand",
				handSize-1, currentPlayer.numCardsHand());
		assertEquals("Player didn't receive coins equals to value trashed",
				4, currentPlayer.getMoney());
	}
}













