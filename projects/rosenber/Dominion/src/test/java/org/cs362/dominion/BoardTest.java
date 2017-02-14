package org.cs362.dominion;

import static org.junit.Assert.*;
import org.junit.Test;

public class BoardTest {

	private Board test;
	
	private Card createCard(String name){
		return new Card(name, 1, 2, 3, new CardType[] {CardType.Action});
	}
	
	@Test
	public void testConstructors() {
		
		test = new Board();
		assertEquals("Board didn't get initialized with a deck",
				1, test.numDecks());
		assertEquals("Initialzed deck in board isn't named 'Trash'",
				"Trash", test.getName(0));
		assertEquals("Initialzed deck in board isn't empty",
				test.numCardsInDeck(0), 0);
	}
	
	@Test
	public void testCreatingDeck(){
		
		test = new Board();
		Card c = createCard("one");
		
		test.createDeck(c, 5);
		assertEquals("New deck wasn't added to the board",
				2, test.numDecks());
		assertEquals("New deck's name set properly",
				"one", test.getName(1));
		assertEquals("Number of cards in new deck wrong",
				test.numCardsInDeck(1), 5);	
	}
	
	@Test
	public void testPrint(){
		test = new Board();
		try{
			test.print();
		}
		catch(Exception e){
			fail("Print function crashes");
		}
	}

	@Test
	public void testDeckInteraction(){
		test = new Board();
		Card c = createCard("one");
		
		test.addToTrash(c);
		assertEquals("Trash doesn't have added card in it",
				test.numCardsInDeck(0), 1);
		
		test.createDeck(createCard("two"), 5);
		
		Card drawn = test.draw(1);
		assertFalse("Drawn card is equal to null",
				drawn == null);
		assertEquals("Deck didn't lose card after draw",
				test.numCardsInDeck(1), 4);
		assertEquals("Card doesn't match deck",
				drawn.getName(), test.getName(1));
		
	}
	
	@Test
	public void foo(){
		
		Card c = createCard("foo");
		test = new Board();
		test.createDeck(c, 10);
		
		try{
			test.lookAtDeck(-1);
			fail("There shouldn't be a deck at idx -1");
		}catch(Exception e){};
		
		assertEquals("Looked at deck card name didn't match card it was made from"
				, c.getName(), test.lookAtDeck(1).getName());
		
		assertEquals("Index of deck doesn't match what it should be"
				, 1, test.getDeckIdx(c.getName()));
		
		try{
			test.getDeckIdx("Blarg");
			fail("This should have crashed");
		}catch(Exception e){};
		
		assertTrue("Doesn't recognize the deck name that should be there",
				test.isDeck(c.getName()));
		
		assertFalse("Recognizes a deck name that doesn't exist",
				test.isDeck("Blarg"));
		
	}
	
}
