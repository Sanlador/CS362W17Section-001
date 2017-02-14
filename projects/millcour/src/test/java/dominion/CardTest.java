package dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class CardTest{
	@Test 
	public void test(){
		Card card = new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1);
		assertEquals(card.getCardName(), Card.CardName.Copper);
		assertEquals(card.getType(), Card.Type.TREASURE);
		assertEquals(card.getTreasureValue(), 1);
		assertEquals(card.getCost(), 0);
		assertEquals(card.getScore(), 0);
		assertEquals(card.getCardName(), Card.CardName.Copper);
	}
}
