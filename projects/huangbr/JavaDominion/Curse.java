public class Curse extends Card
{
	public Curse()
	{
		name = "Curse";
		type = cardType.Curse;
		cost = 0;
		value = 0;
		points = -1;
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

