public class Cutpurse extends Card {
    public Cutpurse(){
        name = "Cutpurse";
        description = "Action - Attack: +2$ Each other player discards a Copper card (or reveals a hand with no Copper).";
        cost = 4;
        action = true;
        attack = true;
    }
    public Boolean play(GameState game){
        Player target;
        Boolean foundCopper;
        if (game.phase != 0) return false;
        else{
            game.money += 2;
            for (int i = 1; i < game.numPlayers; i++){
                target = game.players[(game.whoseTurn + i) % game.numPlayers];
                foundCopper = false;
                for (int j = 0; j < target.hand.size(); j++){
                    if (target.hand.get(j).name == "Copper"){
                        target.discard.add(target.hand.remove(j));
                        foundCopper = true;
                        break;
                    }
                }
                if (!foundCopper) target.revealHand();
            }
            return true;
        }
    }
}