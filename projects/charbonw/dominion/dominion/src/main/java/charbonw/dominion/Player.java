package charbonw.dominion;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Player {
	public final String name;
	public final boolean bot;
	private int actions;
	private int buys;
	private int money;
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> discard = new ArrayList<Card>();
	private ArrayList<Card> hand = new ArrayList<Card>();
	public GameState gameState;
	
	private Scanner scan = new Scanner(System.in);
	
	public Player(String name, boolean bot, GameState gameState, int pCopper, int pEstate) {
		this.name = name;
		this.bot = bot;
		this.gameState = gameState;
		this.actions = 0;
		this.buys = 0;
		this.money = 0;
		int i;
		for (i = 0; i < pCopper; i++)
			this.deck.add(Card.COPPER);
		for (i = 0; i < pEstate; i++)
			this.deck.add(Card.ESTATE);
	}
	
	public int getActions() {
		return this.actions;
	}
	
	public void addActions(int n) {
		this.actions += n;
	}
	
	public int getBuys() {
		return this.buys;
	}
	
	public void addBuys(int n) {
		this.buys += n;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public void addMoney(int n) {
		this.money += n;
	}
	
	public int getHandSize() {
		return this.hand.size();
	}
	
	public int getDeckSize() {
		return this.deck.size();
	}
	
	public int getVictory() {
		int victory = 0;
		for (Card c : deck)
			victory += c.getVictory(this);
		for (Card c : hand)
			victory += c.getVictory(this);
		for (Card c : discard)
			victory += c.getVictory(this);
		return victory;
	}
	
	public boolean handContains(Card c) {
		return this.hand.contains(c);
	}
	
	public Card getHand(int index) {
		return this.hand.get(index);
	}
	
	public Card draw() {
		Card c = this.deck.get(this.deck.size());
		if (c != null) {
			print(this.name + " draws a " + c.name + "\n");
			this.deck.remove(this.deck.size());
			this.hand.add(c);
		}
		return c;											// returns null if deck is empty
	}
	
	public boolean gain(Card c) {
		GameState g = this.gameState;
		if (g.removeSupply(c)) {
			this.deck.add(c);
			return true;
		}
		return false;
	}
	
	public boolean discard(Card c) {
		if (this.hand.remove(c)) {							// Card can only be discarded from player hand
			this.discard.add(c);
			print(this.name + " discards a " + c.name + "\n");
			return true;
		}
		return false;
	}
	
	public void reveal(Card c) {
		print(this.name + " reveals a " + c.name + "\n");
	}
	
	public boolean trash(Card c) {
		if (this.hand.remove(c)) {
			this.gameState.addTrash(c);
			print(this.name + " trashes a " + c.name + "\n");
			return true;
		}
		return false;
	}
	
	public void shuffle() {
		for (Card c : this.hand) {
			this.hand.remove(c);
			this.deck.add(c);
		}
		for (Card c : this.discard) {
			this.discard.remove(c);
			this.deck.add(c);
		}
		Collections.shuffle(this.deck);
	}
	
	public Card chooseCard() {
		print("Choose a card from your hand\n");
		int choice = this.prompt(1);
		return this.hand.get(choice);
	}
	
	public boolean returnSupply(Card c) {
		if (this.hand.remove(c)) {
			this.gameState.addSupply(c);
			return true;
		}
		return false;
	}
	
	// yes = 1; no = 0; else return integer
	public int prompt(int botResponse) {
		if (this.bot || !this.gameState.loud)
			return botResponse;
		String input = scan.next();
		if (input == "y" || input == "yes")
			return 1;
		if (input == "n" || input == "no")
			return 0;
		int choice;
		try {
			choice = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			print("Invalid input, try again\n");
			choice = this.prompt(botResponse);
		}
		return choice;
	}
	
	// Print hand in two columns + top card of discard pile
	public void printBoard() {
		if (!this.gameState.loud)
			return;
		System.out.printf("%n%s Hand:%n", this.name);
		int i = 0;
		for (Card c : this.hand) {
			if ((i & 1) == 0)									// if i is even
				System.out.printf("(%d) %-10s", i, c.name);
			else
				System.out.printf("(%-10d) %s%n", i, c.name);
			i++;
		}
		if ((i & 1) == 0)
			System.out.printf("%n");
		System.out.printf("%nDiscard: %s%n%n", this.discard.getLast());
	}
	
	
}
