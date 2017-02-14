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
		Adventurer, Baron, Council_Room, Mine, Cutpurse, Steward, Ambassador, Feast, Great_Hall, Embargo, Gardens, Smithy, Village;
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

	public int getTreasureValue() { return treasureValue; }
	public int getCost() { return cost; }


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
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Baron,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Cutpurse,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Council_Room,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Village,Type.ACTION,3,0,0);
		ret.add(o);	
		o = new Card(CardName.Great_Hall,Type.ACTION,3,1,0);
		ret.add(o);	
		o = new Card(CardName.Mine,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Steward,Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.Feast,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Embargo,Type.ACTION,2,0,0);
		ret.add(o);
		o = new Card(CardName.Gardens,Type.ACTION,4,0,0);
		ret.add(o);
		return ret;
	}

	public void play(Player player, GameState state) {

		switch(this.cardName) {

		case Adventurer:
			int d_card = 0;
			while(d_card < 2){
				Card c = player.deck.get(0);
				player.drawCard();
				if(c.getType() == Type.TREASURE){
					d_card++;
				}
				else
					player.discard(c);
			}
			return;
			
		case Baron:
			int supply_value;
			for(Card c : player.hand){
				if(c.getCardName() == CardName.Estate){
					player.discard(c);
					player.coins += 4;
					return;
				}			
			}
			if((state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Estate))!=0)){
				player.gain(getCard(state.cards, CardName.Estate)); 
				supply_value = state.gameBoard.get(Card.getCard(state.cards, CardName.Estate));
				state.gameBoard.put(getCard(state.cards, CardName.Estate), supply_value-1);
			} 
			return;
		
		case Embargo:
			player.coins += 2;
			return;
		
		case Gardens:
			return;
			
		case Great_Hall:
			player.drawCard();
			player.numActions++;
			return;
			
		case Council_Room:
			player.drawCard();
			player.drawCard();
			player.drawCard();
			player.drawCard();
			player.numBuys++;
			if(state.players.get(0).player_username == player.player_username)
				state.players.get(1).drawCard();
			else
				state.players.get(0).drawCard();
			return;
		
		case Steward:
			player.coins += 2;
			return;
		case Smithy:
			player.drawCard();
			player.drawCard();
			player.drawCard();
			return;

		case Cutpurse:
			player.coins += 2;
			if(state.players.get(0).player_username == player.player_username)
				state.players.get(1).discard(state.players.get(1).hand.get(0));
			else
				state.players.get(0).discard(state.players.get(0).hand.get(0));
			return;
			
		case Mine:
			for(Card c : player.hand){
				if(c.getCardName() == CardName.Copper){
					if((state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Silver)) != 0)){
						player.hand.remove(c);
						player.buy_to_hand(getCard(state.cards, CardName.Silver));
						return;
					}
				}
				else if(c.getCardName() == CardName.Silver){
					if((state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Gold)) != 0)){
						player.hand.remove(c);
						player.buy_to_hand(getCard(state.cards, CardName.Gold));
						return;
					}
				}
			}
			return;
			
		case Feast:
			player.coins = 5;
			player.buyCard();
			player.numBuys += 1;
			player.coins = 0;
			return;
			
		case Ambassador:
			Card c = player.hand.get(0);
			player.hand.remove(0);
			if(state.players.get(0).player_username == player.player_username)
				state.players.get(1).gain(c);
			else
				state.players.get(0).gain(c);
			return;
		case Village:
			player.drawCard();
			player.numActions += 2;
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

	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return cardName.compareTo(o.cardName);
	}

	


}
