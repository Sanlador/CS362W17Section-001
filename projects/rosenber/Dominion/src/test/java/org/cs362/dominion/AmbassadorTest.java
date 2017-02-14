package org.cs362.dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class AmbassadorTest {

	@Test
	public void testValues() {
		
		Card test = new Ambassador();
		assertEquals("Ambassador name doesn't match",
				"Ambassador", test.getName());
		assertEquals("Ambassador cost doesn't match",
				3, test.getCost());
		assertEquals("Ambassador victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Ambassador value doesn't match",
				0, test.getTreasure());
		assertTrue("Ambassador type doesn't match",
				test.isCardType(CardType.Action));
		assertTrue("Ambassador type doesn't match",
				test.isCardType(CardType.Attack));
		
	}
	
	@Test
	public void testAction(){
		ArrayList<Player> plist = new ArrayList<Player>();
		Player player = new AIPlayer(1);
		plist.add(player);
		Board board = new Board();
		
		player.giveCard(new Copper());
		player.drawCard();
		
		Player other = new AIPlayer(2);
		plist.add(other);
		board.createDeck(new Copper(), 0);
		
		Card c = new Ambassador();
		
		c.Action(plist, player, board);
		other.drawCard();
		
		assertEquals("Deck should be empty",
				0, board.numCardsInDeck(1));
		assertEquals("Player didn't add its card to board",
				0, player.numCardsHand());
		assertEquals("Other player didn't gain copper",
				1, other.numCardsHand());
	}
	
}
