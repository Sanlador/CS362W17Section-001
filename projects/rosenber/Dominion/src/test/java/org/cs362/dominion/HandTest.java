package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import java.util.Random;

public class HandTest {

	private Hand test;
	
	private Card createCard(String name){
		return new Card(name, 1, 2, 3, new CardType[] {CardType.Action});
	}
	
	@Test
	public void randomTesting(){
		Random rand = new Random();
		ArrayList<Card> cards = new ArrayList<Card>();
		for(int j=0; j<10; j++){
			cards.add(createCard(Integer.toString(j)));
		}
		for(int j=0; j<100; j++){
			int num;
			int size;
			Card c;
			test = new Hand();
			for(int k=0; k<10000; k++){
				num = rand.nextInt(11);
				switch(num){
				case 0:
					num = rand.nextInt(10);
					size = test.size();
					test.addCard(cards.get(num));
					assertEquals("Hand size didn't increase when card added",
							size+1, test.size());
					break;
				case 1:
					size = test.size();
					try{
						num = rand.nextInt(10);
						test.removeCard(cards.get(num));
						assertEquals("Hand size didn't decrease when card removed",
								size-1, test.size());
					}
					catch(Exception e){}; //fine
				case 2:
					size = test.size();
					try{
						num = rand.nextInt(test.size()+2);
						test.removeCard(num);
						assertEquals("Hand size didn't decrease when card removed",
								size-1, test.size());
					}
					catch(Exception e){}; //fine
					break;
				case 3:
					try{
						num = rand.nextInt(10);
						c = test.getCard(cards.get(num));
						assertEquals("Returned card doesn't match desired card",
								c, cards.get(num));
					}
					catch(Exception e){}; //fine
					break;
				case 4:
					try{
						num = rand.nextInt(test.size()+1);
						c = test.getCard(num);
						assertEquals("Returned card doesn't match desired card",
								c, test.hand.get(num));
					}
					catch(Exception e){}; //fine
					break;
				case 5:
					size = test.size();
					try{
						num = rand.nextInt(test.size()+1);
						c = test.playCard(cards.get(num));
						assertEquals("Hand size didn't decrease after played",
								size-1, test.size());
						assertEquals("Card played doesn't match card asked (Card)",
								c, cards.get(num));
					}
					catch(Exception e){}; //fine
					break;
				case 6:
					size = test.size();
					try{
						num = rand.nextInt(test.size()+2);
						Card other = test.hand.get(num);
						c = test.playCard(num);
						assertEquals("Hand size didn't decrease after played",
								size-1, test.size());
						assertEquals("Card played doesn't match card asked (idx)",
								c, other);
					}
					catch(Exception e){}; //fine
					break;
				case 7:
					try{
						//test.print();
					}
					catch(Exception e){
						fail("Print function crashed");
					};
					break;
				case 8:
					try{
						num = rand.nextInt(10);
						c = cards.get(num);
						if(test.hand.indexOf(c) == -1){
							assertFalse("Known card not in hand found in hand",
									test.inHand(c));
						}
						else{
							assertTrue("Known card in hand not found in hand",
								test.inHand(c));
						}
					}
					catch(Exception e){
						fail("inHand function crashed");
					}
					break;
				case 9:
					try{
						num = rand.nextInt(10);
						c = cards.get(num);
						size = test.size();
						Card other = test.playCard(c.getName());
						assertEquals("Size didn't decrease after string play card called"
								,size-1, test.size());
						assertEquals("Card returned from string play card doesn't match one asked for",
								c.getName(), other.getName());
					}
					catch(Exception e){};
					break;
				case 10:
					num = rand.nextInt(10);
					c = cards.get(num);
					test.addCard(c);
					assertTrue("Card in hand not found in hand",
							test.inHand(c.getName()));
					assertFalse("Card found in hand that isn't in hand",
							test.inHand("Blard"));
				}
			}
		}
	}
}



















