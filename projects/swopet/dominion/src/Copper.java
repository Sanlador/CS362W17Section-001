public class Copper extends Card {
    public Copper(){
        name = "Copper";
        description = "Treasure: Worth 1$";
        cost = 0;
        value = 1;
        treasure = true;
    }
    Boolean play(GameState game){
        if (game.phase == 1){
            game.money += value;
            return true;
        }
        else {
            System.out.println("You can't play a Copper right now, wait til the Buy phase");
            return false;
        }
    }
}