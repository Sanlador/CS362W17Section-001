import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Zaengle on 2/12/2017.
 */
public class KingdomTest {
    GameState kingState;
    List<Card> cards;
    Player p1;
    Player p2;

    @Before
    public void newMaterial(){
        cards = new ArrayList<Card>(Card.createCards());
        kingState = new GameState(cards);
        p1 = new Player(kingState, "Player 1");
        p2 = new Player(kingState, "Player 2");
        kingState.addPlayer(p1);
        kingState.addPlayer(p2);
        kingState.initializeGame();
    }

    @Test
    public void ambassadorTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Ambassador).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Ambassador).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Ambassador).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Ambassador).getCost(), 3);

        p1.initializePlayerTurn();
        p2.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, Card.CardName.Ambassador));
        p1.playKingdomCard();
        assertEquals(p1.playedCards.size(), 1); //at least one card was played
    }

    @Test
    public void adventurerTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getCost(), 6);

        p1.initializePlayerTurn();
        p2.initializePlayerTurn();
        p1.deck.add(Card.getCard(cards, Card.CardName.Gold));
        p1.hand.add(Card.getCard(cards, Card.CardName.Adventurer));

        p1.playKingdomCard();
        assertEquals(p1.playedCards.size(), 1); //at least one card was played
        assertTrue(p1.hand.size() > 5); //cards were added to the hand
    }

    @Test
    public void villageTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Village).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Village).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Village).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Village).getCost(), 3);

        kingState.players.get(1).initializePlayerTurn();
        kingState.players.get(1).hand.add(Card.getCard(cards, Card.CardName.Village));

        kingState.players.get(1).playKingdomCard();


    }

    @Test
    public void baronTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Baron).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Baron).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Baron).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Baron).getCost(), 4);

        p1.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, Card.CardName.Baron));
        p1.playKingdomCard();

        assertTrue(p1.discard.size() <= 1);

    }

    @Test
    public void Council_RoomTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getCost(), 5);

        //Council room is having the same problem as Village
        p1.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, Card.CardName.Council_Room));
        p1.playKingdomCard();

        assertTrue(p1.discard.size() <= 1);

    }

    @Test
    public void cutpurseTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).getCost(), 4);

        p1.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, Card.CardName.Cutpurse));
        p1.playKingdomCard();

        assertEquals(p1.coins, 4);
        assertTrue(p1.discard.size() <= 1);

    }

    @Test
    public void EmbargoTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Embargo).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Embargo).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Embargo).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Embargo).getCost(), 2);

        p1.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, Card.CardName.Embargo));
        p1.playKingdomCard();

        assertEquals(p1.coins, 2);
        assertTrue(p1.discard.size() <= 1);

    }

    @Test
    public void feastTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Feast).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getCost(), 4);

        p1.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, Card.CardName.Feast));
        p1.playKingdomCard();

        assertTrue(p1.discard.size() <= 1);

    }

    @Test
    public void GardensTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Gardens).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Gardens).score(), 1);
        assertEquals(Card.getCard(cards, Card.CardName.Gardens).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Gardens).getCost(), 4);

        p1.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, Card.CardName.Gardens));
        p1.playKingdomCard();

        assertTrue(p1.discard.size() <= 1);

    }

    @Test
    public void Great_HallTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).score(), 1);
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getCost(), 3);

        //has same problem as council room and village
        p1.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, Card.CardName.Great_Hall));
        p1.playKingdomCard();

        assertTrue(p1.discard.size() <= 1);

    }

    @Test
    public void MineTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Mine).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Mine).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Mine).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Mine).getCost(), 5);

        //has same problem as council room and village
        p1.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, Card.CardName.Mine));
        p1.playKingdomCard();
        p1.hand.add(1, Card.getCard(cards, Card.CardName.Silver));

        p1.hand.add(Card.getCard(cards, Card.CardName.Mine));
        p1.numActions +=1;
        p1.playKingdomCard();

        assertTrue(p1.discard.size() <= 2);

    }

    @Test
    public void Sea_HagTest(){
        assertEquals(Card.getCard(cards, Card.CardName.Sea_Hag).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Sea_Hag).score(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Sea_Hag).getTreasureValue(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Sea_Hag).getCost(), 4);

        //has same problem as council room and village and Great hall
        p1.initializePlayerTurn();
        p1.hand.add(Card.getCard(cards, Card.CardName.Sea_Hag));
        p1.playKingdomCard();

        assertTrue(p1.discard.size() <= 1);

    }
}
