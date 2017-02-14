package dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import dominion.Card.Type;

public class ChancellorTest {


	public static HashMap<Card, Integer> gameBoard = new HashMap<Card, Integer>();	
	List<Card> cards =new ArrayList<Card>(Card.createCards());
	
	Player p = new Player(null, "palpatine");
	Player j = new Player(null, "Jay");
	GameState game = new GameState(cards); 
	
	
	
	
	
	@Test
	public void test() {
		game.addPlayer(p);
		game.addPlayer(j);
		game.initializeGame();
		int x = p.coins;
		System.out.println("coins before = " + x);
		Card temp = Card.getCard(cards, Card.CardName.chancellor);
		temp.play(p, game);
		int y = p.coins;
		System.out.println("coins after = " + y);
	}

}