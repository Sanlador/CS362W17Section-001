package org.cs362.dominion;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class OtherTest {

	
	//Tests the buy function 
	@Test
	  public void testBuy()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
//		         player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      state.players.get(0).coins+= 3;
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand coins = " + state.players.get(0).discard.size());
		      int d = state.players.get(0).discard.size();
		      int d2 = d + 1;
		      state.players.get(0).buyCard();
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand coins = " + state.players.get(0).discard.size() + ", expected = " + 1);
		      assertEquals(d2, state.players.get(0).discard.size());
		      

	   }
	
	
	@Test
	  public void testGameOver()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
//		         player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      state.gameBoard.put(Card.getCard(cards, Card.CardName.Province), 0);
		      System.out.println("Game should be over on check: ");
		      assertTrue(state.isGameOver());
		      

	   }
	
	
	@Test
	  public void testTreasurePlay()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
//		         player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Silver));
		      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Gold));
		      int value = 0;
		      for(Card c: state.players.get(0).hand){
		    	  value+=c.getTreasureValue();
		      }
		      
		      state.players.get(0).playTtreasureCard();
		      
		      System.out.println("Value in hand = " + value + "Treasure value played = " + state.players.get(0).coins);

		      assertEquals(value, state.players.get(0).coins);

	   }
	
	
}
