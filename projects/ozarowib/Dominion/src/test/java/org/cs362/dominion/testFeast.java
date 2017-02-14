package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class testFeast
{
	@Test
	// Tests playing a Feast card
	public void test() throws Throwable {
		// Set up a test game
		Randomness.reset(System.currentTimeMillis());
		GameState state, startstate;
		List<Card> cards;
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		// Give each player one of every card in hand
		Player p = new Player(state, "test-player-1");
		p.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
		p.hand.add(Card.getCard(cards,Card.CardName.Ambassador));
		p.hand.add(Card.getCard(cards,Card.CardName.Baron));
		p.hand.add(Card.getCard(cards,Card.CardName.Copper));
		p.hand.add(Card.getCard(cards,Card.CardName.Council_Room));
		p.hand.add(Card.getCard(cards,Card.CardName.Curse));
		p.hand.add(Card.getCard(cards,Card.CardName.Duchy));
		p.hand.add(Card.getCard(cards,Card.CardName.Estate));
		p.hand.add(Card.getCard(cards,Card.CardName.Feast));
		p.hand.add(Card.getCard(cards,Card.CardName.Festival));
		p.hand.add(Card.getCard(cards,Card.CardName.Gardens));
		p.hand.add(Card.getCard(cards,Card.CardName.Gold));
		p.hand.add(Card.getCard(cards,Card.CardName.Great_Hall));
		p.hand.add(Card.getCard(cards,Card.CardName.Laboratory));
		p.hand.add(Card.getCard(cards,Card.CardName.Mine));
		p.hand.add(Card.getCard(cards,Card.CardName.Province));
		p.hand.add(Card.getCard(cards,Card.CardName.Salvager));
		p.hand.add(Card.getCard(cards,Card.CardName.Silver));
		p.hand.add(Card.getCard(cards,Card.CardName.Smithy));
		p.hand.add(Card.getCard(cards,Card.CardName.Village));
		state.addPlayer(p);
		p = new Player(state, "test-player-2");
		p.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
		p.hand.add(Card.getCard(cards,Card.CardName.Ambassador));
		p.hand.add(Card.getCard(cards,Card.CardName.Baron));
		p.hand.add(Card.getCard(cards,Card.CardName.Copper));
		p.hand.add(Card.getCard(cards,Card.CardName.Council_Room));
		p.hand.add(Card.getCard(cards,Card.CardName.Curse));
		p.hand.add(Card.getCard(cards,Card.CardName.Duchy));
		p.hand.add(Card.getCard(cards,Card.CardName.Estate));
		p.hand.add(Card.getCard(cards,Card.CardName.Feast));
		p.hand.add(Card.getCard(cards,Card.CardName.Festival));
		p.hand.add(Card.getCard(cards,Card.CardName.Gardens));
		p.hand.add(Card.getCard(cards,Card.CardName.Gold));
		p.hand.add(Card.getCard(cards,Card.CardName.Great_Hall));
		p.hand.add(Card.getCard(cards,Card.CardName.Laboratory));
		p.hand.add(Card.getCard(cards,Card.CardName.Mine));
		p.hand.add(Card.getCard(cards,Card.CardName.Province));
		p.hand.add(Card.getCard(cards,Card.CardName.Salvager));
		p.hand.add(Card.getCard(cards,Card.CardName.Silver));
		p.hand.add(Card.getCard(cards,Card.CardName.Smithy));
		p.hand.add(Card.getCard(cards,Card.CardName.Village));
		state.addPlayer(p);
		state.initializeTest();
		// Original state to compare results against
		startstate=(GameState) state.clone();
		// state.players.get(0);	// player1
		// state.players.get(1);	// player2

		// Each player plays a Feast
		for (Player player : state.players)
		{
			int i = 0;
			System.out.println(player.player_username + " is playing");
			Card c = Card.getCard(state.cards,Card.CardName.Feast);
			c.play(player, state);
			// Check that a card was removed from hand
			assertEquals( player.hand.size(), startstate.players.get(i).hand.size()-1 );
			// Make sure it went to the trash
			assertEquals( player.trashpile.size(), 1);
			// Make sure it was the Feast that was trashed
			assertEquals( player.trashpile.get(0).getCardName(), Card.CardName.Feast );
			// Check that player gained a card
			assertEquals( player.discard.size(), startstate.players.get(i).discard.size()+1 );
			// Make sure gained card value is not more than five
			int value = player.discard.get(0).getCost();
			assertTrue( value <= 5 );
			i = 1;
		}
	}
}
