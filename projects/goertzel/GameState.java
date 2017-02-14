package goertzel;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.shuffle;


class GameState {

    List<Player> players = new ArrayList<>();
    private List<Player> winners;
    private int turnCount;

    List<Supply_Stack> victory_supply;
    List<Supply_Stack> treasure_supply;
    List<Supply_Stack> kingdom_supply;



    GameState(String[] names) {
        victory_supply = new ArrayList<>();
        treasure_supply = new ArrayList<>();
        kingdom_supply = new ArrayList<>();
        generateSupplyStacks();

        for (String name : names) {
            players.add(new Player(name));
        }
        for (Player p : players)
            p.linkBoard(this);

        turnCount = 0;
    }

    GameState() {
        victory_supply = new ArrayList<>();
        treasure_supply = new ArrayList<>();
        kingdom_supply = new ArrayList<>();
        generateSupplyStacks();
        turnCount = 0;
    }

    void addPlayer(Player p){
        players.add(p);
        p.linkBoard(this);
    }


    void play(){
        turnCount = 1;
        while (!gameOver()){
            playOneTurn();
        }
        System.out.println("\n\n============================================================\n");
        System.out.println("GAME END\n");

        printBoardInfo();

        System.out.println("\n\nRESULTS");
        for (Player p : players) {
            System.out.println("\t" + p.getPlayerName() + "\tVP: " + p.calcVP());
        }

        winners = determineWinners();
        if (winners.size() == 2) {
            System.out.println("THERE WAS A TIE:");
        }
        else {
            System.out.println("THE WINNER IS:");
            System.out.println("\t" + winners.get(0).getPlayerName());
        }




    }

    public void playOneTurn() {
        if (turnCount == 0) turnCount = 1;
        System.out.println("\n============================================================");
        Player toPlay = players.get((turnCount-1)%2);
        System.out.println("TURN " + turnCount + " : " + toPlay.getPlayerName());
        toPlay.playTurn();
        turnCount++;
    }

    public boolean gameOver(){
        if (findSupplyStack(Card.CardName.PROVINCE).isEmpty()) {return true;}

        List<Supply_Stack> supplyPiles = new ArrayList<>();
        supplyPiles.addAll(victory_supply);
        supplyPiles.addAll(treasure_supply);
        supplyPiles.addAll(kingdom_supply);

        int emptyPiles = 0;
        for (Supply_Stack s : supplyPiles)
            if (s.isEmpty()) emptyPiles++;

        return emptyPiles >= 3;
    }

    private List<Player> determineWinners() {
        int maxVP = 0;
        for (Player p : players)
            if (p.calcVP() > maxVP)
                maxVP = p.calcVP();

        List<Player> winners = new ArrayList<>();
        for (Player p : players)
            if (p.calcVP() == maxVP)
                winners.add(p);
        return winners;
    }

    Supply_Stack findSupplyStack(Card.CardName name){
        List<Supply_Stack> supplyPiles = new ArrayList<>();

        supplyPiles.addAll(victory_supply);
        supplyPiles.addAll(treasure_supply);
        supplyPiles.addAll(kingdom_supply);
        for (Supply_Stack s : supplyPiles)
            if (s.getName() == name) return s;
        return null; // No stack of that name on board
    }

    private void generateSupplyStacks() {
        victory_supply.add(new Supply_Stack(Card.CardName.PROVINCE, 8));
        victory_supply.add(new Supply_Stack(Card.CardName.DUCHY, 8));
        victory_supply.add(new Supply_Stack(Card.CardName.ESTATE, 14));
        victory_supply.add(new Supply_Stack(Card.CardName.CURSE, 10));

        treasure_supply.add(new Supply_Stack(Card.CardName.GOLD, 30));
        treasure_supply.add(new Supply_Stack(Card.CardName.SILVER, 40));
        treasure_supply.add(new Supply_Stack(Card.CardName.COPPER, 60));

        List<Card.CardName> kingdomCards = new ArrayList<>();

        for (Card.CardName name : Card.CardName.values()) {
            if (Card.matchType(name) == Card.CardType.ACTION) {
                kingdomCards.add(name);
            }
        }

        shuffle(kingdomCards);
        List<Card.CardName> toRemove = new ArrayList<>();
        for (int i = kingdomCards.size()-1; i > kingdomCards.size()-3; i--)
            toRemove.add(kingdomCards.get(i));
        kingdomCards.removeAll(toRemove);

        for (Card.CardName name : kingdomCards) {
            kingdom_supply.add(new Supply_Stack(name, 10));
        }
//
//        List<Supply_Stack> supplyPiles = new ArrayList<>();
//        supplyPiles.addAll(victory_supply);
//        supplyPiles.addAll(treasure_supply);
//        supplyPiles.addAll(kingdom_supply);
//
//        for (Supply_Stack s : supplyPiles) {
//
//        }

    }

    void printBoardInfo(){
        System.out.println("BOARD INFO");
        System.out.println("\tPLAYERS");
        for (Player p : players)
            p.printInfo();

        System.out.println("\tVICTORY SUPPLY");
        for (Supply_Stack s : victory_supply)
            System.out.printf("%20s : %d\t%s\n", s.getName(),  s.cardsRemaining(), (s.isEmbargoed())?"Embargoed":"");

        System.out.println("\tTREASURE SUPPLY");
        for (Supply_Stack s : treasure_supply)
            System.out.printf("%20s : %d\t%s\n", s.getName(),  s.cardsRemaining(), (s.isEmbargoed())?"Embargoed":"");

        System.out.println("\tKINGDOM SUPPLY");
        for (Supply_Stack s : kingdom_supply)
            System.out.printf("%20s : %d\t%s\n", s.getName(),  s.cardsRemaining(), (s.isEmbargoed())?"Embargoed":"");
    }

    int getTurnCount(){
        return turnCount;
    }

    List<Player> getWinners(){
        return winners;
    }
}
