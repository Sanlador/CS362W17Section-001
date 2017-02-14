import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
	
public class UnitTest {

	// Card Tests
	   @Test
	   public void testgetTreasure() 
	   {	  
	      assertEquals(Card.copper.getTreasure(), 1);
	   }
	   
	   @Test
	   public void testgetCost() 
	   {
		   assertEquals(Card.province.getCost(), 8);
	   }
	   
	   @Test
	   public void testgetVictory() 
	   {
		   assertEquals(Card.duchy.getVictory(),3);
	   }
	   
	   @Test
	   public void testgetTypes() 
	   {
		   List<String> types = new ArrayList<String>();
		   types.add("action");
		   types.add("attack");
		   assertEquals(Card.cutpurse.getTypes(),types);
	   }
	   
	   @Test
	   public void testsetVictory() 
	   {
		   Card.gardens.setVictory(7);
		   assertEquals(Card.gardens.getVictory(),7);
	   }
	   
	   // Test GameBoard
	   
	   @Test
	   public void testCreateKingdomCardList()
	   {
	       List<Card> cards = GameBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.feast, Card.gardens, Card.great_hall, Card.mine);
	       assertEquals(cards.size(),10);
	   }
	   
	   @Test
	   public void testCreateGameBoard()
	   {
	       List<Card> cards = GameBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.feast, Card.gardens, Card.great_hall, Card.mine);
	       GameBoard gb = new GameBoard(cards,2);
		   System.out.println(GameBoard.getPlayers().size());
	       assertEquals(GameBoard.getPlayers().size(),2);
	       gb = new GameBoard(cards, 3);
	       assertEquals(GameBoard.numCardsAvailable(Card.curse),20);
	       gb = new GameBoard(cards, 4);
	       assertEquals(GameBoard.numCardsAvailable(Card.province),12);
	   }
	   
	   @Test
	   public void testPlay()
	   {
	       List<Card> cards = GameBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.feast, Card.gardens, Card.great_hall, Card.mine);
	       GameBoard gb = new GameBoard(cards,2);
	       assertEquals(gb.play(),1);
	   }
	   
	   @Test
	   public void testEndOfGame()
	   {
	       List<Card> cards = GameBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.feast, Card.gardens, Card.great_hall, Card.mine);
	       GameBoard gb = new GameBoard(cards,2);
	       for (int i = 0; i < 8; i++)
	       {
	    	   gb.takeCard(Card.province);
	       }
	       assertEquals(gb.gameDone(),true);
	       gb = new GameBoard(cards,2);
	       assertEquals(gb.gameDone(),false);
	       for (int i = 0; i < 10; i++)
	       {
	    	   gb.takeCard(Card.adventurer);
	    	   gb.takeCard(Card.ambassador);
	    	   gb.takeCard(Card.baron);
	       }
	       assertEquals(gb.gameDone(),true);
	   }
	   
	   
	   
	   
	   // Player Tests
	   
	   
	   @Test
	   public void testConstructorAndGetNameNum()
	   {
	   	   List<Card> cards = GameBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.feast, Card.gardens, Card.great_hall, Card.mine);
	       GameBoard gb = new GameBoard(cards,2);
		   Player p = new Player("Player 1", 0);
		   assertEquals(p.getName(), "Player 1");
		   assertEquals(p.getNum(), 0);
		   assertEquals(p.getDeck().size(),5);
		   assertEquals(p.getHand().size(),5);
	   }
	   
	   @Test
	   public void testGainCardToHand()
	   {
	   	   List<Card> cards = GameBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.feast, Card.gardens, Card.great_hall, Card.mine);
	       GameBoard gb = new GameBoard(cards,2);
		   Player p = new Player("Player 1", 0);
		   p.gainCardToHand(Card.curse);
		   assertEquals(p.getHand().contains(Card.curse),true);
	   }
	   
	   @Test
	   public void testPlayCards()
	   {
	   	   List<Card> cards = GameBoard.kingdomCards(Card.adventurer, Card.ambassador, Card.baron, Card.council_room, Card.cutpurse, Card.embargo, Card.feast, Card.gardens, Card.great_hall, Card.mine);
	   	   List<Card> moreCards = GameBoard.kingdomCards(Card.smithy, Card.remodel, Card.salvager, Card.council_room, Card.cutpurse, Card.embargo, Card.feast, Card.gardens, Card.great_hall, Card.mine);

	   	   GameBoard gb = new GameBoard(cards,2);
		   Player p = new Player("Player 1", 0);
		   
		   int handSize;
		   int totalVictory;
		   int coins;
		   int totalTreasure;
		   int deckSize;
		   int totalEmbargo;
		   int discardSize;
		   int trashSize;
		   int actions;
		   int buys;
		   
		   p.addActions(100);
		   assertEquals(p.hasActions(), true);
		   
		   p.gainCardToHand(Card.adventurer);
		   p.countTreasure();
		   totalTreasure = p.getCoins();
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertTrue(p.getPlayedCards().size() >= 1);
		   p.countTreasure();
		   assertTrue(p.getDeck().size() == 0 || p.getCoins() > totalTreasure);
		   
		   p.gainCardToHand(Card.copper);
		   p.gainCardToHand(Card.copper);
		   p.gainCardToHand(Card.copper);
		   p.gainCardToHand(Card.estate);
		   p.gainCardToHand(Card.ambassador);
		   handSize = p.getHand().size();
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertTrue(p.getHand().size() <= handSize);

		   p.gainCardToHand(Card.estate);
		   p.gainCardToHand(Card.baron);
		   totalVictory = p.getTotalVictoryPoints();
		   coins = p.getCoins();
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertTrue(p.getTotalVictoryPoints() == totalVictory + 1 || p.getCoins() > coins);
		   
		   p.gainCardToHand(Card.council_room);
		   handSize = p.getHand().size();
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertEquals(p.getHand().size(), handSize + 3);

		   p.gainCardToHand(Card.cutpurse);
		   coins = p.getCoins();
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertEquals(p.getCoins(), coins + 2);
		   

		   p.gainCardToHand(Card.embargo);
		   totalEmbargo = gb.getTotalEmbargoTokens();
		   coins = p.getCoins();
		   handSize = p.getHand().size();
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertTrue(p.getCoins() == coins + 2 && p.getHand().size() == handSize - 1 && gb.getTotalEmbargoTokens() == totalEmbargo + 1);

		   p.gainCardToHand(Card.feast);
		   discardSize = p.getDiscard().size();
		   trashSize = gb.getTrash().size();
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertTrue(p.getDiscard().size() == discardSize + 1 && trashSize == gb.getTrash().size() - 1);
		   
		   
		   p.gainCardToHand(Card.great_hall);		   
		   handSize = p.getHand().size();
		   actions = p.getNumActions();
		   totalVictory = p.getTotalVictoryPoints();
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertTrue(p.getHand().size() >= handSize && p.getNumActions() >= actions);
		   
		   p.discardHandAndInPlay();
		   p.gainCardToHand(Card.mine);
		   p.gainCardToHand(Card.copper);
		   p.gainCardToHand(Card.copper);
		   p.gainCardToHand(Card.copper);
		   p.gainCardToHand(Card.copper);
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertEquals(p.getHand().contains(Card.silver), true);
		   
		   gb = new GameBoard(moreCards,2);
		   p.gainCardToHand(Card.remodel);
		   discardSize = p.getDiscard().size();
		   trashSize = gb.getTrash().size();
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertTrue(p.getDiscard().size() == discardSize + 1 && gb.getTrash().size() == trashSize + 1);
		   
		   p.gainCardToHand(Card.smithy);
		   handSize = p.getHand().size();
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertEquals(handSize, p.getHand().size() - 2);
		   
		   p.discardHandAndInPlay();
		   p.gainCardToHand(Card.salvager);
		   p.gainCardToHand(Card.silver);
		   p.gainCardToHand(Card.silver);
		   p.gainCardToHand(Card.silver);
		   p.gainCardToHand(Card.silver);
		   buys = p.getBuys();
		   coins = p.getCoins();
		   p.playCard(Randomness.randomMember(p.getActionCardIndices()));
		   assertTrue(p.getBuys() == buys + 1 && p.getCoins() == coins + 3);
			   
	   }
	   
	   @Test
	   public void testPlayDominion()
	   {
		   PlayDominion.main(null);
	   }
	   
	   
	   	   
}
	
