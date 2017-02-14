package org.cs362.dominion;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public final class Card implements Comparable<Card>, Cloneable{
	public static enum Type {
		ACTION, TREASURE, VICTORY;
	}

	public static enum CardName {
		/** The Treasure cards */
		Gold, Silver, Cooper, 
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer, Smithy, Village, Ambassador, Baron, councilRoom, Cutpurse, Embargo, Feast, Gardens, greatHall, Mine, Remodel;
		
	}
	/**
	 * @param enum CardName the name of the card (GOLD, SILVER, COPPER, 
	 * @param enum Type the type of the card (Victory, Treasure, or Kingdom
	 * @param int cost the cost of the card
	 * @param int score the score of the card, when the game is finished
	 * @param treasureValue  the treasure value of the treasure cards (cooper, silver, and gold cards)
	 */
	private final Type realType;
	private final CardName cardName;
	private final int cost, score, treasureValue;

	private Card(CardName cardName, Type type, int cost, int score, int treasureValue) {
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.realType = type;
		this.cardName = cardName;
	}

	/**
	 * Returns the type
	 */
	public Type getType() {
		return realType;
	}

	/**
	 * Returns the cardName
	 */
	public CardName getCardName() {
		return cardName;
	}
	/**
	 * Returns the cost
	 */
	public int getCost() {
		return cost;
	}


	/**
	 * Returns the treasureValue
	 */
	public int getTreasureValue() {
		return treasureValue; 
	}
	public int score() { 
			return score; 
		}
	
	public static List<Card> createCards() {
		List<Card> ret = new LinkedList<Card>();

		/** The Treasure cards  */
		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Cooper, Type.TREASURE, 0, 0, 1);
		ret.add(o);

		/** The Victory cards  */ 
		o = new Card(CardName.Province,Type.VICTORY, 	8, 6, 0);
		ret.add(o);
		o = new Card(CardName.Duchy,Type.VICTORY,		5, 3, 0);
		ret.add(o);		
		o = new Card(CardName.Estate,Type.VICTORY,		2, 1, 0);
		ret.add(o);		
		o = new Card(CardName.Curse,Type.VICTORY,		0, -1, 0);
		ret.add(o);				
		/** The Kingdom cards , it should more than 10 cards*/ 	
		
		o = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
		ret.add(o);
		o = new Card(CardName.Ambassador, Type.ACTION, 3,0,0);
		ret.add(o);
		o = new Card(CardName.Baron, Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Village,Type.ACTION,3,0,0);
		ret.add(o);	
		o = new Card(CardName.councilRoom, Type.ACTION, 5,0,0);
		ret.add(o);
		o = new Card(CardName.Cutpurse,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Embargo,Type.ACTION,2,0,0);
		ret.add(o);
		o = new Card(CardName.Feast,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Gardens,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.greatHall,Type.ACTION,3,1,0);
		ret.add(o);
		o = new Card(CardName.Mine,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Remodel,Type.ACTION,4,0,0);
		ret.add(o);
		
		
		return ret;
	}
	
	public void play(Player player, GameState state) {
		
		switch(this.cardName) {
		case Adventurer:
	    	  //Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
	    	  System.out.println("ADVENTURER CARD PLAYED");
	    	  System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
	    	  		"Put those Treasure cards into player hand and discard the other revealed cards.");
	    	  int count = 0;
	    	  int iter = 0;
	    	  do{
	    		  if(!(player.deck.isEmpty())){
	    		  	System.out.println("CARD: " + player.deck.get(iter));
	    		  if(player.deck.get(iter).getTreasureValue() != 0){
	    			  player.hand.add(player.deck.get(iter));
	    			  player.deck.remove(player.deck.get(iter));
	    			  
		        	 		
		        	 	
		        	  count++; //found a treasure card
	    			  
	    		  }
	    		  else if(player.deck.get(iter).getTreasureValue() == 0){
	    			player.discard(player.deck.get(iter));  
	    		  }
	    		  iter++;
	    		  }
	    	}while(count != 2 && iter < player.deck.size()); //we go through the deck until we find 2 treasure cards
	    	  
	    	  return;
		
	    case Ambassador:
			//Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.
			//Note: contains intentional error
	    	count = 0;
	    	
			if(player.deck != null){	
				System.out.println("AMBASSADOR CARD PLAYED");
				System.out.println("CARD: " + player.hand.get(0));
				state.gameBoard.put(player.hand.get(0), 1);
				for(Player p2 : state.players){ //if it isn't the current player, they need to get copy of card
					if(p2 != player){
						//p2.gain(c.getCard(gameBoard, cardName));
					}
				}
				player.hand.remove(0);
				//for(int i = 0; i < player.deck.size(); i++){
				//	if(player.deck.get(0) == player.deck.get(i) && count != 2){
					//	count++;
					//	state.gameBoard.put(player.deck.get(i), 1);
					//	player.hand.remove(i);
				//	}
				
				//}
			}
			player.numActions--;
			return;
		case Baron: //intentional error here: will not be able to gain 4 copper. Instead, can only gain estate
			System.out.println("BARON PLAYED");
			player.gain(getCard(state.cards, CardName.Estate));
			System.out.println("PLAYER GAINS ESTATE");
			
			return;
		
		case councilRoom:
			 System.out.println("COUNCIL ROOM PLAYED, PLAYER DRAWS 4 CARDS");
			 player.numBuys++;
			 player.drawCard();
	         player.drawCard();
	         player.drawCard();
	         player.drawCard();
	         System.out.println("OTHER PLAYER DRAWS A CARD");
	         for(Player p2 : state.players){ //each other player draws card
	        	 if(p2 != player){
	        		 p2.drawCard();
	        	 }
	         }
	        
	         return;
		case Cutpurse:
			System.out.println("CUTPURSE PLAYED, OTHER PLAYER DISCARDS COPPER OR REVEALS HAND");
			int flag = 0; //tells us if we found a copper card or not
			for(Player p2: state.players){
				if(p2 != player){
					for(int i = 0; i < p2.hand.size(); i++){
						if(p2.hand.get(i) == getCard(state.cards, CardName.Cooper)){
							p2.discard(p2.hand.get(i));
							flag = 1;
							break; //we only want to do this once, only one copper card will be removed
						}
						if(flag != 1){
							for(int j = 0; j < p2.hand.size(); j++){
								System.out.println("CARD: " + p2.hand.get(j));
							}
						}
					}
					
				}
			}
			
			
			return;
		
		case Embargo:
			System.out.println("EMBARGO PLAYED. PLAYER GAINS 2 COINS, EMBARGO TOKEN ADDED. ");
			player.coins += 2;
			state.embargoTokens++;
			player.playedCards.remove(getCard(player.playedCards, CardName.Embargo));
			
			return;
		
		case Gardens:
			int extraPoints = player.deck.size() / 10;
			player.extra += extraPoints;
		
			return;
		case Feast:
			System.out.println("FEAST PLAYED. CAN GET A CARD COSTING UP TO 5");
			player.playedCards.remove(getCard(player.playedCards, CardName.Feast));
			player.gain(getCard(state.cards, CardName.councilRoom)); //card costing 5
			System.out.println("PLAYER GETS COUNCILROOM");
			
			return;
		case greatHall:
			System.out.println("GREAT HALL PLAYED. PLUS ONE CARD AND ACTION");
			player.drawCard();
			player.numActions++;
			player.discard(getCard(player.hand, CardName.greatHall));
			
			return;
		case Mine: //Intentional error: Player gets silver card even if there is no copper in hand (even if they don't trash a card)
			System.out.println("MINE PLAYED. TRASH TREASURE FOR TREASURE WORTH UP TO 3 MORE THAN IT");
			for(int i = 0; i < player.hand.size(); i++){
				if(player.hand.get(i) == getCard(state.cards, CardName.Cooper)){
					player.hand.remove(getCard(player.hand, CardName.Cooper));
					break;
				}
			
			}
			player.gain(getCard(state.cards, CardName.Silver));
			
			return;
		case Remodel:
			System.out.println("REMODEL PLAYED. TRASH A CARD AND GAIN A CARD WORTH UP TO 2 MORE THAN IT");
			Card card3 = player.hand.get(0);
			player.hand.remove(0);
			
			int price = card3.cost;
			
			for(int i = 0; i < state.cards.size(); i++){
				if(state.cards.get(i).cost <= price + 2){ //grab a card worth up to 2 more than price
					player.gain(state.cards.get(i));
					break;
				}
			}
			
			return;
		case Smithy:
	         player.drawCard();
	         player.drawCard();
	         player.drawCard();
	      
	         return;
			
		case Village: //
	    	  System.out.println("VILLAGE PLAYED. PLAYER DRAWS A CARD AND GETS 2 EXTRA ACTIONS");
	    	  player.drawCard();
	    	  player.numActions += 2;
	    	  player.hand.remove(getCard(player.hand, CardName.Village));
	    	return;
			
			
		default: return;
		}
	}

	public static Card getCard(List<Card> cards,CardName cardName) {
		for(int i=0; i<cards.size();i++){
			if(cards.get(i).cardName.equals(cardName))
					return 	cards.get(i);
		}
		
		return null;
		
		
		
	}

	   public static List<Card> filter(Iterable<Card> cards, Type target) {
		      List<Card> out = new ArrayList<Card>();
		      for (Card c : cards)
		         if (c.getType() == target)
		            out.add(c);
		      return out;
		   }
	// this method will print out all the constant cards ENUM
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(" \n\t" + this.getCardName());
//		sb.append("-" + this.getType() + " ");
//		sb.append("\t\t Cost: " + this.cost + " ");
//		sb.append("\t\t Score: " + this.score + " ");
//		sb.append("\tTreasure Value: " + this.treasureValue);

		return sb.toString();

	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!super.equals(obj)) return false;
		if(!(obj instanceof Card)) return false;

		return cardName.equals(((Card) obj).cardName);
	}

	@Override
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return cardName.compareTo(o.cardName);
	}
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    } 

}
