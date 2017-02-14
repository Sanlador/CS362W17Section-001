package org.cs362.dominion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.cs362.dominion.Card.CardName;
import org.cs362.dominion.Card.Type;


public class Player {
    List<Card> hand = new ArrayList<Card>();// int hand[MAX_PLAYERS][MAX_HAND];
    LinkedList<Card> deck = new LinkedList<Card>();// int
    // deck[MAX_PLAYERS][MAX_DECK];
    List<Card> discard = new ArrayList<Card>(); // int
    // discard[MAX_PLAYERS][MAX_DECK];
    List<Card> playedCards = new ArrayList<Card>();

    String player_username;

    public static int numPlayers = 2;
    int gameOver = 0;
    int numActions;
    int numBuys;
    int coins;

    final GameState gameState;


    public Player(GameState gameState,String player_username) {
        this.player_username = player_username;
        this.gameState=gameState;
    }

    final Card drawCard() {
        if (deck.isEmpty()) {// Deck is empty
            // Step 1 Shuffle the discard pile back into a deck
            System.out.println("reshuffle the deck of the player "
                    + player_username + " to draw FIVE cards");
            while (discard.size() > 0) {
                int ndx = (int) Randomness.nextRandomInt(discard.size());
                // Move discard to deck
                deck.add(discard.remove(ndx));
            }
        }

        Card toDraw = deck.poll();
        hand.add(toDraw);// Add card to hand and hand count automatically will
        // be incremented since we use List
        System.out.println("draw " + toDraw);
        Collections.sort(hand);
        return toDraw;
    }

    final void initializePlayerTurn() {
        //initialize first player's turn
        numActions = 1;
        coins = 0;
        numBuys = 1;
        //Shuffle your starting 10 cards (7 Coppers & 3 Estates) and place them face-down as your Deck. Draw the top
        //5 cards as your starting hand
        for (int i = 0; i < 5; i++) {
            drawCard();
        }
    }

    //card goes in discard,
    final boolean gain(Card card) {
        discard.add(card);
        System.out.println("Player: "+this.player_username+" gains "+card);
        return true;
    }

    final boolean gainFromBoard(Card card){
        gain(card);
        gameState.gameBoard.put(card, gameState.gameBoard.get(card) - 1);
        return true;
    }

    //Discard hand
    public void discard(Card card) {
        hand.remove(card);
        discard.add(card);
        System.out.println("Player:  "+player_username+" discards "+card);
    }


    public void playKingdomCard() {
        while (numActions > 0) {
            List<Card> actionCards = Card.filter(hand, Type.ACTION);


            if (actionCards.size() == 0)
                return;
            System.out.println("Player.playKingdomCard() " + actionCards.toString());
            Card c = (Card)actionCards.get(0);
            System.out.println("Player.actionPhase Card:" + c.toString());

            playedCards.add(c);
            hand.remove(c);
            numActions -= 1;

            c.play(this, gameState);
        }
    }
    final int scoreFor() {
        int score = 0;
        //score from hand
        for (Card c : hand)
            score += c.score(this);
        //score from discard
        for (Card c : discard)
            score += c.score(this);
        //score from deck
        for (Card c : deck)
            score += c.score(this);



        return score;
    }

    public void playTtreasureCard() {
      List<Card> treasureCards = Card.filter(hand, Type.TREASURE);

        // loop through each treasure
        System.out.println("treasures in hand:");
        for(Card c: treasureCards){
            System.out.println(c.toString());
        }


        // for each treasure card in hand, save the value of the card and add it to the
        // discard pile.
        for(int i = 0; i < treasureCards.size(); i++){
            Card c = treasureCards.get(i);
            System.out.println("Spending: " + c.toString());
            coins  += c.getTreasureValue();
            hand.remove(c);
            //treasureCards.remove(c);
            playedCards.add(c);
        }

    }
    public void buyCard() {

        while(numBuys > 0){
            Map<Card, Integer> treeMap = new TreeMap<Card, Integer>(gameState.gameBoard);
            //List<Card> buyableCards = new LinkedList<Card>();
            List<Card> buyableCards = Card.filterObtainable(treeMap.keySet(), gameState, coins);

//   				for(Card c: treeMap.keySet()){
//   					System.out.println(gameState.gameBoard.get(c));
//   					if(c.getCardName() != CardName.Curse && c.getCost() <= coins){
//   						buyableCards.add(c);
//   					}
//   				}
            int random = gameState.rand.nextInt(buyableCards.size());
            Card c = buyableCards.get(random);

            gainFromBoard(c);
            //gain(buyableCards.get(0));
            coins-= c.getCost();
            //gameState.gameBoard.put(buyableCards.get(0), gameState.gameBoard.get(buyableCards.get(0)) - 1);
            numBuys--;

            int k = gameState.embargoes.get(c);
            List<Card> j = Card.filterName(gameState.cards, CardName.Curse);
            Card x = j.get(0);

            if(k > 0){
                for(int i = 0; i < k; i++){
                    gainFromBoard(x);
                }
            }
        }


    }
    final void endTurn() {
        System.out.println(" --- --------------------------- --- ");
        System.out.println("TO-DO endTurn ");
        System.out.println(" --- --------------------------- --- ");
        numActions = 1;
        numBuys = 1;
        coins = 0;

        int inhand = hand.size();

        for(int i = 0; i < inhand; i++){
            discard(hand.get(0));
        }

        int inplay = playedCards.size();

        for(int j = 0; j < inplay; j++){
            Card c = playedCards.get(0);
            playedCards.remove(c);
            discard.add(c);
        }

        for(int i = 0; i < 5; i++){
            drawCard();
        }
    }


    public void printStateGame(){
        System.out.println(" --- " + this.player_username + " --- ");
        System.out.println(" --- --------------------------- --- ");
        System.out.println(this.gameState.toString());
        System.out.println(" --- --------------------------- --- ");
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(" --- " + this.player_username + " --- ");
        sb.append(" --- --------------------------- --- ");

        sb.append("Hand: " + this.hand);
        sb.append("Discard: " + this.discard);
        sb.append("Deck: " + this.deck);
        sb.append("Played Cards: " + this.playedCards);
        sb.append("numActions: " + this.numActions);
        sb.append("coinss: " + this.coins);
        sb.append("numBuys: " + this.numBuys);
        sb.append("\n");

        return sb.toString();

    }


}