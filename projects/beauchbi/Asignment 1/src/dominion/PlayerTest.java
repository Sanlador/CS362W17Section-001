package dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class PlayerTest {

	  public void test0()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state,testState;
			Randomness.reset(10);	   

//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Cutpurse));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      
		      //Initialize the game!
		      state.initializeGame();

		      testState=(GameState) state.clone();
		      
		     //	play the game 
		      HashMap<Player, Integer> winners=state.play();

		   
		      assertEquals(state.players.get(0).hand.size(),( testState.players.get(0).hand.size()))      ;
		      assertEquals(state.players.get(0).deck.size(),( testState.players.get(0).deck.size()))      ;
	  }

