/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kin-Ho Lam
 */
public class AdventureTest {
    
    public AdventureTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    public Game init(int card)
    {
        Adventure newcard = new Adventure(card);
        System.out.println("Test Card " + newcard.name);
        Game game = new Game(); //create game object
        game.initilize_game(2,10); //int players, int number of kingdom cards
        game.player[0].hand = Player.append(game.player[0].hand, newcard);
        return game;
    }
    
    @Test
    public void testPlay() {
        System.out.println("play");
        Game game = new Game(); //create game object
	game.initilize_game(2,10); //int players, int number of kingdom cards
        Random rand = ThreadLocalRandom.current();
       
        for(int i = 6; i < 16; i++)
        {
            Adventure newcard = new Adventure(i);
            game.player[0].hand = Player.append(game.player[0].hand, newcard);
            assertNotNull(newcard); //make sure cards are created properly
        }
        
        for(int i = 0; i < 10; i++)
        {
            game.player[0].action_turns = 1;
            int play_c = game.randInt(rand, 0);
            assertTrue(game.player_play(0, play_c, rand, game));     
        }
    }
    
    @Test
    public void test_cellar()
    {
        Game game = init(6);
        Random rand = ThreadLocalRandom.current();
        game.player[0].action_turns = 1;
        int play_c = game.randInt(rand, 0);
        assertTrue(game.player_play(0, play_c, rand, game)); 
        assertTrue(game.player[0].hand.length != 0);
    }

    @Test
    public void test_chapel()
    {
        Game game = init(7);
        Random rand = ThreadLocalRandom.current();
        game.player[0].action_turns = 1;
        int play_c = game.randInt(rand, 0);
        assertTrue(game.player_play(0, play_c, rand, game)); 
        assertTrue(game.player[0].hand.length != 0);
    }
    
    @Test
    public void test_councilroom()
    {
        Game game = init(8);
        Random rand = ThreadLocalRandom.current();
        game.player[0].action_turns = 1;
        int play_c = game.randInt(rand, 0);
        assertTrue(game.player_play(0, play_c, rand, game)); 
        assertTrue(game.player[0].hand.length >= 4);
        assertTrue(game.player[0].buy_turns != 0);
    }
    
        @Test
    public void test_festival()
    {
        Game game = init(9);
        Random rand = ThreadLocalRandom.current();
        game.player[0].action_turns = 1;
        int play_c = game.randInt(rand, 0);
        assertTrue(game.player_play(0, play_c, rand, game)); 
        assertTrue(game.player[0].buy_turns != 0);
        assertTrue(game.player[0].action_turns != 0);
    }
    
        @Test
    public void test_village()
    {
        Game game = init(10);
        Random rand = ThreadLocalRandom.current();
        game.player[0].action_turns = 1;
        int play_c = game.randInt(rand, 0);
        assertTrue(game.player_play(0, play_c, rand, game)); 
        assertTrue(game.player[0].buy_turns != 0);
        assertTrue(game.player[0].action_turns != 0);
        assertTrue(game.player[0].hand.length >= 1);
    }
    
        @Test
    public void test_laboratory()
    {
        Game game = init(11);
        Random rand = ThreadLocalRandom.current();
        game.player[0].action_turns = 1;
        int play_c = game.randInt(rand, 0);
        assertTrue(game.player_play(0, play_c, rand, game)); 
        assertTrue(game.player[0].action_turns != 0);
        assertTrue(game.player[0].hand.length >= 2);
    }
    
        @Test
    public void test_smithy()
    {
        Game game = init(12);
        Random rand = ThreadLocalRandom.current();
        game.player[0].action_turns = 1;
        int play_c = game.randInt(rand, 0);
        assertTrue(game.player_play(0, play_c, rand, game)); 
        assertTrue(game.player[0].hand.length >= 3);
    }
    
        @Test
    public void test_witch()
    {
        Game game = init(13);
        Random rand = ThreadLocalRandom.current();
        game.player[0].action_turns = 1;
        game.player[1].vp = 3;
        int play_c = game.randInt(rand, 0);
        assertTrue(game.player_play(0, play_c, rand, game)); 
        assertTrue(game.player[0].hand.length >= 2);
        assertEquals(new Victory(16).name, game.player[1].deck[game.player[1].deck.length-1].name);
        assertTrue(game.player[1].vp != 3);
    }
    
            @Test
    public void test_market()
    {
        Game game = init(14);
        Random rand = ThreadLocalRandom.current();
        game.player[0].action_turns = 1;
        int play_c = game.randInt(rand, 0);
        assertTrue(game.player_play(0, play_c, rand, game)); 
        assertTrue(game.player[0].hand.length >= 1);
    }
    
            @Test
    public void test_woodcutter()
    {
        Game game = init(15);
        Random rand = ThreadLocalRandom.current();
        game.player[0].action_turns = 1;
        int play_c = game.randInt(rand, 0);
        assertTrue(game.player_play(0, play_c, rand, game)); 
        assertTrue(game.player[0].buy_turns >= 1);
    }
}
