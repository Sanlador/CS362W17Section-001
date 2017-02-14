package dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Card{
	public enum Type{
		ACTION, TREASURE, VICTORY;
	}

	public static enum CardName{
		/**The Treasure cards*/
		Gold, Silver, Copper,
		/**The Victory cards */
		Province, Duchy, Estate, Curse,
		/**The Kingdom cards*/
		Adventurer, Ambassador, Baron,
		CouncilRoom, Cutpurse, Embargo,
		Feast, Gardens, GreatHall,
		Mine, Smithy, Tribute,
		Village,
		/**Deck Names for Players*/
		Hand, Discard, Draw;
	}
	private final Type realType;
	private final CardName cardName;
	private final int cost, score, treasureValue;

	Card(CardName cardName, Type type, int cost, int score, int treasureValue){
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.realType = type;
		this.cardName = cardName;
	}

	public Type getType(){
		return realType;
	}

	public int getTreasureValue(){
		return treasureValue;
	}

	public int getCost(){
		return cost;
	}

	public int getScore(){
		return score;
	}

	public CardName getCardName(){
		return cardName;
	}

}

