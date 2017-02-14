import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zaengle on 2/12/2017.
 */
public class BuyTest {
    GameState buyState;
    List<Card> cards;
    Player p1;
    Player p2;

    @Before
    public void newMaterial(){
        cards = new ArrayList<Card>(Card.createCards());
        buyState = new GameState(cards);
        p1 = new Player(buyState, "Player 1");
        p2 = new Player(buyState, "Player 2");
        buyState.addPlayer(p1);
        buyState.addPlayer(p2);
        buyState.initializeGame();
    }

    @Test
    public void buyTestingWith8(){
        p1.initializePlayerTurn();
        assertEquals(p1.coins, 0);
        p1.coins = 8;
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 10);
        p1.buyCard();
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 11);
        p1.printStateGame();
    }

    @Test
    public void buyTestingWith7(){
        p1.initializePlayerTurn();
        assertEquals(p1.coins, 0);
        p1.coins = 7;
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 10);
        p1.buyCard();
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 11);
    }

    @Test
    public void buyTestingWith6(){
        p1.initializePlayerTurn();
        assertEquals(p1.coins, 0);
        p1.coins = 6;
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 10);
        p1.buyCard();
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 11);

    }

    @Test
    public void buyTestingWith5(){
        p1.initializePlayerTurn();
        assertEquals(p1.coins, 0);
        p1.coins = 5;
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 10);
        p1.buyCard();
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 11);
    }

    @Test
    public void buyTestingWith4(){
        p1.initializePlayerTurn();
        assertEquals(p1.coins, 0);
        p1.coins = 4;
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 10);
        p1.buyCard();
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 11);
        int a = 12;
        for(int i = 0; i < 30; i++){
            p1.coins = 4;
            p1.numBuys = 1;
            p1.buyCard();
            assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), a + i);
        }

    }

    @Test
    public void buyTestingWith3(){
        p1.initializePlayerTurn();
        assertEquals(p1.coins, 0);
        p1.coins = 3;
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 10);
        p1.buyCard();
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 11);
        int a = 12;
        for(int i = 0; i < 20; i++){
            p1.coins = 3;
            p1.numBuys = 1;
            p1.buyCard();
            assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), a + i);
        }
    }

    @Test
    public void buyTestingWith2(){
        p1.initializePlayerTurn();
        assertEquals(p1.coins, 0);
        p1.coins = 2;
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 10);
        p1.buyCard();
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 11);
        int a = 12;
        for(int i = 0; i < 10; i++){
            p1.coins = 2;
            p1.numBuys = 1;
            p1.buyCard();
            assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), a + i);
        }
    }
    //Skip 1 because there is no case for one, it just defaults to the buy with 0 coins case
    //because there are no cards worth 1 coin.
    @Test
    public void buyTestingWith0(){
        p1.initializePlayerTurn();
        assertEquals(p1.coins, 0);
        p1.coins = 0;
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 10);
        p1.buyCard();
        assertEquals(p1.deck.size() + p1.hand.size() + p1.discard.size(), 11);
    }

}
