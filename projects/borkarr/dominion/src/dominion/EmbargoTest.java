package dominion;

import static org.junit.Assert.*;

import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

import dominion.Card.CardName;

public class EmbargoTest {

    //   public  static void main(String args[]) throws CloneNotSupportedException{
    @Test
    public void test0() throws Throwable {
        List < Card > cards;
        GameState state, testState;
        Randomness.reset(11);

        int newCoins = 2;
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
        
        state.players.get(0).hand.add(Card.getCard(state.cards, Card.CardName.Embargo));
        state.players.get(0).playKingdomCard();
        int embargoTrue = 0;
        for(HashMap.Entry<Card, Integer> entry : state.embargos.entrySet()){
        	int value = entry.getValue();
        	if(value == 1){
        		embargoTrue = 1;
        	}
        }
        assertEquals(1, embargoTrue);
        for(Card currentCard : state.cards){
			  state.embargos.put(currentCard, 1);
		  }
        state.players.get(1).buyCard();
        System.out.println("  *****    Player-1 Status ***** ");
        System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + (testState.players.get(0).hand.size()));
        System.out.println("coins count = " + state.players.get(0).coins + ", expected = " + (testState.players.get(0).coins + newCoins));
        System.out.println("  *****    Player-2 Status ***** ");
        System.out.println("gained curse = " + state.players.get(1).discard.contains(Card.getCard(state.cards, CardName.Curse)) + ", expected = true");
        System.out.println("score count = " + state.players.get(1).scoreFor() + ", expected < " + testState.players.get(1).scoreFor());
        assertEquals(state.players.get(0).hand.size(), testState.players.get(0).hand.size());
        assertEquals(state.players.get(0).coins, (testState.players.get(0).coins + 2));
        assertEquals(state.embargos.containsValue(1), true);
        assertTrue(state.players.get(1).scoreFor() < testState.players.get(1).scoreFor());
    }
}
    