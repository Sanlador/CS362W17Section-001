/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion;

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
public class GameTest {

    
    public GameTest() {
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

    /**
     * Test of initilize_game method, of class Game.
     */
    @Test
    public void testInitilize_game() {
        try{
         System.out.println("initilize_game");
           int num_players = 0;
           int num_kingdom_cards = 0;
           Game instance = new Game();
           instance.initilize_game(num_players, num_kingdom_cards);
        }
        catch(Exception e){
           fail("Should not have thrown any exception");
        }
    }

    /**
     * Test of set_cards method, of class Game.
     */
    @Test
    public void testSet_cards() {
        try{
            System.out.println("set_cards");
            int imin = 0;
            int imax = 2;
            int val = 8;
            Game instance = new Game();
            instance.set_cards(imin, imax, val);
        }
        catch(Exception e){
           fail("Should not have thrown any exception");
        }
    }

    /**
     * Test of set_turn method, of class Game.
     */
    @Test
    public void testSet_turn() {
        System.out.println("set_turn");
        int prev_player = 2;
        Game instance = new Game();
        instance.initilize_game(prev_player, 0);
        int expResult = 0;
        int result = instance.set_turn(prev_player);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }

    /**
     * Test of check_win method, of class Game.
     */
    @Test
    public void testCheck_win() {
        System.out.println("check_win");
        Game instance = new Game();
        instance.initilize_game(2, 10);
        boolean expResult = false;
        boolean result = instance.check_win();
        assertEquals(expResult, result);
    }

    /**
     * Test of can_buy method, of class Game.
     */
    @Test
    public void testCan_buy() {
        System.out.println("can_buy");
        int t = 0;
        Game instance = new Game();
        instance.initilize_game(2, 10);
        boolean expResult = false;
        boolean result = instance.can_buy(t);
        assertEquals(expResult, result);
    }

    /**
     * Test of can_play method, of class Game.
     */
    @Test
    public void testCan_play() {
        System.out.println("can_play");
        int t = 0;
        Game instance = new Game();
        instance.initilize_game(2, 0);
        boolean expResult = false;
        boolean result = instance.can_play(t);
        assertEquals(expResult, result);
    }

    /**
     * Test of player_buy method, of class Game.
     */
    @Test
    public void testPlayer_buy() {
        System.out.println("player_buy");
        int t = 0;
        int card = 0;
        Game instance = new Game();
        boolean expResult = false;
        boolean result = instance.player_buy(t, card);
        assertEquals(expResult, result);
    }
}
