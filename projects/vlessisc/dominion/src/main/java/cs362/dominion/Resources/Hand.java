package cs362.dominion.Resources;

import java.util.ArrayList;

import cs362.dominion.Cards.*;
import cs362.dominion.Cards.Card.cardTypes;
import cs362.dominion.Exceptions.InvalidSelectionException;
import cs362.dominion.Resources.Player;

public class Hand {

	private ArrayList<Card> handCards = new ArrayList<Card>();
	private ArrayList<Card> playedActionCards = new ArrayList<Card>();
	private ArrayList<Card> discardCards = new ArrayList<Card>();
	private ArrayList<Card> boughtCards = new ArrayList<Card>();
	private int drawnCards = 0;
	
	// holds the number of coins gained from specific options selected in action cards.
	private int actionCoins=0;
	
	

	
	private Deck deck;

	/**
	 * Constructs a new Hand. Sets the cards and deck. Calculates and sets the initial hand value.
	 * @param cards
	 * @param deck
	 */
	public Hand(ArrayList<Card> cards, Deck deck){
		this.handCards = cards;
		this.deck = deck;
	}

	/**
	 * Number of actions available is calculated as:
	 *   1 (one action granted per turn)
	 * + Actions gained from actions cards
	 * @return
	 */
	public int getActions(){
		int actions = 1;
		for(Card card : this.playedActionCards){ actions+=card.getActions(); }
		actions -= this.playedActionCards.size();
		return actions;
	}
	
	/**
	 * Number of buys available is calculated as:
	 *   1 (one buy granted per turn)
	 * + Buys gained from action cards
	 * - Number of cards bought during this turn
	 * @return number of buys available in hand
	 */
	public int getBuys(){
		int buys=1;
		for(Card card : this.playedActionCards){ buys+=card.getBuys(); }
		buys-=this.boughtCards.size();
		return buys;
	}
	
	/**
	 * Coins available to spend is calculated as:
	 *   Coins gained from action cards
	 * + Coins gained from treasure cards
	 * + Coins gain from special action card decisions
	 * - Coins spent on buying cards
	 * @return number of coins available in hand
	 */
	public int getCoins(){
		int coins=0;
		for(Card card : this.playedActionCards){ coins+=card.getCoins(); }
		for(Card card : this.handCards) { if(card.getType()==cardTypes.TREASURE){ coins+=card.getCoins(); }}
		for(Card card : this.boughtCards){ coins-=card.getCost(); }
		coins+=this.actionCoins;
		return coins;
	}
	
	public void addCard(Card card){
		this.handCards.add(card);
	}
	
	public int size(){
		return this.handCards.size();
	}
	
	public ArrayList<Card> allCards(){
		ArrayList<Card> allcard = new  ArrayList<Card>();
		allcard.addAll(this.handCards);
		allcard.addAll(this.boughtCards);
		allcard.addAll(this.playedActionCards);
		return allcard;
	}
	
	/**
	 * This is really not the best way to implement this, but again, time constraints. 
	 * Should be part of the getBuy() function within the card itself
	 * @param number
	 */
	public void addActionPoints(int number){
		this.actionCoins+=number;
	}
	
	public Option playCard(Game game, ArrayList<Player> players, Supply supply, Class<? extends Card> cardType) throws InstantiationException, IllegalAccessException{
		if(this.canPlay(cardType, game.getPhase())){
			Card toRemove = null;
			for(Card card : this.handCards){
				if(card.getClass()==cardType){ toRemove=card; }
			}
			if(toRemove==null) { System.out.println("Cant play"); return null; }
			Card toPlay = this.handCards.remove(this.handCards.indexOf(toRemove));
			for(int i=0; i<toPlay.getCards(); i++){ 
				Card card = this.deck.drawNextCard();
				if(card==null){continue;}
				this.handCards.add(card);
			}
			this.drawnCards+=toPlay.getCards();
			this.playedActionCards.add(toPlay);
			Option cardOption = toPlay.playCard(game, players, supply);
			
			return cardOption;
		 } 
		return null;
	}
	
	public void buyCard(Class<? extends Card> cardType, Game.gamePhase gamePhase, Supply supply) throws InvalidSelectionException{
		if(!this.canBuy(cardType, gamePhase, supply)){
			throw new InvalidSelectionException("Can not buy selected card.");
		}
		this.boughtCards.add(supply.drawCard(cardType));
	}
	
	
	/**
	 * Given the cards played during this hand, number of actions available, which cards are in the
	 * supply, etc. returns true if the card can be played 
	 * @param cardType
	 * @param gamePhase
	 * @return
	 */
	public Boolean canPlay(Class<? extends Card> cardType, Game.gamePhase gamePhase) {
		// If the user does not have the card in there hand, they can't play it

			if(!this.handContainsCard(cardType)){ return false; }
			if(gamePhase == Game.gamePhase.ACTION){
				// If user has no actions, no action cards can be played, return false
				if(this.getActions()<1){ return false;}
				// If the card is not a kingdom card, it can not be played in the action phase
				try {
					if(cardType.newInstance().getType()!=cardTypes.KINGDOM){ return false; }
				}catch (InstantiationException e) {} 
				 catch (IllegalAccessException e) {}
				return true;
			}
		
		return false;
	}
	
