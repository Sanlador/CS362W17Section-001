package dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public final class Card implements Comparable<Card>, Cloneable{
	public enum Type {
		ACTION,
		TREASURE,
		VICTORY;
	}

	public static enum CardName {
		/** The Treasure cards */
		Gold,
		Silver,
		Cooper,
		/** The Victory cards */
		Province,
		Duchy,
		Estate,
		Curse,
		Gardens,
		/** The Kingdom cards */
		Adventurer,
		Ambassador,
		Baron,
		Council_Room,
		Cutpurse,
		Embargo,
		Feast,
		Great_Hall,
		Mine,
		Salvager,
		Smithy,
		Village;
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
	private final int cost,
	score,
	treasureValue;

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
	public int getCost() {
		return cost;
	}

	public int getTreasureValue() {
		return treasureValue;
	}
	public int score(Player player) {
		if(this.cardName == CardName.Gardens){
			return (player.hand.size() + player.discard.size() + player.deck.size()) / 10;
		}
		else {
			return score;
		}
	}

	static List < Card > createCards() {
		List < Card > ret = new LinkedList < Card > ();

		/** The Treasure cards  */
		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Cooper, Type.TREASURE, 0, 0, 1);
		ret.add(o);

		/** The Victory cards  */
		o = new Card(CardName.Province, Type.VICTORY, 8, 6, 0);
		ret.add(o);
		o = new Card(CardName.Duchy, Type.VICTORY, 5, 3, 0);
		ret.add(o);
		o = new Card(CardName.Estate, Type.VICTORY, 2, 1, 0);
		ret.add(o);
		o = new Card(CardName.Curse, Type.VICTORY, 0, -1, 0);
		ret.add(o);
		o = new Card(CardName.Gardens, Type.VICTORY, 4, 0, 0);
		ret.add(o);
		/** The Kingdom cards , it should more than 10 cards*/

		o = new Card(CardName.Adventurer, Type.ACTION, 6, 0, 0);
		ret.add(o);
		o = new Card(CardName.Ambassador, Type.ACTION, 3, 0, 0);
		ret.add(o);
		o = new Card(CardName.Baron, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Council_Room, Type.ACTION, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Cutpurse, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Embargo, Type.ACTION, 2, 0, 0);
		ret.add(o);
		o = new Card(CardName.Feast, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Great_Hall, Type.ACTION, 3, 1, 0);
		ret.add(o);
		o = new Card(CardName.Mine, Type.ACTION, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Salvager, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Smithy, Type.ACTION, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Village, Type.ACTION, 3, 0, 0);
		ret.add(o);
		return ret;
	}

	public void play(Player player, GameState state) {

		switch (this.cardName) {
			case Adventurer:
				//Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
				player.discard(Card.getCard(state.cards, CardName.Adventurer));
				System.out.println(player.hand.size());
				int drawn = 0;
				while (drawn < 2) {
					Card drawCard = player.drawCard();
					player.hand.remove(drawCard);
					if (drawCard.getType() == Type.TREASURE) {
						drawn++;
						player.hand.add(drawCard);
					} else {
						player.discard.add(drawCard);
					}
				}
				//System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
				//	"Put those Treasure cards into player hand and discard the other revealed cards.");
				return;
			case Ambassador:
				// Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.
				player.discard(Card.getCard(state.cards, CardName.Ambassador));
				Card ambassador = Randomness.randomMember(player.hand);
				state.gameBoard.put(Card.getCard(state.cards, ambassador.getCardName()), state.gameBoard.get(ambassador) + 1);
				player.hand.remove(ambassador);
				int another = Randomness.nextRandomInt(2);
				if(another == 1){
					for(int i = 0; i < player.hand.size(); i++){
						if(player.hand.get(i).getCardName() == ambassador.getCardName()){
							state.gameBoard.put(Card.getCard(state.cards, ambassador.getCardName()), state.gameBoard.get(ambassador) + 1);
							player.hand.remove(ambassador);
							break;
						}
					}
				}
				for (Player current : state.players){
					if(!current.player_username.equals(player.player_username)){
						if(state.gameBoard.get(Card.getCard(state.cards, ambassador.getCardName())) != 0){
							current.gain(state.boardTakeCard(ambassador));
						}
					}
				}
				return;
			case Baron:
				player.discard(Card.getCard(state.cards, CardName.Baron));
				player.numBuys += 1;
				int choice = Randomness.nextRandomInt(2);
				if(choice == 1){
					choice = 0;
					for(int i = 0; i < player.hand.size(); i++){
						if(player.hand.get(i).getType() == Type.VICTORY){
							player.discard(player.hand.get(i));
							player.coins += 4;
							choice = 1;
							break;
						}
					}
					if(choice == 0){
						if(state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Estate)) != 0){
							player.gain(state.boardTakeCard(Card.getCard(state.cards, CardName.Estate)));
						}
					}
				}
				else {
					if(state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Estate)) != 0){
						player.gain(state.boardTakeCard(Card.getCard(state.cards, CardName.Estate)));
					}
				}
				return;
			case Council_Room:
				player.discard(Card.getCard(state.cards, CardName.Council_Room));
				for(int i = 0; i < 4; i++){
					player.drawCard();
				}
				player.numBuys++;
				for(Player currentPlayer : state.players) {
					if(!currentPlayer.player_username.equals(player.player_username)){
						currentPlayer.drawCard();
					}
				}
				return;
			case Cutpurse:
				player.discard(Card.getCard(state.cards, CardName.Cutpurse));
				player.coins += 2;
				for(Player currentPlayer : state.players){
					if(!currentPlayer.player_username.equals(player.player_username)){
						if(currentPlayer.hand.contains(Card.getCard(state.cards, CardName.Cooper))){
							currentPlayer.discard(Card.getCard(state.cards, CardName.Cooper));
						}
					}
				}
				return;
			case Embargo:
				player.hand.remove(Card.getCard(state.cards, CardName.Embargo));
				player.coins += 2;
				Card embargo = Randomness.randomMember(state.cards);
				state.embargos.put(embargo, state.embargos.get(embargo) + 1);
			case Feast:
				player.hand.remove(Card.getCard(state.cards, Card.CardName.Feast));
				List <Card> cardStock = new ArrayList<Card>(state.gameBoard.keySet());
				List <Card> buyableCards = Card.availableCostFilter(cardStock, 5, state);
				Card selected = Randomness.randomMember(buyableCards);
				state.boardTakeCard(selected);
				player.gain(selected);
				return;
			case Great_Hall:
				player.discard(Card.getCard(state.cards, CardName.Great_Hall));
				player.drawCard();
				player.numActions++;
				return;
			case Mine:
				player.discard(Card.getCard(state.cards, CardName.Mine));
				List <Card> treasureCards = Card.filter(player.hand, Type.TREASURE);
				if (treasureCards.size() == 0){
					return;
				}
				else if(treasureCards.contains(Card.getCard(state.cards, Card.CardName.Silver))){
					player.hand.add(state.boardTakeCard(Card.getCard(state.cards, Card.CardName.Gold)));
					player.hand.remove(Card.getCard(state.cards, Card.CardName.Silver));
					return;
				}
				else if(treasureCards.contains(Card.getCard(state.cards, Card.CardName.Cooper))){
					if(state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Silver)) >= 1){
						player.hand.add(state.boardTakeCard(Card.getCard(state.cards, Card.CardName.Silver)));
						player.hand.remove(Card.getCard(state.cards, Card.CardName.Cooper));
						return;
					}
				}
					
				else {
					return;
				}
			case Salvager:
				player.discard(Card.getCard(state.cards, CardName.Salvager));
				player.numBuys += 1;
				int cardIndex = Randomness.nextRandomInt(player.hand.size());
				Card trashCard = player.hand.get(cardIndex);
				player.coins += trashCard.getCost();
				player.hand.remove(trashCard);
				return;				
			case Smithy:
				player.discard(Card.getCard(state.cards, CardName.Smithy));
				player.drawCard();
				player.drawCard();
				player.drawCard();
				return;

			case Village:
				player.discard(Card.getCard(state.cards, CardName.Village));
				player.drawCard();
				player.numActions += 2;
				return;
			default:
				return;
		}
	}

	public static Card getCard(List < Card > cards, CardName cardName) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).cardName.equals(cardName))
				return cards.get(i);
		}
		return null;
	}

	public static List < Card > filter(Iterable < Card > cards, Type target) {
		List < Card > out = new ArrayList < Card > ();
		for (Card c: cards)
			if (c.getType() == target)
				out.add(c);
		return out;
	}
	
	public static List < Card > availableCostFilter(Iterable < Card > cards, int coins, GameState state) {
		List < Card > out = new ArrayList < Card > ();
		for (Card c: cards){
			if (c.getCost() <= coins){
				if(state.gameBoard.get(Card.getCard(state.cards, c.cardName)) != 0){
					out.add(c);
				}
			}
		}
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
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (!(obj instanceof Card)) return false;

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