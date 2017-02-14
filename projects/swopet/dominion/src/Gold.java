public class Gold extends Card {
    public Gold(){
        name = "Gold";
        description = "Treasure: Worth 3$";
        cost = 6;
        value = 3;
        treasure = true;
    }
    Boolean play(GameState game){
        if (game.phase == 1){
            game.money += value;
            return true;
        }
        else {
            System.out.println("You can't play a Gold right now, wait til the Buy phase");
            return false;
        }
    }
}