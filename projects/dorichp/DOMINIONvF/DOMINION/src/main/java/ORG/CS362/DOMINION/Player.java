package ORG.CS362.DOMINION;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

//import dominion.Card.Type;


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
//		   state->numnumActions = 1;
//		   state->numnumBuys = 1;
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
		   	  deck.remove(card);
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
		         List<Card> actionCards = Card.filter(hand, Card.Type.ACTION);
		   
		         if (actionCards.size() == 0)
		            return;
		         
		         Card c = (Card)actionCards.get(0);
		         if (c == null)
			           return;
		     //    System.out.println("Player.actionPhase Card:" + c.toString());
	 	System.out.println("Player " + player_username +" has played " + c.getCardName());
			      playedCards.add(c);
			      numActions -= 1;

			      c.play(this, gameState);
		      }
		   }
	   final int scoreFor() { 
		   int score = 0;
		   //score from hand
		      for (Card c : hand)
		    	  score += c.score(this);
		      //score from discard
		      for (Card c : discard)
		    	  score += c.score(this);
		      //score from deck
		      for (Card c : deck)
		    	  score += c.score(this);
		    
		      return score;
	   }    
	   
	   public void playTtreasureCard() {	//Used by hashmap play game function
	   
		   		
    		//You can't exactly "play" a treasure card, so I will use this as a counting coins functions
    	       List<Card> treasurecards = Card.filter(hand, Card.Type.TREASURE);

    	       if(treasurecards.size() == 0){		//if no treasure cards in hand, no coins
    	    	   coins = 0;
    	    	   return;
    	       }
    	       
    	       int i;
    	       Card tempcard;
    	       for(i = 0; i < treasurecards.size(); i++){
    	    	  tempcard = treasurecards.get(i);
    	  
    	    	  if (tempcard.getCardName() == Card.CardName.Copper){
    	    		  coins = coins + 1;
    	    	  }
    	    	  if(tempcard.getCardName() == Card.CardName.Silver){
    	    		  coins = coins + 2;
    	    	  }
    	    	  if(tempcard.getCardName() == Card.CardName.Gold){
    	    		  coins = coins + 3;
    	    	  }
    	       }

    	     
  	       }
	   public void buyCard() {				//Used by hashmap playgame function 
		   System.out.println(" --- --------------------------- --- ");
   			System.out.println("buyCard "); 
   			System.out.println(" --- --------------------------- --- ");   
	   
   			 
   			 int random = (int)  Randomness.random.nextInt(gameState.cards.size());
	         Card pulled = gameState.cards.get(random);
   			 gameState.cards.remove(random);
   			
   	
   			
   			if (numBuys < 1){		//If the player cannot buy anything, then that is it
   				System.out.println("Not enough buys");
   				
   				return;
   			}
   			
   			//If I do allow multiple buys, then I gotta check that the supply pile is still full. 
   			//Not sure how to access that yet..
   			
   			else if (coins < pulled.getCost()){ 			//Not enough coins to purchase card
   				System.out.println("Not enough coins");
   				System.out.println("coins -> " +coins);
   			    gain(Card.getCard(gameState.cards, Card.CardName.Copper));
   				return;	
   			}
   			else{
   				
   				deck.add(pulled);
   				coins = coins - pulled.getCost();
   				numBuys--;
   				System.out.println("Player " + player_username +" bought " +pulled.getCardName() +" for " +pulled.getCost() +" coins." );
   				
   			}
	   
	   }
	   final void endTurn() {		//Vital function used by the hashmap playgame function. 
		   System.out.println(" --- --------------------------- --- ");
   		System.out.println("endTurn "); 
   		System.out.println(" --- --------------------------- --- ");
		   
		   
		   
		   Card temp;
		   int i;
		   for(i = 0; i < hand.size(); i++){
			   temp = hand.get(i);
			   discard(temp);
			  
		   }
		   for(i = 0; i < 5; i++){				//after discarding, re draw 5
			   drawCard();
		   }
		   
		   //numActions = 1;			//Sets up next turn, just in case
		   numBuys = 1;
		   coins = 0;				//making sure that coins will be updated with playTreasureCard function
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
       	 sb.append("coins: " + this.coins);
       	 sb.append("numBuys: " + this.numBuys);
       	 sb.append("\n");     

		     return sb.toString();

		}
	   
}