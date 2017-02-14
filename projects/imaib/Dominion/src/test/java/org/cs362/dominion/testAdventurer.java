package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class testAdventurer
{
	@Test
	public void test() throws Throwable {
		Randomness.reset(System.currentTimeMillis());
		GameState state;
		List<Card> cards;
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		Player testPlayer = new Player(state, "test-player-1");
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
		state.addPlayer(testPlayer);
		testPlayer = new Player(state, "test-player-2");
		testPlayer.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
		state.addPlayer(testPlayer);
		state.prepTestGame();

        for (Player player : state.players)
        {
    	 	Card c = Card.getCard(state.cards,Card.CardName.Adventurer);
    	 	c.play(player, state);
        }
        int treasure = 0;
        for (Player player : state.players)
        {
        	for (int i=player.hand.size(); i>0; i--)
        	{
        		Card c = player.hand.get(i-1);
        		if (c.getType() == Card.Type.TREASURE)
        		{treasure += 1;}
        	}
            assertEquals( treasure, 2 );
            treasure = 0;
        }
	
	}
}
