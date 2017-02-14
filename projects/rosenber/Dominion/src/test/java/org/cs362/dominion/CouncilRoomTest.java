package org.cs362.dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class CouncilRoomTest {

	@Test
	public void testValues() {
		
		Card test = new CouncilRoom();
		assertEquals("Council Room name doesn't match",
				"Council Room", test.getName());
		assertEquals("Council Room cost doesn't match",
				5, test.getCost());
		assertEquals("Council Room victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Council Room value doesn't match",
				0, test.getTreasure());
		assertTrue("Council type doesn't match",
				test.isCardType(CardType.Action));
		
	}

	@Test
	public void testAction(){
		ArrayList<Player> plist = new ArrayList<Player>();
		Player player = new AIPlayer(1);
		plist.add(player);
		Board board = new Board();
		
		for(int j=0; j<4; j++)
			player.giveCard(new Copper());
		
		Player other = new AIPlayer(2);
		other.giveCard(new Copper());
		plist.add(other);

		Card c = new CouncilRoom();
		c.Action(plist, player, board);
		
		assertEquals("Player didn't draw 4 cards",
				4, player.numCardsHand());
		assertEquals("Other player didn't draw 1 card",
				1, other.numCardsHand());
		
	}
	
}
