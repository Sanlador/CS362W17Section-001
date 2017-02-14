package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class VillageTest {

	@Test
	public void testValues() {
		
		Card test = new Village();
		assertEquals("Village name doesn't match",
				"Village", test.getName());
		assertEquals("Village cost doesn't match",
				3, test.getCost());
		assertEquals("Village victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Village value doesn't match",
				0, test.getTreasure());
		assertTrue("Village type doesn't match",
				test.isCardType(CardType.Action));		
	}
	
	@Test
	public void testAction(){
		ArrayList<Player> plist = new ArrayList<Player>();
		Player player = new AIPlayer(1);
		plist.add(player);
		Board board = new Board();
		
		player.giveCard(new Copper());
		
		int handSize = player.numCardsHand();
		
		Card c = new Village();
		c.Action(plist, player, board);
		assertEquals("Player didn't draw a card",
				handSize+1, player.numCardsHand());
		assertEquals("Player didn't gain 2 actions",
				player.getActions(), 2);
		
		
	}
}
