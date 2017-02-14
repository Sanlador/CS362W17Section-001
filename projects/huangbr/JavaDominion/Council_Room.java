public class Council_Room extends Card
{
	public Council_Room()
	{
		name = "Council Room";
		type = cardType.Action;
		cost = 5;
		value = 0;
		points = 0;
		actions = 0;
		draw = 4;
		buy = 1;
	}
	public void play(Dominion game, Player player)
	{
		System.out.println("Playing " + name);
		player.buy += buy;
		for(int i=0; i < draw; i++)
		{
			player.hand.addCard(player.drawCard());
		}
		for(int j=0; j < game.players.length; j++)
		{
			if(game.players[j].playerID == player.playerID)
			{
				continue;
			}
			else
			{
				game.players[j].drawCard();
			}
		}
		discard(game, player, name);
	}
}
