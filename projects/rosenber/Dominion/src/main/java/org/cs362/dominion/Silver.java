/*
 * Implements the Silver card for a game of Dominion. It
 * extends the card class. Sets victory points to 0, 
 * cost to 3, and type to treasure.
 * 
 * Robert Rosenberger
 * 2/9/2017
 */

package org.cs362.dominion;

public class Silver extends Card{

	public Silver(){
		super("Silver", 3, 0, 2, new CardType[] {CardType.Treasure});
	}
	
}
