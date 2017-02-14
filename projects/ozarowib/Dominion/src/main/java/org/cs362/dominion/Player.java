package org.cs362.dominion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Player implements Cloneable
{
	List<Card> hand = new ArrayList<Card>();
	LinkedList<Card> deck = new LinkedList<Card>();
	List<Card> discard = new ArrayList<Card>();
	List<Card> playedCards = new ArrayList<Card>();
	public List<Card> trashpile = new ArrayList<Card>();
	List<Card> trashedCards = new ArrayList<Card>();
	String player_username;
	int numActions;
	int numBuys;
	int coins;
	int numGardens = 0;
	final GameState gameState;

	public Player(GameState gameState,String player_username)
	{
		this.player_username = player_username;
		this.gameState = gameState;
	}

	final Card drawCard()
	{
		if (deck.isEmpty())
		{
			System.out.println("Shuffling " + player_username + " discard pile into new deck");
			while (discard.size() > 0)
			{
				int ndx = (int) Randomness.nextRandomInt(discard.size());
				deck.add(discard.remove(ndx));
			}
		}
		Card toDraw = deck.poll();
		hand.add(toDraw);
		System.out.println(player_username + " drew " + toDraw.getCardName());
		Collections.sort(hand);
		return toDraw;
	}

	final void initializePlayerTurn()
	{
		numActions = 1;
		coins = 0;
		numBuys = 1;
		for (int i = 0; i < 5; i++)
		{
			drawCard();
		}
	}

	// Gives players starting cards without lowering Supply piles
	final boolean makeDecks(Card card)
	{
		discard.add(card);
		System.out.println(player_username + " gained " + card.getCardName());
		return true;
	}

	// Cards bought or taken from Supply
	final boolean gain(Card card)
	{
		if (gameState.gameBoard.get(Card.getCard(gameState.cards, Card.CardName.Gardens)).equals(card))
		{
			numGardens = 1;	// If player takes a Garden increase count of how many they have for game end scoring
		}
		// Add selected card to the discard pile
		discard.add(card);
		System.out.println(player_username + " gained " + card.getCardName());
		// Decrease the amount remaining in the Supply
		int pileindex = gameState.cards.indexOf(card);
		int amountleft = gameState.gameBoard.get(Card.getCard(gameState.cards, card.getCardName()));
		amountleft--;
		gameState.gameBoard.remove(pileindex);
		gameState.gameBoard.put(Card.getCard(gameState.cards, card.getCardName()), amountleft);
		return true;
	}

	// Cards discarded from hand
	public void discard(Card card)
	{
		hand.remove(card);
		discard.add(card);
		System.out.println(player_username + " discarded " + card.getCardName());
	}

	// Cards gained from Adventurer or Mine
	final boolean gainToHand(Card card)
	{
		// Add card to hand instead of discard pile
		hand.add(card);
		System.out.println(player_username + " added " + card.getCardName() + " to hand");
		// Decrease the amount remaining in the Supply
		int pileindex = gameState.cards.indexOf(card);
		int amountleft = gameState.gameBoard.get(Card.getCard(gameState.cards, card.getCardName()));
		amountleft--;
		gameState.gameBoard.remove(pileindex);
		gameState.gameBoard.put(Card.getCard(gameState.cards, card.getCardName()), amountleft);
		return true;
	}

	// Cards discarded from Adventurer
	public void discardFromDeck(Card card)
	{
		// Discard card from deck instead of hand
		deck.remove(card);
		discard.add(card);
		System.out.println(player_username + " discarded " + card.getCardName());
	}

	public void playKingdomCard()
	{
		System.out.println(" --- Action Phase:");
		int alreadysaid = 0;
		if (Card.filter(hand, Card.Type.ACTION).size()==0)
		{
			System.out.println(player_username + " has no actions to play");
			alreadysaid = 1;
		}
		else
		{
			System.out.println(player_username + " has " + numActions + " to use");
		}
		while (numActions > 0)	// For each action player has this turn
		{
			if ((Card.filter(hand, Card.Type.ACTION).size()==0) && (alreadysaid == 0))
			{
				System.out.println(player_username + " has no more actions to play");
			}
			List<Card> actionCards = Card.filter(hand, Card.Type.ACTION);
			if (actionCards.size() == 0)
				return;
			Card c = (Card)actionCards.get(0);	// Take first action card in hand
			if (c == null)
				return;
			System.out.println("Action Phase Card:" + c.toString());
			c.play(this, gameState);	// Play it
			playedCards.add(c);
			numActions -= 1;	// Mark off a turn action after successful play
			hand.remove(c);		// Remove card from hand
			discard.add(c);		// Add it to discard pile
		}
		System.out.println(player_username + " used all actions");
	}

	public void playTreasureCard()
	{
		System.out.println(" --- Treasure Phase:");
		if (Card.filter(hand, Card.Type.TREASURE).size()==0)
		{
			System.out.println(player_username + " has no treasures to play");
		}
		for (int i=hand.size(); i>0; i--)	// Loop through hand
		{
			Card c = hand.get(i-1);
			if (c.getType() == Card.Type.TREASURE)	// If card is a treasure
			{
				System.out.println("Treasure Phase Card:" + c.toString());
				c.play(this, gameState);	// Play it to get coin for buy phase
				playedCards.add(c);
				hand.remove(c);		// Remove card from hand
				discard.add(c);		// Add it to discard pile
			}
			else	// If card is not a treasure
			{
				// Ignore it
			}
		}
		System.out.println(player_username + " has " + coins + " to spend");	// Total coin for this turn
	}

	public void buyCard()
	{
		System.out.println(" --- Buy Phase:");
		System.out.println(player_username + " has " + numBuys + " to buy");
		while (numBuys > 0)	// For each buy player has this turn
		{
			if (coins >= 8)
			{
				// If there is a Province left
				if ((gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Province))!=0) &&
					(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Province))!=null))
				{
					gain(Card.getCard(gameState.cards,Card.CardName.Province));	// Buy it
					coins -= 8;
					numBuys -= 1;
					continue;	// Restart loop with new coin amount
				}
				else	// If that pile is empty
				{
						// Continue to next choice
				}
			}
			if (coins >= 7)
			{
				// If there is an Adventurer left
				if ((gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Adventurer))!=0) &&
					(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Adventurer))!=null))
				{
					gain(Card.getCard(gameState.cards,Card.CardName.Adventurer));	// Buy it
					coins -= 7;
					numBuys -= 1;
					continue;	// Restart loop with new coin amount
				}
				else	// If that pile is empty
				{
						// Continue to next choice
				}
			}
			if (coins >= 6)
			{
				// If there is a Gold left
				if ((gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Gold))!=0) &&
					(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Gold))!=null))
				{
					gain(Card.getCard(gameState.cards,Card.CardName.Gold));	// Buy it
					coins -= 6;
					numBuys -= 1;
					continue;	// Restart loop with new coin amount
				}
				else	// If that pile is empty
				{
						// Continue to next choice
				}
			}
			if (coins >= 5)
			{
				// If there is a Council Room left
				if ((gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Council_Room))!=0) &&
					(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Council_Room))!=null))
				{
					gain(Card.getCard(gameState.cards,Card.CardName.Council_Room));	// Buy it
					coins -= 5;
					numBuys -= 1;
					continue;	// Restart loop with new coin amount
				}
				else	// If that pile is empty
				{
						// Continue to next choice
				}
			}
			if (coins >= 4)
			{
				// If there is a Smithy left
				if ((gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Smithy))!=0) &&
					(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Smithy))!=null))
				{
					gain(Card.getCard(gameState.cards,Card.CardName.Smithy));	// Buy it
					coins -= 4;
					numBuys -= 1;
					continue;	// Restart loop with new coin amount
				}
				else	// If that pile is empty
				{
						// Continue to next choice
				}
			}
			if (coins >= 3)
			{
				// If there is a Village left
				if ((gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Village))!=0) &&
					(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Village))!=null))
				{
					gain(Card.getCard(gameState.cards,Card.CardName.Village));	// Buy it
					coins -= 3;
					numBuys -= 1;
					continue;	// Restart loop with new coin amount
				}
				else	// If that pile is empty
				{
						// Continue to next choice
				}
			}
			if (coins >= 2)
			{
				// If there is an Estate left
				if ((gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Estate))!=0) &&
					(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Estate))!=null))
				{
					gain(Card.getCard(gameState.cards,Card.CardName.Estate));	// Buy it
					coins -= 2;
					numBuys -= 1;
					continue;	// Restart loop with new coin amount
				}
				else	// If that pile is empty
				{
						// Continue to next choice
				}
			}
			if (coins >= 1)
			{
				// If there is a Copper left
				if ((gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Copper))!=0) &&
					(gameState.gameBoard.get(Card.getCard(gameState.cards,Card.CardName.Copper))!=null))
				{
					gain(Card.getCard(gameState.cards,Card.CardName.Copper));	// Take it
					numBuys -= 1;
					continue;	// Restart loop with new coin amount
				}
				else	// If that pile is empty
				{
					System.out.println(player_username + " had no buy options");
					break;
				}
			}
			if (coins == 0)
			{
				System.out.println(player_username + " spent all coin");
				break;
			}
		}
		System.out.println(player_username + " finished buying");
	}

	final void endTurn()
	{
		System.out.println(" --- Clean Up:");
		System.out.println(player_username + " is discarding remaining hand");
		if (hand.size() != 0)
		{
			for (int i=hand.size(); i>0; i--)
			{
				Card c = hand.get(0);
				discard(c);
			}
		}
		System.out.println(player_username + " is drawing a new hand");
		drawCard();
		drawCard();
		drawCard();
		drawCard();
		drawCard();
		numActions = 1;
		coins = 0;
		numBuys = 1;
		System.out.println(" --- --------------------------- --- ");	// Visual turn separator for log
	}

	// Game end score count
	final int scoreFor()
	{
		int score = 0;
		// Score from hand
		for (Card c : hand)
			score += c.score();
		// Score from discard
		for (Card c : discard)
			score += c.score();
		// Score from deck
		for (Card c : deck)
			score += c.score();
		// Score from trash
		for (Card c : trashpile)
			score += c.score();
		// Garden points
		if (numGardens > 0)
		{
			int totalcards = hand.size() + discard.size() + deck.size() + trashpile.size();
			int numTens = (totalcards/10);
			int bonuspoints = (numTens*numGardens);
			score += bonuspoints;
		}
		return score;
	}

	public void printStateGame()
	{
		System.out.println(" --- " + this.player_username + " --- ");
		System.out.println(" --- --------------------------- --- ");
		System.out.println(this.gameState.toString()); 
		System.out.println(" --- --------------------------- --- ");
	}

	@Override
	public String toString()
	{
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

	protected Player clone() throws CloneNotSupportedException
	{
		Player clonePlayer=(Player) super.clone();
		clonePlayer.hand = new ArrayList<Card>(hand);
		clonePlayer.deck = new LinkedList<Card>(deck);
		clonePlayer.discard = new ArrayList<Card>(discard);
		clonePlayer.playedCards = new ArrayList<Card>(playedCards);
		clonePlayer.trashedCards = new ArrayList<Card>(trashedCards);
		return clonePlayer;
	}
}
