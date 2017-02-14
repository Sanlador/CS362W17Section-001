//*************************
//Name: Bradley Imai
//Project: Assignment 1
//File: Card.java
//Class: CS362
//*************************
package org.cs362.dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class Card implements Comparable<Card>, Cloneable
{
	public static enum Type
	{
		ACTION, TREASURE, VICTORY;
	}
	public static enum CardName
	{
		/** The Treasure cards */
		Gold, Silver, Copper, 
		/** The Victory cards */
		Province, Duchy, Estate, Curse,
		/** The Kingdom cards */
		Adventurer, Ambassador, Baron, Council_Room, Laboratory, Festival, Feast, Gardens, Great_Hall, Mine, Salvager, Smithy, Village;
	}
	/**
	 * @param enum CardName the name of the card (Gold, Silver, Copper)
	 * @param enum Type the type of the card (Treasure, Victory, Kingdom)
	 * @param int cost the cost of the card
	 * @param int score the score of the card, when the game is finished
	 * @param treasureValue  the treasure value of the treasure cards (copper, silver, and gold cards)
	 */
	private final Type realType;
	private final CardName cardName;
	private final int cost, score, treasureValue;

	private Card(CardName cardName, Type type, int cost, int score, int treasureValue) {
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.realType = type;
		this.cardName = cardName;
	}

	/**
	 * Returns the type
	 */
	public Type getType() {
		return realType;
	}

	/**
	 * Returns the cardName
	 */
	public CardName getCardName() {
		return cardName;
	}
	/**
	 * Returns the cost
	 */
	public int getCost() {
		return cost;
	}


	/**
	 * Returns the treasureValue
	 */
	public int getTreasureValue() {
		return treasureValue; 
	}
	public int score() { 
			return score; 
		}
	/*
	 * Here are the specfic cards that I will be implementing into the game. they are not all of the cards for Dominion but are the required ones for this assignment
	 */
	/** Card(CardName cardName, Type type, int cost, int score, int treasureValue) */
	public static List<Card> createCards() {
		List<Card> ret = new LinkedList<Card>();

		/** The Treasure cards  */
		/** cost, score, treasure value*/
		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);
		ret.add(o);
		/** The Victory cards  */ 
		o = new Card(CardName.Province,Type.VICTORY, 	8, 6, 0);
		ret.add(o);
		o = new Card(CardName.Duchy,Type.VICTORY,		5, 3, 0);
		ret.add(o);
		o = new Card(CardName.Estate,Type.VICTORY,		2, 1, 0);
		ret.add(o);
		o = new Card(CardName.Curse,Type.VICTORY,		0, -1, 0);
		ret.add(o);				
		/** The Kingdom cards */
		o = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Village,Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.Ambassador,Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.Baron,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Council_Room,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Laboratory,Type.ACTION,4,0,2);
		ret.add(o);
		o = new Card(CardName.Festival,Type.ACTION,5,0,2);
		ret.add(o);
		o = new Card(CardName.Feast,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Gardens,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Great_Hall,Type.ACTION,3,1,0);
		ret.add(o);
		o = new Card(CardName.Mine,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Salvager,Type.ACTION,4,0,0);
		ret.add(o);
		return ret;
	}
	
