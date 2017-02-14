/*
  Author: Levi Willmeth
  Written for OSU CS362 - Software Engineering II
  Assignment 1 - Dominion
*/
package dominion;

public class Dominion{

  public void setupGame(boolean p1Bot, boolean p2Bot){
    GameState game = new GameState();

    game.addPlayer("Amy", p1Bot);
    game.addPlayer("Billy", p2Bot);

    while(game.checkEndConditions())
      game.nextTurn();
  }

  public static void main(String [ ] args){
    System.out.println("Making a new Dominion game.");
    Dominion game1 = new Dominion();
    game1.setupGame(true, true);
  }
}
