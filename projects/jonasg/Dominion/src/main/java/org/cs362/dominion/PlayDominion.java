package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Collections;

public class PlayDominion {
    public static void main(String args[]){

	Randomness.reset();
	List<Card> cards = new ArrayList<Card>(Card.createCards());
	GameState state = new GameState(cards);

	//the cards are achieved by each element/constant in the enum class
	Player player = new Player(state, "player-1");
	state.addPlayer(player);

	player = new Player(state, "player-2");
	state.addPlayer(player);

	//Initialize the game!
	state.initializeGame();

	System.out.println("Initialization DominionBoard:\n " + state.toString());

	HashMap<Player, Integer> winners = state.play();
	System.out.println ("Finished game.\n");

	for(Player p: winners.keySet()){
	    System.out.println ("Player name: " + winners.get(p) + " , Score: " + winners.get(p));
	}

	System.out.println(state.toString());

	System.exit(0);
    }
}
