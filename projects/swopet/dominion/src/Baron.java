public class Baron extends Card {
    public Baron(){
        name = "Baron";
        description = "Action: You may discard an Estate for +4$. If you don't, gain an Estate.";
        cost = 4;
        action = true;
    }
    public Boolean play(GameState game){
        Card card;
        Player player = game.players[game.whoseTurn];
        if (game.phase != 0) return false;
        else{
            if (player.isBot){
                for (int i = 0; i < player.hand.size(); i++){
                    if (player.hand.get(i).name == "Estate"){
                        player.discard.add(player.hand.remove(i));
                        game.money += 4;
                        return true;
                    }
                }
                card = player.gainFrom(game.victoryCards.get(2));
                if (card != null) player.discard.add(card);
                return true;
            }
            else {
                //get input from player
                return true;
            }
        }
    }
}