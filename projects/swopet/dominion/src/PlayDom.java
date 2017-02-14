public class PlayDom {
	public static void main(String [] args) {
        int numPlayers = Integer.parseInt(args[0]);
        GameState game = new GameState(numPlayers);
        while (game.turn() == false){ //returns true when it's the last turn
            //you could add some trace statements here getting game variables
        }
        game.printWinner();
		System.out.println("GAME OVER");
	}
}
