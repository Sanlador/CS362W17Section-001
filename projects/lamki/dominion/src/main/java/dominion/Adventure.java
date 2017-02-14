package dominion;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Adventure extends Card
{
	public Adventure(int name)
	{
		super(name);
		super.index = name;
	}
/*
        @Override
	public void print()
	{
		System.out.println("Adventure Card: ");
		super.print();
	}
*/
        @Override
	public void play(int card, Random rand1, int hand, Player p, Game g)
	{
		Random rand = ThreadLocalRandom.current();
		int rnd_c;
		switch(card)
		{
			case 6: //name = "Cellar";
				if (p.hand.length < 0)
				{
					rnd_c = rand.nextInt(p.hand.length - 1);
					System.out.println("\t\t+1 Action");
					System.out.println("\t\tDiscards and draws " +rnd_c+" cards");
					p.action_turns+=1;

					for(int i = 0; i < rnd_c; i++)
					{
						p.to_deck(rand.nextInt(p.hand.length - 1));
						p.shuffle(rand);
						p.draw(1);
					}
				}
			break;

			case 7: //name = "Chapel";
				if (p.hand.length < 0)
				{
					rnd_c = rand.nextInt(p.hand.length - 1);
					System.out.println("\t\tDiscards " +rnd_c+" cards");
					//p.debug_print();
					for(int i = 0; i < rnd_c; i++)
					{
						//System.out.println(rnd_c);
						p.discard(rand.nextInt(p.hand.length - 1));
					}
				}
			break;

			case 8: //name = "Council Room";
				System.out.println("\t\t+1 Buy");
				System.out.println("\t\tDraws 4 cards");
				
				p.buy_turns++;
				p.draw(4);
			break;

			case 9: //name = "Festival";
				System.out.println("\t\t+2 Actions, +1 Buy");
				
				p.action_turns+=2;
				p.buy_turns++;
			break;

			case 10: //name = "Village";
				System.out.println("\t\t+2 Actions, +1 Buy");
				System.out.println("\t\tDraws 1 card");
				p.action_turns+=2;
				p.buy_turns++;
				p.draw(1);
				p.shuffle(rand);
			break;

			case 11: //name = "Laboratory";
				System.out.println("\t\t+1 Action");
				System.out.println("\t\tDraws 2 cards");
				
				p.action_turns++;
				p.draw(2);
			break;
			
			case 12: //name = "Smithy";
				System.out.println("\t\tDraws 3 cards");
				p.draw(3);
			break;

			case 13: //name = "Witch";
				System.out.println("\t\tDraws 2 cards");
				p.draw(2);
                                for (Player player : g.player) {
                                    if (player.name == null ? p.name != null : !player.name.equals(p.name)) {
                                        player.deck = Player.append(player.deck, new Victory(16)); //give every other player a curse
                                        System.out.println("\t\t" + player.name + " gains a curse");
                                    }
                                }
			break;

			case 14: //name = "Market";
				System.out.println("\t\t+1 Action, +1 Buy");
				System.out.println("\t\tDraws 1 card");
				p.action_turns++;
				p.buy_turns++;
				p.draw(1);
			break;

			case 15: //name = "Woodcutter";
				System.out.println("\t\t+1 Buy");
				p.buy_turns++;
			break;			
		}
	}
}

