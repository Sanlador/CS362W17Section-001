package dominion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
//import dominion.Card.CardName;
import dominion.Card.Type;

public class Player implements Cloneable{
	List<Card> hand = new ArrayList<Card>();// int hand[MAX_PLAYERS][MAX_HAND];
	LinkedList<Card> deck = new LinkedList<Card>();// int
								// deck[MAX_PLAYERS][MAX_DECK];
	List<Card> discard = new ArrayList<Card>(); // int
								// discard[MAX_PLAYERS][MAX_DECK];
	List<Card> playedCards = new ArrayList<Card>();
	String player_username;
	int numActions;
	int numBuys;
        int tempcoins;
	int coins;

	   final GameState gameState;

	
	public Player(GameState gameState,String player_username) {
		this.player_username = player_username;
		this.gameState=gameState;
	}



	final Card drawCard() {
		if (deck.isEmpty()) {// Deck is empty
			// Step 1 Shuffle the discard pile back into a deck
			System.out.println("reshuffle the deck of "+ player_username);
			while (discard.size() > 0) {
				int ndx = (int) Randomness.nextRandomInt(discard.size());
				// Move discard to deck
				deck.add(discard.remove(ndx));
			}
		}

		Card toDraw = deck.poll();
		hand.add(toDraw);// Add card to hand and hand count automatically will
							// be incremented since we use List
		System.out.println(toDraw);
		Collections.sort(hand);
		return toDraw;
	}
        
        
        
        final Card drawType(Card inputType) {
		if (deck.isEmpty()) {// Deck is empty
			// Step 1 Shuffle the discard pile back into a deck
			System.out.println("Empty Deck");
                        return null;
		}

		Card toDraw = deck.poll();
                hand.add(toDraw);// Add card to hand and hand count automatically will
                if( toDraw.getType() == inputType.getType() ) {
                	System.out.println("Drawing: "+toDraw);
                        Collections.sort(hand);
                        return toDraw;
                }

                else {
                   discard(toDraw);
                   toDraw = drawType(inputType);
                }
			// be incremented since we use List
            return null;

	}
	
	   final void initializePlayerTurn() {
		   //initialize first player's turn
//		   state->numnumActions = 1;
//		   state->numnumBuys = 1;
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
		      System.out.println(this.player_username+" Gains "+card);
		      return true;
		   }		   
		 //Discard hand
	   public void discard(Card card) {
		     hand.remove(card);
		      discard.add(card);
		      //System.out.println("Player:  "+player_username+" discards "+card);
		   }
           public void discardType(Card card) {
              List<Card> Templist = Card.filter(hand, card.getType());
              if(Templist.size() > 0){
                hand.remove(Templist.get(0));
                discard.add(Templist.get(0));
              }  
		      //System.out.println("Player:  "+player_username+" discards "+card);
           }
           
            public void trash(Card card) {
		     hand.remove(card);
		      System.out.println("Player:  "+player_username+" Throw's away "+card);
		   }
            
	   public void playKingdomCard() {
		      while (numActions > 0) {
		         List<Card> actionCards = Card.filter(hand, Type.ACTION);
		   
		         if (actionCards.isEmpty()){
                             System.out.println("No action cards to play");
                             return;
                         }
		         
		         Card c = (Card)actionCards.get(0);
		         if (c == null)
			           return;
		         System.out.println("Playing Action Card: " + c.toString());
			      playedCards.add(c);
			      numActions -= 1;
			      c.play(this, gameState);
                              discard(c);
		      }
		   }
           
	   final int scoreFor(GameState state) { 
               int score = 0;
               int sum =0;
               Card q = Card.getCard(state.cards, Card.CardName.Gardens);
		   //score from hand
		      for (Card c : hand){
                          if(c.getCardName() == q.getCardName()){
                              sum++;
                          }    
		    	  score += c.score();
                      }
		      //score from discard
		      for (Card c : discard){
		    	  if(c.getCardName() == q.getCardName()){
                              sum++;
                          }    
		    	  score += c.score();
                      }
		      //score from deck
		      for (Card c : deck){
		    	  if(c.getCardName() == q.getCardName()){
                              sum++;
                          }    
		    	  score += c.score();
                      }
                      
                      if(sum > 0){
                          int sizes = deck.size() + discard.size()+ hand.size();
                          score = score+ sizes/10;
                      }
		      return score;
	   }    
	   
	   public void playTreasureCard() {
		List<Card> Treasure = Card.filter(hand, Type.TREASURE); 
                System.out.println("Playing Treasure:");
                int a = Treasure.size();
                    while (a > 0) {
		         if (Treasure.isEmpty())
		            return;
		         
		         Card c = (Card)Treasure.get(a-1);
		         if (c == null)
			           return;
		         System.out.println(c.toString());

			      playedCards.add(c);
			      coins = coins+ c.getTreasureValue();
                              discard(c);
                              a--;
                       }
                    System.out.println("coins = " + coins);
            }
	   
