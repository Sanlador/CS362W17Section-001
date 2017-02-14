package dominion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



import dominion.Card.Type;


public class Player {
	List<Card> hand = new ArrayList<Card>();// int hand[MAX_PLAYERS][MAX_HAND];
	LinkedList<Card> deck = new LinkedList<Card>();// int
													// deck[MAX_PLAYERS][MAX_DECK];
	List<Card> discard = new ArrayList<Card>(); // int
												// discard[MAX_PLAYERS][MAX_DECK];
	List<Card> playedCards = new ArrayList<Card>();

	String player_username;

	int numActions;
	int numBuys;
	int coins;
        int vp;
	   final GameState gameState;

	
	public Player(GameState gameState,String player_username) {
		this.player_username = player_username;
		this.gameState=gameState;
	}
        
      
  
	
        public void shuffle() {	
            
            for (int i = deck.size()-1; i > 0; i--){	
                int ndx = (int) Randomness.nextRandomInt(deck.size());
                    Card a = deck.get(ndx);
                    deck.set(ndx,deck.get(i));
                    deck.set(i, a);
                
              
            }
               
	}

	final Card drawCard() {
		if (deck.isEmpty()) {// Deck is empty
			// Step 1 Shuffle the discard pile back into a deck
			
			while (discard.size() > 0) {
				
				// Move discard to deck
				deck.add(discard.remove(0));  
                                shuffle();
                                
			}
		}

		Card toDraw = deck.poll();
		hand.add(toDraw);// Add card to hand and hand count automatically will
							// be incremented since we use List
                coins+=toDraw.getTreasureValue();
		System.out.println("draw " + toDraw);
		//Collections.sort(hand);
		return toDraw;
               
	}
        
        
	
	   final void initializePlayerTurn() {
		   //initialize first player's turn
//		   state->numnumActions = 1;
//		   state->numnumBuys = 1;
		      numActions = 1;
		      coins = 0;
		      numBuys = 1;
                      vp=0;
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
		         
		         Card c = (Card)actionCards.get(0);
		         if (c == null)
			           return;
		         System.out.println("Player.actionPhase Card:" + c.toString());

			      playedCards.add(c);
			      numActions -= 1;
                              
			      c.play(this, gameState);
		      }
           }
	   final int scoreFor() { 
		   int score = 0;
                   int gardens=0; //count num gardens cards
                   int total=hand.size()+discard.size()+deck.size();
		   //score from hand
		      for (Card c : hand){
		    	  score += c.score(this);
                          if(c.getCardName()==Card.CardName.Gardens){
                              gardens+=1;
                          }
                      }
		      //score from discard
		      for (Card c : discard){
		    	  score += c.score(this);
                          if(c.getCardName()==Card.CardName.Gardens){
                              gardens+=1;
                          }
                      }
		      //score from deck
		      for (Card c : deck){
		    	  score += c.score(this);
                          if(c.getCardName()==Card.CardName.Gardens){
                              gardens+=1;
                          }
                      }
                    
                    int gPoints = (int)total/10;  //extra points from gardens
                    score+=gPoints;

		      
		    
		      return score;
	    }    
	   
	   public Card playTreasureCard() {  //returns treasure card FROM HAND or null
		List<Card> TCards = Card.filter(hand, Type.TREASURE);
		   
		if(TCards.size() == 0)
                    return null;
		         		      
                return TCards.get(0);   //BUG: returns first card every time
                
	   }
	   public void buyCard() {
		while (numBuys > 0) { //may cause infinite loop if have buys but not enough coins for anything
		    int random = (int)  Randomness.random.nextInt(gameState.cards.size());//
                    Card tmp = gameState.cards.get(random);              
                    Card.CardName name = tmp.getCardName();
                    if(gameState.gameBoard.get(Card.getCard(gameState.cards, name))==null || gameState.gameBoard.get(Card.getCard(gameState.cards, name))<1) continue;
                    if(coins<tmp.getCost()) continue;
                    
                    int cost = tmp.getCost();
                    boolean notPaid=true;
                    while(notPaid){
                        Card tCard = playTreasureCard();  //returns null if no treasure cards
                        System.out.println("Player.buyPhase Card:" + tCard.toString());
                        if(tCard==null){
                            notPaid=false;
                        }
                        else{
                            //minus cost & your coins by treasure value of tCard                         
                            cost-=tCard.getTreasureValue();
                            coins-=tCard.getTreasureValue();
                            playedCards.add(tCard);
                            if(cost<=0){
                                notPaid=false;
                            }
                        }
                    }
                    if(cost>0){
                        coins-=cost;
                    }
                    gain(tmp);
                    int val=gameState.gameBoard.get(Card.getCard(gameState.cards, name))-1;
                    gameState.gameBoard.put(Card.getCard(gameState.cards, name), val);
                    
                    numBuys--;
                }
	   }
	   final void endTurn(){
		//for (Card c : hand){
                  //  discard(c);
                //}      
                for(int i=0;i<hand.size();i++){
                    Card c = hand.get(i);
                    discard(c);
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