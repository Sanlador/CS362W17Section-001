public class Great_Hall extends Card
{
	public Great_Hall()
	{
		name = "Great Hall";
		type = cardType.Victory;
		cost = 3;
		value = 0;
		points = 1;
		actions = 1;
		draw = 1;
		buy = 0;
	}
	public void play(Dominion game, Player player)
	{
		System.out.println("Playing " + name);
		player.actions += actions;
		for(int i=0; i < draw; i++)
		{
			player.hand.addCard(player.drawCard());
		}
		discard(game, player, name);
	}
}
