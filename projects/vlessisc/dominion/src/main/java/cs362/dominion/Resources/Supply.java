package cs362.dominion.Resources;
import cs362.dominion.Cards.*;

import java.util.ArrayList;
import java.util.HashMap;


public class Supply {

	private Game game;
	private ArrayList<Class<? extends Card>> selectedKingdomCards;
	/*
	 * Each key(card type) hold the piles of those cards (array of cards) available in the supply
	 */
	private HashMap<Class<? extends Card>, ArrayList<Card>> supplyCards;	
	private ArrayList<Card> trashPile = new ArrayList<Card>();
	
	
	/**
	 * Constructor for the supply. Builds the supply and validates the kingdom card selections
	 * @param game
	 * @param kingdomCards
	 */
	public Supply(Game game, ArrayList<Class<? extends Card>> kingdomCards){
		// Assign the variables available to all methods within the supply 
		this.game=game;
		/**
		 *  BUG : This will let you add any kingdom cards you like (more than 10)
		 */
		this.selectedKingdomCards = kingdomCards;
		// Validate kingdom cards and build populate supply piles
		try {
			this.initializeSupplyCards();
		} catch (InstantiationException e) {} catch (IllegalAccessException e) {}
	}
	
	public void addCard(Card card){
		this.supplyCards.get(card.getClass()).add(card);
	}
	
	/**
	 * Initializes and populates the supply with the treasure, victory and chosen kingdom cards
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void initializeSupplyCards() throws InstantiationException, IllegalAccessException {
		// Create a new card supply when a new game is initialized. 
		// Each entry in the hash map will represent a pile on the board
		supplyCards = new HashMap<Class<? extends Card>, ArrayList<Card>>();
		// All treasure cards included by default
		supplyCards.put(Copper.class, new ArrayList<Card>());
		supplyCards.put(Silver.class, new ArrayList<Card>());
		supplyCards.put(Gold.class, new ArrayList<Card>());
		// All victory cards included by default
		supplyCards.put(Duchy.class, new ArrayList<Card>());
		supplyCards.put(Estate.class, new ArrayList<Card>());
		supplyCards.put(Province.class, new ArrayList<Card>());
		// Create new piles for each kingdom card
		for(Class<? extends Card> kingdomCardType : this.selectedKingdomCards){
			supplyCards.put(kingdomCardType, new ArrayList<Card>());
		}
		// Populate the supply with the cards respective types and initial supply numbers
		for(Class<? extends Card> cardType : supplyCards.keySet()){
			int numCards  = cardType.newInstance().getInitialSupply(game.getNumPlayers());
			for(int i=0; i<numCards; i++)
				supplyCards.get(cardType).add(cardType.newInstance());
		}	
	}
	
	
	public void trashCard(Card card){
		this.trashPile.add(card);
	}
	
	public Card drawCard(Class<? extends Card> cardType){
		return this.supplyCards.get(cardType).remove(0);	
	}
	
	
	public ArrayList<Class<? extends Card>> emptyPiles(){
		ArrayList<Class<? extends Card>> emptyPileTypes = new ArrayList<Class<? extends Card>>();
		for(Class<? extends Card> cardType : supplyCards.keySet()){
			if(this.cardsInPile(cardType)==0){ emptyPileTypes.add(cardType); }
		}
		return emptyPileTypes;
	}
	
	public ArrayList<Class<? extends Card>> supplyCardTypes(){
		return new ArrayList<Class<? extends Card>>(this.supplyCards.keySet());
	}
	
	public int cardsInPile(Class<? extends Card> cardType){
		return this.supplyCards.get(cardType).size();
	}
	
	
	
	
}
