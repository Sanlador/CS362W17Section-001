package dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class AdventurerTest {

    //   public  static void main(String args[]) throws CloneNotSupportedException{
    @Test
    public void test0() throws Throwable {
        List < Card > cards;
        GameState state, testState;
        Randomness.reset(10);

        int handCards = 2;
        int newActions = 0;
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
        for(int i = 0; i < 5; i++){
        	state.players.get(0).discard(state.players.get(0).hand.get(0));
        }
        
        testState = (GameState) state.clone();
        
        state.players.get(0).hand.add(Card.getCard(state.cards, Card.CardName.Adventurer));
        state.players.get(0).playKingdomCard();
        
        System.out.println("  *****    Player-1 Status ***** ");
        System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + (testState.players.get(0).hand.size() + handCards));
        System.out.println("deck count = " + state.players.get(0).deck.size() + ", expected = " + (testState.players.get(0).deck.size() - handCards));
        System.out.println("numActions = " + state.players.get(0).numActions + ", expected = " + (testState.players.get(0).numActions - 1 + newActions));
        assertEquals(state.players.get(0).hand.size(), (testState.players.get(0).hand.size() + handCards));
        assertEquals(state.players.get(0).deck.size(), (testState.players.get(0).deck.size() - handCards));
    }
}
    