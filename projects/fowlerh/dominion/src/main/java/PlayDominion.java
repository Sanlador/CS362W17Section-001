import java.util.List;
import java.util.ArrayList;

public class PlayDominion {
	
	
		
	public  static void main(String args[])
	{
			// First 10
	       List<Card> cards = GameBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.feast, Card.gardens, Card.great_hall, Card.mine);
	       
	       int seed = Randomness.nextRandomInt(1000);
	       Randomness.reset(seed);
	       
	       GameBoard gb = new GameBoard(cards, 2);
	       
	       gb.play();
	       
	       
	}
		
}
