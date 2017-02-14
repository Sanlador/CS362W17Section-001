public class Silver extends Card
{
	public Silver()
	{
		name = "Silver";
		type = cardType.Treasure;
		cost = 3;
		value = 2;
		points = 0;
		actions = 0;
		draw = 0;
		buy = 0;
	}
	public void play(Dominion game, Player player)
	{
		return;
	}
}

