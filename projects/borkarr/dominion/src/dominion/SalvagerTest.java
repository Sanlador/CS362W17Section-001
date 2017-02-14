package dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import dominion.Card.CardName;

public class SalvagerTest {

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
        
        state.players.get(0).hand.add(Card.getCard(state.cards, Card.CardName.Salvager));
        state.players.get(0).playKingdomCard();
        List < Card > testHand = new ArrayList < Card > (testState.players.get(0).hand);
        int handSize = state.players.get(0).hand.size();
        for(int i = 0; i < handSize; i++){
        	testHand.remove(state.players.get(0).hand.get(i));
        }

        System.out.println(state.players.get(0).hand);
        System.out.println(testState.players.get(0).hand);
        System.out.println("  *****    Player-1 Status ***** ");
        System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + (testState.players.get(0).hand.size() - 1));
        System.out.println("discard count = " + state.players.get(0).discard.size() + ", expected = " + (testState.players.get(0).discard.size() + 1));
        assertEquals(state.players.get(0).hand.size(), testState.players.get(0).hand.size() - 1);
        assertEquals(state.players.get(0).discard.size(), (testState.players.get(0).discard.size() + 1));
        if(testHand.get(0).getCardName() != Card.CardName.Cooper){
        	assertTrue(state.players.get(0).coins > 0);
        }
        else {
        	assertTrue(state.players.get(0).coins == 0);
        }
        //assertEquals(state.players.get(0).coins, (testState.players.get(0).coins + newCoins));
    }
}