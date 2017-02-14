package org.cs362.dominion;

import java.util.Vector;
import java.util.Collections;
import java.util.Random;


public class Player {
	
	Vector<Card> playerHand = new Vector<Card>();		//cards in players' hand
	Vector<Card> playerDeck = new Vector<Card>();		//cards currently in deck/draw pile
	Vector<Card> playerDiscard = new Vector<Card>();	//Discard pile
	Vector<Card> inPlayCards = new Vector<Card>();	//cards player has currently in play
	
	public Vector<Card> getHand() {
		return playerHand;
	}
	
	public Vector<Card> getInPlay() {
		return inPlayCards;
	}
	
	public int getHandSize() {
		return playerHand.size();
	}
	
	//returns the total amount of money that the player has in their hand, for use in buy phase, so excludes action victory etc card values
	public int getMonOnHand() {
		int money = 0;
		for ( Card curCard : playerHand ) {
			if ( curCard.getType() == "treasure" ){
				money += curCard.getMoneyVal();
			}
		}
		return money;
	}
	
	//get total victory points in deck
	public int getVicPoints() {
		int vpSum = 0;
		int deckSize = playerHand.size() + playerDeck.size() + playerDiscard.size() + inPlayCards.size();
		for ( Card curCard : playerHand ) {
			if ( curCard.getName() == "Gardens" ) {
				vpSum += (int)(deckSize / 10);		//is this valid???
			}
			else {
				vpSum += curCard.getVicPoint();
			}
		}
		return vpSum;
	}
	
	//returns the action cards that the player has in hand
	/*public Vector<Card> getActCrdsInHnd() {
		Vector<Card> temp = new Vector<Card>();
		for( Card curCard : playerHand ) {
			if(curCard.getType() == "action" || curCard.getType() == "actionvictory") {
				
			}
		}
		return temp;
	}*/
	
	
	//prints the information of the cards in the players' hand.
	public void printHand() {
		System.out.println("Current hand:\n");
		for(Card curCard : playerHand) {
			System.out.println(curCard.getName() + "\tCost: " + curCard.getCost() + "\tMoney Value: " + curCard.getMoneyVal() + "\tVictory Points: " + curCard.getVicPoint() + "\nDescription: " + curCard.getDesc() );
		}
	}
	
	//returns card to be used in an action phase
	public Card playAction() {
		Random r = new Random();
		r.nextInt(playerHand.size());
		Card possActCard = playerHand.elementAt(r.nextInt(playerHand.size()));
		while ( possActCard.getType() != "action" && possActCard.getType() != "actionvictory" ) {
			possActCard = playerHand.elementAt(r.nextInt(playerHand.size()));
		}
		//put card in play
		playerHand.remove(possActCard);
		inPlayCards.add(possActCard);
		return possActCard;
	}
	
	//shuffling the deck
	public void shuffleDeck() {
		Collections.shuffle(playerDeck);
	}
	
	//initializing deck, meant to be used at start of game, gets players ready to play
	public void initilizeDeck() {
		playerDeck.clear();
		playerHand.clear();
		playerDiscard.clear();
		inPlayCards.clear();
		for (int i = 0; i < 7; i++) {
			playerDeck.add(new Card("Copper"));
		}
		for (int i = 0; i < 3; i++) {
			playerDeck.add(new Card("Estate"));
		}
		this.shuffleDeck();
		this.drawCard(5);
	}
	
	//shuffle discard into deck, like when user needs to draw a card from empty deck
	private void refreshDeck() {
		for(int i = 0; i < playerDiscard.size(); i++) {		//add all the cards in discard to deck
			Card temp = playerDiscard.firstElement();
			playerDeck.add(temp);
			playerDiscard.remove(0);
		}
		this.shuffleDeck();		//shuffle
	}
	
	//meant to be called by gamestate for when clear phase of turn is reached
	public void clearPhase() {
		for(Card curCard : playerHand) {	//put hand into discard
			playerDiscard.add(curCard);
			playerHand.remove(curCard);
		}
		for(Card curCard : inPlayCards) {	//move cards in play to discard
			playerDiscard.add(curCard);
			inPlayCards.remove(curCard);
		}
		this.drawCard(5);
	}
	
	//adventurer, when player plays adventurer card
	public void playAdventurer() {
		int numFound = 0;
		while ( numFound < 2 ) {		//check top card of deck, reveal it.  if it's a treasure, draw it, else discard it.
			Card temp = playerDeck.firstElement();
			System.out.println( "Revealing card: " + temp.getName() );
			if ( temp.getType() == "treasure" ) {
				this.drawCard(1);
				++numFound;
			}
			else {
				this.drawToDiscard(1);
			}
		}
		this.discard(new Card("Adventurer"));
	}
	
	//player draws num of cards specified to hand
	public void drawCard(int numDraw) {
		for(int i = 0; i < numDraw; i++){
			if(playerDeck.isEmpty()) {		//no more cards to draw, calls the refresh deck on itself
				this.refreshDeck();
			}
			Card temp = playerDeck.firstElement();
			playerHand.add(temp);
			playerDeck.remove(0);
		}
	}
	
	//player takes top x cards from top of deck and adds them to discard
	public void drawToDiscard(int numDraw) {
		for(int i = 0; i < numDraw; i++){
			if(playerDeck.isEmpty()) {		//no more cards to draw, calls the refresh deck on itself
				this.refreshDeck();
			}
			Card temp = playerDeck.firstElement();
			playerDiscard.add(temp);
			playerDeck.remove(0);
		}
	}
	
	//"gain" from game
	public void gain(Card newCard) {
		playerDiscard.add(newCard);
	}
	
	//"gain into hand" from game
	public void gainToHand(Card newCard) {
		playerHand.add(newCard);
	}
	
	//discard, returns true if successful, false if not
	public Boolean discard(Card disCard) {
		if ( playerHand.contains(disCard) ) {
			playerHand.remove(disCard);
			playerDiscard.add(disCard);
			return true;
		}
		else {
			return false;
		}
	}
	
	//Trash, idea from game,
	public void trash(Card delCard, String crdLoc) {
		switch(crdLoc) {
		case "Deck":
			playerDeck.remove(delCard);
			break;
		case "Hand":
			playerHand.remove(delCard);
			break;
		case "Discard":
			playerDiscard.remove(delCard);
			break;
		case "Play":
			inPlayCards.remove(delCard);
			break;
		}
	}
	
	//Reveal
	
	
	//Set aside
	
	
}