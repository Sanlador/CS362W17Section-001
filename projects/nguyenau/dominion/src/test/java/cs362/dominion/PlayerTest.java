package cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {
    
    public final List<Card> cards = new ArrayList<Card>(Card.createCards());

    @Test
    public void drawTest() throws Throwable {
        GameState state = new GameState(cards);
        Randomness.reset(10);
        
        Player player = new Player(state, "TestPlayer");
        state.addPlayer(player);
        player = new Player(state, "TestPlayer2");
        state.addPlayer(player);
        
        state.initializeGame();
        
        for(int i=0;i<state.players.size();i++) {
            assertEquals(state.players.get(i).hand.size(),5);
        }
        // This test is subsumed by Card Tests, however initializing with a playerhand of 5
        // means that this test was passed.
    }

    @Test
    public void discardTest() throws Throwable {
        GameState state = new GameState(cards);
        Randomness.reset(10);
        
        Player player = new Player(state, "TestPlayer");
        state.addPlayer(player);
        player = new Player(state, "TestPlayer2");
        state.addPlayer(player);
        
        state.initializeGame();
        
        System.out.println(state.players.get(0).toString());
        
        Card c = Card.getCard(cards,Card.CardName.Cooper);
        List<Card> coppersHand = Card.filter(state.players.get(0).hand,c.getType());
        int expected = (state.players.get(0).hand.size()) - (coppersHand.size());
        System.out.println(coppersHand.size());
        for (int i=0;i<coppersHand.size()+1;i++) {
            state.players.get(0).discard(c);
        }
        // should catch discarding more than there are
        assertEquals(state.players.get(0).hand.size(),expected);
    }
    
    @Test
    public void treasureCardTest() throws Throwable {
        GameState state = new GameState(cards);
        Randomness.reset(10);
        
        Player player = new Player(state, "TestPlayer");
        state.addPlayer(player);
        player = new Player(state, "TestPlayer2");
        state.addPlayer(player);
        
        state.initializeGame();
        
        System.out.println(state.players.get(0).toString());
        
        Card c = Card.getCard(cards,Card.CardName.Cooper);
        List<Card> coppersPlay = Card.filter(state.players.get(0).hand,c.getType());
        
        state.players.get(0).playTreasureCard();
        
        List<Card> coppersHand = Card.filter(state.players.get(0).hand,c.getType());
        
        assertEquals(coppersHand.size(),0);
        assertEquals(state.players.get(0).playedCards.size(),coppersPlay.size());
    }
    
    @Test
    public void buyCardTest() throws Throwable {
        GameState state = new GameState(cards);
        Randomness.reset(10);
        
        Player player = new Player(state, "TestPlayer");
        state.addPlayer(player);
        player = new Player(state, "TestPlayer2");
        state.addPlayer(player);
        
        state.initializeGame();
        
        System.out.println(state.players.get(0).toString());
        
        state.players.get(0).buyCard();
        assertEquals(state.players.get(0).discard.size(),1);
    }
    
    @Test
    public void endPhaseTest() throws Throwable {
        GameState state = new GameState(cards);
        Randomness.reset(10);
        
        Player player = new Player(state, "TestPlayer");
        state.addPlayer(player);
        player = new Player(state, "TestPlayer2");
        state.addPlayer(player);
        
        state.initializeGame();
        
        System.out.println(state.players.get(0).toString());
        
        state.players.get(0).playKingdomCard();
        state.players.get(0).buyCard();
        
        int expectedDiscard = state.players.get(0).playedCards.size() + state.players.get(0).discard.size() + state.players.get(0).hand.size();
        System.out.println(state.players.get(0).toString());
        state.players.get(0).endTurn();
        System.out.println(state.players.get(0).toString());
        assertEquals(state.players.get(0).playedCards.size(),0);
        assertEquals(state.players.get(0).hand.size(),5);
        assertEquals(state.players.get(0).discard.size(),expectedDiscard);
    }
}
