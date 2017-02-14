package org.cs362.dominion;

import java.util.ArrayList;

public class CouncilRoom extends Card{

	public CouncilRoom(){
		super("Council Room", 5, 0, 0,
				new CardType[] {CardType.Action});
	}
	public void Action(ArrayList<Player> players,
			Player currentPlayer, Board board){
		for(int j=0; j<4; j++)
			currentPlayer.drawCard();
		for(int j=0; j<players.size(); j++)
			if(players.get(j) != currentPlayer)
				players.get(j).drawCard();
	};
}
