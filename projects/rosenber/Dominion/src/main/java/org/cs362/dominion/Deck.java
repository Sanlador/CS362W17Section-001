/*
 * This class holds all of the Cards in a Dominion deck as well as
 * implements all of the functionality it needs. Cards can be added,
 * taken, viewed, etc from the deck.
 * 
 *  Robert Rosenberger
 *  Last modified: 2/8/2017
 */
package org.cs362.dominion;
import java.util.ArrayList;
import java.util.Random;

public class Deck{
	
	private String name;
	public ArrayList<Card> deck;
	int embargoCounter;
	
	//constructor
	public Deck(){
		name = "";
		deck = new ArrayList<Card>();
		embargoCounter = 0;
	}
	public Deck(String str){
		name = str;
		deck = new ArrayList<Card>();
		embargoCounter = 0;
	}
	
	//returns the number of embargo counters
	public int getEmbargos(){
		return embargoCounter;
	}
	
	//add value to the embargoConter value
	public void addEmbargos(int val){
		embargoCounter += val;
	}
	
	//returns the name
	public String getName(){
		return name;
	}
	
	//sets the name
	public void setName(String str){
		name = str;
	}
	
	//returns the number of cards in the deck
	public int size(){
		return deck.size();
	}
	
	//add card to the top
	public void addTop(Card c){
		deck.add(c);
	}
	
	//add card to the bottom
	public void addBottom(Card c){
		deck.add(0, c);
	}
	
	//returns the card at the top
	public Card getTop(){
		return deck.get(deck.size()-1);
	}
	
	//returns the card at the bottom
	public Card getBottom(){
		return deck.get(0);
	}
	
	//remove card from the top
	public void removeTop(){
		deck.remove(deck.size()-1);
	}
	
	//remove card from the bottom
	public void removeBottom(){
		deck.remove(0);
	}
	
	//removes and returns the card at the top
	public Card drawTop(){
		Card temp = this.getTop();
		removeTop();
		return temp;
	}
	
	//removes and returns the card at the bottom
	public Card drawBottom(){
		Card temp = this.getBottom();
		removeTop();
		return temp;
	}
	
	//swaps cards at given indices
	// - a and b must be good
	private void swap(int a, int b){
		Card temp = deck.get(a);
		deck.set(a, deck.get(b));
		deck.set(b, temp);
	}
	
	//shuffles the deck
	public void shuffle(){
		if(deck.size() < 2)
			return;
		int timesToSwap = 100;
		Random rand = new Random();
		int one, two;
		for(int j=0; j<timesToSwap; j++){
			one = rand.nextInt(deck.size());
			do{
				two = rand.nextInt(deck.size());
			}while(one == two);
			swap(one,  two);
		}
	}
	
	//checks to see if a specific card is in the deck
	// - returns true if it is, false otherwise
	public boolean isCard(Card c){
		if(deck.indexOf(c) == -1)
			return false;
		return true;
	}
	
	//returns a specific card from the deck
	// - throws indexOutOfRange exception if not in deck
	public Card findSpecific(Card c){
		return deck.get(deck.indexOf(c));
	}
	public Card findSpecific(int idx){
		return deck.get(idx);
	}
	
	//removes a specific card from the deck
	// - throws out of range exception if not in deck
	public void removeSpecific(Card c){
		deck.remove(c);
	}
	
	//returns and removes a specific card from the deck
	// - throws out of range exception if not in deck
	public Card getSpecific(Card c){
		Card temp = deck.get(deck.indexOf(c));
		deck.remove(temp);
		return temp;
	}
}































