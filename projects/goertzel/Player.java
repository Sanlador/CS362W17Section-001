package goertzel;


import java.util.*;

import static java.util.Collections.shuffle;

public class Player {

    private String playerName;
    private Stack<Card> deck;
    private List<Card> hand;
    private List<Card> discardPile;

    private int actions=1;
    private int gold=0;
    private int buys=1;

    private boolean inCleanupPhase = false;

    GameState boardReference = null;

    Player(String name) {
        playerName = name;

        deck = new Stack<>();
        hand = new ArrayList<>();
        discardPile = new ArrayList<>();
    }

    void linkBoard(GameState board){
        boardReference = board;
        getStartingCards();
        reshuffle();
        initializeHand();
    }

    private void getStartingCards(){
        for (int i = 0; i < 3; i++)
            gain(Card.CardName.ESTATE, 10);
        for (int i = 0; i < 7; i++)
            gain(Card.CardName.COPPER, 10);
    }

    void playTurn(){
        printHand();
        actionPhase();
        playTreasures();
        buyPhase();
        cleanupPhase();
        System.out.println("\t" + playerName + " ended their turn");
    }

    private void initializeHand(){
        //System.out.println("\tInitialize Hand");
        actions = 1;
        gold = 0;
        buys = 1;
        drawCards(5);
    }

    private void actionPhase(){
        System.out.println("\tAction Phase");

        while (actions > 0){
            List<Card> actionCards = getActionCards(hand);
            if (actionCards.size() > 0) {
                playCard(actionCards.get(0));
            }
            else {
                return;  //no action cards
            }
        }
    }

    private List<Card> getActionCards(List<Card> hand){
        List<Card> actionCards = new ArrayList<>();
        for (Card c : hand)
            if (c.type == Card.CardType.ACTION)
                actionCards.add(c);
        sortHandByMoreActions(actionCards);
        return actionCards;
    }

    private void sortHandByMoreActions(List<Card> cards){
        cards.sort((Card c1, Card c2) -> c2.cardInc - c1.cardInc);
    }

    private void playTreasures(){
        System.out.println("\t" + playerName + " played their treasures");
        List<Card> treasures = new ArrayList<>();
        for (Card c : hand){
            if (c.type == Card.CardType.TREASURE)
                treasures.add(c);
        }
        for (Card t : treasures)
            playCard(t);
    }

    private void buyPhase(){
        System.out.println("\tBuy Phase\t" + buys + " Buys\t" + gold + " Gold");
        if (gold >= 8)
            buy(Card.CardName.PROVINCE);

        int loopPrevention = 0;
        while ((buys > 0) && (gold > 0) && (loopPrevention < 30)){

            if (gold <= 1){
                Random rand = new Random();
                int n = rand.nextInt(3);
//                System.out.println("#" + n);
                if (n > 1) return;
            }

            List<Card.CardName> options = new ArrayList<>();


            List<Supply_Stack> available = sortByCost();
            options.add(available.get(0).getName());
            if (available.size() >= 2)
                options.add(available.get(1).getName());
            Supply_Stack temp = getRandomSupply();
            if ((temp.getCost() <= gold) && (!temp.isEmpty())) options.add(temp.getName());
            temp = getRandomSupply();
            if ((temp.getCost() <= gold) && (!temp.isEmpty())) options.add(temp.getName());

            shuffle(options);
            buy(options.get(0));
            loopPrevention++;
        }

    }

    private List<Supply_Stack> sortByCost() {
        List<Supply_Stack> allSupply = new ArrayList<>();
        allSupply.addAll(boardReference.victory_supply);
        allSupply.addAll(boardReference.treasure_supply);
        allSupply.addAll(boardReference.kingdom_supply);

        List<Supply_Stack> toRemove = new ArrayList<>();
        for (Supply_Stack s : allSupply)
            if ((s.getCost() > gold) || (s.getName() == Card.CardName.CURSE) || (s.isEmpty()))
                toRemove.add(s);

        allSupply.removeAll(toRemove);

        allSupply.sort((Supply_Stack s1, Supply_Stack s2) -> s2.getCost() - s1.getCost());
        return allSupply;
    }

