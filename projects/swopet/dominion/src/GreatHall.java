public class GreatHall extends Card {
    public GreatHall(){
        name = "Great Hall";
        description = "Action - Victory: +1 Card, +1 Action / +1 VP";
        cost = 3;
        action = true;
        victory = true;
        victoryPoints = 1;
    }
    public Boolean play(GameState game){
        Player player = game.players[game.whoseTurn];
        if (game.phase != 0) return false;
        else{
            player.hand.addAll(player.drawCards(1,game.rand));
            game.actions += 1;
            return true;
        }
    }
}