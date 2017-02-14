package goertzel;


public class Main {

    public static void main(String[] args) {

        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        GameState board = new GameState();
        board.addPlayer(player1);
        board.addPlayer(player2);
        board.printBoardInfo();

//        for (int i = 0; i < 20; i++)
//            board.playOneTurn();

        board.play();
    }
}
