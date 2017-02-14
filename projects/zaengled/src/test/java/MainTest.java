import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Zaengle on 2/12/2017.
 */
public class MainTest {

    @Test
    public void firstTest(){
        List<Card> cards;
        GameState state;
        Randomness.reset(10);
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);

        Player player = new Player(state, "Player-1");
        player.hand.add(Card.getCard(cards, Card.CardName.Ambassador));
        player.hand.add(Card.getCard(cards, Card.CardName.Baron));

        state.addPlayer(player);
        player = new Player(state, "Player-2");
        player.hand.add(Card.getCard(cards, Card.CardName.Sea_Hag));

        state.addPlayer(player);
        state.initializeGame();

        System.out.println("Initialized Dominion Board:\n" + state.toString());

        HashMap<Player, Integer> winners = state.play();
        System.out.println("Finished game.\n");

        for(Player p: winners.keySet()){
            System.out.println("Player name: "+winners.get(p) + " Score: " + winners.get(p));
        }
    }

}
