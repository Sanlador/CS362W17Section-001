package dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class VillageTest {

    //   public  static void main(String args[]) throws CloneNotSupportedException{
    @Test
    public void test0() throws Throwable {
        List < Card > cards;
        GameState state, testState;
        Randomness.reset(10);

        int newCards = 1;
        int newActions = 2;
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
        
        state.players.get(0).hand.add(Card.getCard(state.cards, Card.CardName.Village));
        state.players.get(0).playKingdomCard();
        
        System.out.println("  *****    Player-1 Status ***** ");
        System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + (testState.players.get(0).hand.size() + newCards));
        System.out.println("deck count = " + state.players.get(0).deck.size() + ", expected = " + (testState.players.get(0).deck.size() - newCards));
        System.out.println("numActions = " + state.players.get(0).numActions + ", expected = " + (testState.players.get(0).numActions - 1 + newActions));

        assertEquals(state.players.get(0).hand.size(), (testState.players.get(0).hand.size() + newCards));
        assertEquals(state.players.get(0).deck.size(), (testState.players.get(0).deck.size() - newCards));
    }
}
    