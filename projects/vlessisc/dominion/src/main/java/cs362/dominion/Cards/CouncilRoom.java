package cs362.dominion.Cards;

import java.util.ArrayList;

import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Option;
import cs362.dominion.Resources.Player;
import cs362.dominion.Resources.Supply;

public class CouncilRoom extends Card {
	
	public String getDefinition() {
		return "+4 Cards; +1 Buy. Each other player draws a card.";
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
	
	public int getBuys(){
		return 1;
	}
	
	public int getCost(){
		return 5;
	}
	
	public int getCards(){
		return 4;
	}
	
	public Option playCard(Game game, ArrayList<Player>players, Supply supply){
		for(Player player : players){
			if(player==game.getCurrentPlayer()){ continue; }
			Card toAdd = player.deck.drawNextCard();
			player.deck.hand.addCard(toAdd);
		}
		return null;
	}
}
