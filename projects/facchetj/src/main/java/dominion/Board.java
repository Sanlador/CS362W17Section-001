package dominion;

public class Board {
	public Card[] pioche_card = new Card[17];
	public int[] pioche_number = new int[17];
	public int[] embargo = new int[17];
	private int[] havetotrash = new int[4];
	private int[] havetodraw = new int[4];
	public int[] copperToTrash = new int[4];

	public Board(int nbJoueur) {
		int i;
		// treasures
		for (i = 0; i < 3; i++) {
			pioche_number[i] = (int) (130 - (nbJoueur * 7));
		}
		// CURSE and REALM and Action
		for (i = 3; i < 17; i++) {
			pioche_number[i] = (int) (11);
		}
		// actions cards
		for (i = 0; i < 7; i++) {
			pioche_card[i] = new Card((int) (i + 1));
		}
		for (i = 7; i < 17; i++) {
			pioche_card[i] = new Card((int) i + 1);
		}
		for (i = 0; i < 4; i++) {
			havetotrash[i] = 0;
			havetodraw[i] = 0;
		}
		for (i = 0; i < 17; i++) {
			embargo[i] = 0;
		}
		for (i = 0; i < 4; i++) {
			copperToTrash[i] = 0;
		}

	}

	public Card giveCard(int number) {
		if (number >= 0 && number < 17) {
			if (this.pioche_number[number] > 0) {
				this.pioche_number[number]--;
				return this.pioche_card[number];
			} else
				return new Card(-1);
		} else {
			return new Card(-1);
		}
	}

	public boolean gameDone() {
		int i;
		int count = 0;
		for (i = 0; i < 17; i++) {
			if (this.pioche_number[i] < 1) {
				count++;
			}
		}
		return count > 2;
	}

	public void setEmbargo(int index) {
		this.embargo[index]++;

	}

	public int getAvailability(int pos) {
		return (this.pioche_number[pos]);
	}

	public Card[] getCards() {
		return this.pioche_card;
	}

	public String getCardName(int pos) {
		return this.pioche_card[pos].getNom();
	}

	public int[] getTrash() {
		return this.havetotrash;
	}

	public void setTrash(int id, int number) {
		this.havetotrash[id] = number;
	}

	public int getToDraw(int id) {
		return this.havetodraw[id];
	}

	public void setToDraw(int id, int number) {
		this.havetodraw[id] = +number;
	}
}