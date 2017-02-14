package Dominion;
import java.util.*;

public class Player 
{
	public int actions, turnMoney, purchases, playerNum, vPoints;
	public List<Card> 
			hand = new ArrayList(), 
			discard = new ArrayList(), 
			deck = new ArrayList();
	
	public List<Player> opponents = new ArrayList();
	
	public static void readCards(List<Card> c)
	{
		for (int i = 0; i < c.size(); i++)
		{
			System.out.println(c.get(i).getName());
		}
	}
	
	//determines the amount of treasure in a player's hand at the start of a turn
	protected int getMoney()
	{
		int wealth = 0;
		for (int i = 0; i < hand.size(); i++)
		{
			if (hand.get(i).getName() == "Copper")
			{
				wealth += 1;
			}
			if (hand.get(i).getName() == "Silver")
			{
				wealth += 2;
			}
			if (hand.get(i).getName() == "Gold")
			{
				wealth += 3;
			}
		}

		return wealth;
	}
	
	
	public void turn(Board b, String state)
	{	
		System.out.println("Player " + String.format("%d", playerNum) + "'s turn");
		System.out.println("Player " + String.format("%d", playerNum) + "'s hand:");
		readCards(hand);
		actions = 1;
		purchases = 1;
		List<String> a;

		Random r = new Random();
		int rn;
		
	//action phase
		//check which cards are able to be played, and display those
		while (actions > 0)
		{
			state = "Player " + String.format("%d", playerNum) + "action phase";
			//must have k be set to Null at the beginning of the loop
			List<String> k = new ArrayList();
			for (int i = 0; i < hand.size(); i++)
			{
				if (hand.get(i).getType() == "Kingdom")
					k.add(hand.get(i).getName());
			}
			
			rn = r.nextInt(k.size() + 1);
			
			if (rn < k.size())
			{
			    System.out.println("Player " + String.format("%d", playerNum) + " plays " + k.get(rn));
				playK(k.get(rn), b);
			}
			else
			{
				System.out.println("Player " + String.format("%d", playerNum) + " passes their action phase");
				actions = 0;
			}
			
			actions--;
		}
		
			
			//accept input to play available cards until the player exhausts actions
			// or passes to the buy phase
			
		//buy phase
			//check and display which cards can be bought with current wealth
	
		turnMoney = getMoney();
		while (purchases > 0)
		{
			state = "Player " + String.format("%d", playerNum) + "buy phase";
			a = b.available(turnMoney);
			for (int i = 0; i < a.size(); i++)
			{
				//System.out.println(a.get(i));
			}
			//accept input to buy cards until the player exhaust their buys or passes
			rn = r.nextInt(a.size() + 1);
			if (rn < a.size())
			{
				System.out.println("Player " + String.format("%d", playerNum) + " buys " + a.get(rn));
				b.buy(turnMoney, a.get(rn), hand);
				purchases--;
			}
			else
			{
				System.out.println("Player " + String.format("%d", playerNum) + " passes their buy phase");
				purchases = 0;
			}
			//forgot to set passing as setting purchases to 0
		}
			
		//cleanup phase
			//discards the player's hand and draws a new one
			state = "Player " + String.format("%d", playerNum) + "cleanup phase";
			discardAll(hand);
			drawDeck(5);
			System.out.println("End of Player " + String.format("%d", playerNum) +"'s turn");
			
			
	}

	
	/*
	 * Components include:
	 * - A hand
	 * - Discard
	 * - A Deck
	 * 
	 * Player class will contain a set of methods which play turns;
	 * 
	 * A turn plays as follows:
	 * - Action:
	 * 	Player may play any action card(s) in their hand, and their effect is applied 
	 *  (card is then added to the discard)
	 * - Buy:
	 * 	Player is allowed to buy any card in the supply which has a lower cost than their current treasure
	 * 
	 * - Cleanup:
	 *  Places all cards in hand into discard and redraw
	 *  
	 *  If at any point the count for the player's deck drops to zero,
	 *  shuffle the discard and it becomes the new deck
	 * 
	 */

	public List<Card> shuffle(List<Card> c)
	{
		List<Card> f = new ArrayList();
		int csize = c.size();
		
		for (int i = 0; i < csize; i++)
		{
			Random r = new Random();
			int rn;
			rn = r.nextInt(c.size());
			f.add(c.get(rn));
			c.remove(rn);
		}
		
		return f;
	}
	
