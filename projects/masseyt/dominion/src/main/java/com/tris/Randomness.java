package com.tris;

import java.util.List;
import java.util.Random;

/**
 * Created by Tristan Massey on 2/9/2017.
 */
public class Randomness {
    Randomness(){

    }
    public static long SEED = 0;
    public static Random random = new Random();
    public static void reset(long newSeed){
        random = new Random(newSeed);
    }

    public static int totalCallsToRandom = 0;

    public static int nextRandomInt(int max){
        totalCallsToRandom++;
        return random.nextInt(max);
    }
    public static <T> T randomMember(List<T> list){
        if (list == null || list.isEmpty())
            throw new IllegalArgumentException("Expected non-empty list");
        return list.get(nextRandomInt(list.size()));
    }
}
