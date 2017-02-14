import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;

public class Player {

	private List<Card> hand = new ArrayList<Card>();
	private LinkedList<Card> deck = new LinkedList<Card>();
	private List<Card> discard = new ArrayList<Card>();
	private List<Card> cardsPlayed = new ArrayList<Card>();
	
	private int actions;
	private int coins;
	private int buys;
	private String username;
	private int playerNumber;
	
	public Player(String Username, int playerNumber)
	{
		this.username = Username;
		this.playerNumber = playerNumber;
		makeStartingDeck();
	}
	
	public String getName()
	{
		return this.username;
	}
	
	public int getPlayerNumber()
	{
		return this.playerNumber;
	}
	
	public int getCoins()
	{
		return this.coins;
	}
	
	public List<Card> getHand()
	{
		return this.hand;
	}
	
	public List<Card> getCardsPlayed()
	{
		return this.cardsPlayed;
	}
	
	public LinkedList<Card> getDeck()
	{
		return this.deck;
	}
	
	public List<Card> getDiscard()
	{
		return this.discard;
	}
	
	public int getBuys()
	{
		return this.buys;
	}
	
	public int getActions()
	{
		return this.actions;
	}
	
	public void addActions(int x)
	{
		actions += x;
	}
	
	/*This function creates the starting deck of 7 coppers
	 * and 3 estates. */
	private void makeStartingDeck()
	{
		for (int i = 0; i < 3; i++)
		{
			DominionBoard.takeCard(Card.estate);
			discard.add(Card.estate);
		}
		for (int i = 0; i < 7; i++)
		{
			DominionBoard.takeCard(Card.copper);
			discard.add(Card.copper);
		}
	}
	
	/*Draw a card from the deck, and if it is empty,
	 * shuffle first, then draw. */
	public Card drawCardFromDeck()
	{
		/* If the deck is empty, shuffle deck. */
		if (deck.isEmpty())
		{
			System.out.println("Shuffling " + username + "'s deck.");
			while(discard.size() > 0)
			{
				int i = (int) Randomness.nextRandomInt(discard.size());
				deck.add(discard.remove(i));
			}
		}
		Card cardToDraw = deck.poll();  //Draw the top card from deck
		hand.add(cardToDraw);
		System.out.println(cardToDraw + " was drawn from the deck.");
		Collections.sort(hand);
		return cardToDraw;
	}
	
	/*This function sets up a new turn to give the default number
	 * of actions, coins, and buys, as well as to draw 5 cards. */
	public void newTurn()
	{
		actions = 1;
		coins = 0;
		buys = 1;
		
		System.out.println("Player" + (playerNumber+ 1) + " is now starting a turn.");
		for (int i = 0; i < 5; i++)
		{
			drawCardFromDeck();
		}
	}
	
	//Buys a card and discards it
	public void buyCard(Card c)
	{
		if (buys >= 1)
		{
			if (coins >= c.getCost())
			{
				for (int i = 0; i < c.getEmbargo(); i++)
				{
					if(DominionBoard.availableCards(Card.curse))
					{
						System.out.println(username + " gained curse card because of embargo token.");
						gainCard(Card.curse);
					}
				}
				System.out.println(username + " bought " + c);
				DominionBoard.takeCard(c);
				discard.add(c);
			}
		}
		buys = buys - 1;
		coins = coins - c.getCost();
	}
	
	//Gain card to discard pile
	public void gainCard(Card c)
	{
		System.out.println(username + " gained " + c);
		DominionBoard.takeCard(c);
		discard.add(c);
	}
	
	
	//Gains a card to the players hand
	public void gainCardToHand(Card c)
	{
		System.out.println(username + " gained " + c);
		DominionBoard.takeCard(c);
		hand.add(c);
	}
	
	
	//Puts a card on the top of the deck
	public void gainCardToTopOfDeck(Card c)
	{
		System.out.println(username + " gained " + c + " to top of deck.");
		DominionBoard.takeCard(c);
		deck.addFirst(c);
	}
	
