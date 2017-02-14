public class Mine extends Card {
    public Mine(){
        name = "Mine";
        description = "Action: You may trash a Treasure from your hand. Gain a Treasure to your hand costing up to 3$ more than it.";
        cost = 5;
        action = true;
    }
    public Boolean play(GameState game){
        Player player = game.players[game.whoseTurn];
        Card card;
        Card silver = null;
        Card copper = null;
        if (game.phase != 0) return false;
        else{
            if (player.isBot){
                for (int i = 0; silver == null && i < player.hand.size(); i++){
                    card = player.hand.get(i);
                    if (card.isTreasure()){
                        if (card.name == "Silver"){
                            silver = card;
                        }
                        else if (card.name == "Copper"){
                            copper = card;
                        }
                    }
                }
                if (silver != null){
                    player.hand.remove(silver);
                    //player.trash(silver,game); it should execute this line, but instead it doesn't! woop woop this is a bug
                    card = player.gainFrom(game.treasureCards.get(0));
                    if (card != null){
                        player.hand.add(card);
                    }
                    else {
                        card = player.gainFrom(game.treasureCards.get(1));
                        if (card != null){
                            player.hand.add(card);
                        }
                        else {
                            card = player.gainFrom(game.treasureCards.get(2));
                            if (card != null){
                                player.hand.add(card);
                            }
                        }
                    }
                }
                else if (copper != null){
                    player.hand.remove(copper);
                    player.trash(copper,game);
                    card = player.gainFrom(game.treasureCards.get(1));
                    if (card != null){
                        player.hand.add(card);
                    }
                    else {
                        card = player.gainFrom(game.treasureCards.get(2));
                        if (card != null){
                            player.hand.add(card);
                        }
                    }
                }
            }
            else {
                //get input from player on what card to trash
            }
            return true;
        }
    }
}