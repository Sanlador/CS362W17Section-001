package dominion;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CardTest {
	Player player1, player2;
	Board plateau;

	@Before
	public void initialize() {
		plateau = new Board(2);
		player1 = new Player(plateau, 1);
		player2 = new Player(plateau, 2);
	}

	@Test
	public void testValue() {
		Card carte = new Card(1);
		assertEquals(carte.valeur, 1);
		assertEquals(carte.prix, 0);
		carte = new Card(2);
		assertEquals(carte.valeur, 2);

	}

	@Test
	public void testPrix() {
		assertEquals(plateau.pioche_card[0].prix, 0);
		assertEquals(plateau.pioche_card[1].prix, 3);

	}

	@Test
	public void testName() {
		assertEquals(plateau.pioche_card[0].getNom(), "copper");
	}

	@Test
	public void testId() {
		Card carte = new Card(3);
		assertEquals(3, carte.id);
		carte = new Card(14);
		assertEquals(14, carte.id);

	}

	@Test
	public void testType() {
		Card carte = new Card(2);
		assertEquals(carte.type, "tresor");
		assertEquals(carte.nom, "silver");
	}

	@Test
	public void testExtraCards() {
		Card carte = new Card(18);
		assertEquals(carte.nom, "village");
		assertEquals(carte.type, "action");
		assertEquals(carte.valeur, 0);

		carte = new Card(19);
		assertEquals(carte.nom, "smithy");
		assertEquals(carte.type, "action");
		assertEquals(carte.valeur, 0);
		carte = new Card(20);
		assertEquals(carte.nom, "salvager");
		assertEquals(carte.type, "action");
		assertEquals(carte.valeur, 0);
	}

	@Test
	public void testType2() {
		int i;
		Card carte;
		carte = new Card(1);
		assertEquals("tresor", carte.type);
		carte = new Card(12);
		assertEquals("action", carte.type);
		carte = new Card(15);
		assertEquals("victory", carte.type);
		carte = new Card(4);
		assertEquals("victory", carte.type);
		carte = new Card(2);
		assertEquals("tresor", carte.type);
	}

	@Test
	public void testCurseBug() {
		Card curse = new Card(7);
		assertEquals("curse", curse.type);
	}

	@Test
	public void testBugAventurer() {
		Card adventurer = new Card(8);
		assertEquals(0, adventurer.getPointV());
	}
}