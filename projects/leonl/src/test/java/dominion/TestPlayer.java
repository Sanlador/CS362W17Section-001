package dominion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class TestPlayer {
    private GameState state;
    private Player player1;
    private Player player2;
    private List<Card> cards;

    @Before
    public void initializeGame() {
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);
        player1 = new Player(state, "PLAYER1");
        state.addPlayer(player1);
        player2 = new Player(state, "PLAYERR2");
        state.addPlayer(player2);
        state.initializeGame();
    }

    @Test
    public void testDrawCard() {
        player1.initializePlayerTurn();
        System.out.println(player1);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        System.out.println(player1);
    }

    @Test
    public void testInitializePlayerTurn() {
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        assertEquals(player1.numActions, 1);
        assertEquals(player1.coins, 0);
        assertEquals(player1.numBuys, 1);
        System.out.println(player1);
        player1.initializePlayerTurn();
        assertEquals(player1.hand.size(), 10);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        assertEquals(player1.numActions, 1);
        assertEquals(player1.coins, 0);
        assertEquals(player1.numBuys, 1);
        System.out.println(player1);
    }

    @Test
    public void testGain() {
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.playedCards.size(), 0);
        System.out.println(player1);
        player1.gain(Card.getCard(cards, Card.CardName.Province));
        assertEquals(player1.hand.size(), 5);
        System.out.println(player1);
    }

    @Test
    public void testDiscard() {
        player1.initializePlayerTurn();
        assertEquals(player1.hand.size(), 10);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        System.out.println(player1);
        player1.discard(Card.getCard(player1.hand, Card.CardName.Cooper));
        assertEquals(player1.hand.size(), 9);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 1);
        assertEquals(player1.playedCards.size(), 0);
        System.out.println(player1);
    }

    @Test
    public void testPlayKingdomCard() {
        player1.initializePlayerTurn();
        assertEquals(player1.hand.size(), 10);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        System.out.println(player1);
        player1.hand.add(Card.getCard(cards, Card.CardName.Smithy));
      //  player1.playKingdomCard();
        assertEquals(player1.hand.size(), 11);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        System.out.println(player1);
    }

    @Test
    public void testScoreFor() {
        int score = 9;
        for(Card c : player1.discard){
            score += c.score;
        }
        assertEquals(score, player1.scoreFor());
    }

    @Test
    public void testPlayTreasureCard() {
        int money = 0;
        player1.initializePlayerTurn();
        for(Card c : player1.hand){
            money += c.getTreasureValue();
        }
        player1.playTreasureCard();
        assertEquals(money, player1.coins);
    }

    @Test
    public void testBuyCard() {
        player1.initializePlayerTurn();
        System.out.println(player1);
        player1.playTreasureCard();
        player1.buyCard(state);
        assertTrue(player1.hand.size() < 5);
        System.out.println(player1);
    }

    @Test
    public void testEndTurn() {
        player1.initializePlayerTurn();
        assertEquals(player1.hand.size(), 10);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        System.out.println(player1);
        player1.endTurn();
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.playedCards.size(), 0);
        System.out.println(player1);
    }
}
