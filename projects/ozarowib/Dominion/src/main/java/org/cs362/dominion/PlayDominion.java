package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayDominion
{
	public  static void main(String args[])
	{
		List<Card> cards;
		GameState state;
		//Randomness.reset(10);
		Randomness.reset(System.currentTimeMillis());
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		// System.out.println("Initialization DominionBoard:\n " + state.toString()); 
		Player player = new Player(state, "player-1");
		// player.printStateGame();
		// player.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
		// player.hand.add(Card.getCard(cards,Card.CardName.Smithy));
		state.addPlayer(player);
		player = new Player(state, "player-2");
		// player.hand.add(Card.getCard(cards,Card.CardName.Smithy));
		// player.hand.add(Card.getCard(cards,Card.CardName.Village));
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();
		System.out.println("Initial Dominion Board:\n " + state.toString());
		HashMap<Player, Integer> winners=state.play();
		System.out.println ("Game over after " + state.totalturns + " turns.\n");
		for(Player p: winners.keySet())
		{
			System.out.println (p.player_username + " Score: "+ winners.get(p) );
		}
		// player.printStateGame();	// Display final game state to compare to initial
		System.exit(0);  
	}
}
