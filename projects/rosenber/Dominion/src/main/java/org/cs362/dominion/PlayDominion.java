/*
 * This class is the actual Dominion playing class. It creates a game and
 * asks if the user wants to replay or not
 * 
 * Robert Rosenberger
 * 2/10/2017
 */

package org.cs362.dominion;

public class PlayDominion {

	public static void main(String args[]){
		
		Game game = new Game(2, true);
		game.play();
		
	}
	
}
