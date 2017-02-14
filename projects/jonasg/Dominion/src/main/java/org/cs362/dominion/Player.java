package org.cs362.dominion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.cs362.dominion.Card.Type;


public class Player implements Cloneable {
    List<Card> hand = new ArrayList<Card>();
    LinkedList<Card> deck = new LinkedList<Card>();
    List<Card> discard = new ArrayList<Card>();
    List<Card> playedCards = new ArrayList<Card>();

    String player_username;

    int numActions;
    int numBuys;
    int coins;

    final GameState gameState;

    public Player(GameState gameState,String player_username) {
	this.player_username = player_username;
	this.gameState=gameState;
    }

    public void shuffleDeck() {
	System.out.println("Shuffling deck of player " + player_username);
	while(discard.size() > 0) {
	    int ndx = (int) Randomness.nextRandomInt(discard.size());
	    deck.add(discard.remove(ndx));
	}
    }

    final Card drawCard() {
	if (deck.isEmpty()) // Deck is empty
	    shuffleDeck();

	Card toDraw = deck.poll(); //java list pop method
	hand.add(toDraw);// Add card to hand and hand count automatically will
	// be incremented since we use List
	System.out.println("draw " + toDraw);
	Collections.sort(hand);
	return toDraw;
    }

    final void initializePlayerTurn() {
	//initialize first player's turn
	numActions = 1;
	coins = 0;
	numBuys = 1;
	//Shuffle your starting 10 cards (7 Coppers & 3 Estates) and place
	//them face-down as your Deck. Draw the top 5 cards as your starting hand
	for (int i = 0; i < 5; i++) {
	    drawCard();
	}
    }

    //card goes in discard
    final boolean gain(Card card) {
	//add said card to discard
	discard.add(card);
	System.out.println(this.player_username+" gains "+card);
	return true;
    }

    //Discard card
    public void discard(Card card) {
	hand.remove(card);
	discard.add(card);
	System.out.println("Player:  "+player_username+" discards "+card);
    }

    public void playKingdomCard() {
	while (numActions > 0) {
	    List<Card> actionCards = Card.filter(hand, Type.ACTION);

	    if (actionCards.size() == 0)
		return;

	    Card c = (Card)actionCards.get(0);
	    if (c == null)
		return;

	    //induced bug: does not decrement actions
	    numActions -= 1;
	    playedCards.add(c);
	    hand.remove(c);
	    c.play(this, gameState);
	}
    }

    final int scoreFor() {
	int gardens = 0;
	int score = 0;
	for (Card c : hand) {
	    score += c.score();
	    if(c.getCardName() == Card.CardName.Gardens)
		gardens++;
	}
	for (Card c : deck) {
	    score += c.score();
	    if(c.getCardName() == Card.CardName.Gardens)
		gardens++;
	}
	//score from discard
	for (Card c : discard) {
	    score += c.score();
	    if(c.getCardName() == Card.CardName.Gardens)
		gardens++;
	}
	score += ((int) ((discard.size() + hand.size() + deck.size()) / 10)) * gardens;
	return score;
    }

    public void playTreasureCard() {
	List<Card> treasureCards = Card.filter(hand, Type.TREASURE);
	System.out.println("Player buyphase");

	if(treasureCards.size() == 0)
	    return;

	for(Card card : treasureCards) {
	    System.out.println("Player plays " + card.toString());
	    playedCards.add(card);
	    hand.remove(card);
	    coins += card.getTreasureValue();
	}
    }
    public void buyCard(GameState state) {
	System.out.println("Player has " + coins + " coins");
	int randBuy = Randomness.random.nextInt(17);
	Card buyCard = null;
	while(numBuys > 0) {
	    if(coins == 0)
		break;
	    buyCard = state.cards.get(randBuy);
	    if((!state.isSupplyEmpty(buyCard)) &&
		    (buyCard.getCost() <= coins)) {
		state.reduceSupply(buyCard);
		System.out.println("Player buys " + buyCard);
		gain(buyCard);
		numBuys --;
		coins -= buyCard.getCost();
		for(int i = 0; i < state.boardEmbargos.get(buyCard); i++) {
		    Card curse = Card.getCard(state.cards, Card.CardName.Curse);
		    if(! state.isSupplyEmpty(curse)) {
			state.reduceSupply(curse);
			gain(curse);
		    }
		}
	    }
	    randBuy = Randomness.random.nextInt(17);
	}
    }

    final void endTurn() {
	System.out.println(player_username + " discards their hand and played cards");
	//move cards in hand to discard
	discard.addAll(hand);
	hand.clear();

	//move played cards to discard
	discard.addAll(playedCards);
	playedCards.clear();

	for(int i = 0; i < 5; i++)
	    drawCard();

	numActions = 1;
	//induced bug, does not reset coins
	//coins = 0;
	numBuys = 1;
	System.out.println(" --- " + this.player_username + " done");
    }

    public void printStateGame(){
	System.out.println(" --- " + this.player_username + " --- ");
	System.out.println(" --- --------------------------- --- ");
	System.out.println(this.gameState.toString());
	System.out.println(" --- --------------------------- --- ");
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(" --- " + this.player_username + " --- ");
	sb.append(" --- --------------------------- --- ");
	sb.append("Hand: " + this.hand);
	sb.append("Discard: " + this.discard);
	sb.append("Deck: " + this.deck);
	sb.append("Played Cards: " + this.playedCards);
	sb.append("numActions: " + this.numActions);
	sb.append("coins: " + this.coins);
	sb.append("numBuys: " + this.numBuys);
	sb.append("\n");

	return sb.toString();
    }

    protected Player clone() throws CloneNotSupportedException {
	Player clonePlayer=(Player) super.clone();
	clonePlayer.hand = new ArrayList<Card>(hand);
	clonePlayer.deck = new LinkedList<Card>(deck);
	clonePlayer.discard = new ArrayList<Card>(discard); // int
	clonePlayer.playedCards = new ArrayList<Card>(playedCards);
	return clonePlayer;
    }
}
