package com.omalleya.app;

import static junit.framework.Assert.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.LinkedList;
import java.util.List;

import com.omalleya.app.Card.CardName;

/**
 * Unit test for simple App.
 */
public class PlayerTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PlayerTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PlayerTest.class );
    }

    /**
     * Tests initialize turn
     */
    public void testInitializeTurn()
    {
        List<Card> cards = Card.createCards();
        GameState gs = new GameState(cards);
        Player p1 = new Player(gs, "player 1");
        Player p2 = new Player(gs, "player 2");

        gs.addPlayer(p1);
        gs.addPlayer(p2);
        gs.initializeGame();
        p1.initializePlayerTurn();

        assertTrue( p1.hand.size() == 5 && p1.deck.size() == 5 
                    && p1.numActions == 1 && p1.coins == 0 && p1.numBuys == 1
        );
    }

    /**
     * Tests action phase
     */
    public void testAction()
    {
        List<Card> cards = Card.createCards();
        GameState gs = new GameState(cards);
        Player p1 = new Player(gs, "player 1");
        Player p2 = new Player(gs, "player 2");

        gs.addPlayer(p1);
        gs.addPlayer(p2);
        gs.initializeGame();

        //add Smithy to test that played cards are cleaned up too
        p1.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, CardName.Smithy));
        p1.action();

        //hand and played cards should be 0, discarded should have the card gained
        assertTrue( p1.playedCards.size() == 1 && p1.playedCards.contains(Card.getCard(cards, CardName.Smithy))
                    && !p1.hand.contains(Card.getCard(cards, CardName.Smithy))
                    && !p1.deck.contains(Card.getCard(cards, CardName.Smithy))
        );
    }

    /**
     * Tests playing of treasure cards
     */
    public void testTreasureCards()
    {
        List<Card> cards = Card.createCards();
        GameState gs = new GameState(cards);
        Player p1 = new Player(gs, "player 1");
        Player p2 = new Player(gs, "player 2");

        gs.addPlayer(p1);
        gs.addPlayer(p2);
        gs.initializeGame();

        //getting correct number of coins player should have
        int finalCoins = 0;
        for(Card c : p1.hand) {
            if(c == Card.getCard(cards, CardName.Copper))
                finalCoins++;
        }

        p1.playTreasureCards();
        assertTrue(p1.coins == finalCoins && p1.playedCards.contains(Card.getCard(cards, CardName.Copper))
            && !p1.hand.contains(Card.getCard(cards, CardName.Copper))
        );
    }

    /**
     * Tests buy phase
     */
    public void testBuy()
    {
        List<Card> cards = Card.createCards();
        GameState gs = new GameState(cards);
        Player p1 = new Player(gs, "player 1");
        Player p2 = new Player(gs, "player 2");

        gs.addPlayer(p1);
        gs.addPlayer(p2);
        gs.initializeGame();

        //add Smithy to test that played cards are cleaned up too
        p1.initializePlayerTurn();
        p1.buy(gs);

        //hand and played cards should be 0, discarded should have the card gained
        assertTrue( p1.discarded.size() == 1 );
    }

    /**
     * Tests clean up phase
     */
    public void testCleanUp()
    {
        List<Card> cards = Card.createCards();
        GameState gs = new GameState(cards);
        Player p1 = new Player(gs, "player 1");
        Player p2 = new Player(gs, "player 2");

        gs.addPlayer(p1);
        gs.addPlayer(p2);
        gs.initializeGame();

        //add Smithy to test that played cards are cleaned up too
        p1.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, CardName.Smithy));
        p1.action();
        p1.cleanUp();

        //hand and played cards should be 0, discarded should have all cards that were available
        assertTrue( p1.hand.size() == 0 && p1.discarded.size() == 9 && p1.playedCards.size() == 0 );
    }

    /**
     * Tests draw card
     */
    public void testDrawCard()
    {
        List<Card> cards = Card.createCards();
        GameState gs = new GameState(cards);
        Player p1 = new Player(gs, "player 1");
        Player p2 = new Player(gs, "player 2");

        gs.addPlayer(p1);
        gs.addPlayer(p2);
        gs.initializeGame();

        int initialDeckSize = p1.deck.size();
        p1.drawCard();
        assertTrue( p1.hand.size() == 6 && initialDeckSize == p1.deck.size()+1);
    }

    /**
     * Tests gain card
     */
    public void testGainCard()
    {
        List<Card> cards = Card.createCards();
        GameState gs = new GameState(cards);
        Player p1 = new Player(gs, "player 1");
        Player p2 = new Player(gs, "player 2");

        gs.addPlayer(p1);
        gs.addPlayer(p2);
        gs.initializeGame();

        p1.gain(Card.getCard(cards, CardName.Duchy));
        assertTrue( p1.discarded.contains(Card.getCard(cards, CardName.Duchy)) &&
                    !p1.hand.contains(Card.getCard(cards, CardName.Duchy)) &&
                    !p1.deck.contains(Card.getCard(cards, CardName.Duchy))
        );
    }

    /**
     * Tests discard
     */
    public void testDiscard()
    {
        List<Card> cards = Card.createCards();
        GameState gs = new GameState(cards);
        Player p1 = new Player(gs, "player 1");
        Player p2 = new Player(gs, "player 2");

        gs.addPlayer(p1);
        gs.addPlayer(p2);
        gs.initializeGame();

        p1.discard(Card.getCard(cards, CardName.Copper));
        p1.discard(Card.getCard(cards, CardName.Smithy));

        assertTrue( p1.discarded.contains(Card.getCard(cards, CardName.Copper)) &&
                    !p1.discarded.contains(Card.getCard(cards, CardName.Smithy)) &&
                    p1.discarded.size() == 1
        );
    }

    /**
     * Tests score for
     */
    public void testScoreFor()
    {
        List<Card> cards = Card.createCards();
        GameState gs = new GameState(cards);
        Player p1 = new Player(gs, "player 1");
        Player p2 = new Player(gs, "player 2");

        gs.addPlayer(p1);
        gs.addPlayer(p2);
        gs.initializeGame();

        assertTrue( p1.scoreFor() == 9 );
    }
}
