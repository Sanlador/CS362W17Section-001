package cs362.dominion.Helpers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import cs362.dominion.Cards.Card;
import cs362.dominion.Exceptions.InvalidSelectionException;
import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Player;

/**
 * Card test helper methods
 * @author chrisvlessis
 *
 */
public class Cards {
	
	
	
	public static void testActions(Game game, Class<? extends Card> cardType, int expectedActions) {	
		Player player = game.getCurrentPlayer();
		// Add this card the players hand to ensure they can play it
		player.deck.hand.addCard(game.supply.drawCard(cardType));
		// Get the number of buys in the hand before the play
		int beforePlay = player.deck.hand.getActions();
		// Play the card
		try {
			game.playCard(cardType);
		} catch (InvalidSelectionException e) {
			fail("Invalid Selection");
		}
		// Get the number of buys after the play
		int afterPlay=player.deck.hand.getActions();
		// Make sure the value meets expectations.
		// In this case we subtract one because an action was used to play the card
		assertTrue(afterPlay-beforePlay==(expectedActions-1));
	}
	
	public static void testCards(Game game, Class<? extends Card> cardType, int expectedCards){
		Player player = game.getCurrentPlayer();
		// Add this card the players hand to ensure they can play it
		player.deck.hand.addCard(game.supply.drawCard(cardType));
		// Get the number of buys in the hand before the play
		int beforePlay = player.deck.hand.size();
		// Play the card
		try {
			game.playCard(cardType);
		} catch (InvalidSelectionException e) {
			fail("Invalid Selection");
		}
		// Get the number of buys after the play
		int afterPlay=player.deck.hand.size();
		// Make sure the value meets expectations
		// In this case it should be the value -1 because the cards itself was played
		assertTrue(afterPlay-beforePlay==(expectedCards-1));
	}
	
	public static void testBuys(Game game, Class<? extends Card> cardType, int expectedBuys){
		Player player = game.getCurrentPlayer();
		// Add this card the players hand to ensure they can play it
		player.deck.hand.addCard(game.supply.drawCard(cardType));
		// Get the number of buys in the hand before the play
		int beforePlay = player.deck.hand.getBuys();
		// Play the card
		try {
			game.playCard(cardType);
		} catch (InvalidSelectionException e) {
			fail("Invalid Selection");
		}
		// Get the number of buys after the play
		int afterPlay=player.deck.hand.getBuys();
		// Make sure the value meets expectations
		// In this case it should be the value -1 because the cards itself was played
		assertTrue(afterPlay-beforePlay==(expectedBuys));
	}
	

	public static void testCoins(Game game, Class<? extends Card> cardType, int expectedCoins) {
		Player player = game.getCurrentPlayer();
		// Add this card the players hand to ensure they can play it
		player.deck.hand.addCard(game.supply.drawCard(cardType));
		// Get the number of buys in the hand before the play
		int beforePlay = player.deck.hand.getCoins();
		// Play the card
		try {
			game.playCard(cardType);
		} catch (InvalidSelectionException e) {
			fail("Invalid Selection");
		}
		// Get the number of buys after the play
		int afterPlay=player.deck.hand.getCoins();
		// Make sure the value meets expectations
		// In this case it should be the value -1 because the cards itself was played
		assertTrue(afterPlay-beforePlay==(expectedCoins));
	}
	
	
	/**
	 * Tests if coins are greater than when starting. This is for cards which add cards as well as coins
	 * @param game
	 * @param cardType
	 * @param expectedCoins
	 */
	public static void testCoinsGT(Game game, Class<? extends Card> cardType) {
		Player player = game.getCurrentPlayer();
		// Add this card the players hand to ensure they can play it
		player.deck.hand.addCard(game.supply.drawCard(cardType));
		// Get the number of buys in the hand before the play
		int beforePlay = player.deck.hand.getCoins();
		// Play the card
		try {
			game.playCard(cardType);
		} catch (InvalidSelectionException e) {
			fail("Invalid Selection");
		}
		// Get the number of buys after the play
		int afterPlay=player.deck.hand.getCoins();
		// Make sure the value meets expectations
		// In this case it should be the value -1 because the cards itself was played
		assertTrue(afterPlay>beforePlay);
	}
	
	public static void testDescription(Game game, Class<? extends Card> cardType){
		assertTrue(game.supply.drawCard(cardType).getDefinition()!=null);
	}
	
	public void testVictoryPoints(){
		
	}
	
	
}
