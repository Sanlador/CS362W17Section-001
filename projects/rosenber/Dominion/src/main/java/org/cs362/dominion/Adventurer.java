package org.cs362.dominion;

import java.util.ArrayList;

public class Adventurer extends Card {

	public Adventurer(){
		super("Adventurer", 6, 0, 0, 
				new CardType[] {CardType.Action});
	}
	public void Action(ArrayList<Player> players,
			Player currentPlayer, Board board){
		Card c = null;
		int count = 0;
		while(count < 2){
			c = currentPlayer.getTopCard();
			c.print();
			if(c.isCardType(CardType.Treasure)){
				currentPlayer.addToHand(c);
				count++;
			}
			else{
				currentPlayer.giveCard(c);
			}
		}
	};
}
