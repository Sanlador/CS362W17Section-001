package goertzel;


import org.junit.Test;

import static goertzel.Card.CardName.*;
import static goertzel.Card.CardType.*;
import static goertzel.Card.matchType;
import static junit.framework.TestCase.assertEquals;


public class CardTest {

    @Test
    public void testMatchType() {
        assertEquals(VICTORY, matchType(PROVINCE));
        assertEquals(VICTORY, matchType(DUCHY));
        assertEquals(VICTORY, matchType(ESTATE));
        assertEquals(VICTORY, matchType(CURSE));
        assertEquals(VICTORY, matchType(GARDENS));
        assertEquals(TREASURE, matchType(GOLD));
        assertEquals(TREASURE, matchType(SILVER));
        assertEquals(TREASURE, matchType(COPPER));
        assertEquals(ACTION, matchType(ADVENTURER));
        assertEquals(ACTION, matchType(AMBASSADOR));
        assertEquals(ACTION, matchType(BARON));
        assertEquals(ACTION, matchType(COUNCIL_ROOM));
        assertEquals(ACTION, matchType(CUTPURSE));
        assertEquals(ACTION, matchType(EMBARGO));
        assertEquals(ACTION, matchType(FEAST));
        assertEquals(ACTION, matchType(GREAT_HALL));
        assertEquals(ACTION, matchType(MINE));
        assertEquals(ACTION, matchType(VILLAGE));
        assertEquals(ACTION, matchType(SMITHY));
        assertEquals(ACTION, matchType(SALVAGER));
    }

    @Test
    public void testStats() {
        Card prov = new Card(PROVINCE);
        assertEquals(PROVINCE, prov.name);
        assertEquals(VICTORY, prov.type);
        assertEquals(8, prov.cost);
        assertEquals(0, prov.treasureVal);
        assertEquals(6, prov.victoryPoints);
        assertEquals(0, prov.actInc);
        assertEquals(0, prov.cardInc);
        assertEquals(0, prov.goldInc);
        assertEquals(0, prov.buyInc);

        Card gold = new Card(GOLD);
        assertEquals(GOLD, gold.name);
        assertEquals(TREASURE, gold.type);
        assertEquals(6, gold.cost);
        assertEquals(3, gold.treasureVal);
        assertEquals(0, gold.victoryPoints);
        assertEquals(0, gold.actInc);
        assertEquals(0, gold.cardInc);
        assertEquals(3, gold.goldInc);
        assertEquals(0, gold.buyInc);

        Card village = new Card(VILLAGE);
        assertEquals(VILLAGE, village.name);
        assertEquals(ACTION, village.type);
        assertEquals(3, village.cost);
        assertEquals(0, village.treasureVal);
        assertEquals(0, village.victoryPoints);
        assertEquals(2, village.actInc);
        assertEquals(1, village.cardInc);
        assertEquals(0, village.goldInc);
        assertEquals(0, village.buyInc);
    }


    @Test
    public void testProvince(){
        Card c = new Card(PROVINCE);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        assertEquals(3, p1.calcVP());
        assertEquals(5, p1.cloneHand().size());
        p1.putInHand(c);
        assertEquals(9, p1.calcVP());
        assertEquals(6, p1.cloneHand().size());
        c.play(p1);
        assertEquals(5, p1.cloneHand().size());
        assertEquals(1, p1.cloneDiscardPile().size());
    }

    @Test
    public void testDuchy(){
        Card c = new Card(DUCHY);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        assertEquals(3, p1.calcVP());
        assertEquals(5, p1.cloneHand().size());
        p1.putInHand(c);
        assertEquals(6, p1.calcVP());
        assertEquals(6, p1.cloneHand().size());
        c.play(p1);
        assertEquals(5, p1.cloneHand().size());
        assertEquals(1, p1.cloneDiscardPile().size());
    }

    @Test
    public void testEstate(){
        Card c = new Card(ESTATE);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        assertEquals(3, p1.calcVP());
        assertEquals(5, p1.cloneHand().size());
        p1.putInHand(c);
        assertEquals(4, p1.calcVP());
        assertEquals(6, p1.cloneHand().size());
        c.play(p1);
        assertEquals(5, p1.cloneHand().size());
        assertEquals(1, p1.cloneDiscardPile().size());
    }

