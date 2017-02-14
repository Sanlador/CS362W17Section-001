import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DomGame {
	public int playerCount;									//Number of Players
	public int turnCount;
	public int gameSeed;
	public Player[] players;								//Array of Players
	public List<Deck> kingdomCards;							//The 10 Kingdom Cards
	public List<Deck> basicCards;							//The 7 basic Cards (0 - Copper, 1 - Silver, 2 - Gold, 3 - Curse, 4 - Estate, 5 - Duchy, 6 - Province
	public Random rand;
	
	public DomGame(int seed) {								//Creates the game with the Seed seed
		gameSeed = seed;
		rand = new Random(seed);
		playerCount = 2;
		players = new Player[playerCount];
		for (int i = 0; i < playerCount; i++) {
			players[i] = new Player(i, rand);
			System.out.println("Player " + i + " has ID: " + players[i].ID);
		}
		
		setupTable();						
		
	}
	
	public void setupTable() {								//Setup the Basic and Kingdom Cards
		basicCards = new ArrayList<Deck>();					
		kingdomCards = new ArrayList<Deck>();
		
		basicCards.add(new Deck(new Copper(), 50));			//Setup the Basic Cards
		basicCards.add(new Deck(new Silver(), 40));
		basicCards.add(new Deck(new Gold(), 30));
		basicCards.add(new Deck(new Curse(), 10));
		basicCards.add(new Deck(new Estate(), 8));
		basicCards.add(new Deck(new Duchy(), 8));
		basicCards.add(new Deck(new Province(), 8));
		
		List<Integer>endList = new ArrayList<Integer>();	
		List<Integer>selectList = new ArrayList<Integer>();
		
		for(int i = 0; i < 13; i++) {
			selectList.add(i);
		}
		Collections.shuffle(selectList, rand);					//Randomly select the 10 of 13 Kingdom Cards to be used in this game
		for(int i = 0; i < 10; i++) {
			endList.add(selectList.get(i));
		}
		//System.out.println(endList.toString());				//Print out which Cards were chosen	
			
		for(int i = 0; i < 10; i++) {							//Setup the Kingdom Cards
			switch (endList.get(i)) {
				case 0: 
					kingdomCards.add(new Deck(new Adventurer(), 10));
					break;
				case 1: 
					kingdomCards.add(new Deck(new Ambassador(), 10));
					break;
				case 2: 
					kingdomCards.add(new Deck(new Baron(), 10));
					break;
				case 3: 
					kingdomCards.add(new Deck(new Council_Room(), 10));
					break;
				case 4: 
					kingdomCards.add(new Deck(new Cutpurse(), 10));
					break;
				case 5: 
					kingdomCards.add(new Deck(new Embargo(), 10));
					break;
				case 6: 
					kingdomCards.add(new Deck(new Feast(), 10));
					break;
				case 7: 
					kingdomCards.add(new Deck(new Gardens(), 8));
					break;
				case 8: 
					kingdomCards.add(new Deck(new Great_Hall(), 8));
					break;
				case 9: 
					kingdomCards.add(new Deck(new Mine(), 10));
					break;
				case 10: 
					kingdomCards.add(new Deck(new Remodel(), 10));
					break;
				case 11: 
					kingdomCards.add(new Deck(new Smithy(), 10));
					break;
				case 12: 
					kingdomCards.add(new Deck(new Village(), 10));
					break;
			}
			//System.out.println("Deck " + i + " contains " + kingdomCards.get(i).cards.get(0));
		}
	}
	
	public boolean gameOver() {												//Check if Province pile is empty, or three Kingdom piles are gone
		int emptySupply = 0;
		if(basicCards.get(6).size == 0) { return true; }
		for(int i = 0; i < 10; i++) {
			if(kingdomCards.get(i).size == 0) {emptySupply += 1; }
		}
		if(emptySupply >= 3) { return true; }
		else { return false; }
	}
	
	public Player getWinner() {												//Returns the winning player
		if(players[0].VP > players[1].VP) { return players[0]; }			//Player 0 will win in the event of a tie, should be two winners declared - Bug
		else { return players[1]; }
	}
	
	public void play() {													//Play the game iterating through turns until one player wins
		for(int i = 0; i < playerCount; i++) {
			System.out.println("\nPlayer " + i + ", Turn: " + turnCount);	
			players[i].takeTurn(this);										//Pass the game to the players
			if(gameOver()) {
				System.out.println("Player " + getWinner().ID + " has won with " + getWinner().VP + " Victory Points!");
				break;
			}
			if(i == playerCount-1) { i = -1; turnCount++; }
		}
		
	}

}

