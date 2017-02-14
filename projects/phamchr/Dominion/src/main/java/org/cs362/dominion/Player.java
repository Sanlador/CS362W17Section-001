package org.cs362.dominion;
import java.util.*;

import org.cs362.dominion.Card.CardName;
import org.cs362.dominion.Card.Type;

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
	private Random generator = new Random();

	
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
			      hand.remove(c);
			      numActions -= 1;

			      c.play(this, gameState);
		      }
		   }
	   final int scoreFor() { 
		   int score = 0;
		   int counter = 0; //For Gardens
		   //score from hand
		      for (Card c : hand){
		    	  score += c.score();
		      	counter++;
		      }
		      //score from discard
		      for (Card c : discard){
		    	  score += c.score();
		      	counter++;
		      }
		      //score from deck
		      for (Card c : deck){
		    	  score += c.score();
		      	counter++;
		      }
		      //Garden Point Counter
		      score += (int) Math.floor((double)counter / (double)10.00);
		      
		    
		      return score;
	   }    
	   
	   public void playTreasureCard() {
		   System.out.println(" --- --------------------------- --- ");
		   System.out.println("Playing Treasure Cards "); 
		   System.out.println(" --- --------------------------- --- ");
		   
		   List<Card> TCards = Card.filter(hand, Card.Type.TREASURE);
		   for(Card card : TCards){
			   coins += card.getTreasureValue();
			   System.out.println(card.toString());
			   playedCards.add(card);
			   hand.remove(card);
		   }
		   System.out.println(player_username+" has " + coins + " coins");
		   
	   }
	   public void buyCard() {
		   System.out.println(" --- --------------------------- --- ");
		   System.out.println(" Buying Card(s) ");
		   System.out.println(" --- --------------------------- --- ");
		   int rand = generator.nextInt(10);
		   //I want to make it so that you buy the most expensive thing first, and then go down. RNG to buy whatever.
		   while(numBuys > 0){
			   if(coins >= 8){
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Province)) != 0){
					   gain(Card.getCard(gameState.cards,CardName.Province));
					   coins -= 8;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Province), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Province))-1);
				   }
			   } else if (coins >= 6){
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Gold)) != 0){
					   gain(Card.getCard(gameState.cards,CardName.Gold));
					   coins -= 6;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Gold), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Gold))-1);
				   }
			   } else if (coins >= 5){
				   rand = generator.nextInt(4);
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Duchy)) != 0 && rand == 0){
					   gain(Card.getCard(gameState.cards,CardName.Duchy));
					   coins -= 5;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Duchy), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Duchy))-1);
				   } else if(gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Council_Room))!= null && gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Council_Room))!= 0 && rand == 1){
					   gain(Card.getCard(gameState.cards,CardName.Council_Room));
					   coins -= 5;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Council_Room), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Council_Room))-1);
				   }else if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Mine)) != null && rand == 2 && gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Mine)) != null){
					   gain(Card.getCard(gameState.cards,CardName.Mine));
					   coins -= 5;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Mine), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Mine))-1);
				   } else if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Gardens)) != null && rand == 3 && gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Gardens)) != 0){
					   gain(Card.getCard(gameState.cards,CardName.Gardens));
					   coins -= 5;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Gardens), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Gardens))-1);
				   }
			   } else if (coins >= 4) {
				   rand = generator.nextInt(4);
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Smithy)) != null && gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Smithy)) != 0 && rand == 0){
					   gain(Card.getCard(gameState.cards,CardName.Smithy));
					   coins -= 4;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Smithy), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Smithy))-1);
				   }else if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Feast)) != null && gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Feast)) != 0 && rand == 1){
					   gain(Card.getCard(gameState.cards,CardName.Feast));
					   coins -= 4;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Feast), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Feast))-1);
				   }else if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Ambassador)) != null && gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Ambassador)) != 0 && rand == 2){
					   gain(Card.getCard(gameState.cards,CardName.Ambassador));
					   coins -= 4;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Ambassador), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Ambassador))-1);
				   }else if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Cutpurse)) != null && gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Cutpurse)) != 0 && rand == 3){
					   gain(Card.getCard(gameState.cards,CardName.Cutpurse));
					   coins -= 4;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Cutpurse), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Cutpurse))-1);
				   }
			   }else if (coins >= 3){
				   rand = generator.nextInt(4);
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Silver)) != null && gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Silver)) != 0 && rand == 0){
					   gain(Card.getCard(gameState.cards,CardName.Silver));
					   coins -= 3;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Silver), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Silver))-1);
				   }else if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Village)) != null && gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Village)) != 0 && rand == 1){
					   gain(Card.getCard(gameState.cards,CardName.Village));
					   coins -= 3;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Village), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Village))-1);
				   }else if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Ambassador)) != null && gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Ambassador)) != 0 && rand == 2){
					   gain(Card.getCard(gameState.cards,CardName.Ambassador));
					   coins -= 3;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Ambassador), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Ambassador))-1);
				   }else if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Great_Hall)) != null && gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Great_Hall)) != 0 && rand == 3){
					   gain(Card.getCard(gameState.cards,CardName.Great_Hall));
					   coins -= 3;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Great_Hall), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Great_Hall))-1);
				   }
			   }else if (coins >= 2){
				   rand = generator.nextInt(2);
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Embargo)) != null && gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Embargo)) != 0 && rand == 0){
					   gain(Card.getCard(gameState.cards,CardName.Embargo));
					   coins -= 2;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Embargo), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Embargo))-1);
				   }else if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Estate)) != 0 && rand == 1){
					   gain(Card.getCard(gameState.cards,CardName.Estate));
					   coins -= 2;
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Estate), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Estate))-1);
				   }
			   } else {
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Copper)) != 0){
					   gain(Card.getCard(gameState.cards,CardName.Copper));
					   numBuys--;
					   gameState.gameBoard.put(Card.getCard(gameState.cards, Card.CardName.Copper), gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Copper))-1); 
				   }
			   }
		   }
	   }
	   final void endTurn() {
		   System.out.println(" --- --------------------------- --- ");
		   System.out.println("Ending Turn "); 
		   System.out.println(" --- --------------------------- --- ");
		   coins = 0;
		   System.out.println("Discarding hand now");
		   while(!playedCards.isEmpty()){
			   discard.add(playedCards.get(0));
			   playedCards.remove(0);
		   }
		   while(!hand.isEmpty()){
			   discard(hand.get(0));
		   }
		   System.out.println("Drawing new hand");
		   while(hand.size() != 5){
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
}