	//Discard a card
	public void discard(Card c)
	{
		System.out.println(username + " discarded " + c);
		hand.remove(c);
		discard.add(c);
	}
	
	
	// Plays the action kingdom cards
	public void playCard(int handPos)
	{
		Card c = hand.get(handPos);
		cardsPlayed.add(c);
		hand.remove(handPos);
		
		System.out.println("Playing action card: " + c);
		
		if (actions >= 1)
		{
			switch (c)
			{
			case adventurer: 
				//Reveal cards from your deck until you reveal two treasure cards. 
				//Put those treasure cards into your hand and discard the other cards. 
				//If only one treasure found, stop when full deck is seen.
				int count = 0;
				Card drawncard;
				while (count < 2)
				{
					if(deck.size() == 0)
					{
						break;
					}	
					drawncard = drawCardFromDeck();
					if (drawncard.getTypes().contains("treasure"))
					{
						count += 1;
					}
					else
					{
						discard(drawncard);
					}
				}
				
				break;
			case ambassador: 
				//Reveal a card from your hand. Return up to 2 copies of it to the supply.
				//Then each other player gains a copy of that card. 
				Card revealedCard = Randomness.randomMember(hand);
				int quantity = freqOfCardInHand(revealedCard);
				int returned = Randomness.nextRandomInt(quantity);
				if(returned > 2)
				{
					returned = 2;
				}
				System.out.println(username + " returned "+ returned + " copies of " + revealedCard);
				for (int i = 0; i < returned; i++)
				{
					
					hand.remove(revealedCard);
					DominionBoard.returnCard(revealedCard);
				}
				DominionBoard.ambassadorHelper(playerNumber, revealedCard);
				break;
			case baron:
				// Plus one buy. You may discard an estate for plus four coins or gain an estate.
				buys += 1;
				if (hand.contains(Card.estate))
				{
					discard(hand.get(hand.indexOf(Card.estate)));
					coins += 4;
				}
				else 
				{
					gainCard(Card.estate);
				}
				break;
			case council_room:
				// Plus four cards. Other players draw 1 card. Plus one buy.
				for (int i = 0; i < 4; i++)
				{
					drawCardFromDeck();
				}
				DominionBoard.councilRoomHelper(playerNumber);
				break;
			case cutpurse:
				// Plus two coins. Other players discard a copper or reveal a hand with no copper. 
				coins += 2;
				DominionBoard.cutpurseHelper(playerNumber);
				break;
			case embargo:
				// Plus two coins. Trash this card and put embargo token on top of a supply pile. 
				// When a player buys a card, he gains a curse per embargo token on that pile.
				coins +=2;
				DominionBoard.trashCard(c);
				Card random = Randomness.randomMember(DominionBoard.getNonEmptySupplyPiles());
				random.addEmbargoToCard();
				System.out.println(username + " added embargo token to " + random + " supply pile.");
				break;
			case feast:
				//Trash this card. Gain a card costing up to 5 coins. 
				DominionBoard.trashCard(c);
				gainCard(Randomness.randomMember(DominionBoard.getHighestValueCards(5)));
				break;
			case great_hall:
				//Plus one card, plus one action. Worth 1 victory point at the end. 
				drawCardFromDeck();
				actions += 1;
				break;
			case mine:
				//You may trash treasure from hand and gain a treasure worth up to three coins more than it. 
				List<Integer> treasureCards = treasureCardIndices();
				if(treasureCards.size() > 0){
					int randomCardNum = Randomness.randomMember(treasureCards);
					Card randomCard = hand.get(randomCardNum);
					DominionBoard.trashCard(randomCard);
					if(randomCard == Card.copper)
					{
						gainCardToHand(Card.gold);
					}
					if(randomCard == Card.silver)
					{
						gainCardToHand(Card.gold);
					}
					if(randomCard == Card.gold)
					{
						gainCardToHand(Card.gold);
					}
				}
				break;
			case village:
				drawCardFromDeck();
				actions += 4;
				break;
			case smithy:  
				drawCardFromDeck();
				drawCardFromDeck();
				break;
			case seahag:
				DominionBoard.seaHagHelper(playerNumber);
				break;
			default:
				break;
			}
		}
		actions = actions - 1;
	}
	
	
	//Returns the indices in your hand where you have treasures
	private List<Integer> treasureCardIndices()
	{
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < hand.size(); i++)
		{
			if (hand.get(i).getTypes().contains("treasure"))
			{
				indices.add(i);
			}
		}
		return indices;
	}
	
	//This function checks if you have a specific card in your hand
	public boolean hasCard(Card c)
	{
		return hand.contains(c);
	}
	
	
	//This function checks how many of a certain card you have in your hand
	private int freqOfCardInHand(Card c)
	{
		int i = 0;
		for (Card card : hand)
		{
			if (card == c)
			{
				i = i + 1;
			}
		}
		return i;
	}
	
	//This function returns a list of all the indices of action cards in your hand
	public List<Integer> actionCardIndices()
	{
		List<Integer> actionList = new ArrayList<Integer>();
		
		for (int i = 0; i < hand.size(); i++)
		{
			if (hand.get(i).getTypes().contains("action"))
			{
				actionList.add(i);
			}
		}
		return actionList;
	}
	
	//This function checks to see if you currently have an action in your hand
	public boolean actionInHand()
	{
		boolean action = false;
		for(Card c: hand)
		{
			if(c.getTypes().contains("action"))
			{
				action = true;
				break;
			}
		}
		return action;
	}
	
	//This function checks to see how many actions you have
	public boolean actionsLeft()
	{
		return actions > 0;
	}
	
	// You had another function here that seems the same?
	
	public boolean currentBuys()
	{
		return buys > 0;
	}
	
	public Card buyRandomCard()
	{
		Card c;
		
		List<Card> cardsToBuy = DominionBoard.getHighestValueCards(coins);
		int cardIndex = Randomness.nextRandomInt(cardsToBuy.size());
		c = cardsToBuy.get(cardIndex);
		
		return c;
	}
	
	//This function discards all cards played and in your hand, which is the end of your turn
	public void discardCardsEndTurn()
	{
		for (int i = hand.size() - 1; i >=0; i--)
		{
			discard.add(hand.get(i));
			hand.remove(i);
		}
		for (int i = cardsPlayed.size() - 1; i >= 0; i--)
		{
			discard.add(cardsPlayed.get(i));
			cardsPlayed.remove(i);
		}
	}
	
	public int getTotalVictoryPoints()
	{
		int total = hand.size() + discard.size() + deck.size() + cardsPlayed.size();
		Card.gardens.setVictory(total / 10);
		int points = 0;
		for (Card c: hand)
		{
			points += c.getVictory();
		}
		for (Card c: discard)
		{
			points += c.getVictory();
		}
		for (Card c: deck)
		{
			points += c.getVictory();
		}
		for (Card c: cardsPlayed)
		{
			points += c.getVictory();
		}
		return points;
	}
	
	public void treasureInHand()
	{
		for (Card c : hand)
		{
			coins += c.getCoins();
		}
	}
	
}
