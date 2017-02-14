package dominion;
import java.util.*;

public class Game
{
	Player[] player;
	int[] curse;
	int[] bcc = {0, 0, 0, 30, 40, 60, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //board card count
	//0-2 = victory, 3-5 = treasure, 6-15 = kingdom
        boolean winnokc = false;
	public Game()
	{

	}

	public void initilize_game(int num_players, int num_kingdom_cards)
	{
		player = new Player[num_players];
                if(num_kingdom_cards == 0)
                {
                    winnokc = true;
                }
                
		set_cards(6, 15, num_kingdom_cards);
		if (num_players == 2)
		{	
			set_cards(0, 2, 8);
			//curse = set_cards(10, 1);
		}
		else
		{
			set_cards(0, 2, 12);
			//curse = set_cards(30, 1);
		}
		for(int i = 0; i < num_players; i++)
		{
			player[i] = new Player("Player"+i);
		}
	}

	public void set_cards(int imin, int imax, int val)
	{
		for(int i = imin; i != imax + 1; i++)
		{
			bcc[i] = val;
		}
	}

	public int randInt(Random rnd, int max)
	{
		int random = rnd.nextInt(max + 1);
		return random;
	}

	public int set_turn(int prev_player)
	{
		if (prev_player + 1 < player.length)
		{
			return prev_player + 1;
		}
		else
			return 0;
	}

	public void get_winner()
	{
		int vp;
		int winner = 0;
		vp = player[0].vp;
		for (int i = 1; i < player.length; i++)
		{
			if(player[i].vp > vp)
			{
				vp = player[i].vp;
				winner = i;
			}
			else if(player[i].vp == vp)
			{
				vp = player[i].vp;
				winner = -1;
			}
		}

		if (winner < 0)
		{
			System.out.println("Tie!");
		}
		else
		{
			System.out.println(player[winner].name + " Wins!");
		}

            for (Player player1 : player) {
                System.out.println(player1.name + " VP: " + player1.vp);
            }
	}

	public boolean check_win()
	{
		if(bcc[2]== 0)
		{
			System.out.println("Game Ended. Providence Supply Depleted.");
			get_winner();
			return true;
		}
                if (!winnokc)
                {
                    int empty_count = 0;
                    for(int i = 6; i < 15; i++)
                    {
                            if (bcc[i] == 0)
                            {
                                    empty_count++;
                            }
                    }

                    if (empty_count >= 3)
                    {
                            System.out.println("Game Ended. Three Kingdom Supply Piles Depleted.");
                            get_winner();
                            return true;
                    }
                }
		return false;
	}

	public boolean can_buy(int t)
	{
		return player[t].buy_turns != 0 && player[t].played_value >= 3;
	}

	public boolean can_play(int t)
	{
		return player[t].action_turns != 0 && player[t].ac_hand > 0;
	}

	public boolean player_play(int t, int hand, Random rand, Game g)
	{
		if(player[t].play_action(hand, rand, player[t], g))
                {
                    return true;
                }
            return false;
	}

	public boolean player_buy(int t, int card)
	{
		Card temp_card;
		if (bcc[card]> 0)
		{
			//0-2 = victory, 3-5 = treasure, 6-15 = kingdom
			if (card <= 2)
			{
				temp_card = new Victory(card);
			}
			else if (card >= 3 && card <= 5)
			{
				temp_card = new Treasure(card);
			}
			else 
			{
				temp_card = new Adventure(card);
			}

			if(player[t].played_value >= temp_card.price)
			{
				player[t].buy(temp_card);
				player[t].played_value = player[t].played_value - temp_card.price;
				bcc[card] = bcc[card]- 1;
				System.out.println("\tBuys " + temp_card.name);	
				return true;
			}
		}
		return false;
	}

}