    private void cleanupPhase(){
        //System.out.println("\tCleanup Phase");
        inCleanupPhase = true;
        discardPile.addAll(hand);
        hand.clear();
        initializeHand();
        inCleanupPhase = false;
    }

    void drawCards(int num){
        for (int i = 0; i < num; i++)
            draw();
    }

    private void draw(){
        if (deck.isEmpty())
            reshuffle();
        if (!deck.isEmpty()) {
            Card drawn = deck.pop();
            if ((boardReference.getTurnCount() != 0) && (!inCleanupPhase))
                System.out.println("\t\t" + playerName + " drew " + drawn.name);
            hand.add(drawn);
        }
        else System.out.println("\t\tDeck is empty");
    }

    private void reshuffle(){
        if ((boardReference != null) && (boardReference.getTurnCount() != 0))
            System.out.println("\t" + playerName + " reshuffled their deck");
        deck.addAll(discardPile);
        discardPile.clear();
        shuffle(deck);
    }

    boolean gain(Card.CardName pile, int limit){
        Supply_Stack stack = findSupply(pile);
        if (stack.getCost() > limit) {
            System.out.println("\t\t" + playerName + " could not gain " + pile);
            return false;
        }
        Card temp = stack.take();
        if (temp == null)
            return false;

        if (boardReference.getTurnCount() != 0)
            System.out.println("\t\t" + playerName + " gained " + pile);
        discard(temp);
        return true;
    }

    void gainRandom(int limit){
        List<Supply_Stack> allSupply = new ArrayList<>();
        allSupply.addAll(boardReference.victory_supply);
        allSupply.addAll(boardReference.treasure_supply);
        allSupply.addAll(boardReference.kingdom_supply);

        allSupply.sort((Supply_Stack s1, Supply_Stack s2) -> s2.getCost() - s1.getCost());
        List<Supply_Stack> toRemove = new ArrayList<>();
        for (Supply_Stack s : allSupply){
            if ((s.getCost() > limit) || (s.getName() == Card.CardName.CURSE))
                toRemove.add(s);
        }
        allSupply.removeAll(toRemove);

        while (allSupply.size() > 3){
            allSupply.remove(allSupply.remove(allSupply.size()-1));
        }
        shuffle(allSupply);
        gain(allSupply.get(0).getName(), limit);
    }


    void mineGain(int limit){
        if ((limit >= 6) && !findSupply(Card.CardName.GOLD).isEmpty()) {
            putInHand(findSupply(Card.CardName.GOLD).take());
            System.out.println("\t\t" + playerName + " gained GOLD");
        }
        else if ((limit >= 3) && !findSupply(Card.CardName.SILVER).isEmpty()) {
            putInHand(findSupply(Card.CardName.SILVER).take());
            System.out.println("\t\t" + playerName + " gained SILVER");
        }
        else if ((limit >= 0) && !findSupply(Card.CardName.SILVER).isEmpty()) {
            putInHand(findSupply(Card.CardName.SILVER).take());
            System.out.println("\t\t" + playerName + " gained COPPER");
        }
    }

    private void buy(Card.CardName name){
        Supply_Stack target = findSupply(name);
        if (target.isEmpty()) {
            System.out.println("\t\t" + name + " is empty");
            return;
        }
        if (target.getCost() <= gold) {
            discard(target.take());
            if (target.isEmbargoed())
                gain(Card.CardName.CURSE, 99);
            gold -= target.getCost();
            buys--;
            System.out.println("\t\t" + playerName + " bought " + name);
        }
        else
            System.out.println("\t\t" + playerName + " could not buy " + name);
    }

    void putInHand(Card c){
        hand.add(c);
    }

    private void playCard(Card c){
        c.play(this);
    }

    void discard(Card c){
        discardPile.add(c);
        hand.remove(c);
    }

    Supply_Stack findSupply(Card.CardName name){
        return boardReference.findSupplyStack(name);
    }

    void trashCard(Card c){
        System.out.println("\t\t" + playerName + " trashed " + c.name);
        hand.remove(c);
        deck.remove(c);
        discardPile.remove(c);
    }

