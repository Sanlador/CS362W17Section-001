package dominion;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;




public class PlayDominion {

	   public  static void main(String args[]){
		   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

			
			//the cards  are achieved by each element/constant in the enum class 
			cards = new ArrayList<Card>(Card.createCards());
			state = new GameState(cards);
//			 System.out.println("Initialization DominionBoard:\n " + state.toString()); 
			
		      //Player player = new Player(state, "player-1");
		      Player player = new Player(null,"anderrob");

		        // player.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
		        // player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		      state.addPlayer(player);
		       player = new Player(state, "player2");
		       
		      state.addPlayer(player);		      
		      //Initialize the game!
		      state.initializeGame();
		      
		      System.out.println("Initialization DominionBoard:\n " + state.toString());
		      
		      HashMap<Player, Integer> winners=state.play();
		      System.out.println ("Finished game.\n");
		    
		      for(Player p : winners.keySet()){
		    	  System.out.println ("Player: "+ p.player_username + " , Score: "+ winners.get(p) );
		      }	      
		      
		      
			System.exit(0);  

	   }
	
	
}
