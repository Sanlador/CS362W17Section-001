package cs362.dominion.Cards;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cs362.dominion.Exceptions.ConfiguationException;
import cs362.dominion.Resources.Game;
import cs362.dominion.Helpers.*;

public class GardensTest {

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
		kingdomCards.add(Gardens.class);
		//kingdomCards.add(GreatHall.class);
		//kingdomCards.add(Mine.class);
		/** 3 extra card types **/
		//kingdomCards.add(Smithy.class);
		kingdomCards.add(Bazaar.class);
		kingdomCards.add(Village.class);
		// Create a new game
		try {
			this.game = new Game(2, kingdomCards);
		} catch (ConfiguationException e) {
			fail("Could not initialize game");
		}
	}

	
	/**
	 * Makes sure the garden card, when ten other cards are present, adds a victory point to 
	 * the players hand
	 */
	@Test
	public void testVictoryPoints() {	
		
		game.getCurrentPlayer().deck.hand.addCard(game.supply.drawCard(Gardens.class));
		int pointsBefore = game.getCurrentPlayer().getVictoryPoints();
		for(int i=0; i<10; i++){
			game.getCurrentPlayer().deck.hand.addCard(game.supply.drawCard(Copper.class));
		}
		int pointsAfter = game.getCurrentPlayer().getVictoryPoints();
		assertTrue(pointsAfter-pointsBefore==1);
		
	}
	
	@Test
	public void testDescription() {
		Cards.testDescription(game, Gardens.class);
	}
	

}
