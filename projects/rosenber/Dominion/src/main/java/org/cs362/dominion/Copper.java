/*
 * Implements the Copper card for a game of Dominion. It
 * extends the card class. Sets victory points to 0, 
 * cost to 0, and type to treasure.
 * 
 * Robert Rosenberger
 * 2/9/2017
 */
package org.cs362.dominion;

public class Copper extends Card{

	public Copper(){
		super("Copper", 0, 0, 1, new CardType[] {CardType.Treasure});
	}
	
}
