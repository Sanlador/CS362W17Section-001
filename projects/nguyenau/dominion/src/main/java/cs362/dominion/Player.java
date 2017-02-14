package cs362.dominion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cs362.dominion.Card.Type;

public class Player {
	List<Card> hand = new ArrayList<Card>();
	LinkedList<Card> deck = new LinkedList<Card>();
	List<Card> discard = new ArrayList<Card>();
	List<Card> playedCards = new ArrayList<Card>();

	String playerName;
    int actions, buys, coins;
    final GameState gameState;

    // CONSTRUCTOR
	public Player(GameState gameState, String playerName) {
		this.playerName = playerName;
		this.gameState = gameState;
	}

	final Card drawCard() {
		if (deck.isEmpty()) {// Deck is empty
			// Step 1 Shuffle the discard pile back into a deck
			System.out.println("Shuffling in the discard pile of " + playerName + " to draw FIVE cards.");
			while (discard.size() > 0) {
				int ndx = (int) Randomness.nextRandomInt(discard.size());
				// Move discard to deck
				deck.add(discard.remove(ndx));
			}
		}

		Card toDraw = deck.poll();
		hand.add(toDraw);// Add card to hand and hand count automatically will
							// be incremented since we use List
		System.out.println(this.playerName + " draws: " + toDraw.getCardName() + ". ");
		Collections.sort(hand);
		return toDraw;
	}
	
    public final void initializePlayerTurn() {
          actions = 1;
          coins = 0;
          buys = 1;
          //Shuffle your starting 10 cards (7 Coppers & 3 Estates) and place them face-down as your Deck. Draw the top
          //5 cards as your starting hand
          for (int i = 0; i < 5; i++) {
              drawCard();
          }
    }

    // Adding a card to the deck. In actuality, cards are added to teh discard pile to be shuffled into the deck.
    public final boolean addToDeck(Card c) {
        discard.add(c);
        System.out.println(c.getCardName()+" added to deck.");
        return true;
    }
    
    // Discard a card from the player's hand.
    public void discard(Card c) {
        if(hand.contains(c)) {
            hand.remove(c);
            discard.add(c);
            System.out.println(this.playerName+" has discarded: "+c.getCardName()+".");
        }
        else {
            System.out.println(this.playerName+": No " + c.getCardName() + " cards in the hand, so could not discard.");
        }
    }
    
    // ACTION PHASE: PLAYER MUST PLAY ALL OF HIS ACTION CARDS.
    public void playKingdomCard() {
        System.out.println(">>> ACTION PHASE\nAuto: Playing all possible actions.");
        while (actions > 0) {
            List<Card> actionCards = Card.filter(hand,Type.ACTION);

            if (actionCards.size() == 0)
                return;

            Card c = (Card)actionCards.get(0);
            
            if (c == null)
                return;
            
            System.out.println(playerName + " plays action: " + c.getCardName() + ".");

            // this bit handles trashing Feast; ensuring that it will not be cycled back into the deck via playedcards
            // so when this branch doesn't execute, a Feast disappears forever
            if ((c.getCardName() != Card.CardName.Feast) && (c.getCardName() != Card.CardName.Embargo)) {
                playedCards.add(c);
            }
            
            actions -= 1;

            c.play(this,gameState);
            hand.remove(c);     // VERY IMPORTANT!!! Played card cannot be in hand and in play area at same time.
        }
    }
    
    // BUY PHASE PART 1: PLAYER
    public void playTreasureCard() {
        System.out.println(">>> BUY PHASE\nAuto: playing all treasures and randomly buying a card.");
        List<Card> treasureCards = Card.filter(hand,Type.TREASURE);
        int inSize = treasureCards.size();
        if (treasureCards.size() == 0)
            return;
        
        for(int i=0;i<inSize;i++) {
            Card c = (Card)treasureCards.get(0);
            System.out.println(playerName + " plays treasure: " + c.getCardName() + ".");
            playedCards.add(c);
            coins += c.getTreasureValue();
            hand.remove(c);     // VERY IMPORTANT!!! Played card cannot be in hand and in play area at same time.
            treasureCards = Card.filter(hand,Type.TREASURE);        // update filtered cards in hand
        }
    }
    
