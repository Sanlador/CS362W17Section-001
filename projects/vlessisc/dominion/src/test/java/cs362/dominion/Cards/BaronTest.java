package cs362.dominion.Cards;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import cs362.dominion.Exceptions.ConfiguationException;
import cs362.dominion.Exceptions.InvalidOptionException;
import cs362.dominion.Exceptions.InvalidSelectionException;
import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Option;
import cs362.dominion.Resources.Player;
import cs362.dominion.Helpers.*;

public class BaronTest {
	
	private Class<? extends Card> cardType = Baron.class;
	
	private Game newGame(){
		ArrayList<Class<? extends Card>> kingdomCards = new ArrayList<Class<? extends Card>>();	
		kingdomCards.add(Adventurer.class);
		kingdomCards.add(Ambassador.class);
		kingdomCards.add(Baron.class);
		kingdomCards.add(CouncilRoom.class);
		kingdomCards.add(Cutpurse.class);
		kingdomCards.add(Embargo.class);
		kingdomCards.add(Feast.class);
		kingdomCards.add(Smithy.class);
		kingdomCards.add(Bazaar.class);
		kingdomCards.add(Village.class);
		// Create a new game
		
		try {
			return new Game(2, kingdomCards);
		} catch (ConfiguationException e) {
			fail("Could not initialize game");
		}
		return null;
	}
	
	/**
	 * Makes sure the baron adds exactly one buy to the hand 
	 */
	@Test
	public void testBuys() {
		Game game = newGame();
		int expectedBuys=1;
		Cards.testBuys(game, cardType, expectedBuys);
	}
	
	/**
	 * Sets up hand with ONLY 1 Baron and 1 estate.
	 * Chooses to remove estate after playing baron.
	 * Should be zero cards left
	 */
	@Test
	public void testPlayCard() {
		
		Game game = newGame();
		Player player = game.getCurrentPlayer();
		// Add this card the players hand to ensure they can play it
		player.deck.hand.removeAllCards();
		player.deck.hand.addCard(game.supply.drawCard(cardType));
		player.deck.hand.addCard(game.supply.drawCard(Estate.class));
		try {
			Option cardOptions = game.playCard(cardType);
			cardOptions.decide(0);
		} catch (InvalidSelectionException e) {
			fail("invalid selection made");
		} catch (InvalidOptionException e) {
			fail("invalid option choosen");
		}
		assertTrue(player.deck.hand.size()==0);
	
	}
	
	
	@Test
	public void testDescription() {
		Cards.testDescription(newGame(), Baron.class);
	}

}
