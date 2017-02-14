package dominion;
import java.util.*;

public class Card
{
	String name;
	String type = ""; //money, action, victory
	int price = 0;
	int value = 0;
	int victory;

	int index;

	public Card()
	{

	}
	public Card(int i)
	{
		//int index = i;
		switch(i)
		{

/*Victory Cards*/
			case 0:
				name = "Estate";
				type = "victory";
				price = 2;
				victory = 1;
			break;

			case 1:
				name = "Dutchy";
				type = "victory";
				price = 5;
				victory = 3;
			break;

			case 2:
				name = "Province";
				type = "victory";
				price = 8;
				victory = 6;
			break;

/*Treasure Cards*/
			case 3:
				name = "Copper";
				type = "money";
				price = 0;
				value = 1;
			break;

			case 4:
				name = "Silver";
				type = "money";
				price = 3;
				value = 2;
			break;

			case 5:
				name = "Gold";
				type = "money";
				price = 6;
				value = 3;			
			break;

/*Action Cards*/
			case 6:
				name = "Cellar";
				type = "action";
				price = 2;
				value = 0;
			break;

			case 7:
				name = "Chapel";
				type = "action";
				price = 2;
				value = 0;
			break;

			case 8:
				name = "Council Room";
				type = "action";
				price = 5;
				value = 0;
			break;

			case 9:
				name = "Festival";
				type = "action";
				price = 5;
				value = 0;
			break;

			case 10:
				name = "Village";
				type = "action";
				price = 3;
				value = 0;
			break;

			case 11:
				name = "Laboratory";
				type = "action";
				price = 5;
				value = 0;
			break;
			
			case 12:
				name = "Smithy";
				type = "action";
				price = 4;
				value = 0;
			break;

			case 13:
				name = "Witch";
				type = "action";
				price = 5;
				value = 0;
			break;

			case 14:
				name = "Market";
				type = "action";
				price = 5;
				value = 0;
			break;

			case 15:
				name = "Woodcutter";
				type = "action";
				price = 3;
				value = 0;
			break;

			case 16:
				name = "Curse";
				type = "victory";
				price = 0;
				value = 0;
			break;
		}
	}

	public void print()
	{
		System.out.println("Name: " + name + "\nBuy Value:" + price);
	}

	public void play(int card, Random rand, int hand, Player p, Game g)
	{

	};
}