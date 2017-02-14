package Dominion;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PlayerTest1
{
	@Test
	public void testGame()
	{
		Play.playGame(0);
	}

	@Test
	public void testGetMoney() 
	{
		Player p = new Player(1);
		p.discardAll(p.hand);
		p.hand.add(new Card("Copper"));
		assertEquals("One copper", 1, p.getMoney());
		p.hand.add(new Card("Silver"));
		assertEquals("One copper, one silver", 3, p.getMoney());
		p.hand.add(new Card("Gold"));
		assertEquals("One copper, one silver, one gold", 6, p.getMoney());
		
	}

	@Test
	public void testTurn() 
	{
		Player p = new Player(1);
		Board b = new Board();
		p.turn(b, "");
	}

	@Test
	public void testShuffle() 
	{
		int cop = 0;
		int es = 0;
		boolean same = true;
		Player p = new Player(1);
		List<Card> c = new ArrayList();
		List<Card> test = new ArrayList();
		for (int i = 0; i < 10; i++)
			c.add(new Card("Copper"));
		for (int i = 0; i < 10; i++)
			c.add(new Card("Estate"));
		

		for (int i = 0; i < 10; i++)
			test.add(new Card("Copper"));
		for (int i = 0; i < 10; i++)
			test.add(new Card("Estate"));
		
		List<Card> s = p.shuffle(c);
		
		for (int i = 0; i < 20; i++)
		{
			if (s.get(i).getName() != test.get(i).getName())
				same = false;
		}
		
		for (int i = 0; i < 20; i++)
		{
			if (s.get(i).getName() == "Copper")
				cop++;
			if (s.get(i).getName() == "Estate")
				es++;
		}
		assert(same == false);
		assert(s.size() == 20);
		assert(es == 10);
		assert(cop == 10);
		
	}

	@Test
	public void testPlayer() 
	{
		Player p = new Player(1);
		assert(p.playerNum == 1);
		assert(p.hand.size() == 5);
		for (int i = 0; i < 5; i++)
			assert(p.hand.get(i).getName() == "Copper" || p.hand.get(i).getName() == "Estate");
	}

	@Test
	public void testDrawDeck() 
	{
		Player p = new Player(1);
		List<Card> h = new ArrayList();
		List<Card> d = new ArrayList();
		
		d.add(new Card("Duchy"));
		d.add(new Card("Province"));
		d.add(new Card("Silver"));
		
		p.deck = d;
		p.hand = h;
		
		p.drawDeck(3);
		
		assert(p.hand.get(0).getName() == "Duchy");
		assert(p.hand.get(1).getName() == "Province");
		assert(p.hand.get(2).getName() == "Silver");
		
		assertEquals("d should be empty", 0, d.size());
	}

	@Test
	public void testDiscard() 
	{
		Player p = new Player(1);
		List<Card> h = new ArrayList();
		List<Card> d = new ArrayList();
		p.hand = h;
		p.discard = d;
		p.hand.add(new Card("Copper"));
		Player.readCards(p.discard);
		assert(p.discard.isEmpty() == true);
		
		p.discard(new Card("Copper"));
		assert (p.hand.isEmpty());
		assert (p.discard.size() == 1);
		assert (p.discard.get(0).getName() == "Copper");
	}

	@Test
	public void testDiscardAll() 
	{
		Player p = new Player(1);
		Board b = new Board();
		List<Card> d = new ArrayList();

		for (int i = 0; i < p.hand.size(); i++)
			d.add(p.hand.get(i));
		
		assert(p.hand.size() > 0);
		p.discardAll(p.hand);
		assertEquals("Hand should be empty", 0, p.hand.size());

		for (int i = 0; i < p.discard.size(); i++)
			assert(d.get(i).getName() == p.discard.get(i).getName());
	}

	@Test
	public void testGain() 
	{
		Player p = new Player(1);
		Board b = new Board();
		assert(!Card.search(new Card("Smithy", 4), p.hand));
		p.gain(new Card("Smithy", 4), b.supply);
		assert(Card.search(new Card("Smithy", 4), p.hand));
	}

	@Test
	public void testTrash() 
	{
		List<Card> testSupply = new ArrayList();
		List<Card> hand = new ArrayList();
		Player p = new Player(1);
		p.hand = hand;
		p.hand.add(new Card("Silver"));
		assert(Card.search(new Card("Silver"), p.hand));
		p.trash(new Card("Silver"));
		assert(!Card.search(new Card("Silver"), p.hand));
	}

	@Test
	public void testPlayK()
	{
		List<Card> testSupply = new ArrayList();
		List<Card> hand = new ArrayList();
		Player p = new Player(1);
		Board b = new Board();
		
		hand.add(new Card("Smithy", 4));
		
		p.hand = hand;
		p.playK("Smithy", b);

		assertEquals("Hand should contain three cards", 3, p.hand.size());
	}

}
