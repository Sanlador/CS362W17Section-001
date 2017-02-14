package dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import static org.junit.Assert.*;


public class TestCard{
	private GameState g;
	private Player player1, player2;
	private List<Card> cards;

	@Before
	public void initializeGame(){
		cards = new ArrayList<Card>(Card.createCards());
		g = new GameState(cards);
		player1 = new Player(g, "Player1");
		g.addPlayer(player1);

		player2 = new Player(g, "Player2");
		g.addPlayer(player2);

		g.initializeGame();
	}

	// SMITHY TESTS//
	 @Test
	 public void SmithyTest(){

		 // Test playing the Smithy card
		 //System.out.println(player1);
		 assertEquals(player1.hand.size(), 5);
		 assertEquals(player1.deck.size(), 5);
		 assertEquals(player1.playedCards.size(), 0);

		 player1.hand.add(Card.getCard(cards, Card.CardName.Smithy));
		 player1.playKingdomCard();

		 assertEquals(player1.hand.size(), 8);
		 assertEquals(player1.deck.size(), 2);
		 assertEquals(player1.playedCards.size(), 1);

		 // Testing the value of the Smithy card
		 assertEquals(Card.getCard(cards, Card.CardName.Smithy).getCost(), 4);
		 assertEquals(Card.getCard(cards, Card.CardName.Smithy).getType(), Card.Type.ACTION);

	 }
	 @Test
	 public void AdventurerTest(){

		 //Test playing the Adventurer card
		 assertEquals(player1.deck.size(), 5);
		 assertEquals(player1.hand.size(), 5);

		 player1.hand.add(Card.getCard(cards, Card.CardName.Adventurer));
		 player1.playKingdomCard();

		 assertTrue(player1.hand.size() >= 6);
	 }
	 @Test
	 public void Ambassador(){

		 //Test playing the Ambassador card
		 assertEquals(player1.hand.size(), 5);
		 assertEquals(player1.deck.size(), 5);

		 assertEquals(player2.discard.size(), 0);

		 player1.hand.add(Card.getCard(cards, Card.CardName.Ambassador));
		 player1.playKingdomCard();

		 assertEquals(player1.hand.size(), 4);
		 assertEquals(player1.deck.size(), 5);

		 assertEquals(player2.discard.size(), 1);
	 }
	 @Test
	 public void Baron(){

		 //Test playing the Baron card
		 assertEquals(player1.numBuys, 1);
		 assertEquals(player1.hand.size(), 5);
		 assertEquals(player1.coins, 0);

		 player1.hand.add(Card.getCard(cards, Card.CardName.Baron));
		 if(Card.getCard(player1.hand, Card.CardName.Estate) != null){
					player1.playKingdomCard();
					assertEquals(player1.hand.size(), 4);
					assertEquals(player1.numBuys, 2);
					assertEquals(player1.coins, 4);
			}
			else {
					player1.playKingdomCard();
					assertEquals(player1.hand.size(), 5);
					assertEquals(player1.coins, 0);
					player1.hand.add(Card.getCard(cards, Card.CardName.Embargo));
					player1.playKingdomCard();
					assertEquals(player1.coins, 2);

			}
	 }
	 @Test
	 public void Council_Room(){

		 // Test playing the Council Room card
		 assertEquals(player1.hand.size(), 5);
		 assertEquals(player1.numBuys, 1);

		 player1.hand.add(Card.getCard(cards, Card.CardName.Council_Room));
		 player1.playKingdomCard();

		 assertEquals(player1.hand.size(), 9);
		 assertEquals(player1.numBuys, 2);

		 for(Player p : g.players){
			 if(p != player1) {
				 assertEquals(p.hand.size(), 6);
			 }
		 }
	 }
	 @Test
	 public void Cutpurse(){

		 //Testing playing the Cutpurse card
		 assertEquals(player1.hand.size(), 5);

		 player1.hand.add(Card.getCard(cards, Card.CardName.Cutpurse));
		 player1.playKingdomCard();

		 assertEquals(player1.hand.size(), 5);
		 for(Player p : g.players){
			 if(p != player1){
				 assertEquals(p.hand.size(), 4);
			 }
		 }
	 }
	 @Test
	 public void Embargo(){
		 assertEquals(player1.hand.size(), 5);
		 assertEquals(player1.coins, 0);
		 player1.hand.add(Card.getCard(cards, Card.CardName.Embargo));
		 player1.playKingdomCard();
		 assertEquals(player1.hand.size(), 5);
		 assertEquals(player1.coins, 2);
	 }
	 @Test
	 public void Feast(){
		 assertEquals(player1.hand.size(), 5);
		 assertEquals(player1.deck.size(), 5);

		 assertEquals(player1.coins, 0);
		 player1.hand.add(Card.getCard(cards, Card.CardName.Feast));

		 player1.playKingdomCard();

		 assertEquals(player1.hand.size(), 5);
	 }
	 @Test
	 public void Gardens(){
		 assertEquals(player1.hand.size(), 5);

		 player1.hand.add(Card.getCard(cards, Card.CardName.Great_Hall));
		 player1.playKingdomCard();

		 assertEquals(player1.hand.size(), 6);
	 }
	 @Test
	 public void Great_Hall(){
		  assertEquals(player1.hand.size(), 5);
			assertEquals(player1.numActions, 1);

			player1.hand.add(Card.getCard(cards, Card.CardName.Great_Hall));
			player1.playKingdomCard();

			assertEquals(player1.hand.size(), 6);
			assertEquals(player1.numActions, 1);
	 }
	 @Test
	 public void Mine(){
		 List<Card> before = Card.filter(player1.hand, Card.Type.TREASURE);
		 assertEquals(player1.hand.size(), 5);
		 assertEquals(player1.discard.size(), 0);
		 player1.hand.add(Card.getCard(cards, Card.CardName.Mine));
		 player1.playKingdomCard();

		 List<Card> after = Card.filter(player1.hand, Card.Type.TREASURE);
		 assertEquals(player1.hand.size(), 4);
		 assertEquals(player1.discard.size(), 1);
		 assertTrue(before.size() != after.size());
	 }
	 @Test
	 public void Salvager(){
		 assertEquals(player1.numBuys, 1);
		 assertEquals(player1.coins, 0);

		 player1.hand.add(Card.getCard(cards, Card.CardName.Salvager));
		 player1.playKingdomCard();

		 assertEquals(player1.numBuys, 2);

	 }
	 @Test
	 public void Village(){
		 assertEquals(player1.hand.size(), 5);
		 assertEquals(player1.numActions, 1);

		 player1.hand.add(Card.getCard(cards, Card.CardName.Village));
		 player1.playKingdomCard();

		 assertEquals(player1.hand.size(), 6);
		 assertEquals(player1.numActions, 2);
	 }

}
