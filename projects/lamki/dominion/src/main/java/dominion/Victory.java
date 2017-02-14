package dominion;

public class Victory extends Card
{
	public Victory(int name)
	{
		super(name);
	}

        @Override
	public void print()
	{
		System.out.println("Victory Card: ");
		super.print();
	}
}

