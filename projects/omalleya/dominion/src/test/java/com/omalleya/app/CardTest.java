package com.omalleya.app;

import static junit.framework.Assert.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.LinkedList;
import java.util.List;

import com.omalleya.app.Card.CardName;
import com.omalleya.app.Card.Type;

/**
 * Unit test for simple App.
 */
public class CardTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CardTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CardTest.class );
    }

    /**
     * Tests get type
     */
    public void testGetType()
    {
        Card c = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);

        assertTrue( c.getType() == Type.TREASURE );
    }

    /**
     * Tests get card name
     */
    public void testGetCardName()
    {
        Card c = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);

        assertTrue( c.getCardName() == CardName.Copper );
    }

    /**
     * Tests get treasure value
     */
    public void testGetTreasureVal()
    {
        Card c = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);

        assertTrue( c.getTreasureValue() == 1 );
    }

    /**
     * Tests get score
     */
    public void testGetScore()
    {
        Card c = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);

        assertTrue( c.getScore() == 0 );
    }

    /**
     * Tests get cost
     */
    public void testGetCost()
    {
        Card c = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);

        assertTrue( c.getScore() == 0 );
    }

    /**
     * Tests get card
     */
    public void testGetCard()
    {
        List<Card> cards = Card.createCards();
        GameState gs = new GameState(cards);
        Player p1 = new Player(gs, "player 1");
        Player p2 = new Player(gs, "player 2");

        gs.addPlayer(p1);
        gs.addPlayer(p2);
        gs.initializeGame();

        Card check = null;

        for(Card c : cards) {
            if(c.getCardName() == CardName.Copper)
                check = c;
        };

        assertTrue( check.equals(Card.getCard(cards, CardName.Copper)) );
    }

    /**
     * Tests get card
     */
    public void testFilter()
    {
        List<Card> cards = Card.createCards();

        List<Card> check = new LinkedList<Card>();
        for(Card c : cards) {
            if(c.getType() == Type.TREASURE)
                check.add(c);
        }

        assertTrue( Card.filter(cards, Type.TREASURE).equals(check) );
    }
}
