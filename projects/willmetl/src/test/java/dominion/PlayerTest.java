package dominion;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class PlayerTest{
  private GameState g;
  private Player a;
  private Player b;

  @Before
  public void initPlayer(){
    g = new GameState();
  }

  @Test
  public void testChooseHand(){
    Player a = new Player("Amy", g, true);
    for(int i=0; i<5; i++){
      assertNotNull(a.chooseHand());
    }
    assertNull(a.chooseHand());
  }

  @Test
  public void testNewPlayer(){
    Player a = new Player("Amy", g);
    assertEquals(a.getActions(), 1);
    assertEquals(a.getBuys(), 1);
    assertEquals(a.getHandSize(), 5);
    assertEquals(a.getMoney(), 0);
    assertEquals(a.getName(), "Amy");
    assertEquals(a.countAllCards(), 10);
    assertEquals(a.countVictoryPoints(), 3);
  }

  @Test
  public void testDiscardFromHand(){
    Player a = new Player("Amy", g);
    assertTrue(a.discardFromHand(Card.COPPER));
    assertEquals(a.getHandSize(), 4);
    assertTrue(a.discardFromHand());
    assertEquals(a.getHandSize(), 3);
    assertTrue(a.discardFromHand());
    assertTrue(a.discardFromHand());
    assertEquals(a.getHandSize(), 1);
  }

  @Test
  public void testReturnCardToShared(){
    Player a = new Player("Amy", g);
    assertTrue(a.returnCardToShared(Card.COPPER));
  }

  @Test
  public void testTakeFreeCard(){
    Player a = new Player("Amy", g);
    for(int i=0; i<10; i++) assertEquals(Card.CURSE, g.takeCard(Card.CURSE));
    assertFalse(a.takeFreeCard(Card.CURSE));
  }

  @Test
  public void testPlayCard(){
    Player a = new Player("Amy", g);
    assertTrue(a.playCard(Card.EMBARGO));
    a.addActions(-1);
    assertFalse(a.playCard(Card.EMBARGO));
    a.putInHand(Card.VILLAGE);
  }
}
