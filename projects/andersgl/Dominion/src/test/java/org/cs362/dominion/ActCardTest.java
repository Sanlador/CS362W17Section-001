package org.cs362.dominion;

import org.cs362.dominion.Card.CardName;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

import java.util.List;

public class ActCardTest { //This class will test our action cards, and also implicitly test the implementation of playKingdomCard

	@Test
	public void test0() throws Throwable { //We will test Baron here, as well as the numActions variable
		System.out.println('\n' + "TEST 0 FOR CARDS" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
	  

		// initialize a game state and player cards
	
		 
			
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Baron));
		//Get number of estates before playing Baron (and might as well test numActions here too
		int actions = player.numActions;
		int numEstates = 0;
		
		for(int i = 0; i < state.players.get(0).hand.size(); i++){
			if(state.players.get(0).hand.get(i) == Card.getCard(cards, Card.CardName.Estate)){
				numEstates += 1; 
			}
		
			
		}
		for(int i = 0; i < state.players.get(0).deck.size(); i++){
			if(state.players.get(0).deck.get(i) == Card.getCard(cards, Card.CardName.Estate)){
				numEstates += 1; 
			}
		
			
		}
		for(int i = 0; i < state.players.get(0).discard.size(); i++){
			if(state.players.get(0).discard.get(i) == Card.getCard(cards, Card.CardName.Estate)){
				numEstates += 1; 
			}
		
			
		}
		
		state.players.get(0).playKingdomCard(); //Will only have baron, so play this
		
		int numEstates2 = 0;
		for(int i = 0; i < state.players.get(0).hand.size(); i++){
			if(state.players.get(0).hand.get(i) == Card.getCard(cards, Card.CardName.Estate)){
				numEstates2 += 1; 
			}
		}
		for(int i = 0; i < state.players.get(0).deck.size(); i++){
			if(state.players.get(0).deck.get(i) == Card.getCard(cards, Card.CardName.Estate)){
				numEstates2 += 1; 
			}
		
			
		}
		for(int i = 0; i < state.players.get(0).discard.size(); i++){
			if(state.players.get(0).discard.get(i) == Card.getCard(cards, Card.CardName.Estate)){
				numEstates2 += 1; 
			}
		
			
		}
		
		System.out.println('\n' + "Player actions before playing: " + actions);
		System.out.println("Player actions after playing (should be 0): " + state.players.get(0).numActions);
		
		assertEquals(actions - 1, state.players.get(0).numActions); //Make sure we lost action after playing card
		
		System.out.println('\n' + "Number of estates before playing: " + numEstates);
		System.out.println("Numer of estates after playing: " + numEstates2);
		