    public void buyCard() {
        if (coins == 0) {       // Don't buy a card if you don't have any coins
            return;
        }
        boolean bought = false, enableActionBuy = true, enableVictoryBuy = true, enableTreasureBuy = true;
        
        while(buys != 0) {       // So long as you have one buy
            bought = false;     // Haven't bought the card yet
            int category = Randomness.nextRandomInt(30);        // Randomly select a category, stratified random
            
            if ((category > 21) && (enableActionBuy)) {     // Elect to buy an action card
                List<Card> buyableCards = Card.filter(gameState.cards,Type.ACTION);         // Filter buyable cards
                Collections.shuffle(buyableCards);      // Mix up the card selection
                if(!bought) {
                    for(int pick=0;pick<buyableCards.size();pick++) {       // Iteratively select a filtered card and buy it if able
                        Card chosen = buyableCards.get(pick);
                        if(gameState.gameBoard.get(chosen) == 0) {
                            return;
                        }
                        else if(coins >= chosen.getCost()) {
                            coins -= chosen.getCost();
                            bought = true;
                            buys -= 1;
                            discard.add(chosen);
                            System.out.println(playerName + " buys: " + chosen.getCardName() + ".");
                            if(chosen.getEmTokens() > 0) {
                                System.out.println(chosen.getCardName() + " has " + chosen.getEmTokens() + " Embargo tokens.");
                                Card embargoCurse = Card.getCard(gameState.cards,Card.CardName.Curse);
                                for(int curseByEmbargo=0;curseByEmbargo<chosen.getEmTokens();curseByEmbargo++) {
                                    if(gameState.gameBoard.get(embargoCurse) > 0) {
                                        discard.add(embargoCurse);
                                        gameState.gameBoard.put(embargoCurse,gameState.gameBoard.get(embargoCurse)-1);
                                        System.out.println(playerName + " has gained a Curse via an Embargo token.");
                                    }
                                    else {
                                        System.out.println("No curses left in supply, so no further curses can be gained from this Embargo.");
                                        break;
                                    }
                                }
                            }
                            gameState.gameBoard.put(chosen,gameState.gameBoard.get(chosen)-1);
                            break;
                        }
                    }
                    if(!bought)
                        enableActionBuy = false;        // if no card was purchased, then lock out this category
                }
            }
            else if ((category > 10) && (category < 21) && (enableVictoryBuy)) {
                List<Card> buyableCards = Card.filter(gameState.cards,Type.VICTORY);
                Collections.shuffle(buyableCards);
                if(!bought) {
                    for(int pick=0;pick<buyableCards.size();pick++) {
                        Card chosen = buyableCards.get(pick);
                        if((int)gameState.gameBoard.get(chosen) == 0)
                            return;
                        if((coins >= chosen.getCost()) && (chosen.getCardName() != Card.CardName.Curse)) {
                            coins -= chosen.getCost();
                            bought = true;
                            buys -= 1;
                            discard.add(chosen);
                            System.out.println(playerName + " buys: " + chosen.getCardName() + ".");
                            if(chosen.getEmTokens() > 0) {
                                System.out.println(chosen.getCardName() + " has " + chosen.getEmTokens() + " Embargo tokens.");
                                Card embargoCurse = Card.getCard(gameState.cards,Card.CardName.Curse);
                                for(int curseByEmbargo=0;curseByEmbargo<chosen.getEmTokens();curseByEmbargo++) {
                                    if(gameState.gameBoard.get(embargoCurse) > 0) {
                                        discard.add(embargoCurse);
                                        gameState.gameBoard.put(embargoCurse,gameState.gameBoard.get(embargoCurse)-1);
                                        System.out.println(playerName + " has gained a Curse via an Embargo token.");
                                    }
                                    else {
                                        System.out.println("No curses left in supply, so no further curses can be gained from this Embargo.");
                                        break;
                                    }
                                }
                            }
                            gameState.gameBoard.put(chosen,gameState.gameBoard.get(chosen)-1);
                            break;
                        }
                    }
                    if(!bought)
                        enableVictoryBuy = false;        // if no card was purchased, then lock out this category
                }
            }
            else if ((category < 11) && (enableTreasureBuy)) {
                List<Card> buyableCards = Card.filter(gameState.cards,Type.TREASURE);
                Collections.shuffle(buyableCards);
                if(!bought) {
                    for(int pick=0;pick<buyableCards.size();pick++) {
                        Card chosen = buyableCards.get(pick);
                        if((int)gameState.gameBoard.get(chosen) == 0)
                            return;
                        if((coins >= chosen.getCost())) {
                            coins -= chosen.getCost();
                            bought = true;
                            buys -= 1;
                            discard.add(chosen);
                            System.out.println(playerName + " buys: " + chosen.getCardName() + ".");
                            if(chosen.getEmTokens() > 0) {
                                System.out.println(chosen.getCardName() + " has " + chosen.getEmTokens() + " Embargo tokens.");
                                Card embargoCurse = Card.getCard(gameState.cards,Card.CardName.Curse);
                                for(int curseByEmbargo=0;curseByEmbargo<chosen.getEmTokens();curseByEmbargo++) {
                                    if(gameState.gameBoard.get(embargoCurse) > 0) {
                                        discard.add(embargoCurse);
                                        gameState.gameBoard.put(embargoCurse,gameState.gameBoard.get(embargoCurse)-1);
                                        System.out.println(playerName + " has gained a Curse via an Embargo token.");
                                    }
                                    else {
                                        System.out.println("No curses left in supply, so no further curses can be gained from this Embargo.");
                                        break;
                                    }
                                }
                            }
                            gameState.gameBoard.put(chosen,gameState.gameBoard.get(chosen)-1);
                            break;
                        }
                    }
                    if(!bought)
                        enableTreasureBuy = false;        // if no card was purchased, then lock out this category
                }
            }
            if ((!enableActionBuy) && (!enableVictoryBuy) && (!enableTreasureBuy)) {     // No cards could be purchased, so stop trying to buy
                System.out.println(playerName+": Could not buy a card. Too pricey.");
                return;
            }
        }
    }
    
