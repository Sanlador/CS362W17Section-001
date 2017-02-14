package org.cs362.dominion;

import org.cs362.dominion.Card.Type;

import java.util.*;

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
	int coins;
	final GameState gameState;
    private Random ran = new Random();
	
	public Player(GameState gameState,String player_username) {
		this.player_username = player_username;
		this.gameState=gameState;
		//deck = new LinkedList<Card>();
		//discard = new ArrayList<Card>();
		//hand=new ArrayList<Card>();
		//playedCards=new ArrayList<Card>();
	}



	final Card drawCard() {
		if (deck.isEmpty()) {// Deck is empty
			// Step 1 Shuffle the discard pile back into a deck
			System.out.println("reshuffle the deck of the "
					+ player_username + " to draw cards");
			while (discard.size() > 0) {
				int random = ran.nextInt(discard.size());
				// Move discard to deck
				deck.add(discard.remove(random));
			}
		}

		Card toDraw = deck.poll();
		//if(toDraw != null) {
			hand.add(toDraw);// Add card to hand and hand count automatically will
			// be incremented since we use List
			System.out.println(player_username + "draw " + toDraw);
		//}
		Collections.sort(hand);
		return toDraw;
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
		   if(hand.size()==0){
		   drawCard();
		   drawCard();
		   drawCard();
		   drawCard();
		   drawCard();}

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
		      System.out.println("Player:  "+this.player_username+" discards "+card);
		   }

	   public void playKingdomCard() {
		      while (numActions > 0) {
		         List<Card> actionCards = Card.filter(hand, Type.ACTION);
		         Iterator<Card> iter=actionCards.iterator();
		         while(iter.hasNext()){
		         	Card newCard = iter.next();
		         	if(newCard==Card.getCard(gameState.cards, Card.CardName.Gardens)){
		         		iter.remove();
					}
				 }
		         if (actionCards.size() == 0)
		            return;
		         
		         Card c = (Card)actionCards.get(0);
		         if (c == null){
			           return;}
		         System.out.println("Player.actionPhase Card:" + c.toString());

			      playedCards.add(c);
			      hand.remove(c);
			      numActions -= 1;

			      c.play(this, gameState);
		      }
		   }

        //What does final works?
	    final int scoreFor() {
		   int score = 0;
		   int num1=0, num2=0;
		      for (Card c : hand) {
				  score += c.score();
				  num1++;
			  }
			  //I have a question here: For Card c in discard, this loop sometime doesnt work. In this project, I used For int i=0... takes places in somewhere. I need to check this out.
		      for (Card c : discard) {
		      	if(Card.getCard(discard, Card.CardName.Gardens)!=null){
		      		if(c==Card.getCard(discard, Card.CardName.Gardens)){
		      			num2++;
					}
				}
		      	score += c.score();
		      	num1++;
			  }
		      for (Card c : deck){
				  if(Card.getCard(discard, Card.CardName.Gardens)!=null){
					  if(c==Card.getCard(discard, Card.CardName.Gardens)){
						  num2++;
					  }
				  }
				  score += c.score();
				  num1++;
			  }
		    if(num2>0){
		      	score+=num2*(num1/10);
			}
		      return score;
	   }    
	   
	   public void playTtreasureCard() {
		   //System.out.println(" --- --------------------------- --- ");
		   List<Card> CardTreasure = Card.filter(hand, Card.Type.TREASURE);
		   if(CardTreasure.size() == 0) return;
		   System.out.println("Treasure Cards Played:");
		   for(Card c: CardTreasure)
		   {
			   coins += c.getTreasureValue();
			   System.out.println(c.toString());
			   playedCards.add(c);
			   hand.remove(c);
		   }
		   System.out.println(player_username + "'s coins: " + coins);
		   //System.out.println(" --- --------------------------- --- ");
	   }

	   //buyCard function: Because I truly dont understand how this one working. So I choose the more reasonable one: depend on your coins.
	   //But in my mind, we can have more reasonable AI, for example: add more choose or call IQ: make level on card. then choose high level card first.
	   //working only in AI not human.
	   public void buyCard(GameState state) {//need more changes
		   //System.out.println(" --- --------------------------- --- ");
		   //for each coin, choose a random card to buy. Actually we can add something like combine two cards.
		   	Shop.buying(state, this);
		   	//I have made a new class calls Shop. The reason to do this is make sure the Shop works well (easy to test and check)
            //I write a single testing for buying.
		   }




	   final void endTurn() {
		   //System.out.println(" --- --------------------------- --- ");
		   coins = 0;
		   numActions = 1;
		   numBuys = 1;
		   for(int i = 0; i<playedCards.size(); i++) {
			   discard.add(playedCards.get(i));
		   }
		   for(int j = 0; j<hand.size();j++) {
			   discard.add(hand.get(j));
		   }
		   //System.out.println("discard has : "+discard.size());
  			//System.out.println(" --- --------------------------- --- ");
	   }
	   
	   
	   public void printStateGame(){
		   System.out.println(" --- " + this.player_username + " --- ");
		   //System.out.println(" --- --------------------------- --- ");
     		System.out.println(this.gameState.toString()); 
     		//System.out.println(" --- --------------------------- --- ");
	   }
	   @Override
		public String toString() {
	   	StringBuilder sb = new StringBuilder();
		     
       	 sb.append(" --- " + this.player_username + " --- \n");
       	 sb.append(" --- --------------------------- ---\n ");

       	 sb.append("Hand: " + this.hand + " ");
       	 sb.append("Discard: " + this.discard + " ");
       	 sb.append("Deck: " + this.deck+ " ");
       	 sb.append("Played Cards: " + this.playedCards+ " ");
       	 sb.append("numActions: " + this.numActions+" ");
       	 sb.append("coinss: " + this.coins+ " ");
       	 sb.append("numBuys: " + this.numBuys+ " ");
       	 sb.append("\n");

		     return sb.toString();

		}
	   
	    protected Player clone() throws CloneNotSupportedException {
	    	Player clonePlayer=(Player) super.clone();
	    	clonePlayer.hand = new ArrayList<Card>(hand);// int hand[MAX_PLAYERS][MAX_HAND];
			clonePlayer.deck = new LinkedList<Card>(deck);// int// deck[MAX_PLAYERS][MAX_DECK];
			clonePlayer.discard = new ArrayList<Card>(discard); // int// discard[MAX_PLAYERS][MAX_DECK];
			clonePlayer.playedCards = new ArrayList<Card>(playedCards);
			return clonePlayer;
	    } 	   
}
