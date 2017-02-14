public class Silver extends Card {
    public Silver(){
        name = "Silver";
        description = "Treasure: Worth 2$";
        cost = 3;
        value = 2;
        treasure = true;
    }
    Boolean play(GameState game){
        if (game.phase == 1){
            game.money += value;
            return true;
        }
        else {
            System.out.println("You can't play a Silver right now, wait til the Buy phase");
            return false;
        }
    }
}