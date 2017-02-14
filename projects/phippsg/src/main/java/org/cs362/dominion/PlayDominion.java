package org.cs362.dominion;

public class PlayDominion {

	public static void main( String[] args ) {
		
		GameState test = new GameState(2);
		test.initializeGame();
		
		test.play();
		
		System.exit(0);
    }
}
