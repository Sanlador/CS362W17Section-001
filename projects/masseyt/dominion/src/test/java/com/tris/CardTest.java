package com.tris;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tristan Massey on 2/12/2017.
 */
public class CardTest {
    Card actionCard = new Card(Card.Name.ADVENTURER, Card.Type.ACTION, 5, 5, 5, "THIS IS AN ACTION TEST");
    Card treasureCard = new Card(Card.Name.COPPER, Card.Type.TREASURE, 5, 5, 5, "THIS IS A TREASURE TEST");
    Card victoryCard = new Card(Card.Name.ESTATE, Card.Type.VICTORY, 5, 5, 5, "THIS IS A VICTORY TEST");

    Game g;
    Player p1, p2;
    List<Card> cards;
    @Before
    public void init() {
        cards = new ArrayList<Card>(Card.generate());
        g = new Game(cards);
        p1 = new Player(g, "testP1");
        g.addPlayer(p1);
        p2 = new Player(g, "testP2");
        g.addPlayer(p2);
        g.initGame();
    }


    @Test
    public void testAction() {
        assertTrue(actionCard.getName().equals(Card.Name.ADVENTURER));
        assertTrue(actionCard.getType().equals(Card.Type.ACTION));
        assertEquals(actionCard.getCost(), 5);
    }

    @Test
    public void testTreasure() {
        assertTrue(treasureCard.getName().equals(Card.Name.COPPER));
        assertTrue(treasureCard.getType().equals(Card.Type.TREASURE));
        assertEquals(treasureCard.getCost(), 5);
    }

    @Test
    public void testGen() {
        List<Card> testSet = Card.generate();
        assertTrue(testSet.size() == 20);
    }

    @Test
    public void testAdventurer() {
        p1.hand.add(Card.getCard(cards, Card.Name.ADVENTURER));
        p1.playAction();
        assertTrue(p1.hand.size() >= 6);
        assertEquals(p1.played.size(), 1);
    }

    @Test
    public void testAmbassador() {
        p1.hand.add(Card.getCard(cards, Card.Name.AMBASSADOR));
        p1.playAction();
        assertTrue(p1.hand.size() <= 3);
        assertEquals(p2.discard.size(), 1);
    }

    @Test
    public void testBaron() {
        p1.hand.add(Card.getCard(cards, Card.Name.BARON));
        p1.playAction();
        p1.listCards(p1.hand);
        assertEquals(p1.hand.size(), 4);
        assertEquals(p1.coins, 4);
        assertEquals(p1.played.size(), 1);
    }

    @Test
    public void testCouncilRoom() {
        p1.hand.add(Card.getCard(cards, Card.Name.COUNCIL_ROOM));
        p1.playAction();
        p1.listCards(p1.hand);
        assertEquals(p1.hand.size(), 9);
        assertEquals(p1.played.size(), 1);
        assertEquals(p1.buys, 2);
        assertEquals(p2.hand.size(), 6);
    }

    @Test
    public void testCutpurse() {
        p1.hand.add(Card.getCard(cards, Card.Name.CUTPURSE));
        p1.playAction();
        p1.listCards(p1.hand);
        assertEquals(p1.hand.size(), 5);
        assertEquals(p1.played.size(), 1);
        assertEquals(p1.coins, 2);
        assertEquals(p2.hand.size(), 4);
    }

    @Test
    public void testEmbargo() {
        p1.hand.add(Card.getCard(cards, Card.Name.EMBARGO));
        p1.playAction();
        assertEquals(p1.hand.size(), 5);
        assertEquals(p1.played.size(), 0); //trashed card
        assertEquals(p1.coins, 2);
    }

    @Test
    public void testFeast() {
        p1.hand.add(Card.getCard(cards, Card.Name.FEAST));
        p1.playAction();
        assertEquals(p1.hand.size(), 5);
        assertEquals(p1.played.size(), 0); //trashed card
        assertEquals(p1.discard.size(), 1);
    }

    @Test
    public void testGreatHall() {
        p1.hand.add(Card.getCard(cards, Card.Name.GREAT_HALL));
        p1.playAction();
        assertEquals(p1.hand.size(), 6);
        assertEquals(p1.played.size(), 1);
        assertEquals(p1.discard.size(), 0);
        assertEquals(p1.actions, 1);
        assertEquals(p1.score(), 4);
    }

    @Test
    public void testMine() {
        p1.hand.add(Card.getCard(cards, Card.Name.MINE));
        p1.playAction();
        assertEquals(p1.hand.size(), 5); //TODO BUG
        assertEquals(p1.played.size(), 1);
        assertEquals(p1.discard.size(), 0);
        assertEquals(p1.actions, 0);
    }

    @Test
    public void testRemodel() {
        p1.hand.add(Card.getCard(cards, Card.Name.REMODEL));
        p1.playAction();
        assertEquals(p1.hand.size(), 4);
        assertEquals(p1.played.size(), 1);
        assertEquals(p1.discard.size(), 1);
        assertEquals(p1.actions, 0);
    }

    @Test
    public void testSmithy() {
        p1.hand.add(Card.getCard(cards, Card.Name.SMITHY));
        p1.playAction();
        assertEquals(p1.hand.size(), 8);
        assertEquals(p1.played.size(), 1);
        assertEquals(p1.discard.size(), 0);
        assertEquals(p1.actions, 0);
    }

    @Test
    public void testVillage() {
        p1.hand.add(Card.getCard(cards, Card.Name.VILLAGE));
        p1.playAction();
        assertEquals(p1.hand.size(), 6);
        assertEquals(p1.played.size(), 1);
        assertEquals(p1.discard.size(), 0);
        assertEquals(p1.actions, 2);
    }

}