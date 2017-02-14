import static org.junit.Assert.*;
import java.util.Random;

import org.junit.Test;

public class CardsTest {
	@Test
	public void testAdventurer() {	  
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		testGame.players[0].fillHand(new Random());
		testGame.players[0].hand.addCard(new Adventurer());													
		int value = testGame.players[0].hand.value;																		//The deck only has copper, so ending value is initial + 2
		int treasures = testGame.players[0].hand.treasureCount;															//The deck should end with two more treasures too
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);
		
		assertEquals(value + 2, testGame.players[0].hand.value);														
		assertEquals(treasures + 2, testGame.players[0].hand.treasureCount);	
	}
	
	@Test
	public void testAmbassador() {	  			
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		testGame.players[0].fillHand(new Random());
		testGame.players[0].hand.addCard(new Ambassador());																//Deck Size is now 6
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);													
		if(testGame.players[0].hand.size <= 4) {
			assertEquals(1, testGame.players[1].discard.size);															//If one or more cards is returned, make sure the other player got one
			assertTrue("Ambassador" != testGame.players[1].discard.cards.get(0).name);									//Check to make sure Ambassador wasn't returned
			//System.out.println("Opp Disc: " + testGame.players[1].discard.cards.get(0).name);
		}
		else {
			assertTrue("Ambassador" == testGame.players[0].discard.cards.get(0).name);									//If Ambassador discarded itself, this will cause an outofbounds error
		}
		assertTrue(testGame.players[0].hand.size <= 5);
	}
	
	@Test
	public void testBaron() {	  
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		boolean hasEstate = false;																						//If they have an estate, check to make sure it gets discarded
		testGame.players[0].fillHand(new Random());
		for(int i = 0; i < 5; i++) {
			if(testGame.players[0].hand.cards.get(i).name == "Estate") { hasEstate = true; }							//Check for estates
		}
		testGame.players[0].hand.addCard(new Baron());
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);
		if(hasEstate) {																									//If an estate is discarded
			assertEquals(4, testGame.players[0].hand.size);																//Check to see if two cards were discarded
			assertEquals(4, testGame.players[0].bonusCoins);															//Check the 4 bonus coins
			assertEquals(2, testGame.players[0].discard.size);															//Check that the cards both went to the discard
		}
		else {
			assertEquals(5, testGame.players[0].hand.size);																//Only one card should be discarded
			assertEquals(0, testGame.players[0].bonusCoins);															//No Bonus coins
			assertEquals(2, testGame.players[0].discard.size);
		}
	}
	
	@Test
	public void testCouncil_Room() {	  
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		testGame.players[0].fillHand(new Random());
		testGame.players[0].hand.addCard(new Council_Room());
		assertEquals(6, testGame.players[0].hand.size);
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);
		assertEquals(9, testGame.players[0].hand.size);																	//They should have gained a net three cards
		assertEquals(1, testGame.players[1].hand.size);																	//The opponent should have gained a card
		assertEquals(2, testGame.players[0].buys);																		//Gain a buy
	}
	
	@Test
	public void testCutpurse() {	  																					
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		testGame.players[0].fillHand(new Random());
		testGame.players[1].fillHand(new Random());
		testGame.players[0].hand.addCard(new Cutpurse());
		assertEquals(0, testGame.players[0].bonusCoins);
		assertEquals(5, testGame.players[1].hand.size);
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);
		assertEquals(2, testGame.players[0].bonusCoins);																//Check if the player gained 2 bonus coins
		assertEquals(4, testGame.players[1].hand.size);																	//Check if the other player discarded a card
		assertEquals("Copper", testGame.players[1].discard.cards.get(0).name);											//Check if the opponent discarded a copper
	}
	
	
	@Test
	public void testEmbargo() {	  
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		int embargoCount = 0;
		testGame.players[0].fillHand(new Random());
		testGame.players[0].hand.addCard(new Embargo());
		assertEquals(0, testGame.players[0].bonusCoins);
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);														
		assertEquals(0, testGame.players[0].discard.size);																//Check that it was trashed, not discarded
		assertEquals(2, testGame.players[0].bonusCoins);																//Check that the player gained 2 bonus coins
		for(int i = 0; i < 7; i++) {embargoCount += testGame.basicCards.get(i).embargoes; }
		for(int i = 0; i < 10; i++) {embargoCount += testGame.kingdomCards.get(i).embargoes; }
		assertEquals(1, embargoCount);																					//Make sure a pile gained an embargo
	}
	
	@Test
	public void testFeast() {	  
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		testGame.players[0].fillHand(new Random());
		testGame.players[0].hand.addCard(new Feast());
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);														
		assertEquals(5, testGame.players[0].hand.size);																	
		assertEquals(1, testGame.players[0].discard.size);																//Make sure they gained a card, but trashed the Feast
		assertTrue(testGame.players[0].discard.cards.get(0).value <= 5);												//Make sure the cards value is <= 5
	}
	
	@Test
	public void testGardens() {
		Random rand = new Random();
		Player testPlayer = new Player(0, new Random());
		testPlayer.totalPoints();
		assertEquals(3, testPlayer.VP);
		testPlayer.discard = new Deck(new Gardens(), 10);
		testPlayer.totalPoints();
		assertEquals(13, testPlayer.VP);																				//Check to see if the Gardens gain value appropriately
		testPlayer.discard = new Deck(new Gardens(), 20);
		testPlayer.totalPoints();
		assertEquals(43, testPlayer.VP);																				//This will fail as Gardens don't dynamically update points - Bug
	}
	
	@Test
	public void testGreat_Hall() {	   																					
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		testGame.players[0].fillHand(new Random());
		testGame.players[0].hand.addCard(new Great_Hall());
		assertEquals(6, testGame.players[0].hand.size);
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);
		assertEquals(6, testGame.players[0].hand.size);																	//Check that the net change is 0
		assertEquals(2, testGame.players[0].actions);																	//Check that they gained an action
	}
	
	@Test
	public void testMine() {	  
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		testGame.players[0].fillHand(new Random());
		testGame.players[0].hand.addCard(new Mine());
		int value = testGame.players[0].hand.value;
		assertEquals(6, testGame.players[0].hand.size);
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);
		assertEquals(5, testGame.players[0].hand.size);																	//Check that they discarded Smithy
		assertEquals(value + 1, testGame.players[0].hand.value);														//Check that they exchanged a Copper for Silver
		
	}
	
	@Test
	public void testRemodel() {	  
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		testGame.players[0].fillHand(new Random());
		testGame.players[0].hand.addCard(new Remodel());
		assertEquals(6, testGame.players[0].hand.size);
		assertEquals(0, testGame.players[0].discard.size);
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);
		assertEquals(4, testGame.players[0].hand.size);																	//Check they trashed a card and discarded Remodel - They can, but shouldnt be able to Trash Remodel - Bug
		assertEquals(2, testGame.players[0].discard.size);																//Check they gained a Card
	}
	
	@Test
	public void testVillage() {	  
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		testGame.players[0].fillHand(new Random());
		testGame.players[0].hand.addCard(new Village());
		assertEquals(1, testGame.players[0].actions);
		assertEquals(6, testGame.players[0].hand.size);
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);
		assertEquals(6, testGame.players[0].hand.size);																	//Check they have a net Card change of 0
		assertEquals(3, testGame.players[0].actions);																	//Check they have gained 2 actions
	}
	
	@Test
	public void testSmithy() {	  
		Random rand = new Random();
		DomGame testGame = new DomGame(rand.nextInt());
		testGame.players[0].fillHand(new Random());
		testGame.players[0].hand.addCard(new Smithy());
		assertEquals(6, testGame.players[0].hand.size);
		assertEquals(5, testGame.players[0].draw.size);
		assertEquals(0, testGame.players[0].discard.size);
		testGame.players[0].hand.cards.get(5).play(testGame, 0, 5);
		assertEquals(8, testGame.players[0].hand.size);																	//Check they drew 3 cards
		assertEquals(2, testGame.players[0].draw.size);																	//Check that draw pile lost 3 cards
		assertEquals(1, testGame.players[0].discard.size);																//Check that Smithy was discarded
	}

}
