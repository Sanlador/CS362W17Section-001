package org.cs362.dominion;

import java.util.Vector;
import java.util.Random;
import java.util.HashMap;


public class GameState {

	private Vector<Player> playerList;
	private int numPlayers;
	private int turnNum;
	HashMap<Card, Integer> supply;
	HashMap<Card, Integer> embargoList;
	
	public GameState (int numPlayers) {
		this.playerList = new Vector<Player>();
		for(int i = 0; i < numPlayers; i++){
			playerList.add(new Player());
		}
		this.numPlayers = numPlayers;
		this.turnNum = 0;
		this.supply = new HashMap<Card, Integer>();	//using hashmap so that we can keep track of # of cards left
		this.embargoList = new HashMap<Card, Integer>();
	}
	
	//sets game up after constructor runs
	public void initializeGame () {
		//making sure numPlayers is valid
		if( numPlayers > 4 || numPlayers < 2 ) {
			System.err.println("Please make the gamestate with only between 2 and 4 players.");
			return;
		}
		
		//Build up cards can choose from
		Vector<Card> availKingCards = new Vector<Card>();
		availKingCards.add(new Card("Adventurer"));
		availKingCards.add(new Card("Ambassador"));
		availKingCards.add(new Card("Baron"));
		availKingCards.add(new Card("CouncilRoom"));
		availKingCards.add(new Card("Cutpurse"));
		availKingCards.add(new Card("Embargo"));
		availKingCards.add(new Card("Feast"));
		availKingCards.add(new Card("Gardens"));
		availKingCards.add(new Card("GreatHall"));
		availKingCards.add(new Card("Mine"));
		availKingCards.add(new Card("Smithy"));
		availKingCards.add(new Card("Salvager"));
		availKingCards.add(new Card("SeaHag"));

		
		//randomly choose kingdom cards to add
		Random r = new Random();
		while ( supply.size() < 10 ) {
			int index = r.nextInt(availKingCards.size());		//random number pick
			Card nextCard = availKingCards.elementAt(index);
			if( supply.containsKey(nextCard) ) {	//if the card has already been added
				continue;
			}
			else {		//else, add the card
				if (nextCard.getType() != "action") {	//if it's an actionvictory, basically
					if (numPlayers == 2) {
						supply.put(nextCard, 8);						
					}
					else {
						supply.put(nextCard, 12);
					}
				}
				else {
					supply.put(nextCard, 10);
				}
			}
		}
		
		
		//build up embargo list
		for ( Card key : supply.keySet() ) {
			embargoList.put(key, 0);
		}
		
		
		//now, add treasure and victory cards
		supply.put(new Card("Copper"), 60);
		supply.put(new Card("Silver"), 40);
		supply.put(new Card("Gold"), 30);
		
		if(numPlayers == 2) {
			supply.put(new Card("Estate"), 8);
			supply.put(new Card("Duchy"), 8);
			supply.put(new Card("Province"), 8);
		}
		else {
			supply.put(new Card("Estate"), 12);
			supply.put(new Card("Duchy"), 12);
			supply.put(new Card("Province"), 12);
		}
		
		//curse cards!
		supply.put(new Card("Curse"), ((numPlayers-1)*10) );
		
		for(Player curPlayer : playerList) {		//initialize all the player decks
			curPlayer.initilizeDeck();
		}		
		
	}
	
	
	public void play() {
		Random r = new Random();
		while( this.isGameOver() ) {
			++turnNum;		//starts with turn 1
			System.out.println("This is turn: " + turnNum);
			int playerTurn = -1;
			for( Player curPlayer : playerList ) {
				++playerTurn;
				int numActions = 1;
				int numBuys = 1;
				int numCoins = 0;
				//int spendLimit = curPlayer.getMonOnHand();
				//Action phase
				while ( numActions > 0 ) {
					Card playedCard = curPlayer.playAction();
					switch(playedCard.getName()) {
							
					case "Adventurer":
						curPlayer.playAdventurer();
						--numActions;
						break;
							
					case "Ambassador":			//bug: ambassador has same effect as adventurer
						curPlayer.playAdventurer();
						--numActions;
						break;
							
					case "Baron":
						--numActions;
						++numBuys;
						if ( curPlayer.discard(new Card("Estate")) ) {
							numCoins += 4;
						}
						else {
							this.gain(new Card("Estate"), curPlayer);
						}
						break;
							
					case "CouncilRoom":
						--numActions;
						curPlayer.drawCard(4);
						++numBuys;
						//get all other players to draw a card:
						for( Player othPlayer : playerList ) {
							if ( othPlayer != curPlayer ) {	//if they aren't the current player
								othPlayer.drawCard(1);
							}
						}
						break;
							
					case "Cutpurse":
						--numActions;
						numCoins += 2;
						//get all other players to discard a copper
						for( Player othPlayer : playerList ) {
							if ( othPlayer != curPlayer ) {	//if they aren't the current player
								othPlayer.discard(new Card("Copper"));
							}
						}
						break;
							
					case "Embargo":
						--numActions;
						numCoins += 2;
						//embargo a random card
						this.embargo(getRandCard());
						break;
							
					case "Feast":	//trash this card, choose a random card from supply, gain it
						--numActions;
						curPlayer.trash(new Card("Feast"), "Play");
						Card feastCard = this.getRandCard();
						while(feastCard.getCost() > 5) {	//pick random card until its cost is less than 5
							feastCard = this.getRandCard();
						}
						curPlayer.gain(feastCard);
						//pick a random card, check if its cost is 5 or less, then gain it
						break;
							
					case "GreatHall":	//get an action point and a draw
						curPlayer.drawCard(1);
						break;
							
					case "Mine":	//trash a random treasure from hand, gain higher level treasure
						Vector<Card> playerHand = curPlayer.getHand();	//choose random card from hand, test if treasure, if so trash it
						int numTreasures = 0;
						for ( Card curCard : playerHand ) {
							if ( curCard.getType() == "treasure" ) {
								numTreasures++;
							}
						}
						if (numTreasures == 0) {	//if there are no treasures in hand when played
							break;
						}
						int index = r.nextInt(playerHand.size());
						while ( playerHand.elementAt(index).getType() != "treasure" ) {
							index = r.nextInt(playerHand.size());
						}
						//now that we know  current card is a treasure
						String foundCard = playerHand.elementAt(index).getName();
						curPlayer.trash(playerHand.elementAt(index), "Hand");
						switch(foundCard) {	//gain more expensive treasure... unless you trashed gold hah
						case "Copper":
							curPlayer.gain(new Card("Silver"));
							break;
						case "Silver":
							curPlayer.gain(new Card("Gold"));
							break;
						case "Gold":
							curPlayer.gain(new Card("Gold"));
							break;
						}
						break;
							
					case "Smithy":
						--numActions;
						curPlayer.drawCard(3);
						break;
							
					case "Salvager":	//give a buy, trash random card from hand, gain coin equal to its cost
						--numActions;
						++numBuys;
						Vector<Card> playerHand1 = curPlayer.getHand();
						int index1 = r.nextInt(playerHand1.size());
						while ( playerHand1.elementAt(index1).getType() != "treasure" ) {
							index1 = r.nextInt(playerHand1.size());
						}
						break;
					
					case "SeaHag":		//Each other player discards the top card of his deck, then gains a Curse card
						--numActions;
						//get all other players to discard a card:
						for( Player othPlayer : playerList ) {
							if ( othPlayer != curPlayer ) {	//if they aren't the current player
								othPlayer.drawToDiscard(1);
								othPlayer.gain(new Card("Curse"));
							}
						}
						break;
					}
				}
				
				//Buy phase
				numCoins += curPlayer.getMonOnHand();
				while ( numBuys > 0 ) {		//buy a random card!
					if ( numCoins < 2 ) {	//can't buy anything if broke
						break;
					}
					Card buyCard = getRandCard();
					if ( buyCard.getCost() < numCoins ) {
						this.gain(buyCard, curPlayer);
					}
				}
				
				
				//Clear phase
				curPlayer.clearPhase();
			}
		}
		Player winner = this.getWinner();
		System.out.println("And the winner is: Player " + playerList.indexOf(winner) + " with " + winner.getVicPoints() + " points!");
	}
	
	
	public Card getRandCard() {
		Random r = new Random();
		Vector<Card> cardList = new Vector<Card>(supply.keySet());		//supply's keys are Cards, store them in a vector of cards
		int randNum = r.nextInt(supply.size());
		Card randCard = cardList.elementAt(randNum);
		return randCard;
	}
	
