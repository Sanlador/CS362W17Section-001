package cs362.dominion.Cards;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cs362.dominion.Exceptions.ConfiguationException;
import cs362.dominion.Exceptions.InvalidSelectionException;
import cs362.dominion.Resources.Game;
import cs362.dominion.Helpers.*;

public class EmbargoTest {

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
		//kingdomCards.add(Gardens.class);
		//kingdomCards.add(GreatHall.class);
		//kingdomCards.add(Mine.class);
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
	}
	
	@Test
	public void testDescription() {
		Cards.testDescription(game, Embargo.class);
	}

	
	/**
	 * Makes sure that when played, the Council room card adds a card to every other players deck.
	 * Also make sure that the player who played the card had four cards added to the hand
	 */
	@Test
	public void testPlayCard() {		
		// Create a new game and get the current player
		this.game.getCurrentPlayer().deck.hand.addCard(this.game.supply.drawCard(Embargo.class));
		
		try {
			this.game.playCard(Embargo.class);
		} catch (InvalidSelectionException e) {
			fail("Could not play card");
		}
		// Assert it went to trash
		assertFalse(this.game.getCurrentPlayer().deck.hand.handContainsCard(Embargo.class));
	}

	@Test
	public void assertCoins() {		
		// Create a new game and get the current player
		this.game.getCurrentPlayer().deck.hand.addCard(this.game.supply.drawCard(Embargo.class));
		int beforePlay = this.game.getCurrentPlayer().deck.hand.getCoins();
		try {
			this.game.playCard(Embargo.class);
		} catch (InvalidSelectionException e) {
			fail("Could not play card");
		}
		int afterPlay = this.game.getCurrentPlayer().deck.hand.getCoins();
		// Assert it went to trash
		assertTrue(afterPlay-beforePlay==2);
	}
	
}
