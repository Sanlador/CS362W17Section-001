package cs362.dominion.Resources;


import cs362.dominion.Cards.Card;
import cs362.dominion.Exceptions.ConfiguationException;
import cs362.dominion.Exceptions.InvalidSelectionException;

import java.util.ArrayList;
import java.util.Random;
/**
 * @author chrisvlessis
 * Class representing a players deck. Each player will have a deck consisting of 7 coppers and 3
 * estates initialized upon construction.
 */
public class Game {
	public static final int MAX_PLAYERS = 2;
	private int numPlayers;
	public enum gamePhase {
		ACTION,
		BUY,
		CLEANUP,
	};
	/*
	 * Game state variables
	 */
	private Player currentPlayer;
	private gamePhase phase;
	private Hand handInPlay;
	
	private ArrayList<Player> players = new ArrayList<Player>();
	public Supply supply;
	
	/**
	 * Creates a new dominion game.
	 * @param num_players
	 * @param kingdomCards
	 * @throws ConfiguationException
	 */
	public Game(int num_players, ArrayList<Class<? extends Card>> kingdomCards) throws ConfiguationException{
		this.supply = new Supply(this, kingdomCards);
		this.phase = gamePhase.ACTION;
		this.numPlayers = num_players;
		initializePlayers(num_players);	
		this.currentPlayer = this.players.get(new Random().nextInt(this.players.size()-1));
		this.handInPlay =  this.currentPlayer.deck.hand;
	}

	/**
	 * Makes sure number of players is valid. If so, creates new players and adds them to the game 
	 * @param num_players
	 * @throws ConfiguationException 
	 */
	private void initializePlayers(int num_players) throws ConfiguationException{
		// Assert that the number of players is less than or equal to MAX PLAYERS and greater than
		if((num_players>Game.MAX_PLAYERS) || (num_players<=0))
			throw new ConfiguationException("Invalid number of players.");
		// Create num_players new players
		for(int i=0; i<num_players; i++){
			String player_name = "Player " + i;
			this.players.add(new Player(this, player_name));
		}
	}
	
	/**
	 * @return number of player in the game.
	 */
	public int getNumPlayers(){
		return this.numPlayers;
	}
	
	public gamePhase getPhase(){
		return this.phase;
	}
	
	public Player getCurrentPlayer(){
		return this.currentPlayer;
	}
	
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	
	/**
	 * Allows player to "end turn". Will hand setting the next game phase, discarding hands,
	 * setting the next player, etc.
	 */
	public void nextPhase(){
		if(this.phase==gamePhase.ACTION){
			this.phase = gamePhase.BUY; 
		}else if(this.phase==gamePhase.BUY){
			this.phase = gamePhase.CLEANUP; 
		}else if(this.phase==gamePhase.CLEANUP){ 
			this.currentPlayer.deck.discardHand(); 
			this.currentPlayer.incHandCount();
			this.phase = gamePhase.ACTION; 
			int nextPlayerIndex = (this.players.indexOf(this.currentPlayer) + 1)%(this.numPlayers);
			this.currentPlayer = this.players.get(nextPlayerIndex);
			this.handInPlay = this.currentPlayer.deck.hand;
		}
	}
	
	/**
	 * Returns an ArrayList of cards that are valid to be played given the state of the game, the 
	 * player whose turn it is, the cards in their hand, the state of the supply, etc.
	 * @return
	 */
	public ArrayList<Class<? extends Card>> getPlayableCardTypes(){
		ArrayList<Class<? extends Card>> playableCards = new ArrayList<Class<? extends Card>>();
		if(this.phase==gamePhase.ACTION){ playableCards = this.handInPlay.actionOptions(this.phase, this.supply); }
		if(this.phase==gamePhase.BUY){  playableCards = this.handInPlay.buyOptions(this.phase,this.supply);}
		return playableCards;
	}
	
	/**
	 * 
	 * @param cardType
	 * @return 
	 * @throws InvalidSelectionException
	 */
	public Option playCard(Class<? extends Card> cardType) throws InvalidSelectionException{
		if(this.phase==gamePhase.ACTION){ 
			return this.playActionPhase(cardType); 
		}
		if(this.phase==gamePhase.BUY){ 
			this.playBuyPhase(cardType); 
		}
		return null;
	}
	
	/** 
	 * Plays an action card of type cardType
	 * @param cardType
	 * @throws InvalidSelectionException
	 */
	private Option playActionPhase(Class<? extends Card> cardType) throws InvalidSelectionException{	
		Option cardOption = null;
		try {
			if(!this.handInPlay.canPlay(cardType, this.phase)){
				throw new InvalidSelectionException("Can not play selected card.");
			}
			cardOption = this.handInPlay.playCard(this, this.players, this.supply, cardType);
		} catch (InstantiationException e) {} catch (IllegalAccessException e) {}
		return cardOption;
	}
	
