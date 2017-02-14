package dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;




public final class Card implements Comparable<Card>{
	public enum Type {
		ACTION, TREASURE, VICTORY;
	}

	public static enum CardName {
		/** The Treasure cards */
		Gold, Silver, Copper, 
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer, Ambassador, Baron, Councilroom, Cutpurse, Embargo, Feast, Gardens, Great_hall, Mine, Smithy, Village, Spy ;
	}

	public final Type realType;
	public final CardName cardName;
	public int cost, score, treasureValue, numberof;

	Card(CardName cardName, Type type, int cost, int score, int treasureValue, int numberof) {
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.realType = type;
		this.cardName = cardName;
		this.numberof = numberof;
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
	
	public int getCost(){
		return cost;
	}
	
	public int getValue(){
		return treasureValue;
	}

	public int getTreasureValue() { return treasureValue; }
	public int score(Player player) { return score; }
	public int getNumberof(){return numberof;}
	public void updateNumof(int newnum){ this.numberof=newnum; }
	
	static List<Card> createCards() {
		List<Card> ret = new LinkedList<Card>();

		/** The Treasure cards  */
		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3, 30);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2, 40);
		ret.add(o);
		o = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1, 32);
		ret.add(o);

		/** The Victory cards  */ 
		o = new Card(CardName.Province,Type.VICTORY, 	8, 6, 0, 8);
		ret.add(o);
		o = new Card(CardName.Duchy,Type.VICTORY,		5, 1, 0, 8);
		ret.add(o);		
		o = new Card(CardName.Estate,Type.VICTORY,		2, 3, 0, 2);
		ret.add(o);		
		o = new Card(CardName.Curse,Type.VICTORY,		0, -1, 0, 10);
		ret.add(o);				
		/** The Kingdom cards*/ 	
		
		o = new Card(CardName.Adventurer,Type.ACTION,6,0,0, 10);
		ret.add(o);
		o = new Card(CardName.Ambassador,Type.ACTION,6,0,0,10);
		ret.add(o);
		o = new Card(CardName.Baron,Type.ACTION,6,0,0,10);
		ret.add(o);		
		o = new Card(CardName.Councilroom,Type.ACTION,6,0,0,10);
		ret.add(o);
		o = new Card(CardName.Cutpurse,Type.ACTION,6,0,0,10);
		ret.add(o);
		o = new Card(CardName.Embargo,Type.ACTION,6,0,0,10);
		ret.add(o);
		o = new Card(CardName.Feast,Type.ACTION,6,0,0,10);
		ret.add(o);
		o = new Card(CardName.Gardens,Type.ACTION,6,0,0,10);
		ret.add(o);
		o = new Card(CardName.Great_hall,Type.ACTION,6,0,0,10);
		ret.add(o);
		o = new Card(CardName.Mine,Type.ACTION,6,0,0,10);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0,10);
		ret.add(o);	
		o = new Card(CardName.Village,Type.ACTION,3,0,0,10);
		ret.add(o);
		o = new Card(CardName.Spy,Type.ACTION,6,0,0,10);
		ret.add(o);
		return ret;
	}
	
	public void play(Player player, GameState state) {
		
		switch(this.cardName) {
		case Adventurer:
	    	  //Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
			  System.out.println("ADVENTURER PLAYED : Draw until you get 2 treasure cards\n");
	    	  List<Card> actionCards = Card.filter(player.deck, Type.TREASURE);
	    	  for(int i=0; i<2; i++){
	    	  Card c = (Card)actionCards.get(i);
	    	  System.out.println("Adventurer reveal # " + i + ": " + c.getCardName());
	    	  player.hand.add(c);
	    	  player.deck.remove(c);
	    	  }
	    	  
		
			return;
		case Ambassador:
			//Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.
			System.out.println("AMBASSADOR PLAYED : Reveal Cards and place in supply\n");
			Card c = (Card)player.hand.get(0);
			System.out.println("Ambassador card reveal: " + c.getCardName());
			state.piles[8]= state.piles[8]+1;
			
			return;
			
			
		case Baron:
			//+1 Buy
			//You may discard an Estate for +4 coins. If you don't, gain an Estate.
			System.out.println("BARON PLAYED : +1 Buy, Gain an Estate\n");
			player.numBuys+=1;
			player.gain(Card.getCard(state.cards,Card.CardName.Estate));
			
			return;
			
		case Councilroom:
			//+4 Cards
			//+1 Buy
			//Each other player draws a card.
			System.out.println("COUNCILROOM PLAYED : +4 Cards, +1 Buy\n");
	    	 for(int i =0; i<4; i++){ 
	    		 if(player.deck.size()==0){
		    		  player.shuffleDeck();
		    		  player.drawCard();
		    	  }
		    	  else if(player.deck.size()>0){
		    		  player.drawCard();
		    	  }
	    	 }
	    	 //player.numBuys+=1;
			return;
			
		case Cutpurse:
			//+2 coins
			//Each other player discards a Copper card (or reveals a hand with no Copper).
			System.out.println("CUTPURSE PLAYED : +2 coints, Everyone else discard a copper\n");
			player.discard(Card.getCard(player.hand,Card.CardName.Cutpurse));
			
			return;
		case Embargo:
			//Trash this card. Put an Embargo token on top of a Supply pile.
			System.out.println("EMBARGO PLAYED : Trash this card\n");
			player.hand.remove(Card.getCard(player.hand,Card.CardName.Embargo));
			return;
		case Feast:
			//Trash this card.
			//Gain a card costing up to Coin5.png.
			System.out.println("FEAST PLAYED : Trash this card\n");
			player.hand.remove(Card.getCard(player.hand,Card.CardName.Feast));
			return;
		case Gardens: 
			//Worth 1 victory point per 10 cards you have (round down).
			System.out.println("GARDENS PLAYED : Worth 1 victory point per 10 cards in deck\n");
			return;
		case Great_hall:
			//+1 Card
			//+1 Action
			System.out.println("GREAT HALL PLAYED : +1 Card +1 Action\n");
			if(player.deck.size()==0){
	    		  player.shuffleDeck();
	    		  player.drawCard();
	    	  }
	    	  else if(player.deck.size()>0){
	    		  player.drawCard();
	    	  }
			player.numActions+=1;
			return;
			
		case Mine:
			//You may trash a Treasure from your hand. Gain a Treasure to your hand costing up to Coin3.png more than it.
			System.out.println("MINE PLAYED\n");
			System.out.println("Didn't trash a treasure card with Mine");
			
			return;
		case Smithy:
			System.out.println("SMITHY PLAYED : +3 cards\n");
			for(int i=0; i<3; i++){
				if(player.deck.size()==0){
		    		  player.shuffleDeck();
		    		  player.drawCard();
		    	  }
		    	  else if(player.deck.size()>0){
		    		  player.drawCard();
		    	  }
			}
			return;
			
		case Village:
	    	  System.out.println("VILLAGE PLAYED : +1 card +2 Actions" + "\n");
	    	  if(player.deck.size()==0){
	    		  player.shuffleDeck();
	    		  player.drawCard();
	    	  }
	    	  else if(player.deck.size()>0){
	    		  player.drawCard();
	    	  }
	    	  //player.numActions += 2;
	    	 
			return;
		
			
			
		case Spy:	
			//+1 Card
			//+1 Action
			System.out.println("SPY PLAYED : +1 Card +1 Action\n");
			if(player.deck.size()>0){
					player.hand.add(player.deck.poll());
				
	    	}

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
	   @Override public String toString() {
	   StringBuilder sb = new StringBuilder();
	   sb.append("\t" + this.getCardName()); 
	   //sb.append("-" + this.getType() + " "); 
	  // sb.append("\t\t Cost: " + this.cost + " "); 
	  // sb.append("\t\t Score: " + this.score + " "); 
	   //sb.append("\tTreasure Value: " + this.treasureValue);
	   return sb.toString();
	   }
	   
	@Override 
	   public boolean equals(Object obj) { 
		   if(this == obj) return true;
		   if(!super.equals(obj)) return false; if(!(obj instanceof Card)) return false;
		   
		   return cardName.equals(((Card) obj).cardName);
	   }


	@Override
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return cardName.compareTo(o.cardName);
	}


}
