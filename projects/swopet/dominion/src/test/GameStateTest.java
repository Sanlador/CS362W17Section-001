import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

public class GameStateTest {
    @Test
    public void testConstructor(){
        int numPlayers = 2;
        GameState game = new GameState(2);
        assertEquals(2,game.numPlayers);
        assertEquals(10,game.kingdomCards.size());
        assertEquals(3,game.treasureCards.size());
        assertEquals(4,game.victoryCards.size());
    }
    
    @Test
    public void testTurn(){
        GameState game = new GameState(2);
        int whoseTurn = game.whoseTurn;
        assertEquals(false,game.turn());
        assertEquals((whoseTurn + 1)%game.numPlayers,game.whoseTurn);
    }
    
    @Test
    public void testGameOver(){
        GameState game1 = new GameState(2);
        while (!GameState.emptyPile(game1.victoryCards.get(0))){
            game1.victoryCards.get(0).remove(0);
        }
        assertEquals(true,GameState.emptyPile(game1.victoryCards.get(0)));
        assertEquals(true,game1.turn());
        
        GameState game2 = new GameState(2);
        for (int i = 0; i < 3; i++){
            while (!GameState.emptyPile(game2.kingdomCards.get(i))){
                game2.kingdomCards.get(i).remove(0);
            }
            assertEquals(true,GameState.emptyPile(game2.kingdomCards.get(i)));
        }
        assertEquals(true,game2.turn());
        
        GameState game3 = new GameState(2);
        for (int i = 0; i < 3; i++){
            while (!GameState.emptyPile(game3.victoryCards.get(i))){
                game3.victoryCards.get(i).remove(0);
            }
            assertEquals(true,GameState.emptyPile(game3.victoryCards.get(i)));
        }
        assertEquals(true,game3.turn());
    }
    
    @Test
    public void testPlayDom(){ //to give coverage to PlayDom
        PlayDom.main(new String[]{"2"});
    }
    
    @Test
    public void testWinner(){
        GameState game = new GameState(2);
        game.players[0].deck.add(new Province());
        game.players[0].numCards++;
        int [] winnerData = game.getWinner();
        assertEquals(0,winnerData[0]); //player ID
        assertEquals(9,winnerData[1]); //score
        assertEquals(0,winnerData[2]); //turns taken
    }
}