	public void buyCard(GameState state) {
            int dubCoins = 0;
		while(numBuys > 0) {
                        if(numBuys==2){
                            if(tempcoins == 0){
                                dubCoins = coins /2;
                                coins = coins/2;
                            }
                        }
			System.out.println("Buying");
                        //if (coins == 0) gain(Card.getCard(state.cards, Card.CardName.Copper));
                        if(coins == 1){
                            gain(Card.getCard(state.cards, Card.CardName.Copper));
                            coins = coins -1;
                        }
			else if (coins == 2){
                            int random = (int)  Randomness.random.nextInt(4);
                            //System.out.println("random3: " + random);
                            if(random ==0)gain(Card.getCard(state.cards, Card.CardName.Cellar));
                            if(random ==1)gain(Card.getCard(state.cards, Card.CardName.Chapel));
                            if(random ==2)gain(Card.getCard(state.cards, Card.CardName.Estate));
                            if(random ==3)gain(Card.getCard(state.cards, Card.CardName.Estate));
                            coins = coins -2;
                        }
                        else if (coins == 3) {//randomly choose 3 cost cards?
                            int random = (int)  Randomness.random.nextInt(5);
                                //System.out.println("random4: " + random);
				if(random ==0)gain(Card.getCard(state.cards, Card.CardName.Village));
				if(random ==1)gain(Card.getCard(state.cards, Card.CardName.Woodcutter));
                                if(random ==2)gain(Card.getCard(state.cards, Card.CardName.Chancellor));
                                if(random ==3)gain(Card.getCard(state.cards, Card.CardName.Silver));
                                if(random ==4)gain(Card.getCard(state.cards, Card.CardName.Silver));
                                coins = coins -3;
			} 
                        else if (coins == 4) {//randomly choose one of these
				int random = (int)  Randomness.random.nextInt(5);
                                //System.out.println("random4: " + random);
                                if(random ==0)gain(Card.getCard(state.cards, Card.CardName.Smithy));
				if(random ==1)gain(Card.getCard(state.cards, Card.CardName.Bureaucrat));
				if(random ==2)gain(Card.getCard(state.cards, Card.CardName.Gardens));
				if(random ==3)gain(Card.getCard(state.cards, Card.CardName.Feast));
				if(random ==4)gain(Card.getCard(state.cards, Card.CardName.Militia));
                                coins = coins -4;
			} 
                        else if (coins == 5) {//randomly choose one of these
                            int random = (int)  Randomness.random.nextInt(6);
                                //System.out.println("random5: " + random);
				if(random ==0)gain(Card.getCard(state.cards, Card.CardName.Council_Room));
                                if(random ==1)gain(Card.getCard(state.cards, Card.CardName.Festival));
                                if(random ==2)gain(Card.getCard(state.cards, Card.CardName.Laboratory));
				if(random ==3)gain(Card.getCard(state.cards, Card.CardName.Market));
                                if(random ==4)gain(Card.getCard(state.cards, Card.CardName.Duchy));
                                if(random ==5)gain(Card.getCard(state.cards, Card.CardName.Duchy));
                                coins = coins -5;
			} 
                        else if (coins == 6){
                            int random = (int)  Randomness.random.nextInt(2);
                            if(random ==0)gain(Card.getCard(state.cards, Card.CardName.Adventurer));
                            if(random ==1)gain(Card.getCard(state.cards, Card.CardName.Gold));
                            coins = coins-6;
                        }
                        else if (coins > 8){
                            gain(Card.getCard(state.cards, Card.CardName.Province));
                            coins = coins -8; 
                        }
                        
                        coins = coins + tempcoins + dubCoins;
                        tempcoins =0;
			numBuys--;
		}
	}
	   final void endTurn() {
               coins =0;
               System.out.println("Ending Turn...");
               System.out.println("Discarding Hand: ");
               while(!hand.isEmpty()){
                   Card c = hand.get(0);
                   System.out.println(c);
                   discard(c);
               } 
               System.out.println("Drawing new hand: ");
               while(hand.size() < 5){
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
		     
       	 sb.append(" --- ").append(this.player_username).append(" --- ");
       	 sb.append(" --- --------------------------- --- ");

       	 sb.append("Hand: " + this.hand);
       	 sb.append("Discard: " + this.discard);
       	 sb.append("Deck: " + this.deck);
       	 sb.append("Played Cards: " + this.playedCards);
       	 sb.append("numActions: " + this.numActions);
       	 sb.append("coins: " + this.coins);
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
