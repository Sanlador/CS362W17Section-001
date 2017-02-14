import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class Player_GameState_Test {
	private List<Card> cards;
    private GameState state, testState, controlState;
    private Player player1;
    private Player player2;
    private Player control;
    private Player control2;
    private Player player;
    
// // initialize a game state and player cards
	@Before
	public void reset(){
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		
		controlState = new GameState(cards);
		
		player1 = new Player(state, "player-1");
		state.addPlayer(player1);
		player2 = new Player(state, "player-2");
		state.addPlayer(player2);
		
		control = new Player(controlState, "Controled player 1");
		controlState.addPlayer(control);
		control2 = new Player(controlState, "Controled player 2");
		controlState.addPlayer(control);
	         
	    //Initialize the game!
		state.initializeGame();
	}
	
	@Test
	public void testall() throws Throwable {
		assertEquals(player1.hand.size(), 5); //IF the top 7 asserts are true, state initialize correctly
		assertEquals(player1.discard.size(), 0);
		assertEquals(player1.playedCards.size(), 0);
		assertEquals(player1.deck.size(), 5);
		assertEquals(player1.coins , 0);
		assertEquals(player1.numActions , 1);
		assertEquals(player1.numBuys , 1);
		
		player=(Player) player1.clone();
		player.drawCard();
		
		// Test Draw Card
		assertEquals(player1.hand.size()+1, player.hand.size());  //changed: draw a card, hand +1
		assertEquals(player1.discard.size(), player.discard.size());
		assertEquals(player1.playedCards.size(), player.playedCards.size());
		assertEquals(player1.deck.size()-1, player.deck.size()); // changed:draw a card, deck -1
		assertEquals(player1.coins , player.coins);
		assertEquals(player1.numActions , player.numActions);
		assertEquals(player1.numBuys , player.numBuys);
		
		// Test play kingdom card
		player.hand.add(Card.getCard(cards, Card.CardName.Gardens));
		player.playKingdomCard();
		assertEquals(player1.hand.size()+1, player.hand.size()); 
		assertEquals(player1.discard.size(), player.discard.size());
		assertEquals(player1.playedCards.size()+1, player.playedCards.size()); //changed: clone player played a card and now has 1 more than player 1 in played cards
		assertEquals(player1.deck.size()-1, player.deck.size());   
		assertEquals(player1.coins , player.coins);
		assertEquals(player1.numActions - 1, player.numActions); //changed: player used 1 actions
		assertEquals(player1.numBuys , player.numBuys);
		
		// Test scorefor function
		assertEquals(player1.scoreFor() +1, player.scoreFor()); // Due to the garden card played above, player has 1 more score than player1.
		
		// Test play treasure cards
		player=(Player) player1.clone(); // reset player
		player.hand.add(Card.getCard(cards, Card.CardName.Copper)); //create difference, because we don't have how many copper is in hand of player1
		player.playTtreasureCard();
		player1.playTtreasureCard();  
		assertEquals(player1.coins +1 , player.coins);
		
		// Test Buy cards
		assertEquals(player1.deck.size(), player.deck.size());  
		assertEquals(player1.discard.size(), player.discard.size());
		player.buyCard(state);
		assertEquals(player1.deck.size(), player.deck.size());  
		assertEquals(player1.discard.size()+1, player.discard.size()); // changed: Brought card goes to the discard pile
		
		//Test End turn
		assertTrue(player.playedCards.size() > 0);   // don't assert hand because hand maybe 0 already if you are lucky enough to get 5 copper at the first turn
		player.endTurn();
		assertEquals(player.playedCards.size(), 0);
		
		
		//Test initialize Player turn which resets everything
		player.initializePlayerTurn();
		assertEquals(player.hand.size(), 5); //Can have cards in discard, not sure number of cards in deck
		assertEquals(player.playedCards.size(), 0);
		assertEquals(player.coins , 0);
		assertEquals(player.numActions , 1);
		assertEquals(player.numBuys , 1);
		
	}
	//Test Game ---- game state.play()
	@Test
	public void testGame() throws Throwable {
		assertEquals(player1.scoreFor(), 3);
		assertEquals(player1.hand.size(), 5); //IF the top 7 asserts are true, state initialize correctly
		assertEquals(player1.discard.size(), 0);
		assertEquals(player1.playedCards.size(), 0);
		assertEquals(player1.deck.size(), 5);
		assertEquals(player1.coins , 0);
		assertEquals(player1.numActions , 1);
		assertEquals(player1.numBuys , 1);
		HashMap<Player, Integer> winners=state.play(); // game is only 10 rounds
		System.out.println ("Finished game.\n");
		for(Player p: winners.keySet()){
			System.out.println ("Player name: "+ p.player_username + " , Score: "+ p.scoreFor() );
		}
		player1.endTurn();
		player1.initializePlayerTurn();
		//assertTrue(player1.scoreFor() >= 3); // Not sure if player purchase Victory cards
		assertTrue(player1.hand.size() + player1.deck.size() + player1.playedCards.size() + player1.discard.size() > 10); //IF the top 7 asserts are true, state initialize correctly
	}
	
}
