package cs362.dominion.Cards;

import java.util.ArrayList;

import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Option;
import cs362.dominion.Resources.Player;
import cs362.dominion.Resources.Supply;


public class Embargo extends Card {

	
	public String getDefinition() {
		return "+$2. Trash this card. Put an Embargo token on top of a Supply pile. When a player buys a card, he gains a Curse card per Embargo token on that pile.";
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
	
	public int getCoins(){
		return 2;
	}
	
	public int getCost(){
		return 2;
	}
	
	
	/**
	 * Prof said in class that we don't need to implement curse cards so I will be skipping that 
	 * part of the functionality.
	 * Card will be removed from players hand and put in trash
	 */
	public Option playCard(Game game, ArrayList<Player>players, Supply supply){
		Card toTrash = game.getCurrentPlayer().deck.hand.removeCardFromHand(Embargo.class);
		game.supply.trashCard(toTrash);
		return null;
	}
	
	
}
