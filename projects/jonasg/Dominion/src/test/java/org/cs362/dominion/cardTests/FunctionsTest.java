package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class FunctionsTest {

    @Test
    public void test0() throws Throwable {
	Randomness.reset();

	List<Card> cards = new ArrayList<Card>();
	List<Card> testCards = new ArrayList<Card>(Card.createCards());
	GameState state = new GameState(testCards);

	Card province = Card.getCard(testCards, Card.CardName.Province);

	cards.add(Card.getCard(testCards, Card.CardName.Estate));
	assertEquals(null, Card.getCard(cards, province.getCardName()));

	assertEquals(province.equals(state), false);
	assertEquals(province.equals(Card.getCard(cards, Card.CardName.Estate)), false);
	assertEquals(province.score(), 6);
    }
}
