package org.cs362.dominion;

import java.util.ArrayList;

public class Cutpurse extends Card{

	public Cutpurse(){
		super("Cutpurse", 4, 0, 0,
				new CardType[] {CardType.Action, CardType.Attack});
	}
	public void Action(ArrayList<Player> players,
			Player currentPlayer, Board board){
		currentPlayer.addMoney(2);
		Attack(players, currentPlayer, board);
	};
	public void Attack(ArrayList<Player> players,
			Player currentPlayer, Board board){
		for(int j=0; j<players.size(); j++){
			if(players.get(j) != currentPlayer){
				try{
					players.get(j).discardCard("Copper");
				}
				catch(Exception e){
					System.out.println(players.get(j).getName()+
							" had no copper. This is their hand");
					players.get(j).seeHand();
				}
			}
		}
	}
}
