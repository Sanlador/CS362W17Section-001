package org.cs362.dominion;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Created by 10463 on 2017/2/11.
 */
public class PlayDominionTest {
    private List<Card> cards = new ArrayList<Card>(Card.createCards());
    private GameState state = new GameState(cards);
    private Player player1=new Player(state, "Easy-Com 1");
    private Player player2=new Player(state, "Easy-Com 2");

    @Test
    public void testInitializeGame(){
        state.addPlayer(player1);
        state.addPlayer(player2);
        state.initializeGame();
        System.out.println("Play fair, then: ");
        System.out.println("player1's hand: "+ player1.hand.size());
        System.out.println("player1's deck: "+ player1.deck.size());
        System.out.println("player1's discard: "+ player1.discard.size());
        System.out.println("player2's hand: "+ player1.hand.size());
        System.out.println("player2's deck: "+ player1.deck.size());
        System.out.println("player2's discard: "+ player1.discard.size());

        assertEquals(player1.hand.size(), player2.hand.size());
        assertEquals(player1.deck.size(), player2.deck.size());
        assertEquals(player1.discard.size(), player2.discard.size());
    }

    @Test
    public void testBoard(){
        state.addPlayer(player1);
        state.addPlayer(player2);
        state.initializeGame();
        assert(state.gameBoard!=null);
        System.out.println("Looks like board goes fine");
    }

    @Test
    public void testRound(){
        state.addPlayer(player1);
        state.addPlayer(player2);
        state.initializeGame();
        HashMap<Player, Integer> winners=state.play();
        assert(winners.get(player1)!= winners.get(player2));
    }

}