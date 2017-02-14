public class Cutpurse extends Card
{
	public Cutpurse()
	{
		name = "Cutpurse";
		type = cardType.Attack;
		cost = 4;
		value = 0; 
		points = 0;
		actions = 0; 
		draw = 0; 
		buy = 0; 
	}
	public void play(Dominion game, Player player)
	{
		System.out.println("Playing " + name);
		player.money += 2;
		for(int i=0; i < game.players.length; i++)
		{
			if(game.players[i].playerID == player.playerID)
			{
				continue;
			}
			else
			{
				for(int j=0; j < game.players[i].hand.getSize(); j++)
				{
					if(game.players[i].hand.cards.get(j).name == "Copper")
					{
						game.players[i].hand.cards.get(j).discard(game, game.players[i], "Copper");
					}
					else
					{
						game.players[i].hand.revealAll();
					}
				}
			}
		}
		discard(game, player, name);
	}
}

						



