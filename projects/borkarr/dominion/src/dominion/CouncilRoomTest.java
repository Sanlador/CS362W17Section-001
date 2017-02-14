package dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CouncilRoomTest {

    //   public  static void main(String args[]) throws CloneNotSupportedException{
    @Test
    public void test0() throws Throwable {
        List < Card > cards;
        GameState state, testState;
        Randomness.reset(10);

        int drawCards = 4;

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
        
        state.players.get(0).hand.add(Card.getCard(state.cards, Card.CardName.Council_Room));
        System.out.println("bagool " + state.players.get(0).hand.size());
        state.players.get(0).playKingdomCard();
        
        System.out.println("  *****    Player-1 Status ***** ");
        System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + (testState.players.get(0).hand.size() + drawCards));
        System.out.println("deck count = " + state.players.get(0).deck.size() + ", expected = " + (testState.players.get(0).deck.size() - drawCards));
        System.out.println("buys count = " + state.players.get(0).numBuys + ", expected = " + (testState.players.get(0).numBuys + 1));
        System.out.println("  *****    Player-2 Status ***** ");
        System.out.println("hand count = " + state.players.get(1).hand.size() + ", expected = " + (testState.players.get(0).hand.size() + 1));
        System.out.println("deck count = " + state.players.get(1).deck.size() + ", expected = " + (testState.players.get(0).deck.size() - 1));
        assertEquals(state.players.get(0).hand.size(), (testState.players.get(0).hand.size() + drawCards));
        assertEquals(state.players.get(0).deck.size(), (testState.players.get(0).deck.size() - drawCards));
        assertEquals(state.players.get(0).numBuys, (testState.players.get(0).numBuys + 1));
        assertEquals(state.players.get(1).hand.size(), (testState.players.get(1).hand.size() + 1));
        assertEquals(state.players.get(1).deck.size(), (testState.players.get(1).deck.size() - 1));
    }
}
    