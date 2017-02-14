package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

public class CutpurseTest {

	@Test
	public void testValues() {
		
		Card test = new Cutpurse();
		assertEquals("Cutpurse name doesn't match",
				"Cutpurse", test.getName());
		assertEquals("Cutpurse cost doesn't match",
				4, test.getCost());
		assertEquals("Cutpurse victory points doesn't match",
				0, test.getVictoryPoints());
		assertEquals("Cutpurse value doesn't match",
				0, test.getTreasure());
		assertTrue("Cutpurse type doesn't match",
				test.isCardType(CardType.Action));
		assertTrue("Cutpurse type doesn't match",
				test.isCardType(CardType.Attack));
	}

	@Test
	public void testActionHasCopper(){
		ArrayList<Player> players = new ArrayList<Player>();
		Player currentPlayer = new AIPlayer(1);
		Board board = new Board();
		players.add(currentPlayer);
		Player other = new AIPlayer(2);
		players.add(other);
		other.giveCard(new Copper());
		other.drawCard();
		
		Card c = new Cutpurse();
		c.Action(players, currentPlayer, board);
		
		assertEquals("Player didn't gain coins",
				2, currentPlayer.getMoney());
		assertEquals("Player 2 didn't discard its copper",
				0, other.numCardsHand());
	}
	
	@Test
	public void testActionNoCopper(){
		ArrayList<Player> players = new ArrayList<Player>();
		Player currentPlayer = new AIPlayer(1);
		Board board = new Board();
		players.add(currentPlayer);
		Player other = new AIPlayer(2);
		players.add(other);
		other.giveCard(new Silver());
		other.drawCard();
		Card c = new Cutpurse();
		c.Action(players, currentPlayer, board);
		assertEquals("Player didn't gain coins",
				2, currentPlayer.getMoney());
		assertEquals("Player 2 discarded a card when it shouldn't have",
				1, other.numCardsHand());
	}
	
}












