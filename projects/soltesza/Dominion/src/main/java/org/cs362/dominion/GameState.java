package org.cs362.dominion;

public class GameState {
	public Player currentPlayer;
	public Player otherPlayer; 
	
	private Player player1;
	private Player player2;
	
	public GameState(DominionBoard board) {
		player1 = new Player("Player 1", board);
		player2 = new Player("Player 2", board);
		
		currentPlayer = player1;
		otherPlayer = player2;
	}
	
	public void TakeTurn() {
		currentPlayer.TakeTurn(this);
		
		Player p1 = currentPlayer;
		currentPlayer = otherPlayer;
		otherPlayer = p1;
	}
	
	public void DrawCards(Player player, int numCards) {
		player.deck.DrawCards(numCards);
	}
	
	public void PrintScore() {
		System.out.println("Score:");
		
		System.out.println(player1.GetName() + ": " + player1.GetScore());
		System.out.println(player2.GetName() + ": " + player2.GetScore());
	}
}
