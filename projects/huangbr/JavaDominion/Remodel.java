public class Remodel extends Card
{
	public Remodel()
	{
		name = "Remodel";
		type = cardType.Action;
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
		int randomCard = player.getRandomCardIndex(player.hand);
		int cardCost = player.hand.getCard(randomCard).cost;
		
		boolean goodCard = false;
		while(goodCard == false)
		{
			Pile temp = game.getRandomKingdomCard();
			if(temp.getCard(0).cost <= cardCost +2)
			{
				player.hand.addCard(temp.drawCard());
				goodCard = true;
			}
		}
		player.trashCard(randomCard);
		discard(game, player, name);
	}
}






