package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class BuyTest {
    @Test
    public void test0()  throws Throwable  {
	Randomness.reset();

	int newCards = 3;
	// initialize a game state and player cards
	List<Card> cards = new ArrayList<Card>(Card.createCards());
	GameState state = new GameState(cards);

	Player player = new Player(state, "player-1");
	state.addPlayer(player);

	player = new Player(state, "player-2");
	state.addPlayer(player);

	System.out.println(player.toString());
	player.printStateGame();

	//Initialize the game!
	state.initializeGame();

	//copy or clone the game state to a test case
	GameState testState = (GameState) state.clone();

	//to shorten naming conventions
	Player p1 = state.players.get(0);
	Player p2 = state.players.get(1);
	Player p1t = testState.players.get(0);
	Player p2t = testState.players.get(1);

	System.out.println("hand count = " + p1.hand.size());
	System.out.println("deck count = " + p1.deck.size());
	System.out.println("coins = " + p1.coins);
	System.out.println("numActions = " + p1.numActions);
	System.out.println("numBuys = " + p1.numBuys);

	p1.buyCard(state);
	assertEquals(p1.numBuys, p1t.numBuys);
	assertEquals(p1.discard.size(), p1t.discard.size());
	assertEquals(p1.hand.size(), p1t.hand.size());

	p1.playTreasureCard();
	System.out.println("coins = " + p1.coins);
	assertEquals(p1.hand.size() + p1.playedCards.size(), p1t.hand.size());

	p1.buyCard(state);
	assertEquals(0, p1.numBuys);
	assertEquals(p1t.discard.size() + 1, p1.discard.size());
	assertEquals(p1t.hand.size(), p1.hand.size() + p1.playedCards.size());
    }

    @Test
    public void test1()  throws Throwable  {
	Randomness.reset();

	int newCards = 3;
	// initialize a game state and player cards
	List<Card> cards = new ArrayList<Card>(Card.createCards());
	GameState state = new GameState(cards);

	Player player = new Player(state, "player-1");
	state.addPlayer(player);

	player = new Player(state, "player-2");
	state.addPlayer(player);

	System.out.println(player.toString());
	player.printStateGame();

	//Initialize the game!
	state.initializeGame();

	//copy or clone the game state to a test case
	GameState testState = (GameState) state.clone();

	//to shorten naming conventions
	Player p1 = state.players.get(0);
	Player p2 = state.players.get(1);
	Player p1t = testState.players.get(0);
	Player p2t = testState.players.get(1);

	p1.playTreasureCard();
	p1.endTurn();
	assertEquals(p1t.coins, p1.coins);
	assertEquals(p1t.numActions, p1.numActions);
	assertEquals(p1t.numBuys, p1t.numBuys);
    }
}
