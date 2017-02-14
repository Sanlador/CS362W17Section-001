package org.cs362.dominion;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//This class will test the "value cards" including victory and treasure cards
public class ValCardTest {
	
	@Test
	public void test0() throws Throwable{ //Test our treasure cards, simple value checking
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
		//Give player 0 one of each treasure card to ensure they all have right value at once (player will
		//also draw extra copper, so we shouldn't expect exactly 6 for value)
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Cooper)); //value 1
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Silver)); //value 2
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Gold)); //value 3
		
		int countedCoins = 0; 
		for(int i = 0; i < state.players.get(0).hand.size(); i++){
			countedCoins += state.players.get(0).hand.get(i).getTreasureValue();
		}
		state.players.get(0).playTtreasureCard();
		
		System.out.println('\n' +"Expected value: " + countedCoins);
		System.out.println("Player's actual current coins: " + state.players.get(0).coins);
		
		assertEquals(countedCoins, state.players.get(0).coins);
		
	}

	@Test
	public void test1() throws Throwable{ //Test our victory cards
		List<Card> cards = new ArrayList<Card>(Card.createCards());
		GameState state = new GameState(cards);
		
		
		
		Player player = new Player(state, "player-1");
		//player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		state.addPlayer(player);
		player = new Player(state, "player-2");
		state.addPlayer(player);
		
		//Initialize the game!
		state.initializeGame();
		
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Province)); //value 6
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Duchy)); //value 3
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Estate)); //value 1
		
		int countedPoints = 0; 
		for(int i = 0; i < state.players.get(0).hand.size(); i++){
			countedPoints += state.players.get(0).hand.get(i).score();
		}
		for(int i = 0; i < state.players.get(0).discard.size(); i++){
			countedPoints += state.players.get(0).discard.get(i).score();
		}
		for(int i = 0; i < state.players.get(0).deck.size(); i++){
			countedPoints += state.players.get(0).deck.get(i).score();
		}
		
		
		System.out.println('\n' +"Expected value for points: " + countedPoints);
		System.out.println("Player's actual current points: " + state.players.get(0).scoreFor());
		
		assertEquals(countedPoints, state.players.get(0).scoreFor());
	
		state.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Curse));
		
		System.out.println("Testing curse, this should be one more than below: " + countedPoints);
		System.out.println("Player's actual current points, with curse: " + state.players.get(0).scoreFor());
		
		assertEquals(countedPoints - 1, state.players.get(0).scoreFor()); //Assert that the curse card removes one from score
	}
	
	
	
}
