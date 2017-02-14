import java.util.List;
import java.util.ArrayList;

public class Adventurer extends Card {
    public Adventurer(){
        name = "Adventurer";
        description = "Action: Reveal cards from your deck until you reveal two treasure cards. Put those treasure cards in your hand and discard the other revealed cards.";
        cost = 6;
        action = true;
    }
    public Boolean play(GameState game){
        Player player = game.players[game.whoseTurn];
        Card newCard = null;
        List<Card> revealedCards = new ArrayList<Card>();
        List<Card> treasureCards = new ArrayList<Card>();
        int treasureCount = 0;
        if (game.phase != 0) return false;
        else{
            do {
                newCard = player.drawCard(game.rand);
                if (newCard != null){
                    if (newCard.isTreasure()){
                        System.out.println("Revealed " + newCard.name + ", added to hand");
                        treasureCards.add(newCard);
                        treasureCount++;
                    }
                    else {
                        System.out.println("Revealed " + newCard.name);
                        revealedCards.add(newCard);
                    }
                }
            } while (newCard != null && treasureCount < 2);
            player.discard.addAll(revealedCards);
            player.hand.addAll(treasureCards);
            return true;
        }
    }
}