package org.cs362.dominion;

import java.util.ArrayList;

public class Village extends Card {
	
	public Village(){
		super("Village", 3, 0, 0,
				new CardType[] {CardType.Action});
	}

	public void Action(ArrayList<Player> players,
			Player currentPlayer, Board board){
		currentPlayer.drawCard();
		currentPlayer.addActions(2);
	};
	
}
