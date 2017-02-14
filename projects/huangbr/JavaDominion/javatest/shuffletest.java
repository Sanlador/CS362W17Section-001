import java.util.*;

public class shuffletest
{
	public static void main(String[] agrs)
	{
		List<Integer> numberList = new ArrayList<Integer>();
		for(int i=0; i <= 20; i++)
		{
			numberList.add(i);
		}

		System.out.println(numberList);

		Collections.shuffle(numberList);

		System.out.println(numberList);

	}
}

