package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class valuesTest {
    List<Card> cards = new ArrayList<Card>(Card.createCards());

    @Test
    public void gold()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Gold);
	assertEquals(6, card.getCost());
	assertEquals(0, card.score());
	assertEquals(3, card.getTreasureValue());
    }
    @Test
    public void silver()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Silver);
	assertEquals(3, card.getCost());
	assertEquals(0, card.score());
	assertEquals(2, card.getTreasureValue());
    }
    @Test
    public void copper()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Copper);
	assertEquals(0, card.getCost());
	assertEquals(0, card.score());
	assertEquals(1, card.getTreasureValue());
    }
    @Test
    public void province()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Province);
	assertEquals(8, card.getCost());
	assertEquals(6, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void duchy()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Duchy);
	assertEquals(5, card.getCost());
	assertEquals(3, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void estate()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Estate);
	assertEquals(2, card.getCost());
	assertEquals(1, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void curse()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Curse);
	assertEquals(0, card.getCost());
	assertEquals(-1, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void gardens()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Gardens);
	assertEquals(4, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void adventurer()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Adventurer);
	assertEquals(6, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void ambassador()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Ambassador);
	assertEquals(3, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void baron()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Baron);
	assertEquals(4, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void councilRoom()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.CouncilRoom);
	assertEquals(5, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void cutpurse()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Cutpurse);
	assertEquals(4, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void embargo()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Embargo);
	assertEquals(2, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void feast()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Feast);
	assertEquals(4, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void greatHall()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.GreatHall);
	assertEquals(3, card.getCost());
	assertEquals(1, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void mine()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Mine);
	assertEquals(5, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void minion()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Minion);
	assertEquals(5, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void seaHag()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.SeaHag);
	assertEquals(4, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void smithy()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Smithy);
	assertEquals(4, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
    @Test
    public void village()  throws Throwable  {
	Card card = Card.getCard(cards, Card.CardName.Village);
	assertEquals(3, card.getCost());
	assertEquals(0, card.score());
	assertEquals(0, card.getTreasureValue());
    }
}
