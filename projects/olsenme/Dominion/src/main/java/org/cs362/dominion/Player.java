package org.cs362.dominion;

import java.util.ArrayList; 
import java.util.Collections; 
import java.util.LinkedList; 
import java.util.List; 
import org.cs362.dominion.Card.Type;

	public class Player implements Cloneable{
		//members
		List<Card> hand = new ArrayList<Card>();         // int hand[MAX_PLAYERS][MAX_HAND];
		LinkedList<Card> deck = new LinkedList<Card>();  // int deck[MAX_PLAYERS][MAX_DECK];
		List<Card> discard = new ArrayList<Card>();      // int  discard[MAX_PLAYERS][MAX_DECK];
		List<Card> playedCards = new ArrayList<Card>();
		
		String player_username;

		int numActions;
		int numBuys;
		int coins;
		
		final GameState gameState;
		
		//constructor
		public Player(GameState gameState,String player_username) {
			this.player_username = player_username;
			this.gameState=gameState;
		}
		//adds card to hand, also takes care of the case where the deck is empty, shuffles discard and puts it back in deck to draw from, returns card drawn
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
			//returns top card
			Card toDraw = deck.poll();
			hand.add(toDraw);// Add card to hand and hand count automatically will
			// be incremented since we use List
			System.out.println("draw " + toDraw);
	//		Collections.sort(hand);
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
		   //gets score for a single player
		   final int scoreFor() {
			   int score = 0;
			   //score from hand
			   for (Card c : hand) {
				   score += c.getScore();
			   }
			   //score from discard
			   for (Card c : discard)
				   score += c.getScore();
			   //check for gardens
			   if (discard.contains(Card.getCard(discard, Card.CardName.gardens))) {
				   if (discard.size() > 10 && discard.size() < 20) {
					   score += 1;
				   } else if (discard.size() > 20 && discard.size() < 30) {
					   score += 2;
				   } else if (discard.size() > 30 && discard.size() < 40) {
					   score += 3;
				   } else if (discard.size() > 40 && discard.size() < 50) {
					   score += 4;
				   } else if (discard.size() > 50 && discard.size() < 60) {
					   score += 5;
				   } else {
					   score += 0;
				   }
			   }
			   //score from deck
			   for (Card c : deck){
				   score += c.getScore();
			   //check for gardens
			   if (deck.contains(Card.getCard(deck, Card.CardName.gardens))) {
				   if (deck.size() > 10 && deck.size() < 20) {
					   score += 1;
				   } else if (deck.size() > 20 && deck.size() < 30) {
					   score += 2;
				   } else if (deck.size() > 30 && deck.size() < 40) {
					   score += 3;
				   } else if (deck.size() > 40 && deck.size() < 50) {
					   score += 4;
				   } else if (deck.size() > 50 && deck.size() < 60) {
					   score += 5;
				   }
			   }
		   }
			      return score;
		   }    
		   public void playTreasureCard() {
			   System.out.println(" --- --------------------------- --- ");
			   //get treasure cards
	    		List<Card> treasures= Card.filter(hand,Type.TREASURE); 
	    		if(treasures.size()==0)
	    		{
	    			return; 
	    		}
	    		for(Card c : treasures)
	    		{
	    			coins+=c.getTreasureValue(); 
	    			System.out.println(c.toString());
	    			playedCards.add(c); 
	    			hand.remove(c);
	    		}	
	    		System.out.println(" --- --------------------------- --- ");
		   }
		   public void buyCard() {
			   System.out.println(" --- --------------------------- --- ");
			   if(coins>0 && coins<8 &&numBuys>=1)
			   {
				   //randomly decide which kind of card to buy(1 for treasure, 2 for victory, 3 for action)
				  int random= Randomness.nextRandomInt(3);
				   if(random==1)
				   {
					   	//gold silver or copper
					   	int treasure= Randomness.nextRandomInt(3);
					   	if (treasure==1)
					   	{
						   //add a card to discard
						   gain(Card.getCard(gameState.cards, Card.CardName.Gold));  
					   	}
					   else if (treasure==2)
					   	{
						   //add a card to discard
						   gain(Card.getCard(gameState.cards, Card.CardName.Silver));  
					   	}
					    else
					    {
						   //add a card to discard
						   gain(Card.getCard(gameState.cards, Card.CardName.Copper));  
					     }
				   }
					 else if(random==2)
					 {
							int victory= Randomness.nextRandomInt(3);
							if (victory==1)
						   	{
							   //add a card to discard
							   gain(Card.getCard(gameState.cards, Card.CardName.Estate));  
						   	}
						   else if (victory==2)
						   	{
							   //add a card to discard
							   gain(Card.getCard(gameState.cards, Card.CardName.Province));  
						   	}
						    else
						    {
							   //add a card to discard
							   gain(Card.getCard(gameState.cards, Card.CardName.Duchy));
						     }
					 }
			   }
			   else if(coins>8 && numBuys>=1)
			   {
				   //add a card to discard
				   gain(Card.getCard(gameState.cards, Card.CardName.Province));  
			   }
				  
	   			System.out.println(" --- --------------------------- --- "); 
		   }
		   final void endTurn() {
			   System.out.println(" --- --------------------------- --- ");
			   coins = 0;
				numActions = 1;
				numBuys = 1;
				for(Card c: playedCards)
				{
					discard.add(c);
				}
				for(Card c: hand)
				{
					discard.add(c);
				}
				playedCards.clear();
	         	hand.clear();
	         	
	  			System.out.println(" --- --------------------------- --- ");      
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
		    protected Player clone() throws CloneNotSupportedException {     
		    	Player clonePlayer=(Player) super.clone();    
		    	clonePlayer.hand = new ArrayList<Card>(hand);// int hand[MAX_PLAYERS][MAX_HAND];
		        clonePlayer.deck = new LinkedList<Card>(deck);// int
		        // deck[MAX_PLAYERS][MAX_DECK];
		       clonePlayer.discard = new ArrayList<Card>(discard); // int
		       // discard[MAX_PLAYERS][MAX_DECK];
		       clonePlayer.playedCards = new ArrayList<Card>(playedCards); return clonePlayer;
		        } 
		   
}
