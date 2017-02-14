package dominion_v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//This is taken from the code given by Ali Aburas

public class PlayDominion{

	public  static void main(String[] args){
		if(args.length == 0)
		{
			System.out.println("Usage: java dominion.Playdom int");
			return;
		}
		
		int kingCount = Integer.parseInt(args[0]);
		
		
		List<Card> cards;
		GameState state;
		Randomness.reset(10);
		
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		//System.out.println("Init Dominion Board: \n" + state.toString());
		
		Player player = new Player(state, "player-1");
	//	player.printStateGame();
		//player.hand.add(Card.getCard(cards, Card.CardName.Adventurer));
		//player.hand.add(Card.getCard(cards, Card.CardName.Smithy));
		
		state.addPlayer(player);
		player = new Player(state, "player-2");
		//player.hand.add(Card.getCard(cards, Card.CardName.Adventurer));
		//player.hand.add(Card.getCard(cards, Card.CardName.Smithy));
		state.addPlayer(player);
		
		state.initializeGame(kingCount);
		
		System.out.println("Init Dominion Board: \n" + state.toString());
		HashMap<Player, Integer> winners=state.play();
		System.out.println ("Finished game.\n");
		int i = 1;
		for(Player p: winners.keySet())
		{
			System.out.println ("Player name: "+ i + " , Score: "+ winners.get(p) );
			i++;
		}
		//player.printStateGame();
		System.exit(0);
	}
}