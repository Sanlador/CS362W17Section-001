package org.cs362.dominion;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Random;

public class DeckTest {

	private Deck test;

	private Card createCard(String name){
		return new Card(name, 1, 2, 3, new CardType[] {CardType.Action});
	}
	
	@Test
	public void testName() {
		test = new Deck("Test Deck");
		assertEquals("The deck's name didn't get set correctly",
				test.getName(), "Test Deck");
		
		test.setName("Changed Name");
		assertEquals("setName didn't change the name correctly",
				test.getName(), "Changed Name");
		
		test = new Deck();
		assertEquals("empty name not assigned in deck without String",
				test.getName(), "");
	}

	@Test 
	public void randomTesting(){
		Random rand = new Random();
		ArrayList<Card> cards = new ArrayList<Card>();
		for(int j=0; j<10; j++){
			cards.add(createCard(Integer.toString(j)));
		}
		for(int j=0; j<100; j++){
			test = new Deck();
			Card c;
			Card other;
			int num;
			int size;
			for(int k=0; k<10000; k++){
				num = rand.nextInt(13);
				switch(num){
				case 0:
					num = rand.nextInt(10);
					size = test.size();
					test.addTop(cards.get(num));
					assertEquals("Size didn't change when card added to top",
							size+1, test.size());
					break;
				case 1:
					num = rand.nextInt(10);
					size = test.size();
					test.addBottom(cards.get(num));
					assertEquals("Size didn't change when card added to bottom",
							size+1, test.size());
					break;
				case 2:
					try{
						c = test.deck.get(test.deck.size()-1);
						assertEquals("Top card doesn't match known top card",
							c, test.getTop());
					}
					catch(Exception e){};
					break;
				case 3:
					try{
						c = test.deck.get(0);
						assertEquals("Bottom card doesn't match known top card",
							c, test.getBottom());
					}
					catch(Exception e){};
					break;
				case 4:
					size = test.size();
					try{
						test.removeTop();
						assertEquals("Size didn't change when card removed from top",
							size-1, test.size());
					}
					catch(Exception e){};
					break;
				case 5:
					size = test.size();
					try{
						test.removeBottom();
						assertEquals("Size didn't change when card removed from bottom",
							size-1, test.size());
					}
					catch(Exception e){};
					break;
				case 6:
					size = test.size();
					try{
						c = test.deck.get(test.size()-1);
						other = test.drawTop();
						assertEquals("Size didn't decrease when card drawn from top",
							size-1, test.size());
						assertEquals("Top drawn card didn't match known top card",
							c, other);
					}
					catch(Exception e){};
					break;
				case 7:
					size = test.size();
					try{
						c = test.deck.get(0);
						other = test.drawBottom();
						assertEquals("Size didn't decrease when card drawn from bottom",
							size-1, test.size());
						assertEquals("Bottom drawn card didn't match known bottom card",
							c, other);
					}
					catch(Exception e){};
					break;
				case 8:
					num = rand.nextInt(10);
					c = cards.get(num);
					if(test.deck.indexOf(c) == -1){
						assertFalse("Found card in deck that doesn't exist",
								test.isCard(c));
					}
					else{
						assertTrue("Didn't find card in deck that exists",
								test.isCard(c));
					}
					break;
				case 9:
					num = rand.nextInt(10);
					c = cards.get(num);
					try{
						other = test.findSpecific(c);
						assertEquals("Card found wasn't card asked for",
								c, other);
					}
					catch(Exception e){};
					break;
				case 10:
					num = rand.nextInt(10);
					c = cards.get(num);
					size = test.size();
					try{
						other = test.getSpecific(c);
						assertEquals("Deck size didn't decrease when removing specific card",
								size-1, test.size());
						assertEquals("Specific card removed doesn't match card asked",
								c, other);
					}
					catch(Exception e){};
					break;
				case 11:
					num = rand.nextInt(10);
					try{
						c = cards.get(num);
						if(test.deck.indexOf(c) != -1){
							size = test.size();
							test.removeSpecific(c);
							assertEquals("Size didn't decrement when specific card is removed",
									size-1, test.size());
						}
					}
					catch(Exception e){};
					break;
				case 12:
					num = rand.nextInt(test.size()+2);
					try{
						c = test.deck.get(num);
						other = test.findSpecific(num);
						assertEquals("Found card didn't match desired card from index",
								c, other);
					}
					catch(Exception e){};
				}
			}
		}
	}
	
	@Test
	public void testShuffle(){
		for(int i=0; i<100; i++){
			test = new Deck("Shuffle Test");
			int numCards = 100;
			ArrayList<Card> cards = new ArrayList<Card>();
			for(int j=0; j < numCards; j++)
				cards.add(createCard(String.valueOf(j)));
			for(int j=0; j < numCards; j++)
				test.addTop(cards.get(j));
			
			//true if a the decks are the same
			Boolean flag = true;
			
			test.shuffle();
			for(int j=0; j < numCards; j++){
				if(cards.get(j) != test.drawBottom())
					flag = false;
			}
			assertFalse("Deck didn't change after being shuffled",
					flag);
		}
	}
}
























