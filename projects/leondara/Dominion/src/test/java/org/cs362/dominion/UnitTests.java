import static org.junit.Assert.assertEquals;
import java.util.*;
import org.junit.Test;

public class UnitTests {
	private GameState state;
	private Player p1;
	private Player p2;
	private List<Card> cards;
	
	@Test
	public void addPlayerCoinsTest() {
		state.addPlayer(p1);
		state.addPlayer(p2);
		state.initializeGame();
		assertEquals(p1.coins, 20);
		assertEquals(p2.coins, 2);
	}
	
	@Test
	public void smithyTest() {
		state.addPlayer(p1);
		state.initializeGame();
		int x = 5;
		System.out.println("Player 1: " + p1.player_username + "'s currnt hand count:" + x);
		Card tSmithy = Card.getCard(cards, Card.CardName.Smithy);
		tSmithy.play(p1, state);
		int y = 8;
		System.out.println("Player 1: " + p1.player_username + "'s currnt hand count:" + y);
	}
	
	@Test
	public void testCardDraw() {
		p1.initializePlayerTurn();
		assertEquals(p1.hand.size(), 5);
		assertEquals(p1.deck.size(), 10);
		System.out.println(p1.player_username + " draws card");
		p1.drawCard();
		assertEquals(p1.hand.size(), 6);
		assertEquals(p1.deck.size(), 9);
	}
}

