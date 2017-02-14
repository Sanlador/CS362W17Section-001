package dominion;


import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class GardensTest {

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
        
        state.players.get(0).hand.add(Card.getCard(state.cards, Card.CardName.Gardens));
        
        System.out.println("  *****    Player-1 Status ***** ");
        System.out.println("score count = " + state.players.get(0).scoreFor() + ", expected = " + (testState.players.get(0).scoreFor() + 1));
    }
}
    