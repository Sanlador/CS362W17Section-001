package dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class TestPlayer{
  private GameState state;
  private Player player1;
  private Player player2;
  private List<Card> deckOfCards;

  @Before
  public void initializingGame(){
    deckOfCards = new ArrayList<Card>(Card.createCards());
    state = new GameState(deckOfCards);
    player1 = new Player(state, "PLAYER 1");
    state.addPlayer(player1);
    player2 = new Player(state, "PLAYER 2");
    state.addPlayer(player2);
    state.initializeGame();
  }

  @Test
  public void drawingCard(){
    assertEquals(player1.hand.size(), 5);
    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.discard.size(), 0);

    player1.drawCard();

    assertEquals(player1.hand.size(), 6);
    assertEquals(player1.deck.size(), 4);
    assertEquals(player1.discard.size(), 0);
  }

  @Test
  public void initPlayer(){
    assertEquals(player1.hand.size(), 5);
    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.numActions, 1);
    assertEquals(player1.numBuys, 1);
    assertEquals(player1.coins, 0);

    player1.initializePlayerTurn();

    assertEquals(player1.hand.size(), 10);
    assertEquals(player1.deck.size(), 0);
    assertEquals(player1.numActions, 1);
    assertEquals(player1.numBuys, 1);
    assertEquals(player1.coins, 0);
  }

  @Test
  public void gainCard(){
    assertEquals(player1.hand.size(), 5);
    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.numActions, 1);
    assertEquals(player1.discard.size(), 0);
    assertEquals(player1.numBuys, 1);
    assertEquals(player1.coins, 0);

    player1.gain(Card.getCard(deckOfCards, Card.CardName.Smithy));

    assertEquals(player1.hand.size(), 5);
    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.numActions, 1);
    assertEquals(player1.discard.size(), 1);
    assertEquals(player1.numBuys, 1);
    assertEquals(player1.coins, 0);
  }

  @Test
  public void discard(){
    assertEquals(player1.hand.size(), 5);
    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.numActions, 1);
    assertEquals(player1.discard.size(), 0);
    assertEquals(player1.numBuys, 1);
    assertEquals(player1.coins, 0);

    player1.discard(Card.getCard(player1.hand, Card.CardName.Copper));

    assertEquals(player1.hand.size(), 4);
    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.numActions, 1);
    assertEquals(player1.discard.size(), 1);
    assertEquals(player1.numBuys, 1);
    assertEquals(player1.coins, 0);
  }

  @Test
  public void playKingdomCard(){
    assertEquals(player1.hand.size(), 5);
    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.numActions, 1);
    assertEquals(player1.discard.size(), 0);
    assertEquals(player1.numBuys, 1);
    assertEquals(player1.coins, 0);

    player1.hand.add(Card.getCard(deckOfCards, Card.CardName.Smithy));

    assertEquals(player1.hand.size(), 6);
    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.numActions, 1);
    assertEquals(player1.discard.size(), 0);
    assertEquals(player1.numBuys, 1);
    assertEquals(player1.coins, 0);
  }

  @Test
  public void treasureCard(){
    assertEquals(player1.hand.size(), 5);
    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.numActions, 1);
    assertEquals(player1.discard.size(), 0);
    assertEquals(player1.numBuys, 1);
    assertEquals(player1.coins, 0);
    player1.coins = player1.coins + 7;

    assertEquals(player1.coins, 7);

    player1.playTtreasureCard();
    player1.buyCard();

    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.discard.size(), 1);
  }

  @Test
  public void endTurn(){
    assertEquals(player1.hand.size(), 5);
    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.numActions, 1);
    assertEquals(player1.discard.size(), 0);
    assertEquals(player1.numBuys, 1);
    assertEquals(player1.coins, 0);

    player1.endTurn();

    assertEquals(player1.hand.size(), 0);
    assertEquals(player1.deck.size(), 5);
    assertEquals(player1.discard.size(), 5);
    assertEquals(player1.coins, 0);
  }
}
