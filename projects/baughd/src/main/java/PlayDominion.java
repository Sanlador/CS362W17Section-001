import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PlayDominion {

	   public  static void main(String args[]){

		    List<Card> cards;
		    GameState state;

//INITIALIZE CARDS AND STATE
			//the cards are achieved by each element/constant in the enum class
			cards = new ArrayList<Card>(Card.createCards());
			state = new GameState(cards);
			//System.out.println("Initialization DominionBoard:\n " + state.toString());

//INITIALIZE PLAYERS
            Player player = new Player(state, "PLAYER 1");
		    state.addPlayer(player);

		    player = new Player(state, "PLAYER 2");
		    state.addPlayer(player);

//INITIALIZE GAME
           state.initializeGame();

//PRINT PLAYER DECKS
           System.out.println("\nInitialization of Dominion Board:\n\n" + state.toString());

//ACTUALLY PLAY THE GAME HERE!!!
           HashMap<Player, Integer> winners = state.play();
		   System.out.println ("Finished game.\n\n");

//PRINT WINNERS
           for(Player p: winners.keySet()){
               System.out.println ("Player name: " + p.player_username + " , Score: " + winners.get(p) );
           }

//PRINT PLAYER'S CARDS AND GAMEBOARD
           player.printStateGame();
		      
		      
			System.exit(0);  

	   }
	
	
}
