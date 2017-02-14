package org.cs362.dominion;

import java.util.HashMap;
import java.util.Random;

public class PlayDom {

	public  static void main(String args[]){
		Card[] kCard = new Card[10];
		kCard[0] = Card.ADVENTURER;
		kCard[1] = Card.SMITHY;
		kCard[2] = Card.GARDENS;
		kCard[3] = Card.MINE;
		kCard[4] = Card.REMODEL;
		kCard[5] = Card.COUNCIL_ROOM;
		kCard[6] = Card.GREAT_HALL;
		kCard[7] = Card.DUCHY;
		kCard[8] = Card.FEAST;
		kCard[9] = Card.VILLAGE;
		Game game = new Game(kCard);
		Player p1 = new Player(game);
		Player p2 = new Player(game);
		
		game.start();
		
		while (!game.ended()){
			while (game.getCurrentPlayer().hasActions()){
			
				if (game.getCurrentPlayer().play(Card.ADVENTURER)){
				
				}
				else if (game.getCurrentPlayer().play(Card.MINE)){
				
				}
				else  {
					Card c = game.getCurrentPlayer().hand.get(Randomness.nextRandomInt(game.getCurrentPlayer().hand.size()));
					game.getCurrentPlayer().play(c);
				}
			}
			
			while (game.getCurrentPlayer().buys() > 0){
				game.getCurrentPlayer().spendGold();
				if (game.getCurrentPlayer().buy(Card.PROVINCE)) {
					
				} else if (game.getCurrentPlayer().buy(Card.ADVENTURER)){
					
				}
				else if (game.getCurrentPlayer().buy(Card.MINE)){
				
				}
				else if (game.getCurrentPlayer().buy(Card.GOLD)){
					
				}
				else if (game.getCurrentPlayer().buy(Card.SILVER)){
					
				}
				else  {
					Card c = game.supply.get(Randomness.nextRandomInt(game.supply.size())).peek();
					game.getCurrentPlayer().buy(c);
				}
			}
			System.out.println("Cleanup");
			game.getCurrentPlayer().cleanup();
			game.nextTurn();
			
			
			
		}


	}
}
