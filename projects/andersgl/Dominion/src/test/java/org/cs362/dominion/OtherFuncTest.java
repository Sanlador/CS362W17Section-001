package org.cs362.dominion;


import org.cs362.dominion.Card.CardName;
import org.cs362.dominion.Card.Type;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;



public class OtherFuncTest {

	@Test
	public void test0() { //Test the filter function in card, takes iterable of cards and a type for filter
		System.out.println('\n' + "TESTING FILTER FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		
		List <Card> filteredCards = Card.filter(cards, Card.Type.TREASURE);
		
		for(int i = 0; i < filteredCards.size(); i++){
			System.out.println("Card in filtered cards, all should be treasure: " + filteredCards.get(i));
			assert(filteredCards.get(i).getType() == Card.Type.TREASURE);
		}
		
		
	}

	@Test
	public void test1() { //Test getcard in card class, takes list of cards and cardname and returns a card if it finds it
		System.out.println('\n' + "TESTING getCard FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		List<Card> emptylist = new ArrayList<Card>();
		Card nullcard, card;
		
		nullcard = Card.getCard(emptylist, Card.CardName.Silver); //should return null
		card = Card.getCard(cards, Card.CardName.Silver); //should return silver
		
		System.out.println("Called with empty list, should be null: " + nullcard);
		System.out.println("Called with full list and silver, should be silver: " + card);
		
		assertEquals(card.getCardName(), Card.CardName.Silver);
		assertEquals(nullcard, null);
	}
	
	@Test
	public void test2() {
		System.out.println('\n' + "TESTING createCards FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		System.out.println("Size of the list result of createCards (should be 20): " + cards.size());
		assertEquals(cards.size(), 20);
		
	}
	
	@Test
	public void test3() {
		System.out.println('\n' + "TESTING score (from Card) FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		
		Card card = Card.getCard(cards, Card.CardName.Province); //score should be 6
		int cardScore = card.score();
		
		System.out.println("Result of calling score() with province: " + cardScore);
		assertEquals(cardScore, 6);
	
	}
	@Test
	public void test4() {
		System.out.println('\n' + "TESTING getTreasureValue (from Card) FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		
		Card card = Card.getCard(cards, Card.CardName.Gold); 
		int cardTres = card.getTreasureValue(); //tres should be 3
		
		System.out.println("Result of calling getTreasureValue with gold: " + cardTres);
		assertEquals(cardTres, 3);
	
	}
	@Test
	public void test5() {
		System.out.println('\n' + "TESTING getCost (from Card) FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		
		Card card = Card.getCard(cards, Card.CardName.Gold); 
		int cardCost = card.getCost(); //cost should be 6
		
		System.out.println("Result of calling getCost with gold: " + cardCost);
		assertEquals(cardCost, 6);
		
		
	}
	@Test
	public void test6() {
		System.out.println('\n' + "TESTING getCardName (from Card) FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		
		Card card = Card.getCard(cards, Card.CardName.Gold); 
		Card.CardName cardname = card.getCardName(); //cost should be 6
		
		System.out.println("Result of calling getCardName with gold: " + cardname);
		assertEquals(cardname, Card.CardName.Gold);
	}
	
	@Test
	public void test7() {
		System.out.println('\n' + "TESTING getType (from Card) FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		
		Card card = Card.getCard(cards, Card.CardName.Gold); 
		Card.Type cardType = card.getType(); //cost should be 6
		
		System.out.println("Result of calling getCardName with gold: " + cardType);
		assertEquals(cardType, Card.Type.TREASURE);
	}
	@Test
	public void test8() {
		System.out.println('\n' + "TESTING isGameOver (from gameState) FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
		boolean isOverBefore;
		boolean isOverAfter; //We will force an ending condition, then test to see if it ends game
		// initialize a game state and player cards
		
		Player player = new Player(state, "player-1");
		
		
		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();
		isOverBefore = state.isGameOver();
		
		
		state.gameBoard.values().clear();
		isOverAfter = state.isGameOver();
		
		System.out.println("Game over before: " + isOverBefore);
		System.out.println("Game over after: " + isOverAfter);
		
		assertEquals(isOverBefore, false);
		assertEquals(isOverAfter, true);
		
	}
	@Test
	public void test9() {
		System.out.println('\n' + "TESTING getWinners (from gameState) FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
		
		
		Player player = new Player(state, "player-1");
		
		
		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();
		
		state.players.get(0).extra += 10;
		state.players.get(1).extra += 10; //gives each player extra 10 points
		
		//int player1Score = state.players.get(0).scoreFor();
		//int player2Score = state.players.get(1).scoreFor();
		
		HashMap<Player, Integer> playerScore = state.getWinners();
		
		for (int i = 0; i < 2; i++) {
	        
	         System.out.println("Player " + (i + 1) + "'s score: " + playerScore.get(state.players.get(i)));
	         assert(playerScore.get(state.players.get(i)) >= 10); //We know its at least 10, we gave them 10 points
		}
		
	}
	@Test
	public void test10() {
		System.out.println('\n' + "TESTING drawCard FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
		
		
		Player player = new Player(state, "player-1");
		
		
		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();
		int beforeDraw = state.players.get(0).hand.size();
		state.players.get(0).drawCard();
		int afterDraw = state.players.get(0).hand.size();
		
		System.out.println("Cards before drawing: " + beforeDraw);
		System.out.println("Cards after drawing: " + afterDraw);
		
		assertEquals(beforeDraw + 1, afterDraw);
		
	}
	
	@Test
	public void test11() {
		System.out.println('\n' + "TESTING initializePlayerTurn() FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
		
		
		Player player = new Player(state, "player-1");
		
		
		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();
		
		state.players.get(0).initializePlayerTurn();
		int coins = state.players.get(0).coins;
		int buys = state.players.get(0).numBuys;
		int actions = state.players.get(0).numActions;
	
		System.out.println("Player coins: " + coins);
		System.out.println("Player buys: " + buys);
		System.out.println("Player actions: " + actions);
		
		assertEquals(coins, 0);
		assertEquals(buys, 1);
		assertEquals(actions, 1);
	}
	
	@Test
	public void test12() {
		System.out.println('\n' + "TESTING gain() FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
		
		
		Player player = new Player(state, "player-1");
		
		Card card = Card.getCard(cards, Card.CardName.Province);
		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();
	
		state.players.get(0).discard.clear();
		state.players.get(0).gain(card);
		
		System.out.println("Size of discard after gain (Should be 1): " + state.players.get(0).discard.size());
		assertEquals(state.players.get(0).discard.size(), 1);
		
	}
	
	@Test
	public void test13() {
		System.out.println('\n' + "TESTING discard FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
		
		
		Player player = new Player(state, "player-1");
		
		Card card = Card.getCard(cards, Card.CardName.Province);
		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();
	
		state.players.get(0).discard.clear();
		state.players.get(0).hand.clear();
		
		state.players.get(0).hand.add(card);
		state.players.get(0).hand.add(card);
		state.players.get(0).hand.add(card); //player should now only have 3 provinces
		int sizeBefore = state.players.get(0).hand.size();
		
		state.players.get(0).discard(card); //player will discard one of these
		
		int sizeAfter = state.players.get(0).hand.size();
		//We need to check if hand is one less in size and discard is 1
		System.out.println("Player hand size before: " + sizeBefore);
		System.out.println("Player hand size before: " + sizeAfter);
		
		System.out.println("Discard size after discarding (should be 1): " + state.players.get(0).discard.size());
	
		assertEquals(state.players.get(0).discard.size(), 1);
		assertEquals(sizeBefore, sizeAfter + 1);
	
	}
	
	@Test
	public void test14() {
		System.out.println('\n' + "TESTING scoreFor() FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
		
		
		Player player = new Player(state, "player-1");
		
		Card card = Card.getCard(cards, Card.CardName.Province);
		
		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();
	//Clear these so we can just fill them with score cards
		state.players.get(0).discard.clear();
		state.players.get(0).hand.clear();
		state.players.get(0).deck.clear();
		
		for(int i = 0; i < 5; i++){ //give player 5 province cards to hand
			state.players.get(0).hand.add(card);
		}
		for(int i = 0; i < 5; i++){ //give player 5 province cards to deck
			state.players.get(0).deck.add(card);
		}
		state.players.get(0).discard(card); //give 2 of the provinces from player's hand to the deck
		state.players.get(0).discard(card);
		
		state.players.get(0).extra += 5; //give player 5 extra points (gained from special cards ordinarily)
		//Total score should be 6 (5 + 5) + 5 = 65
		int score = state.players.get(0).scoreFor();
	
		System.out.println("Players score given by scoreFor (65 expected): " + score);
		assertEquals(score, 65);
	}
	
	@Test
	public void test15() {
		System.out.println('\n' + "TESTING playTreasureCard FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
		
		
		Player player = new Player(state, "player-1");
		
		Card card = Card.getCard(cards, Card.CardName.Silver);
		
		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();
	
		state.players.get(0).hand.clear();
		
		state.players.get(0).hand.add(card);
		state.players.get(0).hand.add(card);
		state.players.get(0).hand.add(card);
		//Coins should be 6 now when we call playTreasurecard
		state.players.get(0).playTtreasureCard();
		int coins = state.players.get(0).coins;
		
		System.out.println("Players coins (6 expected): " + coins);
	
		assertEquals(coins, 6);
	}
	
	@Test
	public void test16() {
		System.out.println('\n' + "TESTING buyCard FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
		
		
		Player player = new Player(state, "player-1");
		
		
		
		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();
	
		state.players.get(0).discard.clear();
		
		state.players.get(0).coins = 1;
		state.players.get(0).buyCard();
		
		Card cardBought = state.players.get(0).discard.get(0); //only one item in discard, it will be here
		System.out.println("Added to discard (Expected copper): " + cardBought.getCardName());
		assertEquals(cardBought.getCardName(), Card.CardName.Cooper);
	
	}
	
	
	@Test
	public void test17() {
		System.out.println('\n' + "TESTING endTurn FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
		
		
		Player player = new Player(state, "player-1");
		
		
		
		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();
	
		state.players.get(0).endTurn();
		int coins = state.players.get(0).coins;
		int buys = state.players.get(0).numBuys;
		int actions = state.players.get(0).numActions;
	
		System.out.println("Player coins (should be 0): " + coins);
		System.out.println("Player buys (should be 1): " + buys);
		System.out.println("Player actions (should be 1): " + actions);
		
		assertEquals(coins, 0);
		assertEquals(buys, 1);
		assertEquals(actions, 1);
	
	
	
	}
	@Test
	public void test18() {
		System.out.println('\n' + "TESTING nextRandomInt FUNCTION" + '\n');
		int num = Randomness.nextRandomInt(5);
		System.out.println("Randomness num: " + num);
		assert(num >= 0);
		assert(num < 5);
	
	}
	@Test
	public void test19() {
		System.out.println('\n' + "TESTING randomMember FUNCTION" + '\n');
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		Card c = Randomness.randomMember(cards);
		boolean isIn = false; //This will be used to check if the card returned by randomness is in fact valid
		for(int i = 0; i < cards.size(); i++){
			if(c == cards.get(i)){
				isIn = true;
			}
			
		}
		System.out.println("True if card returned was in the original set: " + isIn);
		assertEquals(isIn, true);
	
	}
	
	
}
