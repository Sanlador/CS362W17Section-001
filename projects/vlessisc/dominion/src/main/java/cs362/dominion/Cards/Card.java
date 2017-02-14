package cs362.dominion.Cards;

import java.util.ArrayList;

import cs362.dominion.Resources.Option;
import cs362.dominion.Resources.Supply;
import cs362.dominion.Resources.Player;
import cs362.dominion.Resources.Game;

public class Card {
	
	public enum cardTypes {
		TREASURE,
		/**
		 * Gold
		 * Silver
		 * Copper
		 */
		VICTORY,
		/**
		 * Duchy
		 * Estate
		 * Province
		 */
		KINGDOM
		/**
		 * Adventurer,
		 * Ambassador,
		 * Baron,
		 * Council_room,
		 * Cutpurse,
		 * Embargo,
		 * Feast,
		 * Gardens,
		 * Great_hall,
		 * Mine
		 * *
		 * Salvager
		 * Smithy
		 * Village
		 */
	};
	
	/**
	 * Card specific implementation function. 
	 * Returns card options of null if no implementation exists.
	 * @param game
	 * @param players
	 * @param supply
	 */
	public Option playCard(Game game, ArrayList<Player>players, Supply supply){
		return null;
	}
	
	/**
	 * Returns the number of cards to draw when this card is played. If card does not yield
	 * additional draws, simply return 0
	 * @return
	 */
	public int getCards(){
		return 0;
	}
	
	/**
	 * The default return for getCost is 0. Cards which do not cost anything will simply not
	 * implement this method
	 * @return 0
	 */
	public int getCost(){
		return 0;
	}
	
	/**
	 * The default return for getActions is 0. Cards which do not grant actions will simply not 
	 * implement this method
	 * @return 0
	 */
	public int getActions(){
		return 0;
	}
	/**
	 * The default return for getCoints is 0. Cards which do not grant coins will simply not 
	 * implement this method
	 * @return 0
	 */
	public int getCoins(){
		return 0;
	}
	/**
	 * The default return for getBuys is 0. Cards which do not grant buys will simply not 
	 * implement this method
	 * @return 0
	 */
	public int getBuys(){
		return 0;
	}
	
	/**
	 * The default return for getVictoryPoints is 0. Cards which do not grant victory points will 
	 * simply not implement this method
	 * @return 0
	 */
	public int getVictoryPoints(){
		return 0;
	}
	
	/*
	 * returns the named type of the card
	 */
	public String getName(){
		return this.getClass().getName().replace("cs362.dominion.Cards.", "");
	}
	
	/*
	 * Gets the card type
	 */
	public cardTypes getType(){
		throw new Error("Failed to immplement method");
	}
	
	/*
	 * Total number of cards of this type in the game
	 */
	public int getInitialSupply(int numPlayers){
		throw new Error("Failed to immplement getTotalNumCards for the following card: " + this.getClass().toString());
	}
	
	public String getDefinition(){
		return null;
	}
	
	
}
