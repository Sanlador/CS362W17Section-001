package dominion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import dominion.Card.Type;

public class Player implements Cloneable{
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
			System.out.println("reshuffle "
					+ player_username + "'s deck and draw FIVE cards");
			while (discard.size() > 0) {
				int ndx = (int) Randomness.nextRandomInt(discard.size());
				// Move discard to deck
				deck.add(discard.remove(ndx));
			}
		}

		Card toDraw = deck.poll();
		hand.add(toDraw);// Add card to hand and hand count automatically will
		// be incremented since we use List
		System.out.println(player_username + " draws " + toDraw);
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
		System.out.println(this.player_username+" gains "+card);
		return true;
	}		   
	//Discard hand
	public void discard(Card card) {
		hand.remove(card);
		discard.add(card);
		System.out.println(player_username+" discards "+card);
	}
	//getHandSize
	public int getHandSize(){
		return hand.size();

	}

	//getAction
	public void getAction(){
		numActions += 1;

	}

	//getBuy
	public void getBuy(){
		numBuys += 1;

	}

	//getCoin
	public void getCoin(){
		coins += 1;

	}


	public void playKingdomCard() {
		while (numActions > 0) {
			List<Card> actionCards = Card.filter(hand, Type.ACTION);

			if (actionCards.size() == 0)
				return;

			Card c = (Card)actionCards.get(0);
			if (c == null)
				return;
			System.out.println( player_username + "'s Action Phase:" + c.toString());

			playedCards.add(c);
			numActions -= 1;

			c.play(this, gameState);
			discard(c);
		}
	}
	final int scoreFor() { 
		int score = 0;
		//score from hand
		for (Card c : hand){
			score += c.score();
			System.out.println("card: " + c);
			System.out.println("card score: " + score);
		}
		//score from discard
		for (Card c : discard){
			score += c.score();
			System.out.println("card: " + c);
			System.out.println("card score: " + score);
		}
		//score from deck
		for (Card c : deck){
			score += c.score();
			System.out.println("card: " + c);
			System.out.println("card score: " + score);
		}


		return score;
	}    

	public void playTreasureCard() {
	
		List<Card> treasureCards = Card.filter(hand, Type.TREASURE);
		if (treasureCards.size() == 0)
			return;
		for (int i = 0; i < treasureCards.size(); i++){
			Card c = (Card)treasureCards.get(0);
			if (c == null)
				return;
			System.out.println( player_username + "'s Treasure Phase:" + c.toString());
			playedCards.add(c);
			c.play(this, gameState);
			discard(c);
			}
		}
	
	public void buyCard() {	
		int amount;
		while (numBuys > 0){
			if(coins < 2){
				amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Copper));
				GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Copper), amount - 1 );
				gain(Card.getCard(gameState.cards, Card.CardName.Copper));
				numBuys--;
			}
			else if (coins == 2){
				Random rand = new Random();
				int rng = rand.nextInt((3-1)+1)+1;
				boolean not_done = true;
				while ( not_done ){
					if (rng == 1){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Estate));
						if (amount == 0){
							rng = 1;
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Estate), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.Estate));
						
						not_done = false;
					}
					else if(rng == 2){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.chapel));
						if (amount == 0){
							rng = 1;
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.chapel), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.chapel));
						
						not_done = false;
					}
					else if(rng == 3){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.cellar));
						if (amount == 0){
							rng = 1;
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.cellar), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.cellar));
						
						not_done = false;
					}		
				}
				numBuys--;
			}
			else if (coins == 3){
				Random rand = new Random();
				int rng = rand.nextInt((3-1)+1)+1;
				boolean not_done = true;
				while ( not_done ){
					if (rng == 1){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.chancellor));
						if (amount == 0){
							rng = 3;
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.chancellor), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.chancellor));
						
						not_done = false;
					}
					else if(rng == 2){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Village));
						if (amount == 0){
							rng = 3;
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Village), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.Village));
						
						not_done = false;
						
					}
					else if (rng == 3){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Silver));
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Silver), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.Silver));
						not_done = false;
					}				
				}	
				numBuys--;
			}
			else if (coins == 4){
				Random rand = new Random();
				int rng = rand.nextInt((4-1)+1)+1;
				boolean not_done = true;
				while ( not_done ){
					if (rng == 1){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.gardens));
						if (amount == 0){
							amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Copper));
							GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Copper), amount - 1 );
							gain(Card.getCard(gameState.cards, Card.CardName.Copper));
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.gardens), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.gardens));
						
						not_done = false;
					}
					else if(rng == 2){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Smithy));
						if (amount == 0){
							amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Copper));
							GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Copper), amount - 1 );
							gain(Card.getCard(gameState.cards, Card.CardName.Copper));
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Smithy), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.Smithy));
						
						not_done = false;
					}
					else if(rng == 3){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.feast));
						if (amount == 0){
							amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Copper));
							GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Copper), amount - 1 );
							gain(Card.getCard(gameState.cards, Card.CardName.Copper));
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.feast), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.feast));
						
						not_done = false;
					}
					else if(rng == 4){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.bureaucrat));
						if (amount == 0){
							amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Copper));
							GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Copper), amount - 1 );
							gain(Card.getCard(gameState.cards, Card.CardName.Copper));
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.bureaucrat), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.bureaucrat));
						
						not_done = false;
					}				
				}
				numBuys--;
			}
			else if (coins == 5){
				Random rand = new Random();
				int rng = rand.nextInt((5-1)+1)+1;
				boolean not_done = true;
				while ( not_done ){
					if (rng == 1){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Duchy));
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Duchy), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.Duchy));
						
						not_done = false;
					}
					else if(rng == 2){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.library));
						if (amount == 0){
							rng = 1;
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.library), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.library));
						
						not_done = false;
					}
					else if (rng == 3){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.laboratory));
						if (amount == 0){
							rng = 1;
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.laboratory), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.laboratory));
						
						not_done = false;
					}
					else if (rng == 4){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.festival));
						if (amount == 0){
							rng = 1;
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.festival), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.festival));
						
						not_done = false;
					}
					else if (rng == 5){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.council_room));
						if (amount == 0){
							rng = 1;
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.council_room), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.council_room));
						
						not_done = false;
					}
				}	
				numBuys--;
			}			
			else if (coins == 6){
				Random rand = new Random();
				int rng = rand.nextInt((2-1)+1)+1;
				boolean not_done = true;
				while ( not_done ){
					if (rng == 1){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Gold));
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Gold), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.Gold));
						
						not_done = false;
					}
					else if(rng == 2){
						amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Adventurer));
						if (amount == 0){
							rng = 1;
							break;
						}
						GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Adventurer), amount - 1 );
						gain(Card.getCard(gameState.cards, Card.CardName.Adventurer));
						
						not_done = false;
					}	
				}
				numBuys--;
			}
			else if (coins == 7){
				amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Gold));
				GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Gold), amount - 1 );
				gain(Card.getCard(gameState.cards, Card.CardName.Gold));	
				
				numBuys--;
			}
			else if (coins > 7){
				amount = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Province));
				GameState.gameBoard.put( Card.getCard(GameState.cards,  Card.CardName.Province), amount - 1 );
				gain(Card.getCard(gameState.cards, Card.CardName.Province));
				numBuys--;
			}
		}
	}
	
	
	final void endTurn() {
		for(int i = 0; i < hand.size(); i++ ){
			Card c = hand.get(i);
			discard(c);
			
		}
		for(int i = 0; i < 5; i++ ){
			drawCard();
			
		}
		numBuys = 1;
		numActions = 1;
		coins = 0;
	}


	public void printStateGame(){
		System.out.println(" --- " + this.player_username + " --- ");
		System.out.println(" --- --------------------------- --- ");
		System.out.println(this.gameState.toString()); 
		System.out.println(" --- --------------------------- --- ");
	}
	@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();

			sb.append("\n --- " + this.player_username + " --- \n");


			sb.append("\nHand: " + this.hand);
			sb.append("\nDiscard: " + this.discard);
			sb.append("\nDeck: " + this.deck);
			sb.append("\nPlayed Cards: " + this.playedCards);
			sb.append("\nnumActions: " + this.numActions);
			sb.append("\ncoinss: " + this.coins);
			sb.append("\nnumBuys: " + this.numBuys);
			sb.append("\n");     

			return sb.toString();

		}
/*
	protected Player clone() throws CloneNotSupportedException {
		Player clonePlayer=(Player) super.clone();
		clonePlayer.hand = new ArrayList<Card>(hand);// int hand[MAX_PLAYERS][MAX_HAND];
		clonePlayer.deck = new LinkedList<Card>(deck);// int
		// deck[MAX_PLAYERS][MAX_DECK];
		clonePlayer.discard = new ArrayList<Card>(discard); // int
		// discard[MAX_PLAYERS][MAX_DECK];
		clonePlayer.playedCards = new ArrayList<Card>(playedCards);
		return clonePlayer;
	} 	   
	*/
}
