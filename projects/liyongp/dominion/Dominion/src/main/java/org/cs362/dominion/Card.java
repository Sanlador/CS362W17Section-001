import java.util.*;



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
		Adventurer, Ambassador, Baron, Council_Room, Cutpurse, Embargo, Feast, Festival, Gardens, Great_Hall, Mine, Smithy, Village;
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
		// CardName, Type, Cost, Score, TreasureValue
		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);
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
		o = new Card(CardName.Ambassador,Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.Baron,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Council_Room,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Cutpurse,Type.ACTION,4,0,2);
		ret.add(o);
		o = new Card(CardName.Embargo,Type.ACTION,2,0,2);
		ret.add(o);
		o = new Card(CardName.Feast,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Festival,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Gardens,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Great_Hall,Type.ACTION,3,1,0);
		ret.add(o);
		o = new Card(CardName.Mine,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Village,Type.ACTION,3,0,0);
		ret.add(o);	
		
		return ret;
	}
	
	public void play(Player player, GameState state) {
		
		switch(this.cardName) {
		
		case Adventurer:
	    	  //Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
	    	  System.out.println("Adventure: Draw 2 treasure cards");
	    	 
	    	  int draws = 2;
	    	  while(draws != 0){
	    		  Card draw = player.drawCard();
	    		  if(draw.realType != Type.TREASURE){
	    			  player.hand.remove(draw);
	    			  player.deck.add(draw);
	    		  }
	    		  else{
	    			  draws--;
	    		  }
	    	  }
			return;
		
		case Ambassador:
			System.out.println("Ambassador: Return up to 2 copies of a card from hand to supply. Other players gain a copy of it.");
			if (player.hand.isEmpty()) {
				return;
			}
				Card chose = player.hand.get(0);
				player.hand.remove(chose);
				int supply = 1;
				if(getCard(player.hand, chose.cardName) != null){
					player.hand.remove(chose);
					supply = 2;
				}
				state.gameBoard.put(chose, state.gameBoard.get(chose)+supply);
				for(Player p: state.players){
					p.gain(chose);
				}
				 
			
			return;
	
				 
		case Baron:
			System.out.println("Baron: buys+1, discard Estate for 4 coins or gain an estate");
			player.numBuys++;
			if(getCard(player.hand, CardName.Estate) != null){
				Card chose1 = getCard(player.hand, CardName.Estate);
				player.discard(chose1);
				player.coins = player.coins+4;
			}
			else{
				player.gain(getCard(state.cards, CardName.Estate));
			}
			return;
		
		case Council_Room:
			System.out.println("Council_Room: Cards +4, buys +1, other players +1 card");
			player.numBuys++;
			player.drawCard();
			player.drawCard();
			player.drawCard();
			for(Player p: state.players){
				p.drawCard();
			}
			return;
			
		case Cutpurse:
			System.out.println("Cutpurse: coins+2, other players discard a copper if any");
			player.coins++;
			player.coins++;
			for(Player p: state.players){
				Card copper = getCard(p.hand, CardName.Copper);
				if(copper != null && p != player){
					p.discard(copper);
				}
			}
			return;
			
		case Embargo:
			System.out.println("Embargo: +2 coins, trash the card and place a token on top of a supply pile. Player buy from pile gains a curse card per token");
			player.coins++;
			player.coins++;
			player.playedCards.remove(getCard(player.playedCards, CardName.Embargo));
			// Don't have token feature yet
			return;
		
		case Feast:
			System.out.println("Feast: Trash this card and gain a card cost up to 5 coins.");
			player.coins = player.coins + 5;
			player.playedCards.remove(getCard(player.playedCards, CardName.Feast));
			return;
			
		case Festival:
			System.out.println("Festival: +2 Actions, +1 Buy, +2 coins");
			player.numActions += 2;
			player.numBuys++;
			player.coins +=2;
			return;
			
		case Gardens:
			System.out.println("Gardens: Worth 1 Victory Points per 10 cards you have.");
			// score adjustment in Players.java
			return;
	
		case Great_Hall:
			System.out.println("Great Hall: +1 card, +1 action and +1 Victory point");
			player.drawCard();
			player.numActions++;
			//points manipulated in Player.java
			return;
		
		case Mine:
			System.out.println("Mine: Trash a Treasure from hand and gain a Treasure to your hand costing up to 3 more than it.");
			if(getCard(player.hand, CardName.Silver) != null){
				player.hand.remove(getCard(player.hand, CardName.Silver));
				player.hand.add(getCard(state.cards, CardName.Gold));
			}
			else if(getCard(player.hand, CardName.Copper) != null){
				player.hand.remove(getCard(player.hand, CardName.Copper));
				player.hand.add(getCard(state.cards, CardName.Silver));
			}
			return;
			
		case Smithy:
			System.out.println("Smithy: +3 Cards");
			player.drawCard();
			player.drawCard();
			player.drawCard();
			return;
			
		case Village:
	    	System.out.println("Village: +1 card, +2 actions");
	    	player.drawCard();
	    	player.numActions = player.numActions + 2;
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
