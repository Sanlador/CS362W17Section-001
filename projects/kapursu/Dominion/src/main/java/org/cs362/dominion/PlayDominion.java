package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Collections;


public class PlayDominion {

	   public  static void main(String args[]){
		   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

			
			//the cards  are achieved by each element/constant in the enum class 
			cards = new ArrayList<Card>(Card.createCards());
			state = new GameState(cards);
//			 System.out.println("Initialization DominionBoard:\n " + state.toString()); 
			
		      Player player = new Player(state, "player-1");
//		      player.printStateGame();
		         player.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
		         player.hand.add(Card.getCard(cards,Card.CardName.Smithy));
		         player.hand.add(Card.getCard(cards,Card.CardName.Mine));
		         player.hand.add(Card.getCard(cards,Card.CardName.CouncilRoom));
		         player.hand.add(Card.getCard(cards,Card.CardName.Embargo));
		         player.hand.add(Card.getCard(cards,Card.CardName.Feast));
		         player.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
		         player.hand.add(Card.getCard(cards,Card.CardName.Smithy));
		         player.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
		      state.addPlayer(player);
		       player = new Player(state, "player-2");
		         player.hand.add(Card.getCard(cards,Card.CardName.Feast));
		         player.hand.add(Card.getCard(cards,Card.CardName.Village));

		         player.hand.add(Card.getCard(cards,Card.CardName.Embargo));
		         player.hand.add(Card.getCard(cards,Card.CardName.CouncilRoom));
		         player.hand.add(Card.getCard(cards,Card.CardName.Ambassador));
		         player.hand.add(Card.getCard(cards,Card.CardName.Baron));
		         player.hand.add(Card.getCard(cards,Card.CardName.Mine));
		         player.hand.add(Card.getCard(cards,Card.CardName.Cutpurse));
		         player.hand.add(Card.getCard(cards,Card.CardName.Smithy));
		         player.hand.add(Card.getCard(cards,Card.CardName.Smithy));
		         player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		      state.addPlayer(player);		      
		      //Initialize the game!
		      state.initializeGame();
		      
		      System.out.println("Initialization DominionBoard:\n " + state.toString());
		      
		      HashMap<Player, Integer> winners=state.play();
		      System.out.println ("Finished game.\n");
		    
		      for(Player p: winners.keySet()){
		    	  System.out.println ("Player name: "+winners.get(p) + " , Score: "+ winners.get(p) );
		      }
		      
	//	      player.printStateGame();
		      
		      
			System.exit(0);  

	   }
	
	
}
