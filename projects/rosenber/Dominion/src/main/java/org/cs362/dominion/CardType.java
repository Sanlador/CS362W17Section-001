/*
 * This enum holds the different types of cards that are in a game of
 * Dominion. It is meant to be stored as many times as needed to fully
 * represent the types a card is.
 *  Example:
 *   - garden has both Action and Victory type
 *   
 *   Robert Rosenberger
 *   Last modified: 2/9/2017
 */
package org.cs362.dominion;
public enum CardType{
	Action, Treasure, Victory, Attack, Reaction, Curse;
}