package dominion;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public final class Card implements Comparable<Card>, Cloneable{
	public static enum Type {
		ACTION, TREASURE, VICTORY;
	}

	public static enum CardName {
		/** The Treasure cards */
		Gold, Silver, Copper, 
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer, Smithy, Village, Ambassador, Baron, Council_Room, Cutpurse, Embargo, Feast, Gardens, Great_Hall, Minion, Mine;
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
		o = new Card(CardName.Baron,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Council_Room,Type.ACTION,5,0,0);
		ret.add(o);	
		o = new Card(CardName.Cutpurse,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Embargo,Type.ACTION,2,0,0);
		ret.add(o);	
		o = new Card(CardName.Feast,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Gardens,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Great_Hall,Type.ACTION,3,0,0);
		ret.add(o);	
		o = new Card(CardName.Minion,Type.ACTION,5,0,0);
		ret.add(o);	
		o = new Card(CardName.Mine,Type.ACTION,5,0,0);
		ret.add(o);	
		return ret;
	}
	
	public void play(Player player, GameState state) {
		
		switch(this.cardName) {
		case Adventurer:
	    	  //Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
	    	  System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
	    	  		"Put those Treasure cards into player hand and discard the other revealed cards.");
	    	  int count = 0;
	    	  Iterator<Card> itor = player.deck.iterator();
	    	  while(itor.hasNext()){
	    		  if(count < 2){
	    			  Card x = itor.next();
	    			  if(x == getCard(state.cards, CardName.Gold) || x == getCard(state.cards, CardName.Silver) || x == getCard(state.cards, CardName.Copper)){
	    				  player.addHand(x);
	    				  itor.remove();
	    				  count++;
	    			  }
	    			  else{
	    				  player.discard(x);
	    				  itor.remove();
	    			  }
	    		  }
	    		  else{
	    			  break;
	    		  }
	    	
	    	  }
			return;
			
		case Smithy:
			 System.out.println("+3 Cards.");
			 player.drawCard();
	         player.drawCard();
	         player.drawCard();
			return;
			
		case Village:
	    	  System.out.println("The player draw +1 Cards.");
	    	  System.out.println("The player gets +2 play Actions.");
	    	  player.drawCard();
	    	  player.numActions += 2;
			return;
			
		case Ambassador:
			System.out.println("Reveal a card from your hand.");
			System.out.println("Return up to 2 copies of it from your hand to the Supply.");
			System.out.println("Then each other player gains a copy of it.");

			return;
			
		case Baron:
			System.out.println("+1 Buy. You may discard an Estate for +4.");
			System.out.println("If you don't, gain an Estate.");
			player.numBuys += 1;
			int ran = (int)  Randomness.random.nextInt(2);
			if(ran == 1){
				player.coins += 4;
			}
			else{
				player.gain(getCard(state.cards, CardName.Estate));
			}
			return;
		
		case Council_Room:
			System.out.println("+4 Cards. +1 Buy.");
			System.out.println("Each other player draws a card.");
			player.drawCard();
			player.drawCard();
			player.drawCard();
			player.drawCard();
			player.numBuys += 1;			
			for(Player p : state.players){
				if(p.player_username != player.player_username){
					p.drawCard();
				}
			}
			return;
			
		case Cutpurse:
			System.out.println("+2. Each other player discards a Copper card (or reveals a hand with no copper)");
			player.coins += 2;
			for(Player p : state.players){
				int cnt = 0;
		    	  Iterator<Card> i = p.deck.iterator();
		    	  while(i.hasNext()){
		    		  if(cnt < 2){
		    			  Card z = i.next();
		    			  if(z == getCard(state.cards, CardName.Copper)){
		    				  p.discard(z);
		    				  i.remove();
		    				  cnt++;
		    			  }
		    		  }
		    		  else{
		    			  System.out.println(p.player_username+" only had "+cnt+" copper.");
		    			  break;
		    		  }
		    	  }
			}
			return;
		case Embargo:
			System.out.println("+2. Trash this card. Put an Embargo token on top of a Supply pile.");
			System.out.println("When a player buys a card, he gains a Curse card per Embargo token on that pile.");
			player.coins += 2;
			player.playedCards.remove(getCard(player.playedCards, CardName.Embargo));
			int r = (int)  Randomness.random.nextInt(10);
			int card = 0;
			for(Card c : filter(state.cards, Type.ACTION)){
				
				if(card == r){
				    System.out.println("Token Added");
				}
			}

			return;
		case Feast:
			System.out.println("Trash this card. Gain a card costing up to 5.");
			player.playedCards.remove(getCard(player.playedCards, CardName.Feast));
			player.coins += 5;
			return;
			
		case Gardens:
			System.out.println("Worth 1 Victory point per 10 cards you have(round down)");

			return;
		case Great_Hall:
			System.out.println("+1 Card. +1 Action.");
			player.drawCard();
			player.numActions++;
			
			return;
			
		case Minion:
			
			return;
			
		case Mine:
			System.out.println("You may trash a Treasure from your hand.");
			System.out.println("Gain a Treasure to your hand costing up to 3 more than it.");
			if(getCard(player.hand, CardName.Copper) != null){
				player.hand.remove(getCard(player.hand, CardName.Copper));
				player.gain(getCard(state.cards, CardName.Silver));
			}
			else if(getCard(player.hand, CardName.Silver) != null){
				player.hand.remove(getCard(player.hand, CardName.Silver));
				player.gain(getCard(state.cards, CardName.Gold));
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
