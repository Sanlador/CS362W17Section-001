package cs362.dominion.Cards;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import cs362.dominion.Exceptions.ConfiguationException;
import cs362.dominion.Exceptions.InvalidOptionException;
import cs362.dominion.Exceptions.InvalidSelectionException;
import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Option;
import cs362.dominion.Resources.Player;
import cs362.dominion.Helpers.*;

public class AmbassadorTest {

	private Class<? extends Card> cardType = Ambassador.class;
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
		try {
			this.game = new Game(2, kingdomCards);
		} catch (ConfiguationException e) {
			fail("Could not initialize game");
		}
	}

	
	/**
	 * Plays ambassador and chooses to reveal Baron from hand.
	 * Makes sure 2 baron cards gets removed from the hand and that each 
	 * other player gets a copy.
	 */
	@Test
	public void testPlayCard() {
				
		// Create a new game and get the current player
		
		this.game.getCurrentPlayer().deck.hand.addCard(this.game.supply.drawCard(Ambassador.class));
		this.game.getCurrentPlayer().deck.hand.addCard(this.game.supply.drawCard(Baron.class));
		this.game.getCurrentPlayer().deck.hand.addCard(this.game.supply.drawCard(Baron.class));
		this.game.getCurrentPlayer().deck.hand.addCard(this.game.supply.drawCard(Baron.class));
		
		try {
			Option cardOptions = this.game.playCard(Ambassador.class);
			cardOptions = this.playBaronOption(cardOptions);
			// play two barons
			cardOptions.decide(1);
		} catch (InvalidSelectionException e){
			fail("Failed to play card");
		} catch (InvalidOptionException e) {
			fail("Failed to play card");
		}
		
		for(Player player : this.game.getPlayers()){
			if(this.game.getCurrentPlayer()!=player){
				assertTrue(player.deck.hand.handContainsCard(Baron.class)==true);
			}
		}
	
	}
	
	private Option playBaronOption(Option cardOption){
		int i=0;
		for(String iStr : cardOption.optionDescriptions()){
			if(iStr.contains("Baron")){ 
				try {
					return cardOption.decide(i);
				} catch (InvalidOptionException e) {}
			}
			i++;
		}
		fail("Could not find Baron option");
		return null;
	}
	
	@Test
	public void testDescription() {
		Cards.testDescription(game, cardType);
	}
	
}
