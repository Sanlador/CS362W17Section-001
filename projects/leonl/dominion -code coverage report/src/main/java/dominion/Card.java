package dominion;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;



public final class Card implements Comparable<Card>, Cloneable{
	Random gen = new Random();
	public static enum Type {
		ACTION, TREASURE, VICTORY;
	}

	public static enum CardName {
		/** The Treasure cards */
		Gold, Silver, Cooper, 
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer, Smithy, Village, Baron, Ambassador, Council_Room,Cutpurse, Embargo, Feast, Gardens, Steward, Great_Hall, Mine;
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
		o = new Card(CardName.Council_Room,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Gardens,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Baron,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Feast,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Mine,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Great_Hall,Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.Steward,Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.Cutpurse,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Embargo,Type.ACTION,2,0,0);
		ret.add(o);
		return ret;
	}
	
	public void play(Player player, GameState state) {
		Card x;
		int count = 0;
		int y = 0;
		switch(this.cardName) {
		case Adventurer:
	    	  //Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
	    	  System.out.println("Adventurer Code");
	    	  System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
	    	  		"Put those Treasure cards into player hand and discard the other revealed cards.");
	    	  int treasures = 0;
	    	  while(treasures < 2){
	    		  x = player.drawCard();
	    		  if(x.getType() == Type.TREASURE){
	    			  treasures++;
	    		  }
	    		  else
	    			  player.discard(x);  
	    	  }
			return;
		case Smithy:
	         player.drawCard();
	         player.drawCard();
	         player.drawCard();
			return;
		
		
		case Village:
	    	  System.out.println("Village");
	    	  System.out.println("The player draw +1 Card.");
	    	  System.out.println("The player gets +2 play Actions.");
	    	  player.drawCard();
	    	  player.numActions += 2;
			return;
			
		case Baron:
			System.out.println("Baron");
			player.numBuys += 1;
			if(getCard(player.hand, CardName.Estate) != null) {
                player.discard(getCard(player.hand, CardName.Estate));
                player.coins += 4;
			}
			else{
				player.gain(getCard(state.cards, CardName.Estate));
			}
			return;
			
		case Ambassador:
			System.out.println("Ambassador");
			System.out.println("-2 Copies of card to supply");
			System.out.println("Each player gets copy");
			x = player.drawCard();
			count = 0;
			while(y < player.hand.size() && count < 2){
				if(player.hand.get(y) == x){
					player.hand.remove(y);
					state.cards.add(x);
					count++;
				}
				else
					y++;
			}
			return;
			
		case Council_Room:
			System.out.println("Council Room");
			System.out.println("\t\t+1 Buy");
		    System.out.println("\t\tDraws 4 cards");
		    player.numBuys += 1;
		    player.drawCard();
		    player.drawCard();
		    player.drawCard();
		    player.drawCard();
			return;
			
		case Cutpurse:
			System.out.println("Cutpurse");
			System.out.println("+2 Coins, Every other player discards Copper");
			for (Player players : state.players)
            {
               players.discard(getCard(players.hand, CardName.Cooper));
            }
			return;
			
		case Embargo:
			player.coins = player.coins + 2;
            player.hand.remove(getCard(player.hand, CardName.Embargo));// removes embargo card
            player.drawCard();
            //B wrong behavior
			return;
			
		case Gardens:
			System.out.println("Gardens");
			System.out.println("+1 Victory Point for every 10 cards");
			int size = player.hand.size();
			int vp = size/10;
			player.numBuys -= 1; //B wrong behavior
			return;
			
		case Mine:
			System.out.println("Mine");
			System.out.println("- Treasure Card");
			System.out.println("+ Next Level Treasure Card");
			  if(getCard(player.hand, CardName.Silver) != null) {
	                player.hand.remove(getCard(player.hand, CardName.Silver));
	                player.gain(getCard(state.cards, CardName.Gold));
	            } else if(getCard(player.hand, CardName.Cooper) != null)
	            {
	                player.hand.remove(getCard(player.hand, CardName.Cooper));
	                player.gain(getCard(state.cards, CardName.Silver));
	            }
			return;
			
		case Great_Hall:
			System.out.println("Great Hall");
			System.out.println("Draw +1 Card");
			System.out.println("+1 Action");
			player.drawCard();
			player.numActions += 1;
			return;
		case Steward:
			System.out.println("Steward");
			System.out.println("+2 Cards or +2Coin or 2Card Trashes");
			int random = gen.nextInt(3);
			if (random == 0){
				player.drawCard();
				player.drawCard();
			}
			else if (random == 1){
				player.coins += 2;
			}
			else if (random == 2){
				if (player.hand.size() > 1){
				player.hand.remove(player.hand.size());
				player.hand.remove(player.hand.size());
				}
				else
					player.hand.remove(player.hand.size());
			}
				
			return;
			
		case Feast:
			System.out.println("Trash this Card. Gain a card costing up to 5 coins.");
			//buy a card that is up to 5 coins
			player.hand.remove(getCard(player.hand,CardName.Feast));
			player.gain(getCard(state.cards, CardName.Mine));
			//B get Mine everytiem wrong behavior
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
	//@Override
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return cardName.compareTo(o.cardName);
	}
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    } 

}
