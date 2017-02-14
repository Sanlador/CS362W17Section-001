package org.cs362.dominion;

import java.util.ArrayList;
import java.util.Random;

public class Feast extends Card {

	public Feast(){
		super("Feast", 4, 0, 0,
				new CardType[] {CardType.Action});
	}
	public void Action(ArrayList<Player> players,
			Player currentPlayer, Board board){
		if(currentPlayer.checkAi()){
			AIAction(players, currentPlayer, board);
			return;
		}
		//TODO: implement non-ai version
	};
	private void AIAction(ArrayList<Player> players,
			Player currentPlayer, Board board){
		board.addToTrash(this);
		int choice = -1;
		Random rand = new Random();
		do{
			choice = -1;
			while(choice < 1)
				choice = rand.nextInt(board.numDecks());
		}while(board.lookAtDeck(choice).getCost() < 5);
		currentPlayer.giveCard(board.draw(choice));
	}
}
