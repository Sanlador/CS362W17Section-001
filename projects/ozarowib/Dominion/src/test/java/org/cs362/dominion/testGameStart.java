package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class testGameStart
{
	@Test
	// Tests initializing a game board
	public void test() throws Throwable {
		// Set up a test game
		Randomness.reset(System.currentTimeMillis());
		GameState state;
		List<Card> cards;
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		Player p = new Player(state, "test-player-1");
		state.addPlayer(p);
		p = new Player(state, "test-player-2");
		state.addPlayer(p);
		state.initializeTest();
		// state.players.get(0);	// player1
		// state.players.get(1);	// player2

		for (Player player : state.players)
		{
			// Check size of player hands before drawing
			assertEquals( player.hand.size(), 0 );
			// Check size of player decks before drawing
			assertEquals( player.deck.size(), 10 );
			// Check card makeup of decks
			int coppercount = 0;
			int estatecount = 0;
			for (int i=0; i<10; i++)
			{
				Card checkcard = player.deck.get(i);
				if (checkcard.getCardName() == Card.CardName.Copper)
				{
					coppercount++;
				}
				if (checkcard.getCardName() == Card.CardName.Estate)
				{
					estatecount++;
				}
			}
			assertEquals( coppercount, 7 );
			assertEquals( estatecount, 3 );
			// Check size of player discard piles
			assertEquals( player.discard.size(), 0 );
			// Check size of player trash piles
			assertEquals( player.trashpile.size(), 0 );
		}
	}
}
