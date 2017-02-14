package org.cs362.dominion;

import java.util.ArrayList;
import java.util.Random;

public class Salvager extends Card{

	public Salvager(){
		super("Salvager", 4, 0, 0,
				new CardType[] {CardType.Action});
	}
	//performs the cards action
	public void Action(ArrayList<Player> players,
			Player currentPlayer, Board board){
		if(currentPlayer.checkAi()){
			AIAction(players, currentPlayer, board);
			return;
		}
		//TODO: implement non-ai version
	}
	
	private void AIAction(ArrayList<Player> players,
			Player currentPlayer, Board board){
		Random rand = new Random();
		int choice = rand.nextInt(currentPlayer.numCardsHand());
		Card c = currentPlayer.playCard(choice);
		board.addToTrash(c);
		currentPlayer.addMoney(c.getCost());
	}
}
