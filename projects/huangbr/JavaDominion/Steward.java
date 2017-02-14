import java.util.*;

public class Steward extends Card
{
	public Steward()
	{
		name = "Steward";
		type = cardType.Action;
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
		Random rand = new Random();
		int n = rand.nextInt(3);
		if(n == 0)
		{
			for(int i=0; i < 2; i++)
			{
				player.hand.addCard(player.drawCard());
			}
		}
		else if(n == 1)
		{
			player.money += 2;
		}
		else if(n == 2)
		{
			int m = rand.nextInt(player.hand.getSize());
			player.trashCard(m);
			m = rand.nextInt(player.hand.getSize());
			player.trashCard(m);
		}
		discard(game, player, name);
	}
}

			


