package Dominion;

public class driver {

	public static void main(String[] args)
	{
		//playGame plays either the number of turns given as input, or, if input is 0, a full game
		//Play.playGame(0);
		
		PlayerTest1 pt = new PlayerTest1();
		pt.testGetMoney();
		pt.testTrash();
		pt.testDiscard();
		pt.testDiscardAll();
		pt.testDrawDeck();
		pt.testGain();
		pt.testPlayer();
		pt.testPlayK();
		pt.testShuffle();
		pt.testTrash();
		pt.testTurn();
		pt.testGame();
	}

}
