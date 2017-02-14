package dominion_v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class GameStateTest {
	List<Card> cards = new ArrayList<Card>(Card.createCards());
	GameState state = new GameState(cards);
	int random = (int)Randomness.random.nextInt(cards.size());
	Card tmp = cards.get(random);
	Player player1 = new Player(state, "player-1");
	Player player2 = new Player(state, "player-2");
	
	@Test
	public void testAddPlayer() {
		state.addPlayer(player1);
		state.addPlayer(player2);
		assert(state.players.size() == 2);
	}

	@Test
	public void testInitializeGame() {
		state.initializeGame(10);
		//System.out.println(GameState.gameBoard.size());
		assert(GameState.gameBoard.size() == 60);
	}
		

	@Test
	public void testPlay() {
		state.addPlayer(player1);
		state.addPlayer(player2);
		state.initializeGame(10);
		HashMap<Player, Integer> winners=state.play();
		assert(winners.get(player1) == player1.scoreFor());
		assert(winners.get(player2) == player2.scoreFor());
		
		
	}
}
