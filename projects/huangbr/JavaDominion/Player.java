import java.util.*;

public class Player
{
	public int playerID;
	public int victoryPoints = 0;
	int buy = 1;
	int actions = 1;
	int money = 0;	
	public Pile deck;
	public Pile hand;
	public Pile discard;

	//Player constructor
	public Player(int ID)
	{
		System.out.println("Player has been initialized.");
		playerID = ID;
		deck = new Pile();
		hand = new Pile();
		discard = new Pile();

		for(int i=0; i <= 10; i++)
		{
			if(i < 7)
			{
				deck.addCard(new Copper());
			}
			else if(i > 7)
			{
				deck.addCard(new Estate());
			}
		}
	}
	
	//Player draws a card from their deck.
	public Card drawCard()
	{
		return deck.drawCard();
	}

	public void discardHand()
	{
		while(hand.size != 0)
		{
			discard.addCard(hand.drawCard());
		}
	}

	//Player discards a card from their hand and adds that card into their discard pile.
	public void discardCard(Pile searchPile, int index)
	{
		discard.addCard(searchPile.getCard(index));
		searchPile.removeCard(index);
	}

	//Player trashes a card from their hand. Removes the card from the game.
	public void trashCard(int index)
	{
		hand.removeCard(index);
	}

	public void actionPhase(Dominion game)
	{
		Random rand = new Random();
		List<Integer> indexList = new ArrayList<Integer>();
		for(int i=0; i < hand.getSize(); i++)
		{
			if(hand.getCard(i).type == Card.cardType.Action || hand.getCard(i).type == Card.cardType.Attack)
			{
				indexList.add(i);
			}
		}
		if(indexList.size() == 0)
		{
			System.out.println("No Action cards in hand.");
			actions--;
		}
		while(actions > 0)
		{
			System.out.println("Trying to play action.");
			int randomIndex = rand.nextInt(indexList.size());
			hand.getCard(randomIndex).play(game,this);
			actions--;
			hand.revealAll();
		}
		System.out.println("Action Phase Over.");
	}

	public void buyPhase(Dominion game)
	{
		Random rand = new Random();
		money += hand.value;
		System.out.println(money);
		while(buy > 0 && money != 0)
		{
			if(money >= 8)
			{
				buyCard(game, money, "Province");
			}
			else if(money >= 6)
			{
				int n = rand.nextInt(1 + 1 - 0) + 0;
				if(n == 0)
				{
					buyCard(game, money, "Gold");
				}
				else if(n ==1)
				{
					buyCard(game, money, game.getRandomKingdomCard().getName(0));
				}
			}
			else if(money == 3)
			{
				buyCard(game, money, "Silver");
			}
			else
			{
				buyCard(game, money, "Copper");
			}
			buy--;
		}
		System.out.println("Buy phase over.");
	}

	public void buyCard(Dominion game, int coins, String name)
	{
		int cardIndex = game.getKingdomCardIndex(name);
		System.out.println("Trying to buy " + name);
		if(cardIndex > -1)
		{
			if(game.kingdomCards.get(cardIndex).getCard(0).cost > coins)
			{
				System.out.println("Not enough money to buy this card.");
			}
			else if(game.kingdomCards.size() == 0)
			{
				System.out.println("No more of this card.");
			}
			else
			{
				discard.addCard(game.kingdomCards.get(cardIndex).drawCard());
				money -= game.kingdomCards.get(cardIndex).getCard(0).cost;
				for(int j=0; j < game.kingdomCards.get(cardIndex).embargos; j++)
				{
					if(game.basicCards.get(6).size != 0)
					{
						discard.addCard(game.basicCards.get(6).drawCard());
						System.out.println("Gained a curse due to an Embargo");
					}
				}		
			}
			
		}
		else if(cardIndex == -1)
		{
			cardIndex = game.getBasicCardIndex(name);
			if(game.basicCards.get(cardIndex).getCard(0).cost > coins)
			{
				System.out.println("Not enough money to buy this card.");
			}
			else if(game.basicCards.size() == 0)
			{
				System.out.println("No more of this card.");
			}
			else
			{
				discard.addCard(game.basicCards.get(cardIndex).drawCard());
				money -= game.basicCards.get(cardIndex).getCard(0).cost;
				for(int j=0; j < game.basicCards.get(cardIndex).embargos; j++)
				{
					if(game.basicCards.get(6).size != 0)
					{
						discard.addCard(game.basicCards.get(6).drawCard());
						System.out.println("Gained a curse due to an Embargo");
					}
				}		
			}
		}
	}

	//Player takes his turn. 
	public void takeTurn(Dominion game)
	{
		reset();
		deck.shufflePile();
		for(int i=0; i < 5; i++)
		{
			if(deck.getSize() == 0)
			{
				rebuildDeck();
			}
			hand.addCard(deck.drawCard());
		}
		actionPhase(game);
		buyPhase(game);
		discardHand();
	}

	//Resets the Players buy, actions and money back to defualt.
	public void reset()
	{
		buy = 1;
		actions = 1;
		money = 0;
	}

	//Recreates the main deck. Happens only when the main deck runs out of cards.
	//The deck is recreated by using the discard pile.
	public void rebuildDeck()
	{
		for(int i=0; i < discard.getSize(); i++)
		{
			deck.addCard(discard.drawCard());
		}
		deck.shufflePile();
	}

	//Returns of the index of the card that is looked for.
	//Returns -1 if the card is not in that pile.
	public int getCardIndexbyName(Pile searchPile, String cardName)
	{
		for(int i=0; i < searchPile.getSize(); i++)
		{
			if(searchPile.getCard(i).name == cardName)
			{
				return i;
			}
		}
		return -1;
	}

	//Returns the index of a random card.
	public int getRandomCardIndex(Pile searchPile)
	{
		Random rand = new Random();
		int randomIndex = rand.nextInt(searchPile.getSize());
		return randomIndex;
	}

	//For debugging. Prints the contents of the deck.
	public void printDeck()
	{
		System.out.println(deck.cards.size());
		for(int i=0; i < deck.cards.size(); i++)
		{	
			deck.cards.get(i).print();
		}
		deck.print();
	}

	public int getPoints()
	{
		int totalPoints = hand.points + deck.points + discard.points;
		return totalPoints;
	}

}	
