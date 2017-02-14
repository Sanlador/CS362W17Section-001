import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zaengle on 2/12/2017.
 */
public class TreasureTest {

    List<Card> cards;

    @Before
    public void newMaterial(){
        cards = new ArrayList<Card>(Card.createCards());

    }

    @Test
    public void testMoney(){
        Card copper = Card.getCard(cards, Card.CardName.Copper);
        assertEquals(Card.getCard(cards, Card.CardName.Copper).getType(), Card.Type.TREASURE);
        assertEquals(copper.getCost(), 0);
        assertEquals(copper.getTreasureValue(), 1);
        assertEquals(Card.getCard(cards, Card.CardName.Copper).score(), 0);

        Card silver = Card.getCard(cards, Card.CardName.Silver);
        assertEquals(Card.getCard(cards, Card.CardName.Silver).getType(), Card.Type.TREASURE);
        assertEquals(silver.getCost(), 3);
        assertEquals(silver.getTreasureValue(), 2);
        assertEquals(Card.getCard(cards, Card.CardName.Silver).score(), 0);

        Card gold = Card.getCard(cards, Card.CardName.Gold);
        assertEquals(Card.getCard(cards, Card.CardName.Gold).getType(), Card.Type.TREASURE);
        assertEquals(gold.getCost(), 6);
        assertEquals(gold.getTreasureValue(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Gold).score(), 0);
    }

}
