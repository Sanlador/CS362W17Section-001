//HAND - arraylist
//which relates player to card
//DECK - linkedlist
//DISCARD

//Start with 7 coppers 3 estates

package com.omalleya.app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.omalleya.app.Card.CardName;


import com.omalleya.app.Card.Type;


import java.util.Collections;


public class Player implements Cloneable{
    LinkedList<Card> deck = new LinkedList<Card>();
    ArrayList<Card> hand = new ArrayList<Card>();
    LinkedList<Card> discarded = new LinkedList<Card>();
    List<Card> playedCards = new ArrayList<Card>();

    public String player_name;
    int numActions, numBuys, coins;

    final GameState gameState;

    public Player(GameState gameState,String player_name) {
		this.player_name = player_name;
		this.gameState=gameState;
	}

    final void initializePlayerTurn() {
		//initialize first player's turn
        numActions = 1;
        coins = 0;
        numBuys = 1;
	}

    //Action phase
        //optional, need not play action card just because you have it
        //must do as much as you can of action card
    public void action() {
        while(numActions>0){
            List<Card> actionCards = Card.filter(hand, Type.ACTION);

            if(actionCards.size() == 0)
                return;

            //play next action card
            Card c = actionCards.get(0);
            if(c == null)
                return;
            
            playedCards.add(c);
            System.out.println(this.player_name + " played " + c);
            hand.remove(c);
            numActions--;
            c.play(this, gameState);

        }
    }

    public void playTreasureCards() {
        List<Card> treasureCards = Card.filter(hand, Type.TREASURE);

        for(int i=0; i<treasureCards.size(); i++) {
            Card c = treasureCards.get(i);
            coins += c.getTreasureValue();
            playedCards.add(c);
            hand.remove(c);
        }
    }

    //Buy phase
        //normally a player may only buy one card
    public void buy(GameState state) {
        if(!playedCards.contains(Card.getCard(state.cards, CardName.Feast)))
            playTreasureCards();
        while(numBuys>0) {
            Card toBuy = Card.getCard(state.cards, CardName.Copper);

            int random = (int)  Randomness.random.nextInt(3);

            List<Card> treasures = Card.filter(state.cards, Type.TREASURE);
            List<Card> victory = Card.filter(state.cards, Type.VICTORY);
            List<Card> actions = Card.filter(state.cards, Type.ACTION);

            //remove card from actions if its not in gameboard
            for(int i=0; i<actions.size(); i++) {
                if(!state.gameBoard.containsKey(actions.get(i))) {
                    actions.remove(i);
                    i--;
                }
            }
            
            if(random == 0) {
                //buy appropriate action
                toBuy = Card.sortCost(actions, state.cards, coins);
            }else if(random == 1) {
                //buy appropriate treasure
                toBuy = Card.sortCost(treasures, state.cards, coins);
            }else if(random == 2 && coins >= 2) {
                //buy appropriate victory
                toBuy = Card.sortCost(victory, state.cards, coins);
            }else {
                //buy copper
                toBuy = Card.sortCost(treasures, state.cards, coins);
            }

            gain(toBuy);
            state.removeCard(toBuy);
            coins -= toBuy.getCost();
            System.out.println(this.player_name + " bought " + toBuy);
            if(playedCards.contains(Card.getCard(state.cards, CardName.Feast)))
                break;
            numBuys--;
            
        }
    }

    //Clean-up phase
    public void cleanUp() {
        for(Card c : hand) {
            discarded.add(c);
        }
        hand.clear();
        for(Card c : playedCards) {
            discarded.add(c);
        }
        playedCards.clear();
        
    }

    private void shuffle() {
        int k;
        for(int i=0; i<deck.size(); i++) {
            k = (int) Randomness.nextRandomInt(deck.size());
            Collections.swap(deck, i, k);
        }
    }

    public void drawCard() {
        if(deck.isEmpty())
        {
            //shuffle deck with cards from discarded
            System.out.println("Shuffling deck with discarded pile");
            while(discarded.size() > 0) {
                deck.add(discarded.poll());
            }
            shuffle();
        }
        hand.add(deck.poll());
    }

    public void gain(Card card) {
        discarded.add(card);
        System.out.println(this.player_name + " gained " + card);
    }

    public void discard(Card card) {
        Boolean tmp = hand.remove(card) == true ? discarded.add(card) : false;
        System.out.println(this.player_name + " discarded " + card);
    }

    final int scoreFor() { 
        int score = 0;
        //score from hand
        for (Card c : hand)
            score += c.getScore();
        //score from discard
        for (Card c : discarded)
            score += c.getScore();
        //score from deck
        for (Card c : deck)
            score += c.getScore();
    
        return score;
    } 

    protected Player clone() throws CloneNotSupportedException {     
        Player clonePlayer=(Player) super.clone();     
        clonePlayer.hand = new ArrayList<Card>(hand);// int hand[MAX_PLAYERS][MAX_HAND];
        clonePlayer.deck = new LinkedList<Card>(deck);// int deck[MAX_PLAYERS][MAX_DECK];
        clonePlayer.discarded = new LinkedList<Card>(discarded); // int discard[MAX_PLAYERS][MAX_DECK];
        clonePlayer.playedCards = new ArrayList<Card>(playedCards); 
        return clonePlayer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
            
        sb.append(" --- " + this.player_name + " --- ");
        sb.append(" --- --------------------------- --- ");

        sb.append("Hand: " + this.hand);
        sb.append("Discard: " + this.discarded);
        sb.append("Deck: " + this.deck);
        sb.append("Played Cards: " + this.playedCards);
        sb.append("numActions: " + this.numActions);
        sb.append("coins: " + this.coins);
        sb.append("numBuys: " + this.numBuys);
        sb.append("\n");     

        return sb.toString();

    }

}