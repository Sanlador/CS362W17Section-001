package cs362.dominion.Cards;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import cs362.dominion.Exceptions.ConfiguationException;
import cs362.dominion.Exceptions.InvalidOptionException;
import cs362.dominion.Exceptions.InvalidSelectionException;
import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Option;
import cs362.dominion.Helpers.*;

public class FeastTest {

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
	public void testPlayCard(){
		// Make it so the Feast card is the only card in the players hand
		this.game.getCurrentPlayer().deck.hand.removeAllCards();
		this.game.getCurrentPlayer().deck.hand.addCard(this.game.supply.drawCard(Feast.class));
		try {
			Option cardOptions = this.game.playCard(Feast.class);
			// after card is played there should be zero cards in the players hand
			assertTrue(this.game.getCurrentPlayer().deck.hand.size()==0);
			this.playCardOptions(cardOptions);
			// make sure a card got added to the players hand
			assertTrue(this.game.getCurrentPlayer().deck.hand.size()==1);
		
			
		} catch (InvalidSelectionException e) {}
	}
	
	private void playCardOptions(Option cardOptions){
		while(cardOptions!=null){
			int optionChosen = new Random().nextInt(cardOptions.numOptions());
			try {
				cardOptions = cardOptions.decide(optionChosen);
			} catch (InvalidOptionException e) {
				fail("Invalid decision made");
			}		
		}
	}
	
	@Test
	public void testDescription() {
		Cards.testDescription(game, Feast.class);
	}
	
	

}
