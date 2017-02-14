package org.cs362.dominion;

import java.util.ArrayList;

public class Smithy extends Card {

	public Smithy(){
		super("Smithy", 3, 0, 0,
				new CardType[] {CardType.Action});
	}
	//performs the cards action
	public void Action(ArrayList<Player> players,
			Player currentPlayer, Board board){
		for(int j=0; j<3; j++)
			currentPlayer.drawCard();
	};
}
