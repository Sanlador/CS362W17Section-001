package org.cs362.dominion;

public class PlayDom {
	public static void main(String args[]) {
		DominionBoard board = new DominionBoard();
		GameState state = new GameState(board);

		while(true) {
			state.TakeTurn();
			if(board.GameOver()) {
				break;
			}
		}
		
		System.out.println("Finished game.");
		state.PrintScore();
		
		
		System.exit(0);
	}
}
