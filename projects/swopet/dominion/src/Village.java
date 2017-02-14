public class Village extends Card {
    public Village(){
        name = "Village";
        description = "Action: +1 Card, +2 Actions";
        cost = 3;
        action = true;
    }
    public Boolean play(GameState game){
        Player player = game.players[game.whoseTurn];
        if (game.phase != 0) return false;
        else{
            player.hand.addAll(player.drawCards(1,game.rand));
            game.actions += 2;
            return true;
        }
    }
}