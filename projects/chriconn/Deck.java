import java.util.*;
// package org.cs362.dominion;
/*
Class Deck
    Variables:
        Private:
            Deck deck

    Functions:
        Private:

        Public:
            Deck()
            Deck(String)
            Deck(int, Card)
            void      printDeck()
            void      printType(String type)
            void      shuffle()
            void      addBottomCard(Card card)
            Card      drawCard()
            boolean   empty()
            int       numCards()
            boolean   hasActions()
            void      addNumberOfCardType(int number, Card card)
            Card      cardInfo(int index)
            int       indexOf(String cardName)

        Deck:
            Description:
            Input:
            Output:
            Potential Errors:
                -

        Deck(String):
            Description:
            Input:
            Output:
            Potential Errors:
                -

        Deck(int, Card):
            Description:
            Input:
            Output:
            Potential Errors:
                -

        printDeck:
            Description:
            Input:
            Output:
            Potential Errors:
                -

        printType(String type):
            Description:
            Input:
            Output:
            Potential Errors:
                -

        shuffle:
            Description:
            Input:
            Output:
            Potential Errors:
                -

        addBottomCard(Card card):
            Description:
            Input:
            Output:
            Potential Errors:
                -

        drawCard:
            Description:
            Input:
            Output:
            Potential Errors:
                -

        empty:
            Description:
            Input:
            Output:
            Potential Errors:
                -

        numCards:
            Description:
            Input:
            Output:
            Potential Errors:
                -

        hasActions:
            Description:
            Input:
            Output:
            Potential Errors:
                -

        addNumberOfCardType(int number, Card card):
            Description:
            Input:
            Output:
            Potential Errors:
                -

        cardInfo(int index):
            Description:
            Input:
            Output:
            Potential Errors:
                -

        indexOf(String cardName):
            Description:
            Input:
            Output:
            Potential Errors:
                -

*/

public class Deck {
    private ArrayList<Card> deck;
    int tokens;
    public Card emptyDeckCard;


    /***************************
            CONSTRUCTORS
    ****************************/

    //Default constructor assigns empty space to deck
    public Deck() {
        deck = new ArrayList<Card>();
        tokens = 0;
        emptyDeckCard = null;
    }

    //Creates specific decks depending on name
    public Deck(String type) {
        deck = new ArrayList<Card>();
        type = type.toLowerCase();
        emptyDeckCard = null;

        if (type == "starter") {
            for (int x = 0; x < 7; x++) {
                deck.add(new Card("copper"));
            }

            for (int x = 0; x < 3; x++) {
                deck.add(new Card("estate"));
            }
        }
        else if (type == "oneofeach") {
            deck.add(new Card("adventurer"));
            deck.add(new Card("ambassador"));
            deck.add(new Card("baron"));
            deck.add(new Card("council room"));
            deck.add(new Card("cutpurse"));
            deck.add(new Card("embargo"));
            deck.add(new Card("feast"));
            deck.add(new Card("gardens"));
            deck.add(new Card("great hall"));
            deck.add(new Card("mine"));
            deck.add(new Card("gold"));
            deck.add(new Card("silver"));
            deck.add(new Card("copper"));
            deck.add(new Card("province"));
            deck.add(new Card("dutchy"));
            deck.add(new Card("estate"));
            deck.add(new Card("village"));
            deck.add(new Card("smithy"));
            deck.add(new Card("adventurer"));
            deck.add(new Card("curse"));
        }
    }

    //Creates a specific deck x number of one type of card
    public Deck(int number, Card card) {
        deck = new ArrayList<Card>();
        emptyDeckCard = new Card(card.getName());
        for (int x = 0; x < number; x++) {
            this.addCard(card);
        }
    }


    /***************************
             ACCESSORS
    ****************************/
    public int numCards()   {return deck.size();}
    public String getName() {
        return this.cardInfo(0).getName();
    }
    public int getTokens()  {return tokens;}
    public int indexOf(String cardName) {
        for (int x = 0; x < deck.size(); x++) {
            if (cardInfo(x).getName().equals(cardName)) {
                return x;
            }
        }
        return -1;
    }
    public int indexOfType(String type) {
        for (int x = 0; x < deck.size(); x++) {
            if (cardInfo(x).isType(type)) {
                return x;
            }
        }
        return -1;
    }
    public Card cardInfo(int index) {
        if (this.empty() && emptyDeckCard != null) {
            return emptyDeckCard;
        }
        return deck.get(index);
    }


    /***************************
              MUTATORS
    ****************************/
    public void setTokens(int number)     {tokens = number;}
    public void modifyTokens (int number) {tokens += number;}

    /***************************
             PRINTING
    ****************************/

    public void printDeck() {
        for (int x = 0; x < deck.size(); x++) {
            if (this.cardInfo(x) == null) {
                System.out.printf("NULL CARD\n");
            }
            else {
                System.out.printf("Card %2d: %s\n", (x+1), this.cardInfo(x).getName());
            }
        }
    }

    //prints all of one kind of card in the deck
    public void printType(String type) {
        type = type.toLowerCase();
        for (int x = 0; x < deck.size(); x++) {
            if (deck.get(x).isType(type)) {
                System.out.printf("Card #2%d: %-15sDescription: %s\n",(x+1), deck.get(x).getName(), deck.get(x).getDescription());
            }
        }
    }


    /***************************
              CHECKS
    ****************************/
    public boolean cardNameEquals(int index, String name) {
        return deck.get(index).getName().equals(name);
    }
    public boolean hasType(String type) {
        if (this.empty()) {
            return false;
        }
        for (int x = 0; x < deck.size(); x++) {
            if (cardInfo(x).isType(type)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasCard(String name) {
        if (this.empty()) {
            return false;
        }
        for (int x = 0; x < deck.size(); x++) {
            if (cardInfo(x).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public boolean empty()  {return (deck.size() == 0) ? true : false;}


    /***************************
              DRAWING
    ****************************/

    //draws a card from the top of the deck
    public Card drawCard() {
        //if deck is larger than 0 remove and return, else return null
        return (deck.size() > 0) ? deck.remove(0) : null;
    };
    public Card drawCardAtIndex(int index) {
        return deck.remove(index);
    }
    //draws the first card that matches that name or returns null
    public Card drawCard(String name) {
        for (int x = 0; x < deck.size(); x++) {
            if (this.cardNameEquals(x, name)) {
                return deck.remove(x);
            }
        }
        return null;
    }


    /***************************
               ADDING
    ****************************/
    public void addCard(Card card)       {deck.add(0, card);}
    public void addBottomCard(Card card) {deck.add(card);}
    public void addNumberOfCardType(int number, Card card) {
        for (int x = 0; x < number; x++) {
            this.addCard(card);
        }
    }


    /***************************
                OTHER
    ****************************/
    public void shuffle() {
        Collections.shuffle(deck, new Random(System.nanoTime()));
    };
}
