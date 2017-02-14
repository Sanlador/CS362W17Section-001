package dominion;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DominionTest {

	@Test
	public void testPlayer0() {
		int resultat;
		PlayGame jeu = new PlayGame();
		String[] args = new String[1];
		args[0] = "-1";
		resultat = jeu.jeu(args);
		assertEquals(resultat, 0);

	}

	@Test
	public void testPlayer1() {
		int resultat;
		PlayGame jeu = new PlayGame();
		String[] args = new String[1];
		args[0] = "-2";
		resultat = jeu.jeu(args);
		assertEquals(resultat, 1);
	}

	@Test
	public void testTie() {
		int resultat;
		PlayGame jeu = new PlayGame();
		String[] args = new String[1];
		args[0] = "-3";
		resultat = jeu.jeu(args);
		assertEquals(resultat, -1);
	}

	@Test
	public void testBacon() {
		int resultat;
		PlayGame jeu = new PlayGame();
		String[] args = new String[1];
		args[0] = "bacon";
		resultat = jeu.jeu(args);
		assertEquals((resultat == 0 || resultat == 1 || resultat == -1), true);
	}

	@Test
	public void testNothing() {
		int resultat;
		PlayGame jeu = new PlayGame();
		String[] arg = {};
		resultat = jeu.jeu(arg);
		assertEquals((resultat == 0 || resultat == 1 || resultat == -1), true);
	}

	@Test
	public void testMain() {
		Dominion main = new Dominion();
		String[] arg = new String[1];
		arg[0] = "";
		main.main(arg);
		
	}
}
