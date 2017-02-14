package dominion;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	Board plateau;

	@Before
	public void initialize() {
		plateau = new Board(2);
	}

	@Test
	public void testGiveCard() {
		int i;
		int number;
		for (i = 0; i < 17; i++) {
			number = plateau.pioche_number[i];
			assertEquals(plateau.giveCard(i), plateau.pioche_card[i]);
			assertEquals(plateau.pioche_number[i], number - 1);
		}
		assertEquals(plateau.giveCard(55).id, -1);
		assertEquals(plateau.giveCard(-14).id, -1);
		for (i = 0; i < 100; i++) {
			plateau.giveCard(8);

		}
		assertEquals(plateau.giveCard(8).id, -1);
	}

	@Test
	public void testSetEmbargo() {
		assertEquals(plateau.embargo[5], 0);
		plateau.setEmbargo(5);
		plateau.setEmbargo(5);
		assertEquals(plateau.embargo[5], 2);
	}

	@Test
	public void testToDraw() {
		assertEquals(plateau.getToDraw(0), 0);
		plateau.setToDraw(0, 4);
		assertEquals(plateau.getToDraw(0), 4);
	}

	@Test
	public void testToTrash() {
		assertEquals(plateau.getTrash()[0], 0);
		plateau.setTrash(0, 2);
		assertEquals(plateau.getTrash()[0], 2);
		plateau.setTrash(0, 2);
		assertEquals(plateau.getTrash()[0], 2);
	}

	@Test
	public void testGamedone() {
		assertEquals(plateau.gameDone(), false);
		plateau.pioche_number[0] = 0;
		plateau.pioche_number[1] = 0;
		plateau.pioche_number[2] = 0;
		assertEquals(plateau.gameDone(), true);
	}

	@Test
	public void testGetAvai() {
		assertEquals(10, plateau.getAvailability(12));
	}

	@Test
	public void testGetCards() {
		assertEquals(plateau.getCards()[1], plateau.pioche_card[1]);
	}

	@Test
	public void testGetCardName() {
		assertEquals(plateau.getCardName(0), "copper");
	}
}