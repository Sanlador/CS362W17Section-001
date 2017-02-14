package ORG.CS362.DOMINION;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BuycardTest{

	@Test
	public void test() {
//		fail("Not yet implemented");
	
		List<Card> cards;
	    GameState state;
		
	    cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
	    
	    Player player = new Player(state, "player-1");
	    state.addPlayer(player);
	    player = new Player(state, "player-2");
	    state.addPlayer(player);
	    
	    state.initializeGame();
	    player.numBuys = 10;	//give player a number of buys
	    player.numActions = 10;	//give player actiosn
	    player.coins = 100;	//Make the player afford any card they buy
	    player.buyCard();
	    
	    //After buying card, the item should be added to the end of the list:
	    //So remove it and see if it is something other than a copper
	    
	   Card Temp = player.deck.getLast();
	   Card Temp2 = player.deck.getFirst();
	//   assertFalse(Temp.getCardName() == Temp2.getCardName() );
	   //Test will fail if the last card is Copper, instead of another choice
//	   System.out.println(100 - Temp.getCost());
	//   System.out.println("  " +player.coins);
	   assertTrue(player.coins == (100 - Temp.getCost()));		//Makes sure coins were spent
	}

}