/*
 
Description: here I will be creating the cases for the cards 
 
*/
	
	public void play(Player player, GameState state) {
		
		switch(this.cardName)
		{
/*
 * Reveal cards from the player deck until player reveals 2 Treasure cards.
 * Put those Treasure cards into player's hand and discard the other revealed cards.
 * steps: loop through hand and find first treasure
 * 		  if found, add to players hand
 * 		  if no treasure discard the card
 *        loop through again for the second treasure card as have done before
 */
		case Adventurer:
			
			for (int k1 = player.deck.size(); k1>0; k1--)
		    {
				Card c1 = player.deck.get(k1-1);
				if (c1.getType() == Card.Type.TREASURE)	
		    	{
		    		player.AdventurerGain(c1);	
		    		break;
		    	}
				else	
				{
					player.AdventurerDiscard(c1);	
				}
			}
			for (int k2 = player.deck.size(); k2>0; k2--)	
		    {
				Card c2 = player.deck.get(k2-1);
				if (c2.getType() == Card.Type.TREASURE)	
		    	{
		    		player.AdventurerGain(c2);	
		    		break;
		    	}
				else	
				{
					player.AdventurerDiscard(c2);	
				}
			}
	    	return;
	    	
/*
 * 3 cards will be drawn from the deck
 * steps: draw (3) cards from the deck
 */	    	

		case Smithy: 
	        player.drawCard();
	        player.drawCard();
	        player.drawCard();
			return;
			
/*
 * // Draw a (1) card, and get (2) actions.
 * steps: draw a card
 * 		  get plus 2 actions
 */	    	
		case Village:
			player.drawCard();
			player.numActions += 2;
	    	System.out.println(player.player_username + " gets +2 actions");
			return;
			
/*
 * Reveal a card from your hand. Return up to 2 copies of it from your hand to the supply.
 * Then each other player gains a copy of it.
 * steps: gets first card from players hand
 *        loop through hand
 *        if another card is found remove it 
 *        remove orginal card from hand
 * 		
 */	

		case Ambassador:
			Card thecard = player.hand.get(0);	
			System.out.println(player.player_username + " reveals " + thecard.cardName);
			for (Card c : player.hand)	
		    {
				if (c.getCardName() == thecard.cardName)	
		    	{
					player.hand.remove(c);	
					int newnum = state.gameBoard.get(Card.getCard(state.cards, thecard.getCardName()));
					int thisindex = state.cards.indexOf(thecard);
					state.gameBoard.remove(thisindex);
					newnum++;
					state.gameBoard.put(Card.getCard(state.cards, thecard.getCardName()), newnum);
					System.out.println(player.player_username + " returns " + thecard.cardName + " to supply");
		    		break;
		    	}
			}
			player.hand.remove(thecard);	
			int newnum = state.gameBoard.get(Card.getCard(state.cards, thecard.getCardName()));
			int thisindex = state.cards.indexOf(thecard);
			state.gameBoard.remove(thisindex);
			newnum++;
			state.gameBoard.put(Card.getCard(state.cards, thecard.getCardName()), newnum);
			System.out.println(player.player_username + " returns " + thecard.cardName + " to supply");
    		player.gain(Card.getCard(state.cards,thecard.cardName));
			return;
/*
 * You may discard an Estate for +4. If you don't, gain an Estate.
 * steps: Loop through player's hand
 * 		  If an Estate card is found
 *        Discard it for coin
 *        If no Estate was in hand to discard get one from supply
 */
		case Baron:
			player.numBuys += 1;
			System.out.println(player.player_username + " gets +1 buy");
			int flag = 0;
			for (Card c : player.hand)	
		    {
				if (c.getCardName() == Card.CardName.Estate)	
		    	{
					player.discard(c);	
					player.coins += 4;
		    		System.out.println(player.player_username + " gets +4 coin");
		    		flag = 1;
		    		break;
		    	}
			}
			if (flag == 0)	
	    	{
				player.gain(Card.getCard(state.cards,Card.CardName.Estate));
	    	}
			return;
			
/*
 * Draw 4 cards, +1 buy. Each other player draws a card.
 * steps: draw 4 cards
 * 		  each player will draw a car
 * NOTE: this will not happen, the player who draws this card will get an extra card. bug
 */
		case Council_Room:
	        player.drawCard();
	        player.drawCard();
	        player.drawCard();
	        player.drawCard();
	        player.numBuys += 1;
	        System.out.println(player.player_username + " gets +1 buy");
    		player.drawCard();
			return;

			
/*
 * Draw 2 cards, +1 action
 * steps: Draw 2 cards
 * 		  plus one action
 */			
		case Laboratory:
	
			player.drawCard();
			player.drawCard();
			player.numActions += 1;
	        System.out.println(player.player_username + " gets +1 actions");
			return;
			
/*
 * +2 actions, +1 buy, +2 coin
 * steps: get 2 actions
 * 		  get a buy
 * 		  add 2 coins 
 */			
		case Festival:
			player.numActions += 2;
			System.out.println(player.player_username + " gets +2 actions");
			player.numBuys += 1;
			System.out.println(player.player_username + " gets +1 buy");
			player.coins += 2;
			System.out.println(player.player_username + " gets +2 coin");
			return;

/*
 * Trash this card. Gain a card costing up to 5. 
 * steps: give 5 coin for one buying round
 * 		  get card
 * 		  reset coin
 * 		  reset actions
 */					
		case Feast:
			Card feastc = Card.getCard(state.cards,Card.CardName.Feast);
			player.hand.remove(feastc);
			player.trashpile.add(feastc);
			player.trashedCards.add(feastc);
			System.out.println(player.player_username + " trashes Feast");
			int currentactions = player.numActions;	
			player.numActions = 1;
			player.coins = 5;	
			player.buyCard();	
			player.coins = 0;	
			player.numActions = currentactions;	
			return;

/*
 * Worth 1 victory point for every 10 cards in hand
 * Notes: this does not run because this action card
 * happens at the end of the game which did not get implemented 
 */	
		case Gardens:
			return;
			
/*
 * Draw 1 card, +1 action
 * steps: draw a card
 * 		  get one action 
 */
		case Great_Hall:
			player.drawCard();
			player.numActions += 1;
			System.out.println(player.player_username + " gets +1 action");
			return;

/*
 *You may trash a treasure from your hand.
 *Gain a treasure to your hand costing up to 3 more than it
 *steps: loop through hand
 *		 if treasure card is found
 *		 remove, add to trashpile, trashcard
 *       if found trade it in for the specfifed valued treasure cards
 *       if no no treasures are found print out no treasures 
 */			
		case Mine:
			int foundone = 0;
			int value = 0;
			for (Card c : player.hand)	// Loop through player's hand
		    {
				if (c.getType() == Card.Type.TREASURE)	// If a Treasure card is found
		    	{
					value = c.getCost();
					player.hand.remove(c);
					player.trashpile.add(c);
					player.trashedCards.add(c);
		    		System.out.println(player.player_username + " trashes " + c.cardName);
		    		foundone = 1;
		    		break;
		    	}
			}
			value += 3;
			if (foundone == 1)
			{
				if (value == 3)
				{
					Card c = Card.getCard(state.cards,Card.CardName.Silver);
					player.MineGain(c);
				}
				if (value == 6)
				{
					Card c = Card.getCard(state.cards,Card.CardName.Gold);
					player.MineGain(c);
				}
				if (value == 9)
				{
					Card c = Card.getCard(state.cards,Card.CardName.Gold);
					player.MineGain(c);
				}
			}
			else
			{
				System.out.println("No treasure to trash");
			}
			return;

/*
 * gets one buy. Trash a card from your hand. Player gets +coin equal to its cost.
 * steps: get one buy
 * 		  get the value of the trashed card
 * 	      and add that value to the coins 
 */
		case Salvager:
			
			player.numBuys += 1;
			System.out.println(player.player_username + " gets +1 buy");
			Card cardtotrash = player.hand.get(0);	// Use first card in player's hand
			int trashvalue = cardtotrash.getCost();
			player.hand.remove(cardtotrash);
			player.trashpile.add(cardtotrash);
			player.trashedCards.add(cardtotrash);
    		System.out.println(player.player_username + " trashes " + cardtotrash.cardName);
			player.coins += trashvalue;
			System.out.println(player.player_username + " gets +" + trashvalue + " coin");
			return;
			
			case Copper:
				player.coins += 1;
				System.out.println(player.player_username + " gets +1 coin");
				return;
			
			case Silver:
				player.coins += 2;
				System.out.println(player.player_username + " gets +2 coin");
				return;
			
			case Gold:
				player.coins += 3;
				System.out.println(player.player_username + " gets +3 coin");
				return;

			case Curse:

				return;

		default: return;
		}
	}

	public static Card getCard(List<Card> cards, CardName cardName) {
		for(int i=0; i<cards.size();i++){
			if(cards.get(i).cardName.equals(cardName))
					return 	cards.get(i);
		}
		return null;
	}

	   public static List<Card> filter(Iterable<Card> cards, Type target) {
		      List<Card> out = new ArrayList<Card>();
		      for (Card c : cards)
		         if (c.getType() == target)
		            out.add(c);
		      return out;
		   }
	// this method will print out all the constant cards ENUM
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(" \n\t" + this.getCardName());
//		sb.append("-" + this.getType() + " ");
//		sb.append("\t\t Cost: " + this.cost + " ");
//		sb.append("\t\t Score: " + this.score + " ");
//		sb.append("\tTreasure Value: " + this.treasureValue);

		return sb.toString();

	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!super.equals(obj)) return false;
		if(!(obj instanceof Card)) return false;

		return cardName.equals(((Card) obj).cardName);
	}

	@Override
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return cardName.compareTo(o.cardName);
	}
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    } 

}
