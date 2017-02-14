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

public class TestGameState{
  private GameState state;
  private Player player1;
  private Player player2;
  private List<Card> deckOfCards;

  @Before
  public void initializeGame() {
        deckOfCards = new ArrayList<Card>(Card.createCards());
        state = new GameState(deckOfCards);
  }

  @Test
  public void addingPlayer(){
    int numPlayers = 0;
    assertTrue(numPlayers == 0);
    player1 = new Player(state, "PLAYER 1");
    state.addPlayer(player1);
    for(Player p : state.players) {
          numPlayers++;
    }
    assertTrue(numPlayers == 1);
  }

  @Test
  public void initializingGame(){
    int numPlayers = 0;
    player1 = new Player(state, "PLAYER 1");
    state.addPlayer(player1);

    for(Player p : state.players) {
          numPlayers++;
    }
    assertTrue(numPlayers == 1);
    assertEquals(player1.hand.size(), 0);
    assertEquals(player1.discard.size(), 0);
    assertEquals(player1.deck.size(), 0);

    state.initializeGame();

    assertEquals(player1.hand.size(), 0);
    assertEquals(player1.discard.size(), 0);
    assertEquals(player1.deck.size(), 0);
  }
}
