package dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import dominion.Card.Type;

public class CouncilRoomTest {


	public static HashMap<Card, Integer> gameBoard = new HashMap<Card, Integer>();	
	List<Card> cards =new ArrayList<Card>(Card.createCards());
	
	Player p = new Player(null, "shadows");
	Player j = new Player(null, "Jay");
	GameState game = new GameState(cards); 
	
	
	
	
	
	@Test
	public void test() {
		game.addPlayer(p);
		game.addPlayer(j);
		game.initializeGame();
		int x = p.hand.size();
		int n = p.numBuys;
		System.out.println("hand size before = " + x);
		System.out.println("buys before = " + n);
		Card temp = Card.getCard(cards, Card.CardName.council_room);
		temp.play(p, game);
		int y = p.hand.size();
		int b = p.numBuys;
		System.out.println("hand size after = " + y);
		System.out.println("buys after = " + b);
	}

}