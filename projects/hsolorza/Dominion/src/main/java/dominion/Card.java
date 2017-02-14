/*
  @author Hannah Solorzano
  @version 2/12/17 Software Engineering 2 - Assignment 1
*/

package dominion;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;



public final class Card implements Comparable<Card>, Cloneable{
	public static enum Type {
		ACTION, TREASURE, VICTORY;
	}

	public static enum CardName {
		/** The Treasure cards */
		Gold, Silver, Copper,
		/** The Victory cards */
		Province, Duchy, Estate, Curse, Gardens,
		/** The Kingdom cards */
		Adventurer, Ambassador, Baron, Council_Room, Cutpurse, Embargo,
		Feast, Great_Hall, Mine, Salvager, Smithy, Village;
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
		o = new Card(CardName.Duchy,Type.VICTORY,		5, 1, 0);
		ret.add(o);
		o = new Card(CardName.Estate,Type.VICTORY,		2, 3, 0);
		ret.add(o);
		o = new Card(CardName.Curse,Type.VICTORY,		0, -1, 0);
		ret.add(o);
		o = new Card(CardName.Gardens, Type.VICTORY,  4, 666, 0);
		ret.add(o);

		/** The Kingdom cards , it should more than 10 cards*/

		o = new Card(CardName.Adventurer,Type.ACTION, 6, 0, 0);
		ret.add(o);
		o = new Card(CardName.Ambassador,Type.ACTION, 	3, 0, 0);
		ret.add(o);
		o = new Card(CardName.Baron, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Council_Room, Type.ACTION, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Cutpurse, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Embargo, Type.ACTION, 2, 0, 0);
		ret.add(o);
		o = new Card(CardName.Feast,Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Great_Hall, Type.ACTION, 3, 1, 0);
		ret.add(o);
		o = new Card(CardName.Mine,Type.ACTION, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Salvager, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Village,Type.ACTION, 3, 0, 0);
		ret.add(o);

		return ret;
	}

	public void play(Player player, GameState state) {

		switch(this.cardName) {
		case Adventurer:
	    	  System.out.println("Adventurer: Reveal cards from the player deck" +
					    "until player reveal 2 Treasure cards. Put those Treasure cards" +
							"into player and discard the other revealed cards.");

					int numOfTreasures = 0;

					while(numOfTreasures != 2){
						Card c = player.drawCard();
						if(c.treasureValue != 0){
							player.gain(c);
							numOfTreasures++;
						}
						else player.discard(c);
					}

			return;
		case Ambassador:
				  System.out.println("Ambassador: Reveal a card from your hand. Return"+
						"up to 2 copies of it from your hand to the Supply. Then each"+
						"player gains a copy of it.");
						 //
						//  Card c = player.hand.get(0);
						//  GameState g;

						 Card chosen = player.hand.get(0);
                    state.gameBoard.put(chosen, state.gameBoard.get(chosen) + 1);
                    for(Player p : state.players)
                    {
                        p.gain(chosen);
                        state.gameBoard.put(chosen, state.gameBoard.get(chosen) - 1);
                    }
                    player.hand.remove(chosen);


			return;
		case Baron:
					System.out.println("Card Baron: +1 Buy. If player choosed to discard an estate, +4. Else, gain an estate");

					player.numBuys = player.numBuys + 1;

					if(getCard(player.hand, CardName.Estate) != null){
						player.discard(getCard(player.hand, CardName.Estate));
						player.coins = player.coins + 4;
					}
					else{
						player.gain(getCard(state.cards, CardName.Estate));
					}

			return;
		case Council_Room:
				System.out.println("Council_Room: +4 Cards, +1 Buy. Each other "+
						"player draws a card");

				for(int i = 1; i < 4; i++) player.drawCard();
				player.numBuys = player.numBuys + 1;


				for (Player players : state.players){
          players.drawCard();
 				}

			return;
		case Cutpurse:
				System.out.println("Cutpurse: Each other player discards a card");
				for (Player players : state.players){
					if(players != player && getCard(players.hand, CardName.Copper) != null){
							players.discard(getCard(player.hand, CardName.Copper));
					}
				}

			return;
		case Embargo:
					System.out.println("Embargo: +2 money. Trash this card, put an " +
							"Embargo token on top of a supply pile");

					player.coins = player.coins + 2;
					player.discard(getCard(player.hand, CardName.Embargo));

					GameState gs = player.gameState;
			return;
		case Feast:
					System.out.println("Feast: Trash this card. Gain a card worth up to" +
						"5 money");

					player.discard(getCard(player.hand, CardName.Feast));
					Card c;
					do{
						c = player.grabSomeCards();
						if(c.cost <= 5){
							// player.hand.add(c);
							break;
						}
					}while(c.cost <= 5);

			return;
		case Gardens:
				// Do nothing
		  return;
		case Great_Hall:
					player.drawCard();
					player.numActions = player.numActions + 1;

			return;
		case Mine:
					System.out.println("Mine: Trash a treasure care. Gain a treasure" +
						"card to your hand costing up to 3 more than it.");
						player.discard(getCard(player.hand, CardName.Copper));

						do{
							c = player.grabSomeCards();
							if(c.cost <= 3){
								// player.hand.add(c);
								break;
							}
						}while(c.cost <= 3);

			return;
		case Salvager:
					System.out.println("Salvager: +1 buy. Trash a card, gain its cost in money.");

					player.numBuys = player.numBuys + 1;

					c = player.hand.get(0);
					player.coins = player.coins + c.cost;

			return;
		case Smithy:
					System.out.println("Smithy: Gain +3 cards");
	        for(int i = 0; i < 3; i++){ player.drawCard();}

			return;

		case Village:
					System.out.println("The player gets +2 Actions and draws 1 card.");

	    	  player.drawCard();
					player.numActions  = player.numActions + 2;

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
