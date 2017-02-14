package cs362.dominion.Cards;

import java.util.ArrayList;

import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Option;
import cs362.dominion.Resources.Player;
import cs362.dominion.Resources.Supply;

public class Mine extends Card {
	
	
	public String getDefinition() {
		return "Trash a Treasure card from your hand. Gain a Treasure card costing up to $3 more; put it into your hand.";
	}
	
	public cardTypes getType(){
		return Card.cardTypes.KINGDOM;
	}
	
	/**
	 * There are 10 of each kingdom card
	 */
	public int getInitialSupply(int numPlayers){
		return 10;
	}
	
	
	public int getCost(){
		return 5;
	}
	
	/**
	 * To avoid complicated series of choices, this card will automatically pick the most favorable
	 * card the player to play. 
	 * If hand contains silver, trash silver, buy gold.
	 * If hand contains copper, trash copper, buy silver.
	 */
	public Option playCard(Game game, ArrayList<Player>players, Supply supply){
		// BUG 1
		// Introduce a bug where a user will discard a treasure card without first checking if the card 
		// they wish to purchase is in the supply
		if(game.getCurrentPlayer().deck.hand.handContainsCard(Silver.class)){
			Card silverCard = game.getCurrentPlayer().deck.hand.removeCardFromHand(Copper.class);
			supply.trashCard(silverCard);
			game.getCurrentPlayer().deck.hand.addCard(supply.drawCard(Gold.class));
			return null;
		}
		
		if(game.getCurrentPlayer().deck.hand.handContainsCard(Copper.class)){
			Card copperCard = game.getCurrentPlayer().deck.hand.removeCardFromHand(Copper.class);
			supply.trashCard(copperCard);
			game.getCurrentPlayer().deck.hand.addCard(supply.drawCard(Silver.class));
		}
		
		return null;
	}
	
	
	
}
