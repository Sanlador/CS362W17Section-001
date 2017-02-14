import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

public class PlayerTest {
    
    @Test
    public void testConstructor(){
        Player player = new Player(true);
        assertEquals(true,player.isBot);
        assertEquals(10,player.numCards);
    }
    
    @Test
    public void testCardDraw(){
        GameState game = new GameState(2);
        assertEquals(5,game.players[0].hand.size());
        assertEquals(5,game.players[0].deck.size());
        game.players[0].hand.addAll(game.players[0].drawCards(3,game.rand));
        assertEquals(8,game.players[0].hand.size());
        assertEquals(2,game.players[0].deck.size());
    }
}