	public Player(int num)
	{
		playerNum = num;
		for (int i = 0; i < 7; i++)
		{
			deck.add(new Card("Copper"));
		}
		for (int i = 0; i < 3; i++)
		{
			deck.add(new Card("Estate"));
		}

		deck = shuffle(deck);
		drawDeck(5);
		
		vPoints = 0;
	}
	
	
	//draws from deck
	public void drawDeck(int n)
	{
		//check if deck has enough cards
		if (deck.size() < n)
		{
			deck.addAll(shuffle(discard));
		}
		Card.draw(deck, hand, n);
	}
	
	public void discard(Card c)
	{
			Card.move(c, hand, discard);
	}
	
	public void discardAll(List<Card> c)
	{
		int handSize = c.size();
		for (int i = 0; i < handSize; i++)
			discard(c.get(0));
	}


	public void gain(Card c, List<Card> s)
	{
		Card r;
		if (Card.search(c, s) == true)
		{
			boolean b = false;
			int i = 0;
			while(b == false)
			{
				if (s.get(i).getName() == c.getName())
					b = true;
				else
					i++;
			}
			Card.move(s.get(i), s, hand);
		}
	}
	
	public void trash(Card c)
	{
		int index = -1;
		for (int i = 0; i < hand.size(); i++)
		{
			if (c.getName() == hand.get(i).getName())
			{
				index = i;
			}
		}
		if (index != -1)
			hand.remove(index);
			
	}
	
