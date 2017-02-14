package dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import java.util.Collections;


public class PlayDominion {

	public  static void main(String args[]){

		List<Card> cards;
		GameState state;

		//the cards  are achieved by each element/constant in the enum class
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);

		Player player = new Player(state, "player-1");
		//player.printStateGame();
		state.addPlayer(player);


		player = new Player(state, "player-2");
		state.addPlayer(player);
		//Initialize the game!
		state.initializeGame();

		System.out.println("Initialization DominionBoard:\n " + state.toString());

		HashMap<Player, Integer> winners=state.play();
		System.out.println ("Finished game.\n");

		for(Player p: winners.keySet()){
			System.out.println ("Player name: "+winners.get(p) + " , Score: "+ winners.get(p) );
		}

		player.printStateGame();


		System.exit(0);

	}


}
