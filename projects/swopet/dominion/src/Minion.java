public class Minion extends Card {
    public Minion(){
        name = "Minion";
        description = "Action - Attack: Choose one: +2$; or discard your hand, +4 Cards, and each other player with at least 5 cards in hand discards their hand and draws 4 cards.";
        cost = 5;
        action = true;
        attack = true;
    }
    public Boolean play(GameState game){
        int choice = 0;
        Player player = game.players[game.whoseTurn];
        Player target;
        if (game.phase != 0) return false;
        else{
            //game.actions += 1; oh no I forgot to do this! what a bug
            if (choice == 0){
                game.money += 2;
            }
            else {
                player.discard.addAll(player.hand);
                player.hand.clear();
                player.hand.addAll(player.drawCards(5,game.rand));
                for (int i = 1; i < game.numPlayers; i++){
                    target = game.players[(game.whoseTurn + i) % game.numPlayers];
                    if (target.hand.size() >= 5){
                        target.discard.addAll(target.hand);
                        target.hand.clear();
                        target.hand.addAll(target.drawCards(5,game.rand));
                    }
                }
            }
            return true;
        }
    }
}