    Supply_Stack getRandomSupply(){
        List<Supply_Stack> allSupply = new ArrayList<>();
        allSupply.addAll(boardReference.victory_supply);
        allSupply.addAll(boardReference.treasure_supply);
        allSupply.addAll(boardReference.kingdom_supply);
        allSupply.addAll(boardReference.kingdom_supply);

        shuffle(allSupply);
        shuffle(allSupply);
        while ((allSupply.get(0).getName() == Card.CardName.CURSE) || (allSupply.get(0).isEmpty()))
            shuffle(allSupply);
        return allSupply.get(0);
    }

    void revealHand(){
        printHand();
    }

    Card randCardFromHand(){
        shuffle(hand);
        return hand.get(0);
    }

    boolean returnCard(Card.CardName name){
        for (Card c : hand){
            if (c.name == name){
                findSupply(name).putBack(c);
                hand.remove(c);
                System.out.println("\t\t" + playerName + " returned " + name);
                return true;
            }
        }
        System.out.println("\t\t" + playerName + " did not return any " + name);
        return false;
    }

    Card revealCardFromDeck(){
        if (deck.isEmpty())
            reshuffle();
        if (!deck.isEmpty()) {
            Card drawn = deck.pop();
            System.out.println("\t\t" + playerName + " revealed " + drawn.name);
            return drawn;
        }
        return null;
    }

    Card trashTreasure(){
        List<Card> treasures = new ArrayList<>();
        for (Card c : hand)
            if (c.type == Card.CardType.TREASURE)
                treasures.add(c);

        if (treasures.size() == 0)
            return null;

        shuffle(treasures);
        Card temp = treasures.get(0);
        trashCard(temp);
        return temp;
    }

    Card handHasCard(Card.CardName name){
        for (Card c : hand)
            if (c.name == name) return c;
        return null;
    }

    void incActions(int amount){
        actions += amount;
    }

    void incGold(int amount) {
        gold += amount;
    }

    void incBuys(int amount) {
        buys += amount;
    }

    void decActions(){
        actions--;
    }

    int calcVP() {
        if (boardReference == null) return 0;
        int sumVP = 0;
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(deck);
        allCards.addAll(hand);
        allCards.addAll(discardPile);
        for (Card c : allCards){
            if (c.name == Card.CardName.GARDENS) sumVP += getCardCount()/10;
            else sumVP += c.victoryPoints;
        }
        return sumVP;
    }

    String getPlayerName() {
        return playerName;
    }

    int getCardCount(){
        return deck.size() + hand.size() + discardPile.size();
    }

    void printInfo(){
        System.out.printf("\t\t%s\tDeck Size: %d\tVP: %d%n", playerName, getCardCount(), calcVP());
    }

    private void printDeck(){
        System.out.println("\t" + playerName + "'s deck:");
        printListOfCards(deck);
    }

    private void printHand(){
        System.out.println("\t" + playerName + "'s hand:");
        printListOfCards(hand);
    }

    private void printDiscard(){
        System.out.println("\t" + playerName + "'s discard pile:");
        printListOfCards(discardPile);
    }

    private void printListOfCards(List<Card> cards){
        int i = 0;
        for (Card c : cards) {
            if (i % 5 == 0) {
                if (i == 0) System.out.print("\t\t");
                else System.out.print("\n\t\t");
            }
            System.out.print(c.name + ", ");
            i++;
        }
        System.out.println();
    }

    public String toString(){
        return playerName;
    }

    List<Card> cloneDeck(){
        List<Card> cloned = new ArrayList<>();
        for (Card c : deck)
            cloned.add(new Card(c.name));
        return cloned;
    }

    List<Card> cloneHand(){
        List<Card> cloned = new ArrayList<>();
        for (Card c : hand)
            cloned.add(new Card(c.name));
        return cloned;
    }

    List<Card> cloneDiscardPile(){
        List<Card> cloned = new ArrayList<>();
        for (Card c : discardPile)
            cloned.add(new Card(c.name));
        return cloned;
    }

    int getActionCount(){
        return actions;
    }

    int getGoldCount(){
        return gold;
    }

    int getBuysCount(){
        return buys;
    }
}
