package dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class DeckTest {

	@Test
	public void test() {
		Deck deck = new Deck(Card.CardName.Baron);
		deck.addCard(new Card(Card.CardName.Tribute, Card.Type.ACTION, 0, 0, 0));
		assertEquals(deck.getPile().size(), 1);
	}

}
