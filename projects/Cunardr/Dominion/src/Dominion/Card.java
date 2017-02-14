package Dominion;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Card 
{
	
	
	private String name, type;
	private List<String> preReq;
	private int cost = 0;
	private int points = 0;
	private int value = 0;
	
	public String getName()
	{
		return name;
	}
	
	

	/*kingdom cards will have the fields:
	 * - Prereq; Cards that must be in either the player's hand or play
	 * 	* canPlay will be called at the start of a player's action phase to test the prereq
	 * - Cost
	 * - Effect list; a list of strings which are fed to the effect function, which calls the appropriate functions 
	*/
	
	
	
	
	public Card(String n)
	{
		points = 0;
		cost = 0;
		//Victory
		if (n == "Estate")
		{
			name = "Estate";
			type = "Victory";
			cost = 2;
			points= 1;
		}
		else if (n == "Duchy")
		{
			name = "Duchy";
			type = "Victory";
			cost = 5;
			points = 3;
		}
		else if (n == "Province")
		{
			name = "Province";
			type = "Victory";
			cost = 8;
			points = 6;
		}
		//Treasure
		else if (n == "Copper")
		{
			name = "Copper";
			type = "Treasure";
			cost = 0;
			value = 1;
		}
		else if (n == "Silver")
		{
			name = "Silver";
			type = "Treasure";
			cost = 3;
			value = 2;
		}
		else if (n == "Gold")
		{
			name = "Gold";
			type = "Treasure";
			cost = 6;
			value = 3;
		}
		else if (n == "Gardens")
		{
			name = n;
			type = "KingdomS";
			cost = 4;
		}
		else if (n == "Great Hall")
		{
			name = n;
			type = "Kingdom";
			cost = 3;
			points = 1;
		}
		else if (n == "Curse")
		{
			name = n;
			type = n;
			points = -1;
		}
	}
	
	public Card(String n, int c)
	{
		name = n;
		type = "Kingdom";
		cost = c;
	}
	
	public String getType()
	{
		return type;
	}
	
	public int getCost()
	{
		return cost;
	}
		
		
	
	/*
	 * The types of cards:
	 * - Treasure
	 * - Victory
	 * - Kingdom 
	 * 	Abilities include:
	 * 	* Draw x (+x card)
	 * 	* Can play x additional action cards (+x Actions)
	 * 	* x additional purchases (+x buy)
	 * 	* Gain x coins (+x coin)
	 *  * Take a card from the supply and place in discard (Gain)
	 *  * Take a card from the supply and place in hand (Gain into hand)
	 *  * Show a card, and put it back where it came from (Reveal)
	 *  * Place card into holding area until a condition is met (Set Aside)
	 *  * Put into discard pile (discard)
	 *  * Put a card into trash which removes it from the game (Trash)
	 * - Curse
	 * Players start with an initial draw pile of 3 estates and 7 coppers
	 * Players draw a hand of five to start (handled in player class)
	 * 
	 * Necessary methods include:
	 * - checkFor; checks if a specific card is in play
	 * - Target: draws, discards or trashes a specific card
	 */
	
	//searches a list for a specific card
	public static boolean search(Card s, List<Card> c)
	{
		boolean b = false;
		for (int i = 0; i < c.size(); i++)
		{
			if (c.get(i).getName() == s.getName())
				b = true;
		}
		return b;
	}
	
	//moves the 'top' card from one pile or hand to another n times
	public static void draw(List<Card> from, List<Card> to, int n)
	{
		for (int i = 0; i < n; i++)
		{
			//account for lack of cards
			if (from.isEmpty() == false)
			{
				to.add(from.get(0));
				from.remove(0);
			}
		}
	}
	
	//moves a specific card from one pile or hand to another
	public static void move (Card c, List<Card> from, List<Card> to)
	{
		boolean control = false;
		int i = 0;
		//confirm card exists
		if (search(c, from) == true)
		{
			while (control == false)
			{
				if (from.get(i).getName() == c.getName())
				{
					control = true;
					from.remove(i);
				}
				else
					i++;
			}
			to.add(c);
		}
		
	}
	
	//adds n actions to the player's turn
	public void action(Player p, int n)
	{
		p.actions += n;
	}
	
	public void buy(Player p, int n)
	{
		p.purchases += n;
	}
	
	public int getPoints()
	{
		return points;
	}
	
}
