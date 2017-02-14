package dominion;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Collections;


public class PlayDominion {

	   public  static void main(String args[]){
		   
		    List<Card> cards;
		    int [] piles;
		    GameState state;
			Randomness.reset(10);	   

			
 
			cards = new ArrayList<Card>(Card.createCards());
			piles= new int[17];
			state = new GameState(cards, piles);

			
		      Player player = new Player(state, "player-1");


		      state.addPlayer(player);
		      player = new Player(state, "player-2");
		      state.addPlayer(player);		      
		      //Initialize the game!
		      state.initializeGame();
		      
		      System.out.println("Initialization DominionBoard:\n " + state.toString());
		      
		      HashMap<Player, Integer> winners=state.play(state);
		      System.out.println ("Finished game.\n");
		    
		      for(Player p: winners.keySet()){
		    	  System.out.println ("Player name: "+winners.get(p) + " , Score: "+ winners.get(p) );
		      }
		      
		      
		      
			System.exit(0);  

	   }
	
	
}