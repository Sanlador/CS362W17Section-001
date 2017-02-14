public class Feast extends Card {
    public Feast(){
        name = "Feast";
        description = "Action: Trash this card. Gain a card costing up to 5$";
        cost = 4;
        action = true;
    }
    public Boolean play(GameState game){
        Player player = game.players[game.whoseTurn];
        if (game.phase != 0) return false;
        else{
            player.trash(this,game);
            if (player.isBot){
                //TODO: select a <=5$ cost card
                //this is a bug, it doesn't gain a card
            }
            else {
                //TODO: get input from player
            }
            return true;
        }
    }
}