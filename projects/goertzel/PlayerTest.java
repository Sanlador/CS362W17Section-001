package goertzel;

import org.junit.Test;

import static goertzel.Card.CardName.*;
import static junit.framework.TestCase.assertEquals;

public class PlayerTest {
    @Test
    public void testPlayTurn() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        board.addPlayer(p1);
        board.playOneTurn();
        assertEquals(2, board.getTurnCount());
        assertEquals(5, p1.cloneHand().size());
    }

    @Test
    public void testDrawCards() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        assertEquals(0, p1.cloneDeck().size());
        assertEquals(0, p1.cloneHand().size());
        assertEquals(0, p1.cloneDiscardPile().size());
        board.addPlayer(p1);
        assertEquals(5, p1.cloneDeck().size());
        assertEquals(5, p1.cloneHand().size());
        assertEquals(0, p1.cloneDiscardPile().size());
        p1.drawCards(3);
        assertEquals(2, p1.cloneDeck().size());
        assertEquals(8, p1.cloneHand().size());
        assertEquals(0, p1.cloneDiscardPile().size());
        Card temp1 = new Card(PROVINCE);
        Card temp2 = new Card(GOLD);
        p1.putInHand(temp1);
        p1.putInHand(temp2);
        assertEquals(2, p1.cloneDeck().size());
        assertEquals(10, p1.cloneHand().size());
        assertEquals(0, p1.cloneDiscardPile().size());
        p1.discard(temp1);
        p1.discard(temp2);
        assertEquals(2, p1.cloneDeck().size());
        assertEquals(8, p1.cloneHand().size());
        assertEquals(2, p1.cloneDiscardPile().size());
        p1.drawCards(3);
        assertEquals(1, p1.cloneDeck().size());
        assertEquals(11, p1.cloneHand().size());
        assertEquals(0, p1.cloneDiscardPile().size());
        p1.drawCards(2);
        assertEquals(0, p1.cloneDeck().size());
        assertEquals(12, p1.cloneHand().size());
        assertEquals(0, p1.cloneDiscardPile().size());
    }

    @Test
    public void testGain() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        board.addPlayer(p1);
        Supply_Stack s1 = board.findSupplyStack(PROVINCE);
        p1.gain(PROVINCE, 99);
        assertEquals(11, p1.getCardCount());
        assertEquals(7, s1.cardsRemaining());
        p1.gain(PROVINCE, 0);
        assertEquals(11, p1.getCardCount());
        assertEquals(7, s1.cardsRemaining());
        for (int i = 0; i < 8; i++){
            p1.gain(PROVINCE, 99);
        }
        assertEquals(18, p1.getCardCount());
        assertEquals(0, s1.cardsRemaining());
    }

    @Test
    public void testPutInHand() {
        Player p1 = new Player("Player 1");
        assertEquals(0, p1.cloneHand().size());
        p1.putInHand(new Card(PROVINCE));
        p1.putInHand(new Card(Card.CardName.SILVER));
        assertEquals(2, p1.cloneHand().size());
        assertEquals(true, p1.cloneHand().get(0).name == PROVINCE);
        assertEquals(true, p1.cloneHand().get(1).name == Card.CardName.SILVER);
    }

    @Test
    public void testDiscard() {
        Player p1 = new Player("Player 1");
        assertEquals(0, p1.getCardCount());
        Card temp = new Card(PROVINCE);
        p1.putInHand(temp);
        p1.discard(temp);
        assertEquals(1, p1.getCardCount());
        assertEquals(0, p1.cloneHand().size());
        assertEquals(1, p1.cloneDiscardPile().size());
        assertEquals(true, p1.cloneDiscardPile().get(0).name == PROVINCE);
    }

    @Test
    public void testTrashCard() {
        Player p1 = new Player("Player 1");
        assertEquals(0, p1.getCardCount());
        Card temp = new Card(Card.CardName.VILLAGE);
        p1.putInHand(temp);
        assertEquals(true, p1.cloneHand().get(0).name == Card.CardName.VILLAGE);
        p1.trashCard(temp);
        assertEquals(0, p1.getCardCount());
    }


    @Test
    public void testRandCardFromHand() {
        Player p1 = new Player("Player 1");
        Card tempC1 = new Card(Card.CardName.VILLAGE);
        p1.putInHand(tempC1);
        Card tempC2 = new Card(Card.CardName.VILLAGE);
        p1.putInHand(tempC2);
        Card temp = p1.randCardFromHand();
        assertEquals(true, temp == tempC1 || temp == tempC2);
    }

    @Test
    public void testReturnCard() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        board.addPlayer(p1);
        Supply_Stack s1 = board.findSupplyStack(PROVINCE);
        p1.gain(PROVINCE, 99);
        assertEquals(11, p1.getCardCount());
        assertEquals(7, s1.cardsRemaining());
        assertEquals(true, p1.cloneDiscardPile().get(0).name == PROVINCE);
    }

    @Test
    public void testRevealCardFromDeck() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        board.addPlayer(p1);
        assertEquals(5, p1.cloneHand().size());
        assertEquals(5, p1.cloneDeck().size());
        Card temp = p1.revealCardFromDeck();
        assertEquals(5, p1.cloneHand().size());
        assertEquals(4, p1.cloneDeck().size());
        assertEquals(true, temp.name == Card.CardName.ESTATE || temp.name == Card.CardName.COPPER);
    }

    @Test
    public void testTrashTreasure() {
        Player p1 = new Player("Player 1");
        p1.putInHand(new Card(Card.CardName.GOLD));
        p1.putInHand(new Card(PROVINCE));
        p1.putInHand(new Card(Card.CardName.SILVER));
        p1.putInHand(new Card(Card.CardName.VILLAGE));
        assertEquals(4, p1.cloneHand().size());
        Card temp = p1.trashTreasure();
        assertEquals(3, p1.cloneHand().size());
        assertEquals(3, p1.getCardCount());
        assertEquals(true, temp.name == Card.CardName.GOLD || temp.name == Card.CardName.SILVER);
        temp = p1.trashTreasure();
        assertEquals(2, p1.cloneHand().size());
        assertEquals(2, p1.getCardCount());
        assertEquals(true, temp.name == Card.CardName.GOLD || temp.name == Card.CardName.SILVER);
        assertEquals(null, p1.trashTreasure());
    }


    @org.junit.Test
    public void testFindSupplyStack() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        board.addPlayer(p1);
        assertEquals(board.findSupplyStack(Card.CardName.GOLD), p1.findSupply(Card.CardName.GOLD));
        assertEquals(30, p1.findSupply(Card.CardName.GOLD).cardsRemaining());
        p1.gain(Card.CardName.GOLD, 99);
        assertEquals(29, p1.findSupply(Card.CardName.GOLD).cardsRemaining());
    }

    @Test
    public void testHandHasCard() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        board.addPlayer(p1);
        board.playOneTurn();
        assertEquals(Card.CardName.COPPER, p1.handHasCard(Card.CardName.COPPER).name);
    }

    @Test
    public void testIncActions() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        board.addPlayer(p1);
        assertEquals(1, p1.getActionCount());
        p1.incActions(3);
        assertEquals(4, p1.getActionCount());
    }

    @Test
    public void testIncGold() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        board.addPlayer(p1);
        assertEquals(0, p1.getGoldCount());
        p1.incGold(4);
        assertEquals(4, p1.getGoldCount());
    }

    @Test
    public void testIncBuys() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        board.addPlayer(p1);
        assertEquals(1, p1.getBuysCount());
        p1.incBuys(1);
        assertEquals(2, p1.getBuysCount());
    }

    @Test
    public void testDecActions() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        board.addPlayer(p1);
        assertEquals(1, p1.getActionCount());
        p1.decActions();
        assertEquals(0, p1.getActionCount());
    }

    @Test
    public void testCalcVP() {
        GameState board = new GameState();
        Player p1 = new Player("Player 1");
        assertEquals(0, p1.calcVP());
        board.addPlayer(p1);
        assertEquals(3, p1.calcVP());
        p1.putInHand(new Card(PROVINCE));
        assertEquals(9, p1.calcVP());
    }

    @Test
    public void getPlayerName() {
        Player p1 = new Player("Player 1");
        assertEquals("Player 1", p1.getPlayerName());
    }

    @Test
    public void testGetCardCount() {
        Player p1 = new Player("Player 1");
        assertEquals(0, p1.getCardCount());
        Card temp1 = new Card(Card.CardName.SMITHY);
        Card temp2 = new Card(Card.CardName.SMITHY);
        p1.putInHand(temp1);
        p1.putInHand(temp2);
        assertEquals(2, p1.getCardCount());
        p1.discard(temp1);
        assertEquals(2, p1.getCardCount());
        p1.trashCard(temp2);
        assertEquals(1, p1.getCardCount());
    }

    @Test
    public void testToString() {
        Player p1 = new Player("Player 1");
        assertEquals("Player 1", p1.toString());
    }

}