
public class Card {
	public static enum Type{
		ACTION, TREASURE, VICTORY;
	}
	public static enum CardName{
		Gold, Silver, Cooper, Province, Duchy, Estate, Curse;
	}
	private Type CardType;
	private CardName cardName;
	private int cost, score, treasureValue;
	
	private Card(CardName cardName, Type type, int cost, int score, int treasureValue){
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.CardType = type;
		this.cardName = cardName;
	}
	public Type getType(){
		return CardType;
	}
	public CardName getCardName(){
		return cardName;
	}
	public int getCost(){
		return cost;
	}
	public int getTreasureValue(){
		return treasureValue;
	}
	
}
