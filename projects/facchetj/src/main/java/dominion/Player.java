package dominion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
	public List<Card> hand = new ArrayList<Card>();
	public List<Card> deck = new ArrayList<Card>();
	public List<Card> defausse = new ArrayList<Card>();
	public List<Card> poubelle = new ArrayList<Card>();
	private int argent;
	private int nbAction;
	private int nbAchat;
	private Board gameBoard;
	private int id;
	// private Scanner sc = new Scanner(System.in);
	private Random rand = new Random();

	public Player(Board plateau, int id) {
		// a faire
		int i;
		for (i = 0; i < 7; i++) {
			deck.add(new Card(1));
		}
		deck.add(new Card(4));
		deck.add(new Card(4));
		deck.add(new Card(4));
		gameBoard = plateau;
		this.id = id;
		this.nbAchat = 1;
		this.nbAction = 1;
		this.argent = 0;

	}

	public void displayAction() {
		int i;
		System.out.println("here are your kingdom cards");
		for (i = 0; i < this.hand.size(); i++) {
			if (this.hand.get(i).getType() == "action") {
				System.out.println(i + "-" + this.hand.get(i).getNom());
			}
		}
	}

	public void playTreasure() {
		int i;
		for (i = 0; i < this.hand.size(); i++) {
			if (this.hand.get(i).getId() < 4) {
				this.play(this.hand.get(i));
			}
		}
	}

	public void removeTreasure() {
		int i;
		for (i = this.hand.size() - 1; i > -1; i--) {
			if (this.hand.get(i).getType() == "tresor") {
				this.poubelle.add(this.hand.get(i));
				this.hand.remove(i);
			}
		}
	}

	public void showPoubelle() {
		int i;
		for (i = 0; i < this.poubelle.size(); i++) {
			this.poubelle.get(i).print();
		}
	}

	public void showHand() {
		int i = this.hand.size();
		int j = 0;
		while (j < i) {
			System.out.println("carte " + j + this.hand.get(j).getNom());
			j++;
		}
	}

	public void showBoard() {
		int i;
		Card[] stock = this.gameBoard.getCards();
		for (i = 0; i < this.gameBoard.getCards().length; i++) {
			System.out.println(i + "-" + stock[i].getNom() + " " + stock[i].getPrix());
		}
	}

	public void gameTurn() {
		int y;
		Card carteMemoire;
		this.rand = new Random();
		int i;
		this.nbAchat = 1;
		this.nbAction = 1;
		int counter;
		for (i = 0; i < 5; i++) {
			this.draw();
		}
		while (this.nbAction != 0) {
			i = rand.nextInt(2);
			if (i == 1) {
				// this.showHand();
				i = -1;
				counter = 0;
				while (this.nbAction > 0) {
					for (i = 0; i < this.hand.size(); i++) {
						if (this.hand.get(i).getType() == "action") {
							carteMemoire = this.hand.get(i);
							play(this.hand.get(i));
							for (y = 0; y < this.hand.size(); y++) {
								if (this.hand.get(y) == carteMemoire) {
									this.hand.remove(y);
								}
							}
							break;
						}
					}
					this.nbAction--;
				}
			} else {
				this.nbAction = 0;
			}
		}
		this.playTreasure();
		this.removeTreasure();
		boolean condition = false;
		while (this.nbAchat != 0) {
			// System.out.println("do you want to buy a card?\n1 -yes\n2 -no");
			i = this.rand.nextInt(2);
			if (i != 1) {
				this.nbAchat = 0;
			} else {
				counter = 0;
				while (condition != true && counter < 1000) {
					i = rand.nextInt(17);
					condition = this.achat(i, this.gameBoard);
				}
				this.nbAchat--;
			}
		}
		this.clear();
		this.argent = 0;

	}

	public void draw() {
		if (this.deck.isEmpty() == false) {
			if (this.deck.get(0).getId() != -1) {
				this.hand.add(this.deck.get(0));
				this.deck.remove(0);
			} else {
				this.deck.remove(0);
			}
		} else {
			while (this.poubelle.isEmpty() != true) {
				this.deck.add(this.poubelle.get(0));
				this.poubelle.remove(0);
			}
			mix();
		}
	}

	public void mix() {
		Random r = new Random();
		int position;
		List<Card> tampon = new ArrayList<Card>();
		while (this.deck.isEmpty() != true) {
			tampon.add(this.deck.get(0));
			this.deck.remove(0);
		}
		while (tampon.size() > 0) {
			position = r.nextInt(tampon.size());
			this.deck.add(tampon.get(position));
			tampon.remove(position);
		}
		// todo
	}

	public void play(Card card) {

		// todo
		/*
		 * adventurer, ambassador, baron, council_room, cutpurse, embargo,
		 * feast, gardens, great_hall, mine
		 * 
		 * And then 3 from this list: minion, outpost, remodel, salvager,
		 * sea_hag, smithy, steward, treasure_map, tribute, village
		 */
		int valeur = card.getValeur();
		this.argent = this.argent + valeur;
		switch (card.getId()) {
		case 8:
			int count = 0;
			Card stock;
			while (count < 2) {
				if (this.deck.isEmpty() != true) {
					stock = this.deck.get(0);
				} else {
					break;
				}
				if (stock.getType() == "tresor") {
					draw();
					count++;
				} else {
					this.deck.remove(0);
					this.poubelle.add(stock);
				}
			}
			break;
		case 9:
			// werid
		case 10:
			this.nbAchat = this.nbAchat + 1;
			int number;
			for (number = 0; number < this.hand.size(); number++) {
				if (this.hand.get(number).getNom() == "estate") {
					number = this.hand.size() + 2;
				}
			}
			if (number == this.hand.size()) {
				this.poubelle.add(this.gameBoard.giveCard(4));
			} else {
				this.argent = +4;
			}
			break;
		case 11:
			this.nbAchat++;
			draw();
			draw();
			draw();
			draw();
			this.gameBoard.setToDraw((this.id + 1) % 2, 1);
			break;
		// add other player draw
		case 12:
			this.argent++;
			this.argent++;
			int i;
			for (i = 0; i < 4; i++) {
				if (i != this.id) {
					this.gameBoard.copperToTrash[i] = +1;
				}
			}
			break;
		// other player discard a copper
		case 13:
			this.gameBoard.setEmbargo(rand.nextInt(17));
			this.nbAchat += 2;
			break;
		case 14:
			boolean drawed = false;
			int alacon;
			while (drawed != true) {
				alacon = this.rand.nextInt(17);
				if (this.gameBoard.pioche_card[alacon].getValeur() < 6 && this.gameBoard.pioche_number[alacon] > 0) {
					this.poubelle.add(this.gameBoard.giveCard(alacon));
					drawed = true;
				}
			}
			break;
		case 15:
			// garden does nothing
			break;
		case 16:
			this.nbAchat++;
			this.nbAction++;
			draw();
			break;
		case 17:
			int trashedValue = -1;
			for (i = 0; i < this.hand.size(); i++) {
				if (this.hand.get(i).getType() == "tresor") {
					trashedValue = this.hand.get(i).getValeur();
					this.poubelle.add(this.hand.get(i));
					this.hand.remove(i);
					break;
				}
			}
			for (i = 2; i > -1; i--) {
				if (trashedValue > -1 && this.gameBoard.pioche_card[i].getValeur() < trashedValue + 4
						&& this.gameBoard.pioche_number[i] > 0) {
					this.poubelle.add(this.gameBoard.giveCard(i));
					break;
				}
			}
			break;
		case 18:
			this.nbAction += 2;
			draw();
			break;
		case 19:
			int counter = 0;
			while (this.deck.isEmpty() != true && counter < 3) {
				draw();
				counter++;
			}
			break;
		case 20:
			this.nbAchat += 1;
			int countersalva = 0;
			boolean counter2 = true;
			for (countersalva = 0; countersalva < this.hand.size(); countersalva++) {
				if (this.hand.get(countersalva).getNom() != "salvager") {
					counter2 = false;
					break;
				}
			}
			if (counter2 == true) {
				break;
			}
			this.poubelle.add(this.hand.get(countersalva));
			this.hand.remove(countersalva);
			this.argent += 4;
			break;
		}

		// sc.close();
	}

	public void clear() {
		// empty the listcarte
		while (hand.isEmpty() == false) {
			poubelle.add(hand.get(hand.size() - 1));
			hand.remove(hand.size() - 1);
			// same for defausse
		}
		while (defausse.isEmpty() == false) {
			poubelle.add(defausse.get(defausse.size() - 1));
			defausse.remove(defausse.size() - 1);
		}
	}

	public boolean achat(int position, Board board) {
		if (this.argent >= board.getCards()[position].getPrix()) {
			if (board.getAvailability(position) > 0) {
				this.defausse.add(this.gameBoard.giveCard(position));
				this.argent -= board.getCards()[position].getPrix();
				return true;
			}
		}
		return false;
	}

	public int getMoney() {
		return this.argent;
	}

	public int getAction() {
		return this.nbAction;
	}

	public int getAchat() {
		return this.nbAchat;
	}

	public int getScore() {
		this.clear();
		while (poubelle.isEmpty() != true) {
			deck.add(poubelle.get(0));
			poubelle.remove(0);
		}
		int i;
		int result = 0;
		for (i = 0; i < deck.size(); i++) {
			result += deck.get(i).getPointV();
		}
		return result;
	}
}
