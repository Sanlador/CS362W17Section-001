package dominion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import dominion.Card.CardName;
import dominion.Card.Type;


public class Player {
	List<Card> hand = new ArrayList<Card>();// int hand[MAX_PLAYERS][MAX_HAND];
	LinkedList<Card> deck = new LinkedList<Card>();// int
	// deck[MAX_PLAYERS][MAX_DECK];
	List<Card> discard = new ArrayList<Card>(); // int
	// discard[MAX_PLAYERS][MAX_DECK];
	List<Card> playedCards = new ArrayList<Card>();

	String player_username;

	int numActions;
	int numBuys;
	int coins;

	final GameState gameState;


	public Player(GameState gameState,String player_username) {
		this.player_username = player_username;
		this.gameState=gameState;
	}

	final Card drawCard() {
		if (deck.isEmpty()) {// Deck is empty
			// Step 1 Shuffle the discard pile back into a deck
			System.out.println("reshuffle the deck of the player "
					+ player_username + " to draw FIVE cards");
			while (discard.size() > 0) {
				int ndx = (int) Randomness.nextRandomInt(discard.size());
				// Move discard to deck
				deck.add(discard.remove(ndx));
			}
		}

		Card toDraw = deck.poll();
		hand.add(toDraw);// Add card to hand and hand count automatically will
		// be incremented since we use List
		System.out.println("draw " + toDraw);
		Collections.sort(hand);
		return toDraw;
	}

	final void initializePlayerTurn() {
		//initialize first player's turn
		numActions = 1;
		coins = 0;
		numBuys = 1;
		//Shuffle your starting 10 cards (7 Coppers & 3 Estates) and place them face-down as your Deck. Draw the top
		//5 cards as your starting hand
		for (int i = 0; i < 5; i++) {
			drawCard();
		}
	}

	//card goes in discard,
	final boolean gain(Card card) {
		discard.add(card);
		System.out.println("Player: "+this.player_username+" gains "+card);
		return true;
	}		 

	//Discard hand
	public void discard(Card card) {
		hand.remove(card);
		discard.add(card);
		System.out.println("Player:  "+player_username+" discards "+card);
	}

	final int scoreFor() { 
		int score = 0;
		//score from hand
		
		List<Card> final_cards = new ArrayList<Card>();
		
		final_cards.addAll(hand);
		final_cards.addAll(deck);
		final_cards.addAll(discard);
		
		for (Card c : final_cards){
			if(c.getCardName() == Card.CardName.Gardens){
				score += (final_cards.size() / 10);
			}
			else
				score += c.score(this);
		}
		return score;
	} 

	public void playKingdomCard() {
		while (numActions > 0) {
			List<Card> actionCards = Card.filter(hand, Type.ACTION);

			if (actionCards.size() == 0)
				return;

			Card c = (Card)actionCards.get(0);
			if (c == null)
				return;
			System.out.println("Player.actionPhase Card:" + c.toString());

			playedCards.add(c);
			numActions--;
			c.play(this, gameState);
			discard(c);

			if(c.getCardName() == CardName.Feast || c.getCardName() == CardName.Embargo){
				discard.remove(c);
			}
		}
	}   

	public void playTtreasureCard() {

		List<Card> treasureCards = Card.filter(hand, Type.TREASURE);

		for(Card c : treasureCards){
			coins += c.getTreasureValue();
		}

	}
	public void buy_to_hand(Card c){
		int supply_value;
		hand.add(c);
		supply_value = gameState.gameBoard.get(c);
		gameState.gameBoard.put(c, supply_value-1);
	}
	public void buyCard() {
		Card.CardName name;
		int supply_value;
		while(numBuys > 0){
			
			if(coins >= 8){
				name = Card.CardName.Province; 
				supply_value = gameState.gameBoard.get(Card.getCard(gameState.cards, name));	
			}
			else if(coins == 6){
				name = Card.CardName.Gold; 
				supply_value = gameState.gameBoard.get(Card.getCard(gameState.cards, name));	
			}
			else if(coins == 5){
				name = Card.CardName.Duchy; 
				supply_value = gameState.gameBoard.get(Card.getCard(gameState.cards, name));	
			}
			else if(coins == 4){
				if(gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Smithy)) != null){
					name = Card.CardName.Smithy; 
					supply_value = gameState.gameBoard.get(Card.getCard(gameState.cards, name));	
				}
				else{
					coins--;
					continue;
				}
			}
			else if (coins == 3){
				name = Card.CardName.Silver; 
				supply_value = gameState.gameBoard.get(Card.getCard(gameState.cards, name));
			}
			else if (coins >= 1){
				name = Card.CardName.Estate; 
				supply_value = gameState.gameBoard.get(Card.getCard(gameState.cards, name));
			}
			else{
				name = Card.CardName.Copper; 
				supply_value = gameState.gameBoard.get(Card.getCard(gameState.cards, name));
			}

			if(supply_value == 0){
				coins--;
				continue;
			}
			//hand.add(Card.getCard(gameState.cards, name));
			gain(Card.getCard(gameState.cards, name));
			gameState.gameBoard.put(Card.getCard(gameState.cards, name), supply_value-1);
			numBuys--;

		}   
	}
	
	final void endTurn() {
		//discard hand at the end of your turn
		while(hand.size() > 0){
			discard(hand.get(0));
		}
		this.initializePlayerTurn();      
	}


	public void printStateGame(){
		System.out.println(" --- " + this.player_username + " --- ");
		System.out.println(" --- --------------------------- --- ");
		System.out.println(this.gameState.toString()); 
		System.out.println(" --- --------------------------- --- ");
	}

	//I made this function so I can print the hand and have it actually be readable 
	public void printHand(){
		int i = 1;
		System.out.println("Start Hand");

		for(Card c: this.hand)
		{
			System.out.println(i + " " + c.getCardName() + "\n");
			i++;
		}
		System.out.println("End Hand");
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(" --- " + this.player_username + " --- ");
		sb.append(" --- --------------------------- --- ");

		sb.append("Hand: " + this.hand);
		sb.append("Discard: " + this.discard);
		sb.append("Deck: " + this.deck);
		sb.append("Played Cards: " + this.playedCards);
		sb.append("numActions: " + this.numActions);
		sb.append("coinss: " + this.coins);
		sb.append("numBuys: " + this.numBuys);
		sb.append("\n");     

		return sb.toString();

	}

}