	//checks string k for card name, and plays the corresponding card
	//set to public for test purposes
	public void playK(String k, Board b)
	{
		int cost = 0;
		
		/*Reveal cards from deck until 2 treasure cards are revealed, put them
		 * in your hand and discard all others*/
		if (k == "Adventurer")
		{
			cost = 6;
			discard(new Card(k, cost));
			List<Card> l = new ArrayList();
			int t = 0;
			while (t < 2)
			{
				System.out.println("Player " + " drew a " + deck.get(0).getName());
				if (deck.get(0).getType() == "Treasure")
				{
					System.out.println("Adding " + deck.get(0).getName());
					drawDeck(1);
					t++;
				}
				else
				{
					discard.add(deck.get(0));
					deck.remove(0);
				}
			}
		}
		
		/*	Reveal a card from your hand. Return up to 2 copies of it from your hand to 4
		 * the Supply. Then each other player gains a copy of it.
		 */
		if (k == "Ambassador")
		{
			cost = 3;
			discard(new Card(k, cost));
			Random r = new Random();
			int rn;
			rn = r.nextInt(hand.size());
			String cardName = hand.get(rn).getName();
			
			System.out.println("Player " + String.format("%d", playerNum) + " Reveals " + hand.get(rn).getName());
			
			int cardCount = 0;
			for (int i = 0; i < hand.size(); i++)
			{
				if (hand.get(i).getName() == cardName)
					cardCount++;
			}
			
			if (cardCount > 1)
			{
				rn = r.nextInt(3);
				for (int i = 0; i < rn; i++)
				{
					Card.move(new Card(cardName, cost), hand, b.supply);
				}
			}
			else
				Card.move(new Card(cardName), hand, b.supply);
			
			for (int i = 0; i < opponents.size(); i++)
				opponents.get(i).gain(new Card(cardName), b.supply);
			
			
		}
		
		/*	+1 Buy, You may discard an Estate card. If you do, +4 coins. 
		 * Otherwise, gain an Estate card.
		 */
		
		if (k == "Baron")
		{
			cost = 4;
			discard(new Card(k, cost));
			purchases++;
			Random r = new Random();
			int rn;
			rn = r.nextInt(2);
			if (Card.search(new Card("Estate", 4), hand) == true)
			{
				System.out.println("Player " + String.format("%d", playerNum) + " discards an estate");
				boolean bo = false;
				int i = 0;
				while(bo == false && i < hand.size())
				{
					if (hand.get(i).getName() == "Estate")
						bo = true;
					else
						i++;
				}
				discard(hand.get(i));
				turnMoney += 4;
			}
			else if(Card.search(new Card("Estate"), b.supply) == true)
			{
				//gain estate from supply
				System.out.println("Player " + String.format("%d", playerNum) + " gains an estate");
				hand.add(new Card("Estate", 3));
			}
		}
		
		/*+ 4 cards, + 1 buy, each other player draws a card*/
		
		if (k == "Council Room")
		{
			cost = 4;
			discard(new Card(k, cost));
			drawDeck(4);
			purchases++;
			for (int i = 1; i < opponents.size(); i++)
			{
				opponents.get(i).drawDeck(1);
			}
		}
		
		if (k == "Cutpurse")
		{
			cost = 4;
			discard(new Card(k, cost));
			turnMoney += 2;
			for (int i = 0; i < opponents.size(); i++)
			{
				if (Card.search(new Card("Copper"), opponents.get(i).hand) == true)
				{
					opponents.get(i).discard(new Card("Copper"));
				}
				else
				{
					System.out.println("Player " + String.format("%d", opponents.get(i).playerNum) +  " reveals hand:");
					readCards(opponents.get(i).hand);
				}
			}
		}
		
		
		/*Place an embargo token on a supply pile. Whenever
		 * a card is bought from that pile, the player gets as many curse 
		 * cards as embargo tokens*/
		
		if (k == "Embargo")
		{
			trash(new Card("Embargo"));
			Random r = new Random();
			int rn;
			rn = r.nextInt(16);
			b.em[rn]++;
		}
		
		/*Trash this card; gain a card costing up to 5*/
		
		if (k == "Feast")
		{
			trash(new Card("Feast"));
			List<Card> l = new ArrayList();
			for (int i = 0; i < b.supply.size(); i++)
			{
				if (b.supply.get(i).getCost() < 5 && !l.contains(b.supply.get(i).getName()))
				{
					l.add(b.supply.get(i));
				}
			}
			Random r = new Random();
			int rn;
			rn = r.nextInt(l.size());
			if (l.get(rn).getType() == "Kingdom")
			{
				gain(new Card(l.get(rn).getName(), l.get(rn).getCost()), b.supply);
			}
			else
			{
				gain(new Card(l.get(rn).getName()), b.supply);
			}
		}
		
		if (k == "Great Hall")
		{
			//readCards(hand);
			cost = 3;
			discard(new Card(k, 3));
			drawDeck(1);
			actions++;
		}
		
		/*Trash a treasure card; gain a treasure card up to 3 
		 * more in cost than the one trashed
		 */
		
		if (k == "Mine")
		{
			cost = 5;
			discard(new Card(k, cost));
			List<String> l = new ArrayList();
			for (int i = 0; i < hand.size(); i++)
			{
				if (hand.get(i).getType() == "Treasure")
					l.add(hand.get(i).getName());
			}
			
			//must account for card requirements
			if (l.isEmpty() == true)
			{
				System.out.println("But they could not play it as they do not have any treasure cards");
				actions++;
				return;
			}
			
			Random r = new Random();
			int rn;
			rn = r.nextInt(l.size());
			if (l.get(rn) == "Copper")
			{
				rn = r.nextInt(2);
				if (rn == 1)
					gain(new Card("Silver"), b.supply);
				else 
					gain(new Card("Copper"), b.supply);
			}
			else if (l.get(rn) == "Silver" || l.get(rn) == "Gold")
			{
				rn = r.nextInt(3);
				if (rn == 2)
					gain(new Card("Gold"), b.supply);
				else if (rn == 1)
					gain(new Card("Silver"), b.supply);
				else 
					gain(new Card("Copper"), b.supply);
			}
			
		}
		
		/*Choose one: +2 money or all players with more than 
		 * 5 cards discard and draw 4
		 */
		
		if (k == "Minion")
		{
			cost = 5;
			discard(new Card(k, cost));
			Random r = new Random();
			int rn;
			rn = r.nextInt(2);
			if (rn > 0)
			{
				turnMoney += 2;
			}
			else
			{
				discardAll(hand);
				drawDeck(4);
				for (int i = 0; i < opponents.size(); i++)
				{
					if (opponents.get(i).hand.size() >= 5)
					{
						opponents.get(i).discardAll(opponents.get(i).hand);
						opponents.get(i).drawDeck(4);
					}
				}
			}
		}
		
		if (k == "Smithy")
		{
			cost = 4;
			discard(new Card(k, cost));
			drawDeck(3);
		}
		
		/*Either draw two, trash two or plus 2 money*/
		
		if (k == "Steward")
		{
			cost = 3;
			discard(new Card(k, cost));
			Random r = new Random();
			int rn;
			rn = r.nextInt(3);
			if (rn == 0)
			{
				drawDeck(2);
			}
			else if (rn == 1)
			{
				turnMoney += 2;
			}
			else
			{
				for (int i = 0; i < 2; i++)
				{
					rn = r.nextInt(hand.size());
					hand.remove(rn);
				}
			}
		}
		
	}
}
