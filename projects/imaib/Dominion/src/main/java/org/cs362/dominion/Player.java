//*************************
//Name: Bradley Imai
//Project: Assignment 1
//File: Player.java
//Class: CS362
//*************************
package org.cs362.dominion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Player implements Cloneable{
	List<Card> hand = new ArrayList<Card>();// int hand[MAX_PLAYERS][MAX_HAND];
	LinkedList<Card> deck = new LinkedList<Card>();// int deck[MAX_PLAYERS][MAX_DECK];
	List<Card> discard = new ArrayList<Card>(); // int discard[MAX_PLAYERS][MAX_DECK];
	List<Card> playedCards = new ArrayList<Card>();
	public List<Card> trashpile = new ArrayList<Card>();
	List<Card> trashedCards = new ArrayList<Card>();

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
			System.out.println("Reshuffling discard pile of "+ player_username +" into a new deck");
			while (discard.size() > 0) {
				int ndx = (int) Randomness.nextRandomInt(discard.size());
				// Move discard to deck
				deck.add(discard.remove(ndx));
			}
		}

		Card toDraw = deck.poll();
		hand.add(toDraw);// Add card to hand and hand count automatically will be incremented since we use List
		System.out.println(player_username+" draws " + toDraw.getCardName());
		Collections.sort(hand);
		return toDraw;
	}

