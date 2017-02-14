package com.omalleya.app;
import java.util.ArrayList; import java.util.HashMap; import java.util.List;
import org.junit.Test; import static org.junit.Assert.*;

public class CutpurseTest {

    //   public  static void main(String args[]) throws CloneNotSupportedException{
    @Test   
    public void test0()  throws Throwable  {   
        List<Card> cards;     
        GameState state,testState; 
        Randomness.reset(10);   
        // initialize a game state and player cards
        cards = new ArrayList<Card>(Card.createCards());  
        state = new GameState(cards);
        Player player = new Player(state, "player-1");          
        player.hand.add(Card.getCard(cards,Card.CardName.Cutpurse));
        state.addPlayer(player);        
        player = new Player(state, "player-2");          
        state.addPlayer(player);                
        //Initialize the game!       
        state.initializeGame();
        state.players.get(0).initializePlayerTurn();           
        System.out.println("hand count = " + state.players.get(0).hand.size());
        System.out.println("deck count = " + state.players.get(0).deck.size());
        System.out.println("coins = " + state.players.get(0).coins);
        System.out.println("numActions = " + state.players.get(0).numActions);
        System.out.println("coins = " + state.players.get(0).coins);
        //copy or clone the game state to a test case
        testState=(GameState) state.clone();
        // play the game
        state.players.get(0).action();      
        System.out.println("  *****    Player-1 Status ***** " );       
        System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = "+ ( testState.players.get(0).hand.size() - 1 ) );       
        System.out.println("deck count = " + state.players.get(0).deck.size() + ", expected = "+ ( testState.players.get(0).deck.size() ) );       
        System.out.println("numActions = " + state.players.get(0).numActions + ", expected = "+ ( testState.players.get(0).numActions - 1 ) );
        System.out.println("coins = " + state.players.get(0).coins + ", expected = "+ ( testState.players.get(0).coins + 2 ) );
        System.out.println("  *****    Player-2 Status *****" );       
        System.out.println("hand count = " + state.players.get(1).hand.size() + ", expected = "+ ( testState.players.get(1).hand.size() - 1  ) );
        System.out.println("deck count = " + state.players.get(1).deck.size() + ", expected = "+ ( testState.players.get(1).deck.size() ) );       
        System.out.println("numActions = " + state.players.get(1).numActions + ", expected = "+ ( testState.players.get(1).numActions  ) );
        assertEquals(state.players.get(0).hand.size(), ( testState.players.get(0).hand.size() - 1 ));       
        assertEquals(state.players.get(0).deck.size(), ( testState.players.get(0).deck.size() ));
        assertEquals(state.players.get(0).coins, ( testState.players.get(0).coins + 2 ));
        assertEquals(state.players.get(1).hand.size(), ( testState.players.get(1).hand.size() - 1 ));
        assertEquals(state.players.get(1).deck.size(), ( testState.players.get(1).deck.size() ));
            
    }
}