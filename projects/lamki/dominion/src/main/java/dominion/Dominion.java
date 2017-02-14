package dominion;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Dominion
{
	public static void main(String[] args)
	{
		System.out.println("\n\t- Dominion Version 1.0 Kin-Ho Lam CS362 -");	
		int play_num;
                int kc = 10;
                switch (args.length) {
                case 2:
                    play_num = Integer.valueOf(args[0]);
                    kc = Integer.valueOf(args[1]);
                    System.out.println("\n\tInitiating Game with "+play_num+" Players and " +kc+ " Kingdom Cards");
                    break;
                case 1:
                    play_num = Integer.valueOf(args[0]);
                    System.out.println("\n\tInitiating Game with "+play_num+" Players and 10 Kingdom Cards");
                    break;
                default:
                    play_num = 2;
                    System.out.println("\n\tUsage: java -jar lamki_dom.jar <number of players> <number of kingdom cards>");
                    System.out.println("\n\tInitiating Game with 2 Players, 10 Kingdom Cards");
                    break;
            }

		Random rand = ThreadLocalRandom.current(); //seed random
		Game game = new Game(); //create game object
		game.initilize_game(play_num,kc); //int players, int number of kingdom cards
		
		int t = game.randInt(rand, play_num-1); //randomly select which player goes first
	
		while(!(game.check_win())) //mainloop
		{
			game.player[t].played_value = 0; //set play value
			game.player[t].ac_hand = 0;

			game.player[t].shuffle(rand); //Before every turn, player shuffles deck
			game.player[t].draw(5); //player draws 5 cards

			game.player[t].action_turns = 1; //set action turns
			game.player[t].buy_turns = 1; //set buy turns
			System.out.println(game.player[t].name + " (VP."+ game.player[t].vp + ")");
			//game.print_bcc();

			game.player[t].count_ac();
			if (game.player[t].ac_hand > 0)
			{
				while(game.can_play(t))
				{
					game.player[t].count_ac();
					if (game.player[t].ac_hand > 0)
					{
						int play_c = game.randInt(rand, game.player[t].hand.length - 1);
						game.player_play(t, play_c, rand, game);
					}
					else
					{
						break;
					}
					//game.player[t].shuffle(rand);
				}
			}
			else
			{
				System.out.println("\tNo action cards drawn");	
                                //game.player[t].debug_print();
			}
			//start buy phase
			game.player[t].play_all_treasure(); //Player plays all treasure

			if (game.player[t].played_value > 0)
			{
				while(game.can_buy(t)) //so long as the player can buy, keep trying to buy
				{
				//	game.player[t].debug_print();
					if (game.player[t].played_value > 0)
					{
						int card = game.randInt(rand, 15); //Randomly choose a card and try to buy it
						//int card = game.randInt(rand, 6);
						game.player_buy(t,card);
					//	game.player[t].debug_print();
					}
					else
					{
						break; //if the player cannot buy anything then break
					}
				}
			}
			else
			{
				System.out.println("\tInsufficient funds");	
			}
			game.player[t].putback(); //put the player's hand back into their deck
			t = game.set_turn(t); //Increment turn for next player
		}
	}
}