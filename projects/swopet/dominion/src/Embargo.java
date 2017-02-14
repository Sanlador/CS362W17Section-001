import java.util.List;
import java.util.ArrayList;

public class Embargo extends Card {
    public Embargo(){
        name = "Embargo";
        description = "Action: +2$. Trash this card. Put an Embargo token on top of a Supply pile. When a player buys a card, he gains a Curse card per Embargo token on that pile.";
        cost = 2;
        action = true;
    }
    public Boolean play(GameState game){
        List<Card> targetPile;
        int pileID;
        Player player = game.players[game.whoseTurn];
        if (game.phase != 0) return false;
        else{
            game.money += 2;
            player.trash(this,game);
            if (player.isBot){
                //currently, place it on a random pile. TODO: bot smartness
                pileID = game.rand.nextInt(17);
                if (pileID >= 7){
                    targetPile = game.kingdomCards.get(pileID - 7);
                }
                else if (pileID >= 3){
                    targetPile = game.victoryCards.get(pileID - 3);
                }
                else {
                    targetPile = game.treasureCards.get(pileID);
                }
            }
            else{
                //currently, place it on a random pile. TODO: add player input
                pileID = game.rand.nextInt(17);
                if (pileID >= 7){
                    targetPile = game.kingdomCards.get(pileID - 7);
                }
                else if (pileID >= 3){
                    targetPile = game.victoryCards.get(pileID - 3);
                }
                else {
                    targetPile = game.treasureCards.get(pileID);
                }
            }
            if (targetPile.get(targetPile.size()-1).token){
                targetPile.get(targetPile.size()-1).value += 1;
            }
            else {
                targetPile.add(new EmbargoToken());
            }
            //TODO: perform the action
            return true;
        }
    }
}