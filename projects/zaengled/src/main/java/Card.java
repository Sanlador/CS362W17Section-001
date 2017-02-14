import java.util.ArrayList;
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
		Adventurer, Smithy, Village, Ambassador, Baron, Council_Room, Cutpurse, Embargo, Feast, Gardens, Great_Hall, Mine, Sea_Hag;
	}
	/**
	 * @param enum CardName the name of the card (GOLD, SILVER, COPPER, 
	 * @param enum Type the type of the card (Victory, Treasure, or Kingdom
	 * @param int cost the cost of the card
	 * @param int score the score of the card, when the game is finished
	 * @param treasureValue  the treasure value of the treasure cards (copper, silver, and gold cards)
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
		o = new Card(CardName.Duchy,Type.VICTORY,		5, 3, 0);
		ret.add(o);		
		o = new Card(CardName.Estate,Type.VICTORY,		2, 1, 0);
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
		o = new Card(CardName.Gardens,Type.ACTION,4,1,0);
		ret.add(o);
		o = new Card(CardName.Great_Hall,Type.ACTION,3,1,0);
		ret.add(o);
		o = new Card(CardName.Mine,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Sea_Hag,Type.ACTION,4,0,0);
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
	    	  	for(int i = 0; i < player.deck.size(); i += 1){


	    	  	    if(player.deck.get(i).getType() != Type.TREASURE){

	    	  	        player.discard(player.drawCard());
                    }else{
						count += 1;
						player.drawCard();
					}

                    if(count == 2){
                        break;
                    }
                }
				return;
			case Smithy:
				System.out.println("+3 Cards");
	         	player.drawCard();
	         	player.drawCard();
	         	player.drawCard();
				return;
			
			case Village:
	    	  	System.out.println("The player draw +1 Card.");
	    	  	player.drawCard();
	    	  	System.out.println("The player gets +2 play Actions.");
	    	  	player.numActions += 2;
				return;
			case Ambassador:
				System.out.println("Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.");
				Card card= player.hand.get(0);
				state.gameBoard.put(card, state.gameBoard.get(card) +1);
				player.hand.remove(card);
				for(Player p : state.players){
					if(p != player) {
                        p.gain(card);
                        state.gameBoard.put(card, state.gameBoard.get(card) - 1);
                    }
				}
				return;
			case Baron:
				System.out.println("+1 Buy\nYou may discard an Estate card. If you do, +$4. Otherwise gain an Estate card.");
				Card est = getCard(player.hand, CardName.Estate);
				if(est == null){
				    player.gain(getCard(state.cards, CardName.Estate));
                } else {
				    player.discard(est);
				    player.coins += 4;
                }
				return;
			case Council_Room:
				System.out.println("+4 Cards; +1 Buy\nEach other player draws a card.");
				player.numBuys += 1;
				for(Player players: state.players){//Covers each player drawing a card
				    players.drawCard();
                }
                for(int i = 0; i < 3; i++){//covers the 3 cards +1 from every player drawing
				    player.drawCard();
                }
				return;
			case Cutpurse:
				System.out.println("+$2\nEach other player discards a Copper card");
				for(Player players: state.players){
				    Card cop = getCard(players.hand, CardName.Copper);
				    if(cop != null) {
				        if(players != player) {
                            players.discard(cop);
                        }
                    }
                    player.coins += 2;
                }
				return;
			case Embargo:
				System.out.println("+$2\nTrash this card. Put an Embargo token on top of a Supply pile. When a player buys a card, he gains a Curse card per Embargo token on that pile.");
				player.coins += 2;
				player.playedCards.remove(getCard(player.playedCards, CardName.Embargo));
				//Code for adding embargo card to supply goes here:
                //
                //
				return;
			case Feast:
			    player.playedCards.remove(this);
				System.out.println("Trash this card. Gain a card costing up to $5");
                int rand = Randomness.nextRandomInt(3);
                if(Card.getCard(state.cards, Card.CardName.Village) != null){
                    player.gain(Card.getCard(state.cards, Card.CardName.Village));
                } else if (Card.getCard(state.cards, Card.CardName.Village) != null) {
                    player.gain(Card.getCard(state.cards, Card.CardName.Village));
                }
				return;
			case Gardens:
			    //gave it a base score of 1, everything else should be handled in the victory calculation function
				System.out.println("Worth 1 Victory point for every 10 cards in your deck (rounded down)");
				return;
			case Great_Hall:
			    //base score is 1 to account for the 1 victory point.
				System.out.println("1 Victory Point\n+1 Card; +1 Action");
				player.drawCard();
				player.numActions += 1;
			    return;
			case Mine:
			    List<Card> treasure = filter(player.hand, Type.TREASURE);
				System.out.println("Trash a Treasure card from your hand. Gain a Treasure card costing up to $3 more; put it into your hand.");
				if(treasure != null){
				    if(treasure.get(1).getCardName() == CardName.Copper){
				        player.gain(Card.getCard(state.cards, CardName.Silver));
				        player.playedCards.remove(this);
                    } else if (treasure.get(1).getCardName() == CardName.Silver){
				        player.gain(Card.getCard(state.cards, CardName.Gold));
				        player.playedCards.remove(this);
                    } //No point in doing a case for a gold card because there is no higher treasure

                }
				return;
			case Sea_Hag:
				System.out.println("Each other player discards the top card of his deck, then gains a Curse card, putting it on top of his deck.");
				for(Player players: state.players){
				    if(players != player) {
                        player.playedCards.remove(player.drawCard());
                        player.gain(Card.getCard(state.cards, CardName.Curse));
                    }
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
