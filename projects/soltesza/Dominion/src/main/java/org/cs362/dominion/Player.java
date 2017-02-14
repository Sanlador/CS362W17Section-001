package org.cs362.dominion;
import java.util.Vector;

public class Player {
	public Deck deck;
	private String name;
	private DominionBoard board;
	
	private int actions = 1;
	private int buys = 1;
	private int coins = 0;
	
	public Player(String name, DominionBoard board) {
		deck = new Deck();
		this.name = name;
		this.board = board;
		
		deck.DrawCards(5);
	}
	
	public String GetName() {return name;}
	
	public int GetScore() {return deck.GetScore();}
	
	public void TakeTurn(GameState state) {
		Vector<Card> hand = deck.GetHand();
		
		//play actions
		for(int i=0; i<actions; i++) {
			Vector<ActionCard> actionCards = new Vector<ActionCard>();
			for(int j=0; j<hand.size(); j++) {
				if(hand.elementAt(i) instanceof ActionCard) {
					actionCards.add((ActionCard)hand.elementAt(i));
				}
			}
			
			if(!actionCards.isEmpty()) {
				ActionCard card = actionCards.elementAt((int)(Math.random()*actionCards.size()));
				card.PlayAction(state);
				PrintAction(card);
			}
		}
		//play treasures
		coins += deck.PlayTreasures();
		
		//buy
		Buy(buys, coins);
		
		//cleanup
		deck.DiscardHand();
		deck.DrawCards(5);
		
		actions = 1;
		buys = 1;
		coins = 0;
	}
	
	public void AddActions(int numActions) {
		this.actions += numActions;
	}
	
	public void AddBuys(int numBuys) {
		this.buys += numBuys;
	}
	
	public void AddCoins(int numCoins) {
		this.coins += numCoins;
	}
	
	public void Buy(int numBuys, int numCoins) {
		for(int i=0; i<numBuys; i++) {
			Vector<Card> buyableCards = board.GetBuyable(numCoins);
			
			int highestValue = 0;
			int lowerBound = buyableCards.size()-1;
			for(int j=buyableCards.size()-1; j>0; j--) {
				int val = buyableCards.get(j).GetValue();
				if(val > highestValue) {
					highestValue = val;
				}
				else if(val < highestValue) {
					lowerBound = j;
				}
			}
			
			int idx = (int)(Math.random() * (buyableCards.size()-lowerBound) + lowerBound);
			
			Vector<Card> newCards = board.BuyCard(idx);
			
			for(int j=0; j<newCards.size(); j++) {
				deck.AddCard(newCards.get(j)); 
			}
			
			PrintBuy(newCards.get(0));
		}
	}
	
	public void GainCurse() {
		Card curse = board.BuyCurse();
		
		if(curse != null) {
			deck.AddCard(curse);
		}
	}
	
	public Card TrashFromHand() {		
		Vector<Card> hand = deck.GetHand();
		if(!hand.isEmpty()) {
			int idx = (int)(Math.random()*hand.size());
			Card card = hand.get(idx);
			deck.TrashCard(card);
			return card;
		}
		else {
			return null;
		}
	}
	
	private void PrintBuy(Card card) {
		System.out.println(name + " bought a " + card.GetName() + " for " + card.GetValue() + " coins.");
	}
	
	private void PrintAction(ActionCard action) {
		System.out.println(name + " played " + action.name);
	}
	
	public void EmbargoPile() {
		Vector<Card> buyable = board.GetBuyable(Integer.MAX_VALUE);
		
		int idx = (int)(Math.random()*buyable.size());
		board.EmbargoPile(idx);
	}
}