	public Player getWinner() {
		Player winningPlayer = playerList.elementAt(0);
		for ( int i = 0; i < numPlayers; ++i ) {
			if ( playerList.elementAt(i).getVicPoints() > winningPlayer.getVicPoints() ) {
				winningPlayer = playerList.elementAt(i);
			}
		}
		return winningPlayer;
	}
	
	//get the number of a card stored in supply
	public int getSupplyVal ( Card testcard ) {
		return supply.get(testcard);
	}
	
	//mark a card as embargoed
	public void embargo ( Card embCard ) {
		embargoList.put(embCard, embargoList.get(embCard) + 1);
	}
	
	//to be called when a player gains a card
	public void gain(Card cardGained, Player gainPlayer) {
		if( supply.get(cardGained) > 0 ){
			supply.put(cardGained, supply.get(cardGained) - 1);
			gainPlayer.gain(cardGained);
		}
		int embNum = embargoList.get(cardGained);	//get the number of embargoes on the card being gained
		for ( int i = 0; i < embNum; ++i ) {
			gainPlayer.gain(new Card("Curse"));
		}
	}
	
	public boolean isGameOver() {
		int numEmpty = 0;
		for ( Integer curStack : supply.values() ) {
			if ( curStack == 0 ) {
				numEmpty++;
			}
			if ( numEmpty == 3 ) {
				return true;
			}
		}
		Card palisade = new Card("Palisade");
		if ( supply.get(palisade) == 0 ) {
			return true;
		}
		return false;
	}
	
	/*public void playGreatHall(Player plrPlayedCrd) {
		
	}*/
	
	
}
