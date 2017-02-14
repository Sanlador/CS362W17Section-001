package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class testSalvager
{
	@Test
	// Tests playing a Salvager card
	public void test() throws Throwable {
		// Set up a test game
		Randomness.reset(System.currentTimeMillis());
		GameState state, startstate;
		List<Card> cards;
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		// Give each player one of every card in hand
		Player testPlayer = new Player(state, "test-player-1");
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Ambassador));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Baron));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Copper));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Council_Room));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Curse));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Duchy));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Estate));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Feast));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Festival));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Gardens));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Gold));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Great_Hall));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Laboratory));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Mine));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Province));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Salvager));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Silver));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Smithy));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Village));
		state.addPlayer(testPlayer);
		testPlayer = new Player(state, "test-player-2");
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Ambassador));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Baron));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Copper));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Council_Room));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Curse));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Duchy));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Estate));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Feast));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Festival));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Gardens));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Gold));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Great_Hall));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Laboratory));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Mine));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Province));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Salvager));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Silver));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Smithy));
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Village));
		state.addPlayer(testPlayer);
		state.initializeTest();
		// Original state to compare results against
		startstate=(GameState) state.clone();
		// state.players.get(0);	// player1
		// state.players.get(1);	// player2

		// Each player plays a Salvager
		for (Player player : state.players)
		{
			int i = 0;
			System.out.println(player.player_username + " is playing");
			Card c = Card.getCard(state.cards,Card.CardName.Salvager);
			c.play(player, state);
			// Verify player buys went up one
			assertEquals( player.numBuys, startstate.players.get(i).numBuys+1 );
			// Check that a card was taken from hand
			assertEquals( player.hand.size(), startstate.players.get(i).hand.size()-1 );
			// Make sure it went to the trash pile
			assertEquals( player.trashpile.size(), 1);
			// Check cost of the trashed card
			int trashcost = player.trashpile.get(0).getCost();
			// Make sure the player got that much coin
			assertEquals( player.coins, startstate.players.get(i).coins+trashcost );
			i = 1;
		}
	}
}
