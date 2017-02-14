package cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;

import java.util.Collections;

public class PlayDominion {
        public  static void main(String args[]){
            List<Card> cards;
            GameState state;
            Randomness.reset(10);	   

            //the cards  are achieved by each element/constant in the enum class
            
            // Create all cards and state instances
            cards = new ArrayList<Card>(Card.createCards());
            state = new GameState(cards);

            // Instantiate player 1
            Player player = new Player(state,"Player 1");
            player.hand.add(Card.getCard(cards,Card.CardName.Ambassador));
            player.hand.add(Card.getCard(cards,Card.CardName.Ambassador));
            state.addPlayer(player);
            
            // Instantiate player 2
            player = new Player(state,"Player 2");
            //player.hand.add(Card.getCard(cards,Card.CardName.Baron));
            //player.hand.add(Card.getCard(cards,Card.CardName.Village));
            state.addPlayer(player);
            
            // Instantiate player 3
            player = new Player(state,"Player 3");
            state.addPlayer(player);

            // Game initialization
            state.initializeGame();
            
            // Playing the game
            System.out.println("Initialization DominionBoard:\n " + state.toString());
            System.out.println("----------------------------------");
            HashMap<Player,Integer> endGameLeaderboard = state.play();
            
            System.out.println("Game Complete.\n");

            System.out.println("Player Name\t\tVictory Points");
            for (Player p: endGameLeaderboard.keySet()){
                System.out.println("- " + p.playerName + "\t\t" + endGameLeaderboard.get(p));
            }

            StringBuilder sb = new StringBuilder();
            sb.append("\n --- Cards on Table --- \n");
            sb.append("Card Name (Number of Cards)\n");
            Map<Card,Integer> treeMap = new TreeMap<Card, Integer>(state.gameBoard);
            for (Card card : treeMap.keySet())
                sb.append("- " + card.getCardName() + " (" + treeMap.get(card) + ")\n");
            
            System.out.println(sb);
            System.exit(0);
	   }
}
