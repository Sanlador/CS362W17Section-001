package com.omalleya.app;


import java.util.List;
import java.util.Random;




public final class Randomness {

  private Randomness(){
    //no instances
  }
  public static final long SEED = 0;

  /**
   * The random number used any testtime a random choice is made. 
   */
 public static Random random = new Random();

  public static void reset(long newSeed){
    random = new Random(newSeed);
  }

  public static int totalCallsToRandom = 0;

 
  /**
   * Uniformly random int from [0, i)
   */
  public static int nextRandomInt(int i) {
    totalCallsToRandom++;
    return random.nextInt(i);
  }


}
