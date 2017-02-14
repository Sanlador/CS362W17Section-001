package org.cs362.dominion;
import java.util.*;
public class Card {

    private String name;
    private int value;
    private int cost;
    private int victoryPoints;
    private String description;
    private ArrayList<String> type;
    private int cards;
    private int actions;
    private int buys;
    private int coins;

    public Card() {
        name = "null";
        value = 0;
        cost = 0;
        victoryPoints = 0;
        description = "null";
        type = new ArrayList<String>();
        cards = 0;
        actions = 0;
        buys = 0;
        coins = 0;
    }
    public Card(String setName) {
        value = 0;
        cost = 0;
        victoryPoints = 0;
        description = "null";
        type = new ArrayList<String>();
        cards = 0;
        actions = 0;
        buys = 0;
        coins = 0;
        setName = setName.toLowerCase();
        if (setName.equals("gold")) {
            name           = "gold";
            value          = 3;
            cost           = 6;
            description    = "The gold card is a treasure card worth 3 coins";
            type.add("treasure");
        }
        else if (setName.equals("silver")) {
            name           = "silver";
            value          = 2;
            cost           = 3;
            description    = "The silver card is a treasure card worth 2 coins";
            type.add("treasure");
        }
        else if (setName.equals("copper")) {
            name           = "copper";
            value          = 1;
            cost           = 0;
            description    = "The copper card is a treasure card worth 1 coin";
            type.add("treasure");
        }
        else if (setName.equals("province")) {
            name           =  "province";
            cost           =  8;
            victoryPoints  =  6;
            description    = "The province is 8 victory points";
            type.add("victory");
        }
        else if (setName.equals("dutchy")) {
            name           =  "dutchy";
            cost           =  5;
            victoryPoints  =  3;
            description    = "The dutchy is 3 victory points";
            type.add("victory");
        }
        else if (setName.equals("estate")) {
            name           =  "estate";
            cost           =  2;
            victoryPoints  =  1;
            description    = "The estate is 1 victory points";
            type.add("victory");
        }
        else if (setName.equals("village")) {
            name           =  "village";
            cost           =  3;
            cards          =  1;
            actions        =  2;
            description    = "The village card allows for two extra actions";
            type.add("action");
        }
        else if (setName.equals("smithy")) {
            name           =  "smithy";
            cost           =  4;
            cards          =  3;
            description    = "The smithy card allows for three new cards";
            type.add("action");
        }
        else if (setName.equals("adventurer")) {
            name           =  "adventurer";
            cost           =  6;
            description    =  "Reveal cards from your deck until you reveal 2 treasure cards. Put those Treasure cards into your hand and discard the other reveal cards.";
            type.add("action");
            type.add("special action");
        }
        else if (setName.equals("witch")) {
            name           =  "witch";
            cost           =  5;
            description    =  "Each other player gains a Curse card.";
            cards          =  2;
            type.add("action");
            type.add("attack");
            type.add("special action");
        }
        else if (setName.equals("cellar")) {
            name           =  "cellar";
            cost           =  2;
            description    =  "Discard any number of cards. +1 Card per card discarded.";
            actions        =  1;
            type.add("action");
            type.add("special action");
        }
        else if (setName.equals("market")) {
            name           =  "market";
            cost           =  5;
            cards          =  1;
            actions        =  1;
            buys           =  1;
            coins          =  1;
            description    = "Gives the player an extra card, coin, buy and action";
            type.add("action");
        }
        else if (setName.equals("curse")) {
            name           =  "curse";
            cost           =  0;
            victoryPoints  =  -1;
            description    = "Counts as a negative victory point";
            type.add("curse");
            type.add("victory");
        }
        else if (setName.equals("ambassador")) {
            name           = "ambassador";
            cost           = 3;
            description    = "Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.";
            type.add("action");
            type.add("attack");
            type.add("special action");
        }
        else if (setName.equals("baron")) {
            name           = "baron";
            cost           = 4;
            description    = "You may discard an Estate card. If you do, +4 Coins. Otherwise, gain an Estate card.";
            type.add("action");
            type.add("special action");
        }
        else if (setName.equals("council room")) {
            name           = "council room";
            cost           = 5;
            cards          = 4;
            buys           = 1;
            description    = "+4 Cards, +1 Buy, Each other player draws a card";
            type.add("action");
            type.add("special action");
        }
        else if (setName.equals("cutpurse")) {
            name           = "cutpurse";
            cost           = 4;
            coins          = 2;
            description    = "Each other player discards a copper card (or reveals a hand with no copper)";
            type.add("action");
            type.add("attack");
            type.add("special action");
        }
        else if (setName.equals("embargo")) {
            name           = "embargo";
            cost           = 2;
            description    = "Trash for +2 coins. Put an Embargo token on top of a Supply pile. - A player gains a Curse card per Embargo token on that pile when a card is bought.";
            type.add("action");
            type.add("special action");
        }
        else if (setName.equals("feast")) {
            name           = "feast";
            cost           = 4;
            description    = "Trash this card. Gain a card costing up to 5 coins";
            type.add("action");
            type.add("special action");
        }
        else if (setName.equals("gardens")) {
            name           = "gardens";
            cost           = 4;
            description    = "Worth 1 victory for every 10 cards in your deck(rounded down)";
            type.add("victory");
        }
        else if (setName.equals("great hall")) {
            name           = "great hall";
            cost           = 3;
            cards          = 1;
            actions        = 1;
            victoryPoints  = 1;
            description    = "Lets the player draw an extra card and have an extra action. +1 victory point";
            type.add("action");
            type.add("victory");
            type.add("special action");
        }
        else if (setName.equals("mine")) {
            name           = "mine";
            cost           = 5;
            description    = "Trash a treasure card from your hand. Gain a treasure card costing up to 3 coins more and put that card in your hand";
            type.add("action");
            type.add("special action");
        }
        else if (setName.equals("salvager")) {
            name            = "salvager";
            cost            = 4;
            buys            = 1;
            description     = "Trash a card from your hand, gain value that the card is worth in purchasing power";
        }
    }

    public void setName(String setName)     {name = setName;}
    public void setValue(int setValue)      {value = setValue;}
    public void setCost(int setCost)        {cost = setCost;}
    public void setVictoryPoints(int setVictoryPoints) {victoryPoints = setVictoryPoints;}
    public void setDescription(String setDescription)  {description = setDescription;}
    public void setCards(int setCards)      { cards = setCards; }
    public void setActions(int setActions)  { actions = setActions; }
    public void setBuys(int setBuys)        { buys = setBuys; }
    public void setCoins(int setCoins)      { coins = setCoins; }

    public String getName()                 {return name;}
    public int getValue()                   {return value;}
    public int getCost()                    {return cost;}
    public int getVictoryPoints()           {return victoryPoints;}
    public String getDescription()          {return description;}
    public ArrayList<String> getType()      {return type;}
    public int getCards()                   {return cards;}
    public int getBuys()                    {return buys;}
    public int getActions()                 {return actions;}
    public int getCoins()                   {return coins;}

    public boolean isType(String typeName) {
        for (int x = 0; x < type.size(); x++) {
            if (type.get(x).equals(typeName)) {
                return true;
            }
        }
        return false;
    }
    public void printCard() {
        System.out.println("Card Info:" + "\nName: " + name + "\nValue: " + value + "\nCost: " + cost + "\nVictory Points: " + victoryPoints + "\nDescription: " + description + "\nExtra Cards to Draw: " + cards + "\nExtra Actions: " + actions + "\nExtra buys: " + buys + "\nExtra Coins: " + coins + "\n");
    }
}