	/**
	 * Buys a card of type cardType
	 * @param cardType
	 * @throws InvalidSelectionException
	 */
	private void playBuyPhase(Class<? extends Card> cardType) throws InvalidSelectionException{
		if(!this.handInPlay.canBuy(cardType, this.phase, this.supply)){
			throw new InvalidSelectionException("Can not play selected card.");
		}
		this.handInPlay.buyCard(cardType, this.phase, this.supply);	
	}
	
	/**
	 * Finds the player with the most victory points.
	 * TODO: Check for ties.
	 * @return player with most victory points
	 */
	public Player getLeadingPlayer(){
		Player winner = null;
		int max_points=-1;
		for(Player player : this.players){
			if(winner!=null && player.getVictoryPoints()==max_points && player.getHands()<winner.getHands()){
				winner=player;
				max_points = player.getVictoryPoints();
				continue;
			}
			if(player.getVictoryPoints()>max_points){
				winner = player;
				max_points = player.getVictoryPoints();
			}
		}
		return winner;
	}
	
	/**
	 * Game is over if:
	 * 1) Any 3 piles are empty
	 * 2) All province cards are taken 
	 * There are 3 pile of province cards... so aren't these two are mutually inclusive?
	 * TODO: figure ^this out
	 */
	public Boolean isOver(){
		if(this.supply.emptyPiles().size()>=3){ return true; }
		return false;
	}
	
	
	/************************************************
	* DEBUG CONVIENIENCE METHODS
	************************************************/
	
	public Boolean _debug_GameState = false;
	public Boolean _debug_CurrentHand = false;
	public Boolean _debug_PlayerDecks = false;
	public Boolean _debug_PlayableCards = false;
	public Boolean _debug_EmptyPiles = false;
	public void _printAllDebugs(){
		
		System.out.println("***************************************************************");
		System.out.println("START DEBUG STREAM");
		System.out.println("***************************************************************");
		if(this._debug_GameState)
			this._printGameState();
		if(this._debug_CurrentHand)
			this._printCurrentHand();
		if(this._debug_PlayerDecks)
			this._printPlayerDecks();
		if(this._debug_PlayableCards)
			this._printPlayableCards();
		if(this._debug_EmptyPiles)
			this._printEmptyPiles();
		System.out.println("***************************************************************");
		System.out.println("END DEBUG STREAM");
		System.out.println("***************************************************************");
	}

	public void _printSupplyCards(){}
	
	public void _printPlayerDecks(){
		System.out.println("--------------------------");
		System.out.println("Player Decks");
		System.out.println("--------------------------");
		for(Player player : this.players){
			System.out.println(player.name + ":");
			for(Card card : player.deck.allCards()){
				System.out.println("- " + card.getName());
			}
		}
	}
	
	public void _printEmptyPiles(){
		ArrayList<Class<? extends Card>> emptyPiles = this.supply.emptyPiles();
		System.out.println("--------------------------");
		System.out.println("Empty Piles");
		System.out.println("--------------------------");
		for(Class<? extends Card> pile : emptyPiles){
			try {
				System.out.println("- " + pile.newInstance().getName());
			} catch (InstantiationException e) {} catch (IllegalAccessException e) {}
		}
	}
	
	public void _printCurrentHand(){
		System.out.println("--------------------------");
		System.out.println("Hand for " + this.currentPlayer.name + ":");
		System.out.println("--------------------------");
		this.handInPlay.printHand();
		System.out.println("--------------------------");
	}
	
	public void _printGameState(){
		System.out.println("--------------------------");
		System.out.println("Current Game State");
		System.out.println("--------------------------");
		System.out.println("Phase: "  + this.phase.toString());
		System.out.println("Player: " + this.currentPlayer.name);
		System.out.println("Coins: " + this.handInPlay.getCoins());
		System.out.println("Actions: " + this.handInPlay.getActions());
		System.out.println("Buys: " + this.handInPlay.getBuys());
		System.out.println("--------------------------");
	}
	
	public void _printPlayableCards(){
		ArrayList<Class<? extends Card>> playableCards = this.getPlayableCardTypes();
		System.out.println("--------------------------");
		System.out.println("Playable Cards");
		System.out.println("--------------------------");
		for(Class<? extends Card> card : playableCards){
			try {
				System.out.println("- " + card.newInstance().getName());
			} catch (InstantiationException e) {} catch (IllegalAccessException e) {}	
		}
		System.out.println("--------------------------");	
	}
	
	public void _printPlayerScores(){
		System.out.println("--------------------------");
		System.out.println("Player Scores");
		System.out.println("--------------------------");
		for(Player player : this.players){
			System.out.println(player.name + ": " + player.getVictoryPoints());
		}
		System.out.println("--------------------------");	
	}
		
}
