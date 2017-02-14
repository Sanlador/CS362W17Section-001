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
		Gold, Silver, Copper, 
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer, Ambassador, Baron, Council_Room, Cutpurse, 
		Embargo, Feast, Gardens, Great_Hall, Mine, Smithy, Village, Sea_Hag;
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
		o = new Card(CardName.Ambassador,Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Village,Type.ACTION,3,0,0);
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
		o = new Card(CardName.Mine,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Sea_Hag,Type.ACTION,3,0,0);
		ret.add(o);
		return ret;
	}
	
	public void play(Player player, GameState state) {
		Card c;
		int i = 0;
		int cnt = 0;
		switch(this.cardName) {
		case Adventurer:
	    	  //Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
	    	  System.out.println(player.player_username + " plays ADVENTURER:");
	    	  System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
		    	  		"Put those Treasure cards into player hand and discard the other revealed cards.");
	    	  int treasureCnt = 0;
	    	  
	    	  while (treasureCnt < 2){ //until player draws 2 treasure cards
	    		  c = player.drawCard();
	    		  if (c.getType() == Type.TREASURE){
	    			  treasureCnt++;
	    		  }
	    		  else
	    			  player.discard(c);
	    	  }
	    	  System.out.println(player.player_username + " finished using ADVENTURER:");
			return;
		
		case Ambassador:
			//Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.
			 c = player.hand.get(0);
			 cnt = 0;
			 while(i < player.hand.size() && cnt < 2){
				 if(player.hand.get(i) == c){
					 player.hand.remove(i);
					 state.cards.add(c);
					 cnt++;
				 }
				 else
					 i++;
			 }
			return;
		
		case Baron:
			//+1 Buy. You may discard an Estate for +4 coins. If you don't. gain an Estate. 
			Card est = getCard(player.hand, CardName.Estate);
			if(est == null){
			    player.gain(getCard(state.cards, CardName.Estate));
            } else {
			    player.discard(est);
			    player.coins += 4;
            }
			return;
			
		case Council_Room:
			//+4 cards, +1 Buy, Each other player draws a card
			for(i=0; i < 4; i++){
				player.drawCard();
			}
			for(i=0; i < state.players.size(); i++){
				state.players.get(i).drawCard();
			}
			return;
			
		case Cutpurse:
			//+2 coins, Each other player discards a Copper card (or reveals a hand with no Copper)
			player.coins+=2;
			for(i=0; i < state.players.size(); i++){
				if(!state.players.get(i).equals(player)){
					if(state.players.get(i).hand.contains(Card.CardName.Copper)){
						int idx = state.players.get(i).hand.indexOf(Card.CardName.Copper);
						state.players.get(i).discard(state.players.get(i).hand.get(idx));
					}
					else
						System.out.println("Player " + i + "'s hand has no Copper.");
				}
			}
			return;
			
		case Embargo:
			//+2 coins, Trash this card. Put an Embargo token on top of a Supply pile. 
			//When a player buys a card, he gains a Curse card per Embargo token on that pile.
			player.hand.remove(getCard(player.hand, CardName.Embargo));
			//BUG
			return;
			
		case Feast:
			//Trash this card. Gain a card costing up to 5 coins
			player.hand.remove(getCard(player.hand, CardName.Feast));
			player.gain(getCard(state.cards, CardName.Mine));
			return;
			
		case Gardens:
			//Worth 1 victory point per 10 cards you have (round down).
			int size = player.hand.size();
			//BUG
			return;
			
		case Great_Hall:
			//+1 card +1 Action
			player.drawCard();
			player.numActions+=1;
			return;
		
		case Mine:
			if(getCard(player.hand, CardName.Silver) != null) {
				player.hand.remove(getCard(player.hand, CardName.Silver));
				player.gain(getCard(state.cards, CardName.Gold));
			}
			else if(getCard(player.hand, CardName.Copper) != null) {
				player.hand.remove(getCard(player.hand, CardName.Copper));
				player.gain(getCard(state.cards, CardName.Silver));
			}
			return;
			
		case Smithy:
	         player.drawCard();
	         player.drawCard();
	         player.drawCard();
	         player.drawCard();
			return;
			
		case Village:
	    	  //System.out.println("DONE Village Code******************************************");
	    	  System.out.println("The player draw +1 Card.");
	    	  player.drawCard();
	    	  System.out.println("The player gets +2 play Actions.");
	    	  player.numActions+=2;
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

	//@Override
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return cardName.compareTo(o.cardName);
	}
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    } 

}
