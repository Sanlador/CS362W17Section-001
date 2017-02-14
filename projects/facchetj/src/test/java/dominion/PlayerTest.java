package dominion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	Board plateau;
	Player joueur, joueur2;

	@Before
	public void initialize() {
		plateau = new Board(2);
		joueur = new Player(plateau, 0);
		joueur2 = new Player(plateau, 1);
	}

	@Test
	public void testDraw() {
		assertEquals(joueur.hand.size(), 0);
		joueur.draw();
		assertEquals(joueur.hand.size(), 1);
		int i;
		for (i = 0; i < 15; i++) {
			joueur.draw();
		}
		assertEquals(joueur.hand.size(), 10);
	}

	@Test
	public void testPlayEstateTreasure() {
		int stock;

		stock = joueur.getMoney();
		joueur.hand.add(new Card(1));
		joueur.play(joueur.hand.get(0));
		assertEquals(stock + 1, joueur.getMoney());
		stock = joueur.getMoney();
		joueur.hand.remove(0);
		joueur.hand.add(new Card(2));
		joueur.play(joueur.hand.get(0));
		joueur.hand.remove(0);
		assertEquals(stock + 2, joueur.getMoney());
		stock = joueur.getMoney();
		joueur.hand.add(new Card(3));
		joueur.play(joueur.hand.get(0));
		joueur.hand.remove(0);
		assertEquals(stock + 3, joueur.getMoney());
		joueur.hand.add(new Card(4));
		stock = joueur.getMoney();
		joueur.play(joueur.hand.get(0));
		joueur.hand.remove(0);
		assertEquals(joueur.getMoney(), stock);
		joueur.hand.add(new Card(5));
		joueur.play(joueur.hand.get(0));
		joueur.hand.remove(0);
		assertEquals(joueur.getMoney(), stock);
		joueur.hand.add(new Card(6));
		joueur.play(joueur.hand.get(0));
		joueur.hand.remove(0);
		assertEquals(joueur.getMoney(), stock);
		joueur.hand.add(new Card(7));
		joueur.play(joueur.hand.get(0));
		joueur.hand.remove(0);
		assertEquals(joueur.getMoney(), stock);

	}

	@Test
	public void testPlayAdventurerCards() {
		joueur.hand.add(new Card(8));
		int stock = joueur.hand.size();
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(joueur.hand.size(), stock + 2);
	}

	@Test
	public void testBaron() { // 10
		joueur.hand.add(new Card(4));
		joueur.hand.add(new Card(10));
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(joueur.getAchat(), 2);
		assertEquals(joueur.getMoney(), 4);
	}

	@Test
	public void test2baron() {
		joueur.hand.add(new Card(10));
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(joueur.getAchat(), 2);
		assertEquals(joueur.poubelle.size(), 1);
	}

	@Test
	public void testCouncil() {// 11
		joueur.hand.add(new Card(11));
		int draw = plateau.getToDraw(0);
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(draw, 0);
		assertEquals(plateau.getToDraw(1), 1);
	}

	@Test
	public void testCutpurse() {// 12
		joueur.hand.add(new Card(12));
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(joueur.getMoney(), 2);
	}

	@Test
	public void testembargo() {// 13
		joueur.hand.add(new Card(13));
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(joueur.getAchat(), 3);

	}

	@Test
	public void testfeast() {// 14
		joueur.hand.add(new Card(14));
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		joueur.poubelle.add(joueur.hand.get(joueur.hand.size() - 1));
		joueur.hand.remove(joueur.hand.size() - 1);
		assertEquals(joueur.poubelle.size(), 2);
	}

	@Test
	public void testGarden() {// 15
		joueur.hand.add(new Card(15));
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
	}

	@Test
	public void testgreat_hall() {// 16
		joueur.hand.add(new Card(16));
		int stock = joueur.deck.size();
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(joueur.deck.size(), stock - 1);
	}

	@Test
	public void testmine() {// 17
		joueur.hand.add(new Card(12));
		joueur.hand.add(new Card(2));
		joueur.hand.add(new Card(17));
		int stock = joueur.poubelle.size();
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(stock + 2, joueur.poubelle.size());
	}

	@Test
	public void testvillage() {// 18
		joueur.hand.add(new Card(18));
		int stock = joueur.deck.size();
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(joueur.deck.size(), stock - 1);
		assertEquals(joueur.getAction(), 3);
	}

	@Test
	public void testSmithy() {// 19
		joueur.hand.add(new Card(19));
		int stock = joueur.deck.size();
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(joueur.deck.size(), stock - 3);
	}

	@Test
	public void testSalvager() {// 20
		joueur.hand.add(new Card(1));
		joueur.hand.add(new Card(20));
		int i = joueur.getMoney();
		int y = joueur.hand.size();
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(i, joueur.getMoney() - 4);
		assertEquals(y, joueur.hand.size() + 1);
	}

	@Test
	public void testSalvager2() {
		joueur.hand.add(new Card(20));
		int i = joueur.getMoney();
		int y = joueur.hand.size();
		joueur.play(joueur.hand.get(joueur.hand.size() - 1));
		assertEquals(i, joueur.getMoney());
		assertEquals(y, joueur.hand.size());
	}

	@Test
	public void testPlayTresureCards() {
		joueur.hand.add(new Card(3));
		joueur.hand.add(new Card(3));
		joueur.hand.add(new Card(3));
		joueur.hand.add(new Card(13));
		joueur.playTreasure();
		assertEquals(joueur.getMoney(), 9);
	}

	@Test
	public void testPlayTreasureNoCard() {
		joueur.playTreasure();
		assertEquals(joueur.getMoney(), 0);
	}

	@Test
	public void testRemoveTreasureNoCard() {
		joueur.removeTreasure();
		assertEquals(joueur.poubelle.size(), 0);
	}

	@Test
	public void testRemoveTresureCards() {
		joueur.hand.add(new Card(3));
		joueur.hand.add(new Card(3));
		joueur.hand.add(new Card(3));
		joueur.hand.add(new Card(13));
		joueur.removeTreasure();
		assertEquals(joueur.poubelle.size(), 3);
	}

	@Test
	public void testShowPoubelle() {
		joueur.showPoubelle();
		joueur.poubelle.add(new Card(3));
		joueur.poubelle.add(new Card(3));
		joueur.poubelle.add(new Card(3));
		joueur.poubelle.add(new Card(13));
		joueur.showPoubelle();
		joueur.showHand();
		joueur.hand.add(new Card(3));
		joueur.hand.add(new Card(3));
		joueur.hand.add(new Card(3));
		joueur.hand.add(new Card(13));
		joueur.showHand();
		joueur.displayAction();
		joueur.showBoard();
	}

	@Test
	public void testGetScore() {
		int score = joueur.getScore();
		assertEquals(score, 3);
		joueur.poubelle.add(new Card(9));
		joueur.poubelle.add(new Card(9));
		joueur.poubelle.add(new Card(9));
		joueur.poubelle.add(new Card(9));
		score = joueur.getScore();
		assertEquals(score, 3);
	}

	@Test
	public void testAchat() {
		joueur.hand.add(new Card(1));
		joueur.hand.add(new Card(1));
		joueur.hand.add(new Card(1));
		joueur.hand.add(new Card(1));
		joueur.playTreasure();
		assertEquals(joueur.achat(2, plateau), true);
		assertEquals(joueur.achat(2, plateau), false);
		plateau.pioche_number[2] = 0;
		joueur.hand.add(new Card(1));
		joueur.hand.add(new Card(1));
		joueur.hand.add(new Card(1));
		joueur.hand.add(new Card(1));
		joueur.playTreasure();
		assertEquals(joueur.achat(2, plateau), false);
		assertEquals(joueur.achat(6, plateau), true);
	}

	@Test
	public void testClear() {
		joueur.hand.add(new Card(1));
		joueur.hand.add(new Card(1));
		joueur.hand.add(new Card(1));
		joueur.hand.add(new Card(1));
		joueur.defausse.add(new Card(1));
		joueur.defausse.add(new Card(1));
		joueur.defausse.add(new Card(1));
		joueur.defausse.add(new Card(1));
		assertEquals(0, joueur.poubelle.size());
		joueur.clear();
		assertEquals(joueur.defausse.size(), 0);
		assertEquals(joueur.hand.size(), 0);
		assertEquals(8, joueur.poubelle.size());

	}

	@Test
	public void testMix() {
		int taille = joueur.deck.size();
		joueur.mix();
		assertEquals(joueur.deck.size(), taille);
	}

	@Test
	public void testDrawNoCard() {
		while (joueur.deck.isEmpty() != true) {
			joueur.poubelle.add(joueur.deck.get(0));
			joueur.deck.remove(0);
		}
		joueur.draw();

	}

	@Test
	public void testGameTurn() {
		int i;
		for (i = 0; i < 100; i++) {
			plateau = new Board(2);
			joueur = new Player(plateau, 0);
			joueur.gameTurn();
		}
	}

	@Test
	public void testGardenbug() {
		joueur.hand.add(new Card(15));
		int score = joueur.getScore();
		assertEquals(5, score);
	}

	@Test
	public void testAmbassadorBug() {
		joueur.hand.add(new Card(9));
		int i;
		for (i = 0; i < 4; i++) {
			joueur.hand.add(new Card(1));
		}
		int size = joueur.hand.size();
		joueur.play(joueur.hand.get(0));
		assertTrue(size > joueur.hand.size());
	}
	/*
	 * @Test public void drawnoCardInDeck() { joueur.poubelle = joueur.deck;
	 * joueur.deck = new ArrayList<Card>(); int stock = joueur.poubelle.size();
	 * joueur.draw(); assertEquals(stock, joueur.deck.size());
	 * 
	 * }
	 */

}
