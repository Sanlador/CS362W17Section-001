import java.util.List;
import java.util.Random;

public final class Randomness
{
	private Randomness()
	{
		
	}
	
	public static final long SEED = 0;
	
	public static Random random = new Random();
	
	public static void reset(long newSeed)
	{
		random = new Random(newSeed);
	}
	
	public  static void main(String args[])
	{
		Randomness.reset(10);   
		for(int i=0; i<10;i++)
		{
			System.out.println("value=: "+ Randomness.nextRandomInt(10));
		} 
		System.exit(0);  
	}

	public static int totalCallsToRandom = 0;
	
	public static int nextRandomInt(int i)
	{
		totalCallsToRandom++;
		return random.nextInt(i);
	}
	
	public static <T> T randomMember(List<T> list)
	{
		if (list == null || list.isEmpty())
		{
			throw new IllegalArgumentException("Expected non-empty list");
		}
		return list.get(nextRandomInt(list.size()));
	}
	
}