package dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dominion.Card.CardName;
import dominion.Card.Type;

public class PlayerTest {

	private static ArrayList<Card> cards;
	private static ArrayList<Card> cards1;
	
	private static GameState gs;
	private static GameState gs1;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		cards = new ArrayList<Card>(Card.createCards());
		gs = new GameState(cards);
		Randomness.reset(10);
		Player player = new Player(gs, "player-1");
		gs.addPlayer(player);
		player = new Player(gs, "player-2");
		gs.addPlayer(player);		      
		//Initialize the game!
		gs.initializeGame();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWholeGame() {
		
		HashMap<Player, Integer> winners=gs.play();
		 for(Player p: winners.keySet()){
		    	System.out.println ("Player name: "+ p.player_username + " , Score: "+ winners.get(p) );
		    }
		    
		 System.out.println(gs.toString());
		 
		 assertTrue(gs.empty_piles >= 1);
		 if(gs.empty_piles == 1)
			 assertTrue(gs.gameBoard.get(Card.getCard(gs.cards, Card.CardName.Province)) == 0);
		
		/* Player p = gs.players.get(0);
		
		p.numActions = 12;
		
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Council_Room));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Mine));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Cutpurse));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Steward));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Great_Hall));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Gardens));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Ambassador));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Adventurer));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Baron));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Embargo));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Village));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Smithy));
		p.hand.add(Card.getCard(gs.cards, Card.CardName.Feast));
		
	
		int discard_before = p.discard.size();
		p.playKingdomCard();
		assertEquals("Card pulled and put into ", p.discard.size(), discard_before + 1);
	*/
	}
	
	@Test
	public void testPlayKingdomCardsBranchesTest(){
		cards1 = new ArrayList<Card>(Card.createCards());
		gs1 = new GameState(cards1);
		Randomness.reset(10);
		Player player = new Player(gs1, "player-1");
		gs1.addPlayer(player);
		player = new Player(gs1, "player-2");
		gs1.addPlayer(player);		      
		//Initialize the game!
		gs1.initializeGame();
		
		 Player p = gs1.players.get(0);

		 p.hand.add(Card.getCard(gs1.cards, Card.CardName.Mine));
		 p.playKingdomCard();
		 p.playTtreasureCard();
		 
		 assertTrue(p.coins >= 2);
		 assertTrue(p.hand.size() == 5);
		 p.endTurn();
		 
		 p.hand.add(Card.getCard(gs1.cards, Card.CardName.Baron));
		 p.playKingdomCard();
		 assertTrue(p.coins >= 4);
		 p.endTurn();
		 int size = p.hand.size();
		 assertEquals(size, 5);
		 
	}
	
	@Test
	public void testPlayTreasureCardsBranchesTest(){
		Player p = gs.players.get(1);
		Card c = Card.getCard(gs.cards, Card.CardName.Copper);
		p.hand.add(c);
		p.hand.add(c);
		p.hand.add(c);
		p.hand.add(c);
		
		p.playTtreasureCard();
		int cscore = 0;
		
		for(Card d : p.hand){
			cscore += d.getTreasureValue();
		}
		p.buyCard();
		assertEquals(p.coins, cscore);
	}
	
	@Test
	public void testBuyCardsBranchesTest(){
		Player p = gs.players.get(1);
		p.endTurn();
		p.hand.clear();
		Card c = Card.getCard(gs.cards, Card.CardName.Smithy);
		p.hand.add(c);
		p.playKingdomCard();
		assertTrue(!p.playedCards.isEmpty());
		
	}
	
	
}