/*
*start players, start with 1 ation 1 buy
*shuffle deck of the 10 cards which include 7 coppers and 3 estates
*draw the 5 cards into hand
*/	
	   final void initializePlayerTurn() {
		      numActions = 1;
		      coins = 0;
		      numBuys = 1;
		      for (int i = 0; i < 5; i++) {
		    	  drawCard();
		      }
		   }


	   final boolean StartGain(Card card){
		   discard.add(card);
		   System.out.println(player_username+" gains "+card.getCardName());
		   return true;
	   }


	   final boolean gain(Card card){
		   discard.add(card);
		   System.out.println(player_username+" gains "+card.getCardName());
		   int newnum = gameState.gameBoard.get(Card.getCard(gameState.cards, card.getCardName()));
		   int thisindex = gameState.cards.indexOf(card);
		   gameState.gameBoard.remove(thisindex);
		   newnum--;
		   gameState.gameBoard.put(Card.getCard(gameState.cards, card.getCardName()), newnum);
		   return true;
	   }
	   

	   final boolean AdventurerGain(Card card){
		   hand.add(card);
		   System.out.println(player_username+" adds "+card.getCardName()+" to hand");
		   System.out.println(player_username+" gains "+card.getCardName());
		   int newnum = gameState.gameBoard.get(Card.getCard(gameState.cards, card.getCardName()));
		   int thisindex = gameState.cards.indexOf(card);
		   gameState.gameBoard.remove(thisindex);
		   newnum--;
		   gameState.gameBoard.put(Card.getCard(gameState.cards, card.getCardName()), newnum);
		   return true;
	   }
	   

	   final boolean MineGain(Card card){
		   hand.add(card);
		   System.out.println(player_username+" adds "+card.getCardName()+" to hand");
		   System.out.println(player_username+" gains "+card.getCardName());
		   int newnum = gameState.gameBoard.get(Card.getCard(gameState.cards, card.getCardName()));
		   int thisindex = gameState.cards.indexOf(card);
		   gameState.gameBoard.remove(thisindex);
		   newnum--;
		   gameState.gameBoard.put(Card.getCard(gameState.cards, card.getCardName()), newnum);
		   return true;
	   }
	   
   /*
   *discard a card from your hand
   */	
	   public void discard(Card card){
		   hand.remove(card);
		   discard.add(card);
		   System.out.println(player_username+" discards "+card.getCardName());
	   }


	   public void AdventurerDiscard(Card card){
		   deck.remove(card);
		   discard.add(card);
		   System.out.println(player_username+" discards "+card.getCardName());
	   }
   /*
   *play kingdom card and do its specfic actions
   */	   
	   public void playKingdomCard() {
		   System.out.println("Action Phase:");
		   if (Card.filter(hand, Card.Type.ACTION).size()==0)
		   {
			   System.out.println("No actions to play.");
		   }
		      while (numActions > 0) {
		         List<Card> actionCards = Card.filter(hand, Card.Type.ACTION);
		         if (actionCards.size() == 0)
		            return;
		         Card c = (Card)actionCards.get(0);
		         if (c == null)
			           return;
		         System.out.println("Action Phase Card:" + c.toString());
			      playedCards.add(c);
			      c.play(this, gameState);
			      numActions -= 1;
				  hand.remove(c);
				  discard.add(c);
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

		      return score;
	   }    
   /*
   *play your treasure card
   *loop through the players hand to see if there is a treasury card
   *if so play it
   */		   
	   public void playTreasureCard() {
		   System.out.println("Treasure Phase:");
			List<Card> treasureCards = Card.filter(hand, Card.Type.TREASURE);
			if (treasureCards.size() == 0)
			{
				System.out.println("No treasures to play.");
				return;
			}
			for (int k1 = hand.size(); k1>0; k1--)	
		    {
				Card c = hand.get(k1-1);
				if (c.getType() == Card.Type.TREASURE)	
		    	{
					System.out.println("Treasure Phase Card:" + c.toString());
					playedCards.add(c);
					c.play(this, gameState);	
					hand.remove(c);
					discard.add(c);
		    	}
				else
				{	}
		    }

	   }
   /*
   *Buy phase I will create a specfic cards to buy
   * 8 coins buy Province
   * 7 coins buy adventure
   * 6 coins buy Gold
   * 5 coins buy councile Room
   * 4 coins buy smithy
   * 3 coins buy Village
   * 2 coins buy estate
   * 1 or 0 buy copper (copper) is free
   * this will loop through for our various actions as well
   */	
	   public void buyCard() {
		   System.out.println("Buy Phase:");
		   while (numBuys > 0)
		   {
			   if (coins >= 8)
			   {
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Province))!= 0 && gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Province))!=null)
				   {
					   gain(Card.getCard(gameState.cards,Card.CardName.Province));
					   coins -= 8;
					   numBuys -= 1;
					   continue;
				   }
				   else	// Out of this card
				   {	}
			   }
			   if (coins >= 7)
			   {
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Adventurer))!= 0 && gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Adventurer))!=null)
				   {
					   gain(Card.getCard(gameState.cards,Card.CardName.Adventurer));
					   coins -= 7;
					   numBuys -= 1;
					   continue;
				   }
				   else	// Out of this card
				   {	}
			   }
			   if (coins >= 6)
			   {
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Gold))!= 0 && gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Gold))!=null)
				   {
					   gain(Card.getCard(gameState.cards,Card.CardName.Gold));
					   coins -= 6;
					   numBuys -= 1;
					   continue;
				   }
				   else	// Out of this card
				   {	}
			   }
			   if (coins >= 5)
			   {
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Council_Room))!= 0 && gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Council_Room))!=null)
				   {
					   gain(Card.getCard(gameState.cards,Card.CardName.Council_Room));
					   coins -= 5;
					   numBuys -= 1;
					   continue;
				   }
				   else	// Out of this card
				   {	}
			   }
			   if (coins >= 4)
			   {
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Smithy))!= 0 && gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Smithy))!=null)
				   {
					   gain(Card.getCard(gameState.cards,Card.CardName.Smithy));
					   coins -= 4;
					   numBuys -= 1;
					   continue;
				   }
				   else	// Out of this card
				   {	}
			   }
			   if (coins >= 3)
			   {
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Village))!= 0 && gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Village))!=null)
				   {
					   gain(Card.getCard(gameState.cards,Card.CardName.Village));
					   coins -= 3;
					   numBuys -= 1;
					   continue;
				   }
				   else	// Out of this card
				   {	}
			   }
			   if (coins >= 2)
			   {
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Estate))!= 0 && gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Estate))!=null)
				   {
					   gain(Card.getCard(gameState.cards,Card.CardName.Estate));
					   coins -= 2;
					   numBuys -= 1;
					   continue;
				   }
				   else	// Out of this card
				   {	}
			   }
			   if (coins >= 1)
			   {
				   if(gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Copper))!= 0 && gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Copper))!=null)
				   {
					   gain(Card.getCard(gameState.cards,Card.CardName.Copper));
					   numBuys -= 1;
					   continue;
				   }
				   else	// Out of this card
				   {	}
			   }
			   if (coins == 0)
			   {
				   System.out.println("Out of coin.");
				   break;
			   }
			}
	   }
	   
   /*
   *at the end of each turn discard hand and draw new 5
   */	   

	   final void endTurn() {
		   System.out.println("Clean Up:");
			if (hand.size() != 0)
			{
				for (int h = hand.size(); h>0; h--)
			    {
					Card c = hand.get(0);
					discard(c);
				}
			}
			else
			{	}
			drawCard();
			drawCard();
			drawCard();
			drawCard();
			drawCard();
  			coins = 0;
  			numActions = 1;
  			numBuys = 1;

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

       	 sb.append("\nHand: " + this.hand);
       	 sb.append("\nDiscard: " + this.discard);
       	 sb.append("\nDeck: " + this.deck);
       	 sb.append("\nPlayed Cards: " + this.playedCards);
       	 sb.append("\nTrashed Cards: " + this.trashedCards);
       	 sb.append("\nnumActions: " + this.numActions);
       	 sb.append("\ncoins: " + this.coins);
       	 sb.append("\nnumBuys: " + this.numBuys);
       	 sb.append("\n");     

		     return sb.toString();

		}
	   
	    protected Player clone() throws CloneNotSupportedException {
	    	Player clonePlayer=(Player) super.clone();
	    	clonePlayer.hand = new ArrayList<Card>(hand);// int hand[MAX_PLAYERS][MAX_HAND];
			clonePlayer.deck = new LinkedList<Card>(deck);// int deck[MAX_PLAYERS][MAX_DECK];
			clonePlayer.discard = new ArrayList<Card>(discard); // int discard[MAX_PLAYERS][MAX_DECK];
			clonePlayer.playedCards = new ArrayList<Card>(playedCards);
			clonePlayer.trashedCards = new ArrayList<Card>(trashedCards);
			return clonePlayer;
	    } 	   
}
