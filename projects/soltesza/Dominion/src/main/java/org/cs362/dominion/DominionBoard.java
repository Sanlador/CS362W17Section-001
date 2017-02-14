package org.cs362.dominion;

import java.util.Vector;
import java.util.Collections;

public class DominionBoard {
	private KingdomSlot provinceRef;
	private KingdomSlot curseRef;
	
	private Vector<KingdomSlot> buyableCards;
	private Vector<KingdomSlot> expendedCards;
	
	public DominionBoard() {		
		buyableCards = new Vector<KingdomSlot>();
		expendedCards = new Vector<KingdomSlot>();
		
		//list of all possible kingdom cards
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.ADVENTURER), 10));
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.AMBASSADOR), 10));
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.BARON), 10));
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.COUNCIL_ROOM), 10));
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.CUTPURSE), 10));
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.EMBARGO), 10));
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.FEAST), 10));
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.GREAT_HALL), 10));
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.MINE), 10));
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.REMODEL), 10));
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.SMITHY), 10));
		buyableCards.addElement(new KingdomSlot(new ActionCard(ActionCard.ActionType.VILLAGE), 10));
		buyableCards.addElement(new KingdomSlot(new VictoryCard(VictoryCard.VictoryType.GARDENS), 10));
		
		while(buyableCards.size() >= 10) { //Error 4: should be > not >=
			buyableCards.remove((int)(Math.random()*buyableCards.size()));
		}
		
		buyableCards.addElement(new KingdomSlot(new TreasureCard(TreasureCard.TreasureType.GOLD), 30));
		buyableCards.addElement(new KingdomSlot(new TreasureCard(TreasureCard.TreasureType.SILVER), 40));
		buyableCards.addElement(new KingdomSlot(new TreasureCard(TreasureCard.TreasureType.COPPER), 46));
		buyableCards.addElement(new KingdomSlot(new VictoryCard(VictoryCard.VictoryType.DUCHY), 8));
		buyableCards.addElement(new KingdomSlot(new VictoryCard(VictoryCard.VictoryType.ESTATE), 8));
		curseRef = new KingdomSlot(new VictoryCard(VictoryCard.VictoryType.CURSE), 10);
		buyableCards.addElement(curseRef);
		provinceRef = new KingdomSlot(new VictoryCard(VictoryCard.VictoryType.PROVINCE), 8);
		buyableCards.addElement(provinceRef);
		
		Collections.sort(buyableCards);
	}
	
	public Vector<Card> GetBuyable(int money) {
		Vector<Card> buyable = new Vector<Card>();
		
		for(int i=0; i<buyableCards.size();  i++) {
			if(buyableCards.elementAt(i).GetValue() <= money) { //if the player can buy the card
				//add card to buyable vector
				buyable.add(buyableCards.elementAt(i).GetCard());
			}
			else {
				break;
			}
		}

		return buyable;
	}
	
	public Vector<Card> BuyCard(int idx) {
		KingdomSlot slot = buyableCards.elementAt(idx);
		
		Vector<Card> cards = new Vector<Card>();
		cards.add(slot.DrawCard());
		
		for(int i=0; i<slot.GetEmbargoes(); i++) {
			Card curse = BuyCurse();
			//if(curse != null) { //Error 3: not checking the return value before adding it to the list
				cards.addElement(curse);
			//}
		}
		
		if(slot.IsEmpty()) {
			expendedCards.addElement(buyableCards.remove(idx));
		}
		
		return cards;
	}
	
	public Card BuyCurse() {
		if(expendedCards.contains(curseRef)) {
			return null;
		}
		else {
			Card card = curseRef.DrawCard();
			if(curseRef.IsEmpty()) {
				expendedCards.add(curseRef);
				buyableCards.remove(curseRef);
			}
			return card;
		}
	}
	
	public void EmbargoPile(int idx) {
		buyableCards.elementAt(idx).AddEmbargo();
	}
	
	public void PrintBoard() {
		System.out.println("Buyable Cards: ");
		for(int i=0; i<buyableCards.size(); i++) {
			System.out.println(buyableCards.elementAt(i).GetCard().GetName());
		}
		System.out.println(System.lineSeparator() + "Expended Cards:");
		for(int i=0; i<expendedCards.size(); i++) {
			System.out.println(expendedCards.elementAt(i).GetCard().GetName());
		}
	}
	
	public boolean GameOver() {
		if(expendedCards.contains(provinceRef)) { //if provinces are included in the vector of expended cards
			System.out.println("All provinces expended. Game over.");
			return true;
		}
		else if(expendedCards.size() >= 3) { //if there are more than three piles of expended cards
			System.out.println("Three piles expended. Game over.");
			return true;
		}
		
		
		return false;
	}
}
