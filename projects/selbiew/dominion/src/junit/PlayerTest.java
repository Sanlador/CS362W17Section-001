import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

public class PlayerTest {

	@Test
	public void testConstructor() {	  														//Check that the Constructor creates with the correct values
		int copperCount = 0;
		int estCount = 0;
		Player testPlayer = new Player(0, new Random());
		assertEquals(0, testPlayer.ID);
		assertEquals(0, testPlayer.hand.size);
		assertEquals(10, testPlayer.draw.size);
		assertEquals(0, testPlayer.discard.size);
		
		for(int i = 0; i < 10; i++){
			if(testPlayer.draw.cards.get(i).name == "Copper") {copperCount += 1; }
			if(testPlayer.draw.cards.get(i).name == "Estate") {estCount += 1; }
		}
		assertEquals(3, estCount);															//Start with 3 Estates and 7 Coppers
		assertEquals(7, copperCount);
	}
	
	
	@Test
	public void testDiscardToDraw() {							
		Player testPlayer = new Player(0, new Random());
		assertEquals(10, testPlayer.draw.size);
		assertEquals(0, testPlayer.discard.size);
		for(int i = 0; i < 10; i++) {
			testPlayer.discard.addCard(testPlayer.draw.drawTop());
		}
		assertEquals(0, testPlayer.draw.size);												//Draw starts with 0 Cards, Discard with 10
		assertEquals(10, testPlayer.discard.size);
		testPlayer.discardToDraw(new Random());
		assertEquals(10, testPlayer.draw.size);												//Draw ends with 10 Cards, 
		assertEquals(0, testPlayer.discard.size);
	}

	@Test
	public void testActionAI() {
		DomGame testDom = new DomGame(0);
		testDom.players[0].fillHand(new Random());
		testDom.players[0].hand.addCard(new Village());
		testDom.players[0].hand.addCard(new Smithy());
		assertEquals(7, testDom.players[0].hand.size);
		testDom.players[0].actionAI(testDom);
		assertEquals(9, testDom.players[0].hand.size);
		assertEquals(1, testDom.players[0].draw.size);
		
		
	}
	
	@Test
	public void testBuyAI() {
		DomGame testDom = new DomGame(0);
		testDom.players[0].bonusCoins = 5;
		testDom.players[0].buyAI(testDom);
		assertEquals(1, testDom.players[0].discard.size);
	}
	
	@Test
	public void testFillHand() {														
		Player testPlayer = new Player(0, new Random());
		assertEquals(0, testPlayer.hand.size);
		testPlayer.fillHand(new Random());
		assertEquals(5, testPlayer.hand.size);												//Check that fillHand fills the hand with 5 cards
		
	}
	
	@Test
	public void testBuyCard() {
		String[] cardName = {"Copper", "Silver", "Gold", "Estate", "Province", "Duchy", "Curse"};
		DomGame testDom = new DomGame(0);
		Random rand = new Random();
		int randIndex = rand.nextInt(7);
		
		int coins = testDom.players[0].buyCard(testDom, 8, cardName[randIndex]);
		assertEquals(cardName[randIndex], testDom.players[0].discard.cards.get(0).name);	//Check that it bought the Correct card
		assertEquals(coins, 8 - testDom.players[0].discard.cards.get(0).cost);				//Check that the correct number of coins were spent
	}
	
	@Test
	public void testGetCardCount() {
		Player testPlayer = new Player(0, new Random());
		assertTrue(10 == testPlayer.getCardCount());										//Check that you start with 10
		testPlayer.hand.addCard(new Copper());	
		assertTrue(11 == testPlayer.getCardCount());										//Plus one is 11		
		testPlayer.draw.addCard(new Silver());				
		assertTrue(12 == testPlayer.getCardCount());										//Plus one is 12 etc...
		testPlayer.discard.addCard(new Gold());
		assertTrue(13 == testPlayer.getCardCount());		
	}
	
	@Test
	public void testGetTotalValue() {
		Player testPlayer = new Player(0, new Random());									
		assertTrue(7 == testPlayer.getTotalValue());										//Check the initial value of 7 (7 Coppers)
		
		testPlayer.hand.addCard(new Copper());
		assertTrue(8 == testPlayer.getTotalValue());										//Plus 1 from copper is 8
		
		testPlayer.draw.addCard(new Silver());
		assertTrue(10 == testPlayer.getTotalValue());										//Plus 2 from Silver is 10
		
		testPlayer.discard.addCard(new Gold());
		assertTrue(13 == testPlayer.getTotalValue());										//Plus 3 from Gold is 13
	}
	
	@Test
	public void testGetAverageValue() {
		Player testPlayer = new Player(0, new Random());
		assertTrue(0.7f == testPlayer.getAverageValue());									//Average value starts at 7/10
		
		testPlayer.hand.addCard(new Copper());
		assertTrue((8f/11f) == testPlayer.getAverageValue());								//Then goes to 8/11
		
		testPlayer.draw.addCard(new Silver());
		assertTrue((10f/12f) == testPlayer.getAverageValue());								//With a Silver it's 10/12
		
		testPlayer.discard.addCard(new Gold());
		assertTrue(1.0f == testPlayer.getAverageValue());									//Adding a Gold makes it 13/13
		
	}
	
	@Test
	public void testGetActionCount() {
		Player testPlayer = new Player(0, new Random());
		assertEquals(0, testPlayer.getActionCount());										//Estates and Coppers have no actions, so starts at 0
		
		testPlayer.hand.addCard(new Adventurer());
		assertEquals(1, testPlayer.getActionCount());										//Check that adding an Adventurer adds one actionCount
		
		testPlayer.draw.addCard(new Ambassador());
		assertEquals(2, testPlayer.getActionCount());										//Check that adding an Ambassador adds one actionCount
		
		testPlayer.discard.addCard(new Baron());
		assertEquals(3, testPlayer.getActionCount());										//Check that adding a Baron adds one actionCount
	}
	
	@Test
	public void testTreasureCount() {
		Player testPlayer = new Player(0, new Random());
		assertEquals(7, testPlayer.getTreasureCount());										//Check they start out with 7 (7 Coppers)
		
		testPlayer.hand.addCard(new Copper());
		assertEquals(8, testPlayer.getTreasureCount());										//Adding a Copper adds one
		
		
		testPlayer.draw.addCard(new Silver());
		assertEquals(9, testPlayer.getTreasureCount());										//Adding a Silver adds one
		
		testPlayer.discard.addCard(new Gold());
		assertEquals(10, testPlayer.getTreasureCount());									//Adding a Gold adds one
	}
	
	@Test
	public void testTotalPoints() {
		Player testPlayer = new Player(0, new Random());
		testPlayer.totalPoints();
		assertEquals(3, testPlayer.VP);														//Check they start out with 3 (3 Estates)
		
		testPlayer.hand.addCard(new Estate());
		testPlayer.totalPoints();
		assertEquals(4, testPlayer.VP);														//Adding an estate adds one
		
		testPlayer.hand.addCard(new Duchy());
		testPlayer.totalPoints();
		assertEquals(7, testPlayer.VP);														//Adding a Duchy adds 3
		
		testPlayer.hand.addCard(new Province());
		testPlayer.totalPoints();
		assertEquals(13, testPlayer.VP);													//Adding a Province adds 6
		
	}


}
