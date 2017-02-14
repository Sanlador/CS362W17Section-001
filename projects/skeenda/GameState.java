package dominion_v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//Once again, this it mostly from the code given to us by Ali Aburas

public class GameState{
	public final List<Player> players = new ArrayList<Player>();
	public static List<Card> cards ;
	public static HashMap<Card, Integer> gameBoard = new HashMap<Card, Integer>();
	
	public GameState(List<Card> cards) {
		GameState.cards=cards;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void initializeGame(int selectedKindom){
		int value;
		
		//Figure out the amount of Kingdom Cards to put in
		gameBoard.put(Card.getCard(cards, Card.CardName.Adventurer), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.Bureaucrat), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.Cellar), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.Chancellor), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.Chapel), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.CouncilRoom), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.Feast), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.Festival), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.Laboratory), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.Library), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.Market), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.Smithy), 0);
		gameBoard.put(Card.getCard(cards, Card.CardName.Woodcutter), 0);
		
		
		int k = 0;
		while (k <= selectedKindom) {
			int random = (int)Randomness.random.nextInt(cards.size());
			Card tmp = cards.get(random);
			if(tmp.getType()!=Card.Type.Action)
			{
				continue;
			}
			value = GameState.gameBoard.get(tmp);
			if(gameBoard.containsKey(tmp) && value > 0){
				continue;
			}
			gameBoard.put(tmp, 10);
			k++;
		}
		gameBoard.put(Card.getCard(cards, Card.CardName.Curse), 10);
		gameBoard.put(Card.getCard(cards, Card.CardName.Province), 8);
		gameBoard.put(Card.getCard(cards, Card.CardName.Dutchy), 8);
		gameBoard.put(Card.getCard(cards, Card.CardName.Estate), 8);
		
		//Money
		gameBoard.put(Card.getCard(cards, Card.CardName.Gold), 30);
		gameBoard.put(Card.getCard(cards, Card.CardName.Silver), 40);
		gameBoard.put(Card.getCard(cards, Card.CardName.Copper), 46);
		
		for(Player player : players)
		{
			for(int i = 0; i < 7; i++)
			{
				player.gain(Card.getCard(cards, Card.CardName.Copper));	
				//player.gain(Card.getCard(cards, Card.CardName.Gold));
			}
			for(int i=0; i < 3; i++)
			{
				player.gain(Card.getCard(cards, Card.CardName.Estate));
			}
			player.numActions = 1;
			player.coins = 0;
			player.numBuys = 1;
			for(int i=0; i<5; i++)
			{
				player.drawCard();
			}
			
		}
		
	}
	
	public HashMap<Player, Integer> play()
	{
		while(!isGameOver()){
			for(Player player : players)
			{
				System.out.println("Player: " + player.playerName + " is Playing");
				System.out.println("HERE I AM \n");	
			player.playKingdomCard();
			player.playTreasureCard();
			player.buyCard();
			player.endTurn();
			player.numActions = 1;
			player.coins = 0;
			player.numBuys = 1;			
			}
		}
		return this.getWinner();
	}
	
	public boolean isGameOver(){
		if((gameBoard.get(Card.getCard(cards,Card.CardName.Province))==null) || (gameBoard.get(Card.getCard(cards, Card.CardName.Province))==0))
		{
			return true;
		}
		if((gameBoard.get(Card.getCard(cards,Card.CardName.Dutchy))==null) || (gameBoard.get(Card.getCard(cards, Card.CardName.Dutchy))==0))
		{
			return true;
		}
		if((gameBoard.get(Card.getCard(cards,Card.CardName.Estate))==null) || (gameBoard.get(Card.getCard(cards, Card.CardName.Estate))==0))
		{
			return true;
		}
		if((gameBoard.get(Card.getCard(cards,Card.CardName.Gold))==null) || (gameBoard.get(Card.getCard(cards, Card.CardName.Gold))==0))
		{
			return true;
		}
		if((gameBoard.get(Card.getCard(cards,Card.CardName.Silver))==null) || (gameBoard.get(Card.getCard(cards, Card.CardName.Silver))==0))
		{
			return true;
		}
		if((gameBoard.get(Card.getCard(cards,Card.CardName.Copper))==null) || (gameBoard.get(Card.getCard(cards, Card.CardName.Copper))==0))
		{
			return true;
		}

		return false;
	}
	
	
	public HashMap<Player, Integer> getWinner()
	{
		HashMap<Player, Integer> playerScore = new HashMap<Player, Integer>();
		for(Player p : players)
		{
			int score = p.scoreFor();
			playerScore.put(p, score);
		}
		return playerScore;
	}

}