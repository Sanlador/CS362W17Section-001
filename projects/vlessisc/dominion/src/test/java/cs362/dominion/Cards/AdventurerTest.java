package cs362.dominion.Cards;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cs362.dominion.Exceptions.ConfiguationException;
import cs362.dominion.Exceptions.InvalidSelectionException;
import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Game.gamePhase;
import cs362.dominion.Resources.Player;
import cs362.dominion.Helpers.*;

public class AdventurerTest {

	private Class<? extends Card> cardType = Adventurer.class;
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
	
	/**
	 * Makes sure the baron adds no buys to the hand 
	 */
	@Test
	public void testBuys() {
		Cards.testBuys(game, cardType, 0);
	}
	
	@Test
	public void testPlayCard() {
		Player testPlayer = game.getCurrentPlayer();
		// add treasures to players hand to make sure they can be drawn by adventurer
		testPlayer.deck.hand.addCard(game.supply.drawCard(Copper.class));
		testPlayer.deck.hand.addCard(game.supply.drawCard(Copper.class));
		testPlayer.deck.hand.addCard(game.supply.drawCard(Copper.class));
		testPlayer.deck.hand.addCard(game.supply.drawCard(Copper.class));
		// skip turns til its action phase for player again
		while(!(game.getPhase()==gamePhase.ACTION && game.getCurrentPlayer()==testPlayer)){
			game.nextPhase();
		}
		
		
		testPlayer.deck.hand.addCard(game.supply.drawCard(cardType));
		int coinsBefore=testPlayer.deck.hand.getCoins();
		try {
			game.playCard(cardType);
		} catch (InvalidSelectionException e) {
			fail("Could not play card");
		}
		int coinsAfter=testPlayer.deck.hand.getCoins();
		// All treasure cards at this point should have been copper so change should be +2
		assertTrue(coinsAfter-coinsBefore==2);

		
	}
	
	@Test
	public void testDescription() {
		Cards.testDescription(game, cardType);
	}


}
