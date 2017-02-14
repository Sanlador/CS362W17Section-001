public class CouncilRoom extends Card {
    public CouncilRoom(){
        name = "Council Room";
        description = "Action: +4 Cards. +1 Buy. Each other player draws a card.";
        cost = 5;
        action = true;
    }
    public Boolean play(GameState game){
        Player player = game.players[game.whoseTurn];
        Player target;
        if (game.phase != 0) return false;
        else{
            player.hand.addAll(player.drawCards(4,game.rand));
            game.buys++;
            for (int i = 1; i < game.numPlayers; i++){
                target = game.players[(game.whoseTurn + i) % game.numPlayers];
                target.hand.addAll(target.drawCards(1,game.rand));
            }
            return true;
        }
    }
}