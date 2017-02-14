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
public class RandomnessTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RandomnessTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RandomnessTest.class );
    }

    /**
     * Tests nest random int
     */
    public void testNextRandomInt() throws Throwable
    {

        int var1 = Randomness.nextRandomInt(50);
        int var2 = Randomness.nextRandomInt(50);

        //random variables before reset
        System.out.println(var1);
        System.out.println(var2);
        //these random numbers could be the same or different after this point
        //they must be from the range [0,50) though
        assertTrue(var1 == var2 || var1 != var2);
        assertTrue(var1 < 50 && var1 >= 0);
        assertTrue(var2 < 50 && var2 >= 0);
        assertTrue(Randomness.totalCallsToRandom != 0);
    

    }

    /**
     * Tests reset
     */
    public void testReset() throws Throwable
    {

        int var1 = Randomness.nextRandomInt(3);
        int var2 = Randomness.nextRandomInt(3);

        //random variables before reset
        System.out.println(var1);
        System.out.println(var2);
        //these random numbers could be the same or different after this point
        assertTrue(var1 == var2 || var1 != var2);

        Randomness.reset(10);   

        //random numbers after reset
        System.out.println(Randomness.nextRandomInt(3));
        System.out.println(Randomness.nextRandomInt(3));

        //these numbers are always going to be the same
        assertEquals(Randomness.nextRandomInt(3), Randomness.nextRandomInt(3));
    

    }

}
