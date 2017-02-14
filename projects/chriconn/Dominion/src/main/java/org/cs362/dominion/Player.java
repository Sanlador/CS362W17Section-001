package org.cs362.dominion;
import java.io.*;
import java.util.*;
public class Player {
    public Deck drawDeck;
    public Deck hand;
    public Deck discard;
    private String name;
    private int actions;
    private int value;
    private int buys;
    private int victoryPoints;
    public Player() {
        drawDeck = new Deck("starter");
        hand = new Deck();
        discard = new Deck();
        actions = 0;
        value = 0;
        buys = 0;
        name = "null";
        drawDeck.shuffle();
    }
    public Player(String playerName) {
        drawDeck = new Deck("starter");
        hand = new Deck();
        discard = new Deck();
        actions = 0;
        value = 0;
        buys = 0;
        name = playerName;
        drawDeck.shuffle();
    }
    public void showHand() {
        hand.printDeck();
    }
    public String getMoves() {
        return "You have:\n\t" + actions + " action(s)\n\t" + buys + " pruchase(s)\n\t" + value + " coin(s)";
    }
    public void buy(Deck type) {
        Card temp = type.drawCard();
        if (temp != null)
            discard.addCard(temp);
    }
    public Card draw() {
        if (drawDeck.empty())
            recycle();
        Card temp = drawDeck.drawCard();
        hand.addBottomCard(temp);
        return temp;
    }
    public void discard() {
        Card temp = hand.drawCard();
        if (temp != null)
            discard.addCard(temp);
    }
    public void discard(String cardName) {
        Card temp = hand.drawCard(cardName);
        if (temp != null)
            discard.addCard(temp);
    }
    public void discardAll() {
        while (!hand.empty()) {
            discard.addCard(hand.drawCard());
        }
    }
    public void discardAtIndex(int index) {
        if (index > hand.numCards()) {
            Card temp = hand.drawCardAtIndex(index);
            if (temp != null) {
                discard.addCard(temp);
            }
        }
    }
    public void draw(int number) {
        for (int x = 0; x < number; x++) {
            draw();
        }
    }
    public void recycle() {
        while (!discard.empty()) {
            drawDeck.addCard(discard.drawCard());
        }
        drawDeck.shuffle();
    }
    public String getName() {return name;}
    public int getActions() {return actions;}
    public int getValues()  {return value;}
    public int getBuys()    {return buys;}
    public int getVictoryPoints() {return victoryPoints;}
    public void modifyActions(int number) {actions = actions + number;}
    public void modifyValues(int number) {value = value + number;}
    public void modifyBuys(int number) {buys = buys + number;}
    public void modifyVictoryPoints(int number) {victoryPoints = victoryPoints + number;}
    public boolean hasActions() {
        if (actions > 0 && hand.hasType("action")) {
            return true;
        }
        return false;
    }
    public boolean hasMoves() {
        if (actions > 0 || buys > 0)
            return true;
        return false;
    }
    public void starterPoints() {
        actions = 1;
        buys = 1;
        value = 0;
    }
    public void sumTreasure() {
        for (int x = 0; x < hand.numCards(); x++) {
            if (hand.cardInfo(x).isType("treasure")) {
                value += hand.cardInfo(x).getValue();
            }
        }
    }
    public boolean handContainsType(String type) {
        return hand.hasType(type);
    }
    public void endTurn() {
        buys = 0;
        actions = 0;
        value = 0;
    }
    public void printHandType(String type) {
        hand.printType(type);
    }
    public void skipActionsPhase() {
        actions = 0;
    }

    public void printAllDecks() {
        String name = getName();
        System.out.println("\n" + name + "'s draw deck: ");
        drawDeck.printDeck();
        System.out.println("\n" +  name + "'s hand:");
        showHand();
        System.out.println("\n" + name + "'s discarded deck: ");
        discard.printDeck();
    }
    private static Scanner scanner = new Scanner(System.in);
}
