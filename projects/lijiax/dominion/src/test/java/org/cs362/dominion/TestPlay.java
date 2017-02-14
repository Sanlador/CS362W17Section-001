package org.cs362.dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestPlay {

    private GameState state;
    private Player player1;
    private Player player2;
    private List<Card> cards;

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
        assertEquals(player1.hand.size(), 10);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        player1.drawCard();
        player1.drawCard();
        player1.drawCard();
        assertEquals(player1.hand.size(), 8);
        assertEquals(player1.deck.size(), 2);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
    }
    @Test
    public void testDiscard() {
        player1.initializePlayerTurn();
        assertEquals(player1.hand.size(), 10);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        player1.discard(Card.getCard(player1.hand, Card.CardName.Copper));
        assertEquals(player1.hand.size(), 9);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 1);
        assertEquals(player1.playedCards.size(), 0);
    }
	}


