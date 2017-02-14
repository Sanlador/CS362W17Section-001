package cs362.dominion.Resources;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cs362.dominion.Cards.*;
import cs362.dominion.Exceptions.ConfiguationException;
import cs362.dominion.Exceptions.InvalidSelectionException;
import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Game.gamePhase;
import cs362.dominion.Resources.Player;

public class GameTest {

	private Game game;
	
	@Before
	public void setUp() throws Exception {
		ArrayList<Class<? extends Card>> kingdomCards = new ArrayList<Class<? extends Card>>();	
		kingdomCards.add(Adventurer.class);
		kingdomCards.add(Ambassador.class);
		kingdomCards.add(Baron.class);
		kingdomCards.add(CouncilRoom.class);
		kingdomCards.add(Cutpurse.class);
		kingdomCards.add(Embargo.class);
		kingdomCards.add(Feast.class);
		/** 3 extra card types **/
		kingdomCards.add(Smithy.class);
		kingdomCards.add(Bazaar.class);
		kingdomCards.add(Village.class);
		// Create a new game
		try {
			this.game = new Game(2, kingdomCards);
		} catch (ConfiguationException e) {
			fail("Could not initialize game");
		}
		// this simply makes sets it to start at the action phase
		for(int i=0; i<10; i++){
			if(game.getPhase()==gamePhase.ACTION){break;}
			if(i>8){fail("Could not reach action phase");}
			game.nextPhase();
		}
	}
	
	/**
	 * Tests that the transition to a new player occurs after a cycle of phases
	 */
	@Test
	public void testGetCurrentPlayer() {
		Player startingPlayer = game.getCurrentPlayer();
		// player should not have changed
		game.nextPhase();
		assertTrue(game.getCurrentPlayer()==startingPlayer);
		// player should not have changed 
		game.nextPhase();
		assertTrue(game.getCurrentPlayer()==startingPlayer);
		// player should have changed
		game.nextPhase();
		assertTrue(game.getCurrentPlayer()!=startingPlayer);
	}

	/**
	 * Tests the phases moves from action->buy->cleanup->action
	 */
	@Test
	public void testNextPhase() {
		// check that it moves to buy phase
		game.nextPhase();
		assertTrue(game.getPhase()==gamePhase.BUY);
		// check that it moves to cleanup 
		game.nextPhase();
		assertTrue(game.getPhase()==gamePhase.CLEANUP);
		// check that it moves to cleanup 
		game.nextPhase();
		assertTrue(game.getPhase()==gamePhase.ACTION);
	}

	
	/**
	 * The playable cards options is a little complex to test. I will be testing the
	 * boundary conditions and variations on the number of buys in a hand and a number
	 * of coins.
	 * Consider a truth table with the columns (buys,coin,playable_cards) 
	 * and assume buys and coins are unlimited.
	 * buys:coins:playable_cards
	 * 	T : T : all
	 * 	T : F : copper
	 * 	F : T : none
	 *	F : F : none
	 * This is the truth table I will be checking against for buys. 
	 */
	@Test
	public void testGetPlayableCardsBuyPhase() {
		// Give user one of each card and move to buy phase
		for(Class<? extends Card> cardType : game.supply.supplyCardTypes()){
			game.getCurrentPlayer().deck.hand.addCard(game.supply.drawCard(cardType));
		}
		// T : T : all 
		// Player has 1 buy and enough coins to buy any card. Buys should be every card in supply
		game.nextPhase();
		assertTrue(game.getPlayableCardTypes().size()==game.supply.supplyCardTypes().size());
		// Remove all treasure cards and make sure nothing can be bought
		// T : F : copper
		while(game.getCurrentPlayer().deck.hand.handContainsCard(Gold.class)){game.getCurrentPlayer().deck.hand.removeCardFromHand(Gold.class);}
		while(game.getCurrentPlayer().deck.hand.handContainsCard(Silver.class)){game.getCurrentPlayer().deck.hand.removeCardFromHand(Silver.class);}
		while(game.getCurrentPlayer().deck.hand.handContainsCard(Copper.class)){game.getCurrentPlayer().deck.hand.removeCardFromHand(Copper.class);}
		assertTrue(game.getPlayableCardTypes().size()==1);
		// Buy a copper so no buys are left
		// F : F : none
		try {
			game.playCard(Copper.class);
		} catch (InvalidSelectionException e) {
			fail("Could not play copper");
		}
		// Add coins. With no buys should not be able to play any cards
		game.getCurrentPlayer().deck.hand.addCard(game.supply.drawCard(Gold.class));
		assertTrue(game.getPlayableCardTypes().size()==0);	
	}
	
	

	/**
	 * Stack current players deck so the are in the lead. 
	 * Assert that current player is leader. 
	 */
	@Test
	public void testGetLeadingPlayer() {
		game.getCurrentPlayer().deck.hand.addCard(game.supply.drawCard(Province.class));
		assertTrue(game.getLeadingPlayer()==game.getCurrentPlayer());
	}

}
