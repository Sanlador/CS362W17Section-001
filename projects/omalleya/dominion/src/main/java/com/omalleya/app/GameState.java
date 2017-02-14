//Hashmap<Card,Integer> Dominionboardmap

//After each turn check if gameover
    //if province card pile is empty
    //or if any 3 supply piles are empty

    //if tie, player with less turns wins
    //if same number of turns, shared victory

package com.omalleya.app;

import java.util.List;

import com.omalleya.app.Card.CardName;
import com.omalleya.app.Card.Type;




import java.util.ArrayList;
import java.util.*;



public class GameState implements Cloneable{
    public List<Player> players = new ArrayList<Player>();
    public final List<Card> cards;
    public HashMap<Card, Integer> gameBoard = new HashMap<Card, Integer>();

    public GameState(List<Card> cards){
        this.cards = cards;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void initializeGame(){
        //check number of players    
        if (players.size() > 4 || players.size() < 2) {
            System.err.println("the number of players must be between 2 and 4");
            return;
        }
        
        //8 each of estate duchy and provinces
        gameBoard.put(Card.getCard(cards, CardName.Estate), 8);
        gameBoard.put(Card.getCard(cards, CardName.Duchy), 8);
        gameBoard.put(Card.getCard(cards, CardName.Province), 8);
        //10 curse cards
        gameBoard.put(Card.getCard(cards, CardName.Curse), 10);

        //Put Treasure Cards on table
        gameBoard.put(Card.getCard(cards, CardName.Gold), 30);
        gameBoard.put(Card.getCard(cards, CardName.Silver), 40);
        gameBoard.put(Card.getCard(cards, CardName.Copper), 46);

        //All treasure cards
        //10 sets of 10 kingdom cards
            //if the kindom card is a victory card as well follow rules for estate, etc.
        int kingdomCount = 0;
        int kingdomSets = 10;

        while (kingdomCount < kingdomSets) {
            int random = (int)  Randomness.random.nextInt(cards.size());
            Card tmp = cards.get(random);
            if(tmp.getType()!=Card.Type.ACTION) continue;
            if(gameBoard.containsKey(tmp)) continue;
            gameBoard.put(tmp, 10);
            kingdomCount++;
        }

        for(Player p : players) {
            for(int i=7; i>0; i--)
                p.gain(Card.getCard(cards, CardName.Copper));
            
            for(int i=3; i>0; i--)
                p.gain(Card.getCard(cards, CardName.Estate));

            //5 cards as your starting hand
            for (int i = 0; i < 5; i++) {
                p.drawCard();
            }
        }
        //System.out.println(this);
    }

    public void removeCard(Card c) {
        int currentNum = gameBoard.get(c);
        gameBoard.put(c, currentNum-1);
    }

    public HashMap<Player, Integer> play() {
        int turns = 0;
        while(!endGame()) {
            turns++;
            for(Player p : players) {
                p.initializePlayerTurn();
                p.action();
                p.buy(this);
                p.cleanUp();
                //5 cards as your starting hand
                for (int i = 0; i < 5; i++) {
                    p.drawCard();
                }
            }
            if(turns==3)
                break;
        }

        return this.getWinners();
    }

    public boolean endGame() {
		 //if stack of Province cards is empty, the game ends
        if((gameBoard.get(Card.getCard(cards, CardName.Province))==null)||(gameBoard.get(Card.getCard(cards, CardName.Province))== 0))
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

    private GameState(List<Card> cards, List<Player> players, HashMap<Card,Integer> gameBoard) {
        this.cards=cards;    
        this.players=players;    
        this.gameBoard=gameBoard;   
    }

    public GameState clone() throws CloneNotSupportedException {    
        List<Player> clonePlayers = new ArrayList<Player>();    
        List<Card> cloneCards = new LinkedList<Card>();    
        HashMap<Card, Integer> cloneGmeBoard = new HashMap<Card,Integer>(gameBoard);   
        for (Player player : players)     
            clonePlayers.add((Player) player.clone());     
        for (Card card : cards)     
            cloneCards.add((Card) card.clone());
        final GameState cloneState= new GameState(cloneCards,clonePlayers,cloneGmeBoard);
        return  cloneState;     
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
}