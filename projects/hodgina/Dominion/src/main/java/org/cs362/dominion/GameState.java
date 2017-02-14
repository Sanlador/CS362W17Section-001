package org.cs362.dominion;

import java.util.*;


public class GameState implements Cloneable{
	   public List<Player> players = new ArrayList<Player>(); ;
	   public List<Card> cards ;
	   public HashMap<Card, Integer> gameBoard = new HashMap<Card, Integer>();
	   List<Card> embargoTokens;
	   private Random gen = new Random();


	   public GameState(List<Card> cards) {
		   this.cards=cards;
	   }
	   public void addPlayer(Player player) {
	  	 	players.add(player);
	   }

	   /*	   initializing all supplies, and shuffling deck and
	   drawing starting hands for all players.  Check that 10 cards selected
	   are in fact (different) kingdom cards, and that numPlayers is valid.*/

	   public void initializeGame(){


			      //initialize supply

			   //check number of players
			   if (players.size() > 4 || players.size() < 2)
			    {
				   System.err.println("the number of players mus be between 2 and 4 ");
			      return ;
			    }
			 //initialize supply for only two players
				  int selectedKindom=0;
				   int Kingdom_Cards_Selected=10;

		      while (selectedKindom < Kingdom_Cards_Selected) {
			         int random = gen.nextInt(cards.size());
			         Card tmp = cards.get(random);
			         if(tmp.getType()!=Card.Type.ACTION) continue;
			         if(gameBoard.containsKey(tmp)) continue;
			         gameBoard.put(tmp, 10);
			         selectedKindom++;
			      }
		        //set number of Curse cards the default number of players is 2
		      gameBoard.put(Card.getCard(cards, Card.CardName.Curse), 10);

		      //set number of Victory cards
		      gameBoard.put(Card.getCard(cards, Card.CardName.Province), 8);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Duchy), 8);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Estate), 8);
		    //set number of Treasure cards
		      gameBoard.put(Card.getCard(cards, Card.CardName.Gold), 30);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Silver), 40);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Copper), 46);


		      for (Player player : players) {
			         for (int i = 0; i < 7; i++) {
			         	System.out.println("\n" + player.player_username + " 's Inistial Card Draw Is: ");
						 player.gain(Card.getCard(cards, Card.CardName.Copper));
					 }
			         for (int i = 0; i < 3; i++)
			            player.gain(Card.getCard(cards,Card.CardName.Estate));

			      }
	   }

	   public HashMap<Player, Integer>  play() {

		   	  int turn =0;
		      while (!isGameOver()) {
		    	  turn++;
		         for (Player player : players) {
					 System.out.println("-----------------------------------");
					 System.out.println("        Turn " + turn + "           ");
					 System.out.println("-----------------------------------");
		        	 	System.out.println("Player: "+ player.player_username + " is playing");
		        	 	player.initializePlayerTurn(); //resets the player's coins, buy's, and Actions.
		   				//player p plays action card
		        	 	player.playKingdomCard(); //Play Action Cards
		        	 	//player plays treasure card
		   			    player.playTtreasureCard(); //Count Treasure cards and add them to Coins
		   			    //player buy cards
		        	    player.buyCard(this); //buy cards
		        	  //player ends turn
		        	    player.endTurn(); //end the players turn and clean up
		         }
		         if(turn==100)
		        	 break;
		      }
		      return this.getWinners();
		   }
	   public boolean isGameOver() {
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

	   /* Set HashMap  of each player and the score (remember ties!) */
	   public HashMap<Player, Integer>  getWinners() {
		   HashMap<Player, Integer> playerScore = new HashMap<Player, Integer>();

		    //get score for each player
		      for (Player p : players) {
		         int score = p.scoreFor();
		         playerScore.put(p, score);
		      }

		      return playerScore;
		   }


	static void addEmbargo(Card card){
	   	System.out.print("Embargo Token Added");
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		if (gameBoard.isEmpty())
			sb.append("The board game is empty you need to initialize the game!!!!");
		else {
			for (Player player : players)
				sb.append(" --- " + player.toString() + "\n");
			sb.append(" --- gameBoard --- \n");
			sb.append("Cards on the table: \n");
			sb.append("Card Name \t\t NumberCards: \n");
			Map<Card, Integer> treeMap = new TreeMap<Card, Integer>(gameBoard);
			for (Card card : treeMap.keySet())
				sb.append("\t " + card.getCardName() + "\t\t "
						+ treeMap.get(card) + "\n");
		}
		return sb.toString();
	}

	   private GameState(List<Card> cards, List<Player> players, HashMap<Card, Integer> gameBoard) {
		   this.cards=cards;
		   this.players=players;
		   this.gameBoard=gameBoard;

	   }

	   public GameState clone() throws CloneNotSupportedException {
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
}
