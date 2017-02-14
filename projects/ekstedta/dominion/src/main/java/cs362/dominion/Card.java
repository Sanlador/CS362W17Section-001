package cs362.dominion;

import java.util.ArrayList;

public enum Card {
    // cost, score, coin value, is action
    Curse(0, -1, 0, false),
    Estate(2, 1, 0, false),
    Duchy(5, 3, 0, false),
    Province(8, 6, 0, false),

    Copper(0, 0, 1, false),
    Silver(3, 0, 2, false),
    Gold(6, 0, 3, false),

    // Required to implement:
    //
    // Adventurer
    // Ambassador
    // Baron
    // CouncilRoom
    // Cutpurse
    // Embargo
    // Feast
    // Gardens
    // GreatHall
    // Mine

    // My optional cards
    // Market
    // Smithy
    // Village

    // Base set
    Adventurer(6),
    CouncilRoom(5),
    Feast(4),
    Gardens(4, 0, 0, false),
    Market(5),
    Mine(5),
    Smithy(4),
    Village(3),

    // Intrigue expansion
    Baron(4),
    GreatHall(3, 1, 0, true),

    // Seaside expansion
    Ambassador(3),
    Cutpurse(4),
    Embargo(2);

    int cost;
    int score;
    int coins;
    boolean isAction;

    Card(int cost) {
        this.cost = cost;
        this.score = 0;
        this.coins = 0;
        this.isAction = true;
    }

    Card(int cost, int score, int coins, boolean isAction) {
        this.cost = cost;
        this.score = score;
        this.coins = coins;
        this.isAction = isAction;
    }

    public int cost() { return this.cost; }
    public int score() { return this.score; }
    public int coins() { return this.coins; }
    public boolean isAction() { return this.isAction; }
    public boolean isTreasure() { return this.coins != 0; }

    // construct a list of cards
    public static ArrayList<Card> list(Card... cards) {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i = 0; i < cards.length; i++) {
            list.add(cards[i]);
        }
        return list;
    }
}
