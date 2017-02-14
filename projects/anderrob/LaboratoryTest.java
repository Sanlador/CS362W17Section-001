package dominion;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class LaboratoryTest {

	
	public static List<Card> cards ;
	@Test
	public void test() {
		int action = 1;
		int buys = 1;
		int coins = 0;
		Card chapel = Card.getCard(cards, Card.CardName.chapel);
		assertEquals(0, action);
		assertEquals(0, buys);
		assertEquals(0, coins);
	}

}
