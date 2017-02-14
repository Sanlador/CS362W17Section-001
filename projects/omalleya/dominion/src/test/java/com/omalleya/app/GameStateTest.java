package com.omalleya.app;

import static junit.framework.Assert.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.HashMap;



import java.util.LinkedList;
import java.util.List;

import com.omalleya.app.Card.CardName;
import com.omalleya.app.Card.Type;

/**
 * Unit test for simple App.
 */
public class GameStateTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public GameStateTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( GameStateTest.class );
    }

    /**
     * Tests adding player to game
     */
    public void testAddPlayer() throws Throwable
    {

        List<Card> cards;     
        GameState state,testState; 
        Randomness.reset(10);   
        // initialize a game state and player cards
        cards = new LinkedList<Card>(Card.createCards());  
        state = new GameState(cards);
        Player player1 = new Player(state, "player-1");          
        state.addPlayer(player1);  
        //copy or clone the game state to a test case
        testState=(GameState) state.clone();       
        Player player2 = new Player(state, "player-2");          
        state.addPlayer(player2);

        System.out.println("State with second player added: \n" + state.players);
        System.out.println("Test state without second player added: \n" + testState.players);
                     
        assertEquals(testState.players.size(), 1);
        assertEquals(state.players.size(), 2);
        assertTrue(state.players.contains(player2));
        assertFalse(testState.players.contains(player2));

    }

    /**
     * Tests initialization of game
     */
    public void testInitializeGame() throws Throwable
    {

        List<Card> cards;     
        GameState state,testState; 
        Randomness.reset(10);   
        // initialize a game state and player cards
        cards = new LinkedList<Card>(Card.createCards());  
        state = new GameState(cards);
        Player player = new Player(state, "player-1");          
        state.addPlayer(player);        
        player = new Player(state, "player-2");          
        state.addPlayer(player);
        //copy or clone the game state to a test case
        testState=(GameState) state.clone();              
        //Initialize the game!       
        state.initializeGame();

        System.out.println("Initial GameBoard");
        System.out.println(state.gameBoard.toString());

        System.out.println("Test GameBoard");
        System.out.println(testState.gameBoard.toString());

        System.out.println("Initial: " + state.gameBoard.get(Card.getCard(cards, CardName.Copper)));
        System.out.println("Initial as test state: " + testState.gameBoard.get(Card.getCard(cards, CardName.Copper)));

        int kingdomCounter = 0;
        for(Card card : state.gameBoard.keySet()) {
            if(card.getType() == Type.ACTION)
                kingdomCounter++;
        }

        //test that each player has a hand of 5 and deck of 5
        for(Player p : state.players) {
            assertEquals(p.hand.size(), 5);
            assertEquals(p.deck.size(), 5);
        }

        //test gameboard intialization and number of kingdom cards
        assertEquals(state.gameBoard.keySet().size(), 17);
        assertEquals(testState.gameBoard.keySet().size(), 0);
        assertEquals(kingdomCounter, 10);

    }

    /**
     * Tests remove card
     */
    public void testRemoveCard() throws Throwable
    {

        List<Card> cards;     
        GameState state,testState; 
        Randomness.reset(10);   
        // initialize a game state and player cards
        cards = new LinkedList<Card>(Card.createCards());  
        state = new GameState(cards);
        Player player = new Player(state, "player-1");          
        state.addPlayer(player);        
        player = new Player(state, "player-2");          
        state.addPlayer(player);                
        //Initialize the game!       
        state.initializeGame();
        //copy or clone the game state to a test case
        testState=(GameState) state.clone();

        System.out.println("Initial GameBoard");
        System.out.println(state.gameBoard.toString());

        System.out.println("Test GameBoard");
        System.out.println(testState.gameBoard.toString());

        System.out.println("Initial: " + state.gameBoard.get(Card.getCard(cards, CardName.Copper)));
        state.removeCard(Card.getCard(cards, CardName.Copper));
        System.out.println("Initial with removal of Copper: " + state.gameBoard.get(Card.getCard(cards, CardName.Copper)));
        System.out.println("Initial as test state: " + testState.gameBoard.get(Card.getCard(cards, CardName.Copper)));

        //assert that the value is one less now than in the initial state
        assertTrue(state.gameBoard.get(Card.getCard(cards, CardName.Copper)).equals(testState.gameBoard.get(Card.getCard(cards, CardName.Copper)) - 1));

    }

    /**
     * Tests play
     */
    public void testPlay() throws Throwable
    {

        List<Card> cards;     
        GameState state,testState; 
        Randomness.reset(10);   
        // initialize a game state and player cards
        cards = new LinkedList<Card>(Card.createCards());  
        state = new GameState(cards);
        Player player = new Player(state, "player-1");          
        state.addPlayer(player);        
        player = new Player(state, "player-2");          
        state.addPlayer(player);                
        //Initialize the game!       
        state.initializeGame();
        //copy or clone the game state to a test case
        testState=(GameState) state.clone();

        for(int i=0; i<state.gameBoard.keySet().size(); i++) {
            assertTrue(state.gameBoard.keySet().getClass().equals(testState.gameBoard.keySet().getClass()));
        }

        System.out.println("Initial GameBoard");
        System.out.println(state.gameBoard.toString());

        System.out.println("Test GameBoard");
        System.out.println(testState.gameBoard.toString());

        //tests that the values of cards in board are all the same
        assertTrue(state.gameBoard.values().containsAll( testState.gameBoard.values() ));

        HashMap<Player, Integer> winnersPlay = state.play();

        //some cards must have been removed from the gameboard
        assertFalse(state.gameBoard.values().containsAll( testState.gameBoard.values() ));

    }

    /**
     * Tests end game
     */
    public void testEndGame() throws Throwable
    {

        List<Card> cards;     
        GameState state,testState; 
        Randomness.reset(10);   
        // initialize a game state and player cards
        cards = new LinkedList<Card>(Card.createCards());  
        state = new GameState(cards);
        Player player = new Player(state, "player-1");          
        state.addPlayer(player);        
        player = new Player(state, "player-2");          
        state.addPlayer(player);                
        //Initialize the game!       
        state.initializeGame();
        state.players.get(0).initializePlayerTurn();
        //copy or clone the game state to a test case
        testState=(GameState) state.clone();

        assertTrue(!state.endGame());
        state.gameBoard.put(Card.getCard(cards, CardName.Province), 0);
        assertTrue(state.endGame());

        state.gameBoard.put(Card.getCard(cards, CardName.Province), 8);
        assertTrue(!state.endGame());
        state.gameBoard.put(Card.getCard(cards, CardName.Copper), 0);
        state.gameBoard.put(Card.getCard(cards, CardName.Silver), 0);
        state.gameBoard.put(Card.getCard(cards, CardName.Gold), 0);
        assertTrue(state.endGame());

    }

    /**
     * Tests get winners
     */
    public void testGetWinners() throws Throwable
    {

        List<Card> cards;     
        GameState state,testState; 
        Randomness.reset(10);   
        // initialize a game state and player cards
        cards = new LinkedList<Card>(Card.createCards());  
        state = new GameState(cards);
        Player player = new Player(state, "player-1");          
        state.addPlayer(player);        
        player = new Player(state, "player-2");          
        state.addPlayer(player);                
        //Initialize the game!       
        state.initializeGame();
        //copy or clone the game state to a test case
        testState=(GameState) state.clone();
        HashMap<Player, Integer> winnersPlay = state.play();
        HashMap<Player, Integer> winners = state.getWinners();

        //testing that winners contains the relevant info to determine if someone won
        assertTrue( winners.containsKey(state.players.get(0)) );
        assertTrue( winners.containsValue(state.players.get(0).scoreFor()) );
        assertTrue( winners.containsKey(state.players.get(1)) );
        assertTrue( winners.containsValue(state.players.get(1).scoreFor()) );
        assertTrue( winnersPlay.equals(winners) );

    }

    /**
     * Tests cloning
     */
    public void testClone() throws Throwable
    {

        List<Card> cards;     
        GameState state,testState; 
        Randomness.reset(10);   
        // initialize a game state and player cards
        cards = new LinkedList<Card>(Card.createCards());  
        state = new GameState(cards);
        Player player = new Player(state, "player-1");          
        state.addPlayer(player);        
        player = new Player(state, "player-2");          
        state.addPlayer(player);                
        //Initialize the game!       
        state.initializeGame();
        state.players.get(0).initializePlayerTurn();
        //copy or clone the game state to a test case
        testState=(GameState) state.clone();

        for(int i=0; i<state.players.size(); i++) {
            assertTrue(state.players.get(i).hand.equals(testState.players.get(i).hand));
            assertTrue(state.players.get(i).deck.equals(testState.players.get(i).deck));
            assertTrue(state.players.get(i).discarded.equals(testState.players.get(i).discarded));
            assertTrue(state.players.get(i).playedCards.equals(testState.players.get(i).playedCards));
        }

        for(int i=0; i<state.cards.size(); i++) {
            assertTrue(state.cards.get(i).toString().equals(testState.cards.get(i).toString()));
        }

        for(int i=0; i<state.gameBoard.keySet().size(); i++) {
            assertTrue(state.gameBoard.keySet().getClass().equals(testState.gameBoard.keySet().getClass()));
        }

    }

}
