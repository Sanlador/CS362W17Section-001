package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class GameStateTest {
    @Test
    public void test0() throws Throwable {
	List<Card> cards = new ArrayList<Card>(Card.createCards());
	GameState state = new GameState(cards);

	Player player = new Player(state, "player-1");
	player.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
	state.addPlayer(player);

	player = new Player(state, "player-2");
	state.addPlayer(player);

	state.initializeGame();

	assertEquals(17, state.gameBoard.size());
    }
    @Test
    public void test1() throws Throwable {
	List<Card> cards = new ArrayList<Card>(Card.createCards());
	GameState state = new GameState(cards);

    	Player player = new Player(state, "player-1");
	player.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
	state.addPlayer(player);

	player = new Player(state, "player-2");
	state.addPlayer(player);

	state.initializeGame();

	for(Player p : state.players)
	    assertEquals(p.numBuys, 1);
    }

    @Test
    public void test2()  throws Throwable  {
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

	assertEquals(state.isGameOver(), false);
	state.play();
	System.out.println(state.toString());
	assertEquals(state.isGameOver(), true);
    }
}
