package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class CardBuyTest {

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
//			
		      Player player = new Player(state, "player-1");
		      //player.deck.add(Card.getCard(cards, Card.CardName.Estate));
		        
		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      testState=(GameState) state.clone();

		     //	play the game 
		      state.players.get(0).coins = 6;
		      state.players.get(0).buyCard();
		      assertEquals(state.players.get(0).discard.get(0), Card.getCard(cards, Card.CardName.Gold));
		      assertEquals(state.players.get(0).coins, 0);
		      
		      state.players.get(0).discard.remove(0);
		      state.players.get(0).numBuys++;
		      state.players.get(0).buyCard();
		      assertEquals(state.players.get(0).discard.get(0), Card.getCard(cards, Card.CardName.Copper));
		      assertEquals(state.players.get(0).coins, 0);
		      
		      System.out.println(state.players.get(0).toString());
		      state.players.get(0).printStateGame();

	   }
	
}
