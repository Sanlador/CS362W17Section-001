/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion;

/**
 *
 * @author Regex
 */
public class playDom {
    
    
    public static void main(String[] args) {        
        Dominion API = new Dominion();
    
    //drawCard    
    //gainCard
    //buyCard
    //shuffle
    //discardCard
    API.unitTestOne(); // deals with the base mechanics cards moving around
    
    //scoreFor
    //updateCoins
    //getWinners
    //endTurn
    //playCard
    //cardEffect
    //isGameOver
    //initializeGame
    API.unitTestTwo(); // accounting
    
    
    //functions tested via use or non-unit tests
        //handCard - ignored mostly because there is no corresponding function for deck or discard
        //fullDeckCount - in scoreFor I inspected print statements manually for hours. It is working.
        //numHandCards - too simple
        //whoseTurn - too simple
        //getCost - tested via use throughout the program
        //supplyCount - too simple
        
        
    
    
      
        
    }
    
    public static void notmainPlayDom(String[] args) {
    //setup 
    //gameState
    int[] k = new int[]{Card.adventurer.ordinal(), Card.gardens.ordinal(), 
            Card.embargo.ordinal(), Card.village.ordinal(), Card.minion.ordinal(), 
            Card.mine.ordinal(), Card.cutpurse.ordinal(), Card.sea_hag.ordinal(), 
            Card.tribute.ordinal(), Card.smithy.ordinal()};
    GameState Game = new GameState();
    Dominion API = new Dominion();
    //    set kingdom cards
    
    System.out.println("Starting game");
    Game = API.initializeGame(2,k,0);
    //Game.reportState();


    //Game.reportPlayerCards(1);

    while (API.isGameOver(Game) != 1) {
        
        API.updateCoins(Game.whoseTurn, Game, 0);
        int money = Game.coins;  
        System.out.println("start: ");
        Game.reportPlayerCards(Game.whoseTurn);

        int numSmithies = 0;
        int numAdventurers = 0;
        int smithyPos = -1;
        int adventurerPos = -1;
      
        for (int i = 0; i < API.numHandCards(Game); i++) 
            if (API.handCard(i, Game) == Card.smithy.ordinal())
                smithyPos = i;
            else if (API.handCard(i, Game) == Card.adventurer.ordinal())
                adventurerPos = i;
    

      
    if (API.whoseTurn(Game) == 0) {
        
        if (smithyPos != -1) {
          System.out.println("0: smithy played from position "); 
          API.playCard(smithyPos, -1, -1, -1, Game); 
          System.out.println("smithy played.\n");
          money = 0;
          int i=0;
          while(i<API.numHandCards(Game)){
            if (API.handCard(i, Game) == Card.copper.ordinal()){
              API.playCard(i, -1, -1, -1, Game);
              money++;
            }
            else if (API.handCard(i, Game) == Card.silver.ordinal()){
              API.playCard(i, -1, -1, -1, Game);
              money += 2;
            }
            else if (API.handCard(i, Game) == Card.gold.ordinal()){
              API.playCard(i, -1, -1, -1, Game);
              money += 3;
            }
            i++;
          }
        }

        if (money >= 8) {
          System.out.println("0: bought province\n"); 
          API.buyCard(Card.province.ordinal(), Game);
        }
        else if (money >= 6) {
          System.out.println("0: bought gold\n"); 
          API.buyCard(Card.gold.ordinal(), Game);
        }
        else if ((money >= 4) && (numSmithies < 2)) {
          System.out.println("0: bought smithy\n"); 
          API.buyCard(Card.smithy.ordinal(), Game);
          numSmithies++;
        }
        else if (money >= 3) {
          System.out.println("0: bought silver\n"); 
          API.buyCard(Card.silver.ordinal(), Game);
        }

        System.out.println("0: end turn\n");
        API.endTurn(Game);
    }
    else {
//        System.out.println("pre-adventureer: ");
//        Game.reportPlayerCards(1);
        if (adventurerPos != -1) {
            
            System.out.println("1: adventurer played from position " + adventurerPos);
            API.playCard(adventurerPos, -1, -1, -1, Game); 
            money = 0;
            int i=0;
            while(i<API.numHandCards(Game)){
              if (API.handCard(i, Game) == Card.copper.ordinal()){
                API.playCard(i, -1, -1, -1, Game);
                money++;         
              }
              else if (API.handCard(i, Game) == Card.silver.ordinal()){
                API.playCard(i, -1, -1, -1, Game);
                money += 2;
              }
              else if (API.handCard(i, Game) == Card.gold.ordinal()){
                API.playCard(i, -1, -1, -1, Game);
                money += 3;
              }
              i++;
            }
        }

//        System.out.println("post-adventureer: ");
//        Game.reportPlayerCards(1);
        if (money >= 8) {
          System.out.println("1: bought province\n");
          API.buyCard(Card.province.ordinal(), Game);
        }
        else if ((money >= 6) && (numAdventurers < 2)) {
          System.out.println("1: bought adventurer\n");
          API.buyCard(Card.adventurer.ordinal(), Game);
          numAdventurers++;
        }else if (money >= 6){
          System.out.println("1: bought gold\n");
              API.buyCard(Card.gold.ordinal(), Game);
          }
        else if (money >= 3){
          System.out.println("1: bought silver\n");
              API.buyCard(Card.silver.ordinal(), Game);
        }
//        System.out.println("post-money: ");
//        Game.reportPlayerCards(1);
        System.out.println("1: endTurn\n");
        
        API.endTurn(Game);      
      }

        System.out.println("Player 0: "+API.scoreFor(0, Game)+"\nPlayer 1: "+API.scoreFor(1, Game)+"\n");

    } // end of While

    
    System.out.println ("pre-score:");
    Game.reportPlayerCards(1);
    System.out.println ("Finished game.\n");
    System.out.println ("Player 0: "+API.scoreFor(0, Game)+"\nPlayer 1: "+API.scoreFor(1, Game)+"\n");

  }
   
    

}