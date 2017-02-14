package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;

public class GameState implements Cloneable
{
	public List<Player> players = new ArrayList<Player>();
	public List<Card> cards;
	public HashMap<Card, Integer> gameBoard = new HashMap<Card, Integer>();	
	int totalturns = 0;
	
	public GameState(List<Card> cards)
	{
		this.cards=cards;
	}

	public void addPlayer(Player player)
	{
		players.add(player);
	}

	public void initializeGame()
	{
		if (players.size() > 4 || players.size() < 2)
		{
			System.err.println("the number of players mus be between 2 and 4 ");
			return ;
		}
		// Initialize supply for two players
		int selectedKindom = 0;
		int Kingdom_Cards_Selected = 13;
		while (selectedKindom < Kingdom_Cards_Selected)
		{
			int random = (int)  Randomness.random.nextInt(cards.size());
			Card tmp = cards.get(random);
			if (tmp.getType()!=Card.Type.ACTION) continue;
			if (gameBoard.containsKey(tmp)) continue;
			gameBoard.put(tmp, 10);
			selectedKindom++;
		}
		// Set number of Curse cards
		gameBoard.put(Card.getCard(cards, Card.CardName.Curse), 10);
		// Set number of Victory cards
		gameBoard.put(Card.getCard(cards, Card.CardName.Province), 8);
		gameBoard.put(Card.getCard(cards, Card.CardName.Duchy), 8);
		gameBoard.put(Card.getCard(cards, Card.CardName.Estate), 8);
		// Set number of Treasure cards
		gameBoard.put(Card.getCard(cards, Card.CardName.Gold), 30);
		gameBoard.put(Card.getCard(cards, Card.CardName.Silver), 40);
		gameBoard.put(Card.getCard(cards, Card.CardName.Copper), 46);
		// Make starting decks
		for (Player player : players)
		{
			for (int i = 0; i < 7; i++)
				player.makeDecks(Card.getCard(cards, Card.CardName.Copper));
			for (int i = 0; i < 3; i++)
				player.makeDecks(Card.getCard(cards, Card.CardName.Estate));
			player.numActions = 1;
			player.coins = 0;
			player.numBuys = 1;
			for (int i = 0; i < 5; i++)
			{
				player.drawCard();
			}
		}
	}

	public HashMap<Player, Integer>  play()
	{
		int turn = 0;
		while (!isGameOver())
		{
			turn++;
			for (Player player : players)
			{
				System.out.println("Turn " + turn + ": " + player.player_username + " is playing");
				player.playKingdomCard();
				player.playTreasureCard();
				player.buyCard();
				player.endTurn();
			}
			// End game after X turns
			// if (turn==3)
			// break;
		}
		totalturns = turn;
		return this.getWinners();
	}

	public boolean isGameOver()
	{
		// If Province card pile is empty
		if ((this.gameBoard.get(Card.getCard(cards,Card.CardName.Province))==null) ||
			(this.gameBoard.get(Card.getCard(cards,Card.CardName.Province))== 0))
		{
			System.out.println("Out of Province cards.");
			return true;
		}
		// If three Supply piles are empty
		int emptySupplyPile = 0;
		for (int i : gameBoard.values())
		{
			if (i <= 0)
				emptySupplyPile++;
			if ( emptySupplyPile >= 3)
			{
				System.out.println("3 Supply piles empty.");
				return true;
			}
		}
		return false;
	}

	// Set HashMap  of each player and the score (remember ties!)
	public HashMap<Player, Integer> getWinners()
	{
		HashMap<Player, Integer> playerScore = new HashMap<Player, Integer>();
		// Get score for each player
		for (Player p : players)
		{
			int score = p.scoreFor();
			playerScore.put(p, score);
		}
		return playerScore;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (gameBoard.isEmpty())
			sb.append("The board game is empty, you need to intialize the game!");
		else
		{
			for (Player player : players)
				sb.append(" --- " + player.toString() + "\n");
			sb.append(" --- gameBoard ---\n");
			sb.append("Cards on the table:\n");
			sb.append(String.format("%-20s %s" , "Name:", "Number:") + "\n");
			Map<Card, Integer> treeMap = new TreeMap<Card, Integer>(gameBoard);
			for (Card card : treeMap.keySet())
				sb.append(String.format("%-20s %s" , "  " + card.getCardName(), "  " + treeMap.get(card)) + "\n");
		}
		return sb.toString();
	}

	private GameState(List<Card> cards, List<Player> players, HashMap<Card, Integer> gameBoard)
	{
		this.cards=cards;
		this.players=players;
		this.gameBoard=gameBoard;
	}

	public GameState clone() throws CloneNotSupportedException
	{
		List<Player> clonePlayers = new ArrayList<Player>();
		List<Card> cloneCards = new ArrayList<Card>();
		HashMap<Card, Integer> cloneGmeBoard = new HashMap<Card, Integer>();	
		for (Player player : players) 
			clonePlayers.add((Player) player.clone());
		for (Card card : cards) 
			cloneCards.add((Card) card.clone());
		for (Card card : gameBoard.keySet())
			cloneGmeBoard.put((Card) card.clone(),gameBoard.get(card));
		final GameState cloneState= new GameState(cloneCards,clonePlayers,cloneGmeBoard);
		return  cloneState;
	}

	// Game initialization for my unit tests
	// Creates the Supply piles and prepares two new decks
	// Skips drawing initial hands because cards will be manually added
	public void initializeTest()
	{
		// Initialize supply for two players
		int selectedKindom = 0;
		int Kingdom_Cards_Selected = 13;
		while (selectedKindom < Kingdom_Cards_Selected)
		{
			int random = (int)  Randomness.random.nextInt(cards.size());
			Card tmp = cards.get(random);
			if (tmp.getType()!=Card.Type.ACTION) continue;
			if (gameBoard.containsKey(tmp)) continue;
			gameBoard.put(tmp, 10);
			selectedKindom++;
		}
		// Set number of Curse cards
		gameBoard.put(Card.getCard(cards, Card.CardName.Curse), 10);
		// Set number of Victory cards
		gameBoard.put(Card.getCard(cards, Card.CardName.Province), 8);
		gameBoard.put(Card.getCard(cards, Card.CardName.Duchy), 8);
		gameBoard.put(Card.getCard(cards, Card.CardName.Estate), 8);
		// Set number of Treasure cards
		gameBoard.put(Card.getCard(cards, Card.CardName.Gold), 30);
		gameBoard.put(Card.getCard(cards, Card.CardName.Silver), 40);
		gameBoard.put(Card.getCard(cards, Card.CardName.Copper), 46);
		// Make starting decks
		for (Player player : players)
		{
			for (int i = 0; i < 7; i++)
				player.makeDecks(Card.getCard(cards, Card.CardName.Copper));
			for (int i = 0; i < 3; i++)
				player.makeDecks(Card.getCard(cards, Card.CardName.Estate));
			player.numActions = 1;
			player.coins = 0;
			player.numBuys = 1;
			System.out.println("Shuffling " + player.player_username + " discard pile into new deck");
			while (player.discard.size() > 0)
			{
				int ndx = (int) Randomness.nextRandomInt(player.discard.size());
				player.deck.add(player.discard.remove(ndx));
			}
		}
	}
}
