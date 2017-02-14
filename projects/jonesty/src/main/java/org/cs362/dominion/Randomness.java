package dominion;


import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;




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
  public static <T> T randomMember(List<T> list) {
    if (list == null || list.isEmpty())
      throw new IllegalArgumentException("Expected non-empty list");
    return list.get(nextRandomInt(list.size()));
  }
  //public static Card retrandomcard(GameState gamestate){


	  //List<Card> valuesList = new ArrayList<Card>(gamestate.getGamestate().values());
	  //int randomIndex = new Random().nextInt(valuesList.size());
	 // Card randomValue = valuesList.get(randomIndex);
	  
	  //return randomValue;
  //}
	public  static void main(String args[]){
		
		Randomness.reset(10);	   

		for(int i=0; i<10;i++){
			System.out.println("value=: "+ Randomness.nextRandomInt(10));
		}
		System.exit(0);   
	}


}
