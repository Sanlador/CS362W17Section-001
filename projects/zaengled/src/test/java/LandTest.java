import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Zaengle on 2/12/2017.
 */
public class LandTest {

    List<Card> cards;

    @Before
    public void newMaterial(){
        cards = new ArrayList<Card>(Card.createCards());
    }

    @Test
    public void testLand(){
        Card estate = Card.getCard(cards, Card.CardName.Estate);
        assertEquals(Card.getCard(cards, Card.CardName.Estate).getType(), Card.Type.VICTORY);
        assertEquals(Card.getCard(cards, Card.CardName.Estate).score(), 1);
        assertEquals(estate.getCost(), 2);
        assertEquals(estate.getTreasureValue(), 0);

        Card duchy = Card.getCard(cards, Card.CardName.Duchy);
        assertEquals(Card.getCard(cards, Card.CardName.Duchy).getType(), Card.Type.VICTORY);
        assertEquals(Card.getCard(cards, Card.CardName.Duchy).score(), 3);
        assertEquals(duchy.getCost(), 5);
        assertEquals(duchy.getTreasureValue(), 0);

        Card province = Card.getCard(cards, Card.CardName.Province);
        assertEquals(Card.getCard(cards, Card.CardName.Province).getType(), Card.Type.VICTORY);
        assertEquals(Card.getCard(cards, Card.CardName.Province).score(), 6);
        assertEquals(province.getCost(), 8);
        assertEquals(province.getTreasureValue(), 0);
    }
}
