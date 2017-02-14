package ORG.CS362.DOMINION;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DrawTest {

	@Test
	public void test() {
	//	fail("Not yet implemented");
		
		List<Card> cards;
	    GameState state;
		
	    cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
	    
	    Player player = new Player(state, "player-1");
	    
	    player.hand.add(Card.getCard(cards,Card.CardName.Adventurer));		
        player.hand.add(Card.getCard(cards,Card.CardName.Smithy));	
        state.players.add(player);
        
        player = new Player(state, "player-1");
        player.hand.add(Card.getCard(cards,Card.CardName.Smithy));			
        player.hand.add(Card.getCard(cards,Card.CardName.Village));	
        state.players.add(player);
        
        
        
     //   Card Temp = player.drawCard();
        
	    state.initializeGame();
	    
	//    player.hand.contains(Temp);
	    //CHECK TO SEE IF CARD IS IN HAND
	    
	}

}
