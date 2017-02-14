public class Feast extends Card
{
	public Feast()
	{
		name = "Feast";
		type = cardType.Action;
		cost = 4;
		value = 0;
		points = 0;
		actions = 0;
		draw = 0;
		buy =0;
	}
	public void play(Dominion game, Player player)
	{
		System.out.println("Playing " + name);
		boolean goodCard = false;
		while(goodCard == false)
		{
			Pile randomPile = game.getRandomKingdomCard();

			if(randomPile.getCard(0).cost <= 5)
			{
				player.discard.addCard(randomPile.drawCard());
				player.trashCard(player.getCardIndexbyName(player.hand, "Feast"));
			}
			else
			{
				continue;
			}
		}
	}
}

