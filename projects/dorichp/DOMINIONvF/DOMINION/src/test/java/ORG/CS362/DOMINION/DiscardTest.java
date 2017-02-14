package ORG.CS362.DOMINION;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DiscardTest {

	@Test
	public void test() {
	//	fail("Not yet implemented");
		
		List<Card> cards;
	    GameState state;
		
	    cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
	    
	    Player player = new Player(state, "player-1");
	    state.addPlayer(player);
	    player = new Player(state, "player-2");
	    state.addPlayer(player);
	    
	    state.initializeGame();
	    
	    Card Temp = player.hand.get(0);		//Pull a card
	    player.discard(Temp);				//Discard a card
	    
	   Card Temp2 = player.discard.get(0);
	   assertEquals(Temp, Temp2 );			//See if Discarded Card is equal to card in discard
	    
	    
	    
	    
	    
		
	}

}
