package cs362.dominion.Resources;

import cs362.dominion.Cards.*;

public class Player {
	

	
	/*
	 * Number hands
	 */
	private int hands = 0;
	/*
	 * Number Buys
	 */
	
	/*
	 * Coins
	 */
	
	/*
	 * Parent Game
	 */
	final Game game;
	
	/*
	 * Players name
	 */
	final String name;
	
	/*
	 * Players deck of cards
	 */
	public Deck deck;
	
	public Player(Game game, String name){
		this.game = game;
		this.name = name;
		this.deck = new Deck(game);
	}
		
	
	public void incHandCount(){
		this.hands+=1;
	}
	
	public int getHands(){
		return this.hands;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getVictoryPoints(){
		int points = 0;
		for(Card card : this.deck.allCards()){
			// Garden cards are calculated differently
			if(card.getClass()==Gardens.class){
				points+=Math.floorDiv(this.deck.allCards().size(),10);
				continue;
			}
			points+=card.getVictoryPoints();
		}
		return points;
	}
	
	
}
