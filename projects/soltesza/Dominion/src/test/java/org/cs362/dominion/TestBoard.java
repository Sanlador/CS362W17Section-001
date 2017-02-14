package org.cs362.dominion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.Vector;

public class TestBoard {
	@Test
	public void testBoard() {
		DominionBoard board = new DominionBoard();
		
		//test GetBuyable
		Vector<Card> buyable = board.GetBuyable(100);
		assertEquals("Unexpected number of buyable cards", 17, buyable.size());
		
		buyable = board.GetBuyable(2);
		assertTrue((buyable.size() < 17));
		
		buyable = board.GetBuyable(0);
		assertTrue(buyable.size() != 0);
		
		//test BuyCard
		buyable = board.GetBuyable(5);
		int idx = buyable.size()-1;
		Vector<Card> cards = board.BuyCard(idx);
		assertEquals("Card vector contains more than 1 card", 1, cards.size());
		assertEquals("Bought card not same type as specified card", buyable.elementAt(idx).GetName(), cards.elementAt(0).GetName());
		
		board.EmbargoPile(idx);
		cards = board.BuyCard(idx);
		assertEquals("Curse not returned with embargoed card", 2, cards.size());
		assertEquals("Embargoed pile did not return specified card in first position", cards.elementAt(0).GetName(), buyable.elementAt(idx).GetName());
		assertEquals("Curse", cards.elementAt(cards.size()-1).GetName());
		
		board.EmbargoPile(idx);
		cards = board.BuyCard(idx);
		assertEquals("did not return correct number of curses", 3, cards.size());
		assertEquals(cards.elementAt(0).GetName(), buyable.elementAt(idx).GetName());
		assertEquals("Curse card not returned", "Curse", cards.elementAt(cards.size()-1).GetName());
		
		//test BuyCurse
		Card curse = board.BuyCurse();
		assertEquals("Curse", curse.GetName());
		
		while(curse != null) {
			curse = board.BuyCurse();
		}
		
		curse  = board.BuyCurse();
		assertEquals("attempting to buy from empty pile does not return null object", null, curse);
		
		//curse should be in expended cards pile
		//board.PrintBoard();
		
		buyable = board.GetBuyable(5);
		idx = buyable.size()-1;
		board.EmbargoPile(idx);
		cards = board.BuyCard(idx);
		assertEquals(1, cards.size());
		assertEquals(cards.elementAt(0).GetName(), buyable.elementAt(idx).GetName());
		
		//test GameOver
		assertFalse("incorrect game end", board.GameOver());
		
		int max = buyable.size();
		while(buyable.size() >= max) {
			cards = board.BuyCard(buyable.size()-1);
			buyable = board.GetBuyable(7);
		}
		
		max = buyable.size();
		while(buyable.size() >= max) {
			cards = board.BuyCard(buyable.size()-1);
			buyable = board.GetBuyable(7);
		}
		
		board.PrintBoard();
		assertTrue("Does not recognize end of game through depletion of 3 piles", board.GameOver());
		
		board = new DominionBoard();
		
		buyable = board.GetBuyable(8);
		
		cards = board.BuyCard(buyable.size()-1);
		
		if(cards.firstElement().GetName() == "Province") {
			assertFalse(board.GameOver());
			
			max = buyable.size();
			while(buyable.size() >= max) {
				cards = board.BuyCard(buyable.size()-1);
				buyable = board.GetBuyable(8);
			}
			
			assertTrue("does not recognize end of game due to depletion of province cards", board.GameOver());
		}
	}
}