		assertEquals(numEstates + 1, numEstates2); //Make sure we gained an estate from playing baron
	
	}
	@Test
	public void test1() throws Throwable{ //Test Mine here
		System.out.println('\n' + "TEST 1 FOR CARDS" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
	  

		// initialize a game state and player cards
		
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Cooper)); //value 1
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Mine));
		
		int countedCoins = 0; 
		for(int i = 0; i < state.players.get(0).hand.size(); i++){
			countedCoins += state.players.get(0).hand.get(i).getTreasureValue();
		}
		for(int i = 0; i < state.players.get(0).discard.size(); i++){
			countedCoins += state.players.get(0).discard.get(i).getTreasureValue();
		}
		for(int i = 0; i < state.players.get(0).deck.size(); i++){
			countedCoins += state.players.get(0).deck.get(i).getTreasureValue();
		}
		
		state.players.get(0).playKingdomCard();
		
		int countedCoins2 = 0; 
		for(int i = 0; i < state.players.get(0).hand.size(); i++){
			countedCoins2 += state.players.get(0).hand.get(i).getTreasureValue();
		}
		for(int i = 0; i < state.players.get(0).discard.size(); i++){
			countedCoins2 += state.players.get(0).discard.get(i).getTreasureValue();
		}
		for(int i = 0; i < state.players.get(0).deck.size(); i++){
			countedCoins2 += state.players.get(0).deck.get(i).getTreasureValue();
		}
	
		System.out.println("Coins before: " + countedCoins);
		System.out.println("Coins after: " + countedCoins2);
		
		assert(countedCoins2 >= countedCoins); //We either add nothing or we gain coins
	}

	@Test
	public void test2() throws Throwable{ //Test Smithy
		System.out.println('\n' + "TEST 2 FOR CARDS" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
	  

		// initialize a game state and player cards
		
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Smithy)); //value 1
		
		int numCardsBefore = state.players.get(0).hand.size();
		state.players.get(0).playKingdomCard();
		int numCards = state.players.get(0).hand.size();
		
		System.out.println("Number of cards before using Smithy: " + numCardsBefore);
		System.out.println("Number of cards after using Smithy: " + numCards);
	
		assertEquals(numCardsBefore + 3, numCards); //Ensure 3 cards were gained
	}
	@Test
	public void test3() throws Throwable{ //Test greatHall
		System.out.println('\n' + "TEST 3 FOR CARDS" + '\n');
		
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
	  

		// initialize a game state and player cards
		
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.greatHall)); //value 1
		
		int numCardsBefore = state.players.get(0).hand.size();
		int numActionsBefore = state.players.get(0).numActions;
		state.players.get(0).playKingdomCard();
		int numCards = state.players.get(0).hand.size();
		int numAction = state.players.get(0).numActions;
		
		System.out.println("Number of cards before using greatHall: " + numCardsBefore); 
		System.out.println("Number of cards after using greatHall: " + numCards); //should be same since we discard greatHall and draw a card
		
		
		System.out.println("Number of actions before using greatHall: " + numActionsBefore); 
		System.out.println("Number of actions after using greatHall: " + numAction); //should be same since we used our first action to play the card
	
		assertEquals(numCardsBefore, numCards);
		assertEquals(numActionsBefore, numAction);
		
	}
	@Test
	public void test4() throws Throwable{ //Test Gardens
		System.out.println('\n' + "TEST 4 FOR CARDS" + '\n');
		
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
	  

		// initialize a game state and player cards
		
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Gardens));
		for(int i = 0; i < 20; i++){
			state.players.get(0).deck.add(Card.getCard(cards, Card.CardName.Cooper));
		} //Give the player 20 cards, they should have 2 extra points after playing Gardens
		int pointsBefore = state.players.get(0).extra;
		
		state.players.get(0).playKingdomCard();
		
		int pointsAfter = state.players.get(0).extra;
		
		System.out.println("Player points before playing Gardens: " + pointsBefore); //should be 0
		System.out.println("Player points after playing Gardens: " + pointsAfter); //should be 21 / 2 = 2 (round down)
		
		assertEquals(pointsBefore + 2, pointsAfter);
		
	}

	@Test
	public void test5() throws Throwable{ //Test Embargo
		System.out.println('\n' + "TEST 5 FOR CARDS" + '\n');
		
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
	  

		// initialize a game state and player cards
		
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Embargo));
		int embargoBefore = state.embargoTokens;
		int coinsBefore = state.players.get(0).coins;
		state.players.get(0).playKingdomCard();
		int embargoAfter = state.embargoTokens;
		int coinsAfter = state.players.get(0).coins;
		
		System.out.println("Player's coins before playing embargo: " + coinsBefore);
		System.out.println("Player's coins after playing embargo: " + coinsAfter);
		
		System.out.println("Embargo tokens before playing embargo: " + embargoBefore);
		System.out.println("Embargo tokens after playing embargo: " + embargoAfter);
		
		assertEquals(coinsBefore + 2, coinsAfter);
		assertEquals(embargoBefore + 1, embargoAfter);
	}

	@Test
	public void test6() throws Throwable{ //Test Cutpurse
		System.out.println('\n' + "TEST 6 FOR CARDS" + '\n');
		
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
	  

		// initialize a game state and player cards
		
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Cutpurse));
		state.players.get(1).hand.add(Card.getCard(cards, Card.CardName.Cooper)); //Cutpurse should remove this, this is what we will test
		//Give player 2 the copper so that player 1 can play cutpurse to remove it
		int copperBefore = 0;
		int copperAfter = 0;
		for(int i = 0; i < state.players.get(1).hand.size(); i++){
			if(state.players.get(1).hand.get(i) == Card.getCard(state.cards, CardName.Cooper)){
				copperBefore++;
	
			}
		}
		state.players.get(0).playKingdomCard();
		for(int i = 0; i < state.players.get(1).hand.size(); i++){
			if(state.players.get(1).hand.get(i) == Card.getCard(state.cards, CardName.Cooper)){
				copperAfter++;
			}
		}
		
		System.out.println("Copper before playing cutpurse: " + copperBefore);
		System.out.println("Copper after playing cutpurse: " + copperAfter);
	
		assertEquals(copperBefore, copperAfter + 1); //Make sure that it took away one copper

	}
	@Test
	public void test7() throws Throwable{ //Test Feast
		System.out.println('\n' + "TEST 7 FOR CARDS" + '\n');
		
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
	  

		// initialize a game state and player cards
		
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Feast));
		int isCouncilroomBefore = 0; //Boolean to check if councilroom is in hand or not (shouldn't be before, should be after)
		
		for(int i = 0; i < state.players.get(0).discard.size(); i++){
			if(state.players.get(0).discard.get(i) == Card.getCard(state.cards, CardName.councilRoom)){
				isCouncilroomBefore = 1;
			}
		}
		state.players.get(0).playKingdomCard();
		int isCouncilroomAfter = 0;
		for(int i = 0; i < state.players.get(0).discard.size(); i++){
			if(state.players.get(0).discard.get(i) == Card.getCard(state.cards, CardName.councilRoom)){
				isCouncilroomAfter = 1;
			}
		}
		System.out.println("Council room cards before playing Feast: " + isCouncilroomBefore);
		System.out.println("Council room cards after playing Feast: " + isCouncilroomAfter);
	
		assertEquals(isCouncilroomBefore + 1, isCouncilroomAfter);

	}
	
	@Test
	public void test8() throws Throwable{ //Test councilRoom
		System.out.println('\n' + "TEST 8 FOR CARDS" + '\n');
		
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
	  

		// initialize a game state and player cards
		
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.councilRoom));
		//Player draws 4 cards, gets extra buy. Other player draws 1 card
		int cardsBefore = state.players.get(0).hand.size();
		int buysBefore = state.players.get(0).numBuys;
		int cardsBefore2 = state.players.get(1).hand.size();
		
		state.players.get(0).playKingdomCard();
		
		int cardsAfter = state.players.get(0).hand.size();
		int buysAfter = state.players.get(0).numBuys;
		int cardsAfter2 = state.players.get(1).hand.size();
		
		System.out.println("Player 1 cards before playing councilRoom: " + cardsBefore);
		System.out.println("Player 1 cards after playing councilRoom: " + cardsAfter);
		System.out.println("Player 2 cards before playing councilRoom: " + cardsBefore2);
		System.out.println("Player 2 cards after playing councilRoom: " + cardsAfter2);
		System.out.println("Player 1 buys before playing councilRoom: " + buysBefore);
		System.out.println("Player 1 buys after playing councilRoom: " + buysAfter);
		
		assertEquals(cardsBefore + 4, cardsAfter); //Make sure player 1 has 4 more cards after playing councilRoom
		assertEquals(cardsBefore2 + 1, cardsAfter2); //Make sure player 2 has 1 more card after player 1 plays councilRoom
		assertEquals(buysBefore + 1, buysAfter); //Make sure player 1 has 1 more buy after playing councilRoom
	}
	
	@Test
	public void test9() throws Throwable{ //Test Village
		System.out.println('\n' + "TEST 9 FOR CARDS" + '\n');
		
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
	  

		// initialize a game state and player cards
		
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Village)); //draw card, +2 actions
		
		int cardsBefore = state.players.get(0).hand.size();
		int actionsBefore = state.players.get(0).numActions;
		
		state.players.get(0).playKingdomCard();
		
		int cardsAfter = state.players.get(0).hand.size();
		int actionsAfter = state.players.get(0).numActions;
		
		System.out.println("Cards before playing village: " + cardsBefore);
		System.out.println("Cards after playing village: " + cardsAfter); //Should be same, since we now longer hold village card but gain a different one
		System.out.println("Actions before playing village: " + actionsBefore); //Should be 1
		System.out.println("Actions after playing village: " + actionsAfter); //Should be 2, since we lose an action after playing the card then gain 2 (1-1+2 = 2)
		
		assertEquals(cardsBefore, cardsAfter);
		assertEquals(actionsBefore + 1, actionsAfter); //we gain 2 actions from having 0 actions
	}
	
	@Test
	public void test10() throws Throwable{ //Test Remodel
		System.out.println('\n' + "TEST 10 FOR CARDS" + '\n');
		
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
	  

		// initialize a game state and player cards
		
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Remodel));
		state.players.get(0).hand.add(0,Card.getCard(cards, Card.CardName.Estate)); //estate worth 2, put in front so we know it will be trashed w/ remodel
		state.cards.add(0,Card.getCard(cards, Card.CardName.Cutpurse)); //Player will end up gaining cut purse as result of trashing estate
		
		int isCutpurseBefore = 0; //Set to one if player gets a cutpurse
		int isCutpurseAfter = 0;
		for(int i = 0; i < state.players.get(0).discard.size(); i++){
			if(state.players.get(0).discard.get(i) == Card.getCard(cards, Card.CardName.Cutpurse)){
				isCutpurseBefore = 1;
			}
		}
		
		
		state.players.get(0).playKingdomCard();
		
		for(int i = 0; i < state.players.get(0).discard.size(); i++){
			if(state.players.get(0).discard.get(i) == Card.getCard(cards, Card.CardName.Cutpurse)){
				isCutpurseAfter = 1;
			}
		}
		
		System.out.println("1 if there was a cutpurse before playing Remodel (we expect 0): " + isCutpurseBefore);
		System.out.println("1 if there was a cutpurse after playing Remodel: " + isCutpurseAfter);
	
		assertEquals(isCutpurseAfter, 1);
	}
	
}
