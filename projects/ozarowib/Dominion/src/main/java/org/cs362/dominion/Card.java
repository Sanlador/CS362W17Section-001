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
		// Treasure cards
		Gold, Silver, Copper, 
		// Victory cards
		Province, Duchy, Estate, Curse,
		// Kingdom cards
		Adventurer, Ambassador, Baron, Council_Room, Feast, Festival, Gardens, Great_Hall, Laboratory, Mine, Salvager, Smithy, Village;
	}
	/**
	 * @param enum CardName the name of the card
	 * @param enum Type the type of the card
	 * @param int cost the cost of the card
	 * @param int score the score of the card, when the game is finished
	 * @param treasureValue the treasure value of the treasure cards
	 */
	private final CardName cardName;
	private final Type realType;
	private final int cost, score, treasureValue;

	private Card(CardName cardName, Type type, int cost, int score, int treasureValue)
	{
		this.cardName = cardName;
		this.realType = type;
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
	}

	public CardName getCardName()
	{return cardName;}

	public Type getType()
	{return realType;}

	public int getCost()
	{return cost;}

	public int score()
	{return score;}

	public int getTreasureValue()
	{return treasureValue;}

	// Card(CardNamee, Type, cost, score, treasureValue)
	public static List<Card> createCards()
	{
		List<Card> ret = new LinkedList<Card>();

		Card o = new Card(CardName.Adventurer, Type.ACTION, 6, 0, 0);
		ret.add(o);
		o = new Card(CardName.Ambassador, Type.ACTION, 3, 0, 0);
		ret.add(o);
		o = new Card(CardName.Baron, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);
		ret.add(o);
		o = new Card(CardName.Council_Room, Type.ACTION, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Curse, Type.VICTORY, 0, -1, 0);
		ret.add(o);
		o = new Card(CardName.Duchy, Type.VICTORY, 5, 4, 0);
		ret.add(o);
		o = new Card(CardName.Estate, Type.VICTORY, 2, 1, 0);
		ret.add(o);
		o = new Card(CardName.Feast, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Festival, Type.ACTION, 5, 0, 2);
		ret.add(o);
		o = new Card(CardName.Gardens, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Great_Hall, Type.ACTION, 3, 1, 0);
		ret.add(o);
		o = new Card(CardName.Laboratory, Type.ACTION, 4, 0, 2);
		ret.add(o);
		o = new Card(CardName.Mine, Type.ACTION, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Province, Type.VICTORY, 8, 6, 0);
		ret.add(o);
		o = new Card(CardName.Salvager, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Smithy, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Village, Type.ACTION,3, 0, 0);
		ret.add(o);
		return ret;
	}

	public void play(Player player, GameState state)
	{
		switch(this.cardName)
		{

			case Adventurer:
			/** Reveal cards from the player deck until player reveals 2 Treasure cards.
			 * Put those Treasure cards into player's hand and discard the other revealed cards. */
				int treasurefound = 0;
				for (int i=player.deck.size(); i>0; i--)	// Look through deck
				{
					Card c = player.deck.get(i-1);
					if (c.getType() == Card.Type.TREASURE)	// If Treasure found
					{
						player.gainToHand(c);				// Add to hand
						treasurefound += 1;
					}
					else									// If not Treasure
					{
						player.discardFromDeck(c);			// Discard
					}
					if (treasurefound == 2)					// For max 2 treasures
					{
						break;
					}
				}
			return;

			case Ambassador:
			/** Reveal a card from your hand. Return up to 2 copies of it from your hand to the supply.
			 * Then each other player gains a copy of it. */
				Card firstcard = player.hand.get(0);	// Use first card in hand
				System.out.println(player.player_username + " revealed " + firstcard.cardName);
				for (Card c : player.hand)
				{
					if (c.getCardName() == firstcard.cardName)	// If match is found in hand
					{
						// Remove card from hand
						player.hand.remove(c);					
						// Increase the amount remaining in the Supply
						int pileindex = state.cards.indexOf(firstcard);
						int amountleft = state.gameBoard.get(Card.getCard(state.cards, firstcard.getCardName()));
						amountleft--;
						state.gameBoard.remove(pileindex);
						state.gameBoard.put(Card.getCard(state.cards, firstcard.getCardName()), amountleft);
						System.out.println(player.player_username + " returned " + firstcard.cardName + " to supply");
						break;
					}
				}
				// Remove first card from hand and add to Supply whether match was found or not
				player.hand.remove(firstcard);
				int pileindex = state.cards.indexOf(firstcard);
				int amountleft = state.gameBoard.get(Card.getCard(state.cards, firstcard.getCardName()));
				amountleft--;
				state.gameBoard.remove(pileindex);
				state.gameBoard.put(Card.getCard(state.cards, firstcard.getCardName()), amountleft);
				System.out.println(player.player_username + " returned " + firstcard.cardName + " to supply");
				// Other player gets one of the card
				player.gain(Card.getCard(state.cards,firstcard.cardName));
			return;

			case Baron:
			/** +1 buy; You may discard an Estate for +4. If you don't, gain an Estate. */
				player.numBuys += 1;
				System.out.println(player.player_username + " got +1 buy");
				int estatefound = 0;
				for (Card c : player.hand)							// Look through hand
				{
					if (c.getCardName() == Card.CardName.Estate)	// If Estate found
					{
						player.discard(c);							// Discard
						player.coins += 4;							// Get coin
						System.out.println(player.player_username + " got +4 coin");
						estatefound = 1;
						break;
					}
				}
				if (estatefound == 0)	// If no Estate in hand take one from Supply
				{
					player.gain(Card.getCard(state.cards,Card.CardName.Estate));
				}
			return;

			case Copper:
			/** +1 coin */
				player.coins += 1;
				System.out.println(player.player_username + " got +1 coin");
			return;

			case Council_Room:
			/** Draw 4 cards, +1 buy. Each other player draws a card. */
				player.drawCard();
				player.drawCard();
				player.drawCard();
				player.drawCard();
				player.numBuys += 1;
				System.out.println(player.player_username + " got +1 buy");
				// Other players draw a card
				player.drawCard();
			return;

			case Curse:
			/** -1 Victory */
				// No play action, affects game end scoring
			return;

			case Feast:
			/** Trash this card. Gain a card costing up to 5. */
				Card trashthis = Card.getCard(state.cards,Card.CardName.Feast);
				player.hand.remove(trashthis);
				player.trashpile.add(trashthis);
				player.trashedCards.add(trashthis);
				System.out.println(player.player_username + " trashed Feast");
				// Save current turn state
				int actions = player.numActions;
				// Play one buy phase with 5 free coin
				player.numActions = 1;
				player.coins = 5;
				player.buyCard();
				// Restore original state to continue turn
				player.coins = 0;
				player.numActions = actions;
			return;

			case Festival:
			/** +2 actions, +1 buy, +2 coin */
				player.numActions += 2;
				System.out.println(player.player_username + " got +2 actions");
				player.numBuys += 1;
				System.out.println(player.player_username + " got +1 buy");
				player.coins += 2;
				System.out.println(player.player_username + " got +2 coin");
			return;

			case Gardens:
			/** Worth 1 victory point for every 10 cards in your deck (rounded down) */
				// No play action, affects game end scoring
			return;

			case Gold:
			/** +3 coin */
				player.coins += 3;
				System.out.println(player.player_username + " got +3 coin");
			return;

			case Great_Hall:
			/** Draw 1 card, +1 action */
				player.drawCard();
				player.numActions += 1;
				System.out.println(player.player_username + " got +1 action");
			return;

			case Laboratory:
			/** Draw 2 cards, +1 action */
				player.drawCard();
				player.drawCard();
				player.numActions += 1;
				System.out.println(player.player_username + " got +1 action");
			return;

			case Mine:
			/** You may trash a treasure from your hand.
			 * Gain a treasure to your hand costing up to 3 more than it. */
				int cardtrashed = 0;
				int gaincost = 0;
				for (Card c : player.hand)	// Look through hand
				{
					if (c.getType() == Card.Type.TREASURE)	// If Treasure found
					{
						// Note value to be gained
						gaincost = (c.getCost()+3);
						// Trash the card
						player.hand.remove(c);
						player.trashpile.add(c);
						player.trashedCards.add(c);
						System.out.println(player.player_username + " trashed " + c.cardName);
						cardtrashed = 1;
						break;
					}
				}
				if (cardtrashed == 1)
				{
					switch(gaincost)
					{
						case 3:	// Trashed Copper, gain Silver
							Card c = Card.getCard(state.cards,Card.CardName.Silver);
							player.gainToHand(c);
						return;

						case 6:	// Trashed Silver, gain Gold
							c = Card.getCard(state.cards,Card.CardName.Gold);
							player.gainToHand(c);
						return;

						case 9:	// Trashed Gold, gain Gold
							c = Card.getCard(state.cards,Card.CardName.Gold);
							player.gainToHand(c);
						return;
					}
				}
				else
				{
					System.out.println(player.player_username + " had no treasures to trash");
				}
			return;

			case Salvager:
			/** +1 buy; Trash a card from your hand. Get +coin equal to its cost. */
				player.numBuys += 1;
				System.out.println(player.player_username + " got +1 buy");
				// Trash first card in hand
				trashthis = player.hand.get(0);
				int trashedcost = trashthis.getCost();
				player.hand.remove(trashthis);
				player.trashpile.add(trashthis);
				player.trashedCards.add(trashthis);
				System.out.println(player.player_username + " trashed " + trashthis.cardName);
				// Get coin 
				player.coins += trashedcost;
				System.out.println(player.player_username + " got +" + trashedcost + " coin");
			return;

			case Silver:
			/** +2 coin */
				player.coins += 2;
				System.out.println(player.player_username + " got +2 coin");
			return;

			case Smithy:
			/** Draw 3 cards */
				player.drawCard();
				player.drawCard();
				player.drawCard();
			return;

			case Village:
			/** Draw 1 card, +2 actions */
				player.drawCard();
				player.numActions += 2;
				System.out.println(player.player_username + " got +2 actions");
			return;

			default: return;
		}
	}

	public static Card getCard(List<Card> cards, CardName cardName)
	{
		for (int i=0; i<cards.size();i++)
		{
			if (cards.get(i).cardName.equals(cardName))
				return 	cards.get(i);
		}
		return null;
	}

	public static List<Card> filter(Iterable<Card> cards, Type target)
	{
		List<Card> out = new ArrayList<Card>();
		for (Card c : cards)
			if (c.getType() == target)
				out.add(c);
		return out;
	}

	// This method will print out all the constant cards ENUM
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(" \n\t" + this.getCardName());
		// sb.append("-" + this.getType() + " ");
		// sb.append("\t\t Cost: " + this.cost + " ");
		// sb.append("\t\t Score: " + this.score + " ");
		// sb.append("\tTreasure Value: " + this.treasureValue);
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (!(obj instanceof Card)) return false;
		return cardName.equals(((Card) obj).cardName);
	}

	@Override
	public int compareTo(Card o)
	{
		// TODO Auto-generated method stub
		return cardName.compareTo(o.cardName);
	}

	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
