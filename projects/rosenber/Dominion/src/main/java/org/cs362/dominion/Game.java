/*
 * This class implements the game of dominion to be played. All classes and objects
 * are initialized in this class. This class facilitates the gameplay between the
 * players and acts and the unspoken referee of Dominion.
 * 
 * Robert Rosenberger
 * 2/10/2017
 */

package org.cs362.dominion;

import java.util.ArrayList;

public class Game {
	
	Player currentPlayer;
	int curPlayerIdx;
	ArrayList<Player> players;
	Board board;
	
	//constructor
	public Game(int numPlayers, boolean AIonly){
		currentPlayer = null;
		curPlayerIdx = 0;
		players = new ArrayList<Player>();
		board = new Board();
		
		//initialize board with decks of cards
		board.createDeck(new Adventurer(), 30);
		board.createDeck(new Ambassador(), 30);
		board.createDeck(new Baron(), 30);
		board.createDeck(new Copper(), 30);
		board.createDeck(new CouncilRoom(), 30);
		board.createDeck(new Curse(), 30);
		board.createDeck(new Cutpurse(), 30);
		board.createDeck(new Duchy(), 30);
		board.createDeck(new Embargo(), 30);
		board.createDeck(new Estate(), 30);
		board.createDeck(new Feast(), 30);
		board.createDeck(new Garden(), 30);
		board.createDeck(new Gold(), 30);
		board.createDeck(new GreatHall(), 30);
		board.createDeck(new Mine(), 30);
		board.createDeck(new Province(), 30);
		board.createDeck(new Salvager(), 30);
		board.createDeck(new Silver(), 30);
		board.createDeck(new Smithy(), 30);
		board.createDeck(new Village(), 30);
		
		if(AIonly){
			for(int j=0; j<numPlayers; j++)
				players.add(new AIPlayer(j));
		}
		else{
			//TODO: make non ai-player init
		}
		for(int j=0; j<numPlayers; j++){
			//draw 7 copper
			for(int k=0; k<7; k++)
				players.get(j).giveCard(board.draw(board.getDeckIdx("Copper")));
			//draw 3 estates
			for(int k=0; k<3; k++)
				players.get(j).giveCard(board.draw(board.getDeckIdx("Estate")));
		}
		
	}
	
	//turn
	public void turn(){
		System.out.println("Beginning " + currentPlayer.getName()+"'s turn!\n");
		actionPhase();
		buyPhase();
		cleanPhase();
	}
	
	//action phase
	public void actionPhase(){
		System.out.println("\n******************************\n"
				           + "*        Action Phase        *\n"
				           + "******************************\n");
		ArrayList<Card> played = new ArrayList<Card>();
		do{
			Card choice = currentPlayer.playCard();
			if(choice != null){ //didn't chose to skip
				played.add(0, choice);
				played.get(0).Action(players, currentPlayer, board);
			}
			currentPlayer.addActions(-1);
		}while(currentPlayer.getActions() > 0);
		for(int j=0; j<played.size(); j++){
			currentPlayer.giveCard(played.get(0));
			played.remove(0);
		}
	}
	
	//buy phase
	public void buyPhase(){
		System.out.println("\n******************************\n"
		           + "*          Buy Phase         *\n"
		           + "******************************\n");
		currentPlayer.computeMoney();
		do{
			currentPlayer.buyCard(board);
			currentPlayer.addBuys(-1);
		}while(currentPlayer.getBuys() > 0);
	}
	
	//clean phase
	// - resets for next turn
	public void cleanPhase(){
		currentPlayer.discardHand();
		currentPlayer.setBuys(1);
		currentPlayer.setMoney(0);
		currentPlayer.setActions(1);
		for(int j=0; j<5; j++)
			currentPlayer.drawCard();
	}
	
	//check for game over
	public boolean isGameOver(){
		if(board.numCardsInDeck("Province") == 0)
			return true;
		else{
			int numWithZero = 0;
			for(int j=1; j<board.numDecks(); j++)
				if(board.numCardsInDeck(j) == 0)
					numWithZero++;
			if(numWithZero > 3)
				return true;
		}
		return false;
	}
	
	//declare winner
	public Player declareWinner(){
		double maxVP = (Double.POSITIVE_INFINITY * -1);
		Player winner = null;
		for(int j=0; j<players.size(); j++)
			if(players.get(j).getVictoryPoints() > maxVP)
				winner = players.get(j);
		System.out.println("\n*********************\nThe game is over!\n"
				+ winner.getName() + " is the winner with "
				+ maxVP + " victory points!!\n"
						+ "************************\n");
		return winner;
	}
	
	//play a game
	public void play(){
		System.out.println("\n\n\n**************************"
				+ "\n\nWelcome to Dominion!!!!\n\n"
				+ "***************************\n\n");
		do{
			currentPlayer = players.get(curPlayerIdx);
			turn();
			curPlayerIdx++;
			if(curPlayerIdx >= players.size())
				curPlayerIdx = 0;
		}while(!isGameOver());
		declareWinner();
	}
	
}
