/*
 * Implements the Province card for a game of Dominion. It
 * extends the card class. Sets victory points to 6, 
 * cost to 8, and type to victory.
 * 
 * Robert Rosenberger
 * 2/9/2017
 */
package org.cs362.dominion;

public class Province extends Card{

	public Province(){
		super("Province", 8, 6, 0, new CardType[] {CardType.Victory});
	}
	
}
