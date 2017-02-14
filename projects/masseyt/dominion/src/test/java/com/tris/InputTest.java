package com.tris;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tristan Massey on 2/12/2017.
 */
public class InputTest {
    private Input input = new Input();
    @Test
    public void testInputLarge() {
        for (int i = 0; i < 1000; i++) {
            int test = input.getInt(0, 100);
            assertTrue(test <= 100 && test >= 0);
        }
    }
    public void testInputSmall() {
        for (int i = 0; i < 100; i++) {
            int test = input.getInt(0, 5);
            assertTrue(test <= 5 && test >= 0);
        }
    }
}
