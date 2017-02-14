/*
 * Implements the Curse card for a game of Dominion. It
 * extends the card class. Sets victory points to -1, 
 * cost to 0, and type to curse.
 * 
 * Robert Rosenberger
 * 2/9/2017
 */
package org.cs362.dominion;

public class Curse extends Card{

	public Curse(){
		super("Curse", 0, -1, 0, new CardType[] {CardType.Curse});
	}
	
}
