package dominion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class DominionTest{


  @Test
  public void initPlayer(){
    Dominion game = new Dominion();
    game.setupGame(true, true);
  }

}
