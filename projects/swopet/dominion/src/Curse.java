public class Curse extends Card {
    public Curse(){
        name = "Curse";
        description = "Curse Card: Provides -1 VP";
        cost = 0;
        victoryPoints = -1;
        curse = true;
    }
    Boolean isCurse(){return true;}
}