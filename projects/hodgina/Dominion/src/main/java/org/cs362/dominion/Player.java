package org.cs362.dominion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.cs362.dominion.Card.Type;


public class Player implements Cloneable{
	List<Card> hand = new ArrayList<Card>();// int hand[MAX_PLAYERS][MAX_HAND];
	LinkedList<Card> deck = new LinkedList<Card>();// int
													// deck[MAX_PLAYERS][MAX_DECK];
	List<Card> discard = new ArrayList<Card>(); // int
												// discard[MAX_PLAYERS][MAX_DECK];
	List<Card> playedCards = new ArrayList<Card>();

	private Random rand_gen = new Random();

	String player_username;

	int numActions;
	int numBuys;
	int coins;

	final GameState gameState;


	public Player(GameState gameState,String player_username) {
		this.player_username = player_username;
		this.gameState=gameState;
	}



	final Card drawCard() {
	    if(discard.isEmpty() && deck.isEmpty()) {
            return null;
        }
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

            Card toDraw = deck.removeFirst();//poll();
            hand.add(toDraw);// Add card to hand and hand count automatically will
            // be incremented since we use List
            System.out.println("draw " + toDraw);
            Collections.sort(hand);
            return toDraw;
	}

	   final void initializePlayerTurn() {
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
		      System.out.println("-----------------------------------");
		      System.out.println(this.player_username+" gains "+card);
              System.out.println("-----------------------------------");
		      return true;
		   }
		 //Discard hand
	   public void discard(Card card) {
		     hand.remove(card);
		      discard.add(card);
		      System.out.println("Player:  "+player_username+" discards "+card);
		   }
	   public void playKingdomCard() {
           System.out.println("-----------------------------------");
           System.out.println("        Playing Kingdom Cards      ");
           System.out.println("-----------------------------------");
		      while (numActions > 0) {
		         List<Card> actionCards = Card.filter(hand, Type.ACTION);

		         if (actionCards.size() == 0)
		            return;

		         Card c = (Card)actionCards.get(0);
		         if (c == null)
			           return;
		         System.out.println("Player.actionPhase Card:" + c.toString());

			      playedCards.add(c);
			      hand.remove(c);
			      numActions -= 1;

			      c.play(this, gameState);
		      }
		   }
	   final int scoreFor() {
           int numGardens = 0;
           int numCards = 0;
           int score = 0;
           //score from hand
           for (Card c : hand)
               score += c.score();
           numCards++;
           //score from discard
           for (Card c : discard){
               if(Card.getCard(discard, Card.CardName.Gardends) != null) {
                   if(c == Card.getCard(discard ,Card.CardName.Gardends)) numGardens++;
               }
               score += c.score();
               numCards++;
           }
           //score from deck
           for (Card c : deck){
               if(Card.getCard(deck, Card.CardName.Gardends) != null){
                   if(c == Card.getCard(discard ,Card.CardName.Gardends)) numGardens++;
               }
               score += c.score();
               numCards++;
           }
           //System.out.println("numGardens: " + numGardens);
           if(numGardens > 0){
               score += numGardens*(numCards/10);
           }

           return score;
       }

