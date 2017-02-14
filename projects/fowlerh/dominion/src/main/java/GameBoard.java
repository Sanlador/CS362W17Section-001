import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class GameBoard {
	
	private static HashMap<Card, HashMap<String, Integer>> supplyPiles = new HashMap<Card, HashMap<String, Integer>>();
	
	private static List<Card> trashPile = new ArrayList<Card>();
	
	private static List<Player> players;
	
	public static void takeCard(Card card)
	{
		supplyPiles.get(card).put("Supply", supplyPiles.get(card).get("Supply") - 1);
	}
	
	public static void returnCard(Card card)
	{
		supplyPiles.get(card).put("Supply", supplyPiles.get(card).get("Supply") + 1);
	}
	
	public static void trashCard(Card card)
	{
		trashPile.add(card);
	}
	
	public List<Card> getTrash()
	{
		return trashPile;
	}
	
	public static boolean placeEmbargo(Card card)
	{
		if (supplyPiles.get(card).get("Supply") > 0)
		{
			supplyPiles.get(card).put("Embargo", supplyPiles.get(card).get("Embargo") + 1);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int play()
	{
		int playerTurn = 0;
		List<Integer> actionCardIndices;
		while (!gameDone())
		{
			//Action Phase
			while (players.get(playerTurn).hasActions() && players.get(playerTurn).hasActionCard())
			{
				actionCardIndices = players.get(playerTurn).getActionCardIndices();
				if (actionCardIndices.size() > 0)
				{
					int indexToPlay = Randomness.nextRandomInt(actionCardIndices.size());
					players.get(playerTurn).playCard(actionCardIndices.get(indexToPlay));
				}
			}
			
			//Buy Phase
			players.get(playerTurn).countTreasure();
			while (players.get(playerTurn).hasBuys())
			{
				if (getPurchaseableCards(players.get(playerTurn).getCoins()).size() > 0)
				{
					players.get(playerTurn).buyCard(players.get(playerTurn).getRandomBuyCard());
				}
			}
			
			//Clean-up Phase
			players.get(playerTurn).discardHandAndInPlay();
			players.get(playerTurn).initPlayerTurn();
			
			//Move to next player
			playerTurn ++;
			if (playerTurn >= players.size())
			{
				playerTurn = 0;
			}
			
		}
		
		displayFinalScores();
		return 1;
		
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
	
	public GameBoard(List<Card> KCards, int numPlayers)
	{
		initializeSupply(KCards, numPlayers);
		players = new ArrayList<Player>();
		for (int i = 0; i < numPlayers; i++)
		{
			players.add(new Player("Player " + (i+1), i));
		}
	}
	
	public static List<Player> getPlayers()
	{
		return players;
	}
	
	private void initializeSupply(List<Card> KCards, int numPlayers)
	{
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		
		
		//Kingdom cards
		for (Card c : KCards)
		{
			if (c.getTypes().contains("victory"))
			{
				if (numPlayers == 2)
				{
					hm = new HashMap<String, Integer>();
					hm.put("Supply", 8);
					hm.put("Embargo", 0);
					supplyPiles.put(c, hm);
				}
				else
				{
					hm = new HashMap<String, Integer>();
					hm.put("Supply", 12);
					hm.put("Embargo", 0);
					supplyPiles.put(c, hm);
				}
			}
			else
			{
				hm = new HashMap<String, Integer>();
				hm.put("Supply", 10);
				hm.put("Embargo", 0);
				supplyPiles.put(c, hm);
			}
		}
		
		//Treasure cards
		
		hm = new HashMap<String, Integer>();
		hm.put("Supply", 60);
		hm.put("Embargo", 0);
		supplyPiles.put(Card.copper, hm);
		hm = new HashMap<String, Integer>();
		hm.put("Supply", 40);
		hm.put("Embargo", 0);
		supplyPiles.put(Card.silver, hm);
		hm = new HashMap<String, Integer>();
		hm.put("Supply", 30);
		hm.put("Embargo", 0);
		supplyPiles.put(Card.gold, hm);
		
		//Victory cards
		if (numPlayers == 2)
		{
			hm = new HashMap<String, Integer>();
			hm.put("Supply", 8 + (numPlayers * 3));
			hm.put("Embargo", 0);
			supplyPiles.put(Card.estate, hm);
			hm = new HashMap<String, Integer>();
			hm.put("Supply", 8);
			hm.put("Embargo", 0);
			supplyPiles.put(Card.duchy, hm);
			hm = new HashMap<String, Integer>();
			hm.put("Supply", 8);
			hm.put("Embargo", 0);
			supplyPiles.put(Card.province, hm);
			hm = new HashMap<String, Integer>();
			hm.put("Supply", 10);
			hm.put("Embargo", 0);
			supplyPiles.put(Card.curse, hm);
		}
		else
		{
			hm = new HashMap<String, Integer>();
			hm.put("Supply", 12 + (3 * numPlayers));
			hm.put("Embargo", 0);
			supplyPiles.put(Card.estate, hm);
			hm = new HashMap<String, Integer>();
			hm.put("Supply", 12);
			hm.put("Embargo", 0);
			supplyPiles.put(Card.duchy, hm);
			hm = new HashMap<String, Integer>();
			hm.put("Supply", 12);
			hm.put("Embargo", 0);
			supplyPiles.put(Card.province, hm);
			if (numPlayers == 3)
			{
				hm = new HashMap<String, Integer>();
				hm.put("Supply", 20);
				hm.put("Embargo", 0);
				supplyPiles.put(Card.curse, hm);
			}
			else
			{
				hm = new HashMap<String, Integer>();
				hm.put("Supply", 30);
				hm.put("Embargo", 0);
				supplyPiles.put(Card.curse, hm);
			}
		}
	}
	
	public static List<Card> getPurchaseableCards(int coins)
	{
		List<Card> l = new ArrayList<Card>();
		
		for (Card c : supplyPiles.keySet())
		{
			if (supplyPiles.get(c).get("Supply") > 0)
			{
				if (c.getCost() <= coins)
				{
					l.add(c);
				}
			}
		}
		
		return l;
	}
	
	public static List<Card> getPurchaseableCards(int coins, String type)
	{
		List<Card> l = new ArrayList<Card>();
		
		for (Card c : supplyPiles.keySet())
		{
			if (c.getTypes().contains(type))
			{
				if (supplyPiles.get(c).get("Supply") > 0)
				{
					if (c.getCost() <= coins)
					{
						l.add(c);
					}
				}
			}
		}
		
		return l;
	}
	
	public static List<Card> getActiveSupplyPiles()
	{
		List<Card> l = new ArrayList<Card>();
		
		for (Card c : supplyPiles.keySet())
		{
			if (supplyPiles.get(c).get("Supply") > 0)
			{
				l.add(c);
			}
		}
		
		return l;
	}
	
	public static boolean hasCardsAvailable(Card c)
	{
		return (supplyPiles.get(c).get("Supply") > 0);
		
	}
	
	public static int numCardsAvailable(Card c)
	{
		return supplyPiles.get(c).get("Supply");
		
	}
	
	public static int numEmbargoTokens(Card card)
	{
		return supplyPiles.get(card).get("Embargo");
	}
	
	public static int getTotalEmbargoTokens()
	{
		int total = 0;
		for (HashMap<String,Integer> s : supplyPiles.values())
		{
			total +=  s.get("Embargo");
		}
		return total;
	}
	
	public static void opponentDiscardCopper(int playerNumber)
	{
		for(int i = 0; i < players.size(); i++)
		{
			if (i != playerNumber)
			{
				if (players.get(i).hasCard(Card.copper))
				{
					System.out.println(players.get(i).getName() +  " discarded copper." );
					players.get(i).Discard(Card.copper);
				}
				else
				{
					System.out.println(players.get(i).getName() + " had no coppers to discard.");

				}
			}
		}
	}
	
	public static void opponentDraw(int playerNumber)
	{
		for(int i = 0; i < players.size(); i++)
		{
			if (i != playerNumber)
			{
				players.get(i).drawCard();
			}
		}
	}
	
	public static void opponentGainCard(int playerNumber, Card c)
	{
		for(int i = 0; i < players.size(); i++)
		{
			if (i != playerNumber)
			{
				players.get(i).gainCard(c);
			}
		}
	}
	
	public boolean gameDone()
	{
		boolean done = false;
		
		if (supplyPiles.get(Card.province).get("Supply") == 0)
		{
			System.out.println("Game Over. Ran out of province cards.");
			done = true;
		}
		else
		{
			int emptyPiles = 0;
			for (HashMap<String, Integer> hm : supplyPiles.values())
			{
				if (hm.get("Supply") == 0)
				{
					emptyPiles ++;
				}
			}
			if (emptyPiles >= 3)
			{
				System.out.println("Game Over. Ran out of 3 supply piles.");
				done = true;
			}
		}
		
		return done;
	}
	
	private static void displayFinalScores()
	{
		int victoryPoints;
		for (Player p : players)
		{
			victoryPoints = p.getTotalVictoryPoints();
			System.out.println(p.getName() + ": " + victoryPoints);
		}
	}
	
}
 