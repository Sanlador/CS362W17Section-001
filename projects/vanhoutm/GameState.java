/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion;

/**
 *
 * @author Regex
 */
public class GameState {
    
    
    int MAX_PLAYERS = 4;
    int MAX_HAND = 500;
    int MAX_DECK = 500;
    
    int numPlayers; 
    int outpostPlayed;
    int outpostTurn;
    int whoseTurn;
    int phase;
    int numActions; 
    int coins; 
    int cardCoinBonus;
    int numBuys; 
    
    int[] supplyCount = new int[Card.treasure_map.ordinal()+1]; // number of card types plus one
    int[] embargoTokens = new int[Card.treasure_map.ordinal()+1];
    int[] handCount = new int[MAX_PLAYERS]; 
    int[] deckCount = new int[MAX_PLAYERS];

    int[] discardCount = new int[MAX_PLAYERS];
    int[] playedCards = new int[MAX_DECK];
    int[][] hand = new int[MAX_PLAYERS][MAX_HAND];
    int[][] deck = new int[MAX_PLAYERS][MAX_DECK];
    int[][] discard = new int[MAX_PLAYERS][MAX_DECK];
    
    int playedCardCount;
    
    
    
    // helper function to tell us everything 
    public void reportPlayerCards(int who){
        int sum = handCount[who] +deckCount[who] + discardCount[who];
        if (whoseTurn == who)
            sum += playedCardCount;
        System.out.println("Player: " + who + " #cards: " + sum);
        System.out.println("Hand: " + handCount[who]); 
        System.out.println("Discard: " + discardCount[who]);
        System.out.println("Deck: " + deckCount[who]);
        if (whoseTurn == who)
            System.out.println("Played: " + playedCardCount);
        
    }
    public void reportState(){
        System.out.println("Max players: " + MAX_PLAYERS);
        System.out.println("Max hand size: " + MAX_HAND);
        System.out.println("Max deck size: " + MAX_DECK);
        
        System.out.println("number of players: " + numPlayers);
        System.out.println("outpostPlayed: " + outpostPlayed);
        System.out.println("outpostTurn: " + outpostTurn);
        System.out.println("whoseTurn: " + whoseTurn);
        System.out.println("phase: " + phase);
        System.out.println("numActions: " + numActions);
        System.out.println("coins: " + coins);
        System.out.println("numBuys: " + numBuys);
        
        
        System.out.println("Card types: ");
        for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i){
            System.out.println(Card.values()[i] + " supply: "+ supplyCount[i]);
            System.out.println(Card.values()[i] + " embargoTokens: "+ embargoTokens[i]);
        }

        for (int i = 0; i < numPlayers; ++i){
            System.out.println("Player " + i + ":");
            System.out.println("handCount: "    + handCount[i]);
            System.out.println("deckCount: "    + deckCount[i]);
            System.out.println("discardCount: " + discardCount[i]);
            
            System.out.println("");
            System.out.println("hand: ");
            for (int j = 0; j < handCount[i]; ++j){
                System.out.println(Card.values()[hand[i][j]]);

            }
            System.out.println("");
            System.out.println("deck: ");
            for (int j = 0; j < deckCount[i]; ++j){
                System.out.println(Card.values()[deck[i][j]]);

            }
            System.out.println("");
            System.out.println("discard: ");
            for (int j = 0; j < discardCount[i]; ++j){
                System.out.println(Card.values()[discard[i][j]]);

            }
            System.out.println("");
            
        }
        System.out.println("playedCardCount: " + playedCardCount);
        System.out.println("playedCards: " );
        for (int i = 0; i < playedCardCount; ++i)
            System.out.println(i + " " + playedCards[i]);
    }
}
//    int[] playedCards = new int[MAX_DECK];