    @Test
    public void testCurse(){
        Card c = new Card(CURSE);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        assertEquals(3, p1.calcVP());
        assertEquals(5, p1.cloneHand().size());
        p1.putInHand(c);
        assertEquals(2, p1.calcVP());
        assertEquals(6, p1.cloneHand().size());
        c.play(p1);
        assertEquals(5, p1.cloneHand().size());
        assertEquals(1, p1.cloneDiscardPile().size());
    }

    @Test
    public void testGold(){
        Card c = new Card(GOLD);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        int temp =  p1.getGoldCount();
        assertEquals(1, p1.getActionCount());
        assertEquals(5, p1.cloneHand().size());
        p1.putInHand(c);
        c.play(p1);
        assertEquals(1, p1.getActionCount());
        assertEquals(temp+3, p1.getGoldCount());
        assertEquals(5, p1.cloneHand().size());
        assertEquals(1, p1.cloneDiscardPile().size());
    }

    @Test
    public void testSilver(){
        Card c = new Card(SILVER);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        int temp =  p1.getGoldCount();
        assertEquals(1, p1.getActionCount());
        assertEquals(5, p1.cloneHand().size());
        p1.putInHand(c);
        c.play(p1);
        assertEquals(1, p1.getActionCount());
        assertEquals(temp+2, p1.getGoldCount());
        assertEquals(5, p1.cloneHand().size());
        assertEquals(1, p1.cloneDiscardPile().size());
    }

    @Test
    public void testCopper(){
        Card c = new Card(COPPER);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        int temp =  p1.getGoldCount();
        assertEquals(1, p1.getActionCount());
        assertEquals(5, p1.cloneHand().size());
        p1.putInHand(c);
        c.play(p1);
        assertEquals(1, p1.getActionCount());
        assertEquals(temp+1, p1.getGoldCount());
        assertEquals(5, p1.cloneHand().size());
        assertEquals(1, p1.cloneDiscardPile().size());
    }

    @Test
    public void testAdventurer(){
        Card c = new Card(ADVENTURER);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        p1.putInHand(c);
        assertEquals(1, p1.getActionCount());
        int temp1 = p1.cloneDeck().size();
        int temp2 = p1.cloneHand().size();
        int temp3 = p1.cloneDiscardPile().size();
        c.play(p1);
        assertEquals(0, p1.getActionCount());
        assertEquals(temp1 + temp3 - 1, p1.cloneDeck().size() + p1.cloneDiscardPile().size());
        assertEquals(temp2+1, p1.cloneHand().size());

    }

    @Test
    public void testAmbassador(){
        Card c = new Card(AMBASSADOR);
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");
        GameState board = new GameState();
        board.addPlayer(p1);
        board.addPlayer(p2);

        p1.putInHand(c);
        assertEquals(1, p1.getActionCount());
        int temp = p1.cloneHand().size();
        int temp2 = p2.getCardCount();
        c.play(p1);
        assertEquals(0, p1.getActionCount());
        assertEquals(true, p1.cloneHand().size() < temp);
        assertEquals(temp2 + 1, p2.getCardCount());
    }

    @Test
    public void testBaron(){
        Card c = new Card(BARON);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        p1.putInHand(c);
        assertEquals(1, p1.getActionCount());
        assertEquals(1, p1.getBuysCount());
        assertEquals(0, p1.getGoldCount());
        c.play(p1);
        assertEquals(0, p1.getActionCount());
        assertEquals(2, p1.getBuysCount());
        int g = p1.getGoldCount();
        assertEquals(true, g == 0 || g == 4);


    }

    @Test
    public void testCouncil_Room(){
        Card c = new Card(COUNCIL_ROOM);
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");
        GameState board = new GameState();
        board.addPlayer(p1);
        board.addPlayer(p2);

        p1.putInHand(c);
        int temp1 = p1.cloneHand().size();
        int temp2 = p1.cloneDeck().size() + p1.cloneDiscardPile().size();
        int temp3 = p2.cloneHand().size();
        int temp4 = p2.getCardCount();
        assertEquals(1, p1.getBuysCount());
        c.play(p1);
        assertEquals(temp1 + temp2, p1.getCardCount());
        assertEquals(temp1 + 3, p1.cloneHand().size());
        assertEquals(temp3+1, p2.cloneHand().size());
        assertEquals(temp4, p2.getCardCount());
        assertEquals(2, p1.getBuysCount());
    }

