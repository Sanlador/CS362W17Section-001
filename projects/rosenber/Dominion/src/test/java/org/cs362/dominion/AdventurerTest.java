package org.cs362.dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class AdventurerTest {

	@Test
	public void testValues() {
		
		Card test = new Adventurer();
		assertEquals("Adventurer name doesn't match",
				"Adventurer", test.getName());
		assertEquals("Adventurer cost doesn't match",
				6, test.getCost());
		assertEquals("Adventurer victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Adventurer value doesn't match",
				0, test.getTreasure());
		assertTrue("Adventurer type doesn't match",
				test.isCardType(CardType.Action));
	}
	
	@Test
	public void testAction(){
		ArrayList<Player> plist = new ArrayList<Player>();
		Player player = new AIPlayer(1);
		plist.add(player);
		Board board = new Board();
		
		player.giveCard(new Copper());
		player.giveCard(new Copper());
		
		Card c = new Adventurer();
		c.Action(plist, player, board);
		
		assertEquals("Player didn't get two treasure cards",
				2, player.numCardsHand());
		
	}
}
