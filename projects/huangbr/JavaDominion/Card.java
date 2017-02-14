import java.util.*;

public abstract class Card
{
	public enum cardType {Treasure, Victory, Action, Attack, Curse};

	protected String name;
	protected cardType type;
	protected int cost;
	protected int value;
	protected int points;
	protected int actions;
	protected int draw;
	protected int buy;
	
	public abstract void play(Dominion game, Player player);
	public void print()
	{
		System.out.println(name);
	}

	public void discard(Dominion game, Player player, String name)
	{
		for(int i=0; i < player.hand.getSize(); i++)
		{
			if(player.hand.cards.get(i).name == name)
			{
				player.discardCard(player.hand, i);
				break;
			}
		}
	}
}

