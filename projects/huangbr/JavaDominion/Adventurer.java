public class Adventurer extends Card
{
	public Adventurer()
	{
		name = "Adventurer";
		type = cardType.Action;
		cost = 6;
		value = 0;
		actions = 0;
		draw = 0;
		buy = 0;
	}
	public void play(Dominion game, Player player)
	{
		System.out.println("Playing " + name);
		int numTreasures =0;
		Pile tempPile = new Pile();
		while(numTreasures < 2)
		{
			player.deck.revealCard(0);
			if(player.deck.getCard(0).type == cardType.Treasure)
			{
				numTreasures++;
				player.hand.addCard(player.deck.drawCard());
			}
			else
			{
				player.discardCard(player.deck, 0);
			}
		}
		discard(game, player, name);
	}
}

		
			
