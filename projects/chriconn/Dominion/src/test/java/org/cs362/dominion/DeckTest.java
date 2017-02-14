package org.cs362.dominion;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.Test;

//@Test goes over functions
//assertEquals("THIS IS TEXT", testTheThing, what it should be);

public class DeckTest {
    @Test
    public void createDeckAndTestSize() {
        Deck test = new Deck();

        assertEquals("The empty deck is not 0", test.deck.size(), 0);
        assertTrue("The empty() does not return true for an empty deck", test.empty());
        assertEquals("DrawCard() returns a card on an empty deck", test.drawCard(), null);
        assertFalse("The empty deck has a treasure type in it", test.hasType("treasure"));

        test.addCard(new Card("copper"));

        assertEquals("The single card copper deck is not named copper", test.getName(), "copper");
        assertFalse("The empty() returns true for a non-empty deck", test.empty());
        assertEquals("The deck with one copper does not have a size of 1", test.deck.size(), 1);
        assertEquals("The deck.size() and deck.numCards() dont match", test.deck.size(), test.numCards());
    }


    @Test
    public void testSingleCardDeck() {
        Deck test = new Deck();

        test.addCard(new Card("copper"));

        assertEquals("The copper card is not named a copper card", test.cardInfo(0).getName(), "copper");
        assertTrue("The deck with a single copper card does not contain a treasure card", test.hasType("treasure"));
        assertEquals("The deck with one copper card does not return an index of 0 for a treasure card", test.indexOfType("treasure"), 0);
    }


    @Test
    public void addAndRemoveCards() {
        Deck test = new Deck();

        Random rand = new Random();
        int n;

        for (int x = 0; x < 1000; x++) {
            n = rand.nextInt(4);
            if (n == 0) {
                test.addCard(new Card("copper"));
            }
            else if (n == 1) {
                test.addBottomCard(new Card("copper"));
            }
            else if (n == 3) {
                test.drawCard();
            }
            else {
                test.drawCard("copper");
            }
        }
    }

    @Test
    public void starterDeck() {
        Deck test = new Deck("starter");
    }

    @Test
    public void oneOfEachDeck() {
        Deck test = new Deck("oneofeach");
    }

    @Test
    public void addRandomCards() {
        Deck test = new Deck();

        //fill the deck with 100 random cards
        for (int x = 0; x < 100; x++) {
            test.addCard(randomCard());
        }

        test.shuffle();
        test.shuffle();
        test.shuffle();

        //print out each type of cards
        System.out.println("\n\nTreasure deck");
        test.printType("treasure");
        System.out.println("\n\nAction deck");
        test.printType("action");
        System.out.println("\n\nSpecial Action deck");
        test.printType("special action");
        System.out.println("\n\nVictory deck");
        test.printType("victory");

        //print the whole deck
        test.printDeck();

        //overwrite the deck 100 times with random numbers of random cards
        Random rand = new Random();
        for (int x = 0; x < 100; x++) {
            int n = rand.nextInt(10) + 1;
            test = new Deck(n, randomCard());
        }

        test = new Deck();
        test.addNumberOfCardType(rand.nextInt(30), randomCard());
    }

    @Test
    public void tokens() {
        Deck tokenTest = new Deck();
        assertEquals("Tokens on empty deck are not zero", tokenTest.getTokens(), 0);
        tokenTest.modifyTokens(3);
        assertEquals("Tokens on 3 token deck are not three", tokenTest.getTokens(), 3);
        tokenTest.resetTokens();
        assertEquals("Reset tokens on are not zero", tokenTest.getTokens(), 0);
    }

    @Test
    public void checkFindingFunctions() {
        Deck test = new Deck();
        for (int x = 0; x < 100; x++) {
            test.addCard(new Card("copper"));
        }

        assertEquals("IndexofType could not find treasure in a deck of coppers", test.indexOfType("treasure"), 0);
        assertEquals("IndexofType found an action in a deck of only coppers", test.indexOfType("action"), -1);


        assertEquals("IndexOf could not find a copper in a deck of only coppers", test.indexOf("copper"), 0);
        assertTrue("hasCard could not find a copper in a deck of only coppers", test.hasCard("copper"));
        assertEquals("IndexOf could find a smithy in a deck of only coppers", test.indexOf("smithy"), -1);
        assertFalse("hasCard could find a smithy in a deck of only coppers", test.hasCard("smithy"));

    }

    @Test
    public void testEmptyDeck() {
        Deck test = new Deck(1, new Card("gold"));
        assertEquals("The single item gold deck does not have only one card", test.numCards(), 1);
        test.drawCard();
        assertTrue("The gold deck was emptied and does not have 0 cards", test.empty());

        assertEquals("The empty gold deck get info for name is not gold", test.getName(), "gold");
        assertEquals("The empty gold deck get info for name is not gold", test.cardInfo(0).getName(), "gold");
        assertEquals("The empty gold deck get info for value is not 3", test.cardInfo(0).getValue(), 3);
        assertEquals("The empty gold deck get info for cost is not 6", test.cardInfo(0).getCost(), 6);
        assertEquals("The empty gold deck get info for description is not the proper description", test.cardInfo(0).getDescription(), "The gold card is a treasure card worth 3 coins");

        test.cardInfo(0).getType();
        assertEquals("The empty gold deck values it shouldn't have it does", test.getTokens(), 0);
        assertEquals("The empty gold deck values it shouldn't have it does", test.cardInfo(0).getBuys(), 0);

    }

    @Test
    public void twoDecks() {
        Deck one = new Deck(10, new Card("witch"));
        Deck two = new Deck(10, new Card("adventurer"));

        for (int x = 0; x < 20; x++) {
            one.addCard(two.drawCard());
        }

        System.out.println("TWO DECKS PRINTED");
        one.printDeck();
    }

    public Card randomCard() {
        String[] cardList = { "gold", "silver", "copper", "estate", "dutchy", "province", "adventurer", "ambassador", "baron", "council room", "cutpurse", "embargo", "feast", "gardens", "great hall", "mine", "witch", "cellar", "market", "village", "smithy", "salvager" };

        Random rand = new Random();
        int n = rand.nextInt(22);
        return new Card(cardList[n]);
    }

    public String randomType() {
        String[] type = {"treasure", "action", "special action", "victory"};

        Random rand = new Random();
        int n = rand.nextInt(4);
        return type[n];
    }
}