    public final void endTurn() {
        System.out.println(">>> END PHASE");
        System.out.println("Cleaning up; discarding hand/played pile and resetting actions/buys/coins.");
        
        for(Card c : hand) {        // discard hand
            discard.add(c);
        }
        hand.clear();
        
        for(Card c : playedCards) {     // discard played cards
            discard.add(c);
        }
        playedCards.clear();
        
        for(int i=0;i<5;i++) {      // draw 5 new cards
            drawCard();
        }
        
        actions = 1;        // reset everything
        buys = 1;
        coins = 0;
        System.out.print("\u001b[2J\u001b[H");      // clear the screen
        System.out.flush();
    }
    
    // FOLLOWING METHODS ARE INFOMRATIONAL
    public void BoardQuery(){
        System.out.println(" --- " + this.playerName + " --- \n");
        System.out.println(this.gameState.toString());
    }
    
    final int scoreFor() {
        int VP = 0, gards = 0, gardScore = 0;
        //score from deck
        for (Card c : deck) {
            VP += c.score();
            if(c.getCardName() == Card.CardName.Gardens) {
                gards += 1;
            }
        }
        //score from hand
        for (Card c : hand) {
            VP += c.score();
            deck.add(c);
            if(c.getCardName() == Card.CardName.Gardens) {
                gards += 1;
            }
        }
        //score from discard
        for (Card c : discard) {
            VP += c.score();
            deck.add(c);
            if(c.getCardName() == Card.CardName.Gardens) {
                gards += 1;
            }
        }
        
        //score from gardens versus all cards the player possesses
        if(gards > 0) {
            gardScore = (deck.size()/10);
            gardScore = gardScore-(gardScore%1);
            gardScore = gardScore*gards;
            //System.out.println(playerName+"GS: "+gardScore+"D"+deck.size()+"G"+gards);
            VP += gardScore;
        }
        return VP;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
         
        sb.append(" --- " + this.playerName + " --- \n");
        sb.append(" --- --------------------------- --- \n");
        
        sb.append("Hand:\t\t [ ");
        for (Card c : this.hand)
            sb.append(">" + c.getCardName() + " ");
        sb.append("] \n");
        
        sb.append("Discard:\t [ ");
        for (Card c : this.discard)
            sb.append(">" + c.getCardName() + " ");
        sb.append("] \n");
        
        sb.append(deck.size()+"Deck:\t\t [ ");
        for (Card c : this.deck)
            sb.append(">" + c.getCardName() + " ");
        sb.append("] \n");
        
        sb.append("Played Cards:\t [ ");
        for (Card c : this.playedCards)
            sb.append(">" + c.getCardName() + " ");
        sb.append("] \n");
        
        sb.append("Actions Left:\t " + this.actions + "\n");
        sb.append("Coins:\t\t " + this.coins + "\n");
        sb.append("Buys Left:\t " + this.buys + "\n");
        sb.append("\n");
        
        return sb.toString();
    }
}
