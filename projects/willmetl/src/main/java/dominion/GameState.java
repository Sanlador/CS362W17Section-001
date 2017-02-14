/*
  Author: Levi Willmeth
  Written for OSU CS362 - Software Engineering II
  Assignment 1 - Dominion
*/
package dominion;
import java.util.*;

public class GameState{
  private final int defaultDeckSize = 100;
  private final int pileSize = 8;     // How many cards per pile
  // Initial currency cards in the bank
  private final int bankCopper = 60;
  private final int bankSilver = 40;
  private final int bankGold = 30;
  // Initial victory point cards in the bank
  private final int bankEstates = 24;
  private final int bankDuchies = 12;
  private final int bankProvinces = 12;
  // Initial kingdom cards in the bank
  private final int bankKingdomCards = 10;
  private final int bankCurses = 10;  // scales with players, 10, 20, 30, etc

  private ArrayList<Card> embargoTokens;
  private ArrayList<Card> supply;
  public Player[] players;
  public int numPlayers = 0;
  public int playerTurn = 0;
  private final int maxPlayers = 2;

  public GameState(){
    // shared cards that players can buy
    this.supply = new ArrayList<Card>(200);
    this.players = new Player[2];
    // Fill the shared deck with the starting cards
    for(int i=0; i<bankCopper; i++)       supply.add(Card.COPPER);
    for(int i=0; i<bankSilver; i++)       supply.add(Card.SILVER);
    for(int i=0; i<bankGold; i++)         supply.add(Card.GOLD);
    for(int i=0; i<bankEstates; i++)      supply.add(Card.ESTATE);
    for(int i=0; i<bankDuchies; i++)      supply.add(Card.DUCHY);
    for(int i=0; i<bankProvinces; i++)    supply.add(Card.PROVINCE);
    for(int i=0; i<bankCurses; i++)       supply.add(Card.CURSE);
    for(int i=0; i<bankKingdomCards; i++){
      supply.add(Card.ADVENTURER);
      supply.add(Card.AMBASSADOR);
      supply.add(Card.BARON);
      supply.add(Card.COUNCILROOM);
      supply.add(Card.CUTPURSE);
      supply.add(Card.EMBARGO);
      supply.add(Card.FEAST);
      supply.add(Card.GARDENS);
      supply.add(Card.GREAT_HALL);
      supply.add(Card.MINE);
      supply.add(Card.SALVAGER);
      supply.add(Card.SMITHY);
      supply.add(Card.VILLAGE);
    }
  }

  public boolean addPlayer(String name){
    return addPlayer(name, false);
  }
  public boolean addPlayer(String name, boolean isBot){
    return addPlayer(new Player(name, this, isBot));
  }
  public boolean addPlayer(Player p){
    if(numPlayers < maxPlayers){
      players[this.numPlayers++] = p;
      return true;
    }else return false;
  }

  public boolean addCard(Card c){
    return supply.add(c);
  }

  public int countCard(Card c){
    return Collections.frequency(supply, c);
  }

  public boolean checkEndConditions(){
    int missingCards = 0;
    for(Card c: Card.values()){
      if(supply.contains(c) == false) missingCards++;
    }
    return supply.contains(Card.PROVINCE)==false || missingCards>=3;
  }

  public Card takeCard(Card c){
    int i = supply.indexOf(c);
    if(i >= 0) return supply.remove(i);
    return null;
  }

  public void nextTurn(){
    players[playerTurn].newTurn();
    playerTurn = (playerTurn+1)%numPlayers;
    if(checkEndConditions())
      endGame();
  }

  public int listCards(){
    int i = 0;
    System.out.format(
      " # - %-15s %s  %s  %s\n", "Card Name", "Cost", "Qty", "Description"
    );
    for(Card c: Card.values()){
      String desc = c.getDesc();
      desc = desc.substring(0, Math.min(desc.length(), 50));
      System.out.format("%2d - %-15s %-5d %2d    %s\n",
        ++i, c, c.costsMoney, Collections.frequency(supply, c), desc
      );
    }
    return i;
  }

  private void endGame(){
    System.out.println("Game over!");
    for(Player p: players){
      System.out.format("%s amassed %d victory points.\n",
        p, p.countVictoryPoints()
      );
    }
    System.out.println("Thanks for playing!");
  }
}
