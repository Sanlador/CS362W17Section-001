package org.cs362.dominion;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {
    private LinkedList<Card> cards;
    private int embargo;

    public Deck() {
        cards = new LinkedList<Card>();
        embargo = 0;
    }

    public Deck(Card card, int num){
        this();
        for (int i = 0; i < num; ++i){
            cards.push(card);
        }
    }

    public void shuffle(){
        Collections.shuffle(cards, Randomness.random);
    }

    public int size(){return cards.size();}

    public Card take(){
    	return cards.pop();
    }
    
    
    public void add(List<Card> cards){
    	this.cards.addAll(cards);
    }
    public void add(Card c){
    	cards.push(c);
    }
    public Card peek(){
    	return cards.peek();
    }
    public void addEmbargo(){
    	embargo++;
    }
    public int embargo(){
    	return embargo;
    }
    public LinkedList<Card> getCards(){
    	return cards;
    }
}