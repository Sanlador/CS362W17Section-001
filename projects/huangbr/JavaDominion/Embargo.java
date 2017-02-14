import java.util.*;

public class Embargo extends Card
{
	public Embargo()
	{
		name = "Embargo";
		type = cardType.Action;
		cost = 2;
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
		game.getRandomKingdomCard().embargos++;

		player.trashCard(player.getCardIndexbyName(player.hand, "Embargo"));
	}
}







