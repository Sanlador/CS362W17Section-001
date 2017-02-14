public class Garden extends Card
{
	public Garden()
	{
		name = "Garden";
		type = cardType.Victory;
		cost = 4;
		value = 0;
		points = 0;
		actions = 0;
		draw = 0;
		buy = 0;
	}
	public void play(Dominion game, Player player)
	{
		discard(game, player, name);
		return;
	}
}

