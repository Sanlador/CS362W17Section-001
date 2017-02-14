package dominion;

import java.util.*;
import java.util.LinkedList;
import java.util.List;

public class Deck{
	private final Card.CardName name;
	private ArrayList<Card> pile;

	Deck(Card.CardName name){
		this.name = name;
		this.pile = new ArrayList<Card>();
	}
	
	public Card.CardName getName(){
		return name;
	}

	public ArrayList<Card> getPile(){
		return pile;
	}
	
	public void addCard(Card card){
		pile.add(card);
	}

	public Card drawCard(){
		Card to_return = pile.get(pile.size()-1);
		pile.remove(pile.size()-1);
		return to_return;
	}

	public void shuffleDeck(){
		Collections.shuffle(pile);
	}	
}
