package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class MineTest {
    @Test
    public void test0()  throws Throwable  {
	Randomness.reset();

	//initialize a game state and player cards
	List<Card> cards = new ArrayList<Card>(Card.createCards());
	GameState state = new GameState(cards);

	Player player = new Player(state, "player-1");
	player.hand.add(Card.getCard(cards,Card.CardName.Mine));
	state.addPlayer(player);

	player = new Player(state, "player-2");
	state.addPlayer(player);

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

	Card copperTest = Card.getCard(cards, Card.CardName.Copper);
	Card silverTest = Card.getCard(cards, Card.CardName.Silver);
	Card goldTest = Card.getCard(cards, Card.CardName.Gold);

	for(int i = 0; i < 20; i++) {
	    p1.hand.clear();
	    p1.hand.add(copperTest);
	    // play the game
	    p1.playKingdomCard();

	    Card silver = Card.getCard(p1.hand, Card.CardName.Silver);

	    assertEquals(silverTest, silver);
	}
	for(int i = 0; i < 20; i++) {
	    p1.hand.clear();
	    p1.hand.add(silverTest);
	    // play the game
	    p1.playKingdomCard();

	    Card gold = Card.getCard(p1.hand, Card.CardName.Gold);

	    assertEquals(goldTest, gold);
	}

    }
}
