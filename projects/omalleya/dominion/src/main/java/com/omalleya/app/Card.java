package com.omalleya.app;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Card implements Comparable<Card>, Cloneable{
    public static enum Type {
        ACTION, TREASURE, VICTORY;
    }

    public static enum CardName {
        //Treasure Cards
        Gold, Silver, Copper,

        //Victory Cards
        Province, Duchy, Estate, Curse,

        //Kingdom Cards
        Adventurer, Ambassador, Baron, Council_Room, Cutpurse, Embargo, Feast, 
        Gardens, Great_Hall, Mine, Militia, Smithy, Village;
    }

    private final Type cardType;
    private final CardName cardName;
    private final int cost, score, treasureValue;

    //card ability for kingdom cards

    Card(CardName cardName, Type type, int cost, int score, int treasureValue){
        this.cost = cost; 
        this.score = score; 
        this.treasureValue = treasureValue; 
        this.cardType = type; 
        this.cardName = cardName;
    }

    public Type getType() { return cardType; }

    public CardName getCardName() { return cardName; }

    public int getTreasureValue() { return treasureValue; }

    public int getScore() { return score; }

	public int getCost() { return cost; }

    static List<Card> createCards() {
        List<Card> ret = new LinkedList<Card>();
        
        /** The Treasure cards  */
		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);
		ret.add(o);

		/** The Victory cards  */ 
		o = new Card(CardName.Province,Type.VICTORY, 8, 6, 0);
		ret.add(o);
		o = new Card(CardName.Duchy,Type.VICTORY, 5, 1, 0);
		ret.add(o);		
		o = new Card(CardName.Estate,Type.VICTORY, 2, 3, 0);
		ret.add(o);		
		o = new Card(CardName.Curse,Type.VICTORY, 0, -1, 0);
		ret.add(o);	

		/** The Kingdom cards*/ 	
		o = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
		ret.add(o);
        o = new Card(CardName.Ambassador,Type.ACTION,3,0,0);
		ret.add(o);
        o = new Card(CardName.Baron, Type.ACTION, 4, 0, 0);
        ret.add(o);
        o = new Card(CardName.Council_Room, Type.ACTION, 5, 0, 0);
        ret.add(o);
		o = new Card(CardName.Cutpurse, Type.ACTION, 4, 0, 0);
        ret.add(o);
		o = new Card(CardName.Embargo, Type.ACTION, 2, 0, 0);
        ret.add(o);
		o = new Card(CardName.Feast, Type.ACTION, 4, 0, 0);
        ret.add(o);
		o = new Card(CardName.Gardens, Type.ACTION, 4, 1, 0);
        ret.add(o);
		o = new Card(CardName.Great_Hall, Type.ACTION, 3, 1, 0);
        ret.add(o);
		o = new Card(CardName.Mine, Type.ACTION, 5, 0, 0);
        ret.add(o);
		o = new Card(CardName.Militia, Type.ACTION, 4, 0, 2);
        ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Village,Type.ACTION,3,0,0);
		ret.add(o);	

		return ret;
    }

	public void play(Player player, GameState gs) {
		switch(this.cardName) {
			case Adventurer:
				int counter = 0;
				while(counter < 2) {
					player.drawCard();
					if(player.hand.get(player.hand.size()-1).cardType == Type.TREASURE) {
						counter++;
					}else {
						player.discard(player.hand.get(player.hand.size()-1));
					}
				}
				return;
			case Ambassador:
				player.drawCard();
				//rest purposefully not implemented
				return;
			case Baron:
				player.numBuys++;
				//randomize choice
				int random = (int)  Randomness.random.nextInt(1);
				if(random==0) {
					player.discard(Card.getCard(gs.cards, CardName.Estate));
					player.coins += 4;
				} else {
					player.gain(Card.getCard(gs.cards, CardName.Estate));
					gs.removeCard(Card.getCard(gs.cards, CardName.Estate));
				}
				return;
			case Council_Room:
				for(int i=0; i<4; i++) {
					player.drawCard();
				}
				player.numBuys++;
				for(Player p : gs.players) {
					if(!p.equals(player)) {
						p.drawCard();
					}
				}
				return;
			case Cutpurse:
				player.coins += 2;
				for(Player p : gs.players) {
					if(!p.equals(player)) {
						p.discard(Card.getCard(gs.cards, CardName.Copper));
					}
				}
				return;
			case Embargo:
				player.coins += 2;
				player.playedCards.remove(this);
				//purposefully didn't implement embargo token as bug
				return;
			case Feast:
				//coins set to 5
				player.coins = 5;
				//buy card up to 5
				player.buy(gs);
				player.coins = 0;
				player.playedCards.remove(this);
				return;
			case Gardens:
				//purposefully not implemented as bug
				return;
			case Great_Hall:
				player.drawCard();
				player.numActions++;
				return;
			case Mine:
				if(player.hand.contains(Card.getCard(gs.cards, CardName.Silver))) {
					player.hand.remove(Card.getCard(gs.cards, CardName.Silver));
					player.gain(Card.getCard(gs.cards, CardName.Gold));
					gs.removeCard(Card.getCard(gs.cards, CardName.Gold));
				}
				else if(player.hand.contains(Card.getCard(gs.cards, CardName.Copper))) {
					player.hand.remove(Card.getCard(gs.cards, CardName.Copper));
					player.gain(Card.getCard(gs.cards, CardName.Silver));
					gs.removeCard(Card.getCard(gs.cards, CardName.Silver));
				} 
				return;
			case Militia:
				player.coins += this.getTreasureValue();
				for(Player p : gs.players) {
					if(!p.equals(player)) {
						while(p.hand.size() > 3) {
							p.discard(p.hand.get(p.hand.size()-1));
						}
					}
				}
				return;
			case Smithy:
				for(int i=0; i<3; i++) {
					player.drawCard();
				}
				return;
			case Village:
				player.drawCard();
				player.numActions += 2;
				return;
			default:
				return;
		}
	}

    public static Card getCard(List<Card> cards,CardName cardName) {
		for(int i=0; i<cards.size();i++){
			if(cards.get(i).cardName.equals(cardName))
					return cards.get(i);
		}
		return null;
	}

	public static List<Card> filter(Iterable<Card> cards, Type target) {
		List<Card> out = new ArrayList<Card>();
		for (Card c : cards)
			if (c.getType() == target)
				out.add(c);
		return out;
	}

	public static Card sortCost(Iterable<Card> cards, List<Card> initialCards, int coins) {
		Card out = Card.getCard(initialCards, CardName.Copper);
		int max = 0;

		for (Card c : cards) {
			if (c.cost <= coins && max < c.cost) {
				out = c;
				max = c.cost;
			}
		}

		return out;
	}

    @Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(" \t" + this.getCardName());
		sb.append("-" + this.getType() + " ");
		sb.append("\t\t Cost: " + this.cost + " ");
		sb.append("\t\t Score: " + this.score + " ");
		sb.append("\tTreasure Value: " + this.treasureValue);

		return sb.toString();

	}

    @Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!super.equals(obj)) return false;
		if(!(obj instanceof Card)) return false;

		return cardName.equals(((Card) obj).cardName);
	}

	public int compareTo(Card o) {
		return cardName.compareTo(o.cardName);
	}

	protected Object clone() throws CloneNotSupportedException { return super.clone(); }
}