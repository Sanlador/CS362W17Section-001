package org.cs362.dominion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TestPlayer {
	@Test
	public void testPlayer() {
		DominionBoard board = new DominionBoard();
		GameState state = new GameState(board);
		Player player = new Player("Player", board);
		
		assertEquals("Player not named correctly", "Player", player.GetName());
		
		int count = player.deck.GetCardCount();
		player.TakeTurn(state);
		assertTrue("Player not buying cards on turn", (count < player.deck.GetCardCount()));
		
		//test GainCurse
		int score = player.GetScore();
		player.GainCurse();
		assertTrue("Player not losing point on curse aquisition", (score > player.GetScore()));
		
		//test TrashFromHand
		int handSize = player.deck.GetHand().size();
		player.TrashFromHand();
		assertTrue("Player not discarding from hand", (handSize > player.deck.GetHand().size()));
		
	}
}
