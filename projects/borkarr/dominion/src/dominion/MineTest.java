package dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class MineTest {

    //   public  static void main(String args[]) throws CloneNotSupportedException{
    @Test
    public void test0() throws Throwable {
        List < Card > cards;
        GameState state, testState;
        Randomness.reset(10);
        
        //		 // initialize a game state and player cards
        cards = new ArrayList < Card > (Card.createCards());
        state = new GameState(cards);
        //			
        Player player = new Player(state, "player-1");
        player.numActions = 1;
        state.addPlayer(player);
        Player player2 = new Player(state, "player-2");
        state.addPlayer(player2);
        
        state.initializeGame();
        
        testState = (GameState) state.clone();
        
        state.players.get(0).hand.add(Card.getCard(state.cards, Card.CardName.Mine));
        state.players.get(0).playKingdomCard();
        
        System.out.println("  *****    Player-1 Status ***** ");
        System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + (testState.players.get(0).hand.size()));
        System.out.println("discard count = " + state.players.get(0).discard.size() + ", expected = " + (testState.players.get(0).discard.size() + 1));
        assertEquals(state.players.get(0).hand.size(), testState.players.get(0).hand.size());
        assertEquals(state.players.get(0).discard.size(), 1);
        assertTrue(state.players.get(0).hand.contains(Card.getCard(state.cards, Card.CardName.Silver)));
        
        for(int i = 0; i < 5; i++){
        	state.players.get(0).discard(state.players.get(0).hand.get(0));
        }
        System.out.println("handssss count = " + state.players.get(0).hand.size() + ", expected = " + (testState.players.get(0).hand.size()));

        state.players.get(0).hand.add(Card.getCard(state.cards, Card.CardName.Mine));
        state.players.get(0).hand.add(Card.getCard(state.cards, Card.CardName.Silver));
        state.players.get(0).numActions = 1;
        System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + (testState.players.get(0).hand.size()));
        state.players.get(0).playKingdomCard();
        assertTrue(state.players.get(0).hand.contains(Card.getCard(state.cards, Card.CardName.Gold)));
    }
}
    