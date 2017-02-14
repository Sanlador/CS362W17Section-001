package org.cs362.dominion;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Vector;

public class TestDeck {
	
	@Test
	public void testDeck() {
		Deck deck = new Deck();
		assertEquals("Not enough cards in initialized deck", 10, deck.GetCardCount());
		assertEquals("Player has cards in hand", 0, deck.GetHand().size());
		assertEquals("PlayTreasures is not zero when hand is empty", 0, deck.PlayTreasures());
		assertEquals("Starting score should be 3", 3, deck.GetScore());
		
		//test drawing cards to hand
		deck.DrawCards(12);
		Vector<Card> hand = deck.GetHand();
		assertEquals("Not all cards transferred to hand", hand.size(), deck.GetCardCount());
		
		deck.DiscardHand();
		hand = deck.GetHand();
		assertEquals("Not all cards discarded from hand", 0, hand.size());
		
		hand = deck.DrawCards(5);
		assertEquals("Hand does not contain 5 cards", 5, hand.size());
		
		deck.DrawCards(10);
		hand = deck.GetHand();
		assertEquals("Hand does not contain 10 cards", 10, hand.size());
		
		//testing playTreasures
		assertEquals("Hand does not contain correct number of cards after playing treasures", 7, deck.PlayTreasures());
		
		int size = deck.GetHand().size();
		deck.DiscardFromHand(hand.get(hand.size()-1));
		assertNotEquals("Hand size has not changed  after discarding", size, deck.GetHand().size());
		
		//test AddCard
		int cc = deck.GetCardCount();
		int score = deck.GetScore();
		Card card1 = new VictoryCard(VictoryCard.VictoryType.CURSE);
		deck.AddCard(card1);
		assertNotEquals("card count has not changed after adding a card", deck.GetCardCount(), cc);
		assertNotEquals("Score has not changed after adding victory card", deck.GetScore(), score);
		
		Card card2 = new VictoryCard(VictoryCard.VictoryType.GARDENS);
		deck.AddCard(card2);
		assertEquals("Garden score is not counted properly", 3, deck.GetScore());
		
		//test TrashCard
		cc = deck.GetCardCount();
		deck.TrashCard(card2);
		assertNotEquals("Card count does not change after trashing", cc, deck.GetCardCount());
		assertEquals("Score does not change after trashing garden", 2, deck.GetScore());
		
		cc = deck.GetCardCount();
		deck.TrashCard(card1);
		assertNotEquals("count does not change after trashing card", cc, deck.GetCardCount());
		assertEquals("Score does not change after trashing Curse", 3, deck.GetScore());
	}
}