	/**	
	 * To buy a card, all of the following conditions must be met:
	 * 1) It must be the buy phase
	 * 2) The players hand must yield at least 1 buy
	 * 3) The players hand must yield enough coins to purchase the card
	 * 4) There must at least one card of this type in the supply	
	 * @param cardType
	 * @param gamePhase
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Boolean canBuy(Class<? extends Card> cardType, Game.gamePhase gamePhase, Supply supply){
		try {
			if(gamePhase != Game.gamePhase.BUY){ return false; }
			if(this.getBuys()<1){ return false;}
			if(cardType.newInstance().getCost()>this.getCoins()){ return false; }
			if(supply.emptyPiles().contains(cardType)){ return false; }
			return true;
		} catch (InstantiationException e) {} catch (IllegalAccessException e) {}
		return false;
	}
	
	
	/**
	 * If the hand contains the requested card, return true.
	 * Else return false
	 * @param cardType
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public Boolean handContainsCard(Class<? extends Card> cardType) {
		for(Card card : this.handCards){
			if(card.getClass()==cardType){ return true; }
		}
		return false;
	}
	
	
	/**
	 * Returns an array of all card types that can be bought with this hand
	 * @return
	 */
	public ArrayList<Class<? extends Card>> buyOptions(Game.gamePhase currentPhase, Supply supply){
		ArrayList<Class<? extends Card>> options = new ArrayList<Class<? extends Card>>();
		for(Class<? extends Card> supplyCardType : supply.supplyCardTypes())
			if(this.canBuy(supplyCardType, currentPhase, supply)){ options.add(supplyCardType); }
		return options;
	}
	
	/**
	 * Returns an array of all action cards that can be played with this hand
	 * @return
	 */
	public ArrayList<Class<? extends Card>> actionOptions(Game.gamePhase currentPhase, Supply supply){
		ArrayList<Class<? extends Card>> options = new ArrayList<Class<? extends Card>>();
		for(Class<? extends Card> supplyCardType : supply.supplyCardTypes()){
			if(this.canPlay(supplyCardType, currentPhase)){
				options.add(supplyCardType);
			}
		}
		return options;
	}
	
	
	public Card removeCardFromHand(Class<? extends Card> cardType){
		Card toRemove = null;
		if(!this.handContainsCard(cardType)){ return toRemove; }
		for(Card card : this.handCards){
			if(card.getClass()==cardType){
				toRemove=card;
				break;
			}
		}
		return this.handCards.remove(this.handCards.indexOf(toRemove));
	}
	
	public ArrayList<Class<? extends Card>> cardTypesInHand() {
		ArrayList<Class<? extends Card>> typesInHand = new ArrayList<Class<? extends Card>>();
		for(Card card : this.handCards){
			typesInHand.add(card.getClass());
		}
		return typesInHand;
	}
	
	public ArrayList<Card> removeAllCards(){
		ArrayList<Card> allCards = new ArrayList<Card>();
		while(this.handCards.size()>0){allCards.add(this.handCards.remove(0));}
		while(this.boughtCards.size()>0){allCards.add(this.boughtCards.remove(0));}
		while(this.discardCards.size()>0){allCards.add(this.discardCards.remove(0));}
		while(this.playedActionCards.size()>0){allCards.add(this.playedActionCards.remove(0));}
		return allCards;
	}
	
	public Boolean discard(Class<? extends Card> cardType){
		Card toDiscard = this.removeCardFromHand(cardType);
		if(toDiscard==null){return false;}
		this.discardCards.add(toDiscard);
		return true;
	}
	
	public void printHand(){
		
		if(this.drawnCards>0){ System.out.println("Drawn Cards: " + this.drawnCards); }
		
		if(this.handCards.size()>0){ System.out.println("Cards in hand:"); }
		for(Card card : this.handCards){
			System.out.println("- " + card.getName());
		}	
		
		if(this.playedActionCards.size()>0){ System.out.println("Played Action Cards:"); }
		for(Card card : this.playedActionCards){
			System.out.println("- " + card.getName());
		}
		if(this.discardCards.size()>0){ System.out.println("Discard Cards:"); }
		for(Card card : this.discardCards){
			System.out.println("- " + card.getName());
		}
		
		if(this.boughtCards.size()>0){ System.out.println("Bought Cards:"); }
		for(Card card : this.boughtCards){
			System.out.println("- " + card.getName());
		}
		
		
	}
	
}
