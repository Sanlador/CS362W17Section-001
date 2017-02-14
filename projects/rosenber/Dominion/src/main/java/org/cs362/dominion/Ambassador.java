package org.cs362.dominion;

import java.util.ArrayList;
import java.util.Random;

public class Ambassador extends Card{
	
	public Ambassador(){
		super("Ambassador", 3, 0, 0, 
				new CardType[] {CardType.Action, CardType.Attack});
	}
	//performs the cards action
	public void Action(ArrayList<Player> players,
			Player currentPlayer, Board board){
		Attack(players, currentPlayer, board);
	};
		
	//performs the cards attack
	public void Attack(ArrayList<Player> players,
			Player currentPlayer, Board board){
		if(currentPlayer.checkAi()){
			AIAttack(players, currentPlayer, board);
			return;
		}
		//TODO: implement non-ai version
	};
	public void AIAttack(ArrayList<Player> players, Player currentPlayer, Board board){
		Random rand = new Random();
		int choice = -1;
		Card c = null;
		choice = rand.nextInt(currentPlayer.numCardsHand());
		c = currentPlayer.playCard(choice);
		board.addToDeck(c, board.getDeckIdx(c.getName()));
		for(int j=0; j<players.size(); j++)
			if(players.get(j) != currentPlayer){
				players.get(j).giveCard(board.draw(board.getDeckIdx(c.getName())));
			}
	}
}