	   public void playTtreasureCard() {
           System.out.println("-----------------------------------");
           System.out.println("        Playing TreasureCards      ");
           System.out.println("-----------------------------------");
		  List<Card> T_Cards = Card.filter(hand, Card.Type.TREASURE);
		  if(T_Cards.size() == 0) {
              return;
          }
		  System.out.println("Treasure Cards Played: ");

		  for(Card current_card : T_Cards){ //goes through and adds up all the treasure and puts them in the played cards pile
		  	coins += current_card.getTreasureValue();
		  	System.out.println(current_card.toString());
		  	playedCards.add(current_card);
		  	hand.remove(current_card);
		  	//System.out.println("Current Number of Coins is: " + coins);
		  }
		  System.out.println(player_username + " has " + coins + " to use in Buy Round.");
	   }
	   public void buyCard(GameState current_state) {

	    //NOTE: This I got a lot of help with this function from David Baugh

           System.out.println("-----------------------------------");
           System.out.println("            Buying Cards           ");
           System.out.println("-----------------------------------");


        while(coins > 0 && numBuys > 0){
           if (coins == 0 || coins == 1){
               gain(Card.getCard(current_state.cards, Card.CardName.Copper));
               current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Copper), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Copper)) - 1 );
           }
           else if (coins == 2) {
               int rand = rand_gen.nextInt(2);
               if (rand == 0 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Embargo)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Embargo)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Embargo));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Embargo), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Embargo)) - 1);
                   coins = coins - 2;
               } else if (rand == 1 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Estate)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Estate)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Estate));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Estate), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Estate)) - 1);
                   coins = coins - 2;
               }
           }
           else if (coins == 3) {//randomly choose 3 cost cards?
               int rand = rand_gen.nextInt(4);
               if (rand == 0 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Village)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Village)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Village));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Village), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Village)) - 1);
                   coins = coins - 3;
               } else if (rand == 1 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Ambassador)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Ambassador)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Ambassador));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Ambassador), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Ambassador)) - 1);
                   coins = coins - 3;
               } else if (rand == 2 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Great_hall)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Great_hall)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Great_hall));
                   coins = coins - 3;
               } else if (rand == 3 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Silver)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Silver)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Silver));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Silver), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Silver)) - 1);
                   coins = coins - 3;
               }

           } else if (coins == 4) {//randomly choose one of these
               int rand = rand_gen.nextInt(6);
               if (rand == 0 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Smithy)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Smithy)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Smithy));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Smithy), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Smithy)) - 1);
                   coins = coins - 4;
               } else if (rand == 1 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Baron)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Baron)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Baron));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Baron), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Baron)) - 1);
                   coins = coins - 4;
               } else if (rand == 2 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Cutpurse)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Cutpurse)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Cutpurse));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Cutpurse), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Cutpurse)) - 1);
                   coins = coins - 4;
               } else if (rand == 3 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Feast)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Feast)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Feast));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Feast), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Feast)) - 1);
                   coins = coins - 4;
               } else if (rand == 5 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Gardends)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Gardends)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Gardends));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Gardends), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Gardends)) - 1);
                   coins = coins - 4;
               }
           } else if (coins == 5) {//randomly choose one of these
               int rand = rand_gen.nextInt(4);
               if (rand == 0 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Mine)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Mine)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Mine));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Mine), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Mine)) - 1);
                   coins = coins - 5;
               } else if (rand == 1 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Council_room)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Council_room)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Council_room));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Council_room), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Council_room)) - 1);
                   coins = coins - 5;
               } else if (rand == 2 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Duchy)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Duchy)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Duchy));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Duchy), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Duchy)) - 1);
                   coins = coins - 5;
               }
               else if (rand == 3 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Laboratory)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Laboratory)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Laboratory));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Duchy), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Laboratory)) - 1);
                   coins = coins - 5;
               }

           } else if (coins == 6) {
               int rand = rand_gen.nextInt(2);
               if (rand == 0 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Gold)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Gold)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Gold));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Gold), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Gold)) - 1);
                   coins = coins - 6;
               } else if (rand == 1 && current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Adventurer)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Adventurer)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Adventurer));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Adventurer), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Adventurer)) - 1);
                   coins = coins - 6;
               }
           } else if (coins == 7) {
               if (current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Gold)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Gold)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Gold));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Gold), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Gold)) - 1);
                   coins = coins - 6;
               }
           } else if (coins >= 8){
               if (current_state.gameBoard.containsKey(Card.getCard(current_state.cards, Card.CardName.Province)) && current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Province)) > 0) {
                   gain(Card.getCard(current_state.cards, Card.CardName.Province));
                   current_state.gameBoard.put(Card.getCard(current_state.cards, Card.CardName.Province), current_state.gameBoard.get(Card.getCard(current_state.cards, Card.CardName.Province)) - 1);
                   coins = coins - 8;
               }
           }
           numBuys--;
       }
}
	   final void endTurn() {
           System.out.println("-----------------------------------");
           System.out.println("              End Turn             ");
           System.out.println("-----------------------------------");


           //reset all coins, actions and buys back to the start values
           coins = 0;
            numActions = 1;
            numBuys = 1;
            //discards all played cards
            for(Card current_card : playedCards){
                discard.add(current_card);
            }
            //discards everything in the hand
           for(Card current_card : hand){
               discard.add(current_card);
           }
           hand.clear(); //clears the hand list
           playedCards.clear(); //clears the played cards list
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

	    protected Player clone() throws CloneNotSupportedException {
	    	Player clonePlayer=(Player) super.clone();
	    	clonePlayer.hand = new ArrayList<Card>(hand);// int hand[MAX_PLAYERS][MAX_HAND];
			clonePlayer.deck = new LinkedList<Card>(deck);// int
															// deck[MAX_PLAYERS][MAX_DECK];
			clonePlayer.discard = new ArrayList<Card>(discard); // int
														// discard[MAX_PLAYERS][MAX_DECK];
			clonePlayer.playedCards = new ArrayList<Card>(playedCards);
			return clonePlayer;
	    }
}
