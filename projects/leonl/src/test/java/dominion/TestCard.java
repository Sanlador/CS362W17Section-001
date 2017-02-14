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


public class TestCard {
    private GameState state;
    private Player player1;
    private Player player2;
    private List<Card> cards;

    @Before
    public void initializePlayer() {
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);
        player1 = new Player(state, "PLAYER1");
        state.addPlayer(player1);
        player2 = new Player(state, "PLAYER2");
        state.addPlayer(player2);
    }

// TREASURE CARD TESTS

    @Test
    public void testValuesCopper() {
        assertEquals(Card.getCard(cards, Card.CardName.Cooper).getCost(), 0);
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
    
    @Test
    public void testValuesVillage() {
        assertEquals(Card.getCard(cards, Card.CardName.Village).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Village).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Village).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayVillage() {
        assertEquals(player1.hand.size(), 0);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Village));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.hand.size(), 1);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        System.out.println(player1);
    }
    @Test
    public void testValuesBaron() {
        assertEquals(Card.getCard(cards, Card.CardName.Baron).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Baron).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Baron).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayBaron() {
        assertEquals(player1.numBuys, 0);
        assertEquals(player1.coins, 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Baron));
        System.out.println(player1);
        if(Card.getCard(player1.hand, Card.CardName.Estate) != null){
            player1.playKingdomCard();
            assertEquals(player1.hand.size(), 4);
            assertEquals(player1.deck.size(), 5);
            assertEquals(player1.discard.size(), 1);
            assertEquals(player1.playedCards.size(), 1);
            assertEquals(player1.numBuys, 2);
            assertEquals(player1.coins, 4);
        } else {
            player1.playKingdomCard();
            assertEquals(player1.hand.size(), 1);
            assertEquals(player1.deck.size(), 0);
            assertEquals(player1.coins, 0);
        }


        System.out.println(player1);
    }
    @Test
    public void testValuesFeast() {
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Feast).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayFeast() {
        assertEquals(player1.hand.size(), 0);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        assertEquals(player1.coins, 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Feast));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 0);
        System.out.println(player1);
    }  
    @Test
    public void testValuesMine() {
        assertEquals(Card.getCard(cards, Card.CardName.Mine).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Mine).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Mine).getType(), Card.Type.ACTION);
    }
    @Test
    public void testValuesGreatHall() {
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getType(), Card.Type.ACTION);
    }



}
