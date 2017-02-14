package org.cs362.dominion;

public class KingdomSlot implements Comparable<KingdomSlot>{
	private Card card;
	
	private int count;
	private int numEmbargoes = 0;
	
	public KingdomSlot(Card card, int count) {
		this.card = card;
		this.count = count;
	}
	
	public boolean IsEmpty() {
		if(count > 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public Card GetCard() { return card; }
	
	public int GetValue() { return card.GetValue(); }
	
	public int GetEmbargoes() { return numEmbargoes; }
	
	public Card DrawCard() {
		if(count > 0) {
			count -= 1;
			//return new object of type card
			return card; //Error 5: should return card.clone(), not card (deep copy vs shallow copy)
		}
		return null;
	}
	
	public void AddEmbargo() {
		numEmbargoes++;
	}
	
	public int compareTo(KingdomSlot other) {
		return card.GetValue() - other.GetValue();
	}
}
