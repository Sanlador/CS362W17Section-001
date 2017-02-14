package org.cs362.dominion;

import java.util.ArrayList;

public class GreatHall extends Card {

	public GreatHall(){
		super("Great Hall", 3, 1, 0,
				new CardType[] {CardType.Action, CardType.Victory});
	}
	//performs the cards action
	public void Action(ArrayList<Player> players,
			Player currentPlayer, Board board){
		currentPlayer.drawCard();
		currentPlayer.addActions(1);
	};
}
