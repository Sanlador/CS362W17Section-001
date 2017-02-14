package org.cs362.dominion;
/*Collaborated with David Baugh*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class PlayDominion {

	   public  static void main(String args[]){

	   	    //members
		    List<Card> cards;
		    GameState state;
		    //set seed
			Randomness.reset(10);	   
		   //initialize
			cards = new ArrayList<Card>(Card.createCards());
			state = new GameState(cards);
			//print initial state of gameboard
			 System.out.println("Initial DominionBoard:\n " + state.toString());
//		     System.out.println("Adding players:\n ");
		      Player player1 = new Player(state,"Player1");
		      state.addPlayer(player1);
		      //player.printStateGame();
		    Player player2 = new Player(state,"Player2");
		   //player2.printStateGame();
		    state.addPlayer(player2);
		  // System.out.println("Players added:\n ");

		   //  player.hand.add(Card.getCard(cards,Card.CardName.adventurer));
		        // player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		        // player.hand.add(Card.getCard(cards,Card.CardName.Smithy));
		         //player.hand.add(Card.getCard(cards,Card.CardName.Village));
		      state.initializeGame();
		      //output state of gameboard +players
		   System.out.println("DominionBoard with players and cards:\n " + state.toString());
		   //System.out.println("Player 1 stats:\n" + player1.toString());
		  // System.out.println("Player 2 stats:\n" + player2.toString());

		   HashMap<Player, Integer> winners = state.play();
		   System.out.println ("Finished game.\n");

		       //Print winners
		      for(Player p: winners.keySet()){
		    	  System.out.println ("Player name: " +p.player_username+", Score: " + winners.get(p) );
		      }
		   System.out.println("Ending board state:\n " + state.toString());
	//	      //player.printStateGame();
			System.exit(0);  
	   }		
}

