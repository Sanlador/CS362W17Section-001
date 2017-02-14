package ORG.CS362.DOMINION;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ShuffleTest {

	@Test
	public void test() {
	
		List<Card> cards;
	    GameState state;
		
	    cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
	    
	    Player player = new Player(state, "player-1");
	    state.addPlayer(player);
	    player = new Player(state, "player-2");
	    state.addPlayer(player);
	    
	    state.initializeGame();
	    
	    System.out.println("Discard Size = " +player.discard.size());
	    Card Temp = player.deck.poll();	//first card
	    player.deck.add(Temp);			//put card back
	    
	    
	    System.out.println("Shuffling");
	    
	    while (player.discard.size() > 0) {
			int ndx = (int) Randomness.nextRandomInt(player.discard.size());
			// Move discard to deck
			player.deck.add(player.discard.remove(ndx));
	}
	    
	    
	//    System.out.println(Temp.getCardName() + " " +Temp2.getCardName());
	    //assertFalse(Temp, Temp2);
	    
}
	
}
