import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class DominionBoard {

	private static HashMap<Card, Integer> supply = new HashMap<Card, Integer>();
	private static List<Card> trash = new ArrayList<Card>();
	private static List<Player> players = new ArrayList<Player>();
	
	public DominionBoard(List<Card> kingdomCards, int number)
	{
		setUpGame(kingdomCards, number);
		players = new ArrayList<Player>();
		for (int i = 0; i < number; i++)
		{
			players.add(new Player("Player" + (i + 1), i));
		}
	}
	
	public int play()
	{
		int turn = 0;
		List<Integer> actionCardIndices;
		System.out.println("");
		System.out.println("STARTING NEW GAME!");
		while(!gameOver())
		{
			System.out.println("");
			players.get(turn).newTurn();
			
			//ACTION
			while(players.get(turn).actionInHand() && players.get(turn).actionsLeft())
			{
				actionCardIndices = players.get(turn).actionCardIndices();
				if (actionCardIndices.size() > 0)
				{
					int index = Randomness.nextRandomInt(actionCardIndices.size());
					players.get(turn).playCard(actionCardIndices.get(index));
				}
			}
			
			//BUY
			players.get(turn).treasureInHand();
			while(players.get(turn).currentBuys())
			{
				if(getHighestValueCards(players.get(turn).getCoins()).size() > 0)
				{
					players.get(turn).buyCard(players.get(turn).buyRandomCard());
				}
			}
			
			//CLEAN UP
			players.get(turn).discardCardsEndTurn();
			
			
			
			//NEXT PLAYER
			turn++;
			if(turn >= players.size())
			{
				turn = 0;
			}
		}
		
		//END GAME
		finalScores();
		return 1;
	}
	
	public List<Player> getPlayers()
	{
		return players;
	}
	
	//function that adds a card to the trash pile
	public static void trashCard(Card c)
	{
		trash.add(c);
	}
	
	public static void takeCard(Card c) 
	{
		supply.put(c, supply.get(c) - 1);
	}
	
	public static void returnCard(Card c)
	{
		supply.put(c,  supply.get(c) + 1);
	}
	
	public static List<Card> kingdomCards(Card k1, Card k2, Card k3, Card k4, Card k5, Card k6, Card k7, Card k8, Card k9, Card k10)
	{
		List<Card> k = new ArrayList<Card>();
		k.add(k1);
		k.add(k2);
		k.add(k3);
		k.add(k4);
		k.add(k5);
		k.add(k6);
		k.add(k7);
		k.add(k8);
		k.add(k9);
		k.add(k10);
		return k;
	}
	
	public void setUpGame(List<Card> kingdomCards, int players)
	{
		//Setting up treasure cards
		supply.put(Card.copper, 60);
		supply.put(Card.silver, 40);
		supply.put(Card.gold, 30);

		//Setting up victory cards
		supply.put(Card.estate, 14);
		supply.put(Card.duchy, 8);
		supply.put(Card.province, 8);
		supply.put(Card.curse, 10);
		
		//Setting up kingdom cards
		for (Card c: kingdomCards)
		{
			if (c.getTypes().contains("victory"))
			{
				supply.put(c,  8);
			}
			else 
			{
				supply.put(c,  10);
			}
		}
	}
	
	public static List<Card> getHighestValueCards(int coins)
	{
		List<Card> cardsYouCanBuy = new ArrayList<Card>();
		
		for (Card c: supply.keySet())
		{
			if (supply.get(c) > 0)
			{
				if (c.getCost() <= coins)
				{
					cardsYouCanBuy.add(c);
				}
			}
		}
		
		List<Card> highestValueCards = new ArrayList<Card>();
		int maxCost = 0;
		
		for(Card c: cardsYouCanBuy)
		{
			if(c.getCost() > maxCost)
			{
				maxCost = c.getCost();
			}
		}
		
		for(Card c: cardsYouCanBuy)
		{
			if (c.getCost() == maxCost)
			{
				highestValueCards.add(c);
			}
		}
		
		return highestValueCards;
	}
	
	public static List<Card> getNonEmptySupplyPiles()
	{
		List<Card> nonEmpty = new ArrayList<Card>();
		
		for(Card c : supply.keySet())
		{
			if (supply.get(c) > 0)
			{
				nonEmpty.add(c);
			}
		}
		
		return nonEmpty;
	}
	
	//tells if a supply pile has available cards or not
	public static boolean availableCards(Card c)
	{
		return supply.get(c) > 0;
	}
	
	//makes players with a copper discard that copper
	public static void cutpurseHelper(int player)
	{
		for(int i=0; i < players.size(); i++)
		{
			if (i!=player)
			{
				if(players.get(i).hasCard(Card.copper))
				{
					System.out.println(players.get(i).getName() + " discarded copper. ");
					players.get(i).discard(Card.copper);
				}
				else
				{
					System.out.println(players.get(i).getName() + " had no coppers to discard. ");
				}
			}
		}
	}
	
	
	//for the council room action card, all other players draw a card
	public static void councilRoomHelper(int player)
	{
		for (int i = 0; i < players.size(); i++)
		{
			if (i != player)
			{
				players.get(i).drawCardFromDeck();
			}
		}
	}
	
	public static void seaHagHelper(int player)
	{
		for(int i = 0; i < players.size(); i++)
		{
			if (i != player)
			{
				Card card = players.get(i).drawCardFromDeck();
				players.get(i).discard(card);
				players.get(i).gainCardToTopOfDeck(Card.curse);
			}
		}
	}
	
	public static void ambassadorHelper(int player, Card c)
	{
		for (int i = 0; i < players.size(); i++)
		{
			if (i != player)
			{
				if (availableCards(c))
				{
					players.get(i).gainCard(c);
				}
			}
		}
	}

	//Game ends if 3 piles are empty or province pile is empty
	public boolean gameOver()
	{
		boolean gameover = false;
		if (supply.get(Card.province)==0)
		{
			System.out.println("GAME OVER! Province card pile is empty. ");
			gameover = true;
		}
		else 
		{
			int empty = 0;
			for (Integer i :supply.values())
			{
				if(i == 0)
				{
					empty = empty + 1;
				}
			}
			if(empty >=3)
			{
 				System.out.println("GAME OVER! 3 supply piles are empty. ");
				gameover = true;
			}
		}
		return gameover;
	}
	
	
	//Prints final scores of each player
	private static void finalScores()
	{
		int victory;
		for (Player p: players)
		{
			victory = p.getTotalVictoryPoints();
			System.out.println(p.getName() + ": " + victory);
		}
	}
}
