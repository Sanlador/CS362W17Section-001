package ORG.CS362.DOMINION;

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
		Adventurer, Smithy, Village, Ambassador, Baron, Council_Room, Embargo,
		Cutpurse, Feast, Gardens, Great_Hall, Mine, Cellar;
	}
	/**
	 * @param enum CardName the name of the card (GOLD, SILVER, COPPER, 
	 * @param enum Type the type of the card (Victory, Treasure, or Kingdom
	 * @param int cost the cost of the card
	 * @param int score the score of the card, when the game is finished
	 * @param treasureValue  the treasure value of the treasure cards (Copper, silver, and gold cards)
	 */
	
	private final Type realType;
	private final CardName cardName;
	private final int cost, score, treasureValue;

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
	
	public int getCost(){
		return cost;
	}

	public int getTreasureValue() { return treasureValue; }
	public int score(Player player) { return score; }
	
	static List<Card> createCards() {
		List<Card> ret = new LinkedList<Card>();

		/** The Treasure cards  */
		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);
		ret.add(o);

		/** The Victory cards  */ 
		o = new Card(CardName.Province,Type.VICTORY, 	8, 6, 0);
		ret.add(o);
		o = new Card(CardName.Duchy,Type.VICTORY,		5, 1, 0);
		ret.add(o);		
		o = new Card(CardName.Estate,Type.VICTORY,		2, 3, 0);
		ret.add(o);		
		o = new Card(CardName.Curse,Type.VICTORY,		0, -1, 0);
		ret.add(o);				
		
		
		
		/** The Kingdom cards , it should more than 10 cards*/ 	
		o = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Village,Type.ACTION,3,0,0);
		ret.add(o);	
		o = new Card(CardName.Ambassador,Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.Baron,Type.ACTION,4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Council_Room,Type.ACTION,5,0,0);
		ret.add(o);
		o= new Card(CardName.Cutpurse, Type.ACTION, 4,0,0);
		ret.add(o);
		o = new Card(CardName.Embargo, Type.ACTION, 4,0, 0);
		ret.add(o);
		o = new Card(CardName.Feast, Type.ACTION, 4,0,0);
		ret.add(o);
		o = new Card(CardName.Gardens, Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Great_Hall, Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.Mine, Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Cellar, Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Village, Type.ACTION,4,0,0);
		ret.add(o);
			
		return ret;
		
	}
	
	public void play(Player player, GameState state) {
		
		int i;
		Card temp;
		int reveal = 0;
		switch(this.cardName) {
	
		case Adventurer:
	    	  //Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into hand
	    	for(i = 0; i < player.deck.size(); i++){
	    	if(reveal < 2){
	    		temp = player.deck.get(i);
	    		System.out.println("Reveal----->" + temp.cardName);
	    		if (temp.realType != Type.TREASURE){
	    			player.hand.add(temp);
	    			player.deck.remove(temp);
	    			reveal++;	
	    		}
	    	}	
	    	}
			return;
		case Smithy:
			 int random = (int)  Randomness.random.nextInt(state.cards.size());
	         temp = state.cards.get(random);
	         state.cards.remove(random);
	     //    player.deck.add(temp);
	       //  player.drawCard();
	        // player.drawCard();
	         //get 3 cards
			return;
		case Village:
	    	player.drawCard();
	    	player.numActions++;
	    	player.numActions++;
			return;
		case Ambassador:
			System.out.println("Reveal----> Ambassador");
			return;
		case Baron:
			player.numBuys ++;
			player.coins = player.coins + 4;
			return;
			//increase number of buys, then pick up +$4
		case Council_Room:
			player.drawCard();
			player.drawCard();
			player.drawCard();
			player.drawCard();
			player.numBuys++;
			return;
		case Cutpurse:
			player.coins = player.coins + 2;
			return;
		
		case Embargo:
			player.coins = player.coins + 2;
			return;
			
		case Feast:
			temp = player.hand.get(0);
			player.discard(temp);						
			return;
		
		case Gardens:	//for every 10 cards in the deck, add 1 coin
			for(i = 0; i < player.deck.size(); i++){
				if(i == 10){
					player.coins++;
				}
			}	
			return;
		
		case Great_Hall:
			player.drawCard();
			player.numActions++;
			return;
			
		case Mine:			//discard treasure card and add $3 + value of discarded card
			for(i = 0; i < player.hand.size(); i++){
				temp = player.hand.get(i);
				if(temp.realType == Type.TREASURE){
					player.discard(temp);
					player.coins = player.coins + (temp.cost + 3);
					return;
				}
			}
			
			return;
			
		case Cellar:
			player.numActions++;
			i = 2;
			temp = player.hand.get(i);
			player.discard(temp);
			player.drawCard();
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


}
