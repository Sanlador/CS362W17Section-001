package dominion;

public class Dominion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Board plateau = new Board(2); int i; Player joueur = new
		 * Player(plateau, 1); Card[] tableau = plateau.getCards(); for (i = 0;
		 * i < 17; i++) { tableau[i].print(); } for (i = 0; i < 10; i++) {
		 * joueur.mix(); } joueur.draw(); joueur.draw(); joueur.draw();
		 * joueur.draw(); joueur.draw(); joueur.showHand();
		 * joueur.playTreasure(); joueur.removeTreasure(); joueur.showHand();
		 * System.out.println("argent = " + joueur.getMoney());
		 * joueur.gameTurn(); System.out.println("fini\n"); joueur.showHand();
		 * System.out.println("asd2"); joueur.showPoubelle();
		 */
		PlayGame instance = new PlayGame();
		instance.jeu(args);

	}

}

class PlayGame {
	public int jeu(String[] args) {
		String truc;
		if (args.length > 0) {
			truc = args[0];
		} else
			truc = new String();

		Board plateau = new Board(2);
		Player[] joueurs = new Player[2];
		joueurs[0] = new Player(plateau, 0);
		joueurs[1] = new Player(plateau, 1);
		if (args.length > 0) {
			if (truc.equals(new String("-1"))) {
				joueurs[0].deck.add(new Card(4));
				plateau.pioche_number[0] = 0;
				plateau.pioche_number[2] = 0;
				plateau.pioche_number[1] = 0;
			} else if (truc.equals(new String("-2"))) {
				joueurs[1].deck.add(new Card(4));
				plateau.pioche_number[0] = 0;
				plateau.pioche_number[2] = 0;
				plateau.pioche_number[1] = 0;
			} else if (truc.equals(new String("-3"))) {
				plateau.pioche_number[0] = 0;
				plateau.pioche_number[2] = 0;
				plateau.pioche_number[1] = 0;
			}
		}

		while (plateau.gameDone() != true) {
			joueurs[0].gameTurn();
			joueurs[1].gameTurn();
		}
		System.out.println("player 0 has " + joueurs[0].getScore() + " points");
		System.out.println("player 1 has " + joueurs[1].getScore() + " points");
		if (joueurs[0].getScore() > joueurs[1].getScore()) {
			System.out.println("Player 0 wins!");
			return 0;
		} else if (joueurs[1].getScore() > joueurs[0].getScore()) {
			System.out.println("Player 1 wins!");
			return 1;
		} else {
			System.out.println("It's a tie!");
			return -1;

		}
	}
}
