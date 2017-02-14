package org.cs362.dominion;

import static org.junit.Assert.*;
import org.junit.Test;

public class GameTest {

	Game test;
	
	@Test
	public void testDeclareWinner(){
		test = new Game(2, true);
		for(int j=0; j<10; j++)
			test.players.get(0).giveCard(new Province());
		
		for(int j=0; j<5; j++){
			test.players.get(1).giveCard(new Province());
		}
		
		Player winner = test.declareWinner();
		assertEquals("The winner was not the player with the most victory points",
				winner, test.players.get(0));
	}

	@Test
	public void TestGameOver(){
		
		test = new Game(2, true);
		boolean repeat = true;
		while(repeat){
			try{
				//empty out the province deck
				test.board.draw(test.board.getDeckIdx("Province"));
			}
			catch(Exception e){
				//province deck now empty
				repeat = false;
			}
		}
		assertTrue("Game didn't end when all province's removed",
				test.isGameOver());
		
		test = new Game(2, true);
		
		//empty out three decks
		for(int j=1; j<4; j++){
			while(repeat){
				try{
					//empty out the province deck
					test.board.draw(j);
				}
				catch(Exception e){
					//province deck now empty
					repeat = false;
				}
			}
		}
		
		assertTrue("Game didn't end when three decks are emtpy",
				test.isGameOver());	
	}
	
	@Test
	public void testActionPhaseNoAction(){
		test = new Game(1, true);
		test.players.get(0).setActions(0);
		test.currentPlayer = test.players.get(0);
		test.actionPhase();
		assertEquals("Player's action wasn't decremented",
				-1, test.players.get(0).getActions());
	}
	
}



























