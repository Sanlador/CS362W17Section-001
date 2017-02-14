import java.util.*;

public class Deck {
	public List<Card> cards;									//The list of cards that comprises the Deck
	public int embargoes;
	public int size;											//The number of cards in the Deck
	public int value;											//The value, in Coins, of the Deck
	public int points;											//The number of Victory Points in the Deck
	public int treasureCount;									//The number of Treasure cards in a deck
	public int actionCount;										//The number of Action cards in a deck
	public int victoryCount;									//The number of Victory cards in a deck
	
	public Deck() {												//Creates a Deck
		cards = new ArrayList<Card>();
		size = 0;
		value = 0;
		points = 0;
		treasureCount = 0;
		actionCount = 0;
		victoryCount = 0;
		embargoes = 0;
	}
	
	public Deck(Card card, int numCards) {						//Create a Deck of numCards cards. Ie 7 Coppers, or 4 Ambassadors
		cards = new ArrayList<Card>();
		for(int i = 0; i < numCards; i++) { addCard(card); }
		size = numCards;
		value = card.value * numCards;
		points = card.points * numCards;
	}
	
	public void addCard(Card card) {							//Adds a card to the Deck
		cards.add(card);
		size++;
		update();
	}
	
	public void removeCard(int index) {							//Remove a card from a specific position
		cards.remove(index);
		size--;
		update();
	}
	
	public Card drawTop() {										//Remove the top card off the deck and return it
		Card drawCard = cards.get(0);
		removeCard(0);
		update();
		return drawCard;
	}
	
	public int getSize() {									
		update();
		return size;
	}
	public int getValue() {
		update();
		return value;
	}
	public int getTreasure() {
		update();
		return treasureCount;
	}
	public int getAction() {
		update();
		return actionCount;
	}
	public int getVictory() {
		update();
		return victoryCount;
	}
	
	public void update() {										//Iterate through the Deck keeping track of Victory Points and Coin value
		int tempPoints = 0;
		int tempValue = 0;
		int tempTreasure = 0;
		int tempAction = 0;
		int tempVictory = 0; 
		for(int i = 0; i < size; i++) {
			tempPoints += cards.get(i).points;
			tempValue += cards.get(i).value;
			if(cards.get(i).cardType == Card.Type.TREASURE) {tempTreasure += 1;}		//Count the Treasures in a Deck
			if(cards.get(i).cardType == Card.Type.ACTION || cards.get(i).cardType == Card.Type.ATTACK) {tempAction += 1;}		//Count the Actions in a Deck
			if(cards.get(i).cardType == Card.Type.VICTORY) {tempVictory += 1;}			//Count the Victory Cards in a Deck
		}
		
		value = tempValue;
		points = tempPoints;
		victoryCount = tempVictory;
		treasureCount = tempTreasure;
		actionCount = tempAction;
	}
	
	public void swap(int i, int j) {							//Swap two cards in the deck
	    Card tmp = cards.get(i);
	    cards.set(i, cards.get(j));
	    cards.set(j, tmp);
	}
	
	public void shuffle(Random rnd) {							//Shuffle the deck
		for (int i = cards.size(); i > 1; i--)					//Can return the same permutation as initial - Bug
	        swap(i - 1, rnd.nextInt(i));
	}
	public void print() {										//Print the Decks information
		update();
		System.out.println("Cards: " + size + " Value: " + value + " Points: " + points);
	}
	public void printCards() {									//Print the Cards in the Hand
		update();
		for(int i = 0; i < size; i++) {
			System.out.println("Card " + i + ": " + cards.get(i).name);
		}
	}
}
