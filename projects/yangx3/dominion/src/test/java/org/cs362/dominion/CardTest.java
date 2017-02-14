package org.cs362.dominion;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.*;

/**
 * Created by 10463 on 2017/2/11.
 */
public class CardTest {
    private List<Card> cards=new ArrayList<Card>(Card.createCards());
    private GameState state=new GameState(cards);
    private Player player1=new Player(state, "Medium-Com 1");
    private Player player2=new Player(state, "Medium-Com 2");

    @Test
    public void getType() throws Exception {
        //test cards type
        System.out.println("Testing on card's Type: ");
        assertEquals(Card.getCard(cards, Card.CardName.Gold).getType(), Card.Type.TREASURE);
        assertEquals(Card.getCard(cards, Card.CardName.Silver).getType(), Card.Type.TREASURE);
        assertEquals(Card.getCard(cards, Card.CardName.Copper).getType(), Card.Type.TREASURE);

        assertEquals(Card.getCard(cards, Card.CardName.Province).getType(), Card.Type.VICTORY);
        assertEquals(Card.getCard(cards, Card.CardName.Duchy).getType(), Card.Type.VICTORY);
        assertEquals(Card.getCard(cards, Card.CardName.Estate).getType(), Card.Type.VICTORY);
        assertEquals(Card.getCard(cards, Card.CardName.Curse).getType(), Card.Type.VICTORY);

        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Smithy).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Village).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Ambassador).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Baron).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Embargo).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Gardens).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Mine).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Minion).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getType(), Card.Type.ACTION);
        System.out.println("Type complete");
    }

    @Test
    public void getCardName() throws Exception {
        //Test three of them
        System.out.println("Testing on card's Name (Get example of three treasures): ");
        assertEquals(Card.getCard(cards, Card.CardName.Gold).getCardName(), Card.CardName.Gold);
        assertEquals(Card.getCard(cards, Card.CardName.Silver).getCardName(), Card.CardName.Silver);
        assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), Card.CardName.Copper);
        System.out.println("Test complete");


    }

    @Test
    public void getCost() throws Exception {
        System.out.println("Testing on card's cost: ");
        assertEquals(Card.getCard(cards, Card.CardName.Gold).getCost(), 6);
        assertEquals(Card.getCard(cards, Card.CardName.Silver).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Copper).getCost(),0);

        assertEquals(Card.getCard(cards, Card.CardName.Province).getCost(), 8);
        assertEquals(Card.getCard(cards, Card.CardName.Duchy).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Estate).getCost(), 2);
        assertEquals(Card.getCard(cards, Card.CardName.Curse).getCost(), 0);

        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getCost(), 6);
        assertEquals(Card.getCard(cards, Card.CardName.Smithy).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Village).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Ambassador).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Baron).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Embargo).getCost(), 2);
        assertEquals(Card.getCard(cards, Card.CardName.Gardens).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Mine).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Minion).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getCost(), 4);
        System.out.println("Test complete ");

    }

    @Test
    public void getTreasureValue() throws Exception {
        System.out.println("Testing on card's value: ");
        assertEquals(Card.getCard(cards, Card.CardName.Gold).getTreasureValue(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Silver).getTreasureValue(), 2);
        assertEquals(Card.getCard(cards, Card.CardName.Copper).getTreasureValue(),1);

        assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).getTreasureValue(), 2);
        assertEquals(Card.getCard(cards, Card.CardName.Embargo).getTreasureValue(), 2);
        System.out.println("Testing on card's value: ");

    }

}