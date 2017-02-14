/*
 * Implements the Estate card for a game of Dominion. It
 * extends the card class. Sets victory points to 1, 
 * cost to 2, and type to victory.
 * 
 * Robert Rosenberger
 * 2/9/2017
 */
package org.cs362.dominion;

public class Estate extends Card{

	public Estate(){
		super("Estate", 2, 1, 0, new CardType[] {CardType.Victory});
	}
	
}
