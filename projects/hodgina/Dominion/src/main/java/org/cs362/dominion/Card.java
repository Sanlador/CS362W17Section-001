package org.cs362.dominion;
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
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer, Ambassador, Baron, Council_room, Cutpurse, Embargo, Feast, Gardends, Great_hall, Laboratory, Mine, Smithy, Village;
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
	private Random generator = new Random();

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

		//CardName cardName, Type type, int cost, int score, int treasureValue)

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
		o = new Card(CardName.Baron,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Council_room,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.Cutpurse,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Embargo,Type.ACTION,2,0,0);
		ret.add(o);
		o = new Card(CardName.Feast,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Gardends,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Great_hall,Type.ACTION,3,0,0);
		ret.add(o);
		o = new Card(CardName.Laboratory,Type.ACTION,5,0,0);
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
            case Gold:
                break;
            case Silver:
                break;
            case Copper:
                break;
            case Province:
                break;
            case Duchy:
                break;
            case Estate:
                break;
            case Curse:
                break;
            case Adventurer: //done
	    	  System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
	    	  		"Put those Treasure cards into player hand and discard the other revealed cards.");
				int num_treasures = 0;

				for(int i = 0; i < player.deck.size(); i++){
					if(num_treasures == 2){
						break;
					}else{
						System.out.println(player.deck.get(i));
						if(player.deck.get(i).getTreasureValue() > 0) {
							num_treasures++;
							player.hand.add(player.deck.get(i));
							player.deck.remove(player.deck.get(i));
						}
						else{
							player.discard(player.deck.get(i));
							player.deck.remove(player.deck.get(i));

					}
				}
			}
			return;
			case Ambassador:
					//Reveal a card from your hand. Return up to 2 copies of it
					// from your hand to the Supply. Then each other player gains a copy of it.
				System.out.println("Give every other player a copy of a card");
				Card given_card = player.hand.get(0); //pick the first card
				state.gameBoard.put(given_card, state.gameBoard.get(given_card) +1); //put it back in supply
				for(Player p : state.players){ //give every other player that card

						p.gain(given_card);
						state.gameBoard.put(given_card, state.gameBoard.get(given_card) - 1);

				}
				player.hand.remove(given_card);

				return;

		case Baron: //done
			System.out.println("+1 Card. +1 Buy.");
			player.drawCard();
			player.numBuys++;
			System.out.println("+4 coins or gains Estate.");
			if(getCard(player.hand, CardName.Estate) != null) {
				player.coins = player.coins + 4;
				player.discard(getCard(player.hand, CardName.Estate));
			} else player.gain(getCard(state.cards, CardName.Estate));
			return;

		case Council_room: //done
		//+4 Cards; +1 Buy
		//Each other player draws a card.
			System.out.println("+4 Cards, +1 Buy, All other Players +1 Cards");
			player.drawCard();
			player.drawCard();
			player.drawCard();
			player.drawCard();
			player.numBuys = player.numBuys + 1;

			for(Player players : state.players){ //All other players +1 card to their hands
				if(players != player) {
					players.drawCard();
				}
			}

			return;


		case Cutpurse:
		//+$2
		//Each other player discards a Copper card (or reveals a hand with no Copper).
			System.out.println("+2 Coins, Every other player discard a copper card");
			player.coins = player.coins + 2;
			for(Player players : state.players){ //for all players but the player who plaed it look for a copper and discard it
				if(players != player && getCard(players.hand, CardName.Copper) != null){
					players.discard(getCard(players.hand, CardName.Copper));
				}
			}
			return;

		case Embargo:
		//+$2
		//Trash this card. Put an Embargo token on top of a Supply pile.
		//When a player buys a card, he gains a Curse card per Embargo token on that pile.

			System.out.println("+2 Coins, Trash this Card, Place Embargo token on a supply pile");
			System.out.println("when a player buys a card, he gains a curse card per Embargo token on the pile");
			player.coins = player.coins + 2;
			player.playedCards.remove(getCard(player.playedCards, CardName.Embargo));
			int random = generator.nextInt(10);
			int card_num = 0;
			for(Card current_card : filter(state.cards, Type.ACTION)) {
				if(card_num == random) {
					GameState.addEmbargo(current_card);
				}
			}
			return;

		case Feast: //This will just add 5 coins
		//Trash this card. Gain a card costing up to $5.
			System.out.println("Trash this card, Gain a Card Costing up to 5 coins");
			player.playedCards.remove(getCard(player.playedCards, CardName.Feast));
			player.coins = player.coins + 5;
			return;

		case Gardends: //This is taken care of in the player.score method
		//Worth 1 Victory for every 10 cards in your deck (rounded down).
			System.out.println("This does nothing until the end of the game. \n At the end of the game, It is worth 1 victory point for every 10 cards in your deck");
			return;

		case Great_hall:
		//1 Victory Point
		//+1 Card; +1 Action.

			System.out.println("+1 Card, +1 Action, +1 Victory Point");
			player.drawCard();
			player.numActions++;
			return;
			case Laboratory:
				//+2 Cards; +1 Action
				player.drawCard();
				player.drawCard();
				player.numActions++;
				return;

		case Mine:
			System.out.println("Trash a Treasure Card. Gain Next Level of Treasure Card ");
			if(getCard(player.hand, CardName.Silver) != null) { //if player has silver in their hand upgrade it to a gold
				player.hand.remove(getCard(player.hand, CardName.Silver));
				player.gain(getCard(state.cards, CardName.Gold));
			} else if(getCard(player.hand, CardName.Copper) != null) { //if the player has copper in there hand and no silver then upgrade to a silver
				player.hand.remove(getCard(player.hand, CardName.Copper));
				player.gain(getCard(state.cards, CardName.Silver));
			}
			return; //if neither is true it will just return
		case Smithy:
			player.drawCard();
			player.drawCard();
			player.drawCard();
			return;

		case Village:

	    	  System.out.println("The player draw +1 Card.");
					player.drawCard();
	    	  System.out.println("The player gets +2 play Actions.");
					player.numActions = player.numActions + 2;
			return;


		default: return;
		}
	}

    static Card getCard(List<Card> cards,CardName cardName) {
        for(Card card: cards){
            if(card.cardName.equals(cardName)){
                return card;
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

		return cardName.compareTo(o.cardName);
	}
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
