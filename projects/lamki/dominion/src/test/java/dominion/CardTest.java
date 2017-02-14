/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kin-Ho Lam
 */
public class CardTest {
    
    public CardTest() {
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
     * Test of print method, of class Card.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        for(int i  = 0; i < 17; i++)
        {
            Card instance = new Card(i);
            instance.print();
        }
    }

    /**
     * Test of play method, of class Card.
     */
    @Test
    public void testPlay() {
        System.out.println("play");
        int card = 0;
        Random rand = null;
        int hand = 0;
        Player p = null;
        Game g = null;
        Card instance = new Card();
        //instance.play(card, rand, hand, p, g);
    }
    
}
