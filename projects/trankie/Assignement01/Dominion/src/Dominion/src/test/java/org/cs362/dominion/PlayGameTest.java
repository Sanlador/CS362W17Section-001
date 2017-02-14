package org.cs362.dominion;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayGameTest {
    private Player player1;
    private Player player2;
    private List<Card> cards;
    private DominionBoard board;

    @Before
    public void initializeTests(){
        board = new DominionBoard();
        board.initialize();
        cards = new ArrayList<Card>(Card.addAllCards());
        player1 = new Player("PLAYER 1", 1, 1, 0);
        player2 = new Player("PLAYER 2", 1, 1, 0);
    }
    @Test
    public void TestWin(){
        assertEquals(player1.deck.size(), 0);
        assertEquals(player2.deck.size(), 0);
        player1.buildDeck();
        player2.buildDeck();
        assertEquals(player1.deck.size(), 10);
        assertEquals(player2.deck.size(), 10);
        player1.deck.add(Card.getCard(cards, Card.CardName.Estate));
        assertEquals(player1.deck.size(), 11);
        assertTrue(player1.checkVictory() > player2.checkVictory());
    }
    @Test
    public void TestLoss(){
        assertEquals(player1.deck.size(), 0);
        assertEquals(player2.deck.size(), 0);
        player1.buildDeck();
        player2.buildDeck();
        assertEquals(player1.deck.size(), 10);
        assertEquals(player2.deck.size(), 10);
        player2.deck.add(Card.getCard(cards, Card.CardName.Estate));
        assertEquals(player2.deck.size(), 11);
        assertTrue(player1.checkVictory() < player2.checkVictory());
    }
    @Test
    public void TestDraw(){
        assertEquals(player1.deck.size(), 0);
        assertEquals(player2.deck.size(), 0);
        player1.buildDeck();
        player2.buildDeck();
        assertEquals(player1.deck.size(), 10);
        assertEquals(player2.deck.size(), 10);
        assertTrue(player1.checkVictory() == player2.checkVictory());
    }
    @Test
    public void TestEmptyCard(){
        assertEquals(board.num_Curse, 10);
        for(int i = 0; i < 10; i ++){
            board.buyingCard(4);
        }
        assertEquals(board.num_Curse, 0);
    }
    @Test
    public void Test3EmptyCard(){
        assertEquals(board.num_Province, 8);
        assertEquals(board.num_Duchy, 8);
        assertEquals(board.num_Estate, 8);
        for(int i = 0; i < 8; i ++){
            board.buyingCard(1);
            board.buyingCard(2);
            board.buyingCard(3);
        }
        assertEquals(board.checkEnd(), 1);
    }
    @Test
    public void TestBuy(){
        assertEquals(player1.getCoins(), 0);
        player1.setCoins(8);
        assertEquals(player1.getCoins(), 8);
        assertEquals(board.num_Province, 8);
        board.buyingCard(1);
        player1.setCoins((-1)*Card.getCard(cards, Card.CardName.Province).getCost());
        assertEquals(board.num_Province, 7);
        assertEquals(player1.getCoins(), 0);
    }
    @Test
    public void TestEmptyDeckDiscard() {
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        player1.drawCard();
    }
    @Test
    public void TestCheckAction(){
        assertEquals(player1.hand.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Village));
        assertEquals(player1.hand.size(), 1);
        assertEquals(player1.checkTypeAction(player1.hand.size()), 1);
    }
}