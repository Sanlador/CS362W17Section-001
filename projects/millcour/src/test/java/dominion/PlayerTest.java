package dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void test() {
		Player p1 = new Player();
		assertEquals(p1.getNumActions(), 1);
		assertEquals(p1.getNumBuys(), 1);
		assertEquals(p1.getVictory(), 0);
		assertEquals(p1.getCoins(), 0);
		assertEquals(p1.getHand().getName(), Card.CardName.Hand);
	}

	@Test
	public void setterTest(){
			Player p1 = new Player();
			p1.setNumActions(3);
			assertEquals(p1.getNumActions(), 3);
			p1.setNumBuys(5);
			assertEquals(p1.getNumBuys(), 5);
			p1.setCoins(2);
			assertEquals(p1.getCoins(), 2);
			p1.setVictory(8);
			assertEquals(p1.getVictory(), 8);
	}
	
	@Test
	public void coinsTest(){
		Player p1 = new Player();
		p1.getHand().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getHand().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getHand().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.totalCoins();
		assertEquals(p1.getCoins(), 3);
	}
	
	@Test
	public void turnTest(){
		Player p1 = new Player();
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Smithy, Card.Type.ACTION, 4, 0, 0));
		p1.getDraw().getPile().add(new Card(Card.CardName.Estate, Card.Type.TREASURE, 2, 1, 0));
		p1.getDraw().getPile().add(new Card(Card.CardName.Estate, Card.Type.TREASURE, 2, 1, 0));
		p1.getDraw().getPile().add(new Card(Card.CardName.Estate, Card.Type.TREASURE, 2, 1, 0));
		p1.takeTurn();
		assertEquals(p1.getNumActions(), 0);
		assertEquals(p1.getNumBuys(), 0);
	}
	
	@Test
	public void resetTest(){
		Player p1 = new Player();
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Smithy, Card.Type.ACTION, 4, 0, 0));
		p1.getHand().getPile().add(new Card(Card.CardName.Estate, Card.Type.TREASURE, 2, 1, 0));
		p1.getHand().getPile().add(new Card(Card.CardName.Estate, Card.Type.TREASURE, 2, 1, 0));
		p1.getHand().getPile().add(new Card(Card.CardName.Estate, Card.Type.TREASURE, 2, 1, 0));
		p1.setNumActions(7);
		p1.setNumBuys(9);
		p1.setCoins(10);
		p1.resetPlayer();
		assertEquals(p1.getCoins(), 0);
		assertEquals(p1.getNumActions(), 1);
		assertEquals(p1.getNumBuys(), 1);
		assertEquals(p1.getHand().getPile().size(), 5);
	}
	
	@Test
	public void discardToDrawTest(){
		Player p1 = new Player();
		p1.getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.getDraw().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		p1.drawHand();
	}
}
