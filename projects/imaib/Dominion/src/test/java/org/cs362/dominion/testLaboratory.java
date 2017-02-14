package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class testLaboratory
{
	@Test
	public void test() throws Throwable {
		Randomness.reset(System.currentTimeMillis());
		GameState state, originalState;
		List<Card> cards;
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		Player testPlayer = new Player(state, "test-player-1");
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Laboratory));
		state.addPlayer(testPlayer);
		testPlayer = new Player(state, "test-player-2");
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Laboratory));
		state.addPlayer(testPlayer);
		state.prepTestGame();
		originalState=(GameState) state.clone();
		
        for (Player player : state.players)
        {
    	 	Card c = Card.getCard(state.cards,Card.CardName.Laboratory);
    	 	c.play(player, state);
        }
	     assertEquals( state.players.get(0).hand.size(), originalState.players.get(0).hand.size()+2 );
          assertEquals( state.players.get(0).numActions, originalState.players.get(0).numActions+1 );
	}
}
