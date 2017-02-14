package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class GameStateTest {

	//   public  static void main(String args[]) throws CloneNotSupportedException{
	  @Test
	  public void test4()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state,testState;
		    
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
			 System.out.println(state.toString());
		      Player player = new Player(state, "player-1");
		      System.out.println(player.toString());
		      System.out.println(state.toString());
		        
		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      System.out.println(state.toString());
		      testState=(GameState) state.clone();

		     //	play the game 
		      assertEquals((int)state.gameBoard.get(Card.getCard(cards, Card.CardName.Estate)),8 );
		      System.out.println(state.toString());
		      

	   }
	
}
