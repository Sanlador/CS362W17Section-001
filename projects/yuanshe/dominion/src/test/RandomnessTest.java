package test;

import dominion.Randomness;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by [author] on 2/4/2017.
 */
public class RandomnessTest {

    @Before
    public void resetSeed()
    {
        Randomness.reset(24245);
    }

    @Test
    public void generateRandIntTest()
    {
        assertEquals(550, Randomness.nextRandomInt(1000));
        assertEquals(663, Randomness.nextRandomInt(1000));
        assertEquals(713, Randomness.nextRandomInt(1000));
        assertEquals(642, Randomness.nextRandomInt(1000));
        assertEquals(431, Randomness.nextRandomInt(1000));
        assertEquals(610, Randomness.nextRandomInt(1000));
        assertEquals(581, Randomness.nextRandomInt(1000));
        assertEquals(970, Randomness.nextRandomInt(1000));
        assertEquals(950, Randomness.nextRandomInt(1000));
        assertEquals(105, Randomness.nextRandomInt(1000));
    }

    @Test
    public void randomMemberTest()
    {
        List<Integer> iList = new ArrayList<>(Arrays.asList(23,24,25,26,27,28,29,30));
        assertTrue(iList.contains(Randomness.randomMember(iList)));
        assertTrue(iList.contains(Randomness.randomMember(iList)));
        assertTrue(iList.contains(Randomness.randomMember(iList)));
        assertTrue(iList.contains(Randomness.randomMember(iList)));
        assertTrue(iList.contains(Randomness.randomMember(iList)));

    }

}
