public class Baron extends Card
{
	public Baron()
	{
		name = "Baron";
		type = cardType.Action;
		cost = 4;
		value = 0;
		points = 0; 
		actions =0;
		draw = 0;
		buy = 1;
	}
	public void play(Dominion game, Player player)
	{
		System.out.println("Playing " + name);
		int estateIndex = player.getCardIndexbyName(player.hand, "Estate");
		if(estateIndex == -1)
		{
			if(game.basicCards.get(3).size == 0)
			{
				discard(game, player, name);
				return;
			}
			Card newEstate = game.basicCards.get(3).drawCard();
			player.discard.addCard(newEstate);
		}
		else
		{
			player.discardCard(player.hand, estateIndex);
			player.money += 4;
		}
		discard(game, player, name);
	}
}

