package org.cs362.dominion;
import java.io.*;
import java.util.*;
public class Game {
    public static void main(String[] args) {
        // Game dominion = new Game("Connor", "Billy", "Lily", "Robert");
        Game dominion = new Game("Connor", "Billy", "Lily", "Robert", "Aelin", "Shanna");
        for (int x = 0; x < dominion.numPlayers(); x++) {
            dominion.getPlayer(x).draw(5);
        }
        while (!dominion.win()) {
            for (int x = 0; x < dominion.numPlayers(); x++) {
                dominion.playerTurn(x);
            }
        }

        dominion.tallyPoints();

    }

    ArrayList<Deck> bank;
    public Deck trash;
    int numPlayers;
    ArrayList<Player> players;
    public ArrayList<String> cardsInGame;

    public Game(String ... names) {
        bank = new ArrayList<Deck>();
        trash = new Deck();
        cardsInGame = new ArrayList<String>();
        bank.add(new Deck(30, new Card("gold")));
        bank.add(new Deck(40, new Card("silver")));
        bank.add(new Deck(60, new Card("copper")));
        bank.add(new Deck(12, new Card("province")));
        bank.add(new Deck(12, new Card("dutchy")));
        bank.add(new Deck(24, new Card("estate")));

        cardsInGame.add("village");
        cardsInGame.add("smithy");
        cardsInGame.add("adventurer");
        cardsInGame.add("witch");
        cardsInGame.add("cellar");
        cardsInGame.add("market");
        cardsInGame.add("curse");
        cardsInGame.add("ambassador");
        cardsInGame.add("baron");
        cardsInGame.add("council room");
        cardsInGame.add("cutpurse");
        cardsInGame.add("embargo");
        cardsInGame.add("feast");
        cardsInGame.add("gardens");
        cardsInGame.add("great hall");
        cardsInGame.add("mine");
        cardsInGame.add("salvager");

        for (String deckName: cardsInGame) {
            bank.add(new Deck(10, new Card(deckName)));
        }
        cardsInGame.add("gold");
        cardsInGame.add("silver");
        cardsInGame.add("copper");
        cardsInGame.add("province");
        cardsInGame.add("dutchy");
        cardsInGame.add("estate");
        players = new ArrayList<Player>();
        for (String s: names) {
            players.add(new Player(s));
        }
        numPlayers = names.length;
    }
    public String randomCard() {
        Random rand = new Random();
        return cardsInGame.get(rand.nextInt(cardsInGame.size()));
    }
    public boolean yesNo() {
        Random rand = new Random();
        if (rand.nextInt(2) == 1) {
            return true;
        }
        else {
            return false;
        }
    }
    public String randomTreasure() {
        Random rand = new Random();
        int n = rand.nextInt(3);
        if (n == 0)         { return "gold";   }
        else if (n == 1)    { return "silver"; }
        else                { return "copper"; }
    }
    public void playerTurn(int num) {

        String name = getPlayer(num).getName();
        String cardToPlay;

        getPlayer(num).starterPoints();

        while (getPlayer(num).hasActions()) {
            cardToPlay = randomCard();
            playCard(num, cardToPlay);
        }

        getPlayer(num).printAllDecks();
        getPlayer(num).sumTreasure();
        System.out.println("\n");
        System.out.println(getPlayer(num).getMoves());

        String purchaseCard;
        boolean done = true;
        do {
            purchaseCard = randomCard();
            printBank();
            System.out.printf("Buying %s\n", purchaseCard);
            if (getDeck(purchaseCard) != null) {
                getPlayer(num).buy(getDeck(purchaseCard));
                getPlayer(num).modifyBuys(-1);
                getPlayer(num).modifyValues(-1 * getDeck(purchaseCard).cardInfo(0).getCost());
                done = false;
            }
        } while (!done && (getPlayer(num).getBuys() > 0));
        getPlayer(num).discardAll();
        getPlayer(num).draw(5);
    }
    public boolean playCard(int num, String cardName) {
        if (getPlayer(num).hand.indexOf(cardName) >= 0) {
            Card temp = new Card(cardName);
            if (!temp.isType("action")) {
                return false;
            }
            else {
                System.out.printf("Playing %s\n", cardName);
                applyCardActions(getPlayer(num), num, temp);
                if (! (cardName.equals("feast") || cardName.equals("embargo")) ) {
                    getPlayer(num).discard(cardName);
                }
                getPlayer(num).modifyActions(-1);
                return true;
            }
        }
        else {
            return false;
        }
    }
    public void applyCardActions(Player player, int num, Card card) {
        Random rand = new Random();
        player.modifyActions(card.getActions());
        player.modifyValues(card.getValue());
        player.modifyBuys(card.getBuys());
        if (card.getCards() > 0) {
            player.draw(card.getCards());
        }
        if (card.isType("special action")) {
            if (card.getName().equals("adventurer")) {
                int numTreasures = 0;
                while (numTreasures < 2) {
                    if (player.draw().isType("treasure")) {
                        numTreasures++;
                    }
                    else {
                        player.discardAtIndex(player.hand.numCards() - 1);
                    }
                }
            }
            else if (card.getName().equals("baron")) {
                boolean discard = yesNo();
                if (discard) {
                    player.discard("estate");
                    player.modifyValues(4);
                }
                else {
                    buy(player, "estate");
                }
            }
            else if (card.getName().equals("witch")) {
                for (int x = 0; x < players.size(); x++) {
                    if (x != num) {
                        buy(getPlayer(x), "curse");
                    }
                }
            }
            else if (card.getName().equals("cellar")) {
                String discard;
                int numDraw = 0;
                int n;
                do {
                    n = rand.nextInt(200);
                    if ( n > 40) {
                        discard = randomCard();
                    }
                    else {
                        discard = "done";
                    }
                    if (player.hand.hasCard(discard)) {
                        player.discard(discard);
                        numDraw++;
                        player.showHand();
                        System.out.println("");
                    }
                } while (!discard.equals("done"));
                player.draw(numDraw);
            }
            else if (card.getName().equals("council room")) {
                for (int x = 0; x < players.size(); x++) {
                    if (x != num) {
                        getPlayer(x).draw();
                    }
                }
            }
            else if (card.getName().equals("cutpurse")) {
                for (int x = 0; x < players.size(); x++) {
                    if (x != num) {
                        if (getPlayer(x).hand.hasCard("copper")) {
                            // Line below is buggy code
                            // getPlayer(x).discardAtIndex(getPlayer(x).hand.indexOf("copper"));
                            // this one is correct
                            getPlayer(x).discard("copper");
                        }
                    }
                }
            }
            else if (card.getName().equals("feast")) {
                trash.addCard(player.hand.drawCard("feast"));
                buy(player, randomCard());
            }
            else if (card.getName().equals("mine")) {
                boolean again = true;
                if (player.hand.hasCard("copper") || player.hand.hasCard("silver")) {
                    String treasureName = randomTreasure();
                    do {
                        if (treasureName.equals("copper") && player.hand.hasCard("copper")) {
                            trash.addCard(player.hand.drawCard("copper"));
                            player.hand.addCard(getDeck("silver").drawCard());
                            again = false;
                        }
                        else if (treasureName.equals("silver") && player.hand.hasCard("silver")) {
                            trash.addCard(player.hand.drawCard("silver"));
                            player.hand.addCard(getDeck("gold").drawCard());
                            again = false;
                        }
                        treasureName = randomTreasure();
                    } while(again);
                }
            }
            else if (card.getName().equals("ambassador")) {
                String supply = randomCard();
                int numberBack = rand.nextInt(2) + 1;
                for (int y = 0; y < numberBack; y++) {
                    if (player.hand.hasCard(supply)) {
                        getDeck(supply).addCard(player.hand.drawCard(supply));
                        for (int x = 0; x < players.size(); x++) {
                            if (x != num) {
                                getPlayer(x).buy(getDeck(supply));
                            }
                        }
                    }
                }
            }
            else if (card.getName().equals("embargo")) {
                trash.addCard(player.hand.drawCard("embargo"));
                player.modifyValues(2);
                getDeck(randomCard()).modifyTokens(1);
            }
        }
    }
    public void buy(Player player, String name) {
        if (getDeck(name).numCards() > 0) {
            player.buy(getDeck(name));
        }
    }
    public Player getPlayer(int number) {
        return players.get(number);
    }
    public Deck getDeck(String name) {
        for(int x = 0; x < bank.size(); x++) {
            String deckName = bank.get(x).getName();
            if (deckName.equals(name)) {
                return bank.get(x);
            }
        }
        return null;
    }
    public int numPlayers() {
        return numPlayers;
    }
    public int calculateVictoryPoints(Player player) {
        int victoryPoints = 0;
        int numCards = player.drawDeck.numCards();
        for (int x = 0; x < numCards; x++) {
            if (player.drawDeck.cardInfo(x).isType("victory")) {
                victoryPoints +=  player.drawDeck.cardInfo(x).getVictoryPoints();
            }
        }
        return victoryPoints;
    }
    public boolean win() {
        if (getDeck("province").empty()) {
            return true;
        }
        int emptyDecks = 0;
        for (int x = 0; x < bank.size(); x++) {
            if (bank.get(x).empty()) {
                emptyDecks++;
                if (emptyDecks == 3) {
                    return true;
                }
            }
        }
        return false;
    }
    public void printAllDecks(int num) {
        String name = getPlayer(num).getName();
        System.out.println("\n" + name + "'s draw deck: ");
        getPlayer(num).drawDeck.printDeck();
        System.out.println("\n" +  name + "'s hand:");
        getPlayer(num).showHand();
        System.out.println("\n" + name + "'s discarded deck: ");
        getPlayer(num).discard.printDeck();
    }
    public void printBank() {
        for (int x = 0; x < bank.size(); x++) {
            System.out.printf("%-15s# remaining: %d\tCost: %d\tTokens: %d\tDescription: %s\n", bank.get(x).getName(), bank.get(x).numCards(), bank.get(x).cardInfo(0).getCost(), bank.get(x).getTokens(), bank.get(x).cardInfo(0).getDescription());
        }
    }
    public void showHand(int playerNumber) {
        System.out.println(getPlayer(playerNumber).getName() + "'s hand: ");
        getPlayer(playerNumber).showHand();
    }
    public void printBank(int coinLimit) {
        for (int x = 0; x < bank.size(); x++) {
            if (bank.get(x).cardInfo(0).getCost() <= coinLimit) {
                System.out.printf("%-15s# remaining: %d\tCost: %d\tTokens: %d\tDescription: %s\n", bank.get(x).cardInfo(0).getName(), bank.get(x).numCards(), bank.get(x).cardInfo(0).getCost(), bank.get(x).getTokens(), bank.get(x).cardInfo(0).getDescription());
            }
        }
    }
    public void tallyPoints() {
        int gardensCount = 0;
        for (Player player: players) {
            player.discardAll();
            player.recycle();

            player.printAllDecks();
            for (int y = 0; y < player.drawDeck.numCards(); y++) {
                if (player.drawDeck.cardInfo(y).getName().equals("gardens")) {
                    gardensCount++;
                }
            }
            System.out.printf("\n\n\n\nNUMBER OF GARDENS: %d\n\n", gardensCount);
            player.modifyVictoryPoints(calculateVictoryPoints(player));
            player.modifyVictoryPoints(gardensCount * (player.drawDeck.numCards() / 10));
            System.out.printf("%-15s has %d victory points\n", player.getName(), player.getVictoryPoints());
        }
    }
}
