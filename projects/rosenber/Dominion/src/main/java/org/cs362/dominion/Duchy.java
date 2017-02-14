/*
 * Implements the Dutchy card for a game of Dominion. It
 * extends the card class. Sets victory points to 3,
 * cost to 5, and type to victory.
 * 
 * Robert Rosenberger
 * 2/9/2017
 */

package org.cs362.dominion;


public class Duchy extends Card {
	
	public Duchy(){
		super("Duchy", 5, 3, 0, new CardType[] {CardType.Victory});
	}
	
}
