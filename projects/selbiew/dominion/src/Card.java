public abstract class Card {
	
	protected enum Type { TREASURE, VICTORY, ACTION, ATTACK };
	
	protected String name;														//The Card's Name
	protected String description;												//The Description written on the Card
	protected Type cardType;													//Treasure, Victory, Action, or Attack
	protected int cost;															//How much it is to buy
	protected int value;														//Value in coins
	protected int points;														//Value in Victory Points
	protected int actions;														//The Actions that playing this Card gives
	protected int cardDraw;														//The number of Cards gained when this card is played
	protected int buys;															//The number of Buys gained by playing the Card
	protected int playOrder = 13; 												//The Play Order of Actions, ensures cards are played in the right order, non-actions have the highest play order
	
	public void print() {
		System.out.println("Name: " + name);
		System.out.println("Description: " + description);
		System.out.println("Card Type: " + cardType);
		System.out.println("Cost: " + cost);
		System.out.println("Value: " + value);
		System.out.println("Points: " + points);
		System.out.println("Actions: " + actions);
		System.out.println("Card Draw: " + cardDraw);
		System.out.println("Buys: " + buys);
	}
	
	public float dynamicValue() {												//Running value of a card based on other cards in the deck
		return (float)value;
	}

	public void play(DomGame domgame, int ID, int cardIndex) {					//Play the Card
	}

}
