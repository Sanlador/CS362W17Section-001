public class Ambassador extends Card
{
	public Ambassador()
	{
		name = "Ambassador";
		type = cardType.Attack;
		cost = 3;
		value = 0;
		points = 0;
		actions = 0;
		draw = 0;
		buy = 0;
	}
	public void play(Dominion game, Player player)
	{
		System.out.println("Playing " + name);
		int cardIndex = player.getRandomCardIndex(player.hand);
		player.hand.revealCard(cardIndex);
		String name = player.hand.getName(cardIndex);

		int counter = 0;
		for(int i=0; i < player.hand.getSize(); i++)
		{
			if(counter == 2)
			{
				break;
			}
			else if(player.hand.cards.get(i).name == player.hand.getCard(cardIndex).name)
			{
				int index = game.getKingdomCardIndex(name);
				game.kingdomCards.get(index).addCard(player.hand.getCard(i));
				player.hand.removeCard(i);
				counter++;
			}
		}
		discard(game, player, name);
	}
}










			

