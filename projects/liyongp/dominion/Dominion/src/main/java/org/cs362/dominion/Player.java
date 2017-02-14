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
	private Random random;


	final GameState gameState;

	
	public Player(GameState gameState,String player_username) {
		this.player_username = player_username;
		this.gameState=gameState;
		random = new Random();
		//hand = new ArrayList<Card>();
		//deck = new LinkedList<Card>();
		//discard = new ArrayList<Card>();
		//playedCards = new ArrayList<Card>();
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
			discard.clear();
		}

		
		Card toDraw = deck.poll();
		hand.add(toDraw);// Add card to hand and hand count automatically will
							// be incremented since we use List
		System.out.println("draw " + toDraw);
		if(hand.isEmpty() == false){
			
			Collections.sort(hand);
		}
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
		         List<Card> actionCards = Card.filter(hand, Card.Type.ACTION); //Type - changed
		   
		         if (actionCards.size() == 0)
		            return;
		         
		         Card c = (Card)actionCards.get(0);
		         if (c == null)
			           return;
		         System.out.println("Player.actionPhase Card:" + c.toString());

			      playedCards.add(c);
			      numActions -= 1;

			      c.play(this, gameState);
			      hand.remove(c);
		      }
		   }
	   
	   final int scoreFor() { 
		   int score = 0;
		   int numGardenCard = 0;
		   int pTotalCards = 0;
		   //score and cards from hand
		   for (Card c : hand){
			   if(Card.getCard(hand, Card.CardName.Gardens) != null) {
				   if(c == Card.getCard(hand ,Card.CardName.Gardens)) 
					   numGardenCard++;
				   }
			   score += c.score();
			   pTotalCards++;
		   }
		 
		      //score and cards from discard
		   for (Card c : discard){
			   if(Card.getCard(discard, Card.CardName.Gardens) != null) {
				   if(c == Card.getCard(discard ,Card.CardName.Gardens)) 
					   numGardenCard++;
			   }
		       pTotalCards++;
		       score += c.score();
		   }
		      
		      //score and cards from deck
		   for (Card c : deck){
			   if(Card.getCard(deck, Card.CardName.Gardens) != null) {
				   if(c == Card.getCard(deck ,Card.CardName.Gardens)) 
					   numGardenCard++;
				   }
			   
			   pTotalCards++;
			   score += c.score();
		   }
		      
		      //cards from playedCards
		   for (Card c : playedCards){
			   if(Card.getCard(playedCards, Card.CardName.Gardens) != null) {
				   if(c == Card.getCard(playedCards ,Card.CardName.Gardens)) 
					   numGardenCard++;
				   }
			   pTotalCards++;
			   score += c.score(); // for Great Hall - action card
		   }

		   if(numGardenCard > 0)
			   score = score + numGardenCard*(pTotalCards/10);
		      
		    
		   return score;
	   }    
	   
	   public void playTtreasureCard() {
		   List<Card> treasure = Card.filter(hand, Card.Type.TREASURE);
		   for(Card c: treasure){
			   coins = coins + c.getTreasureValue();
			   playedCards.add(c);
			   hand.remove(c);
			   System.out.println(c.getCardName());
		   }
		   System.out.println(player_username + " has " + coins + " coins!\n");
	   }
	   
	   public void buyCard(GameState state) {
		   while(numBuys > 0){
			   if(coins == 0){
				   gain(Card.getCard(state.cards, Card.CardName.Copper));
				   System.out.println(player_username +" brought Copper\n");
				   numBuys--;
			   }
			   else{
				   int x = random.nextInt(17);
				   Card c = state.cards.get(x);
				   if((c.getCardName() == Card.CardName.Copper && coins >=6) || (c.getCardName() == Card.CardName.Copper && coins >=6)){
					   gain(Card.getCard(state.cards, Card.CardName.Gold));
					   System.out.println(player_username +" brought Gold\n");
					   numBuys--;
				   }
				   else if(c.getCardName() == Card.CardName.Copper && coins >= 3){
					   gain(Card.getCard(state.cards, Card.CardName.Silver));
					   System.out.println(player_username +" brought Silver\n");
					   numBuys--;
				   }
				   else if(coins >= c.getCost()){
					   gain(c);
					   System.out.println(player_username +" brought " +c.getCardName() + "\n");
					   numBuys--;
				   }
				   else
					   continue;
			   }
		   }  
	   }
	   final void endTurn() {
		   for(Card c: playedCards){
			   discard.add(c);
		   }
		   playedCards.clear();
		   for(Card c: hand){
			   discard.add(c);
		   }
		   hand.clear();
		   coins = 0;
		   numActions = 1;
		   numBuys = 1;
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
		     
       	 sb.append(" --- " + this.player_username + " --- \n");
       	 sb.append(" --- --------------------------- --- \n");
       	 sb.append("Hand: " + this.hand);
       	 sb.append("\nDiscard: " + this.discard);
       	 sb.append("\nDeck: " + this.deck);
       	 sb.append("\nPlayed Cards: " + this.playedCards);
       	 sb.append("\nnumActions: " + this.numActions);
       	 sb.append("\ncoins: " + this.coins);
       	 sb.append("\nnumBuys: " + this.numBuys);
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
