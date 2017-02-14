import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Ambassador extends Card {
    public Ambassador(){
        name = "Ambassador";
        description = "Action - Attack: Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.";
        cost = 3;
        action = true;
        attack = true;
    }
    public Boolean play(GameState game){
        List<Integer> curses = new ArrayList<Integer>();
        List<Integer> estates = new ArrayList<Integer>();
        List<Integer> coppers = new ArrayList<Integer>();
        Iterator<Integer> iter;
        int count;
        int next;
        Card card;
        Player player = game.players[game.whoseTurn];
        if (game.phase != 0) return false;
        else{
            if (player.isBot){ //this card has a choice so we implement bot behavior
                for (int i = player.hand.size() - 1; i >= 0; i--){ //get their indices, highest to lowest
                    switch (player.hand.get(i).name){
                        case "Curse":   curses.add(i);
                                        break;
                        case "Copper":  coppers.add(i);
                                        break;
                        case "Estate":  estates.add(i);
                                        break;
                        default:        break;
                    } 
                }
                //Look for curses first, then 2 estates, then  2 coppers, then an estate, then a copper
                if (!curses.isEmpty()){
                    count = 0;
                    iter = curses.iterator();
                    while (iter.hasNext() && count < 2){
                        next = iter.next();
                        card = player.hand.remove(next);
                        game.victoryCards.get(3).add(0,card); //add to the 0th index in case there's a token on top
                        count++;
                    }
                    for (int i = 1; i < game.numPlayers; i++){
                        card = game.players[(game.whoseTurn + i) % game.numPlayers].gainFrom(game.victoryCards.get(3));
                        if (card == null) break;
                        else game.players[(game.whoseTurn + i) % game.numPlayers].discard.add(card);
                    }
                }
                else if (coppers.size() > estates.size()){
                    count = 0;
                    iter = coppers.iterator();
                    while (iter.hasNext() && count < 2){
                        next = iter.next();
                        card = player.hand.remove(next);
                        game.treasureCards.get(2).add(0,card); //add to the 0th index in case there's a token on top
                        count++;
                    }
                    for (int i = 1; i < game.numPlayers; i++){
                        card = game.players[(game.whoseTurn + i) % game.numPlayers].gainFrom(game.treasureCards.get(2));
                        if (card == null) break;
                        else game.players[(game.whoseTurn + i) % game.numPlayers].discard.add(card);
                    }
                }
                else if (!estates.isEmpty()){
                    count = 0;
                    iter = estates.iterator();
                    while (iter.hasNext() && count < 2){
                        next = iter.next();
                        card = player.hand.remove(next);
                        game.victoryCards.get(2).add(0,card); //add to the 0th index in case there's a token on top
                        count++;
                    }
                    for (int i = 1; i < game.numPlayers; i++){
                        card = game.players[(game.whoseTurn + i) % game.numPlayers].gainFrom(game.victoryCards.get(2));
                        if (card == null) break;
                        else game.players[(game.whoseTurn + i) % game.numPlayers].discard.add(card);
                    }
                }
                else{ //it should never get here, since my bot won't choose to play an Ambassador in absence of a Curse, Copper, or Estate, even if it is available. This can be changed later to add the option of playing higher-cost junk cards against your opponent late in the game
                    
                }
                return true;
            }
            else {
                //get choice from player
                return true;
            }
        }
    }
}