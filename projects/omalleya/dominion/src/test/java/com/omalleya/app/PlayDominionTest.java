package com.omalleya.app;

import static junit.framework.Assert.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;


import java.io.FileInputStream;
import java.io.IOException;



import java.io.InputStream;


import java.util.ArrayList;
import java.util.HashMap;



import java.util.LinkedList;
import java.util.List;

import com.omalleya.app.Card.CardName;
import com.omalleya.app.Card.Type;

/**
 * Unit test for simple App.
 */
public class PlayDominionTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PlayDominionTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PlayDominionTest.class );
    }

    /**
     * Tests play dominion
     */
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        PlayDominion.main(args);
    }

}
