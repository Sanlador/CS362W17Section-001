package dominion; 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;


public class CardTest {
    private GameState state;
    private Player player1;
    private Player player2;
    private List<Card> cards;

    @Before
    public void initializePlayer() {
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);
        player1 = new Player(state, "PLAYER 1");
        state.addPlayer(player1);
        player2 = new Player(state, "PLAYER 2");
        state.addPlayer(player2);
    }

// TREASURE CARD TESTS

    @Test
    public void testValuesCopper() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        //assertEquals(Card.COPPER.costsAction, 0);
        assertEquals(Card.getCard(cards, Card.CardName.Cooper).getCost(), 0);
        //assertEquals(Card.COPPER.givesActions, 0);
        //assertEquals(Card.COPPER.givesCardDraws, 0);
        //assertEquals(Card.COPPER.givesMoney, 1);
        assertEquals(Card.getCard(cards, Card.CardName.Cooper).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Cooper).getType(), Card.Type.TREASURE);
    }

    @Test
    public void testPlayCopper() {
        assertEquals(player1.coins, 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Cooper));
        player1.playTreasureCard();
        assertEquals(player1.coins, 1);
    }

    @Test
    public void testValuesSilver() {
        assertEquals(Card.getCard(cards, Card.CardName.Silver).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Silver).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Silver).getType(), Card.Type.TREASURE);
    }

    @Test
    public void testPlaySilver() {
        assertEquals(player1.coins, 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Silver));
        player1.playTreasureCard();
        assertEquals(player1.coins, 2);
    }

    @Test
    public void testValuesGold() {
        assertEquals(Card.getCard(cards, Card.CardName.Gold).getCost(), 6);
        assertEquals(Card.getCard(cards, Card.CardName.Gold).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Gold).getType(), Card.Type.TREASURE);
    }

    @Test
    public void testPlayGold() {
        assertEquals(player1.coins, 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Gold));
        player1.playTreasureCard();
        assertEquals(player1.coins, 3);
    }


// ACTION CARD TESTS


    @Test
    public void testValuesSmithy() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Smithy).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Smithy).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Smithy).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlaySmithy() {
        player1.numActions = 1;
        assertEquals(player1.hand.size(), 0);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        player1.deck.add(Card.getCard(cards, Card.CardName.Estate)); //random cards to run past and discard
        player1.deck.add(Card.getCard(cards, Card.CardName.Estate));
        player1.deck.add(Card.getCard(cards, Card.CardName.Cooper));
        player1.deck.add(Card.getCard(cards, Card.CardName.Cooper));
        player1.hand.add(Card.getCard(cards, Card.CardName.Smithy));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.hand.size(), 3);
        assertEquals(player1.deck.size(), 1);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 1);
        System.out.println(player1);
    }

    @Test
    public void testValuesAdventurer() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getCost(), 6);
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayAdventurer() {
        player1.numActions = 1;
        assertEquals(player1.coins, 0);
        assertEquals(player1.hand.size(), 0);
        player1.deck.add(Card.getCard(cards, Card.CardName.Estate)); //random cards to run past and discard
        player1.deck.add(Card.getCard(cards, Card.CardName.Estate));
        player1.deck.add(Card.getCard(cards, Card.CardName.Cooper));
        player1.deck.add(Card.getCard(cards, Card.CardName.Cooper));
        player1.hand.add(Card.getCard(cards, Card.CardName.Adventurer));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.hand.size(), 2);
        assertEquals(player1.discard.size(), 2);
        assertEquals(player1.playedCards.size(), 1);
        player1.playTreasureCard();
        System.out.println(player1);
        assertEquals(player1.hand.size(), 0);
        assertEquals(player1.discard.size(), 2);
        assertEquals(player1.playedCards.size(), 3);
        assertEquals(player1.coins, 2);
    }

}
