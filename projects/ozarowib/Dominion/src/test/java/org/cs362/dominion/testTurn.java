package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class testTurn
{
	@Test
	// Tests the Player functions for choosing and using cards
	public void test() throws Throwable {
		// Set up a test game
		Randomness.reset(System.currentTimeMillis());
		GameState state, startstate;
		List<Card> cards;
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		// Set up test players
		Player p = new Player(state, "test-player-1");
    	// Simulate a starting hand of 1 Action card and 4 Treasure
    	p.hand.add(Card.getCard(cards,Card.CardName.Smithy));
    	p.hand.add(Card.getCard(cards,Card.CardName.Copper));
    	p.hand.add(Card.getCard(cards,Card.CardName.Copper));
    	p.hand.add(Card.getCard(cards,Card.CardName.Copper));
    	p.hand.add(Card.getCard(cards,Card.CardName.Copper));
		state.addPlayer(p);
		p = new Player(state, "test-player-2");
    	p.hand.add(Card.getCard(cards,Card.CardName.Smithy));
    	p.hand.add(Card.getCard(cards,Card.CardName.Copper));
    	p.hand.add(Card.getCard(cards,Card.CardName.Copper));
    	p.hand.add(Card.getCard(cards,Card.CardName.Copper));
    	p.hand.add(Card.getCard(cards,Card.CardName.Copper));
		state.addPlayer(p);
		state.initializeTest();
		// Original state to compare results against
		startstate=(GameState) state.clone();
		// state.players.get(0);	// player1
		// state.players.get(1);	// player2

		// Each player will take one turn
		for (Player player : state.players)
		{
			int i = 0;
			// Test that player hand was drawn correctly
			assertEquals( player.hand.size(), 5 );
			// Run action phase
			player.playKingdomCard();
			// Player should use the Smithy card
			// Hand should go -1 from use then +3 from draw, or +2 from original size
			assertEquals( player.hand.size(), startstate.players.get(i).hand.size()+2 );
			// Deck should go -3 from original size
			assertEquals( player.deck.size(), startstate.players.get(i).deck.size()-3 );
			// Smithy should go to discard pile
			assertEquals( player.discard.size(), startstate.players.get(i).discard.size()+1 );
			assertEquals( player.discard.get(0).getCardName(), Card.CardName.Smithy );
			// Run treasure phase
			player.playTreasureCard();
			// Player will have at least the 4 coin from starting hand
			assertFalse( player.coins < 4 );
			// Note the current discard pile size
			int discardsize = player.discard.size();
			// Run buy phase
			player.buyCard();
			// Player has one buy and at least 4 coin
			// So player should buy exactly one card and send it to discard pile
			assertEquals( player.discard.size(), discardsize+1 );
			// Run cleanup
			player.endTurn();
			// Hand should be redrawn to 5
			assertEquals( player.hand.size(), 5 );
			// Because the starting hand was simulated, player did not have to shuffle discard yet
			// So deck should still have 2 cards left to draw
			assertEquals( player.deck.size(), 2 );
			// Verify player actions, coins, and buys all reset for next turn
			assertEquals( player.numActions, 1 );
			assertEquals( player.coins, 0 );
			assertEquals( player.numBuys, 1 );
			// Turn complete, repeat for second player to finish round
			i = 1;
		}
	}
}
