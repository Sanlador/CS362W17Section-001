package org.cs362.dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestDraw {
    private List<Card> cards;
    private GameState state;
    private Player player1;
    private Player player2;

    @Before
    public void initializeGame() {
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);
        player1 = new Player(state, "PLAYER 1");
        state.addPlayer(player1);
        player2 = new Player(state, "PLAYER 2");
        state.addPlayer(player2);
        state.initializeGame();
    }

    @Test
    public void testDraw() {
        player1.initializePlayerTurn();
        player1.drawCard();
        player1.drawCard();
        player1.drawCard();
        assertEquals(player1.hand.size(), 7);
        assertEquals(player1.deck.size(), 3);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
    }
    
	}


