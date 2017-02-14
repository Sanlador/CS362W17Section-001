package dominion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class GameStateTest{
  private GameState g;
  private Player a;
  private Player b;

  @Before
  public void initPlayer(){
    g = new GameState();
  }

  @Test
  public void testAddPlayer(){
    assertEquals(g.numPlayers, 0);
    g.addPlayer("Amy");
    assertEquals(g.numPlayers, 1);
    Player b = new Player("Benny", g, true);
    g.addPlayer(b);
    assertEquals(g.numPlayers, 2);
    assertFalse(g.addPlayer("Charlie", true));
    assertEquals(g.numPlayers, 2);
  }

  @Test
  public void testCheckEndConditions(){
    g.addPlayer("Amy");
    assertFalse(g.checkEndConditions());
    for(int i=0; i<12; i++){
      g.takeCard(Card.DUCHY);
      g.takeCard(Card.MINE);
      g.takeCard(Card.BARON);
    }
    assertTrue(g.checkEndConditions());
    g.addCard(Card.MINE);
    assertFalse(g.checkEndConditions());
    for(int i=0; i<12; i++) g.takeCard(Card.PROVINCE);
    assertTrue(g.checkEndConditions());
  }

  @Test
  public void testTakeCard(){
    assertEquals(g.takeCard(Card.COPPER), Card.COPPER);
    for(int i=0; i<12; i++) g.takeCard(Card.DUCHY);
    assertEquals(g.takeCard(Card.DUCHY), null);
  }

  @Test
  public void testNextTurn(){
    g.addPlayer("Amy", true);
    g.addPlayer("Benny", true);
    for(int i=0; i<20; i++) g.nextTurn();
  }
}
