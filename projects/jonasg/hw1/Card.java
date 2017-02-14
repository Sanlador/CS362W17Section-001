package org.cs362.dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class Card implements Comparable<Card>, Cloneable {
    public enum Type {
	ACTION, TREASURE, VICTORY;
    }

    public static enum CardName {
	/** The Treasure cards */
	Gold, Silver, Copper,
	/** The Victory cards */
	Province,Duchy,Estate,Curse,
	/** The Kingdom cards */
	Adventurer, Ambassador, Baron, CouncilRoom, Cutpurse,
	Embargo, Feast, Gardens, GreatHall, Mine, Smithy, Village,
	Minion, SeaHag;
    }
    /**
     * @param enum CardName the name of the card (GOLD, SILVER, COPPER,
     * @param enum Type the type of the card (Victory, Treasure, or Kingdom
     * @param int cost the cost of the card
     * @param int score the score of the card, when the game is finished
     * @param treasureValue  the treasure value of the treasure cards (copper, silver, and gold cards)
     */
    private final Type realType;
    private final CardName cardName;
    private final int cost, score, treasureValue;

    //Card constructor
    Card(CardName cardName, Type type, int cost, int score, int treasureValue) {
	this.cost = cost;
	this.score = score;
	this.treasureValue = treasureValue;
	this.realType = type;
	this.cardName = cardName;
    }

    //returns the cost
    public int getCost() {return cost;}
    //returns the type
    public Type getType() {return realType;}
    //returns the card name
    public CardName getCardName() {return cardName;}
    //returns treasure value
    public int getTreasureValue() {return treasureValue;}
    //returns victory point value
    public int score() {return score;}

    static List<Card> createCards() {
	List<Card> ret = new LinkedList<Card>();

	/** The Treasure cards  */
	Card o = new Card(CardName.Gold,Type.TREASURE,6,0,3);
	ret.add(o);
	o = new Card(CardName.Silver,Type.TREASURE,3,0,2);
	ret.add(o);
	o = new Card(CardName.Copper,Type.TREASURE,0,0,1);
	ret.add(o);

	/** The Victory cards  */
	o = new Card(CardName.Province,Type.VICTORY,8,6,0);
	ret.add(o);
	//induced bug: duchy gives 2 vp instead of 3
	o = new Card(CardName.Duchy,Type.VICTORY,5,2,0);
	ret.add(o);
	o = new Card(CardName.Estate,Type.VICTORY,2,1,0);
	ret.add(o);
	o = new Card(CardName.Curse,Type.VICTORY,0,-1,0);
	ret.add(o);

	/** The Kingdom cards, it should create more than 10 cards*/
	o = new Card(CardName.Gardens,Type.VICTORY,4,0,0);
	ret.add(o);
	o = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
	ret.add(o);
	o = new Card(CardName.Ambassador,Type.ACTION,3,0,0);
	ret.add(o);
	o = new Card(CardName.Baron,Type.ACTION,4,0,0);
	ret.add(o);
	o = new Card(CardName.CouncilRoom,Type.ACTION,5,0,0);
	ret.add(o);
	o = new Card(CardName.Cutpurse,Type.ACTION,4,0,0);
	ret.add(o);
	o = new Card(CardName.Embargo,Type.ACTION,2,0,0);
	ret.add(o);
	o = new Card(CardName.Feast,Type.ACTION,4,0,0);
	ret.add(o);
	o = new Card(CardName.GreatHall,Type.ACTION,3,1,0);
	ret.add(o);
	o = new Card(CardName.Mine,Type.ACTION,5,0,0);
	ret.add(o);
	o = new Card(CardName.Minion,Type.ACTION,5,0,0);
	ret.add(o);
	o = new Card(CardName.SeaHag,Type.ACTION,4,0,0);
	ret.add(o);
	o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
	ret.add(o);
	o = new Card(CardName.Village,Type.ACTION,3,0,0);
	ret.add(o);

	return ret;
    }

    public void play(Player player, GameState state) {

	System.out.println("Player " + player.player_username + " playing " + this.getCardName());

	switch(this.cardName) {
	    case Adventurer:
		int treasureCount = 0;
		List<Card> revealed = new ArrayList<Card>();
		//while the player has < 2 treasure cards
		while(treasureCount < 2) {
		    //break if the player ran out of cards to draw
		    if((player.deck.size() == 0) && (player.discard.size() == 0))
			break;
		    if(player.deck.isEmpty())
			player.shuffleDeck();
		    Card card = player.deck.poll();
		    //if the card is a treasure, add it to hand
		    //and increment count
		    if(card.realType == Type.TREASURE) {
			treasureCount++;
			player.hand.add(card);
		    }
		    //else add it to revealed (to be discarded)
		    else
			revealed.add(card);
		}
		//discard all not treasures
		player.discard.addAll(revealed);
		revealed.clear();
		return;

	    case Ambassador:
		//select a random card to discard
		int randReturn = Randomness.random.nextInt(player.hand.size());
		Card returnCard = player.hand.get(randReturn);
		//can put back 0, 1, or 2 copies
		int toPutBack = Randomness.random.nextInt(3);
		int realPutBack = 0;
		for(int i = 0; i < toPutBack; i++) {
		    //put back x copies
		    if(player.hand.remove(returnCard)) {
			realPutBack++;
			state.gameBoard.put(returnCard,
				state.gameBoard.get(returnCard) + 1);
		    }
		}
		System.out.println("Player put back " + realPutBack +
			" " + returnCard.cardName);
		//each other player gains a copy
		for(Player person : state.players)
		    if(person != player)
			if(state.reduceSupply(returnCard))
			    person.gain(returnCard);
		return;

	    case Baron:
		player.numBuys += 1; //+1 buy
		//if the player has an estate, discard and +4 gold
		if(getCard(player.hand, CardName.Estate) != null) {
		    player.discard(getCard(player.hand, CardName.Estate));
		    player.coins += 4;
		    //else gain an estate
		} else {
		    //if there are estates
		    if(state.reduceSupply(getCard(state.cards, CardName.Estate)))
			//gain an estate
			player.gain(getCard(state.cards, CardName.Estate));
		}
		return;

	    case CouncilRoom:
		//player draws 4 cards
		for(int i = 0; i < 4; i++)
		    player.drawCard();
		//player gains 1 buy
		player.numBuys += 1;
		//each other player draws a card
		for(Player person : state.players)
		    if(person != player)
			person.drawCard();
		return;

	    case Cutpurse:
		//player gains +2 gold
		player.coins += 2;
		//Every other player discards copper
		//(or reveals hand w/ no copper)
		for(Player person : state.players) {
		    if(person != player) {
			Card copper = getCard(person.hand, CardName.Copper);
			if(copper != null)
			    person.discard(copper);
		    }
		}
		return;

	    case Embargo:
		player.coins += 2; //+2 gold
		player.playedCards.remove(this); //trash embargo
		//select a random pile to embargo
		int embargoRand;
		Card embargoCard = null;
		while(embargoCard == null) {
		    embargoRand = Randomness.random.nextInt(18);
		    if(state.gameBoard.containsKey(state.cards.get(embargoRand)))
			embargoCard = state.cards.get(embargoRand);
		}
		System.out.println("Player embargos " + embargoCard.cardName);
		//increase the embargo token count by 1
		state.boardEmbargos.put(embargoCard,
			state.boardEmbargos.get(embargoCard) + 1);
		return;

	    case Feast:
		//trash feast
		player.playedCards.remove(this);
		//gain a card costing up to 5
		boolean found = false;
		int rand = 0;
		Card card = null;
		while(! found) {
		    //gain a random card costing up to 5
		    rand = (int) Randomness.random.nextInt(17);
		    card = state.cards.get(rand);
		    if((! state.isSupplyEmpty(card)) && (card.cost <= 5)) {
			state.reduceSupply(card);
			player.gain(card);
			found = true;
		    }
		}
		return;

	    case GreatHall:
		player.drawCard(); //+1 card
		player.numActions += 1; //+1 action
		return;

	    case Mine:
		//if there is a treasure that can be mined
		//trash it and upgrade to a treasure 3 cost more
		if(filter(player.hand, Type.TREASURE) != null) {
		    int randTreasure = Randomness.random.nextInt(2);
		    int minedTreasure = 0;
		    Card treasure = null;
		    if(randTreasure == 1) { //50% chance to copper > silver
			treasure = getCard(player.hand, CardName.Copper);
			if(treasure != null) {
			    System.out.println("Player upgrades copper to silver");
			    player.hand.remove(treasure);
			    player.hand.add(getCard(state.cards, CardName.Silver));
			    minedTreasure++;
			}
			treasure = getCard(player.hand, CardName.Silver);
			if((treasure != null) && (minedTreasure == 0)) {
			    System.out.println("Player upgrades silver to gold");
			    player.hand.remove(treasure);
			    player.hand.add(getCard(state.cards, CardName.Gold));
			}
		    } else {
			treasure = getCard(player.hand, CardName.Silver);
			if(treasure != null) {
			    System.out.println("Player upgrades silver to gold");
			    player.hand.remove(treasure);
			    player.hand.add(getCard(state.cards, CardName.Gold));
			    minedTreasure++;
			}
			//induced bug: player may play mine but not upgrade
			//their treasure (in the case they have copper/silver)
			/*
			treasure = getCard(player.hand, CardName.Copper);
			if((treasure != null) && (minedTreasure == 0)) {
			    System.out.println("Player upgrades copper to silver");
			    player.hand.remove(treasure);
			    player.hand.add(getCard(state.cards, CardName.Silver));
			}
			*/

		    }
		}
		//and put it into your hand
		return;

	    case Minion:
		player.numActions += 1; //+1 action
		if(Randomness.random.nextInt(2) == 1) { //choose
		    player.coins += 2; //+2 coins
		    System.out.println("Player gains 2 coins");
		} else { //or
		    //player discards hand and draws 4 cards
		    player.discard.addAll(player.hand);
		    player.hand.clear();
		    for(int i = 0; i < 4; i++)
			player.drawCard();
		    //everyone who has 5+ cards discards hand and draws 4
		    for(Player person : state.players) {
			if((person != player) && (person.hand.size() >= 5)) {
			    person.discard.addAll(person.hand);
			    person.hand.clear();
			    for(int i = 0; i < 4; i++)
				person.drawCard();
			}
		    }
		}
		return;

	    case SeaHag:
		//each other player discards top deck card,
		//and topdecks a curse
		for(Player person : state.players) {
		    if(person != player) {
			if(person.deck.isEmpty())
			    person.shuffleDeck();
			Card toDiscard = person.deck.poll();
			person.discard.add(toDiscard);
			Card curse = getCard(state.cards, CardName.Curse);
			if(state.reduceSupply(curse))
			    person.deck.addFirst(curse);
		    }
		}
		return;

	    case Smithy:
		//+3 cards
		for(int i = 0; i < 3; i++)
		    player.drawCard();
		return;

	    case Village:
		//+1 card, +2 actions
		player.drawCard();
		player.numActions += 2;
		return;

	    default: return;
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

    // this method will print out all the constant cards ENUM
    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();

	sb.append(" \t" + this.getCardName());
	sb.append("-" + this.getType() + "   ");
	sb.append("\t Cost: " + this.cost + " ");
	sb.append("\t Score: " + this.score + " ");
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

    @Override
    public int compareTo(Card o) {
	// TODO Auto-generated method stub
	return cardName.compareTo(o.cardName);
    }

    @Override
    public Card clone() throws CloneNotSupportedException {
	return (Card) super.clone();
    }
}
