package org.cs362.dominion;

import java.util.ArrayList;
import java.util.Random;

public class Mine extends Card {

	public Mine(){
		super("Mine", 5, 0, 0,
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
	};
	
	private void AIAction(ArrayList<Player> players,
		Player currentPlayer, Board board){
		
		Random rand = new Random();
		int choice = -1;
		Card c = null;
		do{
			choice = rand.nextInt(currentPlayer.numCardsHand());
			c = currentPlayer.lookAtCard(choice);
		}while(!c.isCardType(CardType.Treasure));
		c = currentPlayer.playCard(choice);
		board.addToTrash(c);
		Card otherC = null;
		choice = -1;
		do{
			while(choice < 1)
				choice = rand.nextInt(board.numDecks());
			otherC = board.lookAtDeck(choice);
		}while(!otherC.isCardType(CardType.Treasure) && otherC.getCost() > c.getCost()+3);
		currentPlayer.giveCard(board.draw(choice));
	}
}
