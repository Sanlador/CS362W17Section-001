package org.cs362.dominion;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class testVillage {

	//   public  static void main(String args[]) throws CloneNotSupportedException{
	  @Test
	  public void test0()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state,testState;
			//Randomness.reset(10);	   

		    int newCards = 0;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Village));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      
		      //copy or clone the game state to a test case
		      testState=(GameState) state.clone();
  		     //	play the game 
		  //    HashMap<Player, Integer> winners=state.play();
		       player.discard(Card.getCard(player.hand, Card.CardName.Village));
		      
		      assertEquals(state.players.get(0).hand.size(),( testState.players.get(0).hand.size() + newCards ))      ;
		      assertEquals(state.players.get(0).deck.size(),( testState.players.get(0).deck.size() - newCards ))      ;
	      
		    

		      
		      

	   }
	
	
}
