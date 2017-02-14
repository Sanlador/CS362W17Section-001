import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class DeckTest {
	@Test
	public void testConstructor() {	  
		Deck testDeck = new Deck();													//Check starting values are correct
		assertEquals(0, testDeck.cards.size());
		assertEquals(0, testDeck.value);
		assertEquals(0, testDeck.points);
		assertEquals(0, testDeck.embargoes);
		assertEquals(0, testDeck.victoryCount);
		assertEquals(0, testDeck.actionCount);
		assertEquals(0, testDeck.treasureCount);
	}
	
	@Test
	public void testConstructorSpecific() {											//Check more specific Constructor's values are correct
		Random rand = new Random();
		int deckSize = rand.nextInt(25);
		Deck testDeck = new Deck(new Copper(), deckSize);
		
		assertEquals(deckSize, testDeck.size);
		assertEquals(deckSize, testDeck.value);
		assertEquals(0, testDeck.points);
	}
	
	@Test
	public void testAddCard() {														
		Deck testDeck = new Deck();
		assertEquals(0, testDeck.size);
		testDeck.addCard(new Copper());
		
		assertEquals(new Copper().name, testDeck.cards.get(0).name);				//Check that the name is correct
		assertEquals(1, testDeck.size);												//Check that the size changes
		assertEquals(1, testDeck.value);											//Check that value changes
	}
	
	@Test
	public void testRemoveCard() {													
		Deck testDeck = new Deck();
		testDeck.addCard(new Copper());
		assertEquals(new Copper().name, testDeck.cards.get(0).name);
		assertEquals(1, testDeck.size);												//Check size is initially 1
		
		testDeck.removeCard(0);
		assertEquals(0, testDeck.size);												//Check that size is 0 after removal
	}
	
	@Test
	public void testDrawTop() {														
		Deck testHand = new Deck();
		Deck testDraw = new Deck();
		testDraw.addCard(new Copper());
		
		assertEquals(new Copper().name, testDraw.cards.get(0).name);
		assertEquals(0, testHand.size);
		assertEquals(1, testDraw.size);
		
		testHand.addCard(testDraw.drawTop());
		assertEquals(new Copper().name, testHand.cards.get(0).name);
		assertEquals(1, testHand.size);												//Check that the hand gained a card
		assertEquals(0, testDraw.size);												//Check that the Draw lost a card
	}
	
	@Test
	public void testUpdate() {										//Iterate through the Deck keeping track of Victory Points and Coin value
		Deck testDeck = new Deck();
		assertEquals(0, testDeck.size);
		
		testDeck.addCard(new Copper());
		testDeck.addCard(new Estate());
		testDeck.addCard(new Feast());
		testDeck.update();
		
		assertEquals(1, testDeck.value);							//Check that value updated
		assertEquals(1, testDeck.victoryCount);						//Check that the Victory Card count updated
		assertEquals(1, testDeck.actionCount);						//Check that the Action Card count updated
	}
	
	
	@Test
	public void testShuffle() {										//Shuffle the deck
		Boolean sameDeck = true;
		Deck testDeckOne = new Deck();
		testDeckOne.addCard(new Copper());
		testDeckOne.addCard(new Estate());
		testDeckOne.addCard(new Feast());
		testDeckOne.addCard(new Silver());
		testDeckOne.addCard(new Duchy());
		
		Deck testDeckTwo = new Deck();
		testDeckTwo.addCard(new Copper());
		testDeckTwo.addCard(new Estate());
		testDeckTwo.addCard(new Feast());
		testDeckTwo.addCard(new Silver());
		testDeckTwo.addCard(new Duchy());
		
		testDeckOne.shuffle(new Random());
		
		for(int i = 0; i < 5; i++) {
			if(testDeckOne.cards.get(i).name != testDeckTwo.cards.get(i).name) { sameDeck = false; }
		}
		assertEquals(false, sameDeck);								//Check that the Deck is different, the Deck can but should not be the same - Bug
		
	}
	

}
