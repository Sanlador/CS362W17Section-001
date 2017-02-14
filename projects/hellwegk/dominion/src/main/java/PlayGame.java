import java.util.List;


public class PlayGame {
	
	public static void main(String args[])
	{
		List<Card> cards = DominionBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.smithy, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.seahag, Card.gardens, Card.great_hall);
		int seed = Randomness.nextRandomInt(1000);
		Randomness.reset(seed);
		
		DominionBoard game = new DominionBoard(cards, 2);
		
		game.play();
	}
	
}
