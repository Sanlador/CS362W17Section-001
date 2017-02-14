package dominion;
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
		Adventurer, Smithy, Village, bureaucrat, feast, festival, cellar, chapel, chancellor, council_room, gardens, laboratory, library;
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
	public int geTreasureValue() {
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
		o = new Card(CardName.bureaucrat,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.chapel,Type.ACTION,2,0,0);
		ret.add(o);	
		o = new Card(CardName.chancellor,Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.council_room,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.feast,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.festival,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.gardens,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.laboratory,Type.ACTION,5,0,0);
		ret.add(o);	
		o = new Card(CardName.library,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.cellar,Type.ACTION,2,0,0);
		ret.add(o);

		return ret;
	}

	public void play(Player player, GameState state) {

		switch(this.cardName) {
			//1
			case Adventurer:
				//Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
				System.out.println("TODO Adventurer Code******************************************");
				System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
						"Put those Treasure cards into player hand and discard the other revealed cards.");

				break;
			//2
			case Smithy:
				player.drawCard();
				player.drawCard();
				player.drawCard();
				break;
			//3	
			case Village:
				System.out.println("The player draw +1 Card.");
				player.drawCard();
				System.out.println("The player gets +2 play Actions.");
				player.getAction();
				player.getAction();
				break;
			//4
			case bureaucrat:
				//+ 2 money
				player.getCoin();
				player.getCoin();

				//other players reveal their hand 
				break;
			//5
			case chapel:
				//trash up to 4 cards from your hand
				break;
			//6
			case cellar:
				//+1 action
				player.getAction();

				break;
			//7
			case chancellor:
				//+2 money
				player.getCoin();
				player.getCoin();

				//put whole deck into discard
				break;
			//8
			case council_room:
				//+4 cards
				player.drawCard();
				player.drawCard();
				player.drawCard();	
				player.drawCard();
				//+1 buy
				player.getBuy();
				//all other players draw a card
				break;
			//9
			case feast:
				//trash this card, gain a card costing up to 5
				break;
			//10
			case festival:
				//+2 actions
				player.getAction();
				player.getAction();
				//+1 buy
				player.getBuy();
				//+2 money
				player.getCoin();
				player.getCoin();

				break;
			//11
			case gardens:
				//+1 victory card for every 10 cards in your deck
				break;	
			//12
			case laboratory:
				//+2 cards
				player.drawCard();
				player.drawCard();
				//+1 action
				player.getAction();
				break;
			//13
			case library:
				//draw until you have 7 cards in your hand
				if (player.getHandSize() < 7){
					while (player.getHandSize() < 7){
						player.drawCard();
					}
				}
			default:
			
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
			//		sb.append("\Treasure Value: " + this.treasureValue);

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
