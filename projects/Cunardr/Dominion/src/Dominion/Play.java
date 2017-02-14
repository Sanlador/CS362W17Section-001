package Dominion;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Play 
{
	/*play maintains turn order and handling, communicates between the different
	 * classes, and acts as the main
	 * 
	 * Play initializes the game by calling the constructors at the 
	 * start of the game
	 * it then begins a loop which checks the status of the game (whether or not it is done)
	 * It then begins the next player's turn
	 */
	
	public static String state;
	
	public static void playGame(int rounds)
	{
		state = "Initializing";
		Board b = new Board();
		
		List<Player> p = new ArrayList();
		
		for (int i = 0; i < 2; i++)
		{
			p.add(new Player(i + 1));
		}
		
		p.get(0).opponents.add(p.get(1));
		p.get(1).opponents.add(p.get(0));
		
		boolean control = true;
		int turn = 0;
		
		if (rounds == 0)
		{
			while (control == true)
			{
				state = "run game";
				p.get(turn).turn(b, state);
				turn++;
				if (turn > 1)
					turn = 0;
				if (b.gameEnd() == true)
				{
					System.out.println("Game is over");
					control = false;
				}
			}
		}
		else
		{
			while (rounds > 0)
			{
				state = "Test turn run";
				p.get(turn).turn(b, state);
				turn++;
				if (turn > 1)
					turn = 0;
				rounds--;
			}
			
		}
		
		for (int i = 0; i < p.size(); i++)
		{
			state = "Count points player" + String.format("%d", i + 1);
			p.get(i).discardAll(p.get(i).hand);
			p.get(i).discardAll(p.get(i).deck);
			for (int j = 0; j < p.get(i).discard.size(); j++)
			{
				if (p.get(i).discard.get(j).getType() == "Victory" || p.get(i).discard.get(j).getName() == "Great Hall")
					p.get(i).vPoints += p.get(i).discard.get(j).getPoints();
				else if (p.get(i).discard.get(j).getName() == "Gardens")
					p.get(i).vPoints += p.get(i).discard.size()/10;
				else if (p.get(i).discard.get(j).getType() == "Curse")
					p.get(i).vPoints -= 1;
			}
		}
		
		if (p.get(0).vPoints >= p.get(1).vPoints)
			System.out.println("Player 1 wins with " + String.format("%d", p.get(0).vPoints) + " to " + String.format("%d", p.get(1).vPoints));
		else 
			System.out.println("Player 2 wins with " + String.format("%d", p.get(1).vPoints) + " to " + String.format("%d", p.get(0).vPoints));
		state = "End";
	}
	
		
	
	
}
