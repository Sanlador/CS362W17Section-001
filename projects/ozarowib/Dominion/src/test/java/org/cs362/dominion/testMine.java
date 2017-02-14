package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class testMine
{
	@Test
	// Tests playing a Mine card in general
	public void test1() throws Throwable {
		// Set up a test game
		Randomness.reset(System.currentTimeMillis());
		GameState state;
		List<Card> cards;
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		Player p = new Player(state, "test-player-1");
		p.hand.add(Card.getCard(cards,Card.CardName.Copper));
		p.hand.add(Card.getCard(cards,Card.CardName.Gold));
		p.hand.add(Card.getCard(cards,Card.CardName.Mine));
		p.hand.add(Card.getCard(cards,Card.CardName.Silver));
		state.addPlayer(p);
		p = new Player(state, "test-player-2");
		p.hand.add(Card.getCard(cards,Card.CardName.Copper));
		p.hand.add(Card.getCard(cards,Card.CardName.Gold));
		p.hand.add(Card.getCard(cards,Card.CardName.Mine));
		p.hand.add(Card.getCard(cards,Card.CardName.Silver));
		state.addPlayer(p);
		state.initializeTest();
		// state.players.get(0);	// player1
		// state.players.get(1);	// player2

		// Each player plays a Mine
		for (Player player : state.players)
		{
			System.out.println(player.player_username + " is playing");
			Card c = Card.getCard(state.cards,Card.CardName.Mine);
			c.play(player, state);
			// Check that a card was trashed
			assertEquals( player.trashpile.size(), 1);
			// Make sure it was a treasure card
			assertEquals( player.trashpile.get(0).getType(), Card.Type.TREASURE);
		}
	}
	
	@Test
	// Tests trashing Copper
	public void test2() throws Throwable {
		// Set up a test game
		Randomness.reset(System.currentTimeMillis());
		GameState state;
		List<Card> cards;
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		Player p = new Player(state, "test-player-1");
		p.hand.add(Card.getCard(cards,Card.CardName.Copper));
		p.hand.add(Card.getCard(cards,Card.CardName.Mine));
		state.addPlayer(p);
		p = new Player(state, "test-player-2");
		p.hand.add(Card.getCard(cards,Card.CardName.Copper));
		p.hand.add(Card.getCard(cards,Card.CardName.Mine));
		state.addPlayer(p);
		state.initializeTest();
		// state.players.get(0);	// player1
		// state.players.get(1);	// player2

		// Each player plays a Mine
		for (Player player : state.players)
		{
			System.out.println(player.player_username + " is playing");
			Card c = Card.getCard(state.cards,Card.CardName.Mine);
			c.play(player, state);
			// Check that Copper was trashed
			assertEquals( player.trashpile.get(0).getCardName(), Card.CardName.Copper );
			// Make sure Silver was gained
			List<Card> playerhand = Card.filter(player.hand, Card.Type.TREASURE);
			assertEquals( playerhand.get(0).getCardName(), Card.CardName.Silver );
		}
	}

	@Test
	// Tests trashing Silver
	public void test3() throws Throwable {
		// Set up a test game
		Randomness.reset(System.currentTimeMillis());
		GameState state;
		List<Card> cards;
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		Player p = new Player(state, "test-player-1");
		p.hand.add(Card.getCard(cards,Card.CardName.Silver));
		p.hand.add(Card.getCard(cards,Card.CardName.Mine));
		state.addPlayer(p);
		p = new Player(state, "test-player-2");
		p.hand.add(Card.getCard(cards,Card.CardName.Silver));
		p.hand.add(Card.getCard(cards,Card.CardName.Mine));
		state.addPlayer(p);
		state.initializeTest();
		// state.players.get(0);	// player1
		// state.players.get(1);	// player2
	
		// Each player plays a Mine
		for (Player player : state.players)
		{
			System.out.println(player.player_username + " is playing");
			Card c = Card.getCard(state.cards,Card.CardName.Mine);
			c.play(player, state);
			// Check that Silver was trashed
			assertEquals( player.trashpile.get(0).getCardName(), Card.CardName.Silver );
			// Make sure Gold was gained
			List<Card> playerhand = Card.filter(player.hand, Card.Type.TREASURE);
			assertEquals( playerhand.get(0).getCardName(), Card.CardName.Gold );
		}
	}

	@Test
	// Tests trashing Gold
	public void test4() throws Throwable {
		// Set up a test game
		Randomness.reset(System.currentTimeMillis());
		GameState state;
		List<Card> cards;
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		Player p = new Player(state, "test-player-1");
		p.hand.add(Card.getCard(cards,Card.CardName.Gold));
		p.hand.add(Card.getCard(cards,Card.CardName.Mine));
		state.addPlayer(p);
		p = new Player(state, "test-player-2");
		p.hand.add(Card.getCard(cards,Card.CardName.Gold));
		p.hand.add(Card.getCard(cards,Card.CardName.Mine));
		state.addPlayer(p);
		state.initializeTest();
		// state.players.get(0);	// player1
		// state.players.get(1);	// player2
	
		// Each player plays a Mine
		for (Player player : state.players)
		{
			System.out.println(player.player_username + " is playing");
			Card c = Card.getCard(state.cards,Card.CardName.Mine);
			c.play(player, state);
			// Check that Gold was trashed
			assertEquals( player.trashpile.get(0).getCardName(), Card.CardName.Gold );
			// Make sure Gold was gained back
			List<Card> playerhand = Card.filter(player.hand, Card.Type.TREASURE);
			assertEquals( playerhand.get(0).getCardName(), Card.CardName.Gold );
		}
	}
}
