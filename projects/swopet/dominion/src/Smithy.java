public class Smithy extends Card {
    public Smithy(){
        name = "Smithy";
        description = "Action: +3 Cards";
        cost = 4;
        action = true;
    }
    public Boolean play(GameState game){
        Player player = game.players[game.whoseTurn];
        if (game.phase != 0) return false;
        else{
            player.hand.addAll(player.drawCards(4,game.rand)); //buggaroo
            return true;
        }
    }
}