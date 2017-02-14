package org.cs362.dominion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.cs362.dominion.Card.Type;





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
	int extra;
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
			if(toDraw == null){
				System.exit(0);
			}
			Collections.sort(hand);
		
			return toDraw;
		
	}
	
	   final void initializePlayerTurn() {
		   //initialize first player's turn
//		   state->numnumActions = 1;
//		   state->numnumBuys = 1;
		      numActions = 1;
		      coins = 0;
		      numBuys = 1;
		      //Shuffle your starting 10 cards (7 Coppers & 3 Estates) and place them face-down as your Deck. Draw the top
		      //5 cards as your starting hand
		      int i = 0;
		      while(i < 5) { //we get an error here as we attempt to draw null...deck seems to be empty after first run
		    	  drawCard(); //^ with this deck size thing it prevents error but if we remove it we get a better run
		    	  i++;
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
			      numActions -= 1;

			      c.play(this, gameState);
		      }
		   }
	   final int scoreFor() { 
		   int score = 0;
		   //score from hand
		      for (Card c : hand)
		    	  score += c.score();
		      //score from discard
		      for (Card c : discard)
		    	  score += c.score();
		      //score from deck
		      for (Card c : deck)
		    	  score += c.score();
		      //extra score as result of gardens card
		      score += extra;
		      
		    
		      return score;
	   }    
	   
	   public void playTtreasureCard() {
		    /*1. sum up all the treasure cards in hand (filter function in card can be used to grab just treasure cards)
		     *2. Add cards to playedCards, since the coins will be used/played to get action cards, then treasure cards 
		     *3. Remove treasure cards from hand so that we don't accidently double the player's wealth
		     */
		   System.out.println("GETTING TREASURE VALUES..."); 
		   //List<Card> tresCards = new ArrayList<Card>();
		    List<Card> tresCards = Card.filter(hand, Card.Type.TREASURE);
		   // System.out.println("hand size: " + hand.size());
		    //System.out.println("tresCards size: " + tresCards.size());
		    if(tresCards != null){
		    	for(int i = 0; i < tresCards.size(); i++){
		    		coins += tresCards.get(i).getTreasureValue();
		    	    playedCards.add(tresCards.get(i));
		    	    hand.remove(tresCards.get(i));
		    	}
		    	 System.out.println("COINS: " + coins);
		    }
		  
		    
		    
		    
    		
    		
    		
	   
	   }
	   public void buyCard() { 
		    /*1. give options for all possible numbers of coins
		     *2. If 0 coins, we just end the turn
		     *3. If 1 coins, we buy copper 
		     *4. If 2 coins, we randomly choose either silver/estate/... (other card worth 2)
		     *5. If 3 coins, we randomly choose either gold/dutchy/...
		     *6. We keep doing this, starting from highest feasible coin value: if(highest){buy x} if(highest-1) {buy x}
		     *until we get down to some base cases 
		     *8 is the highest card value, so we only need to go up to this
		     */
		   
		    System.out.println("BUY PHASE");
   			  
   			while(numBuys > 0){	
	   			if(coins >= 8){
	   				//Province
	   				
	   				gain(Card.getCard(gameState.cards, Card.CardName.Province));
	   				coins -= 8;
	   				numBuys--;
   			}
	   			if(coins == 6 || coins == 7){
	   				int randomNum = 1 + (int)(Math.random() * 2);
	   				//adventurer gold
	   				if(randomNum == 1){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Adventurer));
	   				}
	   				else if(randomNum == 2){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Gold));
	   				}
	   				
	   				coins -= 6;
	   				numBuys--;
	   			}
	   			if(coins == 5){
	   				int randomNum = 1 + (int)(Math.random() * 3);
	   				//duchy mine councilroom
	   				if(randomNum == 1){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Duchy));
	   				}
	   				else if(randomNum == 2){
	   					gain(Card.getCard(gameState.cards, Card.CardName.councilRoom));
	   				}
	   				else if(randomNum == 3){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Mine));
	   				}
	   				
	   				coins -= 5;
	   				numBuys--;
	   			}
	   			
	   			if(coins == 4){
	   				int randomNum = 1 + (int)(Math.random() * 5);
	   				//baron smithy cutpurse gardens remodel
	   				if(randomNum == 1){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Baron));
	   				}
	   				else if(randomNum == 2){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Smithy));
	   				}
	   				else if(randomNum == 3){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Cutpurse));
	   				}
	   				else if(randomNum == 4){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Gardens));
	   				}
	   				else if(randomNum == 5){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Remodel));
	   				}
	   				coins -= 4;
	   				numBuys--;
	   			}
	   			
	   			if(coins == 3){
	   				int randomNum = 1 + (int)(Math.random() * 4);
	   				//greathall ambassador village silver
	   				if(randomNum == 1){
	   					gain(Card.getCard(gameState.cards, Card.CardName.greatHall));
	   				}
	   				else if(randomNum == 2){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Ambassador));
	   				}
	   				else if(randomNum == 3){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Village));
	   				}
	   				else if(randomNum == 4){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Silver));
	   				}
	   				coins -= 3;
	   				numBuys--;
	   			}
	   			if(coins == 2){
	   				int randomNum = 1 + (int)(Math.random() * 2); 
	   				
	   				if(randomNum == 1){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Estate));
	   				}
	   				else if(randomNum == 2){
	   					gain(Card.getCard(gameState.cards, Card.CardName.Embargo));
	   				}
	   				coins -= 2;
	   				numBuys--;
	   			}
	   			if(coins == 1){
	   				gain(Card.getCard(gameState.cards, Card.CardName.Cooper));
	   				coins--;
	   				numBuys--;
	   			}
	   			if(coins == 0){
	   				return;
	   				
	   			}
	   
   			}
	   
	   }
	   final void endTurn() {
		    /*1. reinitialize actions, buys, coins
		     *2. discard hand, played cards 
		     *3. reinitialize hand, played cards
		     */
		    System.out.println("ENDING TURN");
		     
  			numActions = 1;
  			numBuys = 1;
  			coins = 0;
  			for(int i = 0; i < hand.size(); i++){
  				discard(hand.get(i));
  				
  			}
  			
  			
  			for(int i = 0; i < playedCards.size(); i++){
  				playedCards.remove(playedCards.get(0));
  				discard.add(playedCards.get(0));
  			}
  			for(int i = 0; i < 5; i++){
  				drawCard();
  			}
	   
	   
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