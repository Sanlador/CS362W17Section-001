package goertzel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class GameStateTest {

    @Test
    public void testCompletePlay() {
        GameState board = new GameState(new String[]{"Player 1", "Player 2"});

        assertEquals(board.victory_supply.size(), 4);
        assertEquals(board.treasure_supply.size(), 3);
        assertEquals(board.kingdom_supply.size(), 10);
        for (int i = 0; i < 4; i++)
            assertEquals(false, board.victory_supply.get(i).isEmpty());
        for (int i = 0; i < 3; i++)
            assertEquals(false, board.treasure_supply.get(i).isEmpty());
        for (int i = 0; i < 10; i++)
            assertEquals(false, board.kingdom_supply.get(i).isEmpty());


        board.play();
        assertEquals(true, board.getWinners().size() >= 1);
        assertEquals(true, board.getWinners().size() <= 2);
    }

    @Test
    public void testAddPlayers() {
        GameState board = new GameState();
        assertEquals(0, board.players.size());
        assertEquals(null, board.getWinners());
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        board.addPlayer(p1);
        assertEquals(1, board.players.size());
        board.addPlayer(p2);
        assertEquals(2, board.players.size());
        assertEquals("Player 1", board.players.get(0).getPlayerName());
        assertEquals("Player 2", board.players.get(1).getPlayerName());

        assertEquals(board, p1.boardReference);
        assertEquals(board, p2.boardReference);

        assertEquals(10, p1.getCardCount());
        assertEquals(10, p2.getCardCount());
    }

    @Test
    public void testOneTurn() {
        GameState board = new GameState(new String[]{"Player 1", "Player 2"});
        assertEquals(0, board.getTurnCount());
        board.playOneTurn();
        assertEquals(2, board.getTurnCount());
        assertEquals(false, board.gameOver());
        assertEquals(null, board.getWinners());

        board.playOneTurn();
        assertEquals(3, board.getTurnCount());
        assertEquals(false, board.gameOver());
        assertEquals(null, board.getWinners());
    }

}