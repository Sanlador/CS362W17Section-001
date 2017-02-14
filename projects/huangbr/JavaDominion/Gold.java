public class Gold extends Card
{
	public Gold()
	{
		name = "Gold";
		type = cardType.Treasure;
		cost = 6;
		value = 3;
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

