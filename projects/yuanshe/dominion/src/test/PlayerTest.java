package test;

import dominion.Player;
import dominion.GameState;
import dominion.Card;
import dominion.Card.Type;
import dominion.Card.CardName;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by [author] on 2/4/2017.
 */

public class PlayerTest {
    private Player player;
    @Before
    public void initGame()
    {
        GameState gameState = new GameState(Card.createCards());
        player = new Player(gameState, "Alex");
        player.gain(new Card(CardName.Gold, Type.TREASURE, 6, 0, 3));
        player.gain(new Card(CardName.Silver, Type.TREASURE, 3, 0, 2));
        player.gain(new Card(CardName.Cooper, Type.TREASURE, 0, 0, 1));
        player.gain(new Card(CardName.Province, Type.VICTORY, 8, 6, 0));
        player.gain(new Card(CardName.Duchy, Type.VICTORY, 5, 1, 0));
        player.initializePlayerTurn();
    }

    @Test
    public void gainTest()
    {
        assertTrue(player.gain(new Card(CardName.Estate, Type.VICTORY, 2, 3, 0)));
        assertTrue(player.gain(new Card(CardName.Curse, Type.VICTORY, 0, -1, 0)));
        assertTrue(player.gain(new Card(CardName.Adventurer, Type.ACTION, 6, 0, 0)));
        assertTrue(player.gain(new Card(CardName.Smithy, Type.ACTION, 4, 0, 0)));
        assertTrue(player.gain(new Card(CardName.Village, Type.ACTION, 3, 0, 0)));
    }

    @Test
    public void scoreFor()
    {
        assertEquals(7, player.scoreFor());
    }
}
