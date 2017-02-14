package org.cs362.dominion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TestKingdomSlot {
	@Test
	public void testSlot() {
		Card card = new TreasureCard(TreasureCard.TreasureType.COPPER);
		KingdomSlot slot = new KingdomSlot(card,1);
		
		assertEquals("Card does not contain the card", card, slot.GetCard());
		assertEquals(0, slot.GetEmbargoes());
		slot.AddEmbargo();
		assertEquals(1, slot.GetEmbargoes());
		
		Card newCard = slot.DrawCard();
		
		assertTrue("card is not a treasure card", newCard instanceof TreasureCard);
		assertTrue("drawn card is not a deep copy", newCard != card);
		assertEquals("Copper", newCard.GetName());
		
		newCard = slot.DrawCard();
		assertEquals(null, newCard);
		
		newCard = slot.DrawCard();
		assertEquals(null, newCard);
	}
}
