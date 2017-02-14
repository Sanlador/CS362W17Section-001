package cs362.dominion.Cards;

import java.util.ArrayList;

import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Option;
import cs362.dominion.Resources.Player;
import cs362.dominion.Resources.Supply;


public class Adventurer extends Card {
	
	
	public String getDefinition() {
		return "Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards in your hand and discard the other revealed cards.";
	}
	
	public cardTypes getType(){
		return cardTypes.KINGDOM;
	}
	
	/**
	 * There are 10 of each kingdom card
	 */
	public int getInitialSupply(int numPlayers){
		return 10;
	}
	
	
	public int getCost(){
		return 6;
	}
	
	/**
	 * Reveal cards from your deck until you reveal 2 Treasure cards. 
	 * Put those Treasure cards in your hand and discard the other revealed cards
	 */
	public Option playCard(Game game, ArrayList<Player>players, Supply supply){
		int foundTreasureCards=0;
		// Continue revealing until 2 found treasure cards
		while(foundTreasureCards<2){
			Card drawnCard = game.getCurrentPlayer().deck.drawNextCard();
			// if treasure card. Put in hand, increment treasure count
			if(drawnCard.getType()==cardTypes.TREASURE){
				game.getCurrentPlayer().deck.hand.addCard(drawnCard);
				foundTreasureCards+=1;
			} else{
				// discard
				game.getCurrentPlayer().deck.discard(drawnCard);
			}
		}
		return null;
	}
	
}
