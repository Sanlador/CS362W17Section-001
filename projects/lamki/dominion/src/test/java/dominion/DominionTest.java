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

/**
 *
 * @author Kin-Ho Lam
 */
public class DominionTest {
    
    public DominionTest() {
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
     * Test of main method, of class Dominion.
     */

    @Test
    public void test_0p()
    {
        System.out.println("0 PLAYERS");
        String[] args = {"2"};
        Dominion.main(args);
    }
    
    @Test
    public void test_1p()
    {
        System.out.println("1 PLAYERS");
        String[] args = {"1"};
        Dominion.main(args);
    }
    
    @Test
    public void test_2p()
    {
        System.out.println("2 PLAYERS");        
        String[] args = {"2"};
        Dominion.main(args);
    }
    
    @Test
    public void test_5p()
    {
        System.out.println("5 PLAYERS");
        String[] args = {"5"};
        Dominion.main(args);
    }
    
    @Test
    public void no_kingdom()
    {
        System.out.println("2 PLAYERS NO KINGDOM");
        String[] args = {"2", "2"};
        Dominion.main(args);
    }
    
    @Test
    public void no_args()
    {
        System.out.println("NO ARGS");
        String[] args = {};
        Dominion.main(args);
    }
    
}
