package org.cs362.dominion;

import java.util.ArrayList;
import java.util.Random;

public class Baron extends Card{

	public Baron(){
		super("Baron", 4, 0, 0,
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
		currentPlayer.addBuys(1);
		Random rand = new Random();
		if(rand.nextBoolean()){
			try{
				currentPlayer.discardCard("Estate");
				currentPlayer.addMoney(4);
				return;
			}
			catch(Exception e){} //didn't have an estate
		}
		currentPlayer.giveCard(board.draw(board.getDeckIdx("Estate")));
	}
}
