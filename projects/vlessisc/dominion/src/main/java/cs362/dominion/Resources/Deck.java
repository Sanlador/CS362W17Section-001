package cs362.dominion.Resources;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import cs362.dominion.Cards.*;

/**
 * @author chrisvlessis
 * Class representing a players deck. Each player will have a deck consisting of 7 coppers and 3
 * estates initialized upon construction.
 */
public class Deck {

	public static final int INITIAL_COPPERS = 7;
	public static final int INITIAL_ESTATES = 3;
	
	
	private ArrayList<Card> discardPile = new ArrayList<Card>();
	private ArrayList<Card> cards = new ArrayList<Card>();
	public Hand hand = null;
	
	public Deck(Game game){
		initializeCards(game);
		shuffle();
		this.drawNewHand();
	}
	
	private void initializeCards(Game game){
		for(int i=0;i<Deck.INITIAL_COPPERS;i++)
			this.cards.add(game.supply.drawCard(Copper.class));
		for(int i=0;i<Deck.INITIAL_ESTATES;i++)
			this.cards.add(game.supply.drawCard(Estate.class));
	}

	public void discardHand(){
		if(this.hand==null){return;}
		this.discardPile.addAll(this.hand.removeAllCards());
		this.drawNewHand();
		return;
	}
	
	public ArrayList<Card> allCards(){
		ArrayList<Card> allCards = new ArrayList<Card>();
		allCards.addAll(this.cards);
		allCards.addAll(this.hand.allCards());
		allCards.addAll(this.discardPile);
		return allCards;
	}
	
	public Card drawNextCard(){
		if(this.cards.size()==0){ this.replenishCards(); }
		// there are no cards left in the players deck return null
		if(this.cards.size()==0){ return null; }
		return this.cards.remove(0);
	}
	
	public void discard(Card card){
		this.discardPile.add(card);
	}
	
	
	private void drawNewHand(){
		ArrayList<Card> handCards = new ArrayList<Card>();
		for (int i=0; i<5; i++){ 
			if(this.cards.size()==0){ this.replenishCards(); }
			handCards.add(this.cards.remove(0)); 
		}
		this.hand = new Hand(handCards, this);
	}
	
	private void replenishCards(){
		while(this.discardPile.size()>0){this.cards.add(this.discardPile.remove(0)); }
		this.shuffle();
	}
		
	private void shuffle(){
		long seed = System.nanoTime();
		Collections.shuffle(this.cards, new Random(seed));
	}
	
	
	
	
}
