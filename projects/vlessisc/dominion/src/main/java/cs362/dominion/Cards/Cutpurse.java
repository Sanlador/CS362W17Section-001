package cs362.dominion.Cards;

import java.util.ArrayList;

import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Option;
import cs362.dominion.Resources.Player;
import cs362.dominion.Resources.Supply;

public class Cutpurse extends Card {
	
	
	public String getDefinition() {
		return "+$2. Each other player discards a Copper card (or reveals a hand with no Copper).";
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
		return 4;
	}
	

	
	public Option playCard(Game game, ArrayList<Player>players, Supply supply){
		for(Player player : players){
			if(player!=game.getCurrentPlayer() && player.deck.hand.handContainsCard(Copper.class)){
				player.deck.hand.discard(Copper.class);
			}
		}
		return null;
	}
	
}
