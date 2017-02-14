package org.cs362.dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public final class Card implements Comparable<Card>{
	public enum Type {
		ACTION, ACTION_ATTACK, ACTION_VICTORY, TREASURE, VICTORY;
	}

	public static enum CardName {
		/** The Treasure cards */
		Gold, Silver, Cooper, 
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer, Smithy, Village, Ambassador, Baron, CouncilRoom, Cutpurse, Embargo, Feast, Gardens, GreatHall, Mine, Minion ;
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
	public final int cost, score, treasureValue;

	Card(CardName cardName, Type type, int cost, int score, int treasureValue) {
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

	public int getTreasureValue() { return treasureValue; }
	public int score() { return score; }
	
	static List<Card> createCards() {
		List<Card> ret = new LinkedList<Card>();

		/** The Treasure cards  */
		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Cooper, Type.TREASURE, 0, 0, 1);
		ret.add(o);

		/** The Victory cards  */ 
		o = new Card(CardName.Province,Type.VICTORY, 8, 6, 0);
		ret.add(o);
		o = new Card(CardName.Duchy,Type.VICTORY,5, 1, 0);
		ret.add(o);		
		o = new Card(CardName.Estate,Type.VICTORY,2, 3, 0);
		ret.add(o);		
		o = new Card(CardName.Curse,Type.VICTORY,0, -1, 0);
		ret.add(o);				
		/** The Kingdom cards , it should more than 10 cards*/ 	
		
		o = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Village, Type.ACTION, 4, 0, 0); 
		ret.add(o); 
		o = new Card(CardName.Ambassador,Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.Baron,Type.ACTION, 3, 0, 0); 	
		ret.add(o); 
		o = new Card(CardName.CouncilRoom,Type.ACTION, 5, 0, 0);
		ret.add(o);  	
		o = new Card(CardName.Cutpurse,Type.ACTION, 5, 0, 0); 
		ret.add(o); 	
		o = new Card(CardName.Mine,Type.ACTION, 5, 0, 0); 	
		ret.add(o); 	
		o = new Card(CardName.Embargo,Type.ACTION, 2, 0, 0); 
 		ret.add(o); 
		o = new Card(CardName.Feast,Type.ACTION,4, 0, 0); 	
		ret.add(o); 
		return ret; 
	}
	
	public void play(Player player, GameState state) {
		
		switch(this.cardName) {
		case Adventurer:
	    	  //Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
	    	  System.out.println("Adventurer action card is being played---------------------------------");
		  	Card o;
			o = player.drawCard(); 
			System.out.println("Card being revealed: " + o); 
			//if(o->getType() == TREASURE)
			player.hand.add(o);  
	    	  	o = player.drawCard(); 
			System.out.println("Card being revealed: " + o); 
			player.hand.add(o); 
			//System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
	    	  	//	"Put those Treasure cards into player hand and discard the other revealed cards.");
			System.out.println("------------------------------------------------------------------"); 
		
			return;
		case Smithy:
	         System.out.println("Smithy action card is being played-------------------------------" ); 
		 player.drawCard();
	         player.drawCard();
	         player.drawCard();
		 System.out.println("-----------------------------------------------------------------"); 
			return;
			
		case Village:
		  System.out.println("Village action card is being played-------------------------------"); 
	    	  player.numActions = player.numActions + 2; 
		  System.out.println("------------------------------------------------------------------"); 
			return;
		
		case Ambassador:
		System.out.println("Chancellor action card is being played-------------------------------"); 
		//player.hand; 

		//System.out.println("The player reveals two cards from hand. Returns up to 2 copies of from hand to supply. THen each other player gains a cpy of it"); 	
		System.out.println("--------------------------------------------------------------------");
			return; 
		
		case Baron: 
		System.out.println("Baron action card is being played------------------------------------------"); 
		player.numBuys = player.numBuys + 1;
		player.hand.add(player.drawCard()); 
		//System.out.println("Player  gets +1 buy. Player can discard an Estate for +4  or can gain an estate"); 
		System.out.println("------------------------------------------------------------------"); 
			return; 
	
		case CouncilRoom: 
		System.out.println("CouncilRoom action card is being played--------------------------------------"); 
		player.drawCard();
		player.drawCard(); 
		System.out.println("---------------------------------------------------------------");
		//System.out.println("All other players must draw a card. Shuffle if needed");
		 
			return; 
		case Cutpurse: 
		System.out.println("Cutpurse action card is being played------------------------------------------"); 
		player.drawCard();
		player.drawCard();
		//System.out.println("+2 for player and all other players have to discard 1 copper cards or show a hand with no copper card"); 	
		System.out.println("--------------------------------------------------------------------------"); 
			return; 

		case Mine: 
		System.out.println("Mine action card is being played-----------------------------------------------"); 
		player.gain(this);
		player.hand.add(player.drawCard()); 
		//System.out.println("Player can trash a copper and gain silver or trash a silver and gain gold"); 
		System.out.println("------------------------------------------------------------------------"); 
			return; 
		
		case Embargo: 
		System.out.println("Embargo action card is being played------------------------------------------");
		player.gain(this);  
		player.coins = player.coins + 2; 
		//NOT PLAYING WITH CURSE CARDS
		
	//	System.out.println("+2, Trash this card, put embargo token on top of supply pile, when a player buys a gad, he also gets a curse card per embargo token on that pile"); 
		System.out.println("---------------------------------------------------------------------"); 
			return; 

		case Feast: 
		System.out.println("Feast action card is being played------------------------------------------------"); 
		player.gain(this);
		for(int i = 0; i < 5; i++){
			player.drawCard(); 
		}
		//System.out.println("Trash this card. Gain a card up to +5"); 
		System.out.println("-----------------------------------------------------------------");
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

		sb.append(" \t" + this.getCardName());
		sb.append("-" + this.getType() + " ");
		sb.append("\t\t Cost: " + this.cost + " ");
		sb.append("\t\t Score: " + this.score + " ");
		sb.append("\tTreasure Value: " + this.treasureValue);

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

	 protected Object clone() throws CloneNotSupportedException {         return super.clone();     }

}; 
