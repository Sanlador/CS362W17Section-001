//*************************
//Name: Bradley Imai
//Project: Assignment 1
//File: GameState.java
//Class: CS362
//*************************
package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;


public class GameState implements Cloneable{
	   public List<Player> players = new ArrayList<Player>();
	   public List<Card> cards;
	   public HashMap<Card, Integer> gameBoard = new HashMap<Card, Integer>();	
	   
	   public GameState(List<Card> cards) {
		   this.cards=cards;
		   
	   }
	   public void addPlayer(Player player) {
		      players.add(player);
	   }   

	   
 /*
*initializing all supplies, and shuffling deck and
drawing starting hands for all players.  Check that 10 cards selected
are in fact (different) kingdom cards, and that numPlayers is valid.
* steps: initialize supply
*        check for the number of players
*        supply for only two players requirements for this assignment
*        set number of cures cards however this card is not being used
*        set victory cards
*        set treasury cards
*/  
	   public void initializeGame(){
			   if (players.size() > 4 || players.size() < 2)
			    {
				   System.err.println("the number of players mus be between 2 and 4 ");
			      return ;
			    }
				  int selectedKindom=0;
				   int Kingdom_Cards_Selected=13;
		      while (selectedKindom < Kingdom_Cards_Selected) {
			         int random = (int)  Randomness.random.nextInt(cards.size());//
			         Card tmp = cards.get(random);
			         if(tmp.getType()!=Card.Type.ACTION) continue;
			         if(gameBoard.containsKey(tmp)) continue;
			         gameBoard.put(tmp, 10);
			         selectedKindom++;
			      }		   
		      gameBoard.put(Card.getCard(cards, Card.CardName.Curse), 10);
		      //Victory cards
		      gameBoard.put(Card.getCard(cards, Card.CardName.Province), 8);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Duchy), 8);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Estate), 8);
		      //Treasure cards
		      gameBoard.put(Card.getCard(cards, Card.CardName.Gold), 30);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Silver), 40);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Copper), 46);

		      for (Player player : players) {
			         for (int i = 0; i < 7; i++)
			            player.StartGain(Card.getCard(cards, Card.CardName.Copper));
			         for (int i = 0; i < 3; i++)
			            player.StartGain(Card.getCard(cards,Card.CardName.Estate));
			         
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
		        	 	System.out.println("Turn "+turn+": "+ player.player_username + " is playing");
		   				//player plays action card
		        	 	player.playKingdomCard();
		        	 	//player plays treasure card
		   			    player.playTreasureCard();
		   			    //player buy cards
		        	    player.buyCard();
		        	    //player ends turn
		        	    player.endTurn();
		         }
		         //if(turn==3)
		        	 //break;
		      }
		      return this.getWinners();
		   }
	   public boolean isGameOver() {
		 //if stack of Province cards is empty, the game ends
		   if((this.gameBoard.get(Card.getCard(cards, Card.CardName.Province))==null)||(this.gameBoard.get(Card.getCard(cards, Card.CardName.Province))== 0))
		   {System.out.println("Out of Province cards.");
			   return true;}
		 //if three supply piles are at 0, the game ends
		      int emptySupplyPile = 0;
		      for (int i : gameBoard.values()){
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
			sb.append("The board game is empty, you need to intialize the game!");
		else {
			for (Player player : players)
				sb.append(" --- " + player.toString() + "\n");
			sb.append(" --- gameBoard --- \n");
			sb.append("Cards on the table: \n");
			sb.append(String.format("%-20s %s" , "Name:", "Number:") + "\n");
			Map<Card, Integer> treeMap = new TreeMap<Card, Integer>(gameBoard);
			for (Card card : treeMap.keySet())
				sb.append(String.format("%-20s %s" , "  " + card.getCardName(), "  " + treeMap.get(card)) + "\n");
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
	   
   /*
   *Customized game setup function for test suite (doesn't include drawing initial hand)
   * steps: initialize supply
   *        check for the number of players
   *        supply for only two players requirements for this assignment
   *        set number of cures cards however this card is not being used
   *        set victory cards
   *        set treasury cards
   *        Since this is my testing, I will not be drawing a new cards
   */     
		
		public void prepTestGame()
		{
			// Initialize supply
			int selectedKindom=0;
			int Kingdom_Cards_Selected=13;
		    while (selectedKindom < Kingdom_Cards_Selected) {
		    	int random = (int)  Randomness.random.nextInt(cards.size());
			    Card tmp = cards.get(random);
			    if(tmp.getType()!=Card.Type.ACTION) continue;
			    if(gameBoard.containsKey(tmp)) continue;
			    gameBoard.put(tmp, 10);
			    selectedKindom++;
			}
		      //set number of Curse cards		   
		      gameBoard.put(Card.getCard(cards, Card.CardName.Curse), 10);
		      //set number of Victory cards
		      gameBoard.put(Card.getCard(cards, Card.CardName.Province), 8);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Duchy), 8);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Estate), 8);
		      //set number of Treasure cards
		      gameBoard.put(Card.getCard(cards, Card.CardName.Gold), 30);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Silver), 40);
		      gameBoard.put(Card.getCard(cards, Card.CardName.Copper), 46);
		      // Create starting deck for each player
		      for (Player player : players) {
			         for (int i = 0; i < 7; i++)
			            player.StartGain(Card.getCard(cards, Card.CardName.Copper));
			         for (int i = 0; i < 3; i++)
			            player.StartGain(Card.getCard(cards,Card.CardName.Estate));
			         player.numActions = 1;
			         player.coins = 0;
			         player.numBuys = 1;
				      //Shuffle your starting 10 cards (7 Coppers & 3 Estates) and place them face-down as your Deck.
						System.out.println("Reshuffling discard pile of "+ player.player_username +" into a new deck");
						while (player.discard.size() > 0) {
							int ndx = (int) Randomness.nextRandomInt(player.discard.size());
							// Move discard to deck
							player.deck.add(player.discard.remove(ndx));
						}
			      }
		     }
}
