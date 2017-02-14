import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnitTest {

	@Test
	public void playGameTest()
	{
		PlayGame.main(null);
	}
	
	@Test
	public void getVictoryTest()
	{
		assertEquals(Card.province.getVictory(), 6);
	}
	
	@Test
	public void getCostTest()
	{
		assertEquals(Card.duchy.getCost(), 5);
	}
	
	@Test
	public void getCoinsTest()
	{
		assertEquals(Card.gold.getCoins(), 3);
	}
	
	@Test
	public void getEmbargoTest()
	{
		assertEquals(Card.seahag.getEmbargo(), 0);
	}
	
	@Test
	public void getTypesTest()
	{
		List<String> typeList = new ArrayList<String>();
		typeList.add("action");
		typeList.add("victory");
		assertEquals(Card.great_hall.getTypes(), typeList);
	}
	
	@Test
	public void playTest()
	{
		List<Card> cards = DominionBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.smithy, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.seahag, Card.gardens, Card.great_hall);
		DominionBoard board = new DominionBoard(cards, 2);
		assertEquals(board.play(), 1);
	}
	
	@Test
	public void kingdomCardsTest()
	{
		List<Card> cards = DominionBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.smithy, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.seahag, Card.gardens, Card.great_hall);
		assertTrue(cards.contains(Card.smithy) && cards.size() == 10);
	}
	
	@Test
	public void DominionBoardTest ()
	{
		List<Card> cards = DominionBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.smithy, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.seahag, Card.gardens, Card.great_hall);
		DominionBoard board = new DominionBoard(cards, 2);
		assertEquals(board.getPlayers().size(), 2);
	}
	
	@Test
	public void actionCardsTest()
	{
		List<Card> cards = DominionBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.smithy, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.seahag, Card.mine, Card.great_hall);
		DominionBoard board = new DominionBoard(cards, 2);
		Player p = new Player("Player1", 0);
		int hand;
		int coins;
		int actions;
		
		p.addActions(10);
		assertEquals(p.actionsLeft(), true);
		
		//makes sure deck is empty or you end up with more cards in your hand.
		p.gainCardToHand(Card.adventurer);
		hand = p.getHand().size();
		p.playCard(Randomness.randomMember(p.actionCardIndices()));
		assertTrue(p.getDeck().size() == 0  || p.getHand().size() > hand);
		
		//make sure you don't gain any cards and your hand is either the same size or smaller than before.
		p.gainCardToHand(Card.ambassador);
		p.gainCardToHand(Card.copper);
		hand = p.getHand().size();
		p.playCard(Randomness.randomMember(p.actionCardIndices()));
		assertTrue(p.getHand().size() <= hand);
				
		//makes sure you gain 4 coins if you have an estate and discard it
		p.gainCardToHand(Card.baron);
		p.gainCardToHand(Card.estate);
		coins = p.getCoins();
		p.playCard(Randomness.randomMember(p.actionCardIndices()));
		assertTrue(p.getCoins() == coins + 4);
		
		//makes sure you draw 3 cards
		p.gainCardToHand(Card.council_room);
		hand = p.getHand().size();
		p.playCard(Randomness.randomMember(p.actionCardIndices()));
		assertTrue(p.getHand().size() == hand + 3);
		
		//makes sure you gain 2 coins
		p.gainCardToHand(Card.cutpurse);
		coins = p.getCoins();
		p.playCard(Randomness.randomMember(p.actionCardIndices()));
		assertTrue(p.getCoins() == coins + 2);
		
		//makes sure you gain 2 coins and have one less card than before
		p.gainCardToHand(Card.embargo);
		coins = p.getCoins();
		hand = p.getHand().size();
		p.playCard(Randomness.randomMember(p.actionCardIndices()));
		assertTrue(p.getCoins() == coins + 2 && p.getHand().size() < hand);
		
		//make sure you gain 1 card and 1 action
		p.gainCardToHand(Card.great_hall);
		hand = p.getHand().size();
		actions = p.getActions();
		p.playCard(Randomness.randomMember(p.actionCardIndices()));
		assertTrue(p.getHand().size() == hand && p.getActions() == actions);

		//makes sure you gain coins(or have same amount if you don't trash one)
		p.gainCardToHand(Card.mine);
		coins = p.getCoins();
		p.playCard(Randomness.randomMember(p.actionCardIndices()));
		assertTrue(p.getCoins() >= coins);
	}
	
	@Test
	public void gameOverTest()
	{
		List<Card> cards = DominionBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.smithy, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.seahag, Card.gardens, Card.great_hall);
		DominionBoard board = new DominionBoard(cards, 2);
		for (int i = 0; i < 8; i++)
		{
			DominionBoard.takeCard(Card.province);
		}
		assertTrue(board.gameOver() == true);
		board = new DominionBoard(cards, 2);
		assertTrue(board.gameOver() == false);
		for(int i = 0; i < 10; i++)
		{
			DominionBoard.takeCard(Card.smithy);
			DominionBoard.takeCard(Card.ambassador);
			DominionBoard.takeCard(Card.council_room);
		}
		assertTrue(board.gameOver() == true);
	}
	
	
	
}
