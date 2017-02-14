package org.cs362.dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.cs362.dominion.Card.CardName;
import org.cs362.dominion.Card.Type;



public final class Card implements Comparable<Card>{
	public enum Type {
		ACTION, TREASURE, VICTORY;
	}

	public static enum CardName {
		/** The Treasure cards */
		Gold, Silver, Cooper, 
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer, Smithy, Village, Baron, Cutpurse, GreatHall, CouncilRoom, Feast, Ambassador, Gardens, Embargo, Market, Mine;
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
		
		o = new Card(CardName.Baron, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Cutpurse, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.GreatHall, Type.ACTION, 3, 1, 0);
		ret.add(o);
		o = new Card(CardName.CouncilRoom, Type.ACTION, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Feast, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Ambassador, Type.ACTION, 3, 0, 0);
		ret.add(o);
		o = new Card(CardName.Gardens, Type.VICTORY, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Embargo, Type.ACTION, 2, 0, 0);
		ret.add(o);
		o = new Card(CardName.Market, Type.ACTION, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Village,Type.ACTION,3,0,0);
		ret.add(o);	
		o = new Card(CardName.Mine, Type.ACTION, 5, 0, 0);
		ret.add(o);
		return ret;
	}
	
	public void play(Player player, GameState state) {
		
		switch(this.cardName) {
		case Adventurer:
	    	  //Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
	    	  //System.out.println("TODO Adventurer Code******************************************");
	    	  //List<Card> ret = new LinkedList<Card>();
	    	  
	    	  int found = 0;
//	    	  System.out.println(player.deck.size());
	    	  
	    	  while(found < 2){
	    		  Card Top = player.drawCard();
	    		  System.out.println("Revealed: " + Top.toString());
	    		  if(Top.getType() == Type.TREASURE){
//	    			  player.hand.add(Top);
	    			  found++;
	    		  }
	    		  
	    		  else{
	    			  player.discard(Top);
	    		  };
	    	  };
	    	  System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
	    	  		"Put those Treasure cards into player hand and discard the other revealed cards.");
	    	  for(Card c: player.hand){
      	 		System.out.println(c.toString());
      	 	}
		
			return;
		case Smithy:
	         player.drawCard();
	         player.drawCard();
	         player.drawCard();
			return;
			
		case Village:
	    	  //System.out.println("TODO Village Code******************************************");
	    	  player.numActions += 2;
	    	  player.drawCard();
	    	  System.out.println("The player draw +1 Card.");
	    	  System.out.println("The player gets +2 play Actions.");
			return;
			
		case Baron:
			//System.out.println("TODO Baron Code******************************************");
			List<Card> estateCards = Card.filterName(player.hand, CardName.Estate);
			
			if(estateCards.size() == 0){
				player.gainFromBoard(getCard(state.cards, CardName.Estate));
//				player.gain(getCard(state.cards, CardName.Estate));
//				state.gameBoard.put(getCard(state.cards, CardName.Estate), state.gameBoard.get(getCard(state.cards, CardName.Estate)) - 1);
			}
			
			else{
				Card c = estateCards.get(0);
				player.discard(c);
				player.coins+= 4;
			}
				player.numBuys++;
			return;
			
		case Cutpurse:
			//System.out.println("TODO Cutpurse Code******************************************");
			
			player.coins+=2;
			for(Player p: state.players){
				List<Card> copperCards = filterName(p.hand, CardName.Cooper);
				if(copperCards.size() > 0 && p.player_username != player.player_username){
					p.discard(copperCards.get(0));
				}
			}
			return;
			
		case GreatHall:
			//System.out.println("TODO GreatHall Code******************************************");
			
			player.drawCard();
			player.numActions++;
			return;
			
		case CouncilRoom:
			//System.out.println("TODO CouncilRoom Code******************************************");
			
			for(Player p: state.players){
				p.drawCard();
			}
			player.drawCard();
			player.drawCard();
			player.drawCard();
			player.numBuys++;
			return;
			
		case Feast:
			//System.out.println("TODO Feast Code******************************************");
			Map<Card, Integer> treeMap = new TreeMap<Card, Integer>(state.gameBoard);
			List<Card> buyableCards = filterObtainable(treeMap.keySet(), state, 5);
			player.gainFromBoard(buyableCards.get(0));
			
//			for(Card c: state.gameBoard.keySet()){
//				if(c.cost <= 5){
//					player.hand.add(c);
//					state.gameBoard.put(c, state.gameBoard.get(c) - 1);
//				}
//			}
			List<Card> feastCards = filterName(player.playedCards, CardName.Feast);
			player.playedCards.remove(feastCards.get(0));
			return;
			
		case Ambassador:
			//System.out.println("TODO Ambassador Code******************************************");
			
			Card c = player.hand.get(0);
			state.gameBoard.put(c, state.gameBoard.get(c) + 1);
			for(Player p: state.players){
				if(p.player_username != player.player_username){
					p.gainFromBoard(c);
				}
			}
			player.hand.remove(c);
			return;
			
		case Embargo:
			//System.out.println("TODO Embargo Code******************************************");
			player.coins+=2;
			int random = (int)  Randomness.random.nextInt(3);
			Map<Card, Integer> treeGet = new TreeMap<Card, Integer>(state.gameBoard);
			List<Card> buyableCards2 = filterObtainable(treeGet.keySet(), state, 5);
			Card tmp = buyableCards2.get(random);
			state.embargoes.put(tmp, state.embargoes.get(tmp) + 1);
			List<Card> Embargos = filterName(player.playedCards, CardName.Embargo);
			player.playedCards.remove(Embargos.get(0));
			return;
			
		case Market:
			//System.out.println("TODO Market Code******************************************");
			
			player.numActions++;
			player.drawCard();
			player.numBuys++;
			player.coins++;
			return;
			
		case Mine:
			//System.out.println("TODO Mine Code******************************************");
			
			List<Card> treasureCards = Card.filter(player.hand, Type.TREASURE);
			
			if(treasureCards.size() > 0){
				Card c1 = treasureCards.get(0);
				int coinage = c1.cost;
				treasureCards = filter(state.cards, Type.TREASURE);
				List<Card> SilverCards = Card.filterName(treasureCards, CardName.Silver);
				List<Card> GoldCards = Card.filterName(treasureCards, CardName.Gold);
				if(coinage == 0){
					player.hand.remove(c1);
					player.gainFromBoard(SilverCards.get(0));
					Card c2 = Card.getCard(player.discard,Card.CardName.Silver);
					player.hand.add(c2);
					player.discard.remove(c2);
				}
				else{
					player.hand.remove(c1);
					player.gainFromBoard(GoldCards.get(0));
					Card c2 = Card.getCard(player.discard,Card.CardName.Gold);
					player.hand.add(c2);
					player.discard.remove(c2);
				}
			}
			return;
			
		default: return;
		}
	}

	public static Card getCard(List<Card> cards,CardName cardName) {
		for(int i=0; i<cards.size();i++){
			if(cards.get(i).cardName.equals(cardName)){
//				Card c = cards.get(i);
//				cards.remove(i);
				return cards.get(i);
			}
					
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
	   
	   public static List<Card> filterName(Iterable<Card> cards, CardName name){
		   List<Card> out = new ArrayList<Card>();
		   for(Card c : cards)
			   if(c.getCardName() == name)
				   out.add(c);
		   return out;
	   }
	   
	   public static List<Card> filterObtainable(Iterable<Card> cards, GameState state, int cost){
		   List<Card> out = new ArrayList<Card>();
		   for(Card c : cards)
			   if(c.getCost() <= cost && c.getCardName() != CardName.Curse && state.gameBoard.get(c) > 0)
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

//	@Override
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return cardName.compareTo(o.cardName);
	}

	protected Object clone() throws CloneNotSupportedException {         return super.clone();     }

}
