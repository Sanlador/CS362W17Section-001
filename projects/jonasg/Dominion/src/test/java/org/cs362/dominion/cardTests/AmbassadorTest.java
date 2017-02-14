package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class AmbassadorTest {

    @Test
    public void test0()  throws Throwable  {
	Randomness.reset();

	//initialize a game state and player cards
	List<Card> cards = new ArrayList<Card>(Card.createCards());
	GameState state = new GameState(cards);

	Player player = new Player(state, "player-1");
	player.hand.add(Card.getCard(cards,Card.CardName.Ambassador));
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

	//for later reference

	//sanity checking that it copied right
	for(Card card : state.gameBoard.keySet())
	    assertEquals(state.gameBoard.get(card), testState.gameBoard.get(card));

	// play the game
	p1.playKingdomCard();

	//determine how many got put back
	int putBack = p1t.hand.size() - (p1.hand.size() + p1.playedCards.size());

	System.out.println(" ***** Player-1 Status *****");
	System.out.println("Player put back = " + (p1t.hand.size() - p1.playedCards.size() - p1.hand.size())
		+ ", new on board = " + putBack);
	System.out.println("hand count = " + (p1.hand.size() + p1.playedCards.size()) +
		", expected = " + (p1t.hand.size() - putBack));
	System.out.println("deck count = " + p1.deck.size() +
		", expected = " + p1t.deck.size());
	System.out.println("numActions = " + p1.numActions +
		", expected = " + (p1t.numActions - 1));
	System.out.println("discard pile =" + p1.discard.size() +
		", expected = " + p1t.discard.size());
	assertEquals(p1t.hand.size() - (p1.hand.size() + p1.playedCards.size()), putBack);
	assertEquals(p1.deck.size(), p1t.deck.size());
	assertEquals(p1.discard.size(), p1t.discard.size());

	System.out.println(" ***** Player-2 Status *****" );
	System.out.println("hand count = " + p2.hand.size() +
		", expected = " + p2t.hand.size());
	System.out.println("deck count = " + p2.deck.size() +
		", expected = " + p2t.deck.size());
	System.out.println("discard = " + p2.discard.size() +
		", expected = " + (p2t.discard.size() + 1));
	assertEquals(p2.hand.size(), p2t.hand.size());
	assertEquals(p2.deck.size(), p2t.deck.size());
	assertEquals(p2.discard.size(), p2t.discard.size() + 1);

	System.out.println(" ***** Game State *****");
	int statePutBack = 0;
	int curKey = 0;
	for(Card card : state.gameBoard.keySet()) {
	    curKey = state.gameBoard.get(card) - testState.gameBoard.get(card);
	    System.out.println(card.getCardName() + " state = " + state.gameBoard.get(card) + " test = " +
		    testState.gameBoard.get(card));
	    if(curKey != 0) {
		if(statePutBack > 0) {
		    System.out.println("Internal error, check ambassador");
		    assertEquals(1, -1);
		}
		statePutBack += curKey;
		//check if the state reduced by the amount of players
		assertEquals((int) state.gameBoard.get(card),
			testState.gameBoard.get(card) - testState.players.size() + 1 + putBack);
	    }
	}
    }
}
