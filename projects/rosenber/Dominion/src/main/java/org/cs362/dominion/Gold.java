/*
 * Implements the Gold card for a game of Dominion. It
 * extends the card class. Sets victory points to 0, 
 * cost to 6, and type to treasure.
 * 
 * Robert Rosenberger
 * 2/9/2017
 */
package org.cs362.dominion;

public class Gold extends Card{

	public Gold(){
		super("Gold", 6, 0, 3, new CardType[] {CardType.Treasure});
	}
	
}
