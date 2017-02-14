package dominion;


import java.util.ArrayList;
import java.util.Random;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;



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
	
	public void resetturn(){
		numActions = 1;
		numBuys=1;
	}

	final Card drawCard() {
		
		if(hand.size()<5){

		Card toDraw = deck.poll();
		hand.add(toDraw);  
		System.out.println("draw " + toDraw);
		return toDraw;
		
		
		}
		else{
			return null;
		}
	}
	
	final void shuffleDeck(){
		if (deck.isEmpty()) {
			System.out.println("reshuffle the deck of the player "
					+ player_username + " and now drawing up to 5 cards");
			while (discard.size() > 0) {
				int ndx = (int) Randomness.nextRandomInt(discard.size());
				deck.add(discard.remove(ndx));
			}
		}
	}
	
	   final void initializePlayerTurn() {
		      numActions = 1;
		      coins = 0;
		      numBuys = 1;
		      //Shuffle your starting 10 cards (7 Coppers & 3 Estates) and place them face-down as your Deck. Draw the top
		      //5 cards as your starting hand
		      for(int i=0; i<5; i++){
		    	  if(deck.size()==0){
		    		  shuffleDeck();
		    		  drawCard();
		    	  }
		    	  else if(deck.size()>0){
		    		  drawCard();
		    	  	  
		    	  }

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
		   
		         if (actionCards.size() == 0){
		        	System.out.println("no action cards in hand");
		            return;
		         }
		         
		         Card c = (Card)actionCards.get(0);
		         if (c == null)
			           return;
		         System.out.println("Player.actionPhase Card:" + c.toString());

			      discard(c);
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
	   public void buyCard(GameState gamestate) {
		   int turn_budget=0;
		   int counter = 0;
		   Random ran = new Random();
		   int x = ran.nextInt(11) + 5;
		   HashMap<Card, Integer> buyable = gamestate.getGamestate();
		   turn_budget=0;
		   
		   while(numBuys>0){
			   int random = ran.nextInt(11) + 5;
			   for(int i=0; i<hand.size(); i++){
				   Card c = (Card)hand.get(i);
				   turn_budget+=c.getValue();
			   }
			   Set set = buyable.entrySet();
			   Iterator iterator = set.iterator();
			   
			   while(iterator.hasNext()) {
				      counter+=1;
				      Map.Entry mentry = (Map.Entry)iterator.next();
				      Card f = (Card)mentry.getKey();
				      if(counter==random){
				      if(f.getCost()<=random){
				    	  if(f.getCost()<=turn_budget){
				    		  if(gamestate.piles[counter]>0){
				    		  System.out.println("Purchased: " + f);
				    		  discard.add(f);
				    		  gamestate.piles[counter]= gamestate.piles[counter] -1;
				    		  break;
				    		  }
				    	  }
				    	  
				     }
				     }
				      
			   }
			   break;
			   
		   }
		   endTurn();
		   
		   
		   
				   
			
	   }
	   final void endTurn() {
		    int limit = hand.size();
		   	for(int i=hand.size()-1; i>=0; i--){
		   	
		   		Card c = hand.get(i);
			    discard(c);
			    
			    

		   	}

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