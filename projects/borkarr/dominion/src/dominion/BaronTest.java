package dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class BaronTest {

    //   public  static void main(String args[]) throws CloneNotSupportedException{
    @Test
    public void test0() throws Throwable {
        List < Card > cards;
        GameState state, testState;
        Randomness.reset(9);

        int newBuys = 1;
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
        
        state.players.get(0).hand.add(Card.getCard(state.cards, Card.CardName.Baron));
        state.players.get(0).playKingdomCard();
        
        System.out.println("  *****    Player-1 Status ***** ");
        System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = 4 or 5");
        System.out.println("deck count = " + state.players.get(0).deck.size() + ", expected = " + (testState.players.get(0).deck.size()));
        System.out.println("numBuys = " + state.players.get(0).numBuys + ", expected = " + (testState.players.get(0).numBuys + newBuys));
        assertEquals(state.players.get(0).deck.size(), testState.players.get(0).deck.size());
        System.out.println(state.players.get(0).scoreFor());
        if(state.players.get(0).scoreFor() == 3){
        	assertEquals(state.players.get(0).coins, testState.players.get(0).coins + 4);
        }
        else {
        	assertEquals(state.players.get(0).scoreFor(), testState.players.get(0).scoreFor() + 1);
        }
    }
}
    