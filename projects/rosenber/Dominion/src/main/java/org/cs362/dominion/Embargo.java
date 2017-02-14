	package org.cs362.dominion;

import java.util.ArrayList;
import java.util.Random;

public class Embargo extends Card{

	public Embargo(){
		super("Embargo", 2, 0, 0,
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
	
	public void AIAction(ArrayList<Player> players,
			Player currentPlayer, Board board){
		currentPlayer.addMoney(2);
		board.addToTrash(this);
		boolean repeat;
		int choice = -1;
		Random rand = new Random();
		do{
			repeat = false;
			while(choice < 1)
				choice = rand.nextInt(board.numDecks());
			board.getDeck(choice).addEmbargos(1);
		}while(repeat);
	}
}
