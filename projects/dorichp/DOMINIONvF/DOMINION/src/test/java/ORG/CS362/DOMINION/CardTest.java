package ORG.CS362.DOMINION;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CardTest {

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
	    
	    
	    player.hand.add(Card.getCard(cards,Card.CardName.Baron));		//add the baron card	
	    
	    List<Card> actionCards = Card.filter(player.hand, Card.Type.ACTION);	//filter out the actions
	    Card c = (Card)actionCards.get(0);										//get baron, the only one
	    System.out.println(c.getCardName());							
	    
	    int precoins = player.coins;				//Adds 4 coins and adds 1 to number of buys
	    int	prebuys = player.numBuys;			//Record the number of coisn and buys before
	    
	    c.play(player, state);
	    
	    int postcoins = player.coins;
	    int postbuys = player.numBuys;
	    
	    assertTrue(precoins + 4 == postcoins);			//Make sure 4 coins were added
	    assertTrue(prebuys + 1 == postbuys);			//make sure buys is increased by 1

	//    System.out.println(precoins + " " +postcoins);
	  //  System.out.println(prebuys + " "+ postbuys);
	    
	    
	}

}
