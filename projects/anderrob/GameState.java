package dominion;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;




//struct gameState {
//	  int numPlayers; //number of players
//	  int supplyCount[treasure_map+1];  //this is the amount of a specific type of card given a specific number.
//	  int embargoTokens[treasure_map+1];
//	  int outpostPlayed;
//	  int outpostTurn;
//	  int whoseTurn;
//	  int phase;
//	  int numActions; /* Starts at 1 each turn */
//	  int coins; /* Use as you see fit! */
//	  int numBuys; /* Starts at 1 each turn */
//	  int hand[MAX_PLAYERS][MAX_HAND];
//	  int handCount[MAX_PLAYERS];
//	  int deck[MAX_PLAYERS][MAX_DECK];
//	  int deckCount[MAX_PLAYERS];
//	  int discard[MAX_PLAYERS][MAX_DECK];
//	  int discardCount[MAX_PLAYERS];
//	  int playedCards[MAX_DECK];
//	  int playedCardCount;
//	};


public class GameState implements Cloneable{
	   public List<Player> players = new ArrayList<Player>(); ;
	   public static List<Card> cards ;
	   public static HashMap<Card, Integer> gameBoard = new HashMap<Card, Integer>();	
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
				   System.err.println("the number of players must be between 2 and 4 ");
			      return ;
			    }
			 //initialize supply for only two players
				int selectedKindom=0;
				int Kingdom_Cards_Selected=13;// We only defined Adventurer, smithy, and Village. We need to define more kingdom cards the Card class
				   								// we should change 3 to the  exact of the number of 
				   								//kingdom cards. look at the requirements of the assignment-1
			
				
				gameBoard.put(Card.getCard(cards, Card.CardName.cellar), 0);
			    gameBoard.put(Card.getCard(cards, Card.CardName.chancellor), 0);
			    gameBoard.put(Card.getCard(cards, Card.CardName.council_room), 0);
			    gameBoard.put(Card.getCard(cards, Card.CardName.Adventurer), 0);
			    gameBoard.put(Card.getCard(cards, Card.CardName.bureaucrat), 0);
			    
			    gameBoard.put(Card.getCard(cards, Card.CardName.feast), 0);
			    gameBoard.put(Card.getCard(cards, Card.CardName.festival), 0);
			    gameBoard.put(Card.getCard(cards, Card.CardName.gardens), 0);
			    gameBoard.put(Card.getCard(cards, Card.CardName.laboratory), 0);
			  
			    gameBoard.put(Card.getCard(cards, Card.CardName.library), 0);
			    gameBoard.put(Card.getCard(cards, Card.CardName.Village), 0);
			    gameBoard.put(Card.getCard(cards, Card.CardName.Smithy), 0);
			    gameBoard.put(Card.getCard(cards, Card.CardName.chapel), 0);	
				int value;
		      while (selectedKindom < Kingdom_Cards_Selected) {
			         int random = (int)  Randomness.random.nextInt(cards.size());//
			         Card tmp = cards.get(random);
			         if(tmp.getType()!=Card.Type.ACTION) continue;
			         value = GameState.gameBoard.get(tmp);
			         if(gameBoard.containsKey(tmp) && value > 0)
			         {
			        	 continue;
			         }
			        // if(gameBoard.containsKey(tmp)) continue;
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
		      //set number of action cards
		      /*

		   */

		      for (Player player : players) {	        
		    	  for (int i = 0; i < 7; i++)
			            player.gain(Card.getCard(cards, Card.CardName.Copper));
			      for (int i = 0; i < 3; i++)
			            player.gain(Card.getCard(cards,Card.CardName.Estate));
		    	  player.numActions = 1;
			      player.coins = 0;
			      player.numBuys = 1;
				      //Shuffle your starting 10 cards (7 Coppers & 3 Estates) and place them face-down as your Deck. Draw the top
				      //5 cards as your starting hand
				      for (int i = 0; i < 5; i++) {
				    	  player.drawCard();
				      }
			      } 
	   }
	   
	   public HashMap<Player, Integer>  play() {
			 
		   	  int turn =0;	
		      while (!isGameOver()) {
		    	  turn++;
		         for (Player player : players) {
		        	 	System.out.println(player.player_username + " is playing");
		   				//player p plays action card
		        	 	player.playKingdomCard();
		        	 	//player plays treasure card
		        	 	
		   			    player.playTreasureCard();
		   			    System.out.println("hand size: " + player.hand.size());
		   			    for(int i = 0; i < player.hand.size(); i++ ){
		   			    	player.coins += 1;
		   			    }
		   			    
		   			    //player buy cards
		        	    player.buyCard();
		        	    //player ends turn
		        	    player.endTurn();  
		         }
		      }
		      return this.getWinners();
		   }
	   public boolean isGameOver() {
		 //if stack of Province cards is empty, the game ends
		   if((this.gameBoard.get(Card.getCard(cards, Card.CardName.Province))==null)|| (this.gameBoard.get(Card.getCard(cards, Card.CardName.Province))== 0) || (this.gameBoard.get(Card.getCard(cards, Card.CardName.Gold))==null)||(this.gameBoard.get(Card.getCard(cards, Card.CardName.Gold))== 0) || (this.gameBoard.get(Card.getCard(cards, Card.CardName.Estate))==null)||(this.gameBoard.get(Card.getCard(cards, Card.CardName.Estate))== 0) || (this.gameBoard.get(Card.getCard(cards, Card.CardName.Copper))==null)||(this.gameBoard.get(Card.getCard(cards, Card.CardName.Copper))== 0) || (this.gameBoard.get(Card.getCard(cards, Card.CardName.Silver))==null)|| (this.gameBoard.get(Card.getCard(cards, Card.CardName.Silver))== 0))
			   return true;
		 //if three supply pile are at 0, the game ends
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
	   
	   @Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		if (gameBoard.isEmpty())
			sb.append("The board game is empty you need to intialize the game!!!!");
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
}
/*
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
*/