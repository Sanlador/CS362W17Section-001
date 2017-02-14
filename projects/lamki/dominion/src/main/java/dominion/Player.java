package dominion;

import java.util.*;
import java.util.ArrayList;

public class Player
{
	String name; // default = Player X
	int  action_turns; //default = 0
	int  buy_turns; //default = 0
	int  vp = 0; //default = 0
	int played_value = 0; //total value of cards played
	int ac_hand = 0; //number of action cards in hand
	Card[] deck = {new Victory(0), new Victory(0), new Victory(0), new Victory(3), new Victory(3), new Victory(3), new Victory(3), new Victory(3), new Victory(3), new Victory(3)};
	Card[] hand = {};
	Card[] limbo = {}; //Where action cards go after they have been played

	public Player(String set_name)
	{
		name = set_name;
		action_turns = 0;
		buy_turns = 0;
		vp = 3;
	}

	public void shuffle(Random rnd)
	{
		for (int i = deck.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			Card a = deck[index];
			deck[index] = deck[i];
			deck[i] = a;
		}
	}

	public Card[] remove(Card[] x, int i)
	{
		ArrayList<Card> temp_deck = new ArrayList<>(Arrays.asList(x));
		temp_deck.remove(i);
		x = temp_deck.toArray(new Card[0]);
		return x;
	}

	static Card[] append(Card[] arr, Card element) 
	{ //TODO: Create a give function to apply card attributes as they go into a player's deck (Victory, curse, money) 
	    final int N = arr.length;
	    arr = Arrays.copyOf(arr, N + 1);
	    arr[N] = element;
	    return arr;
	}
 
	public void draw(int n)
	{
		if(n <= deck.length)
		{
			int last = deck.length-1;
			for (int i = last; i != last - n; i--)
			{
				hand = append(hand, deck[i]);
				deck = remove(deck, i);
			}
		}
	}

	public void count_ac()
	{
		ac_hand = 0;
		for(int i = 0; i < hand.length - 1; i++)
		{
			if ("action".equals(hand[i].type))
			{
				ac_hand = ac_hand + 1;
			}
		}
	}

	public void discard(int n)
	{
		hand = remove(hand, n);
	}

	public void putback() 
	{
		int last = hand.length-1;
		for (int i = last; i != -1; i--)
		{
			deck = append(deck, hand[i]);
			hand = remove(hand, i);
		}
		last = limbo.length-1;
		for (int i = last; i != -1; i--)
		{
			deck = append(deck, limbo[i]);
			limbo = remove(limbo, i);
		}
	}

	public void play_all_treasure()
	{
            for (Card hand1 : hand) {
                played_value += hand1.value;
            }
	}

	public int buy(Card x)
	{
		buy_turns = buy_turns - 1;
		deck = append(deck, x);
		if ("victory".equals(x.type))
		{
			vp = vp + x.victory;
			//debug_print();
		}
		return 0;
	}

	public boolean play_action(int i, Random rand, Player p, Game g)
	{
		if ("action".equals(hand[i].type))
		{
			action_turns = action_turns - 1;
			Card temp = hand[i];

			System.out.println("\tPlays " + temp.name);
			to_limbo(i); 

			ac_hand = ac_hand - 1;	
			temp.play(temp.index, rand, i, p, g);
                        return true;
		}
            return false;
	}

	public void to_limbo(int i)
	{
            limbo = append(limbo, hand[i]); 
            hand = remove(hand, i);
	}

	public void to_deck(int i)
	{
            deck = append(deck, hand[i]); 
            hand = remove(hand, i);
	}
/*
	public void debug_print()
	{
		System.out.println("\nName: " + name + "\tVP: " + vp + "\tAC: "+ac_hand+ "\tPlayed Value: "+ played_value+"\tBuy Turns: " + buy_turns + "\tPlay Turns: " + action_turns);
		
		System.out.print("\tD" + deck.length + ": ");
            for (Card deck1 : deck) {
                System.out.print("'" + deck1.name + "' ");
            }
		System.out.print("\n\tH" + hand.length + ": ");
            for (Card hand1 : hand) {
                System.out.print("'" + hand1.name + "' ");
            }
		System.out.print("\n");
		
	}
*/
}
