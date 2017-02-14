public class Mine extends Card
{
	public Mine()
	{
		name = "Mine";
		type = cardType.Action;
		cost = 0;
		value = 0;
		actions = 0;
		draw = 0;
		buy = 0;
	}
	public void play(Dominion game, Player player)
	{
		System.out.println("Playing " + name);
		int copperInHand = player.getCardIndexbyName(player.hand, "Copper");
		if(copperInHand == -1)
		{
			int silverInHand = player.getCardIndexbyName(player.hand, "Silver");
			if(silverInHand == -1)
			{
				discard(game, player, name);
				return;
			}
			else
			{
				if(game.basicCards.get(2).getSize() == 0)
				{
					discard(game, player, name);
					return;
				}
				player.hand.addCard(game.basicCards.get(2).drawCard());
				discard(game, player, name);
				return;
			}
		}
		else
		{
			if(game.basicCards.get(1).getSize() == 0)
			{
				discard(game, player, name);
				return;
			}
			player.hand.addCard(game.basicCards.get(1).drawCard());
			discard(game, player, name);
			return;
		}
	}
}

						



