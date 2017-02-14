package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class PlayerTest {
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

	assertEquals(p1.scoreFor(), 3);
	p1.hand.add(Card.getCard(cards, Card.CardName.Gardens));
	assertEquals(p1.scoreFor(), 4);

	p2.playTreasureCard();
	assertEquals(p2.coins, Card.filter(p2t.hand, Card.Type.TREASURE).size());

	p2.endTurn();
	assertEquals(p2.discard.size(), p2t.hand.size());
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

	System.out.println("hand count = " + p1.hand.size());
	System.out.println("deck count = " + p1.deck.size());
	System.out.println("coins = " + p1.coins);
	System.out.println("numActions = " + p1.numActions);
	System.out.println("numBuys = " + p1.numBuys);

	p1.initializePlayerTurn();

	System.out.println(" ***** Player-1 Status *****");
	System.out.println("numBuys = " + p1.numBuys +
		", expected = " + 1);
	System.out.println("coins = " + p1.coins +
		", expected = " + 0);
	System.out.println("numActions = " + p1.numActions +
		", expected = " + 1);
	assertEquals(p1.numBuys, 1);
	assertEquals(p1.coins, 0);
	assertEquals(p1.numActions, 1);
    }
}
