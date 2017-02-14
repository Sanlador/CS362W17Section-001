import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collection;

public class Card {
    String name;
    String description;
    int cost = 0;
    int value = 0;
    int victoryPoints = 0;
    Boolean action = false;
    Boolean attack = false;
    Boolean treasure = false;
    Boolean victory = false;
    Boolean curse = false;
    Boolean token = false;
    String getName(){return name;}
    Boolean isAction(){return action;}
    Boolean isAttack(){return attack;}
    Boolean isTreasure(){return treasure;}
    Boolean isVictory(){return victory;}
    Boolean isCurse(){return curse;}
    Boolean isToken(){return token;}
    Boolean play(GameState game){return false;}
}