    @Test
    public void testCutpurse(){
        Card c = new Card(CUTPURSE);
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");
        GameState board = new GameState();
        board.addPlayer(p1);
        board.addPlayer(p2);

        p1.putInHand(c);
        assertEquals(0, p1.getGoldCount());
        c.play(p1);
        assertEquals(2, p1.getGoldCount());
        if (p2.cloneHand().size() == 4)
            assertEquals(1, p2.cloneDiscardPile().size());
        else
            for (Card d : p2.cloneHand())
                assertEquals(false, d.name == COPPER);
    }

    @Test
    public void testEmbargo(){
        Card c = new Card(EMBARGO);
        Player p1 = new Player("P1");
        Player p2 = new Player("P2");
        GameState board = new GameState();
        board.addPlayer(p1);
        board.addPlayer(p2);
        Supply_Stack s1 = new Supply_Stack(COPPER, 10);
        board.kingdom_supply.add(s1);
        s1.embargo();


        p1.putInHand(c);
        assertEquals(3, p1.calcVP());
        assertEquals(3, p2.calcVP());
        assertEquals(11, p1.getCardCount());
        assertEquals(10, p2.getCardCount());
        s1.embargo();
        c.play(p1);
        p2.putInHand(new Card(CURSE));
        assertEquals(3, p1.calcVP());
        assertEquals(2, p2.calcVP());
        assertEquals(10, p1.getCardCount());
        assertEquals(11, p2.getCardCount());
    }

    @Test
    public void testFeast(){
        Card c = new Card(FEAST);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        assertEquals(10, p1.getCardCount());
        p1.putInHand(c);
        c.play(p1);
        assertEquals(11, p1.getCardCount());
    }

    @Test
    public void testGardens(){
        Card c = new Card(GARDENS);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        assertEquals(3, p1.calcVP());
        p1.putInHand(c);
        assertEquals(4, p1.calcVP());
        for (int i = 0; i < 10; i++)
            p1.putInHand(new Card(COPPER));
        assertEquals(5, p1.calcVP());
        for (int i = 0; i < 10; i++)
            p1.putInHand(new Card(COPPER));
        p1.putInHand(new Card(PROVINCE));
        assertEquals(12, p1.calcVP());
    }

    @Test
    public void testGreat_Hall(){
        Card c = new Card(GREAT_HALL);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        assertEquals(1, p1.getActionCount());
        assertEquals(5, p1.cloneHand().size());
        assertEquals(5, p1.cloneDeck().size());
        p1.putInHand(c);
        c.play(p1);
        assertEquals(1, p1.getActionCount());
        assertEquals(6, p1.cloneHand().size());
        assertEquals(4, p1.cloneDeck().size());
    }

    @Test
    public void testMine(){
        Card c = new Card(MINE);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        assertEquals(10, p1.getCardCount());
        int tempSum = 0;
        for (Card d : p1.cloneHand())
            tempSum += d.treasureVal;
        p1.putInHand(c);
        c.play(p1);

        assertEquals(true, p1.cloneHand().size() == 5);
        int tempSum2= 0;
        for (Card d : p1.cloneHand())
            tempSum2 += d.treasureVal;

        assertEquals(true, tempSum2 >= tempSum);
    }

    @Test
    public void testVillage(){
        Card c = new Card(VILLAGE);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        p1.putInHand(c);
        assertEquals(6, p1.cloneHand().size());
        assertEquals(5, p1.cloneDeck().size());
        assertEquals(11, p1.getCardCount());
        assertEquals(1, p1.getActionCount());
        c.play(p1);
        assertEquals(6, p1.cloneHand().size());
        assertEquals(4, p1.cloneDeck().size());
        assertEquals(11, p1.getCardCount());
        assertEquals(2, p1.getActionCount());
    }

    @Test
    public void testSmithy(){
        Card c = new Card(SMITHY);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);
        p1.putInHand(c);
        assertEquals(6, p1.cloneHand().size());
        assertEquals(5, p1.cloneDeck().size());
        assertEquals(11, p1.getCardCount());
        c.play(p1);
        assertEquals(8, p1.cloneHand().size());
        assertEquals(2, p1.cloneDeck().size());
        assertEquals(11, p1.getCardCount());
    }

    @Test
    public void testSalvager(){
        Card c = new Card(SALVAGER);
        Player p1 = new Player("P1");
        GameState board = new GameState();
        board.addPlayer(p1);

        int temp = p1.getGoldCount();
        assertEquals(1, p1.getBuysCount());
        p1.putInHand(c);
        assertEquals(11, p1.getCardCount());
        c.play(p1);
        assertEquals(10, p1.getCardCount());
        assertEquals(true, p1.getGoldCount() >= temp);
    }

}