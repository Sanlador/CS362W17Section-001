package cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;

public class GameState {
    public final List<Player> players = new ArrayList<Player>();
    public final List<Card> cards;
    public HashMap<Card, Integer> gameBoard = new HashMap<Card, Integer>();
    
    // CONSTRUCTOR
    public GameState(List<Card> cards) {
        this.cards=cards;
    }
    
    public void addPlayer(Player player) {
        players.add(player);
    }
   
    public void initializeGame(){
        //initialize supply

        //check number of players
        if ((players.size() < 2) || (players.size() > 4)) {
            System.err.println("Error: You must initialize the game with 2 players.");
            System.exit(0);
        }
        
        //initialize supply for only two players
        int selectedKindom=0;
        int Kingdom_Cards_Selected=12; // We only defined Adventurer, smithy, and Village. We need to define more kingdom cards the Card class
                                        // we should change 3 to the  exact of the number of 
                                        //kingdom cards. look at the requirements of the assignment-1
        while (selectedKindom < Kingdom_Cards_Selected) {
            int random = (int)  Randomness.random.nextInt(cards.size());//
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
        gameBoard.put(Card.getCard(cards, Card.CardName.Gardens), 8);
        
        //set number of Treasure cards
        gameBoard.put(Card.getCard(cards, Card.CardName.Gold), 30);
        gameBoard.put(Card.getCard(cards, Card.CardName.Silver), 40);
        gameBoard.put(Card.getCard(cards, Card.CardName.Cooper), 46);

        for (Player player : players) {
            for (int i = 0; i < 7; i++)
                player.addToDeck(Card.getCard(cards,Card.CardName.Cooper));
            for (int i = 0; i < 3; i++)
                player.addToDeck(Card.getCard(cards,Card.CardName.Estate));

            player.actions = 1;
            player.coins = 0;
            player.buys = 1;
            
            //Shuffle your starting 10 cards (7 Coppers & 3 Estates) and place them face-down as your Deck. Draw the top
            //5 cards as your starting hand
            
            for (int i = 0; i < 5; i++) {
                player.drawCard();
            }
        }
    }
    
    // THE FOLLOWING METHOD IS CORE TO DOMINION
    public HashMap<Player, Integer> play() {
         
        int turn = 0;
        while (!isGameOver()) {
            turn++;
            for (Player player : players) {
                System.out.println("::TURN BEGIN: It's "+ player.playerName + "'s turn.\n");
                System.out.println(player.toString());
                
                // action phase
                player.playKingdomCard();
                
                // buy phase playing treasure cards
                player.playTreasureCard();
                
                // buy phase buying cards
                player.buyCard();
                
                // cleanup/end phase
                player.endTurn();
            }
            
            // DEV: end game
            if(turn == 50) {
                System.out.println("Out of turns!");
                break;
            }
        }
        return this.getWinners();
    }
    
    // GAMESTATE CHECK METHODS FOLLOW
    public boolean isGameOver() {
        if((gameBoard.get(Card.getCard(cards, Card.CardName.Province))==null)||(gameBoard.get(Card.getCard(cards, Card.CardName.Province))== 0))
            return true;
        int emptySupplyPile = 0;
        for (int i : gameBoard.values()){
            if (i == 0)
                emptySupplyPile++;
            if (emptySupplyPile >= 3) {
                return true;
            }
        }
        return false;
    }

    // INFORMATIONAL
    public HashMap<Player,Integer> getWinners() {
        HashMap<Player,Integer> leaderboard = new HashMap<Player,Integer>();

        for (Player p : players) {
            int victoryPoints = p.scoreFor();
            leaderboard.put(p,victoryPoints);
        }

        return leaderboard;
    }
   
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (gameBoard.isEmpty())
            sb.append("The board game is empty; you need to intialize the game!");
        else {
            for (Player player : players)
                sb.append(" --- " + player.toString() + "\n");
            sb.append(" --- Cards on Table --- \n");
            sb.append("Card Name (Number of Cards)\n");
            Map<Card,Integer> treeMap = new TreeMap<Card, Integer>(gameBoard);
            for (Card card : treeMap.keySet())
                sb.append("- " + card.getCardName() + " (" + treeMap.get(card) + ")\n");
        }
        return sb.toString();
    }
}
