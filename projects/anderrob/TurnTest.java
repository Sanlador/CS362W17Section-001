package dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class TurnTest {

	@Test
	public void test() {
		List<Card> cards;
	    GameState state;
		Randomness.reset(10);	   

		
		//the cards  are achieved by each element/constant in the enum class 
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
//		 System.out.println("Initialization DominionBoard:\n " + state.toString()); 
		
	      //Player player = new Player(state, "player-1");
	      Player player = new Player(null,"anderrob");

	        // player.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
	        // player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

	      state.addPlayer(player);
	       player = new Player(state, "player2");
	       
	      state.addPlayer(player);		      
	      //Initialize the game!
	      state.initializeGame();
	      
	      
	      HashMap<Player, Integer> winners=state.play();
	      
	      for(Player p : winners.keySet()){
	    	  //if (p.getScore() == winners.get(p))
	    	  
	    	  System.out.println ("Player: "+ p.player_username + " , Score: "+ winners.get(p) );
	    	  System.out.println ("Pass" );
	    	  //if winner.getScore = winner.get(p)
	      }	
	}

}
