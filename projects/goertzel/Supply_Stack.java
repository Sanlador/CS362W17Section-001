package goertzel;


import java.util.Stack;


class Supply_Stack {

    private Card.CardName stackName;
    private Stack<Card> pile;
    private int cost;

    private boolean isEmbargoed;


    Supply_Stack(Card.CardName name, int count){
        stackName = name;
        cost = Card.stats(name)[0];
        isEmbargoed = false;
        pile = new Stack<>();
        for (int i = 0; i < count; i++) {
            pile.push(new Card(name));
        }
    }

    Card take(){
        if (!pile.isEmpty())
            return pile.pop();
        if (stackName == Card.CardName.CURSE)
            System.out.println("\t\tNo more CURSES");
        else
            System.out.println("\t\t" + stackName + " is empty");
        return null;
    }

    void putBack(Card c){
        if (c.name != stackName)
            System.out.println("\t\t" + c.name + " cannot be put here");
        pile.push(c);
    }

    void embargo(){
        isEmbargoed = true;
    }

    Card.CardName getName(){
        return stackName;
    }

    int getCost(){
        return cost;
    }

    int cardsRemaining(){
        return pile.size();
    }

    boolean isEmpty(){
        return pile.isEmpty();
    }

    boolean isEmbargoed(){
        return isEmbargoed;
    }
}
