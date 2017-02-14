package org.cs362.dominion;
import java.util.List;
import java.util.Random;
import java.security.SecureRandom;

public final class Randomness {

    public static final long SEED = 0;

    public static Random random = new SecureRandom();

    public static void reset(){
	//this is bad
	//random = new Random(newSeed);
	//this is good. Ignore the seed altogether.
	random = new SecureRandom();
    }

    public static int totalCallsToRandom = 0;

    public static int nextRandomInt(int i) {
	totalCallsToRandom++;
	return random.nextInt(i);
    }
}
