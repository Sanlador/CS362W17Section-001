public class Smithy extends Card
{
	public Smithy()
	{
		name = "Smithy";
		type = cardType.Action;
		cost = 4;
		value = 0;
		points = 0;
		actions = 0;
		draw = 3;
		buy = 0;
	}
	public void play(Dominion game, Player player)
	{
		System.out.println("Playing " + name);
		for(int i=0; i < draw; i++)
		{
			player.hand.addCard(player.drawCard());
		}
		discard(game, player, name);
	}
}
