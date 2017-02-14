public class Estate extends Card
{
	public Estate()
	{
		name = "Estate";
		type = cardType.Victory;
		cost = 2;
		value = 0;
		points = 1;
		actions = 0;
		draw = 0;
		buy = 0;
	}
	
	public void play(Dominion game, Player player)
	{
		return;
	}
}

