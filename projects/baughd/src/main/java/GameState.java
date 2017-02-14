import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.Random;

public class GameState{
    List<Player> players = new ArrayList<Player>();
    List<Card> cards;
	HashMap<Card, Integer> gameBoard = new HashMap<Card, Integer>();
	List<Card> embargoTokens = new ArrayList<Card>();
	private Random gen = new Random();

	GameState(List<Card> cards) {
		   this.cards = cards;
	   }

	void addPlayer(Player player) {
		      players.add(player);
	   }
	   
	void initializeGame(){

//CHECK NUMBER OF PLAYERS
        if (players.size() > 4 || players.size() < 2) {
		    System.err.println("the number of players must be between 2 and 4 ");
			return ;
        }
//INITIALIZE SUPPLY
		int selectedKingdom = 0;
		int Kingdom_Cards_Selected = 10;

//INITIALIZE ACTION CARDS
        while (selectedKingdom < Kingdom_Cards_Selected) {
		    int random = gen.nextInt(cards.size());//
			Card tmp = cards.get(random);
			if(tmp.getType() != Card.Type.ACTION) continue;
			if(gameBoard.containsKey(tmp)) continue;
			gameBoard.put(tmp, 10);
			selectedKingdom++;
        }

    //set number of Curse cards the default number of players is 2
		gameBoard.put(Card.getCard(cards, Card.CardName.Curse), 10);
		      
//INITIALIZE VICTORY CARDS
		gameBoard.put(Card.getCard(cards, Card.CardName.Province), 8);
		gameBoard.put(Card.getCard(cards, Card.CardName.Duchy), 8);
		gameBoard.put(Card.getCard(cards, Card.CardName.Estate), 8);

//INITIALIZE TREASURE CARDS
		gameBoard.put(Card.getCard(cards, Card.CardName.Gold), 30);
		gameBoard.put(Card.getCard(cards, Card.CardName.Silver), 40);
		gameBoard.put(Card.getCard(cards, Card.CardName.Copper), 46);

//INITIALIZE PLAYER'S CARDS
		for (Player player : players) {
		    System.out.println("\n" + player.player_username + "'s Initial Card Draw: ");
		    for (int i = 0; i < 7; i++)
			    player.gain(Card.getCard(cards, Card.CardName.Copper));
            for (int i = 0; i < 3; i++)
			    player.gain(Card.getCard(cards,Card.CardName.Estate));
        }
    }
	   
	HashMap<Player, Integer>  play() {
			 
		   	  int turn = 0;
		      while (!isGameOver()) {
		    	  turn++;
		         for (Player player : players) {
		        	 	System.out.println("\n--- " + player.player_username + " IS PLAYING ---");
		        	 	player.initializePlayerTurn();
		   				//player plays action card
		        	 	player.playKingdomCard();
		        	 	//player plays treasure card
		   			    player.playTreasureCard();
		   			    //player buy cards
		        	    player.buyCard(this);
		        	  //player ends turn
		        	    player.endTurn();
		         }
		         if(turn == 100)
		        	break;
		      }
		      System.out.println("\nTURNS PLAYED\n" + turn);
		      return this.getWinners();
		   }

	boolean isGameOver() {
		 //if stack of Province cards is empty, the game ends
		   if((this.gameBoard.get(Card.getCard(cards, Card.CardName.Province))==null)||(this.gameBoard.get(Card.getCard(cards, Card.CardName.Province))== 0))
			   return true;
		 //if three supply pile are at 0, the game ends
		      int emptySupplyPile = 0;
		      for (int i : gameBoard.values()){
		         if (i == 0)
		        	 emptySupplyPile++;
		         if ( emptySupplyPile >= 3)
		         {
		           return true;
		         }
		      }
		         return false;
		   }

	HashMap<Player, Integer>  getWinners() {
    //Set HashMap  of each player and the score (remember ties!)
	    HashMap<Player, Integer> playerScore = new HashMap<Player, Integer>();

		//get score for each player
		for (Player p : players) {
		    int score = p.scoreFor();
		    playerScore.put(p, score);
        }

        return playerScore;
    }

	static void addEmbargo(Card card)
    {
        System.out.println("Token Added.");
    }

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		if (gameBoard.isEmpty())
			sb.append("The board game is empty you need to intialize the game!!!!");
		else {
			for (Player player : players)
				sb.append(" --- ").append(player.toString()).append("\n");
			sb.append(" --- gameBoard --- \n");
			sb.append("Cards on the table: \n");
			sb.append("Card Name: \t\t NumberCards: \n");
			Map<Card, Integer> treeMap = new TreeMap<Card, Integer>(gameBoard);
			for (Card card : treeMap.keySet())
				sb.append("\t").append(card.getCardName()).append("\t\t\t").append(treeMap.get(card)).append("\n");
		}
		return sb.toString();
	}   
	   
	private GameState(List<Card> cards, List<Player> players, HashMap<Card, Integer> gameBoard) {
		   this.cards=cards;
		   this.players=players;
		   this.gameBoard=gameBoard;
	   }
}