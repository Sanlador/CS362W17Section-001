import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CardTest {
    private GameState state;
    private Player player1;
    private Player player2;
    private List<Card> cards;

    @Before
    public void initializeGame() {
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);
        player1 = new Player(state, "PLAYER 1");
        state.addPlayer(player1);
        player2 = new Player(state, "PLAYER 2");
        state.addPlayer(player2);
        state.initializeGame();
    }

//---------------------
// TREASURE CARD TESTS
//---------------------
    @Test
    public void testValuesCopper() {
        Card Copper = Card.getCard(cards, Card.CardName.Copper);
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Copper.getCost(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Copper).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Copper).getType(), Card.Type.TREASURE);
    }

    @Test
    public void testPlayCopper() {
        assertEquals(player1.coins, 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Copper));
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

//-------------------
// ACTION CARD TESTS
//-------------------

    @Test
    public void testValuesAdventurer() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getCost(), 6);
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayAdventurer() {
        player1.initializePlayerTurn();
        assertEquals(player1.coins, 0);
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.playedCards.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Adventurer));
        System.out.println(player1);
        player1.playKingdomCard();
        assertTrue(player1.hand.size() >= 6);
        assertTrue(player1.deck.size() >= 1);//depends if coppers are in front or not
        assertEquals(player1.playedCards.size(), 1);
        System.out.println(player1);
    }

    @Test
    public void testValuesSmithy() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Smithy).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Smithy).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Smithy).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlaySmithy() {
        player1.initializePlayerTurn();
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Smithy));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.hand.size(), 8);
        assertEquals(player1.deck.size(), 2);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 1);
        System.out.println(player1);
    }

    @Test
    public void testValuesVillage() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Village).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Village).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Village).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayVillage() {
        player1.initializePlayerTurn();
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Village));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.hand.size(), 6);
        assertEquals(player1.deck.size(), 4);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 1);
        assertEquals(player1.numActions, 2);
        System.out.println(player1);
    }

    @Test
    public void testValuesAmbassador() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Ambassador).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Ambassador).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Ambassador).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayAmbassador() {
        player1.initializePlayerTurn();
        player2.initializePlayerTurn();
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        assertEquals(player2.discard.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Ambassador));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.hand.size(), 4);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 1);
        assertEquals(player2.discard.size(), 1);
        System.out.println(player1);
    }

    @Test
    public void testValuesBaron() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Baron).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Baron).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Baron).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayBaron() {
        player1.initializePlayerTurn();
        assertEquals(player1.numBuys, 1);
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
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
            assertEquals(player1.hand.size(), 5);
            assertEquals(player1.deck.size(), 4);
            assertEquals(player1.discard.size(), 0);
            assertEquals(player1.playedCards.size(), 1);
            assertEquals(player1.numActions, 2);
            assertEquals(player1.coins, 0);
        }


        System.out.println(player1);
    }

    @Test
    public void testValuesCouncilRoom() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayCouncilRoom() {
        player1.initializePlayerTurn();
        player2.initializePlayerTurn();
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        assertEquals(player1.numBuys, 1);
        player1.hand.add(Card.getCard(cards, Card.CardName.Council_Room));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.hand.size(), 9);
        assertEquals(player1.deck.size(), 1);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 1);
        assertEquals(player1.numBuys, 2);
        for(Player p : state.players){
            if(p != player1) assertEquals(p.hand.size(), 6);
        }
        System.out.println(player1);
    }

    @Test
    public void testValuesCutpurse() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayCutpurse() {
        player1.initializePlayerTurn();
        player2.initializePlayerTurn();
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        assertEquals(player1.coins, 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Cutpurse));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 1);
        assertEquals(player1.coins, 2);
        for(Player p : state.players){
            if(p != player1) assertEquals(p.hand.size(), 4);
        }
        System.out.println(player1);
    }

    @Test
    public void testValuesEmbargo() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Embargo).getCost(), 2);
        assertEquals(Card.getCard(cards, Card.CardName.Embargo).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Embargo).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayEmbargo() {
        player1.initializePlayerTurn();
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        assertEquals(player1.coins, 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Embargo));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        assertEquals(player1.coins, 2);
//        assertTrue(state.embargoTokens.size() > 0);
        System.out.println(player1);
    }

    @Test
    public void testValuesFeast() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Feast).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayFeast() {
        player1.initializePlayerTurn();
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        assertEquals(player1.coins, 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Feast));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertTrue(player1.coins > 5);
        System.out.println(player1);
    }

    @Test
    public void testValuesGreatHall() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).score(), 1);
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayGreatHall() {
        player1.initializePlayerTurn();
        assertEquals(player1.numActions, 1);
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Great_Hall));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.hand.size(), 6);
        assertEquals(player1.deck.size(), 4);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 1);
        assertEquals(player1.numActions, 1);
        System.out.println(player1);
    }

    @Test
    public void testValuesMine() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Mine).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Mine).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Mine).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayMine() {
        player1.initializePlayerTurn();
        List<Card> before = Card.filter(player1.hand, Card.Type.TREASURE);
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Mine));
        System.out.println(player1);
        player1.playKingdomCard();
        List<Card> after = Card.filter(player1.hand, Card.Type.TREASURE);
        assertEquals(player1.hand.size(), 4);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 1);
        assertEquals(player1.playedCards.size(), 1);
        assertTrue(before.size() != after.size());
        assertTrue(Card.getCard(player1.discard, Card.CardName.Silver) != null);
        assertTrue(Card.getCard(player1.discard, Card.CardName.Gold) == null);
        System.out.println(player1);
    }

    @Test
    public void testValuesRemodel() {
        //assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(), "Copper"); //String<Copper> vs CardName<Copper>?
        assertEquals(Card.getCard(cards, Card.CardName.Remodel).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Remodel).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Remodel).getType(), Card.Type.ACTION);
    }

    @Test
    public void testPlayRemodel() {
        player1.initializePlayerTurn();
        assertEquals(player1.hand.size(), 5);
        assertEquals(player1.deck.size(), 5);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.playedCards.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Remodel));
        System.out.println(player1);
        player1.playKingdomCard();
        assertEquals(player1.deck.size(), 5);
        assertTrue(player1.coins > 2);
        System.out.println(player1);
    }

}